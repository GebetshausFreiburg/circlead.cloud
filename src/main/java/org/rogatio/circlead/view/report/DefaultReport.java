/*
 * Circlead - Develop and structure evolutionary Organisations
 * 
 * @author Matthias Wegner
 * @version 0.1
 * @since 01.07.2018
 * 
 */
package org.rogatio.circlead.view.report;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Element;
import org.rogatio.circlead.control.Repository;
import org.rogatio.circlead.control.synchronizer.ISynchronizer;

/**
 * The Class DefaultReport.
 */
public class DefaultReport implements IReport {

	protected final Repository R = Repository.getInstance();

	/** The name. */
	private String name = null;

	/** The description. */
	private String description = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.rogatio.circlead.view.IReport#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * Sets the description.
	 *
	 * @param description the new description
	 */
	protected void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	protected void setName(String name) {
		this.name = name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.rogatio.circlead.view.IWorkitemRenderer#render(org.rogatio.circlead.
	 * control.synchronizer.ISynchronizer)
	 */
	@Override
	public Element render(ISynchronizer synchronizer) {
		return null;
	}

	@Override
	public List<String> getHead() {
		List<String> head = new ArrayList<String>();
		head.add("<link rel=\"stylesheet\" href=\"styles.css\">");
		return head;
	}

	@Override
	public String getDescription() {
		return description;
	}

}
