/*
 * Circlead - Develop and structure evolutionary Organisations
 * 
 * @author Matthias Wegner
 * @version 0.1
 * @since 01.07.2018
 * 
 */
package org.rogatio.circlead.model.data;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kjetland.jackson.jsonSchema.annotations.JsonSchemaDescription;
import com.kjetland.jackson.jsonSchema.annotations.JsonSchemaTitle;

/**
 * The Class ActivityDataitem.
 */
public class TeamDataitem extends DefaultDataitem {

	/** The responsible. */
	@JsonSchemaTitle("Description")
	@JsonSchemaDescription("Description of the team")
	private String description;

	private String recurrenceRule;

	private String category;
	private String type;
	private String subtype;

	private List<TeamEntry> teams = new ArrayList<TeamEntry>();

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description.
	 *
	 * @param description the new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	public List<TeamEntry> getTeamEntries() {
		return teams;
	}

	public void setTeamEntries(List<TeamEntry> teams) {
		this.teams = teams;
	}
	
	@JsonIgnore
	public void addTeamEntry(TeamEntry teamEntry) {
		this.teams.add(teamEntry);
	}

	public String getRecurrenceRule() {
		return recurrenceRule;
	}

	public void setRecurrenceRule(String recurrenceRule) {
		if (recurrenceRule != null) {
			this.recurrenceRule = recurrenceRule.toUpperCase().trim();
		}
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSubtype() {
		return subtype;
	}

	public void setSubtype(String subtype) {
		this.subtype = subtype;
	}

	public List<TeamEntry> getTeams() {
		return teams;
	}

	public void setTeams(List<TeamEntry> teams) {
		this.teams = teams;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

}
