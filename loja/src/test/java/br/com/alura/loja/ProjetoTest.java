package br.com.alura.loja;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.thoughtworks.xstream.XStream;

import br.com.alura.loja.modelo.Projeto;
import junit.framework.Assert;

public class ProjetoTest {
	
	private HttpServer server; // Criação do atributo que recebe um server

	@Before // Antes de executar o teste inicializo o servidor
	public void startServidor() {
		server = Servidor.inicializaServidor();
	}
	
	@After // Depois do teste executado desligo o servidor
	public void mataServidor() {
		server.stop();
	}
	
	@Test
	public void testaQueAConexaoComOServidorFuncionaNoPathDeProjetos() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("http://localhost:8082"); // API local
		String conteudo = target.path("/projetos").request().get(String.class);
		
		Projeto projeto = (Projeto) new XStream().fromXML(conteudo); // Recebe o XML e converte novamente para objeto java
		
		Assert.assertEquals("Minha loja", projeto.getNome()); // Teste com o objeto em java
		
		//Assert.assertTrue(conteudo.contains("<nome>Minha loja"));

	}

}
