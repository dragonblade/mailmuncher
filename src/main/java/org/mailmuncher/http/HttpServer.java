package org.mailmuncher.http;

import org.apache.wicket.protocol.http.ContextParamWebApplicationFactory;
import org.apache.wicket.protocol.http.WicketFilter;
import org.apache.wicket.protocol.http.WicketServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class HttpServer {
	private final int port;

	public HttpServer(int port) {
		this.port = port;
	}

	public void start() {
		Server server = new Server(this.port);

		ServletContextHandler sch = new ServletContextHandler(ServletContextHandler.SESSIONS);
		ServletHolder sh = new ServletHolder(WicketServlet.class);
		sh.setInitParameter(ContextParamWebApplicationFactory.APP_CLASS_PARAM, WicketApplication.class.getName());
		sh.setInitParameter(WicketFilter.FILTER_MAPPING_PARAM, "/*");

		sh.setInitParameter("wicket.configuration", "deployment");

		sch.addServlet(sh, "/*");
		server.setHandler(sch);

		try {
			server.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
