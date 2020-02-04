package br.com.alura.loja;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.thoughtworks.xstream.XStream;

import br.com.alura.loja.modelo.Carrinho;
import br.com.alura.loja.modelo.Produto;

public class ClienteTest {

	private HttpServer server;

	@Before
	public void startaServidor() {
		server = Servidor.inicializaServidor();
	}
	
	@After
	public void mataServidor() {
		server.stop();
	}
	
	@Test
	public void testaQueBuscarUmCarrinhoTrazOCarrinhoEsperado() {

		Client client = ClientBuilder.newClient(); 
		WebTarget target = client.target("http://localhost:8082"); 
		String conteudo = target.path("/carrinhos/1").request().get(String.class);

		Carrinho carrinho = (Carrinho) new XStream().fromXML(conteudo); 
		Assert.assertEquals("Rua Vergueiro 3185, 8 andar", carrinho.getRua()); 
		
	}
	
	@Test
	public void testaQueSuportaNovosCarrinhos() {
		
		Client client = ClientBuilder.newClient(); 
		WebTarget target = client.target("http://localhost:8082");
		
		Carrinho carrinho = new Carrinho(); // Criando o carrinho e transformando em XML para realizar o POST
		carrinho.adiciona(new Produto(315l, "Oculos de sol", 150, 1));
		carrinho.setRua("Av. Salgado filho");
		carrinho.setCidade("Guarulhos");
		
		String xml = carrinho.toXML();
		
    	Entity<String> entity = Entity.entity(xml, MediaType.APPLICATION_XML); 
    	
    	Response response = target.path("/carrinhos").request().post(entity);
    	
    	Assert.assertEquals(201, response.getStatus()); // Teste 01: verificando se o status code é igual a 201 created
    	
    	                                         
    	String location = response.getHeaderString("Location"); // Teste 02: Recupeso a uri criada conforme POST
    	String conteudo = client.target(location).request().get(String.class); // Após chamo a requisição passando a localização da uri
    	Assert.assertTrue(conteudo.contains("Oculos")); // Recuperando o conteudo após a requisição acima eu vejo se o conteudo contém o esperado
    
	}
	

}
