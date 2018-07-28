/*
 * Circlead - Develop and structure evolutionary Organisations
 * 
 * @author Matthias Wegner
 * @version 0.1
 * @since 01.07.2018
 * 
 */
package org.rogatio.circlead.model.work;

import java.util.List;
import java.util.Map;

import org.jsoup.nodes.Element;
import org.rogatio.circlead.control.Repository;
import org.rogatio.circlead.control.synchronizer.atlassian.parser.TablesParserElement;
import org.rogatio.circlead.model.data.ContactDataitem;
import org.rogatio.circlead.model.data.IDataitem;
import org.rogatio.circlead.model.data.PersonDataitem;
import org.rogatio.circlead.view.IRenderer;
import org.rogatio.circlead.view.RenderUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class Person.
 */
public class Person extends DefaultWorkitem implements IRenderer {

	/**
	 * Instantiates a new person.
	 */
	public Person() {
		this.dataitem = new PersonDataitem();
	}

	/**
	 * Instantiates a new person.
	 *
	 * @param dataitem the dataitem
	 */
	public Person(IDataitem dataitem) {
		super(dataitem);
	}

	/**
	 * Gets the fullname.
	 *
	 * @return the fullname
	 */
	public String getFullname() {
		return this.getDataitem().getFullname();
	}

	/**
	 * Sets the fullname.
	 *
	 * @param name the new fullname
	 */
	public void setFullname(String name) {
		this.getDataitem().setFullname(name);
	}

	/**
	 * Sets the contacts.
	 *
	 * @param contacts the new contacts
	 */
	public void setContacts(List<ContactDataitem> contacts) {
		this.getDataitem().setContacts(contacts);
	}

	/**
	 * Gets the contacts.
	 *
	 * @return the contacts
	 */
	public List<ContactDataitem> getContacts() {
		return this.getDataitem().getContacts();
	}

	/**
	 * Sets the data.
	 *
	 * @param contactTables the new data
	 */
	public void setData(TablesParserElement contactTables) {
		this.getDataitem().setData(contactTables.getData());
	}

	/**
	 * Gets the data.
	 *
	 * @return the data
	 */
	public Map<String, String> getData() {
		return this.getDataitem().getData();
	}

	/**
	 * Sets the contacts.
	 *
	 * @param contactTables the new contacts
	 */
	public void setContacts(TablesParserElement contactTables) {
		this.getDataitem().setContacts(contactTables.getContacts());
	}

	/* (non-Javadoc)
	 * @see org.rogatio.circlead.view.IRenderer#render()
	 */
	@Override
	public Element render() {
		Element element = new Element("p");

		RenderUtil.addH2(element, "Kontaktdaten");

		for (ContactDataitem contact : this.getContacts()) {
			RenderUtil.addH3(element, contact.getName());
			RenderUtil.addList(element, contact.getAsList());
		}

		Map<String, String> d = this.getData();
		if (d != null) {
			if (d.size() > 0) {
				RenderUtil.addH2(element, "Stammdaten");
				RenderUtil.addTable(element, d);
			}
		}

		RenderUtil.addH2(element, "Rollen");
		RenderUtil.addRoleList(element, Repository.getInstance().getRolesWithPerson(this.getFullname()), this);

		return element;
	}

	/* (non-Javadoc)
	 * @see org.rogatio.circlead.model.work.DefaultWorkitem#getDataitem()
	 */
	@Override
	public PersonDataitem getDataitem() {
		return (PersonDataitem) dataitem;
	}

	/* (non-Javadoc)
	 * @see org.rogatio.circlead.model.work.DefaultWorkitem#toString()
	 */
	@Override
	public String toString() {
		return this.getDataitem().toString() + ", type=" + getType();
	}

}
