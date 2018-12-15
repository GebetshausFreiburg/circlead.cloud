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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

import org.dmfs.rfc5545.recur.Freq;
import org.jsoup.nodes.Element;
import org.rogatio.circlead.control.CircleadRecurrenceRule;
import org.rogatio.circlead.control.synchronizer.ISynchronizer;
import org.rogatio.circlead.control.synchronizer.atlassian.parser.PairTableParserElement;
import org.rogatio.circlead.control.synchronizer.file.FileSynchronizer;
import org.rogatio.circlead.control.validator.IValidator;
import org.rogatio.circlead.control.validator.ValidationMessage;
import org.rogatio.circlead.model.Parameter;
import org.rogatio.circlead.model.WorkitemStatusParameter;
import org.rogatio.circlead.model.data.ContactDataitem;
import org.rogatio.circlead.model.data.IDataRow;
import org.rogatio.circlead.model.data.IDataitem;
import org.rogatio.circlead.model.data.PersonDataitem;
import org.rogatio.circlead.model.data.TeamEntry;
import org.rogatio.circlead.model.data.Timeslice;
import org.rogatio.circlead.util.ObjectUtil;
import org.rogatio.circlead.util.PropertyUtil;
import org.rogatio.circlead.util.StringUtil;
import org.rogatio.circlead.view.SvgBuilder;
import org.rogatio.circlead.view.renderer.ISynchronizerRendererEngine;
import org.rogatio.circlead.view.renderer.IWorkitemRenderer;

/**
 * The Class Person is the working item for a person.
 *
 * @author Matthias Wegner
 */
public class Person extends DefaultWorkitem implements IWorkitemRenderer, IValidator, IDataRow {

	/**
	 * Instantiates a new person.
	 */
	public Person() {
		this.dataitem = new PersonDataitem();
	}

