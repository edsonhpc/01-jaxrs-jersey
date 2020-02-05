package br.com.alura.loja;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.filter.LoggingFilter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;

import br.com.alura.loja.modelo.Projeto;
import junit.framework.Assert;

public class ProjetoTest {
	
	private HttpServer server; // Criação do atributo que recebe um server
	private WebTarget target;
	private Client client;

	@Before // Antes de executar o teste inicializo o servidor
	public void startServidor() {
		server = Servidor.inicializaServidor();
		ClientConfig config = new ClientConfig(); // Crio a configuração de um client
		config.register(new LoggingFilter()); // Registro a configuração com filtro de Loggin
		this.client = ClientBuilder.newClient(config); // Crio o Client baseado nessa configuração
		this.target = client.target("http://localhost:8082"); // API local
	}
	
	@After // Depois do teste executado desligo o servidor
	public void mataServidor() {
		server.stop();
	}
	
	@Test
	public void testaQueAConexaoComOServidorFuncionaNoPathDeProjetos() {
	
		String conteudo = target.path("/projetos/1").request().get(String.class);
		
		Projeto projeto = new Gson().fromJson(conteudo, Projeto.class);
		
		Assert.assertEquals("Minha loja", projeto.getNome()); 

	}
	
	@Test
	public void testaQueAdicionaNovosProjetos() {
	
	  Projeto projeto = new Projeto(2l, "Planejamento Eisa", 2020); // Crio um projeto novo
	  
	  String json = projeto.toJSON(); // Converto o novo objeto para Json
	
	  Entity<String> entity = Entity.entity(json, MediaType.APPLICATION_JSON); // Usamos o entity para representar o que será enviado

      Response response = target.path("/projetos").request().post(entity); // Faço a chamada passando o json + cabeçalho com mediaType
      Assert.assertEquals(201, response.getStatus()); // 1º Teste: Checa se o recurso foi criado
      
      String location = response.getHeaderString("Location");
      String conteudo = client.target(location).request().get(String.class); 
      Assert.assertTrue(conteudo.contains("Eisa")); // 2º Teste: Verifica se o conteúdo contém a palavra Eisa  
      	
	}
	
}
