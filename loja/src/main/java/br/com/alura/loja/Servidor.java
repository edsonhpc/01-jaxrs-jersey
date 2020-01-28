package br.com.alura.loja;

import java.io.IOException;
import java.net.URI;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

public class Servidor {

	public static void main(String[] args) throws IOException {
		
		URI uri = URI.create("http://localhost:8081"); // Criando URI e portal que o servidor deve rodar
		ResourceConfig config = new ResourceConfig().packages("br.com.alura.loja"); // Com o config criamos a configuração para o Grizzly que nossa aplicação é baseada em JAX-RS
		HttpServer server = GrizzlyHttpServerFactory.createHttpServer(uri, config);
		
		System.out.println("Servidor Rodando...");
		System.in.read(); // Parando o servidor quando usuário apertar o enter.
		server.stop(); // Servidor parado.

	}

}