	/**
	 * Instantiates a new person.
	 *
	 * @param dataitem the dataitem of the person of class
	 *                 {@link org.rogatio.circlead.model.data.PersonDataitem}
	 */
	public Person(IDataitem dataitem) {
		super(dataitem);

		if (!(dataitem instanceof PersonDataitem)) {
			throw new IllegalArgumentException("IDataitem must be of type PersonDataitem");
		}
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
	 * Sets the image-filename of the avatar from a person.
	 *
	 * @param avatar the new filename for the avatar
	 */
	public void setAvatar(String avatar) {
		this.getDataitem().setAvatar(avatar);
	}

	/**
	 * Sets the team fraction.
	 *
	 * @param fte the new team fraction
	 */
	public void setTeamFraction(double fte) {
		this.getDataitem().setTeamFraction(fte);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.rogatio.circlead.model.work.DefaultWorkitem#getReferencedItems()
	 */
	@Override
	public List<IWorkitem> getReferencedItems() {
		List<IWorkitem> references = new ArrayList<IWorkitem>();
		return references;
	}

	/**
	 * Gets the organisational workload of the person.
	 *
	 * @return the organisational workload
	 */
	public double getOrganisationalWorkload() {
		double fte = this.getDataitem().getFullTimeEquivalent();

		if (fte == 0) {
			return 0;
		}

		double tf = this.getDataitem().getTeamFraction();

		double divider = (fte / 100.0) * ((100.0 - tf) / 100.0);

		double alloc = R.getAverageAllokationInOrganisation(this.getFullname(), Freq.WEEKLY);

		return 100 * alloc / (divider * 40.0);
	}

	/**
	 * Gets the team workload of a person.
	 *
	 * @return the team workload
	 */
	public double getTeamWorkload() {
		double fte = this.getDataitem().getFullTimeEquivalent();

		if (fte == 0) {
			return 0;
		}

		double tf = this.getDataitem().getTeamFraction();
		if (tf == 0) {
			return 0;
		}

		double divider = (fte / 100.0) * (tf / 100.0);

		double teamAllocation = R.getAverageAllokationInTeams(this, Freq.WEEKLY);

		return 100 * teamAllocation / (divider * 40.0);
	}

	/**
	 * Gets the team fraction.
	 *
	 * @return the team fraction
	 */
	public double getTeamFraction() {
		return this.getDataitem().getTeamFraction();
	}

	/**
	 * Sets the team fraction. Percentage which defines the team-time-equivalent
	 * from the full-time-equivalent.
	 *
	 * @param tf the new team fraction
	 */
	public void setTeamFraction(String tf) {
		if (StringUtil.isNotNullAndNotEmpty(tf)) {
			double f = Double.parseDouble(tf.replace("%", ""));
			this.getDataitem().setTeamFraction(f);
		} else {
			this.getDataitem().setTeamFraction(0.0);
		}
	}

	/**
	 * Sets the full time equivalent of a person in percent. 100% mean 40h/week.
	 *
	 * @param fte the new full time equivalent
	 */
	public void setFullTimeEquivalent(String fte) {
		if (StringUtil.isNotNullAndNotEmpty(fte)) {
			double f = Double.parseDouble(fte.replace("%", ""));
			this.getDataitem().setFullTimeEquivalent(f);
		} else {
			this.getDataitem().setTeamFraction(0.0);
		}
	}

	/**
	 * Sets the full time equivalent.
	 *
	 * @param fte the new full time equivalent
	 */
	public void setFullTimeEquivalent(double fte) {
		this.getDataitem().setFullTimeEquivalent(fte);
	}

	/**
	 * Gets the full time equivalent.
	 *
	 * @return the full time equivalent
	 */
	public double getFullTimeEquivalent() {
		return this.getDataitem().getFullTimeEquivalent();
	}

	/**
	 * Gets the firstname of a person.
	 *
	 * @return the firstname
	 */
	public String getFirstname() {
		return this.getDataitem().getFirstname();
	}

	/**
	 * Gets the secondname of a person.
	 *
	 * @return the secondname
	 */
	public String getSecondname() {
		return this.getDataitem().getSecondname();
	}

	/**
	 * Checks if person takes a specific role defined by roleIdentifier.
	 *
	 * @param roleIdentifier the role identifier
	 * @return true, if person takes role
	 */
	public boolean takesRole(String roleIdentifier) {
		List<Role> roles = R.getRolesWithPerson(this);
		if (ObjectUtil.isListNotNullAndEmpty(roles)) {
			for (Role r : roles) {
				if (r.getTitle().equalsIgnoreCase(roleIdentifier)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Gets the fullname of a person.
	 *
	 * @return the fullname
	 */
	public String getFullname() {
		return this.getDataitem().getFullname();
	}

	/**
	 * Sets the fullname of a person.
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
	 * Gets the contact.
	 *
	 * @param organisationIdentifier the organisation identifier
	 * @return the contact
	 */
	public ContactDataitem getContact(String organisationIdentifier) {
		List<ContactDataitem> contacts = this.getDataitem().getContacts();
		for (ContactDataitem contactDataitem : contacts) {
			if (contactDataitem.getOrganisation() != null) {
				if (contactDataitem.getOrganisation().equalsIgnoreCase(organisationIdentifier)) {
					return contactDataitem;
				}
			}
		}
		return null;
	}

	/**
	 * Gets the first private contact.
	 *
	 * @return the first private contact
	 */
	public ContactDataitem getFirstPrivateContact() {
		List<ContactDataitem> contacts = this.getDataitem().getContacts();
		for (ContactDataitem contactDataitem : contacts) {
			if (contactDataitem.getName().equalsIgnoreCase(Parameter.PRIVATE.toString())) {
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

	/**
	 * Gets the timeslices.
	 *
	 * @param freq the freq
	 * @return the timeslices
	 */
	public Map<String, List<Timeslice>> getTimeslices(Freq freq) {
		final Map<String, List<Timeslice>> map = this.getOrganisationalTimeslices(freq, false);
		Map<String, List<Timeslice>> m = this.getTeamTimeslices(freq);
		m.forEach((k, v) -> map.put(k, v));
		return map;
	}

	/**
	 * Gets the organisational timeslices.
	 *
	 * @param freq     the freq
	 * @param optimize the optimize
	 * @return the organisational timeslices
	 */
	public Map<String, List<Timeslice>> getOrganisationalTimeslices(Freq freq, boolean optimize) {
		LinkedHashMap<String, List<Timeslice>> map = new LinkedHashMap<String, List<Timeslice>>();
		List<Role> orgRoles = R.getOrganisationalRolesWithPerson(this.getFullname());
		for (Role role : orgRoles) {
			String rule = role.getRecurrenceRule(this.getFullname());
			if (StringUtil.isNotNullAndNotEmpty(rule)) {
				CircleadRecurrenceRule crr = new CircleadRecurrenceRule(rule);
				List<Timeslice> ts = crr.getAllokationSlices(freq);
				map.put(role.getTitle(), ts);
			}
		}

		if (optimize) {
			/*
			 * Merge Timeslices of small taken roles to optimize view
			 */
			if (map.size() > 3) {
				/*
				 * Sort timeslice-datasets by size
				 */
				Map<String, List<Timeslice>> dmap = ObjectUtil.sort(map);
				map = new LinkedHashMap<String, List<Timeslice>>();
				Map<String, List<Timeslice>> tmap = new LinkedHashMap<String, List<Timeslice>>();

				/*
				 * hold biggest three slice-datasets and merge rest
				 */
				Vector<String> keys = new Vector<String>(dmap.keySet());
				for (int i = keys.size() - 1; i >= 0; i--) {
					if (i > keys.size() - 4) {
						map.put(keys.get(i), dmap.get(keys.get(i)));
					} else {
						tmap.put(keys.get(i), dmap.get(keys.get(i)));
					}
				}
				List<Timeslice> ld = ObjectUtil.merge(tmap);
				if (ld != null) {
					map.put("Andere Rollen", ld);
				}
			}
		}
		return map;
	}

	/**
	 * Gets the team timeslices.
	 *
	 * @param freq the freq
	 * @return the team timeslices
	 */
	public Map<String, List<Timeslice>> getTeamTimeslices(Freq freq) {
		Map<String, List<Timeslice>> map = new LinkedHashMap<String, List<Timeslice>>();

		List<Team> foundTeams = R.getTeamsWithMember(this);
		for (Team team : foundTeams) {
			List<Timeslice> ts = team.getAllokationSlices(this, freq);
			map.put(team.getTitle(), ts);
		}

		return map;
	}

	/**
	 * Calc fractions.
	 */
	public void calcFractions() {
		double allocO = R.getAverageAllokationInOrganisation(this.getFullname(), Freq.WEEKLY);
		double allocT = R.getAverageAllokationInTeams(this, Freq.WEEKLY);

		double alloc = allocO + allocT;

		if (this.getFullTimeEquivalent() == 0) {
			this.setFullTimeEquivalent(Math.ceil(100 * alloc / 40.0));
		}
		if (this.getTeamFraction() == 0) {
			if (alloc != 0) {
				double c = Math.ceil((100 * allocT / alloc));
				this.setTeamFraction(c);
			}
		}

//		if (this.getFullTimeEquivalent() < 5) {
//			this.setFullTimeEquivalent(5.0);
//		}
//		
//		if (this.getTeamFraction() < 50) {
//			this.setTeamFraction(50);
//		}
	}

	/**
	 * Checks for contact data.
	 *
	 * @return true, if successful
	 */
	public boolean hasContactData() {
		for (ContactDataitem contact : this.getContacts()) {
			if (StringUtil.isNotNullAndNotEmpty(contact.getAddress())) {
				return true;
			}
			if (StringUtil.isNotNullAndNotEmpty(contact.getPhone())) {
				return true;
			}
			if (StringUtil.isNotNullAndNotEmpty(contact.getMobile())) {
				return true;
			}
			if (StringUtil.isNotNullAndNotEmpty(contact.getMail())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Gets the readable team alloc and workload.
	 *
	 * @return the readable team alloc and workload
	 */
	public String getReadableTeamAllocAndWorkload() {
		double sum = R.getAverageAllokationInTeams(this, Freq.WEEKLY);

		double tw = this.getTeamWorkload();
		String teamWorkload = "";
		if (tw != 0.0) {
			teamWorkload = ", " + Math.round(tw) + "%";
		}

		return " (" + Math.round(sum) + "h/Woche" + teamWorkload + ")";
	}

	/**
	 * Gets the readable organisational alloc and workload.
	 *
	 * @return the readable organisational alloc and workload
	 */
	public String getReadableOrganisationalAllocAndWorkload() {
		double sumR = R.getAverageAllokationInOrganisation(this.getFullname(), Freq.WEEKLY);

		double ow = this.getOrganisationalWorkload();
		String orgWorkload = "";
		if (ow != 0.0) {
			orgWorkload = ", " + Math.round(ow) + "%";
		}

		String add = "";
		if (sumR != 0) {
			add = " (" + Math.round(sumR) + "h/Woche" + orgWorkload + ")";
		}
		return add;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.rogatio.circlead.view.IRenderer#render()
	 */
	@Override
	public Element render(ISynchronizer synchronizer) {
		/*
		 * get renderer of synchronizer to generate rendered view of data
		 */
		ISynchronizerRendererEngine renderer = synchronizer.getRenderer();

		/*
		 * Instanciate html-paragraph as conteiner for data
		 */
		Element element = new Element("p");

		/*
		 * Add person-avatar if it exists
		 */
		if (StringUtil.isNotNullAndNotEmpty(this.getAvatar())) {
			renderer.addImage(element, getAvatar(), 250);
		}

		/*
		 * Add person-dna-profile if system can handle svg
		 */
		if (synchronizer instanceof FileSynchronizer) {
			element.append(SvgBuilder.createPersonDnaProfile(this, 512).toString());
		}

		/*
		 * Display full-time-equivalent or team-fraction of fte if set
		 */
		long fte = Math.round(this.getFullTimeEquivalent());
		long tf = Math.round(this.getTeamFraction());
		if ((fte != 0) || (tf != 0)) {
			renderer.addH2(element, "Plankapazität");
		}
		if (fte != 0) {
			renderer.addItem(element, Parameter.FTE.toString(), fte + "%");
		}
		if (tf != 0) {
			renderer.addItem(element, Parameter.TEAMFRACTION.toString(), tf + "%");
		}

		/*
		 * show contact-data if data is set
		 */
		if (this.hasContactData()) {
			renderer.addH2(element, CONTACTS.toString());
			for (ContactDataitem contact : this.getContacts()) {
				renderer.addH3(element, contact.getName());
				renderer.addList(element, contact.getAsList());
			}
		}

		/*
		 * show custom data table if it is set
		 */
		Map<String, String> d = this.getData();
		if (d != null) {
			if (d.size() > 0) {
				renderer.addH2(element, PERSONDATA.toString());
				renderer.addTable(element, d);
			}
		}

		/*
		 * show roles for person if existent
		 */
		List<Role> orgRoles = R.getOrganisationalRolesWithPerson(this.getFullname());
		if (ObjectUtil.isListNotNullAndEmpty(orgRoles)) {
			renderer.addH2(element, ROLESINORGANISATION.toString() + getReadableOrganisationalAllocAndWorkload());
			renderer.addRoleList(element, orgRoles, this);
		}

		/*
		 * show teams for person if existent
		 */
		List<Team> foundTeams = R.getTeamsWithMember(this);
		if (ObjectUtil.isListNotNullAndEmpty(foundTeams)) {
			renderer.addH2(element, ROLESINTEAM.toString() + getReadableTeamAllocAndWorkload());

			/*
			 * create team-bullet-list
			 */
			Element ul = element.appendElement("ul");
			for (Team team : foundTeams) {
				Element li = ul.appendElement("li");
				/*
				 * create link to team
				 */
				renderer.addTeamLink(li, team);

				/*
				 * add status of team
				 */
				WorkitemStatusParameter status = WorkitemStatusParameter.get(team.getStatus());
				if (status != null) {
					li.append("&nbsp;");
					renderer.addStatus(li, status.getName());
				}

				/*
				 * add team-entries if they exist
				 */
				List<TeamEntry> x = team.getTeamEntries();
				Element ul2 = li.appendElement("ul");
				for (TeamEntry e : x) {
					if (e.getPersons().contains(this.getFullname())) {
						Element li2 = ul2.appendElement("li");
						addTeamEntry(li2, team, e, renderer);
					}
				}
			}
		}

		/*
		 * show ressource chart
		 */
		renderer.addRessourceChart(element, this);

		return element;
	}

	/**
	 * Gets the average allokation.
	 *
	 * @param freq the freq
	 * @return the average allokation
	 */
	public double getAverageAllokation(Freq freq) {
		double orgAlloc = R.getAverageAllokationInOrganisation(this.getFullname(), freq);
		double teamAlloc = R.getAverageAllokationInTeams(this, freq);
		return orgAlloc + teamAlloc;
	}

	/**
	 * Adds the team entry.
	 *
	 * @param listElement the list element
	 * @param team        the team
	 * @param teamEntry   the team entry
	 * @param renderer    the renderer
	 */
	private void addTeamEntry(Element listElement, Team team, TeamEntry teamEntry,
			ISynchronizerRendererEngine renderer) {
		Role role = R.getRole(teamEntry.getRoleIdentifier());

		if (teamEntry.getRoleIdentifier() != null) {
			if (role != null) {
				renderer.addRoleLink(listElement, role);
			} else {
				listElement.appendText(teamEntry.getRoleIdentifier());
			}
		} else {
			listElement.appendText("-");
		}

		if (teamEntry.hasRecurrenceRule(this.getFullname())) {
			String rule = teamEntry.getRecurrenceRule(this.getFullname());
			listElement.append("&nbsp;<span style=\"color: rgb(192,192,192);\">"
					+ rule.replace("R=", "").replace("RRULE=", "") + "</span>");
		} else if (StringUtil.isNotNullAndNotEmpty(team.getRecurrenceRule())) {
			String rule = team.getRecurrenceRule();
			listElement.append("&nbsp;<span style=\"color: rgb(192,192,192);\">"
					+ rule.replace("R=", "").replace("RRULE=", "") + "</span>");
		}
		
		if (role!=null) {
			String comment = teamEntry.getComment(this.getFullname());
			if (StringUtil.isNotNullAndNotEmpty(comment)) {
				listElement.append("&nbsp;|&nbsp;<span style=\"color: rgb(192,192,192);\">"
						+ comment + "</span>");
			}
		}
		
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

		boolean ignoreReprepresentationMessage = false;
		String roleIdentifier = PropertyUtil.getInstance().getApplicationDefaultRoleCoreMember();
		Role r = R.getRole(roleIdentifier);
		if (r != null) {
			if (r.getDataitem().getRepresentation(this.getTitle()).equalsIgnoreCase("Inaktiv")
					|| r.getDataitem().getRepresentation(this.getTitle()).equalsIgnoreCase("Pausiert")) {
				ignoreReprepresentationMessage = true;
			}
		}

		if (r != null) {
			if (this.getStatus().equalsIgnoreCase("Inaktiv") || this.getStatus().equalsIgnoreCase("Pausiert")) {
				ignoreReprepresentationMessage = true;
			}
		}

		ArrayList<Role> roles = R.getOrganisationalRolesWithPerson(this.getFullname());
		if (roles.size() == 0) {
			if (!ignoreReprepresentationMessage) {
				ValidationMessage m = new ValidationMessage(this);
				m.error("Person has no role", "Person '" + this.getFullname() + "' has no related role");
				messages.add(m);
			}
		}

		List<Team> foundTeams = R.getTeamsWithMember(this);
		if (!ObjectUtil.isListNotNullAndEmpty(foundTeams)) {
			if (!ignoreReprepresentationMessage) {
				ValidationMessage m = new ValidationMessage(this);
				m.warning("Person has no team-role", "Person '" + this.getFullname() + "' has no related team-role");
				messages.add(m);
			}
		}

		/*
		 * if (!this.hasAbbreviation()) { ValidationMessage m = new
		 * ValidationMessage(this); m.warning("No abbreviation added", "Role '" +
		 * this.getTitle() + "' has no abbreviation"); messages.add(m); }
		 */
		return messages;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.rogatio.circlead.model.data.IDataRow#getDataRow()
	 */
	@Override
	public Map<Parameter, Object> getDataRow() {
		Map<Parameter, Object> map = new TreeMap<Parameter, Object>();

		addDataRowElement(this.getFirstname(), Parameter.FIRSTNAME, map);
		addDataRowElement(this.getSecondname(), Parameter.SECONDNAME, map);
		addDataRowElement(this.getFamilyname(), Parameter.FAMILYNAME, map);
		addDataRowElement(this.getFirstname(), Parameter.FIRSTNAME, map);

		ContactDataitem cdi = this.getFirstPrivateContact();
		if (cdi != null) {
			addDataRowElement(cdi.getMail(), Parameter.MAIL, map);
			addDataRowElement(cdi.getMobile(), Parameter.MOBILE, map);
			addDataRowElement(cdi.getPhone(), Parameter.PHONE, map);
			addDataRowElement(cdi.getAddress(), Parameter.ADRESS, map);
		}

		return map;
	}

	/**
	 * Adds the data row element.
	 *
	 * @param value     the value
	 * @param parameter the parameter
	 * @param map       the map
	 */
	private void addDataRowElement(String value, Parameter parameter, Map<Parameter, Object> map) {
		if (StringUtil.isNotNullAndNotEmpty(value)) {
			map.put(parameter, value);
		}
	}

}
