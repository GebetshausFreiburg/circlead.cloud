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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.rogatio.circlead.util.StringUtil;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kjetland.jackson.jsonSchema.annotations.JsonSchemaDescription;
import com.kjetland.jackson.jsonSchema.annotations.JsonSchemaTitle;

// TODO: Auto-generated Javadoc
/**
 * The Class ActivityDataitem hold the data of activity.
 *
 * @author Matthias Wegner
 */
public class ActivityDataitem extends DefaultDataitem {

	/** The responsible. */
	@JsonSchemaTitle("Responsible")
	@JsonSchemaDescription("Responsible role")
	private String responsible;

	/** The accountable. */
	@JsonSchemaTitle("Accountable")
	@JsonSchemaDescription("Accountable role")
	private String accountable;

	/** The subactivities. */
	private List<ActivityDataitem> subactivities;

	/** The description. */
	private String description;

	/** The aid. */
	private String aid;

	/** The child. */
	private String child;

	/** The bpmn. */
	private String bpmn;

	/** The results. */
	private String results;

	/** The howtos. */
	private List<String> howtos;

	/** The supplier. */
	@JsonSchemaTitle("Supplier")
	@JsonSchemaDescription("Supplier role")
	private List<String> supplier = new ArrayList<String>();

	/** The consultant. */
	@JsonSchemaTitle("Consultant")
	@JsonSchemaDescription("Consultant role")
	private List<String> consultant = new ArrayList<String>();

	/** The informed. */
	@JsonSchemaTitle("Informed")
	@JsonSchemaDescription("Informed role")
	private List<String> informed = new ArrayList<String>();

	/**
	 * Gets the role.
	 *
	 * @return the role
	 */
	public String getResponsible() {
		return responsible;
	}

	/**
	 * Sets the role.
	 *
	 * @param role the new role
	 */
	public void setResponsible(String role) {
		this.responsible = role;
	}

	/**
	 * Gets the accountable.
	 *
	 * @return the accountable
	 */
	public String getAccountable() {
		return accountable;
	}

	/**
	 * Sets the accountable.
	 *
	 * @param accountable the new accountable
	 */
	public void setAccountable(String accountable) {
		this.accountable = accountable;
	}

	/**
	 * Gets the child.
	 *
	 * @return the child
	 */
	public String getChild() {
		return child;
	}

	/** The link references. */
	@JsonIgnore
	private List<String> linkReferences = new ArrayList<String>();
	
	/** The link types. */
	@JsonIgnore
	private Map<String, String> linkTypes = new HashMap<String, String>();
	
	/** The link labels. */
	@JsonIgnore
	private Map<String, String> linkLabels = new HashMap<String, String>();

	/**
	 * Gets the link references.
	 *
	 * @return the link references
	 */
	@JsonIgnore
	public List<String> getLinkReferences() {
		return this.linkReferences;
	}

	/**
	 * Gets the link type.
	 *
	 * @param linkReference the link reference
	 * @return the link type
	 */
	@JsonIgnore
	public String getLinkType(String linkReference) {
		return linkTypes.get(linkReference);
	}

	/**
	 * Gets the link label.
	 *
	 * @param linkReference the link reference
	 * @return the link label
	 */
	@JsonIgnore
	public String getLinkLabel(String linkReference) {
		return linkLabels.get(linkReference);
	}

	/**
	 * Sets the child.
	 *
	 * @param child the new child
	 */
	public void setChild(String child) {

		if (StringUtil.isNotNullAndNotEmpty(child)) {
			if (child.contains(",")) {
				StringTokenizer st = new StringTokenizer(child, ",");
				while (st.hasMoreTokens()) {
					String token = st.nextToken().trim();
					int idx = token.indexOf("[");
					if (idx != -1) {
						String linkType = token.substring(idx, token.length()).replace("[", "").replace("]", "").trim();
						String linkReference = token.substring(0, idx).trim();
						linkReferences.add(linkReference);
						int idx2 = linkType.indexOf(":");
						if (idx2 != -1) {
							String linkLabel = linkType.substring(idx2, linkType.length());
							linkLabels.put(linkReference, linkLabel);
							linkType = linkType.substring(0, idx2).trim();
							linkTypes.put(linkReference, linkType);
						} else {
							linkTypes.put(linkReference, linkType);
						}
					} else {
						linkReferences.add(token);
					}
				}
			} else {
				linkReferences.add(child);
			}
		}

		this.child = child;
	}

	/**
	 * Gets the bpmn.
	 *
	 * @return the bpmn
	 */
	public String getBpmn() {
		return bpmn;
	}

	/**
	 * Sets the bpmn.
	 *
	 * @param bpmn the new bpmn
	 */
	public void setBpmn(String bpmn) {
		if (bpmn != null) {
			bpmn = bpmn.toLowerCase();
		}
		this.bpmn = bpmn;
	}

	/**
	 * Gets the supplier.
	 *
	 * @return the supplier
	 */
	public List<String> getSupplier() {
		return supplier;
	}

	/**
	 * Sets the supplier.
	 *
	 * @param supplier the new supplier
	 */
	public void setSupplier(List<String> supplier) {
		this.supplier = supplier;
	}

	/**
	 * Gets the consultant.
	 *
	 * @return the consultant
	 */
	public List<String> getConsultant() {
		return consultant;
	}

	/**
	 * Sets the consultant.
	 *
	 * @param consultant the new consultant
	 */
	public void setConsultant(List<String> consultant) {
		this.consultant = consultant;
	}

	/**
	 * Gets the informed.
	 *
	 * @return the informed
	 */
	public List<String> getInformed() {
		return informed;
	}

	/**
	 * Sets the informed.
	 *
	 * @param informed the new informed
	 */
	public void setInformed(List<String> informed) {
		this.informed = informed;
	}

	/**
	 * Gets the howtos.
	 *
	 * @return the howtos
	 */
	public List<String> getHowtos() {
		return howtos;
	}

	/**
	 * Sets the howtos.
	 *
	 * @param howtos the new howtos
	 */
	public void setHowtos(List<String> howtos) {
		this.howtos = StringUtil.clean(howtos);
	}

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
	 * Gets the results.
	 *
	 * @return the results
	 */
	public String getResults() {
		return results;
	}

	/**
	 * Sets the results.
	 *
	 * @param results the new results
	 */
	public void setResults(String results) {
		this.results = results;
	}

	/**
	 * Gets the aid.
	 *
	 * @return the aid
	 */
	public String getAid() {
		return aid;
	}

	/**
	 * Sets the aid.
	 *
	 * @param aid the new aid
	 */
	public void setAid(String aid) {
		this.aid = aid;
	}

	/**
	 * Gets the subactivities.
	 *
	 * @return the subactivities
	 */
	public List<ActivityDataitem> getSubactivities() {
		return subactivities;
	}

	/**
	 * Sets the subactivities.
	 *
	 * @param subactivities the new subactivities
	 */
	public void setSubactivities(List<ActivityDataitem> subactivities) {
		this.subactivities = subactivities;
	}

}
