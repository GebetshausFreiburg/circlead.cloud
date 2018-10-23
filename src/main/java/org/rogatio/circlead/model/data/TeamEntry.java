package org.rogatio.circlead.model.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.rogatio.circlead.model.Parameter;
import org.rogatio.circlead.util.StringUtil;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class TeamEntry {

	private String roleIdentifier;
	private int needed;
	private String level;
	private List<String> personIdentifiers;

	public TeamEntry() {
	}

	public TeamEntry(Map<String, String> dataMap) {
		this.setRoleIdentifier(dataMap.get(Parameter.ROLE.toString()));
		this.setNeeded(dataMap.get(Parameter.NEEDED.toString()));
		this.setLevel(dataMap.get(Parameter.LEVEL.toString()));
		this.setPersonIdentifiers(dataMap.get(Parameter.PERSON.toString()));
	}

	public String getRoleIdentifier() {
		return roleIdentifier;
	}

	public void setRoleIdentifier(String roleIdentifier) {
		this.roleIdentifier = roleIdentifier;
	}

	public int getNeeded() {
		return needed;
	}

	public void setNeeded(String needed) {
		try {
			this.needed = Integer.parseInt(needed);
		} catch (Exception e) {
			this.needed = 0;
		}
	}

	public void setNeeded(int needed) {
		this.needed = needed;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		if (StringUtil.isNotNullAndNotEmpty(level)) {
			level = level.trim().replace("%", "");
		}
		this.level = level;
	}

	public List<String> getPersons() {
		return personIdentifiers;
	}

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

	@JsonIgnore
	public String getRecurrenceRule(String personIdentifier) {
		if (recurrenceRules.containsKey(personIdentifier)) {
			return recurrenceRules.get(personIdentifier);
		}

		return "";
	}

	@JsonIgnore
	public boolean hasRecurrenceRule(String personIdentifier) {
		if (recurrenceRules.containsKey(personIdentifier)) {
			return true;
		}
		return false;
	}

	@JsonIgnore
	private Map<String, String> comments = new HashMap<String, String>();

	@JsonIgnore
	private Map<String, String> recurrenceRules = new HashMap<String, String>();

	@JsonIgnore
	private void setRecurrenceRuleMatch(String personFullname, String value) {
		value = value.trim();
		if (value.startsWith("R=")) {
			recurrenceRules.put(personFullname, value);
		}
	}

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
