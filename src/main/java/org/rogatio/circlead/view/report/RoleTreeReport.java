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
import org.rogatio.circlead.control.synchronizer.file.FileSynchronizer;
import org.rogatio.circlead.model.Parameter;
import org.rogatio.circlead.model.WorkitemStatusParameter;
import org.rogatio.circlead.model.work.Role;
import org.rogatio.circlead.util.ObjectUtil;
import org.rogatio.circlead.view.ISynchronizerRendererEngine;
import org.rogatio.circlead.view.SvgBuilder;

public class RoleTreeReport extends DefaultReport {

	public RoleTreeReport() {
		this.setName("RoleTree Report");
		this.setDescription("Phylogenetischer Rollenbaum");
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
	
	private void addList(Role role, Element ul, ISynchronizerRendererEngine renderer) {
		
		renderer.addRoleItem(ul.appendElement("li"), null, role.getTitle());
		
		List<Role> children = R.getRoleChildren(role.getTitle(), Comparators.REDUNDANCE);
		
		if (ObjectUtil.isListNotNullAndEmpty(children)) {
			
			Element ulchild = ul.appendElement("ul");
			for (Role rolechild : children) {
				addList(rolechild, ulchild, renderer);	
			}
			
		}
	}

}
