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

	/** The recurrence rule. */
	private String recurrenceRule;

	/** The category. */
	private String category;
	
	/** The type. */
	private String type;
	
	/** The subtype. */
	private String subtype;
	
	/** The start. */
	private String start;
	
	/** The end. */
	private String end;

	/** The teams. */
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

	/**
	 * Gets the team entries.
	 *
	 * @return the team entries
	 */
	public List<TeamEntry> getTeamEntries() {
		return teams;
	}

	/**
	 * Sets the team entries.
	 *
	 * @param teams the new team entries
	 */
	public void setTeamEntries(List<TeamEntry> teams) {
		this.teams = teams;
	}
	
	/**
	 * Adds the team entry.
	 *
	 * @param teamEntry the team entry
	 */
	@JsonIgnore
	public void addTeamEntry(TeamEntry teamEntry) {
		this.teams.add(teamEntry);
	}
	
	/**
	 * Gets the recurrence rule.
	 *
	 * @return the recurrence rule
	 */
	public String getRecurrenceRule() {
		return recurrenceRule;
	}

	/**
	 * Sets the recurrence rule.
	 *
	 * @param recurrenceRule the new recurrence rule
	 */
	public void setRecurrenceRule(String recurrenceRule) {
		if (recurrenceRule != null) {
			this.recurrenceRule = recurrenceRule.toUpperCase().trim();
		}
	}
	
	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Sets the type.
	 *
	 * @param type the new type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Gets the subtype.
	 *
	 * @return the subtype
	 */
	public String getSubtype() {
		return subtype;
	}

	/**
	 * Sets the subtype.
	 *
	 * @param subtype the new subtype
	 */
	public void setSubtype(String subtype) {
		this.subtype = subtype;
	}

	/**
	 * Gets the teams.
	 *
	 * @return the teams
	 */
	public List<TeamEntry> getTeams() {
		return teams;
	}

	/**
	 * Sets the teams.
	 *
	 * @param teams the new teams
	 */
	public void setTeams(List<TeamEntry> teams) {
		this.teams = teams;
	}

	/**
	 * Gets the category.
	 *
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * Sets the category.
	 *
	 * @param category the new category
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * Gets the start.
	 *
	 * @return the start
	 */
	public String getStart() {
		return start;
	}

	/**
	 * Sets the start.
	 *
	 * @param start the new start
	 */
	public void setStart(String start) {
		this.start = start;
	}

	/**
	 * Gets the end.
	 *
	 * @return the end
	 */
	public String getEnd() {
		return end;
	}

	/**
	 * Sets the end.
	 *
	 * @param end the new end
	 */
	public void setEnd(String end) {
		this.end = end;
	}
	
}
