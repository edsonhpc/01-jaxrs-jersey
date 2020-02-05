package br.com.alura.loja.resource;

import java.net.URI;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
	@Consumes(MediaType.APPLICATION_XML) // Neste caso informo o mediaType que o servidor irá consumir os dados.
	public Response adiciona(String conteudo) { // O Retorno será do tipo Response = Resposta
		
		Carrinho carrinho = (Carrinho) new XStream().fromXML(conteudo);
		new CarrinhoDAO().adiciona(carrinho);
		
		URI uri = URI.create("/carrinhos/"+carrinho.getId()); // Depois de criado o carrinho crio a URI Location de onde o recurso está.
				
		return Response.created(uri).build(); // No final devolvo para o cliente a resposta de recurso criado, passando o Location
		                                     // Ao utilizar o Response.creaded será devolvido o status code 201 para o cliente
		                                    // Os status code é um padrão a ser seguido por toda web
	}
	
	@Path("{id}/produtos/{produtoId}") // Criação do path com a URI que podemos chamar de subrecurso, remover produto do carrinho
	@DELETE
	public Response removerProduto(@PathParam("id") long id , @PathParam("produtoId") long produtoId ) { // Pego valores do path

		Carrinho carrinho = new CarrinhoDAO().busca(id); // Busco primeiro o carrinho
		carrinho.remove(produtoId); // Removo o produto passando o produtoId parametro recebido no path
		
	return Response.ok().build(); // Devolvo o status 200 OK
		
	}
	
	
	
}
