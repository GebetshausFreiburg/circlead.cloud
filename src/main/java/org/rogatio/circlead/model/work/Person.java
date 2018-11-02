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
import org.rogatio.circlead.control.synchronizer.atlassian.AtlassianSynchronizer;
import org.rogatio.circlead.control.synchronizer.atlassian.parser.PairTableParserElement;
import org.rogatio.circlead.control.synchronizer.atlassian.parser.Parser;
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
import org.rogatio.circlead.util.StringUtil;
import org.rogatio.circlead.view.ISynchronizerRendererEngine;
import org.rogatio.circlead.view.IWorkitemRenderer;
import org.rogatio.circlead.view.SvgBuilder;

/**
 * The Class Person.
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
	 * Sets the team fraction.
	 *
	 * @param fte the new team fraction
	 */
	public void setTeamFraction(double fte) {
		this.getDataitem().setTeamFraction(fte);
	}

	/**
	 * Gets the organisational workload.
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
	 * Gets the team workload.
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

		// System.out.println("dt"+divider);

		double teamAllocation = R.getAverageAllokationInTeams(this, Freq.WEEKLY);

		// teamAllocation = 10;

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
	 * Sets the team fraction.
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
	 * Sets the full time equivalent.
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
	 * Gets the firstname.
	 *
	 * @return the firstname
	 */
	public String getFirstname() {
		return this.getDataitem().getFirstname();
	}

	/**
	 * Gets the secondname.
	 *
	 * @return the secondname
	 */
	public String getSecondname() {
		return this.getDataitem().getSecondname();
	}
	
	/**
	 * Takes role.
	 *
	 * @param roleIdentifier the role identifier
	 * @return true, if successful
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
			// Merge Timeslices of small taken roles to optimize view
			if (map.size() > 3) {
				// Sort timeslice-dataets by size
				Map<String, List<Timeslice>> dmap = ObjectUtil.sort(map);
				map = new LinkedHashMap<String, List<Timeslice>>();
				Map<String, List<Timeslice>> tmap = new LinkedHashMap<String, List<Timeslice>>();

				// hold biggest three slice-datasets and merge rest
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

		if (synchronizer.getClass().getSimpleName().equals(FileSynchronizer.class.getSimpleName())) {
			element.append(SvgBuilder.createPersonDnaProfile(this, 512).toString());
		}

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

		renderer.addH2(element, ROLESINORGANISATION.toString() + add);

		List<Role> orgRoles = R.getOrganisationalRolesWithPerson(this.getFullname());
		renderer.addRoleList(element, orgRoles, this);

		List<Team> foundTeams = R.getTeamsWithMember(this);
		if (ObjectUtil.isListNotNullAndEmpty(foundTeams)) {
			double sum = R.getAverageAllokationInTeams(this, Freq.WEEKLY);

			double tw = this.getTeamWorkload();
			String teamWorkload = "";
			if (tw != 0.0) {
				teamWorkload = ", " + Math.round(tw) + "%";
			}

			renderer.addH2(element, ROLESINTEAM.toString() + " (" + Math.round(sum) + "h/Woche" + teamWorkload + ")");
			Element ul = element.appendElement("ul");
			for (Team team : foundTeams) {
				Element li = ul.appendElement("li");
				String c = "";
				if (StringUtil.isNotNullAndNotEmpty(team.getCategory())) {
					c = " (" + team.getCategory() + ")";
				}

				if (synchronizer.getClass().getSimpleName().equals(AtlassianSynchronizer.class.getSimpleName())) {
					li.append("<ac:link><ri:page ri:content-title=\"" + team.getTitle()
							+ "\" ri:version-at-save=\"1\"/><ac:plain-text-link-body><![CDATA[" + team.getTitle() + ""
							+ c + "]]></ac:plain-text-link-body></ac:link>");
				} else if (synchronizer.getClass().getSimpleName().equals(FileSynchronizer.class.getSimpleName())) {
					li.appendElement("a").attr("href", "../web/" + team.getId(synchronizer) + ".html")
							.appendText(team.getTitle() + c);
				}

				WorkitemStatusParameter status = WorkitemStatusParameter.get(team.getStatus());
				if (status != null) {
					Element s = Parser.getStatus(status.getName());
					li.append("&nbsp;");
					s.appendTo(li);
				}

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

		addRessourceChart(synchronizer, element);

		return element;
	}

	/**
	 * Adds the ressource chart.
	 *
	 * @param synchronizer the synchronizer
	 * @param element the element
	 */
	private void addRessourceChart(ISynchronizer synchronizer, Element element) {
		ISynchronizerRendererEngine renderer = synchronizer.getRenderer();

		if (synchronizer.getClass().getSimpleName().equals(AtlassianSynchronizer.class.getSimpleName())) {
			final Map<String, List<Timeslice>> map = this.getOrganisationalTimeslices(Freq.MONTHLY, true);

			String colors = "";
			String colorArray[] = { "#989898", "#A8A8A8", "#B8B8B8", "#C0C0C0" };

			for (int i = 0; i < map.size(); i++) {
				if (colors == null) {
					colors = new String("");
				}
				colors += colorArray[i];

				if ((i + 1) < map.size()) {
					colors += ", ";
				}
			}

			Map<String, List<Timeslice>> m = this.getTeamTimeslices(Freq.MONTHLY);
			m.forEach((k, v) -> map.put(k, v));

			Element chart = Parser.addChartMacro("", "h/Monat", "Monat", colors, map);
			if (chart != null) {
				renderer.addH2(element, "Ressourcenallokation");
				chart.appendTo(element);
			}
		}
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
				if (renderer.getSynchronizer().getClass().getSimpleName()
						.equals(AtlassianSynchronizer.class.getSimpleName())) {
					listElement.appendElement("ac:link").append(
							"<ri:page ri:content-title=\"" + role.getTitle() + "\" ri:version-at-save=\"1\" />");
				} else if (renderer.getSynchronizer().getClass().getSimpleName()
						.equals(FileSynchronizer.class.getSimpleName())) {
					listElement.appendElement("a")
							.attr("href", "../web/" + role.getId(renderer.getSynchronizer()) + ".html")
							.appendText(role.getTitle());
				}
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

//		listElement.appendText(""+ team.getAverageAllokation(this, Freq.WEEKLY) );
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

		ArrayList<Role> roles = R.getOrganisationalRolesWithPerson(this.getFullname());
		if (roles.size() == 0) {
			ValidationMessage m = new ValidationMessage(this);
			m.error("Person has no role", "Person '" + this.getFullname() + "' has no related role");
			messages.add(m);
		}

		List<Team> foundTeams = R.getTeamsWithMember(this);
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.rogatio.circlead.model.data.IDataRow#getDataRow()
	 */
	@Override
	public Map<Parameter, Object> getDataRow() {
		Map<Parameter, Object> map = new TreeMap<Parameter, Object>();

//		addDataRowElement(this.getTitle(), Parameter.TITLE, map);
		addDataRowElement(this.getFirstname(), Parameter.FIRSTNAME, map);
		addDataRowElement(this.getSecondname(), Parameter.SECONDNAME, map);
		addDataRowElement(this.getFamilyname(), Parameter.FAMILYNAME, map);
		addDataRowElement(this.getFirstname(), Parameter.FIRSTNAME, map);

		ContactDataitem cdi = this.getFirstPrivateContact();
		addDataRowElement(cdi.getMail(), Parameter.MAIL, map);
		addDataRowElement(cdi.getMobile(), Parameter.MOBILE, map);
		addDataRowElement(cdi.getPhone(), Parameter.PHONE, map);
		addDataRowElement(cdi.getAddress(), Parameter.ADRESS, map);

		/*
		 * Map<String, String> d = this.getData(); if (d != null) { if (d.size() > 0) {
		 * for (String key : d.keySet()) { String value = d.get(key);
		 * addDataRowElement(value, key, map); } } }
		 */

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
