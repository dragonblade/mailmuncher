package org.mailmuncher.model;

import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.util.UUID;

public class Mail implements Comparable<Mail> {
	private UUID id;

	private String from;

	private String to;

	private String subject;

	private String body;

	private String bodyHtml;

	private String rawMail;

	private MimeMessage mimeMessage;

	private LocalDateTime received;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getBodyHtml() {
		return bodyHtml;
	}

	public void setBodyHtml(String bodyHtml) {
		this.bodyHtml = bodyHtml;
	}

	public String getRawMail() {
		return rawMail;
	}

	public void setRawMail(String rawMail) {
		this.rawMail = rawMail;
	}

	public MimeMessage getMimeMessage() {
		return mimeMessage;
	}

	public void setMimeMessage(MimeMessage mimeMessage) {
		this.mimeMessage = mimeMessage;
	}

	public LocalDateTime getReceived() {
		return received;
	}

	public void setReceived(LocalDateTime received) {
		this.received = received;
	}

	@Override
	public int compareTo(Mail o) {
		return o.getReceived().compareTo(this.getReceived());
	}
}
