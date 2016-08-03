package org.mailmuncher;

import org.mailmuncher.http.HttpServer;
import org.mailmuncher.smtp.SmtpServer;

import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
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
						"Test mail with subject\nTest mail with subject",
						null);
				sendTestMail(session,
						"from@example.com",
						"to@example.com",
						null,
						"Test mail without subject\nTest mail without subject",
						null);
				sendTestMail(session,
						"from@example.com",
						"to@example.com",
						"Test mail multipart",
						"Test mail multipart\nTest mail multipart",
						"<p>Test mail multipart</p>\n<br>\n<p>Test mail multipart</p>");
				sendTestMail(session,
						"from@example.com",
						"to@example.com",
						"Test mail HTML only",
						null,
						"<p>Test mail HTML only</p>\n<br>\n<p>Test mail HTML only</p>");
			}
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	private static void sendTestMail(Session session, String from, String to, String subject, String bodyPlain,
									 String bodyHtml) throws MessagingException {
		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress(from));
		message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(to));
		message.setSubject(subject);
		message.setSentDate(new Date());
		if (bodyPlain != null && bodyHtml != null) {
			Multipart multipart = new MimeMultipart();
			BodyPart plain = new MimeBodyPart();
			plain.setContent(bodyPlain, "text/plain");
			BodyPart html = new MimeBodyPart();
			html.setContent(bodyHtml, "text/html");
			multipart.addBodyPart(plain);
			multipart.addBodyPart(html);
			message.setContent(multipart);
		} else if (bodyPlain != null) {
			message.setText(bodyPlain, "utf-8", "plain");
		} else if (bodyHtml != null) {
			message.setText(bodyHtml, "utf-8", "html");
		}

		Transport.send(message);
	}
}
