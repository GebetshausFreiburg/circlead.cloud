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

import org.rogatio.circlead.util.StringUtil;

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
	
}
