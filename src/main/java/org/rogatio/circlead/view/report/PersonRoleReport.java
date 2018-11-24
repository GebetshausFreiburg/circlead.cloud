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
import org.rogatio.circlead.control.synchronizer.atlassian.AtlassianSynchronizer;
import org.rogatio.circlead.control.synchronizer.file.FileSynchronizer;
import org.rogatio.circlead.model.work.Person;
import org.rogatio.circlead.model.work.Role;
import org.rogatio.circlead.util.ObjectUtil;
import org.rogatio.circlead.view.renderer.ISynchronizerRendererEngine;

/**
 * The Class PersonRoleReport.
 * 
 * @author Matthias Wegner
 */
public class PersonRoleReport extends DefaultReport {

	/** The role identifier. */
	private String roleIdentifier;
	
	/**
	 * Instantiates a new person role report.
	 *
	 * @param roleIdentifier the role identifier
	 */
	public PersonRoleReport(String roleIdentifier) {
		this.roleIdentifier = roleIdentifier;
		this.setName("Person Role "+roleIdentifier+" Report");
		this.setDescription("Druckvorlage Personen die mindestens die Rolle "+roleIdentifier+" tragen");
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

		if (synchronizer.getClass().getSimpleName().equals(AtlassianSynchronizer.class.getSimpleName())) {
			element.appendText("Not implementes for Atlassian Synchronizer");
		}

		if (synchronizer.getClass().getSimpleName().equals(FileSynchronizer.class.getSimpleName())) {
			element.append("<p style=\"page-break-before: always\">");

			Role roleSet = R.getRole(roleIdentifier);
			List<String> personIdentifiers = roleSet.getPersonIdentifiers();
			if (ObjectUtil.isListNotNullAndEmpty(personIdentifiers)) {
				for (int i = 0; i < personIdentifiers.size(); i++) {
					String personIdentifier = personIdentifiers.get(i);
					Person p = R.getPerson(personIdentifier);
					
					if (p!=null) {
						Element h1 = element.appendElement("h1");
						h1.appendText(p.getTitle());
						renderer.addStatus(element, p.getStatus());	
						p.render(synchronizer).appendTo(element);
						element.append("<p style=\"page-break-before: always\">");
					
						List<Role> or = R.getOrganisationalRolesWithPerson(p);
						if (or.size() > 0) {
							for (Role role : or) {
								Element h2 = element.appendElement("h2");
								h2.appendText(role.getTitle());
								renderer.addStatus(element, role.getStatus());
								role.render(synchronizer).appendTo(element);
								element.append("<p style=\"page-break-before: always\">");
							}
						}
						
						List<Role> tr = R.getTeamRolesWithPerson(p);
						if (tr.size() > 0) {
							for (Role role : tr) {
								Element h2 = element.appendElement("h2");
								h2.appendText(role.getTitle());
								renderer.addStatus(element, role.getStatus());
								role.render(synchronizer).appendTo(element);
								element.append("<p style=\"page-break-before: always\">");
							}
						}
						
					}
				}
			}
		}

		return element;
	}

}
