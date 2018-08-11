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
import org.rogatio.circlead.model.StatusParameter;
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

		for (Person person : Repository.getInstance().getPersons()) {
			String a = person.getDataValue("Kürzel");
			Element tr = table.appendElement("tr");
			if (a != null) {
				tr.appendElement("th").appendText(a);
			} else {
				tr.appendElement("th").appendText("-");
			}
			tr.appendElement("td").appendText(person.getFullname());
		}

		return element;
	}

}
