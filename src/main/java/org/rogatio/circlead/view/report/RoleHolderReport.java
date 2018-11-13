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
import org.rogatio.circlead.control.synchronizer.ISynchronizer;
import org.rogatio.circlead.control.synchronizer.atlassian.AtlassianSynchronizer;
import org.rogatio.circlead.control.synchronizer.file.FileSynchronizer;
import org.rogatio.circlead.model.WorkitemStatusParameter;
import org.rogatio.circlead.model.work.Person;
import org.rogatio.circlead.model.work.Role;
import org.rogatio.circlead.model.work.Team;
import org.rogatio.circlead.util.ObjectUtil;
import org.rogatio.circlead.view.ISynchronizerRendererEngine;

/**
 * The Class RoleHolderReport.
 * 
 * @author Matthias Wegner
 */
public class RoleHolderReport extends DefaultReport {

	/**
	 * Instantiates a new role holder report.
	 */
	public RoleHolderReport() {
		this.setName("RoleHolder Report"); 
		this.setDescription("Bericht über den Status der Rollenträger, wenn diese in der Organisation noch nicht ausgeprägt sind.");
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

		renderer.addH2(element, "Unbesetzte Rollen");
		renderer.addStatus(element, WorkitemStatusParameter.UNASSIGNED.toString());

		List<Role> foundUnrelatedRoles = new ArrayList<Role>();
		List<Role> roles = R.getRoles();
		for (Role role : roles) {
			boolean found = false;
			// Role-Holder in Organisation
			if (!ObjectUtil.isListNotNullAndEmpty(role.getPersonIdentifiers())) {
				found = true;
			}
			
			// Role-Holder in Teams
			List<Team> foundTeams = R.getTeamsWithRole(role);
			if (!ObjectUtil.isListNotNullAndEmpty(foundTeams)) {
				found = true;
			}
			
			if (!found) {
				foundUnrelatedRoles.add(role);	
			}
		}
		renderer.addRoleList(element, foundUnrelatedRoles);
		
		addRoles("Unklare Rollen", WorkitemStatusParameter.CRITICAL, synchronizer, element);
		addRoles("Entstehende Rollen", WorkitemStatusParameter.DRAFT, synchronizer, element);
		addRoles("Zeitweise Rollen", WorkitemStatusParameter.TEMPORARY, synchronizer, element);
		addRoles("Überarbeitete Rollen", WorkitemStatusParameter.INPROGRESS, synchronizer, element);
		
		addRoles("Aktive Rollen - Untrainiert", WorkitemStatusParameter.ACTIVE, WorkitemStatusParameter.UNSKILLED, "0", synchronizer, element);
		addRoles("Aktive Rollen - Anfänger", WorkitemStatusParameter.ACTIVE, WorkitemStatusParameter.STARTER, "25", synchronizer, element);
		addRoles("Aktive Rollen - Beginner", WorkitemStatusParameter.ACTIVE, WorkitemStatusParameter.BEGINNER, "50", synchronizer, element);
		addRoles("Aktive Rollen - Experte", WorkitemStatusParameter.ACTIVE, WorkitemStatusParameter.EXPERT, "75", synchronizer, element);

		return element;
	}
	
	/**
	 * Adds the roles.
	 *
	 * @param title the title
	 * @param wis the wis
	 * @param wiStatus the wi status
	 * @param skillLevel the skill level
	 * @param synchronizer the synchronizer
	 * @param element the element
	 */
	private void addRoles(String title, WorkitemStatusParameter wis, WorkitemStatusParameter wiStatus, String skillLevel,  ISynchronizer synchronizer, Element element) {
		ISynchronizerRendererEngine renderer = synchronizer.getRenderer();
		renderer.addH2(element, title);
		renderer.addStatus(element, wis.toString());
		renderer.addStatus(element, wiStatus.toString());
		Element ul = element.appendElement("ul");
		List<Role> roles = R.getRoles();
		for (Role role : roles) {
			if (ObjectUtil.isListNotNullAndEmpty(role.getPersonIdentifiers())) {
				for (String personIdentifier : role.getPersonIdentifiers()) {
					Person person = R.getPerson(personIdentifier);
					if (person != null) {
						if (role.getDataitem().hasRepresentation(personIdentifier)) {
							String representation = role.getDataitem().getRepresentation(personIdentifier);
							WorkitemStatusParameter status = WorkitemStatusParameter.get(representation);
							if (status != null) {
								if (status == wis) {
									if (role.getDataitem().hasSkill(personIdentifier)) {
										String skill = role.getDataitem().getSkill(personIdentifier);
										if (skill != null) {
											if (skill.equals(skillLevel)) {
												addLink(synchronizer, ul, role, person);
											}
										}
									}

								}
							}
						}
					}
				}
			}
		}
	}
	
	/**
	 * Adds the roles.
	 *
	 * @param title the title
	 * @param wiStatus the wi status
	 * @param synchronizer the synchronizer
	 * @param element the element
	 */
	private void addRoles(String title, WorkitemStatusParameter wiStatus, ISynchronizer synchronizer, Element element) {
		ISynchronizerRendererEngine renderer = synchronizer.getRenderer();
		renderer.addH2(element, title);
		renderer.addStatus(element, wiStatus.toString());
		Element ul = element.appendElement("ul");
		List<Role> roles = R.getRoles();
		for (Role role : roles) {
			if (ObjectUtil.isListNotNullAndEmpty(role.getPersonIdentifiers())) {
				for (String personIdentifier : role.getPersonIdentifiers()) {
					Person person = R.getPerson(personIdentifier);
					if (person != null) {
						if (role.getDataitem().hasRepresentation(personIdentifier)) {
							String representation = role.getDataitem().getRepresentation(personIdentifier);
							WorkitemStatusParameter status = WorkitemStatusParameter.get(representation);
							if (status != null) {
								if (status == wiStatus) {
									addLink(synchronizer, ul, role, person);
								}
							}
						}
					}
				}
			}
		}
	}

	/**
	 * Adds the link.
	 *
	 * @param synchronizer the synchronizer
	 * @param ul the ul
	 * @param role the role
	 * @param person the person
	 */
	private void addLink(ISynchronizer synchronizer, Element ul, Role role, Person person) {
		if (synchronizer.getClass().getSimpleName()
				.equals(FileSynchronizer.class.getSimpleName())) {
			Element li = ul.appendElement("li");
			li.appendElement("a").attr("href", "../web/" + role.getId(synchronizer) + ".html")
			.appendText(role.getTitle());
			li.append("&nbsp;-&nbsp;");
			li.appendElement("a").attr("href", "../web/" + person.getId(synchronizer) + ".html")
			.appendText(person.getTitle());
		}

		if (synchronizer.getClass().getSimpleName()
				.equals(AtlassianSynchronizer.class.getSimpleName())) {
			Element li = ul.appendElement("li");
			li.appendElement("ac:link")
					.append("<ri:page ri:content-title=\"" + role.getTitle()
							+ "\" ri:version-at-save=\"1\" />");
			li.append("&nbsp;-&nbsp;");
			li.appendElement("ac:link")
			.append("<ri:page ri:content-title=\"" + person.getTitle()
					+ "\" ri:version-at-save=\"1\" />");
		}
	}
	
}
