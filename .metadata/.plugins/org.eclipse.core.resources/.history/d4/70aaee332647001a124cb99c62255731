package br.com.alura.loja.resource;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.thoughtworks.xstream.annotations.XStreamAlias;

import br.com.alura.loja.dao.ProjetoDAO;
import br.com.alura.loja.modelo.Projeto;

@Path("projetos")
public class ProjetoResource {

	@Path("{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	
	public String busca(@PathParam("id") long id) {

		Projeto projeto = new ProjetoDAO().busca(id);

		return projeto.toJSON();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public String adiciona(String conteudo) {

		Projeto projeto = new Gson().fromJson(conteudo, Projeto.class);
		
		System.out.println(projeto);
		new ProjetoDAO().adiciona(projeto);

		return "<status>sucesso</status>";
	}
}
