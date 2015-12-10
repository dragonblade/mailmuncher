package org.mailmuncher.http;

import de.agilecoders.wicket.webjars.WicketWebjars;
import org.apache.wicket.Page;
import org.apache.wicket.markup.html.IPackageResourceGuard;
import org.apache.wicket.markup.html.SecurePackageResourceGuard;
import org.apache.wicket.protocol.http.WebApplication;
import org.mailmuncher.http.page.MailListPage;
import org.mailmuncher.http.page.MailPage;

public class WicketApplication extends WebApplication {
	@Override
	public Class<? extends Page> getHomePage() {
		return MailListPage.class;
	}

	@Override
	public void init() {
		super.init();

		mountPage("/mail/${uuid}", MailPage.class);

		WicketWebjars.install(this);

		final IPackageResourceGuard packageResourceGuard = getResourceSettings().getPackageResourceGuard();
		if (packageResourceGuard instanceof SecurePackageResourceGuard) {
			SecurePackageResourceGuard guard = (SecurePackageResourceGuard) packageResourceGuard;
			guard.addPattern("+*.woff");
			guard.addPattern("+*.woff2");
			guard.addPattern("+*.eot");
			guard.addPattern("+*.svg");
			guard.addPattern("+*.ttf");
			guard.addPattern("+*.css.map");
			guard.addPattern("+*.min.map");
		}
	}
}