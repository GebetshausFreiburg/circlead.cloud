package org.rogatio.circlead.view;

import org.jsoup.nodes.Element;
import org.rogatio.circlead.control.synchronizer.ISynchronizer;

public class DefaultReport implements IReport {

	private String name = null;

	@Override
	public String getName() {
		return name;
	}

	protected void setName(String name) {
		this.name = name;
	}

	@Override
	public Element render(ISynchronizer synchronizer) {
		return null;
	}

}
