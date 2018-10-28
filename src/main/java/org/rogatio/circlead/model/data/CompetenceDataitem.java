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

public class CompetenceDataitem extends DefaultDataitem {

	private String description;
	
	private String parent;
	
	private List<CompetenceDataitem> competencies = new ArrayList<CompetenceDataitem>();
	
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
	
	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public List<CompetenceDataitem> getCompetencies() {
		return competencies;
	}

	public void setCompetencies(List<CompetenceDataitem> competencies) {
		this.competencies = competencies;
	}
	
}
