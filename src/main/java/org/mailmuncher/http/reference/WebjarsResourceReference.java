package org.mailmuncher.http.reference;

import de.agilecoders.wicket.webjars.request.resource.IWebjarsResourceReference;
import org.apache.wicket.request.resource.PackageResourceReference;

import static de.agilecoders.wicket.webjars.util.WebjarsVersion.useRecent;

public class WebjarsResourceReference extends PackageResourceReference implements IWebjarsResourceReference {
	private static final long serialVersionUID = 1L;

	private String originalName;

	public WebjarsResourceReference(String name) {
		super(WebjarsResourceReference.class, useRecent(name));

		this.originalName = name;
	}

	@Override
	public String getOriginalName() {
		return originalName;
	}
}
