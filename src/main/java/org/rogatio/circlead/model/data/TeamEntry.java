package org.rogatio.circlead.model.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.rogatio.circlead.model.Parameter;
import org.rogatio.circlead.model.work.Person;
import org.rogatio.circlead.util.StringUtil;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

// TODO: Auto-generated Javadoc
/**
 * The Class TeamEntry is a subclass of the
 * {@link org.rogatio.circlead.model.data.TeamDataitem}.
 */
public class TeamEntry {

	/** The identifier of the role which is needed in the team. */
	private String roleIdentifier;

	/** The amount of needed persons to carry the role. */
	private int needed;

	/** The minimum skill level which is needed to fullfill the teamrole. */
	private String level;

	/** The person identifiers. */
	@JsonProperty("persons")
	private List<String> personIdentifiers;

	/**
	 * Instantiates a new team entry.
	 */
	public TeamEntry() {
	}

	/**
	 * Instantiates a new team entry.
	 *
	 * @param dataMap the data map
	 */
	public TeamEntry(Map<String, String> dataMap) {
		this.setRoleIdentifier(dataMap.get(Parameter.ROLE.toString()));
		this.setNeeded(dataMap.get(Parameter.NEEDED.toString()));
		this.setLevel(dataMap.get(Parameter.LEVEL.toString()));
		this.setPersonIdentifiers(dataMap.get(Parameter.PERSON.toString()));
	}

	/**
	 * Gets the identifier of the role.
	 *
	 * @return the role identifier
	 */
	public String getRoleIdentifier() {
		return roleIdentifier;
	}

	/**
	 * Sets the identifier of the team-role. Is mandatory for a TeamEntry. If
	 * roleIdentifier is not set, the TeamRole is invalid.
	 *
	 * @param roleIdentifier the new role identifier
	 */
	public void setRoleIdentifier(String roleIdentifier) {
		this.roleIdentifier = roleIdentifier;
	}

	/**
	 * Gets the amount of needed persons which take the team-role.
	 *
	 * @return the needed
	 */
	public int getNeeded() {
		return needed;
	}

	/**
	 * Sets the amount of needed persons to take the team-role. If string of needed
	 * representation could not be parsed, the needed is set to 0.
	 *
	 * @param needed the new needed
	 */
	public void setNeeded(String needed) {
		try {
			this.needed = Integer.parseInt(needed);
		} catch (Exception e) {
			this.needed = 0;
		}
	}

	/**
	 * Sets the amount of needed persons to take the team-role.
	 *
	 * @param needed the new needed
	 */
	public void setNeeded(int needed) {
		this.needed = needed;
	}

	/**
	 * Contains person.
	 *
	 * @param person the person
	 * @return true, if successful
	 */
	@JsonIgnore
	public boolean containsPerson(Person person) {
		List<String> p = this.getPersons();
		if (p.contains(person.getFullname())) {
			return true;
		}
		return false;
	}

	/**
	 * Gets the minimum skill-level which is needed to fullfill the team-role.
	 *
	 * @return the level
	 */
	public String getLevel() {
		return level;
	}

	/**
	 * Sets the minimum skill-level between 0-100 percent skill.
	 *
	 * @param level the new level
	 */
	public void setLevel(String level) {
		if (StringUtil.isNotNullAndNotEmpty(level)) {
			level = level.trim().replace("%", "");
		}
		this.level = level;
	}

	/**
	 * Gets the persons which hold the team-role as string-representation of
	 * personIdentifiers. Is a calculated list and not part of the
	 * json-data-representation inside the TeamDataitem.
	 *
	 * @return the personIdentifiers which hold the role
	 */
	@JsonIgnore
	public List<String> getPersons() {
		return personIdentifiers;
	}

	/**
	 * Gets the personIdentifiers of all persons which take the team-role of the
	 * entry. If a personal recurrence-rule exists for a person it must follow the
	 * syntax
	 * 
	 * *
	 * 
	 * <pre>
	 * personIdentifier [Rx=...]
	 * </pre>
	 *
	 * <ul>
	 * <li>
	 * 
	 * <pre>
	 * personIdentifier
	 * </pre>
	 * 
	 * Defines person which takes team-role. Is similar to fullname of person. This
	 * i the only mandatory value.
	 * <li>
	 * 
	 * <pre>
	 * Rx=
	 * </pre>
	 * 
	 * CircleadRecurrenceRule as String Representation. Could be Circlead-Standard
	 * starting with R= or Google-RecurrenceRule-Pattern starting with RRULE=
	 * </ul>
	 *
	 * @return the person as list of personIdentifiers with added personal
	 *         recurrence-rule (if set).
	 */
	@JsonProperty("persons")
	public List<String> getPersonIdentifiers() {
		List<String> list = new ArrayList<String>();

		if (this.personIdentifiers != null) {

			for (String person : this.personIdentifiers) {
				StringBuilder sb = new StringBuilder();

				sb.append(person);

				if (this.hasRecurrenceRule(person)) {
					sb.append(" [");
					sb.append(this.getRecurrenceRule(person));
					sb.append("]");
				}

				list.add(sb.toString());
			}
		}

		return list;
	}

	/**
	 * Gets the recurrence rule of a person set by personIdentifier
	 *
	 * @param personIdentifier the person identifier
	 * @return the recurrence rule of the person. If not set empty string "" is
	 *         returned, not null string
	 */
	@JsonIgnore
	public String getRecurrenceRule(String personIdentifier) {
		if (recurrenceRules.containsKey(personIdentifier)) {
			return recurrenceRules.get(personIdentifier);
		}

		return "";
	}

	/**
	 * Checks for personal recurrence rule
	 *
	 * @param personIdentifier the person identifier
	 * @return true, if successful
	 */
	@JsonIgnore
	public boolean hasRecurrenceRule(String personIdentifier) {
		if (recurrenceRules.containsKey(personIdentifier)) {
			return true;
		}
		return false;
	}

	/** The recurrence rules. */
	@JsonIgnore
	private Map<String, String> recurrenceRules = new HashMap<String, String>();

	/**
	 * Sets the recurrence rule match.
	 *
	 * @param personFullname the person fullname
	 * @param value          the value
	 */
	@JsonIgnore
	private void setRecurrenceRuleMatch(String personFullname, String value) {
		value = value.trim();
		if (value.startsWith("R=")) {
			recurrenceRules.put(personFullname, value);
		}
	}

	/**
	 * Sets the person identifiers.
	 *
	 * @param personIdentifiers the new person identifiers
	 */
	@JsonProperty("persons")
	public void setPersonIdentifiers(List<String> personIdentifiers) {
		this.personIdentifiers = personIdentifiers;
	}

	/**
	 * Sets the persons with personIdentifier and their recurrence-rule by
	 * string-representation.
	 *
	 * @param personIdentifiers the new personIdentifiers and optional
	 *                          personal-recurrence rule.
	 */
	@JsonIgnore
	public void setPersonIdentifiers(String personIdentifiers) {

		List<String> list = new ArrayList<String>();

		List<String> persons = StringUtil.toList(personIdentifiers);

		if (persons != null) {
			for (String person : persons) {
				int idx = person.indexOf("[");
				if (idx != -1) {
					String val = person.substring(idx, person.length()).replace("[", "").replace("]", "").trim();
					String fullname = person.substring(0, idx).trim();
					setRecurrenceRuleMatch(fullname, val);
					list.add(fullname);
				} else {
					list.add(person);
				}
			}
		}

		this.personIdentifiers = list;
	}

}
