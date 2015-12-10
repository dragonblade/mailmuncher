package org.mailmuncher;

import org.mailmuncher.http.HttpServer;
import org.mailmuncher.smtp.SmtpServer;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

public class MailMuncher {
	public static void main(String[] args) {
		SmtpServer smtp = new SmtpServer(2525);
		smtp.start();

		HttpServer http = new HttpServer(2580);
		http.start();

		addTestMails(2525);
	}

	private static void addTestMails(int port) {
		Properties properties = new Properties();
		properties.put("mail.smtp.host", "localhost");
		properties.put("mail.smtp.port", port);
		properties.put("mail.smtp.debug", true);

		Session session = Session.getInstance(properties);

		try {
			for (int i = 0; i < 2; i++) {
				sendTestMail(session,
						"from@example.com",
						"to@example.com",
						"Test mail",
						"Test mail with subject");
				sendTestMail(session,
						"from@example.com",
						"to@example.com",
						null,
						"Test mail without subject");
			}
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	private static void sendTestMail(Session session, String from, String to, String subject, String body)
			throws MessagingException {
		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress(from));
		message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(to));
		message.setSubject(subject);
		message.setSentDate(new Date());
		message.setText(body);

		Transport.send(message);
	}
}
