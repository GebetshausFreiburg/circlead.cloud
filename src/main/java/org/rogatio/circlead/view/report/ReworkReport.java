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
import org.rogatio.circlead.model.WorkitemStatusParameter;
import org.rogatio.circlead.model.work.IWorkitem;
import org.rogatio.circlead.model.work.Role;
import org.rogatio.circlead.model.work.Rolegroup;
import org.rogatio.circlead.util.ObjectUtil;
import org.rogatio.circlead.view.ISynchronizerRendererEngine;

/**
 * The Class ReworkReport.
 */
public class ReworkReport extends DefaultReport {

	/**
	 * Instantiates a new rework report.
	 */
	public ReworkReport() {
		this.setName("Rework Report");
		this.setDescription("Anzeige aller Workitems (Rollen und Rollengruppen) die nicht Aktiv sind");
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

		List<Rolegroup> rolegroups = new ArrayList<Rolegroup>();
		rolegroups.addAll(R.getRolegroups(WorkitemStatusParameter.DRAFT));
		rolegroups.addAll(R.getRolegroups(WorkitemStatusParameter.CRITICAL));
		rolegroups.addAll(R.getRolegroups(WorkitemStatusParameter.INPROGRESS));
		rolegroups.addAll(R.getRolegroups(WorkitemStatusParameter.TEMPORARY));
		renderer.addWorkitemTable(element, ObjectUtil.castList(IWorkitem.class, rolegroups));

		element.appendElement("p");

		List<Role> roles = new ArrayList<Role>();
		roles.addAll(R.getRoles(WorkitemStatusParameter.DRAFT));
		roles.addAll(R.getRoles(WorkitemStatusParameter.CRITICAL));
		roles.addAll(R.getRoles(WorkitemStatusParameter.INPROGRESS));
		roles.addAll(R.getRoles(WorkitemStatusParameter.TEMPORARY));
		renderer.addWorkitemTable(element, ObjectUtil.castList(IWorkitem.class, roles));

		return element;
	}

}
