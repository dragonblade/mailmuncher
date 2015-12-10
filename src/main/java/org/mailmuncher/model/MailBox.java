package org.mailmuncher.model;

import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

public class MailBox {
	private static MailBox instance;

	private Set<Mail> mailBox = new TreeSet<>();

	protected MailBox() {
	}

	public static MailBox getInstance() {
		if (instance == null)
			instance = new MailBox();
		return instance;
	}

	public void addMail(Mail mail) {
		if (mail.getId() == null)
			mail.setId(UUID.randomUUID());
		mailBox.add(mail);
	}

	public Mail getMail(UUID uuid) {
		Optional<Mail> mailOptional = mailBox.stream().filter(mail -> mail.getId().equals(uuid)).findFirst();
		return mailOptional.isPresent() ? mailOptional.get() : null;
	}

	public Set<Mail> getMailBox() {
		return mailBox;
	}
}
