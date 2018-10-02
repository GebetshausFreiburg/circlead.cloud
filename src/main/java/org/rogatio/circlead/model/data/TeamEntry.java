package org.rogatio.circlead.model.data;

import static org.rogatio.circlead.model.Parameter.ROLE;

import java.util.List;
import java.util.Map;

import org.rogatio.circlead.model.Parameter;
import org.rogatio.circlead.util.StringUtil;

public class TeamEntry {

	private String roleIdentifier;
	private int needed;
	private String level;
	private List<String> personIdentifiers;

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

	public List<String> getPersonIdentifiers() {
		return personIdentifiers;
	}

	public void setPersonIdentifiers(String personIdentifiers) {
		this.personIdentifiers = StringUtil.toList(personIdentifiers);
	}

}
