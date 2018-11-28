name = "PersonList Report"
description = "Adressliste aller Mitarbeiter in der Organisation"

import org.jsoup.nodes.Element;
import org.rogatio.circlead.control.synchronizer.ISynchronizer;
import org.rogatio.circlead.control.synchronizer.atlassian.AtlassianSynchronizer;
import org.rogatio.circlead.model.Parameter;
import org.rogatio.circlead.model.WorkitemStatusParameter;
import org.rogatio.circlead.model.data.ContactDataitem;
import org.rogatio.circlead.model.work.Person;
import org.rogatio.circlead.util.StringUtil;
import org.rogatio.circlead.view.renderer.ISynchronizerRendererEngine;

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
		
		//Need a Space-Column on right side in confluence, because PDF-Export truncates right column
		if (synchronizer.getClass().getSimpleName().equals(AtlassianSynchronizer.class.getSimpleName())) {
			tr.appendElement("th").append("&nbsp;");
		}

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
					
					//Need a Space-Column on right side in confluence, because PDF-Export truncates right column
					if (synchronizer.getClass().getSimpleName().equals(AtlassianSynchronizer.class.getSimpleName())) {
						tr.appendElement("td").append("&nbsp;");
					}
				}
			}
		}

		return element;
