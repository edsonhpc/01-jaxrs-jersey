package br.com.alura.loja;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
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
		String conteudo = target.path("/projetos/1").request().get(String.class);
		
		Projeto projeto = new Gson().fromJson(conteudo, Projeto.class);
		
		Assert.assertEquals("Minha loja", projeto.getNome()); 

	}
	
	@Test
	public void testaQueAdicionaNovosProjetos() {
	  Client client = ClientBuilder.newClient();
	  WebTarget target = client.target("http://localhost:8082");
	  
	  Projeto projeto = new Projeto(2l, "Planejamento Eisa", 2020); // Crio um projeto novo
	  
	  String json = projeto.toJSON(); // Converto o novo objeto para Json
	
	  Entity<String> entity = Entity.entity(json, MediaType.APPLICATION_JSON); // Usamos o entity para representar o que será enviado

      Response response = target.path("/projetos").request().post(entity); // Faço a chamada passando o json + cabeçalho com mediaType
      Assert.assertEquals("<status>sucesso</status>", response.readEntity(String.class)); // Executo o teste para saber se deu certo
	  
	  
	  
		
	}
	

}
