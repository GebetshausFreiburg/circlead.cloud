/*
 * Circlead - Develop and structure evolutionary Organisations
 * 
 * @author Matthias Wegner
 * @version 0.1
 * @since 01.07.2018
 * 
 */
package org.rogatio.circlead.model.work;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jsoup.nodes.Element;
import org.rogatio.circlead.control.IValidator;
import org.rogatio.circlead.control.ValidationMessage;
import org.rogatio.circlead.control.synchronizer.ISynchronizer;
import org.rogatio.circlead.control.synchronizer.atlassian.parser.ListParserElement;
import org.rogatio.circlead.model.data.IDataitem;
import org.rogatio.circlead.model.data.RoleDataitem;
import org.rogatio.circlead.util.ObjectUtil;
import org.rogatio.circlead.view.IRenderer;
import org.rogatio.circlead.view.RenderUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class Role.
 */
public class Role extends DefaultWorkitem implements IRenderer, IValidator {

	/**
	 * Instantiates a new role.
	 */
	public Role() {
		this.dataitem = new RoleDataitem();
	}

	// public String getId(ISynchronizer synchronizer) {
	// return this.getDataitem().getId(synchronizer);
	// }

	/**
	 * Instantiates a new role.
	 *
	 * @param dataitem the dataitem
	 */
	public Role(IDataitem dataitem) {
		super(dataitem);
	}

	/*
	 * public String getSource(String id) { return this.sources.get(id); }
	 * 
	 * public void setSource(String id, String source) { this.sources.put(id, source); }
	 */

	/**
	 * Gets the organisation identifier.
	 *
	 * @return the organisation identifier
	 */
	public String getOrganisationIdentifier() {
		return this.getDataitem().getOrganisation();
	}

	/**
	 * Sets the organisation identifier.
	 *
	 * @param organisation the new organisation identifier
	 */
	public void setOrganisationIdentifier(String organisation) {
		this.getDataitem().setOrganisation(organisation);
	}

