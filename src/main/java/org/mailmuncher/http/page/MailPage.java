package org.mailmuncher.http.page;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.mailmuncher.model.Mail;
import org.mailmuncher.model.MailBox;

import javax.mail.MessagingException;
import java.util.Collections;
import java.util.UUID;
import java.util.stream.Collectors;

public class MailPage extends BasePage {
	private Mail mail;

	public MailPage() {
		super();

		setResponsePage(getApplication().getHomePage());
	}

	public MailPage(PageParameters parameters) {
		super(parameters);

		if (parameters.get("uuid").isEmpty()) {
			setResponsePage(getApplication().getHomePage());
			return;
		}
		mail = MailBox.getInstance().getMail(UUID.fromString(parameters.get("uuid").toString()));
		if (mail == null) {
			setResponsePage(getApplication().getHomePage());
			return;
		}

		init();
	}

	private void init() {
		String headers = null;
		try {
			headers = Collections.list(mail.getMimeMessage().getAllHeaderLines()).stream()
					.map(Object::toString)
					.collect(Collectors.joining("\n"))
					.toString();
		} catch (MessagingException e) {
			e.printStackTrace();
		}

		this.add(new Label("subject", new PropertyModel<>(mail, "subject")) {
			private static final long serialVersionUID = 1L;

			@Override
			public void onComponentTagBody(MarkupStream markupStream, ComponentTag openTag) {
				if (getDefaultModelObject() != null)
					super.onComponentTagBody(markupStream, openTag);
				else
					replaceComponentTagBody(markupStream, openTag, getString("empty-subject"));
			}
		});
		this.add(new Label("from", new PropertyModel<>(mail, "from")));
		this.add(new Label("to", new PropertyModel<>(mail, "to")));
		this.add(new Label("received", new PropertyModel<>(mail, "received")));
		this.add(new Label("headers", headers));
		this.add(new Label("body", new PropertyModel<>(mail, "body")));
		this.add(new MultiLineLabel("bodyHtmlEscaped", new PropertyModel<>(mail, "bodyHtml")));
	}
}
