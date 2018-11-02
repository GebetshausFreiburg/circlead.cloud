/*
 * Circlead - Develop and structure evolutionary Organisations
 * 
 * @author Matthias Wegner
 * @version 0.1
 * @since 01.07.2018
 * 
 */
package org.rogatio.circlead.model.work;

import static org.rogatio.circlead.model.Parameter.ABBREVIATION;
import static org.rogatio.circlead.model.Parameter.BRANDROLE;
import static org.rogatio.circlead.model.Parameter.CHILDS;
import static org.rogatio.circlead.model.Parameter.PARENT;
import static org.rogatio.circlead.model.Parameter.RESPONSIBLEROLE;
import static org.rogatio.circlead.model.Parameter.SUMMARY;
import static org.rogatio.circlead.model.Parameter.SYNONYMS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.jsoup.nodes.Element;
import org.rogatio.circlead.control.synchronizer.ISynchronizer;
import org.rogatio.circlead.control.validator.IValidator;
import org.rogatio.circlead.control.validator.ValidationMessage;
import org.rogatio.circlead.model.Parameter;
import org.rogatio.circlead.model.data.CompetenceDataitem;
import org.rogatio.circlead.model.data.IDataRow;
import org.rogatio.circlead.model.data.IDataitem;
import org.rogatio.circlead.model.data.RolegroupDataitem;
import org.rogatio.circlead.util.ObjectUtil;
import org.rogatio.circlead.util.StringUtil;
import org.rogatio.circlead.view.ISynchronizerRendererEngine;
import org.rogatio.circlead.view.IWorkitemRenderer;

/**
 * The Class Rolegroup.
 */
public class Rolegroup extends DefaultWorkitem implements IWorkitemRenderer, IValidator, IDataRow {

	/**
	 * Instantiates a new rolegroup.
	 */
	public Rolegroup() {
		this.dataitem = new RolegroupDataitem();
	}

	/**
	 * Instantiates a new rolegroup.
	 *
	 * @param dataitem
	 *            the dataitem of the rolegroup of class  
	 * {@link org.rogatio.circlead.model.data.RolegroupDataitem}
	 */
	public Rolegroup(IDataitem dataitem) {
		super(dataitem);
		
		if (!(dataitem instanceof RolegroupDataitem)) {
			throw new IllegalArgumentException("IDataitem must be of type RolegroupDataitem");
		}
	}

	/**
	 * Gets the lead identifier.
	 *
	 * @return the lead identifier
	 */
	public String getLeadIdentifier() {
		return this.getDataitem().getLead();
	}

	/**
	 * Gets the parent identifier.
	 *
	 * @return the parent identifier
	 */
	public String getParentIdentifier() {
		return this.getDataitem().getParent();
	}

	/**
	 * Sets the lead identifier.
	 *
	 * @param lead
	 *            the new lead identifier
	 */
	public void setLeadIdentifier(String lead) {
		this.getDataitem().setLead(lead);
	}

	/**
	 * Sets the parent identifier.
	 *
	 * @param parentIdentifier
	 *            the new parent identifier
	 */
	public void setParentIdentifier(String parentIdentifier) {
		this.getDataitem().setParent(parentIdentifier);
	}

	/**
	 * Gets the summary.
	 *
	 * @return the summary
	 */
	public String getSummary() {
		return this.getDataitem().getSummary();
	}

	/**
	 * Sets the summary.
	 *
	 * @param summary
	 *            the new summary
	 */
	public void setSummary(String summary) {
		this.getDataitem().setSummary(summary);
	}
	
	/**
	 * Gets the abbreviation.
	 *
	 * @return the abbreviation
	 */
	public String getAbbreviation() {
		return this.getDataitem().getAbbreviation();
	}
	
	/**
	 * Sets the abbreviation.
	 *
	 * @param abbreviation
	 *            the new abbreviation
	 */
	public void setAbbreviation(String abbreviation) {
		this.getDataitem().setAbbreviation(abbreviation);
	}

