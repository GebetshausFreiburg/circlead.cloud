/*
 * Circlead - Develop and structure evolutionary Organisations
 * 
 * @author Matthias Wegner
 * @version 0.1
 * @since 01.07.2018
 * 
 */
package org.rogatio.circlead.view;

import java.util.List;

import org.jsoup.nodes.Element;
import org.rogatio.circlead.control.Repository;
import org.rogatio.circlead.control.synchronizer.ISynchronizer;
import org.rogatio.circlead.model.work.Role;
import org.rogatio.circlead.model.work.Rolegroup;
import org.rogatio.circlead.util.ObjectUtil;

/**
 * The Class OverviewReport.
 */
public class OverviewReport extends DefaultReport {

	/**
	 * Instantiates a new overview report.
	 */
	public OverviewReport() {
		this.setName("Overview Report");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.rogatio.circlead.view.DefaultReport#render(org.rogatio.circlead.control.synchronizer.ISynchronizer)
	 */
	@Override
	public Element render(ISynchronizer synchronizer) {
		ISynchronizerRenderer renderer = synchronizer.getRenderer();
		Element element = new Element("p");

		element.append("<p style=\"page-break-before: always\">");

		if (Repository.getInstance().getRolegroups().size() > 0) {
			List<Rolegroup> rolegroups = Repository.getInstance().getRolegroups();
			for (Rolegroup rolegroup : rolegroups) {
				Element h1 = element.appendElement("h1");
				if (rolegroup.getParentIdentifier() != null) {
					List<Rolegroup> childRolegroups = Repository.getInstance().getRolegroupChildren(rolegroup.getTitle());
					if (ObjectUtil.isListNotNullAndEmpty(childRolegroups)) {
						h1.append("<img src=\"images\\groupparent.png\">");
					} else {
						h1.append("<img src=\"images\\group.png\">");
					}
				} else {
					h1.append("<img src=\"images\\groupchild.png\">");
				}
				// renderer.addH1(element, rolegroup.getTitle());
				h1.appendText(rolegroup.getTitle());
				renderer.addStatus(element, rolegroup.getStatus());

				rolegroup.render(synchronizer).appendTo(element);
				element.append("<p style=\"page-break-before: always\">");

				List<Role> roles = Repository.getInstance().getRoles(rolegroup.getTitle());
				if (roles.size() > 0) {
					for (Role role : roles) {
						Element h2 = element.appendElement("h2");
						h2.append("<img src=\"images\\role.png\">");
						h2.appendText(role.getTitle());
						renderer.addStatus(element, role.getStatus());
						role.render(synchronizer).appendTo(element);
						element.append("<p style=\"page-break-before: always\">");
					}
				}
			}
		}

		return element;
	}

}
