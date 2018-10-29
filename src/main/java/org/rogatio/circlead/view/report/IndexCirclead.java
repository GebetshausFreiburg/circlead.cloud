/*
 * Circlead - Develop and structure evolutionary Organisations
 * 
 * @author Matthias Wegner
 * @version 0.1
 * @since 01.07.2018
 * 
 */
package org.rogatio.circlead.view.report;

import org.jsoup.nodes.Element;
import org.rogatio.circlead.control.synchronizer.ISynchronizer;
import org.rogatio.circlead.control.synchronizer.atlassian.AtlassianSynchronizer;
import org.rogatio.circlead.control.synchronizer.file.FileSynchronizer;
import org.rogatio.circlead.model.Parameter;
import org.rogatio.circlead.model.WorkitemType;
import org.rogatio.circlead.view.ISynchronizerRendererEngine;

public class IndexCirclead extends DefaultReport {

	public IndexCirclead() {
		this.setName("Index Circlead");
		this.setDescription("Übersicht Circlead-Inhalte");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.rogatio.circlead.view.DefaultReport#render(org.rogatio.circlead.control.
	 * synchronizer.ISynchronizer)
	 */
	@Override
	public Element render(ISynchronizer synchronizer) {
		@SuppressWarnings("unused")
		ISynchronizerRendererEngine renderer = synchronizer.getRenderer();
		Element element = new Element("p");

		if (synchronizer.getClass().getSimpleName().equals(AtlassianSynchronizer.class.getSimpleName())) {
			element.appendText("Not implementes for Atlassian Synchronizer");
		}

		if (synchronizer.getClass().getSimpleName().equals(FileSynchronizer.class.getSimpleName())) {
			Element ul = element.appendElement("ul");
			ul.appendElement("li").append("<a href=\"Index " + WorkitemType.ROLE.getName() + ".html\">" + Parameter.ROLES + "</a>");
			ul.appendElement("li").append(
					"<a href=\"Index " + WorkitemType.ROLEGROUP.getName() + ".html\">" + Parameter.ROLEGROUPS + "</a>");
			ul.appendElement("li").append("<a href=\"Index " + WorkitemType.PERSON.getName() + ".html\">" + Parameter.PERSONS + "</a>");
			ul.appendElement("li").append(
					"<a href=\"Index " + WorkitemType.ACTIVITY.getName() + ".html\">" + Parameter.ACTIVITIES + "</a>");
			ul.appendElement("li").append("<a href=\"Index " + WorkitemType.TEAM.getName() + ".html\">" + Parameter.TEAMS + "</a>");
			ul.appendElement("li").append("<a href=\"Index " + WorkitemType.REPORT.getName() + ".html\">" + Parameter.REPORTS + "</a>");
			ul.appendElement("li").append("<a href=\"Index " + WorkitemType.HOWTO.getName() + ".html\">" + Parameter.HOWTOS + "</a>");
		}

		return element;
	}

}
