// Version: v1.0.0
// Author: Matthias Wegner

// Name of report
name = "RoleList Report Details"

// Description of report
description = "Detaillierte Liste aller Rollen die den Status und die Besetzung ausweist."

import org.jsoup.nodes.Element;
import org.rogatio.circlead.control.synchronizer.ISynchronizer;
import org.rogatio.circlead.control.synchronizer.file.FileSynchronizer;
import org.rogatio.circlead.model.Parameter;
import org.rogatio.circlead.model.WorkitemStatusParameter;
import org.rogatio.circlead.model.work.Role;
import org.rogatio.circlead.view.SvgBuilder;
import org.rogatio.circlead.view.renderer.ISynchronizerRendererEngine;

ISynchronizerRendererEngine renderer = synchronizer.getRenderer();
		Element element = new Element("p");

		Element table = element.appendElement("div").appendElement("table");
		Element tr = table.appendElement("tr");
		tr.appendElement("th").appendText("Name");
		if (synchronizer.getClass().getSimpleName().equals(FileSynchronizer.class.getSimpleName())) {
			tr.appendElement("th").attr("width", "68px").appendText("DNA");
		}
		tr.appendElement("th").appendText("Besetzung");
		tr.appendElement("th").appendText("Stabilität (Organisation)");
		tr.appendElement("th").appendText("Stabilität (Teams)");
		tr.appendElement("th").appendText(Parameter.STATUS.toString());

		for (Role role : R.getRoles()) {
			tr = table.appendElement("tr");
			renderer.addRoleItem(tr.appendElement("td"), null, role.getTitle());

			if (synchronizer.getClass().getSimpleName().equals(FileSynchronizer.class.getSimpleName())) {
				tr.appendElement("td").append(SvgBuilder.createRoleDnaProfile(role, 64).toString());
			}

			if (role.getRedundance() == 0.0) {
				if (!role.isSituational()) {
					renderer.addStatus(tr.appendElement("td"), WorkitemStatusParameter.UNASSIGNED.getName());
				} else {
					renderer.addStatus(tr.appendElement("td"), WorkitemStatusParameter.SITUATIONAL.getName());
				}
			} else if (role.getRedundance() < 2.0) {
				if (role.isSpecialized()) {
					renderer.addStatus(tr.appendElement("td"), WorkitemStatusParameter.ADEQUATE.getName());
				} else {
					renderer.addStatus(tr.appendElement("td"), WorkitemStatusParameter.ASSIGNED.getName());
				}			
			} else if (role.getRedundance() >= 2.0) {
				renderer.addStatus(tr.appendElement("td"), WorkitemStatusParameter.OVERASSIGNED.getName());
			}

			tr.appendElement("td").appendText("" + Math.round(role.getRedundanceOrganisation() * 100) + "%");
			tr.appendElement("td").appendText("" + Math.round(role.getRedundanceTeam() * 100) + "%");

			renderer.addStatus(tr.appendElement("td"), role.getStatus());

		}

		return element;
