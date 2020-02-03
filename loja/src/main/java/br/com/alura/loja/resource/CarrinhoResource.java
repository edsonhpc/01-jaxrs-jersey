package br.com.alura.loja.resource;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.thoughtworks.xstream.XStream;

import br.com.alura.loja.dao.CarrinhoDAO;
import br.com.alura.loja.modelo.Carrinho;

@Path("carrinhos")
public class CarrinhoResource {

	// Também com essa anotação GET estamos dizendo que seu acesso será do tipo GET médoto tradicional que busca dados do servidor p/ cliente
	// Nesta anotação estamos informando que o método busca produz conteúdo do tipo XML
	@Path("{id}")
	@GET 
	@Produces(MediaType.APPLICATION_XML) // Anotação necessária para que o nosso cliente saiba que o conteúdo recebido é XML/JSON
	public String busca(@PathParam("id") long id) {
		System.out.println("PathParam = " + id);
		Carrinho carrinho = new CarrinhoDAO().busca(id); // Busca o id 1 do carrinhoDAO
		
		return carrinho.toXML(); // Informo que o retorno para o nosso cliente é XML ou JSON
		
	}
	
	@POST
	@Produces(MediaType.APPLICATION_XML)
	public String adiciona(String conteudo) {
		
		Carrinho carrinho = (Carrinho) new XStream().fromXML(conteudo);
		new CarrinhoDAO().adiciona(carrinho);
		
		return "<status>sucesso</status>";
	}
	
}
