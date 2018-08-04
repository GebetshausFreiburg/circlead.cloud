/*
 * Circlead - Develop and structure evolutionary Organisations
 * 
 * @author Matthias Wegner
 * @version 0.1
 * @since 01.07.2018
 * 
 */
package org.rogatio.circlead.view;

import org.jsoup.nodes.Element;
import org.rogatio.circlead.control.synchronizer.ISynchronizer;

/**
 * The Class DefaultReport.
 */
public class DefaultReport implements IReport {

	/** The name. */
	private String name = null;

	/* (non-Javadoc)
	 * @see org.rogatio.circlead.view.IReport#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	protected void setName(String name) {
		this.name = name;
	}

	/* (non-Javadoc)
	 * @see org.rogatio.circlead.view.IWorkitemRenderer#render(org.rogatio.circlead.control.synchronizer.ISynchronizer)
	 */
	@Override
	public Element render(ISynchronizer synchronizer) {
		return null;
	}

}
