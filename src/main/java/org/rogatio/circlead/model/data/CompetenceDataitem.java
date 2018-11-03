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

/**
 * The Class CompetenceDataitem hold the data of competence
 * 
 * @author Matthias Wegner
 */
public class CompetenceDataitem extends DefaultDataitem {

	/** The description. */
	private String description;
	
	/** The parent. */
	private String parent;
	
	/** The competencies. */
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
	
	/**
	 * Gets the parent.
	 *
	 * @return the parent
	 */
	public String getParent() {
		return parent;
	}

	/**
	 * Sets the parent.
	 *
	 * @param parent the new parent
	 */
	public void setParent(String parent) {
		this.parent = parent;
	}

	/**
	 * Gets the competencies.
	 *
	 * @return the competencies
	 */
	public List<CompetenceDataitem> getCompetencies() {
		return competencies;
	}

	/**
	 * Sets the competencies.
	 *
	 * @param competencies the new competencies
	 */
	public void setCompetencies(List<CompetenceDataitem> competencies) {
		this.competencies = competencies;
	}
	
}
