package org.rogatio.circlead.view;

import java.util.List;

import org.jsoup.nodes.Element;
import org.rogatio.circlead.control.Repository;
import org.rogatio.circlead.control.ValidationMessage;
import org.rogatio.circlead.control.synchronizer.ISynchronizer;
import org.rogatio.circlead.model.work.Role;
import org.rogatio.circlead.model.work.Rolegroup;

public class RolegroupReport extends DefaultReport {

	private Rolegroup rolegroup;

	public RolegroupReport(Rolegroup rolegroup) {
		this.setName("Report '" + rolegroup.getTitle() + "'");
		this.rolegroup = rolegroup;
	}

	@Override
	public Element render(ISynchronizer synchronizer) {
		ISynchronizerRenderer renderer = synchronizer.getRenderer();
		Element element = new Element("p");

		renderer.addH2(element, rolegroup.getTitle());
		rolegroup.render(synchronizer).appendTo(element);
		element.append("<p style=\"page-break-before: always\">");
		
		List<Role> roles = Repository.getInstance().getRoles(rolegroup.getTitle());
		if (roles.size() > 0) {
			for (Role role : roles) {
				renderer.addH2(element, role.getTitle());
				role.render(synchronizer).appendTo(element);
				element.append("<p style=\"page-break-before: always\">");
			}
		}

		return element;
	}

}
