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
import org.rogatio.circlead.control.synchronizer.ISynchronizer;
import org.rogatio.circlead.model.data.ContactDataitem;
import org.rogatio.circlead.model.work.Person;
import org.rogatio.circlead.model.work.Role;
import org.rogatio.circlead.model.work.Rolegroup;
import org.rogatio.circlead.util.ObjectUtil;
import org.rogatio.circlead.util.StringUtil;
import org.rogatio.circlead.view.renderer.ISynchronizerRendererEngine;

/**
 * The Class RolegroupSummaryReport.
 * 
 * @author Matthias Wegner
 */
public class RolegroupSummaryReport extends DefaultReport {

	/**
	 * Instantiates a new rolegroup summary report.
	 */
	public RolegroupSummaryReport() {
		this.setName("Rolegroup-Summary Report");
		this.setDescription("Tabellarische Übersicht aller Rollengruppen mit der Auflistung aller enthaltenen Rollen");
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

		Element table = new Element("table");
		table.attr("class", "wrapped");
		Element tbody = table.appendElement("tbody");

		Element tr = tbody.appendElement("tr");
		tr.appendElement("th").attr("colspan", "1").appendText("Bereich (Rollengruppe)");
		tr.appendElement("th").attr("colspan", "1").appendText("Beschreibung");
		tr.appendElement("th").attr("colspan", "1").appendText("Rollen");
		tr.appendElement("th").attr("colspan", "1").appendText("Zugehörige Rollengruppen");
		tr.appendElement("th").attr("colspan", "1").appendText("Ansprechpartner (Organisationsentwickler)");

		List<Rolegroup> rolegroups = R.getRolegroups();
		for (Rolegroup rolegroup : rolegroups) {
			tr = tbody.appendElement("tr");
			if (StringUtil.isNotNullAndNotEmpty(rolegroup.getTitle())) {
				tr.appendElement("td").attr("colspan", "1").appendText(rolegroup.getTitle());
			} else {
				tr.appendElement("td").attr("colspan", "1").appendText("-");		
			}
			
			if (StringUtil.isNotNullAndNotEmpty(rolegroup.getSummary())) {
				tr.appendElement("td").attr("colspan", "1").appendText(rolegroup.getSummary());
			} else {
				tr.appendElement("td").attr("colspan", "1").appendText("-");		
			}
			
			List<Role> roles = R.getRoles(rolegroup.getTitle());
			if (ObjectUtil.isListNotNullAndEmpty(roles)) {
				Element td = tr.appendElement("td").attr("colspan", "1");
//				Element ul = td.appendElement("ul");
//				List<Role> roles = R.getRoles(this.getTitle());
//				if (roles.size() > 0) {
//					renderer.addH2(element, BRANDROLE.toString());
					renderer.addRoleList(td, roles);
//				}
//				for (Role role : roles) {
//					ul.appendElement("li").appendText(role.getTitle());
//				}
			} else {
				tr.appendElement("td").attr("colspan", "1").appendText("-");		
			}
		
			List<Rolegroup> rgs = R.getRolegroupChildren(rolegroup.getTitle());
			if (ObjectUtil.isListNotNullAndEmpty(rgs)) {
				Element td = tr.appendElement("td").attr("colspan", "1");
				renderer.addRolegroupList(td, rgs);
			} else {
				tr.appendElement("td").attr("colspan", "1").appendText("-");			
			}
			
			String lead = rolegroup.getLeadIdentifier();
			if (StringUtil.isNotNullAndNotEmpty(lead)) {
				Person p = R.getPerson(lead);
				
				Element td = tr.appendElement("td");
				
				if (p!=null) {
					/*if (StringUtil.isNotNullAndNotEmpty(p.getAvatar())) {
						if (synchronizer.getClass().getSimpleName().equals(AtlassianSynchronizer.class.getSimpleName())) {
							td.append(Parser.addImageFromOtherPage(p.getFullname(), p.getAvatar(), 100, 1));
						} else if (synchronizer.getClass().getSimpleName().equals(FileSynchronizer.class.getSimpleName())) {
							td.append("<img src=\"..\\data\\images\\profile\\"+p.getAvatar()+"\" alt=\""+p.getFullname()+"\" width=\"100px\">");
						}
						td.appendElement("br");
					}*/
					
					List<ContactDataitem> c = p.getContacts();
					if (ObjectUtil.isListNotNullAndEmpty(c)) {
						ContactDataitem contact = null;
						for (ContactDataitem con : c) {
							String r = rolegroup.getResponsibleIdentifier();
							Role role = R.getRole(r);
							
							if (role!=null) {
								if (role.getOrganisationIdentifier().equals(con.getOrganisation())) {
									contact = con;
								}
							}
						}
						if (contact==null) {
							if (c.size()>0) {
								contact = c.get(0);
							}
						}
						if (contact!=null) {
						td.
							appendText(p.getFullname()).appendElement("br").
							appendText(contact.getMail()).appendElement("br").
							appendText(contact.getMobile());
						}
					} else {
					td.attr("colspan", "1").appendText("-");									
					}
				} else {
					td.attr("colspan", "1").appendText("-");					
				}
			} else {
				tr.appendElement("td").attr("colspan", "1").appendText("-");		
			}
			
		}
		
		table.appendTo(element);

		return element;
	}

}
