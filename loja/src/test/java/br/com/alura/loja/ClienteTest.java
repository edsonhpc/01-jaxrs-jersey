package br.com.alura.loja;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.junit.Test;

import junit.framework.Assert;

public class ClienteTest {

	@Test
	public void testaQueAConexaoComServidorFunciona() {
		
		Client client = ClientBuilder.newClient(); // Criação do cliente http com servidor
		WebTarget target = client.target("http://www.mocky.io"); // Passo a URI base 
		String conteudo = target.path("/v2/52aaf5deee7ba8c70329fb7d").request().get(String.class); 
		// Informo que eu quero uma URI específica
		// Na requisição acima o servidor irá devolver uma resposta, por sua vez, queremos somente a String que for recebida.
		
		//System.out.println(conteudo);
		
		Assert.assertTrue(conteudo.contains("<rua>Rua Vergueiro 3185")); // Verificando se o XML recebido contém o conteúdo desejado
		// O motivo do código acima é se saber se a conexão com o servidor funcionou rodar como junit test
		
	}
	
	@Test
    public void testaQueAConexaoComOServidorFuncionaNoPathDeProjetos() {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8081");
        String conteudo = target.path("/projetos").request().get(String.class);
        Assert.assertTrue(conteudo.contains("<nome>Minha loja"));


    }
}
