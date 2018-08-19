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
import org.rogatio.circlead.control.Repository;
import org.rogatio.circlead.control.synchronizer.ISynchronizer;
import org.rogatio.circlead.model.WorkitemStatusParameter;
import org.rogatio.circlead.model.data.ContactDataitem;
import org.rogatio.circlead.model.work.IWorkitem;
import org.rogatio.circlead.model.work.Person;
import org.rogatio.circlead.model.work.Role;
import org.rogatio.circlead.model.work.Rolegroup;
import org.rogatio.circlead.util.ObjectUtil;

public class PersonListReport extends DefaultReport {

	/**
	 * Instantiates a new rework report.
	 */
	public PersonListReport() {
		this.setName("PersonList Report");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.rogatio.circlead.view.DefaultReport#render(org.rogatio.circlead.control.synchronizer.ISynchronizer)
	 */
	@Override
	public Element render(ISynchronizer synchronizer) {
		// ISynchronizerRenderer renderer = synchronizer.getRenderer();
		Element element = new Element("p");

		Element table = element.appendElement("div").appendElement("table");
		Element tr = table.appendElement("tr");
		tr.appendElement("th").appendText("Kürzel");
		tr.appendElement("th").appendText("Vorname");
		tr.appendElement("th").appendText("Nachname");
		tr.appendElement("th").appendText("Adresse");
		tr.appendElement("th").appendText("Mail");
		tr.appendElement("th").appendText("Mobil");
		tr.appendElement("th").appendText("Festnetz");
		tr.appendElement("th").appendText("Status");

		for (Person person : Repository.getInstance().getPersons()) {
			String a = person.getDataValue("Kürzel");
			tr = table.appendElement("tr");
			if (a != null) {
				tr.appendElement("td").appendText(a);
			} else {
				tr.appendElement("td").appendText("-");
			}
			tr.appendElement("td").appendText(person.getNames());
			tr.appendElement("td").appendText(person.getFamilyname());

			ContactDataitem contact = person.getFirstPrivateContact();
			if (contact != null) {
				tr.appendElement("td").appendText("" + contact.getAddress());
				tr.appendElement("td").appendText("" + contact.getMail());
				tr.appendElement("td").appendText("" + contact.getMobile());
				tr.appendElement("td").appendText("" + contact.getPhone());
			}
			tr.appendElement("td").appendText("" + person.getStatus());
			
		}

		return element;
	}

}
