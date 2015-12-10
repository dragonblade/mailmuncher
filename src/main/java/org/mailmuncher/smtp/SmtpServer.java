package org.mailmuncher.smtp;

import org.subethamail.smtp.server.SMTPServer;

public class SmtpServer {
	private final int port;

	public SmtpServer(int port) {
		this.port = port;
	}

	public void start() {
		SMTPServer server = new SMTPServer(new MailHandlerFactory());
		server.setPort(port);
		server.setSoftwareName("MailMuncher SMTP server, version " + getClass().getPackage().getImplementationVersion());

		try {
			server.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
