/*
 * Circlead - Develop and structure evolutionary Organisations
 * 
 * @author Matthias Wegner
 * @version 0.1
 * @since 01.07.2018
 * 
 */
package org.rogatio.circlead.view.report;

import java.util.List;

import org.jsoup.nodes.Element;
import org.rogatio.circlead.control.Comparators;
import org.rogatio.circlead.control.synchronizer.ISynchronizer;
import org.rogatio.circlead.model.work.Role;
import org.rogatio.circlead.util.ObjectUtil;
import org.rogatio.circlead.util.StringUtil;
import org.rogatio.circlead.view.ISynchronizerRendererEngine;

/**
 * The Class RoleTreeReport.
 */
public class RoleTreeReport extends DefaultReport {

	/**
	 * Instantiates a new role tree report.
	 */
	public RoleTreeReport() {
		this.setName("RoleTree Report");
		this.setDescription(
				"Phylogenetischer Rollenbaum der Rollen. Zeigt die Vererbungshierarchie der Rollen an. Von Links nach Rechts nimmt der Kompetenzgrad und die Spezialisierung der Rollen zu.");
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

		Element ul = element.appendElement("ul");

		List<Role> roots = R.getRootRoles(Comparators.REDUNDANCE);
		if (ObjectUtil.isListNotNullAndEmpty(roots)) {
			for (Role role : roots) {
				addList(role, ul, renderer);
			}
		}

		return element;
	}

	/**
	 * Adds the list.
	 *
	 * @param role     the role
	 * @param ul       the ul
	 * @param renderer the renderer
	 */
	private void addList(Role role, Element ul, ISynchronizerRendererEngine renderer) {

		Element li = ul.appendElement("li");
		renderer.addRoleItem(li, null, role.getTitle());

		if (StringUtil.isNotNullAndNotEmpty(role.getPurpose())) {
			li.appendText("" + role.getPurpose());
		}

		List<Role> children = R.getRoleChildren(role.getTitle(), Comparators.REDUNDANCE);

		if (ObjectUtil.isListNotNullAndEmpty(children)) {

			Element ulchild = ul.appendElement("ul");
			for (Role rolechild : children) {
				addList(rolechild, ulchild, renderer);
			}

		}
	}

}
