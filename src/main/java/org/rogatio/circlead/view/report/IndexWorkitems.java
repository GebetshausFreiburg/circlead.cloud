/*
 * Circlead - Develop and structure evolutionary Organisations
 * 
 * @author Matthias Wegner
 * @version 0.1
 * @since 01.07.2018
 * 
 */
package org.rogatio.circlead.view.report;

import java.util.List;

import org.jsoup.nodes.Element;
import org.rogatio.circlead.control.synchronizer.ISynchronizer;
import org.rogatio.circlead.model.WorkitemType;
import org.rogatio.circlead.model.data.HowTo;
import org.rogatio.circlead.model.work.Activity;
import org.rogatio.circlead.model.work.Person;
import org.rogatio.circlead.model.work.Role;
import org.rogatio.circlead.model.work.Rolegroup;
import org.rogatio.circlead.model.work.Team;
import org.rogatio.circlead.view.ISynchronizerRendererEngine;

public class IndexWorkitems extends DefaultReport {

	private WorkitemType type;

	public IndexWorkitems(WorkitemType type) {
		this.type = type;
		this.setName("Index " + type.getName());
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
		ISynchronizerRendererEngine renderer = synchronizer.getRenderer();
		Element element = new Element("p");

		element.append("<a href=\"Index Circlead.html\">[Index]</a>");
		element.append("<br/>");
		
		if (type == WorkitemType.ROLE) {
			List<Role> roles = R.getRoles();
			renderer.addRoleList(element, roles);
		} else if (type == WorkitemType.ROLEGROUP) {
			List<Rolegroup> wi = R.getRolegroups();
			renderer.addRolegroupList(element, wi);
		} else if (type == WorkitemType.PERSON) {
			List<Person> wi = R.getPersons();
			renderer.addPersonList(element, wi);
		} else if (type == WorkitemType.TEAM) {
			List<Team> wi = R.getTeams();
			renderer.addTeamList(element, wi);
		} else if (type == WorkitemType.REPORT) {
			List<IReport> wi = R.getReports();
			renderer.addReportList(element, wi);
		} else if (type == WorkitemType.HOWTO) {
			List<HowTo> wi = R.getIndexHowTos();
			renderer.addHowToList(element, wi);
		} else if (type == WorkitemType.ACTIVITY) {
			List<Activity> wi = R.getActivities();
			renderer.addActivityList(element, wi);
		}

		return element;
	}

}
