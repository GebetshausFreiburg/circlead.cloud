// Version: v1.0.0
// Author: Matthias Wegner

// Load application default role identifier
def roleIdentifier = PropertyUtil.getInstance().getApplicationDefaultRoleReport()

// Name of report
name = "Person Role "+roleIdentifier+" Report"

// Name of report
description = "Druckvorlage Personen die mindestens die Rolle "+roleIdentifier+" tragen"

import java.util.List;
import org.jsoup.nodes.Element;
import org.rogatio.circlead.control.synchronizer.ISynchronizer;
import org.rogatio.circlead.control.synchronizer.atlassian.AtlassianSynchronizer;
import org.rogatio.circlead.control.synchronizer.file.FileSynchronizer;
import org.rogatio.circlead.model.work.Person;
import org.rogatio.circlead.model.work.Role;
import org.rogatio.circlead.util.ObjectUtil;
import org.rogatio.circlead.view.renderer.ISynchronizerRendererEngine;

ISynchronizerRendererEngine renderer = synchronizer.getRenderer();
		Element element = new Element("p");

		if (synchronizer.getClass().getSimpleName().equals(AtlassianSynchronizer.class.getSimpleName())) {
			element.appendText("Not implementes for Atlassian Synchronizer");
		}

		if (synchronizer.getClass().getSimpleName().equals(FileSynchronizer.class.getSimpleName())) {
			element.append("<p style=\"page-break-before: always\">");

			Role roleSet = R.getRole(roleIdentifier);
                        if (roleSet!=null) {
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
		}

		return element;
