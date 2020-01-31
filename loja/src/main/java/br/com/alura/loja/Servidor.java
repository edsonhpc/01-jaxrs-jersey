package br.com.alura.loja;

import java.io.IOException;
import java.net.URI;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

public class Servidor {

	public static void main(String[] args) throws IOException {
		
		HttpServer server = inicializaServidor();
		System.out.println("Servidor Rodando...");
		System.in.read();
		server.stop();

	}

	public static HttpServer inicializaServidor() { // Abstração na inicialização do servidor
		URI uri = URI.create("http://localhost:8082/"); // Criando URI e portal que o servidor deve rodar
		ResourceConfig config = new ResourceConfig().packages("br.com.alura.loja"); // Com o config criamos a configuração para o Grizzly que nossa aplicação é baseada em JAX-RS
		HttpServer server = GrizzlyHttpServerFactory.createHttpServer(uri, config);
		
		exibirStacktraceNoConsole();
		
		return server;
		
	}

	private static void exibirStacktraceNoConsole() {
		Logger l = Logger.getLogger("org.glassfish.grizzly.http.server.HttpHandler");
		l.setLevel(Level.FINE);
		l.setUseParentHandlers(false);
		ConsoleHandler ch = new ConsoleHandler();
		ch.setLevel(Level.ALL);
		l.addHandler(ch);
	}  

}
