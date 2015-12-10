package org.mailmuncher.http.page;

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.mailmuncher.model.Mail;
import org.mailmuncher.model.MailBox;

import java.util.ArrayList;
import java.util.List;

public class MailListPage extends BasePage {
	public MailListPage() {
		super();
		init();
	}

	public MailListPage(PageParameters parameters) {
		super(parameters);
		init();
	}

	private void init() {
		IModel<List<Mail>> mailList = new AbstractReadOnlyModel<List<Mail>>() {
			@Override
			public List<Mail> getObject() {
				return new ArrayList<>(MailBox.getInstance().getMailBox());
			}
		};

		this.add(new ListView<Mail>("mail", mailList) {
			@Override
			protected void populateItem(ListItem<Mail> item) {
				item.add(new Label("from", new PropertyModel<>(item.getModel(), "from")));
				item.add(new Label("to", new PropertyModel<>(item.getModel(), "to")));
				MarkupContainer viewLink = new BookmarkablePageLink<>("view", MailPage.class,
						new PageParameters().add("uuid", item.getModelObject().getId()));
				viewLink.add(new Label("subject", new PropertyModel<>(item.getModel(), "subject")) {
					private static final long serialVersionUID = 1L;

					@Override
					public void onComponentTagBody(MarkupStream markupStream, ComponentTag openTag) {
						if (getDefaultModelObject() != null)
							super.onComponentTagBody(markupStream, openTag);
						else
							replaceComponentTagBody(markupStream, openTag, getString("empty-subject"));
					}
				});
				item.add(viewLink);
				item.add(new Label("received", new PropertyModel<>(item.getModel(), "received")));
			}
		});
	}
}
