package org.mailmuncher.http;

import org.mailmuncher.model.Mail;
import org.mailmuncher.model.Mailbox;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.mail.MessagingException;
import java.util.Collections;
import java.util.UUID;
import java.util.stream.Collectors;

@org.springframework.stereotype.Controller
public class Controller {

	@RequestMapping(path = "/", method = RequestMethod.GET)
	public String index(Model model) {
		model.addAttribute("mailbox", Mailbox.getInstance().getMailbox());
		return "index";
	}

	@RequestMapping(path = "/{uuid}", method = RequestMethod.GET)
	public String mail(@PathVariable String uuid, Model model) {
		Mail mail;
		try {
			mail = Mailbox.getInstance().getMail(UUID.fromString(uuid));
		} catch (IllegalArgumentException e) {
			return "redirect:/";
		}
		if (mail == null)
			return "redirect:/";

		String headers = null;
		try {
			headers = Collections.list(mail.getMimeMessage().getAllHeaderLines()).stream()
					.map(Object::toString)
					.collect(Collectors.joining("\n"))
					.toString();
		} catch (MessagingException e) {
			e.printStackTrace();
		}

		String plainEscaped = null;
		if (mail.getBody() != null) {
			plainEscaped = mail.getBody().replaceAll("\\n|\\r\\n", "<br>");
		}

		model.addAttribute("current", mail);
		model.addAttribute("headers", headers);
		model.addAttribute("plainEscaped", plainEscaped);
		model.addAttribute("mailbox", Mailbox.getInstance().getMailbox());
		return "mail";
	}

	@RequestMapping(path = "/{uuid}/view", method = RequestMethod.GET)
	public String html(@PathVariable String uuid, Model model) {
		Mail mail;
		try {
			mail = Mailbox.getInstance().getMail(UUID.fromString(uuid));
		} catch (IllegalArgumentException e) {
			return "redirect:/";
		}
		if (mail == null)
			return "redirect:/";

		model.addAttribute("html", mail.getBodyHtml());
		return "html";
	}
}
