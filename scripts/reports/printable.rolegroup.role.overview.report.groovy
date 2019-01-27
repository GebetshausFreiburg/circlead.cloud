// Version: v1.0.0
// Author: Matthias Wegner

// Name of report
name = "Overview Report"

// Description of report
description = "Druckvorlage aller Rollengruppen und Rollen (mit Seitenumbruch nach jedem Workitem)"

import java.util.List;
import org.rogatio.circlead.control.validator.ValidationMessage;
import org.jsoup.nodes.Element;
import org.rogatio.circlead.control.synchronizer.ISynchronizer;
import org.rogatio.circlead.control.synchronizer.atlassian.AtlassianSynchronizer;
import org.rogatio.circlead.control.synchronizer.file.FileSynchronizer;
import org.rogatio.circlead.model.work.Role;
import org.rogatio.circlead.model.work.Rolegroup;
import org.rogatio.circlead.util.ObjectUtil;
import org.rogatio.circlead.view.renderer.ISynchronizerRendererEngine;

	ISynchronizerRendererEngine renderer = synchronizer.getRenderer();
		Element element = new Element("p");

		if (synchronizer.getClass().getSimpleName().equals(AtlassianSynchronizer.class.getSimpleName())) {
			element.appendText("Not implementes for Atlassian Synchronizer");
		}

		if (synchronizer.getClass().getSimpleName().equals(FileSynchronizer.class.getSimpleName())) {
			element.append("<p style=\"page-break-before: always\">");

			if (R.getRolegroups().size() > 0) {
				List<Rolegroup> rolegroups = R.getRolegroups();
				for (Rolegroup rolegroup : rolegroups) {
					Element h1 = element.appendElement("h1");
					if (rolegroup.getParentIdentifier() != null) {
						List<Rolegroup> childRolegroups = R
								.getRolegroupChildren(rolegroup.getTitle());
						if (ObjectUtil.isListNotNullAndEmpty(childRolegroups)) {
							h1.append("<img src=\"images\\groupparent.png\" width=\"32px\"/>");
						} else {
							h1.append("<img src=\"images\\group.png\" width=\"32px\"/>");
						}
					} else {
						h1.append("<img src=\"images\\groupchild.png\" width=\"32px\"/>");
					}
					// renderer.addH1(element, rolegroup.getTitle());
					h1.appendText(rolegroup.getTitle());
					renderer.addStatus(element, rolegroup.getStatus());

					rolegroup.render(synchronizer).appendTo(element);
					element.append("<p style=\"page-break-before: always\">");

					List<Role> roles = R.getRoles(rolegroup.getTitle());
					if (roles.size() > 0) {
						for (Role role : roles) {
							Element h2 = element.appendElement("h2");
							h2.append("<img src=\"images\\role.png\" width=\"32px\"/>");
							h2.appendText(role.getTitle());
							renderer.addStatus(element, role.getStatus());
							role.render(synchronizer).appendTo(element);
							element.append("<p style=\"page-break-before: always\">");
						}
					}
				}
			}
		}

		return element;
