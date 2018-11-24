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
import org.rogatio.circlead.control.synchronizer.file.FileSynchronizer;
import org.rogatio.circlead.model.Parameter;
import org.rogatio.circlead.model.WorkitemStatusParameter;
import org.rogatio.circlead.model.work.Role;
import org.rogatio.circlead.model.work.Team;
import org.rogatio.circlead.util.PropertyUtil;
import org.rogatio.circlead.view.SvgBuilder;
import org.rogatio.circlead.view.renderer.ISynchronizerRendererEngine;

/**
 * The Class RoleNeed.
 * 
 * @author Matthias Wegner
 */
public class RoleNeedReport extends DefaultReport {

	/**
	 * Instantiates a new role need report.
	 */
	public RoleNeedReport() {
		this.setName("Role Need Report");
		this.setDescription("Liste aller Bedarfe bzgl. Rollen.");
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

		Element table = element.appendElement("div").appendElement("table");
		Element tr = table.appendElement("tr");
		tr.appendElement("th").appendText("Name");
		tr.appendElement("th").appendText("Typ");
		if (synchronizer.getClass().getSimpleName().equals(FileSynchronizer.class.getSimpleName())) {
			tr.appendElement("th").attr("width", "68px").appendText("DNA");
		}
		tr.appendElement("th").appendText(Parameter.STATUS.toString());
		tr.appendElement("th").appendText("Info");

		List<Role> setRoles = new ArrayList<Role>();

		List<Role> roles = R.getRolesWithPersonRepresentation(WorkitemStatusParameter.TEMPORARY);
		setRoles.addAll(roles);
		for (Role role : roles) {
			tr = table.appendElement("tr");
			renderer.addRoleItem(tr.appendElement("td"), null, role.getTitle());

			tr.appendElement("td").appendText("Organisationsrolle");

			if (synchronizer.getClass().getSimpleName().equals(FileSynchronizer.class.getSimpleName())) {
				tr.appendElement("td").append(SvgBuilder.createRoleDnaProfile(role, 64).toString());
			}

			renderer.addStatus(tr.appendElement("td"), WorkitemStatusParameter.TEMPORARY.getName());
			tr.appendElement("td").appendText(
					"Rolle ist komissarisch besetzt. Es werden Personen gesucht die die Rolle mit übernehmen oder ganz übernehmen.");

		}

		List<Role> rolesX = R.getRoles();
		for (Role role : rolesX) {

//			System.out.println(role.getSkillLevel() +" "+ role.getTitle());

			if (role.getPersonIdentifiers().size() == 1 && !roles.contains(role)) {
				setRoles.add(role);
				tr = table.appendElement("tr");
				renderer.addRoleItem(tr.appendElement("td"), null, role.getTitle());

				tr.appendElement("td").appendText("Organisationsrolle");

				if (synchronizer.getClass().getSimpleName().equals(FileSynchronizer.class.getSimpleName())) {
					tr.appendElement("td").append(SvgBuilder.createRoleDnaProfile(role, 64).toString());
				}

				renderer.addStatus(tr.appendElement("td"), "Unterbesetzt");
				tr.appendElement("td").appendText(
						"Rolle ist nur durch eine Person besetzt. Verstärkung gesucht um die Rolle zu stärken.");

			}
		}

		for (Role role : rolesX) {
			// System.out.println(role.getOrganisationalSkillLevel()+"
			// "+role.getRedundance() +" "+ role.getTitle());
			if (role.getRedundance() < 2 && !setRoles.contains(role) && !role.isSituational()) {
				tr = table.appendElement("tr");
				renderer.addRoleItem(tr.appendElement("td"), null, role.getTitle());

				tr.appendElement("td").appendText("Organisationsrolle");

				if (synchronizer.getClass().getSimpleName().equals(FileSynchronizer.class.getSimpleName())) {
					tr.appendElement("td").append(SvgBuilder.createRoleDnaProfile(role, 64).toString());
				}

				renderer.addStatus(tr.appendElement("td"), "Unterbesetzt");
				tr.appendElement("td").appendText(
						"Rolle ist noch nicht genügend abgedeckt. Verstärkung gesucht um die Rolle zu stärken.");

			}
		}

		List<Team> teams = R.getTeamsWithLowRedundance();
		for (Team team : teams) {
			tr = table.appendElement("tr");
			renderer.addTeamItem(tr.appendElement("td"), null, team.getTitle());

			tr.appendElement("td").appendText("Teamrolle");

			if (synchronizer.getClass().getSimpleName().equals(FileSynchronizer.class.getSimpleName())) {
				tr.appendElement("td").append(SvgBuilder.createTeamDnaProfile(team, 64).toString());
			}

			renderer.addStatus(tr.appendElement("td"), "Unterbesetzt");
			tr.appendElement("td").appendText("Team ist unterbesetzt. Verstärkung gesucht um Team zu stärken.");

		}

		teams = R.getTeamsWithSize(1);
		for (Team team : teams) {
			if (!team.isSpecialized() && team.getCategory()
					.equalsIgnoreCase(PropertyUtil.getInstance().getApplicationDefaultTeamcategory())) {
				tr = table.appendElement("tr");
				renderer.addTeamItem(tr.appendElement("td"), null, team.getTitle());

				tr.appendElement("td").appendText("Teamrolle");

				if (synchronizer.getClass().getSimpleName().equals(FileSynchronizer.class.getSimpleName())) {
					tr.appendElement("td").append(SvgBuilder.createTeamDnaProfile(team, 64).toString());
				}

				renderer.addStatus(tr.appendElement("td"), "Einfachbesetzung");
				tr.appendElement("td")
						.appendText("Team ist nur einfach besetzt. Verstärkung gesucht um Team zu stärken.");

			}
		}

		List<String> openTeams = R.getTeamsNotinweek(PropertyUtil.getInstance().getApplicationDefaultTeamcategory());
		for (String openTeam : openTeams) {
			tr = table.appendElement("tr");
			renderer.addTeamItem(tr.appendElement("td"), null, " Team " + openTeam);

			tr.appendElement("td").appendText("Teamrolle");

			if (synchronizer.getClass().getSimpleName().equals(FileSynchronizer.class.getSimpleName())) {
				tr.appendElement("td").appendText("-");
			}

			renderer.addStatus(tr.appendElement("td"), "Unbesetzt");
			tr.appendElement("td").appendText("Team ist unbesetzt. Pionier gesucht.");

		}

		return element;
	}

}
