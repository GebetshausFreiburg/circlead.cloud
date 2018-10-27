/*
 * Circlead - Develop and structure evolutionary Organisations
 * 
 * @author Matthias Wegner
 * @version 0.1
 * @since 01.07.2018
 * 
 */
package org.rogatio.circlead.view.report;

import org.jsoup.nodes.Element;
import org.rogatio.circlead.control.synchronizer.ISynchronizer;
import org.rogatio.circlead.model.Parameter;
import org.rogatio.circlead.model.WorkitemStatusParameter;
import org.rogatio.circlead.model.data.ContactDataitem;
import org.rogatio.circlead.model.work.Person;
import org.rogatio.circlead.util.StringUtil;
import org.rogatio.circlead.view.ISynchronizerRendererEngine;

/**
 * The Class PersonListReport.
 */
public class PersonListReport extends DefaultReport {

	public PersonListReport() {
		this.setName("PersonList Report");
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
		tr.appendElement("th").appendText(Parameter.FIRSTNAME.toString());
		tr.appendElement("th").appendText(Parameter.FAMILYNAME.toString());
		tr.appendElement("th").appendText(Parameter.ADRESS.toString());
		tr.appendElement("th").appendText(Parameter.MAIL.toString());
		tr.appendElement("th").appendText(Parameter.MOBILE.toString());
		tr.appendElement("th").appendText(Parameter.PHONE.toString());

		for (Person person : R.getPersons()) {

			if (!person.getStatus().toUpperCase().equals(WorkitemStatusParameter.INACTIVE.getName().toUpperCase())) {
				String a = person.getDataValue(Parameter.ABBREVIATION2.toString());
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
			}
		}

		return element;
	}

}
