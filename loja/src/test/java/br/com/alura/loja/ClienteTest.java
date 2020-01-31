package br.com.alura.loja;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.thoughtworks.xstream.XStream;

import br.com.alura.loja.modelo.Carrinho;

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

		Client client = ClientBuilder.newClient(); // Criação do cliente http com servidor
		WebTarget target = client.target("http://localhost:8082"); // Passo a URI base
		String conteudo = target.path("/carrinhos/1").request().get(String.class);
		// Informo que eu quero uma URI específica
		// Na requisição acima o servidor irá devolver uma resposta, por sua vez,
		// queremos somente a String que for recebida.

		 //System.out.println(conteudo);

		//Assert.assertTrue(conteudo.contains("<rua>Rua Vergueiro 3185")); // Verificando se o XML recebido contém o
																			// conteúdo desejado
		// O motivo do código acima é se saber se a conexão com o servidor funcionou
		// rodar como junit test
		 
		 Carrinho carrinho = (Carrinho) new XStream().fromXML(conteudo); // Deserializando o XML para objeto.
		 Assert.assertEquals("Rua Vergueiro 3185, 8 andar", carrinho.getRua()); // Teste direto com o objeto 

	}
	

}
