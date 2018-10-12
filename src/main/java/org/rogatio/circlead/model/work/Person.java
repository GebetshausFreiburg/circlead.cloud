/*
 * Circlead - Develop and structure evolutionary Organisations
 * 
 * @author Matthias Wegner
 * @version 0.1
 * @since 01.07.2018
 * 
 */
package org.rogatio.circlead.model.work;

import static org.rogatio.circlead.model.Parameter.CONTACTS;
import static org.rogatio.circlead.model.Parameter.PERSONDATA;
import static org.rogatio.circlead.model.Parameter.ROLESINORGANISATION;
import static org.rogatio.circlead.model.Parameter.ROLESINTEAM;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.dmfs.rfc5545.recur.Freq;
import org.jsoup.nodes.Element;
import org.rogatio.circlead.control.Repository;
import org.rogatio.circlead.control.synchronizer.ISynchronizer;
import org.rogatio.circlead.control.synchronizer.atlassian.AtlassianSynchronizer;
import org.rogatio.circlead.control.synchronizer.atlassian.parser.PairTableParserElement;
import org.rogatio.circlead.control.synchronizer.atlassian.parser.Parser;
import org.rogatio.circlead.control.synchronizer.file.FileSynchronizer;
import org.rogatio.circlead.control.validator.IValidator;
import org.rogatio.circlead.control.validator.ValidationMessage;
import org.rogatio.circlead.model.data.ContactDataitem;
import org.rogatio.circlead.model.data.IDataitem;
import org.rogatio.circlead.model.data.PersonDataitem;
import org.rogatio.circlead.model.data.TeamEntry;
import org.rogatio.circlead.util.CircleadRecurrenceRule;
import org.rogatio.circlead.util.ObjectUtil;
import org.rogatio.circlead.util.StringUtil;
import org.rogatio.circlead.view.ISynchronizerRendererEngine;
import org.rogatio.circlead.view.IWorkitemRenderer;

/**
 * The Class Person.
 */
public class Person extends DefaultWorkitem implements IWorkitemRenderer, IValidator {

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
	 * Gets the familyname.
	 *
	 * @return the familyname
	 */
	public String getFamilyname() {
		return this.getDataitem().getFamilyname();
	}

	/**
	 * Gets the names.
	 *
	 * @return the names
	 */
	public String getNames() {
		StringBuilder sb = new StringBuilder();
		if (this.getDataitem().getFirstname() != null) {
			sb.append(this.getDataitem().getFirstname() + " ");
		}
		if (this.getDataitem().getSecondname() != null) {
			sb.append(this.getDataitem().getSecondname() + " ");
		}
		return sb.toString().trim();
	}

	/**
	 * Gets the avatar.
	 *
	 * @return the avatar
	 */
	public String getAvatar() {
		return this.getDataitem().getAvatar();
	}

