package org.mailmuncher.http;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class HttpServer {
	private final int port;

	public HttpServer(int port) {
		this.port = port;
	}

	public void start() {
		Server server = new Server(port);

		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
		context.setConfigLocation("org.mailmuncher.http");
		context.register(Config.class);

		ServletContextHandler contextHandler = new ServletContextHandler();
		contextHandler.addServlet(new ServletHolder(new DispatcherServlet(context)), "/*");
		server.setHandler(contextHandler);

		try {
			server.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
