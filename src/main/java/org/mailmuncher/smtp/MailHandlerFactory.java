package org.mailmuncher.smtp;

import org.mailmuncher.model.Mail;
import org.mailmuncher.model.MailBox;
import org.subethamail.smtp.MessageContext;
import org.subethamail.smtp.MessageHandler;
import org.subethamail.smtp.MessageHandlerFactory;
import org.subethamail.smtp.RejectException;

import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.Properties;

public class MailHandlerFactory implements MessageHandlerFactory {
	@Override
	public MessageHandler create(MessageContext messageContext) {
		return new MailHandler(messageContext);
	}

	class MailHandler implements MessageHandler {
		MessageContext context;
		Mail mail;

		public MailHandler(MessageContext context) {
			this.context = context;
			this.mail = new Mail();
		}

		@Override
		public void from(String from) throws RejectException {
			this.mail.setFrom(from);
		}

		@Override
		public void recipient(String recipient) throws RejectException {
			this.mail.setTo(recipient);
		}

		@Override
		public void data(InputStream data) throws RejectException, IOException {
			String rawMail = this.convertStreamToString(data);
			mail.setRawMail(rawMail);

			Session session = Session.getDefaultInstance(new Properties());
			InputStream is = new ByteArrayInputStream(rawMail.getBytes());

			try {
				MimeMessage message = new MimeMessage(session, is);
				mail.setSubject(message.getSubject());
				mail.setMimeMessage(message);

				Object messageContent = message.getContent();
				if (messageContent instanceof Multipart) {
					Multipart multipart = (Multipart) messageContent;
					for (int i = 0; i < multipart.getCount(); i++) {
						BodyPart bodyPart = multipart.getBodyPart(i);
						String contentType = bodyPart.getContentType();

						if (contentType.matches("text/plain.*"))
							mail.setBody(convertStreamToString(bodyPart.getInputStream()));
						else if (contentType.matches("text/html.*"))
							mail.setBodyHtml(convertStreamToString(bodyPart.getInputStream()));
					}
				} else if (messageContent instanceof InputStream) {
					InputStream mailContent = (InputStream) messageContent;
					mail.setBody(convertStreamToString(mailContent));
				} else if (messageContent instanceof String) {
					String contentType = message.getContentType();

					if (contentType.matches("text/plain.*"))
						mail.setBody(messageContent.toString());
					else if (contentType.matches("text/html.*"))
						mail.setBodyHtml(messageContent.toString());
				}
			} catch (MessagingException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void done() {
			mail.setReceived(LocalDateTime.now());

			MailBox.getInstance().addMail(mail);
		}

		/**
		 * Converts given input stream to String
		 *
		 * @param is InputStream
		 * @return String
		 */
		protected String convertStreamToString(InputStream is) {
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			StringBuilder stringBuilder = new StringBuilder();

			String line;
			try {
				while ((line = reader.readLine()) != null) {
					stringBuilder.append(line);
					stringBuilder.append("\n");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			return stringBuilder.toString();
		}
	}
}