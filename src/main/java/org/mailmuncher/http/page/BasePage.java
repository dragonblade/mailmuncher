package org.mailmuncher.http.page;

import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.mailmuncher.http.reference.BootstrapCssResourceReference;
import org.mailmuncher.http.reference.BootstrapJavascriptResourceReference;
import org.mailmuncher.http.reference.JqueryJavascriptResourceReference;

public abstract class BasePage extends WebPage {
	public BasePage() {
		super();
		init();
	}

	public BasePage(PageParameters parameters) {
		super(parameters);
		init();
	}

	private void init() {
		this.add(new BookmarkablePageLink<Void>("brandLink", getApplication().getHomePage()));
		this.add(new BookmarkablePageLink<Void>("homeLink", getApplication().getHomePage()));
		this.add(new Label("version", new StringResourceModel("version",
				new Model<>(getClass().getPackage().getImplementationVersion()))));
	}

	@Override
	public void renderHead(IHeaderResponse response) {
		super.renderHead(response);

		response.render(CssHeaderItem.forReference(new BootstrapCssResourceReference()));
		response.render(JavaScriptHeaderItem.forReference(new JqueryJavascriptResourceReference()));
		response.render(JavaScriptHeaderItem.forReference(new BootstrapJavascriptResourceReference()));
	}
}
