package org.mailmuncher.model;

import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

public class Mailbox {
	private static Mailbox instance;

	private Set<Mail> mailbox = new TreeSet<>();

	protected Mailbox() {
	}

	public static Mailbox getInstance() {
		if (instance == null)
			instance = new Mailbox();
		return instance;
	}

	public void addMail(Mail mail) {
		if (mail.getId() == null)
			mail.setId(UUID.randomUUID());
		mailbox.add(mail);
	}

	public Mail getMail(UUID uuid) {
		Optional<Mail> mailOptional = mailbox.stream().filter(mail -> mail.getId().equals(uuid)).findFirst();
		return mailOptional.isPresent() ? mailOptional.get() : null;
	}

	public Set<Mail> getMailbox() {
		return mailbox;
	}
}
