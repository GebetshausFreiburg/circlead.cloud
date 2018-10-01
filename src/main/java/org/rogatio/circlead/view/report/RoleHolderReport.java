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
import org.rogatio.circlead.control.synchronizer.atlassian.AtlassianSynchronizer;
import org.rogatio.circlead.control.synchronizer.atlassian.parser.Parser;
import org.rogatio.circlead.control.synchronizer.file.FileSynchronizer;
import org.rogatio.circlead.model.Parameter;
import org.rogatio.circlead.model.WorkitemStatusParameter;
import org.rogatio.circlead.model.work.Person;
import org.rogatio.circlead.model.work.Role;
import org.rogatio.circlead.model.work.Rolegroup;
import org.rogatio.circlead.util.ObjectUtil;
import org.rogatio.circlead.view.ISynchronizerRendererEngine;

public class RoleHolderReport extends DefaultReport {

	public RoleHolderReport() {
		this.setName("RoleHolder Report");
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

		renderer.addH2(element, "Verwaiste Rollen");
		renderer.addStatus(element, WorkitemStatusParameter.UNASSIGNED.toString());
		
		List<Role> foundUnrelatedRoles = new ArrayList<Role>();
		List<Role> roles = Repository.getInstance().getRoles();
		for (Role role : roles) {
			if (!ObjectUtil.isListNotNullAndEmpty(role.getPersonIdentifiers())) {
				foundUnrelatedRoles.add(role);
			}
		}
		Element ul = element.appendElement("ul");
		for (Role role : foundUnrelatedRoles) {
			ul.appendElement("li").appendText(role.getTitle());
		}
		
		renderer.addH2(element, "Unklare Rollen");
		renderer.addStatus(element, WorkitemStatusParameter.CRITICAL.toString());
		ul = element.appendElement("ul");
		roles = Repository.getInstance().getRoles();
		for (Role role : roles) {
			if (ObjectUtil.isListNotNullAndEmpty(role.getPersonIdentifiers())) {
				for (String personIdentifier : role.getPersonIdentifiers()) {
					Person person = Repository.getInstance().getPerson(personIdentifier);
					if (person != null) {
						if (role.getDataitem().hasRepresentation(personIdentifier)) {
							String representation = role.getDataitem().getRepresentation(personIdentifier);
							WorkitemStatusParameter status = WorkitemStatusParameter.get(representation);
							if (status != null) {
								if (status==WorkitemStatusParameter.CRITICAL) {
									ul.appendElement("li").appendText(role.getTitle()+" - "+person.getFullname());
								}
							}
						}
					}
				}
			}
		}
		
		renderer.addH2(element, "Entstehende Rollen");
		renderer.addStatus(element, WorkitemStatusParameter.DRAFT.toString());
		ul = element.appendElement("ul");
		roles = Repository.getInstance().getRoles();
		for (Role role : roles) {
			if (ObjectUtil.isListNotNullAndEmpty(role.getPersonIdentifiers())) {
				for (String personIdentifier : role.getPersonIdentifiers()) {
					Person person = Repository.getInstance().getPerson(personIdentifier);
					if (person != null) {
						if (role.getDataitem().hasRepresentation(personIdentifier)) {
							String representation = role.getDataitem().getRepresentation(personIdentifier);
							WorkitemStatusParameter status = WorkitemStatusParameter.get(representation);
							if (status != null) {
								if (status==WorkitemStatusParameter.DRAFT) {
									ul.appendElement("li").appendText(role.getTitle()+" - "+person.getFullname());
								}
							}
						}
					}
				}
			}
		}
		
		renderer.addH2(element, "Zeitweise Rollen");
		renderer.addStatus(element, WorkitemStatusParameter.TEMPORARY.toString());
		ul = element.appendElement("ul");
		roles = Repository.getInstance().getRoles();
		for (Role role : roles) {
			if (ObjectUtil.isListNotNullAndEmpty(role.getPersonIdentifiers())) {
				for (String personIdentifier : role.getPersonIdentifiers()) {
					Person person = Repository.getInstance().getPerson(personIdentifier);
					if (person != null) {
						if (role.getDataitem().hasRepresentation(personIdentifier)) {
							String representation = role.getDataitem().getRepresentation(personIdentifier);
							WorkitemStatusParameter status = WorkitemStatusParameter.get(representation);
							if (status != null) {
								if (status==WorkitemStatusParameter.TEMPORARY) {
									ul.appendElement("li").appendText(role.getTitle()+" - "+person.getFullname());
								}
							}
						}
					}
				}
			}
		}
		
		renderer.addH2(element, "Überarbeitete Rollen");
		renderer.addStatus(element, WorkitemStatusParameter.INPROGRESS.toString());
		ul = element.appendElement("ul");
		roles = Repository.getInstance().getRoles();
		for (Role role : roles) {
			if (ObjectUtil.isListNotNullAndEmpty(role.getPersonIdentifiers())) {
				for (String personIdentifier : role.getPersonIdentifiers()) {
					Person person = Repository.getInstance().getPerson(personIdentifier);
					if (person != null) {
						if (role.getDataitem().hasRepresentation(personIdentifier)) {
							String representation = role.getDataitem().getRepresentation(personIdentifier);
							WorkitemStatusParameter status = WorkitemStatusParameter.get(representation);
							if (status != null) {
								if (status==WorkitemStatusParameter.INPROGRESS) {
									ul.appendElement("li").appendText(role.getTitle()+" - "+person.getFullname());
								}
							}
						}
					}
				}
			}
		}
		
		renderer.addH2(element, "Aktive Rollen - Untrainiert");
		renderer.addStatus(element, WorkitemStatusParameter.ACTIVE.toString());
		renderer.addStatus(element, WorkitemStatusParameter.UNSKILLED.toString());
		ul = element.appendElement("ul");
		roles = Repository.getInstance().getRoles();
		for (Role role : roles) {
			if (ObjectUtil.isListNotNullAndEmpty(role.getPersonIdentifiers())) {
				for (String personIdentifier : role.getPersonIdentifiers()) {
					Person person = Repository.getInstance().getPerson(personIdentifier);
					if (person != null) {
						if (role.getDataitem().hasRepresentation(personIdentifier)) {
							String representation = role.getDataitem().getRepresentation(personIdentifier);
							WorkitemStatusParameter status = WorkitemStatusParameter.get(representation);
							if (status != null) {
								if (status==WorkitemStatusParameter.ACTIVE) {
									if (role.getDataitem().hasSkill(personIdentifier)) {
										String skill = role.getDataitem().getSkill(personIdentifier);
										if (skill!=null) {
											if (skill.equals("0")) {
												ul.appendElement("li").appendText(role.getTitle()+" - "+person.getFullname());										
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
		
		renderer.addH2(element, "Aktive Rollen - Anfänger");
		renderer.addStatus(element, WorkitemStatusParameter.ACTIVE.toString());
		renderer.addStatus(element, WorkitemStatusParameter.STARTER.toString());
		ul = element.appendElement("ul");
		roles = Repository.getInstance().getRoles();
		for (Role role : roles) {
			if (ObjectUtil.isListNotNullAndEmpty(role.getPersonIdentifiers())) {
				for (String personIdentifier : role.getPersonIdentifiers()) {
					Person person = Repository.getInstance().getPerson(personIdentifier);
					if (person != null) {
						if (role.getDataitem().hasRepresentation(personIdentifier)) {
							String representation = role.getDataitem().getRepresentation(personIdentifier);
							WorkitemStatusParameter status = WorkitemStatusParameter.get(representation);
							if (status != null) {
								if (status==WorkitemStatusParameter.ACTIVE) {
									if (role.getDataitem().hasSkill(personIdentifier)) {
										String skill = role.getDataitem().getSkill(personIdentifier);
										if (skill!=null) {
											if (skill.equals("25")) {
												ul.appendElement("li").appendText(role.getTitle()+" - "+person.getFullname());										
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
		
		renderer.addH2(element, "Aktive Rollen - Beginner");
		renderer.addStatus(element, WorkitemStatusParameter.ACTIVE.toString());
		renderer.addStatus(element, WorkitemStatusParameter.BEGINNER.toString());
		ul = element.appendElement("ul");
		roles = Repository.getInstance().getRoles();
		for (Role role : roles) {
			if (ObjectUtil.isListNotNullAndEmpty(role.getPersonIdentifiers())) {
				for (String personIdentifier : role.getPersonIdentifiers()) {
					Person person = Repository.getInstance().getPerson(personIdentifier);
					if (person != null) {
						if (role.getDataitem().hasRepresentation(personIdentifier)) {
							String representation = role.getDataitem().getRepresentation(personIdentifier);
							WorkitemStatusParameter status = WorkitemStatusParameter.get(representation);
							if (status != null) {
								if (status==WorkitemStatusParameter.ACTIVE) {
									if (role.getDataitem().hasSkill(personIdentifier)) {
										String skill = role.getDataitem().getSkill(personIdentifier);
										if (skill!=null) {
											if (skill.equals("50")) {
												ul.appendElement("li").appendText(role.getTitle()+" - "+person.getFullname());										
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
		
		renderer.addH2(element, "Aktive Rollen - Experte");
		renderer.addStatus(element, WorkitemStatusParameter.ACTIVE.toString());
		renderer.addStatus(element, WorkitemStatusParameter.EXPERT.toString());
		ul = element.appendElement("ul");
		roles = Repository.getInstance().getRoles();
		for (Role role : roles) {
			if (ObjectUtil.isListNotNullAndEmpty(role.getPersonIdentifiers())) {
				for (String personIdentifier : role.getPersonIdentifiers()) {
					Person person = Repository.getInstance().getPerson(personIdentifier);
					if (person != null) {
						if (role.getDataitem().hasRepresentation(personIdentifier)) {
							String representation = role.getDataitem().getRepresentation(personIdentifier);
							WorkitemStatusParameter status = WorkitemStatusParameter.get(representation);
							if (status != null) {
								if (status==WorkitemStatusParameter.ACTIVE) {
									if (role.getDataitem().hasSkill(personIdentifier)) {
										String skill = role.getDataitem().getSkill(personIdentifier);
										if (skill!=null) {
											if (skill.equals("75")) {
												ul.appendElement("li").appendText(role.getTitle()+" - "+person.getFullname());										
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
		
		return element;
	}

}
