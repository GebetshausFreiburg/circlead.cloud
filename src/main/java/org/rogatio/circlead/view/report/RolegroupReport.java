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
import org.rogatio.circlead.control.synchronizer.atlassian.parser.Parser;
import org.rogatio.circlead.model.work.Role;
import org.rogatio.circlead.model.work.Rolegroup;
import org.rogatio.circlead.util.ObjectUtil;
import org.rogatio.circlead.view.ISynchronizerRendererEngine;

/**
 * The Class RolegroupReport shows for every rolegroup the included data with
 * all coresponding roles. Pagebreak is included after every item.
 */
public class RolegroupReport extends DefaultReport {

	/** The rolegroup. */
	private Rolegroup rolegroup;

	/**
	 * Creates the reports.
	 *
	 * @return the list
	 */
	public static List<IReport> createReports() {
		List<IReport> reports = new ArrayList<IReport>();
		Repository repository = Repository.getInstance();
		if (repository.getRolegroups().size() > 0) {
			List<Rolegroup> rolegroups = repository.getRolegroups();
			for (Rolegroup rolegroup : rolegroups) {
				reports.add(new RolegroupReport(rolegroup));
			}
		}
		return reports;
	}

	public RolegroupReport(String rolegroupIdentifier) {
		Rolegroup rolegroup = Repository.getInstance().getRolegroup(rolegroupIdentifier);
		if (rolegroup != null) { 
			this.setName("Report '" + rolegroup.getTitle() + "'");
			this.rolegroup = rolegroup;
			this.setDescription("(Druckfähige) Zusammenfassung der Rollengruppe '"+rolegroupIdentifier+"' mit allen enthaltenen Rollen"); 
		}
	}

	/**
	 * Instantiates a new rolegroup report.
	 *
	 * @param rolegroup the rolegroup
	 */
	public RolegroupReport(Rolegroup rolegroup) {
		this.setName("Report '" + rolegroup.getTitle() + "'");
		this.rolegroup = rolegroup;
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

		renderer.addH2(element, rolegroup.getTitle());
		renderer.addStatus(element, rolegroup.getStatus());

		rolegroup.render(synchronizer).appendTo(element);

		renderer.addH3(element, "Rollentragende Personen in Rollengruppe");
		List<String> persons = new ArrayList<String>();
		List<Role> roles = R.getRoles(rolegroup.getTitle());
		if (roles.size() > 0) {
			for (Role role : roles) {
				List<String> pIds = role.getPersonIdentifiers();
				for (String p : pIds) {
					if (!persons.contains(p)) {
						persons.add(p);
					}
				}
			}
		}
		renderer.addList(element, persons);

		renderer.addH3(element, "Vorgänge zur Rollengruppe");
		addJiraMacro(element, rolegroup);

		element.append("<p style=\"page-break-before: always\">");

		roles = R.getRoles(rolegroup.getTitle());
		if (roles.size() > 0) {
			for (Role role : roles) {
				renderer.addH2(element, role.getTitle());
				renderer.addStatus(element, role.getStatus());

				role.render(synchronizer).appendTo(element);

				renderer.addH3(element, "Vorgänge zur Rolle");
				addJiraMacro(element, role);

				element.append("<p style=\"page-break-before: always\">");
			}
		}

		return element;
	}

	/**
	 * Adds the jira macro.
	 *
	 * @param element   the element
	 * @param rolegroup the rolegroup
	 */
	private void addJiraMacro(Element element, Rolegroup rolegroup) {
		String columns = "key,summary,type,created,updated,due,assignee,reporter,priority,status,resolution";
		String serverId = "aa483c16-161d-3599-b439-850eba0fbf58";

		StringBuilder jqlQueryString = new StringBuilder();
		jqlQueryString.append("labels in (");
		jqlQueryString.append("Rollengruppe:" + rolegroup.getTitle().replace(" ", "") + ", ");
		jqlQueryString.append("Rolegroup:" + rolegroup.getTitle().replace(" ", ""));

		if (ObjectUtil.isListNotNullAndEmpty(rolegroup.getSynonyms())) {

			List<String> rgList = new ArrayList<String>();
			for (String s : rolegroup.getSynonyms()) {
				rgList.add(s.replace(" ", ""));
			}

			jqlQueryString.append(", ");
			jqlQueryString.append("Rollengruppe:" + String.join(", Rollengruppe:", rgList));
			jqlQueryString.append(", ");
			jqlQueryString.append("Rolegroup:" + String.join(", Rolegroup", rgList));
		}

		jqlQueryString.append(") order by created DESC");

		Element jiraMacro = Parser.getJiraMacro(columns, 100, jqlQueryString.toString(), serverId);
		jiraMacro.appendTo(element);
	}

	/**
	 * Adds the jira macro.
	 *
	 * @param element the element
	 * @param role    the role
	 */
	private void addJiraMacro(Element element, Role role) {
		String columns = "key,summary,type,created,updated,due,assignee,reporter,priority,status,resolution";
		String serverId = "aa483c16-161d-3599-b439-850eba0fbf58";

		StringBuilder jqlQueryString = new StringBuilder();
		jqlQueryString.append("labels in (");
		jqlQueryString.append("Rolle:" + role.getTitle().replace(" ", "") + ", ");
		jqlQueryString.append("Role:" + role.getTitle().replace(" ", ""));

		if (ObjectUtil.isListNotNullAndEmpty(role.getSynonyms())) {

			List<String> rgList = new ArrayList<String>();
			for (String s : role.getSynonyms()) {
				rgList.add(s.replace(" ", ""));
			}

			jqlQueryString.append(", ");
			jqlQueryString.append("Role:" + String.join(", Role:", rgList));
			jqlQueryString.append(", ");
			jqlQueryString.append("Rolle:" + String.join(", Rolle:", rgList));
		}

		jqlQueryString.append(") order by created DESC");

		Element jiraMacro = Parser.getJiraMacro(columns, 100, jqlQueryString.toString(), serverId);
		jiraMacro.appendTo(element);
	}

}
