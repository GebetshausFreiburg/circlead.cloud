/*
 * Circlead - Develop and structure evolutionary Organisations
 * 
 * @author Matthias Wegner
 * @version 0.1
 * @since 01.07.2018
 * 
 */
package org.rogatio.circlead.model.work;

import static org.rogatio.circlead.model.Parameter.CATEGORY;
import static org.rogatio.circlead.model.Parameter.DESCRIPTION;
import static org.rogatio.circlead.model.Parameter.RECURRENCERULE;
import static org.rogatio.circlead.model.Parameter.SUBTYPE;
import static org.rogatio.circlead.model.Parameter.TYPE;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.TreeMap;

import org.dmfs.rfc5545.DateTime;
import org.dmfs.rfc5545.recur.Freq;
import org.jsoup.nodes.Element;
import org.rogatio.circlead.control.CircleadRecurrenceRule;
import org.rogatio.circlead.control.synchronizer.ISynchronizer;
import org.rogatio.circlead.control.synchronizer.atlassian.parser.HeaderTableParserElement;
import org.rogatio.circlead.control.synchronizer.atlassian.parser.Parser;
import org.rogatio.circlead.control.validator.IValidator;
import org.rogatio.circlead.control.validator.ValidationMessage;
import org.rogatio.circlead.model.Parameter;
import org.rogatio.circlead.model.WorkitemStatusParameter;
import org.rogatio.circlead.model.data.IDataRow;
import org.rogatio.circlead.model.data.IDataitem;
import org.rogatio.circlead.model.data.TeamDataitem;
import org.rogatio.circlead.model.data.TeamEntry;
import org.rogatio.circlead.model.data.Timeslice;
import org.rogatio.circlead.util.ObjectUtil;
import org.rogatio.circlead.util.PropertyUtil;
import org.rogatio.circlead.util.StringUtil;
import org.rogatio.circlead.view.ISynchronizerRendererEngine;
import org.rogatio.circlead.view.IWorkitemRenderer;

/**
 * The Class Team is the working item for a Team.
 *
 * @author Matthias Wegner
 */
public class Team extends DefaultWorkitem implements IWorkitemRenderer, IValidator, IDataRow {

	/**
	 * Instantiates a new team.
	 */
	public Team() {
		this.dataitem = new TeamDataitem();
	}

	/**
	 * Instantiates a new team.
	 *
	 * @param dataitem the dataitem of the team of class
	 *                 {@link org.rogatio.circlead.model.data.TeamDataitem}
	 */
	public Team(IDataitem dataitem) {
		super(dataitem);

		if (!(dataitem instanceof TeamDataitem)) {
			throw new IllegalArgumentException("IDataitem must be of type TeamDataitem");
		}
	}

	/**
	 * Sets the recurrence rule.
	 *
	 * @param recurrenceRule the new recurrence rule
	 */
	public void setRecurrenceRule(String recurrenceRule) {
		this.getDataitem().setRecurrenceRule(recurrenceRule);
	}

	/**
	 * Sets the team type.
	 *
	 * @param type the new team type
	 */
	public void setTeamType(String type) {
		this.getDataitem().setType(type);
	}

	/**
	 * Gets the team type.
	 *
	 * @return the team type
	 */
	public String getTeamType() {
		return this.getDataitem().getType();
	}

	/**
	 * Sets the team subtype.
	 *
	 * @param subtype the new team subtype
	 */
	public void setTeamSubtype(String subtype) {
		this.getDataitem().setSubtype(subtype);
	}

	/**
	 * Gets the team subtype.
	 *
	 * @return the team subtype
	 */
	public String getTeamSubtype() {
		return this.getDataitem().getSubtype();
	}

	/**
	 * Sets the end date of the team-event. Is optional.
	 *
	 * @param end the new end
	 */
	public void setEnd(String end) {
		this.getDataitem().setEnd(end);
	}