	/**
	 * Gets the responsible identifier.
	 *
	 * @return the responsible identifier
	 */
	public String getResponsibleIdentifier() {
		return getDataitem().getResponsible();
	}

	/**
	 * Sets the responsible identifier.
	 *
	 * @param identifier
	 *            the new responsible identifier
	 */
	public void setResponsibleIdentifier(String identifier) {
		this.getDataitem().setResponsible(identifier);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.rogatio.circlead.model.work.DefaultWorkitem#getDataitem()
	 */
	@Override
	public RolegroupDataitem getDataitem() {
		return (RolegroupDataitem) dataitem;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.rogatio.circlead.model.work.DefaultWorkitem#toString()
	 */
	@Override
	public String toString() {
		return this.getDataitem().toString() + ", type=" + getType();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.rogatio.circlead.view.IRenderer#render()
	 */
	@Override
	public Element render(ISynchronizer synchronizer) {
		ISynchronizerRendererEngine renderer = synchronizer.getRenderer();
		
		Element element = new Element("p");

		if (StringUtil.isNotNullAndNotEmpty(this.getResponsibleIdentifier())) {
			Role role = R.getRole(this.getResponsibleIdentifier());
			if (role != null) { 
				renderer.addRoleItem(element, RESPONSIBLEROLE.toString(), this.getResponsibleIdentifier());

				List<String> personIdentifiers = role.getPersonIdentifiers();
				renderer.addPersonList(element, personIdentifiers, role, this.getLeadIdentifier());
			}
		}

		if (StringUtil.isNotNullAndNotEmpty(this.getParentIdentifier())) {
			renderer.addRolegroupItem(element, PARENT.toString()+": ", this.getParentIdentifier());
		}
		if (StringUtil.isNotNullAndNotEmpty(this.getAbbreviation())) {
			renderer.addItem(element, ABBREVIATION.toString(), this.getAbbreviation());
		}
		renderer.addItem(element, SYNONYMS.toString(), this.getSynonyms());

		renderer.addH2(element, SUMMARY.toString());
		renderer.addItem(element, this.getSummary());

		List<Role> roles = R.getRoles(this.getTitle());
		if (roles.size() > 0) {
			renderer.addH2(element, BRANDROLE.toString());
			renderer.addRoleList(element, roles);
		}

		List<Rolegroup> childRolegroups = R.getRolegroupChildren(this.getTitle());
		if (childRolegroups != null) {
			if (childRolegroups.size() > 0) {
				renderer.addH2(element, CHILDS.toString());
				renderer.addRolegroupList(element, childRolegroups);
			}
		}

		return element;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.rogatio.circlead.control.IValidator#validate()
	 */
	@Override
	public List<ValidationMessage> validate() {
		List<ValidationMessage> messages = new ArrayList<ValidationMessage>();

		if (!StringUtil.isNotNullAndNotEmpty(this.getResponsibleIdentifier())) {
			ValidationMessage m = new ValidationMessage(this);
			m.warning("No responsible role added", "Rolegroup '" + this.getTitle() + "' has no named responsible role");
			messages.add(m);
		} else {
			Role rg = R.getRole(this.getResponsibleIdentifier());
			if (rg == null) {
				ValidationMessage m = new ValidationMessage(this);
				m.warning("Responsible role not found",
						"Responsible role '" + this.getResponsibleIdentifier() + "' not found for Rolegroup '" + this.getTitle() + "'");
				messages.add(m);
			}
		}
		
		/*if (this.getTitle().contains(" ")) {
			ValidationMessage m = new ValidationMessage(this);
			m.warning("Title has Whitespace", "Rolegroup '" + this.getTitle() + "' has whitespace in title");
			messages.add(m);
		}*/

		List<Role> roles = R.getRoles(this.getTitle());
		if (roles.size() == 0) {
			ValidationMessage m = new ValidationMessage(this);
			m.warning("No role contained", "Rolegroup '" + this.getTitle() + "' is not added to any role");
			messages.add(m);
		}

		if (!StringUtil.isNotNullAndNotEmpty(this.getLeadIdentifier())) {
			ValidationMessage m = new ValidationMessage(this);
			m.warning("No lead named", "Rolegroup '" + this.getTitle() + "' has no named person as lead");
			messages.add(m);
		}

		if (StringUtil.isNotNullAndNotEmpty(this.getResponsibleIdentifier())) {
			Role role = R.getRole(this.getResponsibleIdentifier());
			if (role != null) {
				List<String> personIdentifiers = role.getPersonIdentifiers();
				if (StringUtil.isNotNullAndNotEmpty(this.getLeadIdentifier())) {
					boolean found = false;
					for (String pi : personIdentifiers) {
						if (pi.equalsIgnoreCase(this.getLeadIdentifier())) {
							found = true;
						}
					}
					if (!found) {
						ValidationMessage m = new ValidationMessage(this);
						m.warning("Lead not in role", "Rolegroup '" + this.getTitle() + "' has lead person '" + this.getLeadIdentifier() + "' not set in role '"
								+ role.getTitle() + "'");
						messages.add(m);
					}
				}
			}
		}

		return messages;
	}
	
	/**
	 * Gets the synonyms.
	 *
	 * @return the synonyms
	 */
	public List<String> getSynonyms() {
		return this.getDataitem().getSynonyms();
	}
	
	/**
	 * Sets the synonyms.
	 *
	 * @param synonyms the new synonyms
	 */
	public void setSynonyms(String synonyms) {
		List<String> list = Arrays.asList(synonyms.split("[\\s,]+"));
		this.setSynonyms(list);
	}

	/**
	 * Sets the synonyms.
	 *
	 * @param synonyms
	 *            the new synonyms
	 */
	public void setSynonyms(List<String> synonyms) {
		this.getDataitem().setSynonyms(synonyms);
	}
	
	/* (non-Javadoc)
	 * @see org.rogatio.circlead.model.data.IDataRow#getDataRow()
	 */
	@Override
	public Map<Parameter, Object> getDataRow() {
		Map<Parameter, Object> map = new TreeMap<Parameter, Object>();

		addDataRowElement(this.getTitle(), Parameter.TITLE, map);
		addDataRowElement(this.getAbbreviation(), Parameter.ABBREVIATION, map);
		addDataRowElement(this.getLeadIdentifier(), Parameter.CONTACTPERSON, map);
		addDataRowElement(this.getSynonyms(), Parameter.SYNONYMS, map);
		addDataRowElement(this.getSummary(), Parameter.SUMMARY, map);
		addDataRowElement(this.getParentIdentifier(), Parameter.PARENT, map);
		addDataRowElement(this.getResponsibleIdentifier(), Parameter.RESPONSIBLEROLE, map);
		
		return map;
	}

	/**
	 * Adds the data row element.
	 *
	 * @param values the values
	 * @param parameter the parameter
	 * @param map the map
	 */
	private void addDataRowElement(List<String> values, Parameter parameter, Map<Parameter, Object> map) {
		if (ObjectUtil.isListNotNullAndEmpty(values)) {
			StringBuilder sb = new StringBuilder();
			for (String value : values) {
				sb.append(" - ").append(value).append("\n");
			}
			
			map.put(parameter, sb.toString());
		}
	}
	
	/**
	 * Adds the data row element.
	 *
	 * @param value the value
	 * @param parameter the parameter
	 * @param map the map
	 */
	private void addDataRowElement(String value, Parameter parameter, Map<Parameter, Object> map) {
		if (StringUtil.isNotNullAndNotEmpty(value)) {
			map.put(parameter, value);
		}
	}
}
