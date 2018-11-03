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
 * The Class RolegroupDataitem holds the data of a rolegroup
 */
public class RolegroupDataitem extends DefaultDataitem {

	/** The synonyms. */
	@JsonSchemaTitle("Synonyms")
	@JsonSchemaDescription("Synonym keywords for the rolegroup")
	private List<String> synonyms = new ArrayList<String>();
	
	/** The abbreviation. */
	@JsonSchemaTitle("Abbreviation")
	@JsonSchemaDescription("Abbreviation of the rolegroup")
	private String abbreviation;
	
	/** The responsible. */
	private String responsible;
	
	/** The summary. */
	private String summary;
	
	/** The lead. */
	private String lead;
	
	/** The parent. */
	private String parent;
	
	/**
	 * Gets the lead.
	 *
	 * @return the lead
	 */
	public String getLead() {
		return lead;
	}

	/**
	 * Sets the lead.
	 *
	 * @param lead the new lead
	 */
	public void setLead(String lead) {
		this.lead = lead;
	}
	
	/**
	 * Gets the responsible.
	 *
	 * @return the responsible
	 */
	public String getResponsible() {
		return responsible;
	}
	
	/**
	 * Gets the abbreviation.
	 *
	 * @return the abbreviation
	 */
	public String getAbbreviation() {
		return abbreviation;
	}

	/**
	 * Sets the abbreviation.
	 *
	 * @param abbreviation
	 *            the new abbreviation
	 */
	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation.trim();
	}

	/**
	 * Sets the responsible.
	 *
	 * @param resonsible the new responsible
	 */
	public void setResponsible(String resonsible) {
		this.responsible = resonsible;
	}

	/**
	 * Gets the summary.
	 *
	 * @return the summary
	 */
	public String getSummary() {
		return summary;
	}

	/**
	 * Sets the summary.
	 *
	 * @param summary the new summary
	 */
	public void setSummary(String summary) {
		this.summary = summary;
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
	 * Gets the synonyms.
	 *
	 * @return the synonyms
	 */
	public List<String> getSynonyms() {

		List<String> list = new ArrayList<String>();
		for (String syn : synonyms) {
			if (StringUtil.isNotNullAndNotEmpty(syn)) {
				list.add(syn);
			}
		}

		if (synonyms.size() == 0) {
			return null;
		}

		return synonyms;
	}

	/**
	 * Sets the synonyms.
	 *
	 * @param synonyms
	 *            the new synonyms
	 */
	public void setSynonyms(List<String> synonyms) {
		this.synonyms = StringUtil.clean(synonyms);
	}
	
}