	/**
	 * Sets the rolegroup identifier.
	 *
	 * @param rolegroup the new rolegroup identifier
	 */
	public void setRolegroupIdentifier(String rolegroup) {
		this.getDataitem().setRolegroup(rolegroup);
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
	 * Sets the person identifiers.
	 *
	 * @param element the new person identifiers
	 */
	public void setPersonIdentifiers(ListParserElement element) {
		this.setPersons(element.getList());
	}

	/**
	 * Sets the person identifiers.
	 *
	 * @param persons the new person identifiers
	 */
	public void setPersonIdentifiers(String persons) {
		List<String> list = Arrays.asList(persons.split("[\\s,]+"));
		this.setPersons(list);
	}

	/**
	 * Sets the persons.
	 *
	 * @param persons the new persons
	 */
	public void setPersons(List<String> persons) {
		this.getDataitem().setPersons(persons);
	}

	/**
	 * Sets the competences.
	 *
	 * @param element the new competences
	 */
	public void setCompetences(ListParserElement element) {
		this.setCompetences(element.getList());
	}

	/**
	 * Sets the competences.
	 *
	 * @param competences the new competences
	 */
	public void setCompetences(String competences) {
		List<String> list = Arrays.asList(competences.split("[\\s,]+"));
		this.setCompetences(list);
	}

	/**
	 * Sets the competences.
	 *
	 * @param competences the new competences
	 */
	public void setCompetences(List<String> competences) {
		this.getDataitem().setCompetences(competences);
	}

	/**
	 * Sets the guidelines.
	 *
	 * @param element the new guidelines
	 */
	public void setGuidelines(ListParserElement element) {
		this.setGuidelines(element.getList());
	}

	/**
	 * Sets the guidelines.
	 *
	 * @param guidelines the new guidelines
	 */
	public void setGuidelines(String guidelines) {
		List<String> list = Arrays.asList(guidelines.split("[\\s,]+"));
		this.setGuidelines(list);
	}

	/**
	 * Sets the guidelines.
	 *
	 * @param guidelines the new guidelines
	 */
	public void setGuidelines(List<String> guidelines) {
		this.getDataitem().setGuidelines(guidelines);
	}

	/**
	 * Sets the opportunities.
	 *
	 * @param element the new opportunities
	 */
	public void setOpportunities(ListParserElement element) {
		this.setOpportunities(element.getList());
	}

	/**
	 * Sets the opportunities.
	 *
	 * @param opportunities the new opportunities
	 */
	public void setOpportunities(String opportunities) {
		List<String> list = Arrays.asList(opportunities.split("[\\s,]+"));
		this.setOpportunities(list);
	}

	/**
	 * Sets the opportunities.
	 *
	 * @param opportunities the new opportunities
	 */
	public void setOpportunities(List<String> opportunities) {
		this.getDataitem().setOpportunities(opportunities);
	}

	/**
	 * Sets the responsibilities.
	 *
	 * @param element the new responsibilities
	 */
	public void setResponsibilities(ListParserElement element) {
		this.setResponsibilities(element.getList());
	}

	/* (non-Javadoc)
	 * @see org.rogatio.circlead.model.work.DefaultWorkitem#setVersion(java.lang.String)
	 */
	public void setVersion(String version) {
		this.getDataitem().setVersion(version);
	}

	/**
	 * Sets the responsibilities.
	 *
	 * @param responsibilities the new responsibilities
	 */
	public void setResponsibilities(String responsibilities) {
		List<String> list = Arrays.asList(responsibilities.split("[\\s,]+"));
		this.setResponsibilities(list);
	}

	/**
	 * Gets the rolegroup identifier.
	 *
	 * @return the rolegroup identifier
	 */
	public String getRolegroupIdentifier() {
		return this.getDataitem().getRolegroup();
	}

	/**
	 * Sets the responsibilities.
	 *
	 * @param responsibilities the new responsibilities
	 */
	public void setResponsibilities(List<String> responsibilities) {
		this.getDataitem().setResponsibilities(responsibilities);
	}

	/**
	 * Gets the competences.
	 *
	 * @return the competences
	 */
	public List<String> getCompetences() {
		return this.getDataitem().getCompetences();
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
	 * Gets the responsibilities.
	 *
	 * @return the responsibilities
	 */
	public List<String> getResponsibilities() {
		return this.getDataitem().getResponsibilities();
	}

	/**
	 * Gets the person identifiers.
	 *
	 * @return the person identifiers
	 */
	public List<String> getPersonIdentifiers() {
		return this.getDataitem().getPersonIdentifiers();
	}

	/**
	 * Gets the opportunities.
	 *
	 * @return the opportunities
	 */
	public List<String> getOpportunities() {
		return this.getDataitem().getOpportunities();
	}

	/**
	 * Gets the guidelines.
	 *
	 * @return the guidelines
	 */
	public List<String> getGuidelines() {
		return this.getDataitem().getGuidelines();
	}

	/**
	 * Gets the activities.
	 *
	 * @return the activities
	 */
	public List<String> getActivities() {
		return this.getDataitem().getActivities();
	}

	/**
	 * Sets the activities.
	 *
	 * @param element the new activities
	 */
	public void setActivities(ListParserElement element) {
		this.setActivities(element.getList());
	}

	/**
	 * Sets the activities.
	 *
	 * @param activities the new activities
	 */
	public void setActivities(String activities) {
		List<String> list = Arrays.asList(activities.split("[\\s,]+"));
		this.setActivities(list);
	}

	/**
	 * Sets the activities.
	 *
	 * @param activities the new activities
	 */
	public void setActivities(List<String> activities) {
		this.getDataitem().setActivities(activities);
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
	 * @param synonyms the new synonyms
	 */
	public void setSynonyms(List<String> synonyms) {
		this.getDataitem().setSynonyms(synonyms);
	}

	/* (non-Javadoc)
	 * @see org.rogatio.circlead.model.work.DefaultWorkitem#getDataitem()
	 */
	@Override
	public RoleDataitem getDataitem() {
		return (RoleDataitem) dataitem;
	}

	// public ISynchronizer getDefaultSynchronizer() {
	// return this.getDataitem().getDefaultSynchronizer();
	// }

	/**
	 * Sets the parent.
	 *
	 * @param parentRole the new parent
	 */
	public void setParent(String parentRole) {
		this.getDataitem().setParent(parentRole);
	}

	/**
	 * Sets the abbreviation.
	 *
	 * @param abbreviation the new abbreviation
	 */
	public void setAbbreviation(String abbreviation) {
		this.getDataitem().setAbbreviation(abbreviation);
	}

	/**
	 * Gets the parent identifier.
	 *
	 * @return the parent identifier
	 */
	public String getParentIdentifier() {
		return this.getDataitem().getParent();
	}

	/*
	 * public String getSources() { return "source (id="+dataitem.getId()+") = '" + sources.get(dataitem.getId())+"', "+
	 * "source (aid="+dataitem.getAid()+") = '" + sources.get(dataitem.getAid())+"'"; }
	 */

	/* (non-Javadoc)
	 * @see org.rogatio.circlead.model.work.DefaultWorkitem#toString()
	 */
	@Override
	public String toString() {
		return this.getDataitem().toString() + ", type=" + getType();
	}

	/* (non-Javadoc)
	 * @see org.rogatio.circlead.view.IRenderer#render()
	 */
	@Override
	public Element render() {
		Element element = new Element("p");

		RenderUtil.addItem(element, "Abkürzung", this.getAbbreviation());
		RenderUtil.addRolegroupItem(element, "Rollengruppe", this.getRolegroupIdentifier());
		RenderUtil.addItem(element, "Organisation", this.getOrganisationIdentifier());
		RenderUtil.addRoleItem(element, "Vorgänger", this.getParentIdentifier());
		RenderUtil.addItem(element, "Synonyme", this.getSynonyms());

		if (ObjectUtil.isListNotNullAndEmpty(this.getPersonIdentifiers())) {
			RenderUtil.addH2(element, "Rollenträger");
			RenderUtil.addPersonList(element, this.getPersonIdentifiers(), this);
		}

		if (ObjectUtil.isListNotNullAndEmpty(this.getCompetences())) {
			RenderUtil.addH2(element, "Kompetenzen");
			RenderUtil.addList(element, this.getCompetences());
		}

		if (ObjectUtil.isListNotNullAndEmpty(this.getResponsibilities())) {
			RenderUtil.addH2(element, "Verantwortungen");
			RenderUtil.addList(element, this.getResponsibilities());
		}

		if (ObjectUtil.isListNotNullAndEmpty(this.getOpportunities())) {
			RenderUtil.addH2(element, "Befugnisse");
			RenderUtil.addList(element, this.getOpportunities());
		}

		if (ObjectUtil.isListNotNullAndEmpty(this.getGuidelines())) {
			RenderUtil.addH2(element, "Regeln");
			RenderUtil.addList(element, this.getGuidelines());
		}

		if (ObjectUtil.isListNotNullAndEmpty(this.getActivities())) {
			RenderUtil.addH2(element, "Aufgaben");
			RenderUtil.addList(element, this.getActivities());
		}

		return element;
	}

	/**
	 * Checks for organisation identifier.
	 *
	 * @return true, if successful
	 */
	public boolean hasOrganisationIdentifier() {
		if (this.getDataitem().getOrganisation() != null) {
			return true;
		}
		return false;
	}

	/**
	 * Checks for rolegroup identifier.
	 *
	 * @return true, if successful
	 */
	public boolean hasRolegroupIdentifier() {
		if (this.getDataitem().getRolegroup() != null) {
			return true;
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see org.rogatio.circlead.control.IValidator#validate()
	 */
	@Override
	public List<ValidationMessage> validate() {
		List<ValidationMessage> messages = new ArrayList<ValidationMessage>();

		if (!this.hasOrganisationIdentifier()) {
			ValidationMessage m = new ValidationMessage(this);
			m.warning("No organisation added", "Role '" + this.getTitle() + "' has no named organisation");
			messages.add(m);
		}

		if (!this.hasRolegroupIdentifier()) {
			ValidationMessage m = new ValidationMessage(this);
			m.error("Rolegroup not set", "Rolegroup in role '" + this.getTitle() + "' not set.");
			messages.add(m);
		}

		return messages;
	}

}