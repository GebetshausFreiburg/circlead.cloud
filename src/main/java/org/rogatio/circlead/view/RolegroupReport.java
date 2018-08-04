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

/**
 * The Class RolegroupReport shows for every rolegroup the included data with all coresponding roles. Pagebreak is included after every item.
 */
public class RolegroupReport extends DefaultReport {

	/** The rolegroup. */
	private Rolegroup rolegroup;

	/**
	 * Instantiates a new rolegroup report.
	 *
	 * @param rolegroup the rolegroup
	 */
	public RolegroupReport(Rolegroup rolegroup) {
		this.setName("Report '" + rolegroup.getTitle() + "'");
		this.rolegroup = rolegroup;
	}

	/* (non-Javadoc)
	 * @see org.rogatio.circlead.view.DefaultReport#render(org.rogatio.circlead.control.synchronizer.ISynchronizer)
	 */
	@Override
	public Element render(ISynchronizer synchronizer) {
		ISynchronizerRenderer renderer = synchronizer.getRenderer();
		Element element = new Element("p");

		renderer.addH2(element, rolegroup.getTitle());
		renderer.addStatus(element, rolegroup.getStatus());
		
		rolegroup.render(synchronizer).appendTo(element);
		element.append("<p style=\"page-break-before: always\">");
		
		List<Role> roles = Repository.getInstance().getRoles(rolegroup.getTitle());
		if (roles.size() > 0) {
			for (Role role : roles) {
				renderer.addH2(element, role.getTitle());
				renderer.addStatus(element, role.getStatus());
				
				role.render(synchronizer).appendTo(element);
				element.append("<p style=\"page-break-before: always\">");
			}
		}

		return element;
	}

}