	/**
	 * Gets the end date of the team-event.
	 *
	 * @return the end date
	 */
	public String getEnd() {
		return this.getDataitem().getEnd();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.rogatio.circlead.model.work.DefaultWorkitem#getReferencedItems()
	 */
	@Override
	public List<IWorkitem> getReferencedItems() {
		List<IWorkitem> references = new ArrayList<IWorkitem>();
		List<TeamEntry> x = getTeamEntries();
		if (ObjectUtil.isListNotNullAndEmpty(x)) {
			for (TeamEntry e : x) {
				List<String> pi = e.getPersons();
				if (ObjectUtil.isListNotNullAndEmpty(pi)) {
					for (String p : pi) {
						Person person = R.getPerson(p);
						if (person != null) {
							if (!references.contains(person)) {
								references.add(person);
							}
						}
					}
				}
				String ri = e.getRoleIdentifier();
				if (StringUtil.isNotNullAndNotEmpty(ri)) {
					Role role = R.getRole(ri);
					if (role != null) {
						if (!references.contains(role)) {
							references.add(role);
						}
					}
				}
			}
		}
		return references;
	}

	/**
	 * Sets the start date of the team-event. Is optional.
	 *
	 * @param start the new start
	 */
	public void setStart(String start) {
		this.getDataitem().setStart(start);
	}

	/**
	 * Gets the start date of the team-event.
	 *
	 * @return the start
	 */
	public String getStart() {
		return this.getDataitem().getStart();
	}
	
	/**
	 * Gets the duration between the start and end-date in days.
	 *
	 * @return the duration
	 */
	public int getDuration() {
		return (int)((this.getEndDate().getTime() - this.getStartDate().getTime())/ (24*60*60*1000));
	}
	
	/**
	 * Gets the end date. If no date is set it is 12 months after start date.
	 *
	 * @return the end date
	 */
	public Date getEndDate() {
		if (this.getDataitem().getEnd()!=null) {
			DateTime s = DateTime.parse(this.getDataitem().getEnd());
			SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd");
			try {
				return f.parse(s.toString());
			} catch (ParseException e) {
			}
		}
		
		Calendar c = Calendar.getInstance();
		c.setTime(this.getStartDate());
		c.add(Calendar.MONTH, 12);
		
		return c.getTime();
	}
	
	/**
	 * Gets the start date. If not set it is set to today
	 *
	 * @return the start date
	 */
	public Date getStartDate() {
		if (this.getDataitem().getStart()!=null) {
			DateTime s = DateTime.parse(this.getDataitem().getStart());
			SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd");
			try {
				return f.parse(s.toString());
			} catch (ParseException e) {
			}
		}
		
		return new Date();
	}

	/**
	 * Sets the category.
	 *
	 * @param category the new category
	 */
	public void setCategory(String category) {
		this.getDataitem().setCategory(category);
	}

	/**
	 * Gets the category.
	 *
	 * @return the category
	 */
	public String getCategory() {
		return this.getDataitem().getCategory();
	}

	/**
	 * Gets the circlead recurrence rule.
	 *
	 * @return the circlead recurrence rule
	 */
	public CircleadRecurrenceRule getCircleadRecurrenceRule() {

		if (StringUtil.isNotNullAndNotEmpty(getRecurrenceRule())) {
			CircleadRecurrenceRule crr = new CircleadRecurrenceRule(getRecurrenceRule());

			// Set start-date, if not null
			if (StringUtil.isNotNullAndNotEmpty(this.getStart())) {
				crr.setStartDate(this.getStart());
			}

			// Set end-date, if not null
			if (StringUtil.isNotNullAndNotEmpty(this.getEnd())) {
				crr.setUntilDate(this.getEnd());
			}

			return crr;

		}

		return null;
	}

	/**
	 * Gets the recurrence rule.
	 *
	 * @return the recurrence rule
	 */
	public String getRecurrenceRule() {
		return this.getDataitem().getRecurrenceRule();
	}

	/**
	 * Gets the data row.
	 *
	 * @return the data row
	 */
	/* (non-Javadoc)
	 * @see org.rogatio.circlead.model.data.IDataRow#getDataRow()
	 */
	@Override
	public Map<Parameter, Object> getDataRow() {
		Map<Parameter, Object> map = new TreeMap<Parameter, Object>();

		addDataRowElement(this.getTitle(), Parameter.TITLE, map);
		addDataRowElement(this.getTeamType(), Parameter.TYPE, map);
		addDataRowElement(this.getTeamSubtype(), Parameter.SUBTYPE, map);
		addDataRowElement(this.getStart(), Parameter.STARTDATE, map);
		addDataRowElement(this.getEnd(), Parameter.ENDDATE, map);
		addDataRowElement(this.getTeamMembers(), Parameter.PERSONS, map);
		addDataRowElement(this.getStatus(), Parameter.STATUS, map);
		
		List<Timeslice> slices = this.getAllokationSlices(Freq.MONTHLY);
		for (Timeslice timeslice : slices) {
			Parameter p = Parameter.get(timeslice.getSliceStart());
			if (p!=null) {
				p.setDetail(timeslice.getSliceStart());
				addDataRowElement(""+((int)timeslice.getAllokation()), p, map);	
			}
		}
	
		return map;
	}

	/**
	 * Adds the data row element.
	 *
	 * @param value the value
	 * @param parameter the parameter
	 * @param map the map
	 */
	private void addDataRowElement(String value, Parameter parameter, Map<Parameter, Object> map) {
		if (StringUtil.isNotNullAndNotEmpty(value)) {
			map.put(parameter, value);
		}
	}

	/**
	 * Adds the data row element.
	 *
	 * @param values the values
	 * @param parameter the parameter
	 * @param map the map
	 */
	private void addDataRowElement(List<String> values, Parameter parameter, Map<Parameter, Object> map) {
		if (ObjectUtil.isListNotNullAndEmpty(values)) {
			StringBuilder sb = new StringBuilder();
			for (String value : values) {
				sb.append(" - ").append(value).append("\n");
			}

			map.put(parameter, sb.toString());
		}
	}

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return this.getDataitem().getDescription();
	}

