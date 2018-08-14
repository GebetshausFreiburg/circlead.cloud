/*
 * Circlead - Develop and structure evolutionary Organisations
 * 
 * @author Matthias Wegner
 * @version 0.1
 * @since 01.07.2018
 * 
 */
package org.rogatio.circlead.view;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Element;
import org.rogatio.circlead.control.Repository;
import org.rogatio.circlead.control.synchronizer.ISynchronizer;
import org.rogatio.circlead.model.WorkitemStatusParameter;
import org.rogatio.circlead.model.work.IWorkitem;
import org.rogatio.circlead.model.work.Role;
import org.rogatio.circlead.model.work.Rolegroup;
import org.rogatio.circlead.util.ObjectUtil;

public class ReworkReport extends DefaultReport {

	/**
	 * Instantiates a new rework report.
	 */
	public ReworkReport() {
		this.setName("Rework Report");
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

		List<Rolegroup> rolegroups = new ArrayList<Rolegroup>();
		rolegroups.addAll(Repository.getInstance().getRolegroups(WorkitemStatusParameter.DRAFT));
		rolegroups.addAll(Repository.getInstance().getRolegroups(WorkitemStatusParameter.CRITICAL));
		rolegroups.addAll(Repository.getInstance().getRolegroups(WorkitemStatusParameter.INPROGRESS));
		rolegroups.addAll(Repository.getInstance().getRolegroups(WorkitemStatusParameter.TEMPORARY));
		renderer.addWorkitemTable(element, ObjectUtil.castList(IWorkitem.class, rolegroups));
		
		element.appendElement("p");
		
		List<Role> roles = new ArrayList<Role>();
		roles.addAll(Repository.getInstance().getRoles(WorkitemStatusParameter.DRAFT));
		roles.addAll(Repository.getInstance().getRoles(WorkitemStatusParameter.CRITICAL));
		roles.addAll(Repository.getInstance().getRoles(WorkitemStatusParameter.INPROGRESS));
		roles.addAll(Repository.getInstance().getRoles(WorkitemStatusParameter.TEMPORARY));
		renderer.addWorkitemTable(element, ObjectUtil.castList(IWorkitem.class, roles));

		//System.out.println(element);
		
		return element;
	}

}
