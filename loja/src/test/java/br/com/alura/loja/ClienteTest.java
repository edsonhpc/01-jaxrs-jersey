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
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.thoughtworks.xstream.XStream;

import br.com.alura.loja.modelo.Carrinho;
import br.com.alura.loja.modelo.Produto;

public class ClienteTest {

	private HttpServer server;
	private WebTarget target;
	private Client client;

	@Before
	public void startaServidor() {
		server = Servidor.inicializaServidor();
		ClientConfig config = new ClientConfig(); // Instancio uma configuração de client
		config.register(new LoggingFilter()); // Com a configuração do client, registro ela com API de Log que é LogginFilter
		this.client = ClientBuilder.newClient(config);  // Depois crio o ClientBuilder Baseado nesse configuração
		this.target = client.target("http://localhost:8082"); 
	}
	
	@After
	public void mataServidor() {
		server.stop();
	}
	
	@Test
	public void testaQueBuscarUmCarrinhoTrazOCarrinhoEsperado() {
		Carrinho carrinho = target.path("/carrinhos/1").request().get(Carrinho.class);
		Assert.assertEquals("Rua Vergueiro 3185, 8 andar", carrinho.getRua()); 
	}
	
	@Test
	public void testaQueSuportaNovosCarrinhos() {
		
		Carrinho carrinho = new Carrinho(); // Criando o carrinho e transformando em XML para realizar o POST
		carrinho.adiciona(new Produto(315l, "Oculos de sol", 150, 1));
		carrinho.setRua("Av. Salgado filho");
		carrinho.setCidade("Guarulhos");
		
    	Entity<Carrinho> entity = Entity.entity(carrinho, MediaType.APPLICATION_XML); 
    	Response response = target.path("/carrinhos").request().post(entity);
    	
    	Assert.assertEquals(201, response.getStatus()); // Testando o status de resposta                                     
    	String location = response.getHeaderString("Location"); 
    	
    	Carrinho carrinhoCarregado = client.target(location).request().get(Carrinho.class); // Recuperando os dados do Post 
    	Assert.assertEquals("Oculos de sol",carrinhoCarregado.getProdutos().get(0).getNome()); 
    
	}
	
}