	/**
	 * Sets the description.
	 *
	 * @param description the new description
	 */
	public void setDescription(String description) {
		this.getDataitem().setDescription(description);
	}

	/**
	 * Sets the team-entries from html-table-parser. Only add team-entry if
	 * roleodentifier is named
	 *
	 * @param table the new team table
	 */
	public void setTeamTable(HeaderTableParserElement table) {
		List<Map<String, String>> dataList = table.getDataList();
		if (dataList.size() > 0) {
			List<TeamEntry> teams = new ArrayList<TeamEntry>();
			for (Map<String, String> dataMap : dataList) {
				TeamEntry entry = new TeamEntry(dataMap);
				if (StringUtil.isNotNullAndNotEmpty(entry.getRoleIdentifier())) {
					teams.add(entry);
				}
			}
			this.getDataitem().setTeamEntries(teams);
		}

	}

	/**
	 * Adds the team enty.
	 *
	 * @param teamEntry the team entry
	 */
	public void addTeamEnty(TeamEntry teamEntry) {
		this.getDataitem().addTeamEntry(teamEntry);
	}

	/**
	 * Gets the team entries.
	 *
	 * @return the team entries
	 */
	public List<TeamEntry> getTeamEntries() {
		return this.getDataitem().getTeams();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.rogatio.circlead.model.work.DefaultWorkitem#getDataitem()
	 */
	@Override
	public TeamDataitem getDataitem() {
		return (TeamDataitem) dataitem;
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

	/**
	 * Gets the team size.
	 *
	 * @return the team size
	 */
	public int getTeamSize() {
		return getTeamMembers().size();
	}

	/**
	 * Gets the team redundance. The needs in the team are used to calculate if the
	 * team has fullfilled roles or not. 100% mean that every role is only be taken
	 * by one person. Ideal a team has >200%
	 *
	 * @return the team redundance
	 */
	public double getRedundance() {
		double sum = 0;
		List<TeamEntry> list = this.getTeamEntries();
		if (ObjectUtil.isListNotNullAndEmpty(list)) {
			for (TeamEntry teamEntry : list) {
				List<String> p = teamEntry.getPersons();
				if (ObjectUtil.isListNotNullAndEmpty(p)) {
					if (teamEntry.getNeeded() > 0.0) {
						sum += ((double) p.size()) / ((double) teamEntry.getNeeded());
					} else {
						sum += ((double) p.size());
					}
				}
			}
			sum = sum / (double) list.size();
		}
		return sum;
	}

	/**
	 * Gets the team members which take a named role.
	 *
	 * @param role the role
	 * @return the team members
	 */
	public List<String> getTeamMembers(Role role) {
		List<String> personIdentifiers = new ArrayList<String>();
		List<TeamEntry> list = this.getTeamEntries();
		for (TeamEntry teamEntry : list) {
			if (teamEntry.getRoleIdentifier().equals(role.getTitle())) {
				List<String> p = teamEntry.getPersons();
				for (String pi : p) {
					if (!personIdentifiers.contains(pi)) {
						personIdentifiers.add(pi);
					}
				}
			}
		}
		return personIdentifiers;
	}

	/**
	 * Gets the readable rule.
	 *
	 * @return the readable rule
	 */
	public String getReadableRule() {
		CircleadRecurrenceRule crr = new CircleadRecurrenceRule(this.getRecurrenceRule());
		return crr.getReadableRule();
	}

	/**
	 * Gets the roles.
	 *
	 * @param person the person
	 * @return the roles
	 */
	public List<String> getRoles(Person person) {
		List<String> roles = new ArrayList<String>();
		List<TeamEntry> list = this.getTeamEntries();
		for (TeamEntry teamEntry : list) {
			List<String> p = teamEntry.getPersons();
			for (String pi : p) {
				if (person.getFullname().equalsIgnoreCase(pi)) {
					if (!roles.contains(teamEntry.getRoleIdentifier())) {
						roles.add(teamEntry.getRoleIdentifier());
					}
				}
			}
		}
		return roles;
	}

	/**
	 * Count the number of team members.
	 *
	 * @return the team members
	 */
	public List<String> getTeamMembers() {
		List<String> personIdentifiers = new ArrayList<String>();
		List<TeamEntry> list = this.getTeamEntries();
		for (TeamEntry teamEntry : list) {
			List<String> p = teamEntry.getPersons();
			for (String pi : p) {
				if (!personIdentifiers.contains(pi)) {
					personIdentifiers.add(pi);
				}
			}
		}
		return personIdentifiers;
	}

	/**
	 * Render.
	 *
	 * @param synchronizer the synchronizer
	 * @return the element
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.rogatio.circlead.view.IRenderer#render()
	 */
	@Override
	public Element render(ISynchronizer synchronizer) {
		ISynchronizerRendererEngine renderer = synchronizer.getRenderer();

		Element element = new Element("p");

//		if (StringUtil.isNotNullAndNotEmpty(this.getCategory())) {
//			if (this.getTeamSize() < 2) {
//				renderer.addStatus(element, "Internal");
//			} else {
//				renderer.addStatus(element, "External");
//			}
//		}

		if (StringUtil.isNotNullAndNotEmpty(this.getCategory())) {
			renderer.addItem(element, CATEGORY.toString(), this.getCategory());
		}
		if (StringUtil.isNotNullAndNotEmpty(this.getTeamType())) {
			renderer.addItem(element, TYPE.toString(), this.getTeamType());
		}
		if (StringUtil.isNotNullAndNotEmpty(this.getTeamSubtype())) {
			renderer.addItem(element, SUBTYPE.toString(), this.getTeamSubtype());
		}
		if (StringUtil.isNotNullAndNotEmpty(this.getRecurrenceRule())) {
			renderer.addItem(element, RECURRENCERULE.toString(), this.getRecurrenceRule());
		}

		renderer.addItem(element, "Mitglieder", this.getTeamSize() + "");
		renderer.addItem(element, "Stabilität", ((int) (this.getRedundance() * 100)) + "%");

		if (StringUtil.isNotNullAndNotEmpty(this.getDescription())) {
			renderer.addH2(element, DESCRIPTION.toString());
			renderer.addItem(element, this.getDescription());
		}

		if (ObjectUtil.isListNotNullAndEmpty(this.getTeamEntries())) {
			Element table = Parser.createTeamEntryTable(this.getTeamEntries(), synchronizer, true);
			table.appendTo(element);
		}

		if (this.getCategory() != null) {
			if (this.getCategory().equals(PropertyUtil.getInstance().getApplicationDefaultTeamcategory())) {
				if (StringUtil.isNotNullAndNotEmpty(PropertyUtil.getInstance().getApplicationDefaultTeamMessage())) {
					String m = PropertyUtil.getInstance().getApplicationDefaultTeamMessage(); 
					Element p = element.appendElement("p");
					p.appendText(m);
				}
			}
		}

		return element;
	}

	/**
	 * Gets the teamrole match. Calculates for every role if needed minimum ist
	 * follfilled by persons which takes role
	 *
	 * @return the teamrole match
	 */
	public Map<String, Integer> getTeamroleMatch() {
		Map<String, Integer> map = new HashMap<String, Integer>();
		List<TeamEntry> entries = this.getTeamEntries();
		for (TeamEntry teamEntry : entries) {
			if (ObjectUtil.isListNotNullAndEmpty(teamEntry.getPersons())) {
				if (teamEntry.getPersons().size() < teamEntry.getNeeded()) {
					int diff = -teamEntry.getNeeded() + teamEntry.getPersons().size();
					map.put(teamEntry.getRoleIdentifier(), diff);
				}
			} else {
				map.put(teamEntry.getRoleIdentifier(), -teamEntry.getNeeded());
			}
		}
		return map;
	}

	/**
	 * Validate.
	 *
	 * @return the list
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.rogatio.circlead.control.IValidator#validate()
	 */
	@Override
	public List<ValidationMessage> validate() {
		List<ValidationMessage> messages = new ArrayList<ValidationMessage>();

		if (StringUtil.isNotNullAndNotEmpty(this.getEnd())) {
			DateTime end = DateTime.parse(TimeZone.getDefault(), this.getEnd());
			Date d = CircleadRecurrenceRule.convertDate(end);

			WorkitemStatusParameter status = WorkitemStatusParameter.get(this.getStatus());
			if (status != null) {
				if (d.before(new Date()) && status != WorkitemStatusParameter.CLOSED) {
					ValidationMessage m = new ValidationMessage(this);
					m.warning("Teamevent deprecated",
							"Team '" + this.getTitle() + "' should be closed, because EndDate is before today");
					messages.add(m);
				}
			}
		}

		List<TeamEntry> entries = this.getTeamEntries();
		for (TeamEntry teamEntry : entries) {
			if (ObjectUtil.isListNotNullAndEmpty(teamEntry.getPersons())) {

				for (String identifier : teamEntry.getPersons()) {
					Person p = R.getPerson(identifier);
					if (p == null) {
						ValidationMessage m = new ValidationMessage(this);
						m.warning("Person not idenfied",
								"Person '" + identifier + "' for role '" + teamEntry.getRoleIdentifier() + "' in Team '"
										+ this.getTitle() + "' could not be identified");
						messages.add(m);
					}
				}

				if (teamEntry.getPersons().size() < teamEntry.getNeeded()) {
					ValidationMessage m = new ValidationMessage(this);
					m.warning("Teamrole not completly set",
							"Teamrole '" + teamEntry.getRoleIdentifier() + "' in Team '" + this.getTitle() + "' needs "
									+ teamEntry.getNeeded() + "persons and has only " + teamEntry.getPersons().size());
					messages.add(m);
				}
			} else {
				ValidationMessage m = new ValidationMessage(this);
				m.error("Teamrole empty", "Teamrole '" + teamEntry.getRoleIdentifier() + "' in Team '" + this.getTitle()
						+ "' needs " + teamEntry.getNeeded() + " persons");
				messages.add(m);
			}
		}

		return messages;
	}

	/**
	 * Gets the circlead recurrence rule.
	 *
	 * @param e                the e
	 * @param personIdentifier the person identifier
	 * @return the circlead recurrence rule
	 */
	private CircleadRecurrenceRule getCircleadRecurrenceRule(TeamEntry e, String personIdentifier) {
		String rule = e.getRecurrenceRule(personIdentifier);
		CircleadRecurrenceRule crr = new CircleadRecurrenceRule(rule);
		if (this.getCircleadRecurrenceRule() != null) {
			CircleadRecurrenceRule cx = this.getCircleadRecurrenceRule();
			if (cx.getStartDate() != null) {
				crr.setStartDate(cx.getStartDate().toString());
			}
			if (cx.getUntilDate() != null) {
				crr.setUntilDate(cx.getUntilDate().toString());
			}
		}
		return crr;
	}

	/**
	 * Gets the allokation slices.
	 *
	 * @param freq the freq
	 * @return the allokation slices
	 */
	public List<Timeslice> getAllokationSlices(Freq freq) {
		List<Timeslice> slices = null;
		List<String> personIdentifiers = this.getTeamMembers();
		for (String personIdentifier : personIdentifiers) {
			Person p = R.getPerson(personIdentifier);
			if (p != null) {
				List<Timeslice> tempSlices = getAllokationSlices(p, freq);
				slices = ObjectUtil.merge(tempSlices, slices);
			}
		}
		return slices;
	}

	/**
	 * Gets the allokation slices.
	 *
	 * @param person the person
	 * @param freq   the freq
	 * @return the allokation slices
	 */
	public List<Timeslice> getAllokationSlices(Person person, Freq freq) {
		// System.out.println(rule.getAllokationSlices(Freq.WEEKLY));
		List<Timeslice> slices = null;
		List<TeamEntry> x = getTeamEntries();

		if (x == null) {
			CircleadRecurrenceRule crr = this.getCircleadRecurrenceRule();
			if (crr != null) {
				slices = crr.getAllokationSlices(freq);
				return slices;
			} else {
				return null;
			}
		}

		int counter = 0;
		for (TeamEntry e : x) {
			if (e.getPersons().contains(person.getFullname())) {
				counter++;
				// Use as default team-recurrencerule, but if person has own rule then overwrite
				// default
				if (e.hasRecurrenceRule(person.getFullname())) {
					CircleadRecurrenceRule crr = getCircleadRecurrenceRule(e, person.getFullname());
//					String rule = e.getRecurrenceRule(person.getFullname());
//					CircleadRecurrenceRule crr = new CircleadRecurrenceRule(rule);
//					if (this.getCircleadRecurrenceRule() != null) {
//						CircleadRecurrenceRule cx = this.getCircleadRecurrenceRule();
//						if (cx.getStartDate() != null) {
//							crr.setStartDate(cx.getStartDate().toString());
//						}
//						if (cx.getUntilDate() != null) {
//							crr.setUntilDate(cx.getUntilDate().toString());
//						}
//					}
					List<Timeslice> tempSlices = crr.getAllokationSlices(freq);
					slices = ObjectUtil.merge(tempSlices, slices);
				} else if (StringUtil.isNotNullAndNotEmpty(this.getRecurrenceRule())) {
					CircleadRecurrenceRule crr = this.getCircleadRecurrenceRule();
					List<Timeslice> tempSlices = crr.getAllokationSlices(freq);
					slices = ObjectUtil.merge(tempSlices, slices);
				}
			}
		}

		if (counter == 0) {
			CircleadRecurrenceRule crr = new CircleadRecurrenceRule(getRecurrenceRule());
			slices = crr.getAllokationSlices(freq);
			return slices;
		}

		slices = ObjectUtil.divideSlices(slices, counter);

		return slices;
	}

	/**
	 * Gets the average allokation.
	 *
	 * @param person the person
	 * @param freq   the freq
	 * @return the average allokation
	 */
	public double getAverageAllokation(Person person, Freq freq) {
		double sum = 0;
		List<TeamEntry> teamEntries = getTeamEntries();

		// if no entry found, the use average allocation from team recurrence-rule
		if (teamEntries == null) {
			CircleadRecurrenceRule crr = this.getCircleadRecurrenceRule();
			if (crr != null) {
				double allok = crr.getAverageAllokation(freq);
				return allok;
			} else {
				return 0;
			}
		}

		int counter = 0;
		// if entries found, then calculate average allocation by person, because one
		// person can take more than one role in a team
		for (TeamEntry entry : teamEntries) {
			if (entry.getPersons().contains(person.getFullname())) {
				counter++;
				// if recurrencerule for person is set, the use it. When not use default of team
				if (entry.hasRecurrenceRule(person.getFullname())) {
					CircleadRecurrenceRule crr = getCircleadRecurrenceRule(entry, person.getFullname());
//					String rule = entry.getRecurrenceRule(person.getFullname());
//					CircleadRecurrenceRule crr = new CircleadRecurrenceRule(rule);
					double allok = crr.getAverageAllokation(freq);
					sum += allok;
				} else if (StringUtil.isNotNullAndNotEmpty(this.getRecurrenceRule())) {
					CircleadRecurrenceRule crr = new CircleadRecurrenceRule(getRecurrenceRule());
					double allok = crr.getAverageAllokation(freq);
					sum += allok;
				}
			}
		}

		if (counter == 0) {
			CircleadRecurrenceRule crr = new CircleadRecurrenceRule(getRecurrenceRule());
			double allok = crr.getAverageAllokation(freq);
			return allok;
		}

		return sum / ((double) counter);
	}

}