	/**
	 * Sets the avatar.
	 *
	 * @param avatar the new avatar
	 */
	public void setAvatar(String avatar) {
		this.getDataitem().setAvatar(avatar);
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
	 * Gets the first private contact.
	 *
	 * @return the first private contact
	 */
	public ContactDataitem getFirstPrivateContact() {
		List<ContactDataitem> contacts = this.getDataitem().getContacts();
		for (ContactDataitem contactDataitem : contacts) {
			if (contactDataitem.getName().equalsIgnoreCase("Privat")) {
				return contactDataitem;
			}
		}
		return null;
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
	public void setData(PairTableParserElement contactTables) {
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
	public void setContacts(PairTableParserElement contactTables) {
		this.getDataitem().setContacts(contactTables.getContacts());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.rogatio.circlead.view.IRenderer#render()
	 */
	@Override
	public Element render(ISynchronizer synchronizer) {
		ISynchronizerRendererEngine renderer = synchronizer.getRenderer();

		Element element = new Element("p");

		if (StringUtil.isNotNullAndNotEmpty(this.getAvatar())) {
			if (synchronizer.getClass().getSimpleName().equals(AtlassianSynchronizer.class.getSimpleName())) {
				element.append(Parser.addImage(getAvatar(), 250, 1));
			} else if (synchronizer.getClass().getSimpleName().equals(FileSynchronizer.class.getSimpleName())) {
				element.append("<img src=\"..\\data\\images\\profile\\" + getAvatar() + "\" alt=\"" + this.getFullname()
						+ "\" width=\"250px\">");
			}
		}

		renderer.addH2(element, CONTACTS.toString());

		for (ContactDataitem contact : this.getContacts()) {
			renderer.addH3(element, contact.getName());
			renderer.addList(element, contact.getAsList());
		}

		Map<String, String> d = this.getData();
		if (d != null) {
			if (d.size() > 0) {
				renderer.addH2(element, PERSONDATA.toString());
				renderer.addTable(element, d);
			}
		}

		double sumR = Repository.getInstance().getAverageAllokationInOrganisation(this.getFullname(), Freq.WEEKLY);
		renderer.addH2(element, ROLESINORGANISATION.toString() + " ("+Math.round(sumR)+"h/Woche)");
		
		List<Role> orgRoles = Repository.getInstance().getRolesWithPerson(this.getFullname());
		renderer.addRoleList(element, orgRoles, this);

		List<Team> foundTeams = Repository.getInstance().getTeamsWithMember(this);
		if (ObjectUtil.isListNotNullAndEmpty(foundTeams)) {
			double sum = Repository.getInstance().getAverageAllokationInTeams(this, Freq.WEEKLY);
			
			renderer.addH2(element, ROLESINTEAM.toString()+ " ("+Math.round(sum)+"h/Woche)");
			Element ul = element.appendElement("ul");
			for (Team team : foundTeams) {
				Element li = ul.appendElement("li");
				String c = "";
				if (StringUtil.isNotNullAndNotEmpty(team.getCategory())) {
					c = " (" + team.getCategory() + ")";
				}

				if (synchronizer.getClass().getSimpleName().equals(AtlassianSynchronizer.class.getSimpleName())) {
					li.append("<ac:link><ri:page ri:content-title=\""+team.getTitle()+"\" ri:version-at-save=\"1\"/><ac:plain-text-link-body><![CDATA["+team.getTitle()+""+c+"]]></ac:plain-text-link-body></ac:link>");
				} else if (synchronizer.getClass().getSimpleName().equals(FileSynchronizer.class.getSimpleName())) {
					li.appendElement("a").attr("href", "../web/" + team.getId(synchronizer) + ".html").appendText(team.getTitle()+c);
				}
				
				List<TeamEntry> x = team.getTeamEntries();
				Element ul2 = li.appendElement("ul");
				for (TeamEntry e : x) {
					if (e.getPersonIdentifiers().contains(this.getFullname())) {
						Element li2 = ul2.appendElement("li");
						renderer.addRoleItem(li2, null, e.getRoleIdentifier());
					}
				}
			}
		}

		return element;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.rogatio.circlead.model.work.DefaultWorkitem#getDataitem()
	 */
	@Override
	public PersonDataitem getDataitem() {
		return (PersonDataitem) dataitem;
	}

	/**
	 * Gets the data value.
	 *
	 * @param dataKey the data key
	 * @return the data value
	 */
	public String getDataValue(String dataKey) {
		if (this.getData() != null) {
			if (this.getData().containsKey(dataKey)) {
				return this.getData().get(dataKey);
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.rogatio.circlead.model.work.DefaultWorkitem#toString()
	 */
	@Override
	public String toString() {
		return this.getDataitem().toString() + ", type=" + getType();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.rogatio.circlead.control.IValidator#validate()
	 */
	@Override
	public List<ValidationMessage> validate() {
		List<ValidationMessage> messages = new ArrayList<ValidationMessage>();

		ArrayList<Role> roles = Repository.getInstance().getRolesWithPerson(this.getFullname());
		if (roles.size() == 0) {
			ValidationMessage m = new ValidationMessage(this);
			m.error("Person has no role", "Person '" + this.getFullname() + "' has no related role");
			messages.add(m);
		}

		List<Team> foundTeams = Repository.getInstance().getTeamsWithMember(this);
		if (!ObjectUtil.isListNotNullAndEmpty(foundTeams)) {
			ValidationMessage m = new ValidationMessage(this);
			m.warning("Person has no team-role", "Person '" + this.getFullname() + "' has no related team-role");
			messages.add(m);
		}
		
		/*
		 * if (!this.hasAbbreviation()) { ValidationMessage m = new
		 * ValidationMessage(this); m.warning("No abbreviation added", "Role '" +
		 * this.getTitle() + "' has no abbreviation"); messages.add(m); }
		 */
		return messages;
	}

}
