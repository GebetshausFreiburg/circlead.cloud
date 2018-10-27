package org.rogatio.circlead.model.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.rogatio.circlead.model.Parameter;
import org.rogatio.circlead.model.work.Person;
import org.rogatio.circlead.util.StringUtil;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The Class TeamEntry.
 */
public class TeamEntry {

	/** The role identifier. */
	private String roleIdentifier;
	
	/** The needed. */
	private int needed;
	
	/** The level. */
	private String level;
	
	/** The person identifiers. */
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
	 * Gets the role identifier.
	 *
	 * @return the role identifier
	 */
	public String getRoleIdentifier() {
		return roleIdentifier;
	}

	/**
	 * Sets the role identifier.
	 *
	 * @param roleIdentifier the new role identifier
	 */
	public void setRoleIdentifier(String roleIdentifier) {
		this.roleIdentifier = roleIdentifier;
	}

	/**
	 * Gets the needed.
	 *
	 * @return the needed
	 */
	public int getNeeded() {
		return needed;
	}

	/**
	 * Sets the needed.
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
	 * Sets the needed.
	 *
	 * @param needed the new needed
	 */
	public void setNeeded(int needed) {
		this.needed = needed;
	}

	@JsonIgnore
	public boolean containsPerson(Person person) {
		List<String> p = this.getPersons();
		if (p.contains(person.getFullname())) {
			return true;
		}
		return false;
	}
	
	/**
	 * Gets the level.
	 *
	 * @return the level
	 */
	public String getLevel() {
		return level;
	}

	/**
	 * Sets the level.
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
	 * Gets the persons.
	 *
	 * @return the persons
	 */
	public List<String> getPersons() {
		return personIdentifiers;
	}

	/**
	 * Gets the person identifiers.
	 *
	 * @return the person identifiers
	 */
	public List<String> getPersonIdentifiers() {
		List<String> list = new ArrayList<String>();

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

		return list;
	}
	
	/**
	 * Gets the recurrence rule.
	 *
	 * @param personIdentifier the person identifier
	 * @return the recurrence rule
	 */
	@JsonIgnore
	public String getRecurrenceRule(String personIdentifier) {
		if (recurrenceRules.containsKey(personIdentifier)) {
			return recurrenceRules.get(personIdentifier);
		}

		return "";
	}

	/**
	 * Checks for recurrence rule.
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

	/** The comments. */
	@JsonIgnore
	private Map<String, String> comments = new HashMap<String, String>();

	/** The recurrence rules. */
	@JsonIgnore
	private Map<String, String> recurrenceRules = new HashMap<String, String>();

	/**
	 * Sets the recurrence rule match.
	 *
	 * @param personFullname the person fullname
	 * @param value the value
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
