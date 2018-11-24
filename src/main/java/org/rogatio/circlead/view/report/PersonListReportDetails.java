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

import org.dmfs.rfc5545.recur.Freq;
import org.jsoup.nodes.Element;
import org.rogatio.circlead.control.synchronizer.ISynchronizer;
import org.rogatio.circlead.control.synchronizer.file.FileSynchronizer;
import org.rogatio.circlead.model.Parameter;
import org.rogatio.circlead.model.data.ContactDataitem;
import org.rogatio.circlead.model.work.Person;
import org.rogatio.circlead.model.work.Role;
import org.rogatio.circlead.util.StringUtil;
import org.rogatio.circlead.view.SvgBuilder;
import org.rogatio.circlead.view.renderer.ISynchronizerRendererEngine;

/**
 * The Class PersonListReportDetails.
 * 
 * @author Matthias Wegner
 */
public class PersonListReportDetails extends DefaultReport {

	/**
	 * Instantiates a new person list report details.
	 */
	public PersonListReportDetails() {
		this.setName("PersonList Report Details");
		this.setDescription("Detaillierte Mitarbeiterliste (inkl. Auslastung und Rollenanzahl))");
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

		Element table = element.appendElement("div").appendElement("table");
		Element tr = table.appendElement("tr");
		tr.appendElement("th").appendText(Parameter.ABBREVIATION2.toString());
		tr.appendElement("th").appendText("Name");
		if (synchronizer.getClass().getSimpleName().equals(FileSynchronizer.class.getSimpleName())) {
			tr.appendElement("th").attr("width", "68px").appendText("DNA");
		}
		tr.appendElement("th").appendText(Parameter.ADRESS.toString());
		tr.appendElement("th").appendText(Parameter.MAIL.toString());
		tr.appendElement("th").appendText(Parameter.MOBILE.toString());
		tr.appendElement("th").appendText(Parameter.PHONE.toString());
		tr.appendElement("th").appendText("Anzahl Rollen");
		tr.appendElement("th").appendText(Parameter.ROLES.toString());
		tr.appendElement("th").appendText("h/Woche");
		tr.appendElement("th").appendText(Parameter.FTE.toString());
		tr.appendElement("th").appendText("Org-Workload");
		tr.appendElement("th").appendText(Parameter.TEAMFRACTION.toString());
		tr.appendElement("th").appendText("Team-Workload");
		tr.appendElement("th").appendText(Parameter.STATUS.toString());

		for (Person person : R.getPersons()) {
			String a = person.getDataValue(Parameter.ABBREVIATION2.toString());
			tr = table.appendElement("tr");
			if (a != null) {
				tr.appendElement("td").appendText(a);
			} else {
				tr.appendElement("td").appendText("-");
			}

			renderer.addPersonItem(tr.appendElement("td"), null, person.getFullname());

			if (synchronizer.getClass().getSimpleName().equals(FileSynchronizer.class.getSimpleName())) {
				tr.appendElement("td").append(SvgBuilder.createPersonDnaProfile(person, 64).toString());
			}
			
			ContactDataitem contact = person.getFirstPrivateContact();
			if (contact != null) {
				tr.appendElement("td").appendText("" + contact.getAddress());
				tr.appendElement("td").appendText("" + contact.getMail());
				if (StringUtil.isNotNullAndNotEmpty(contact.getMobile())) {
					tr.appendElement("td").appendText("" + contact.getMobile());
				} else {
					tr.appendElement("td").appendText("-");
				}
				if (StringUtil.isNotNullAndNotEmpty(contact.getPhone())) {
					tr.appendElement("td").appendText("" + contact.getPhone());
				} else {
					tr.appendElement("td").appendText("-");
				}
			}

			ArrayList<Role> roles = R.getRolesWithPerson(person);
			tr.appendElement("td").appendText(roles.size() + "");

			Element td = tr.appendElement("td");
			renderer.addRoleList(td, roles);

			long hpw = Math.round(person.getAverageAllokation(Freq.WEEKLY));
			if (hpw != 0) {
				tr.appendElement("td").appendText("" + hpw);
			} else {
				tr.appendElement("td").appendText("-");
			}
			
			tr.appendElement("td").appendText(Math.round(person.getFullTimeEquivalent())+"%");
			
			tr.appendElement("td").appendText(Math.round(person.getOrganisationalWorkload())+"%");
			
			tr.appendElement("td").appendText(Math.round(person.getTeamFraction())+"%");

			tr.appendElement("td").appendText(Math.round(person.getTeamWorkload())+"%");
			
			renderer.addStatus(tr.appendElement("td"), person.getStatus());

		}

		return element;
	}

}
