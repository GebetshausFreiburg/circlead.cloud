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
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.rogatio.circlead.model.WorkitemStatusParameter;
import org.rogatio.circlead.util.StringUtil;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kjetland.jackson.jsonSchema.annotations.JsonSchemaDescription;
import com.kjetland.jackson.jsonSchema.annotations.JsonSchemaTitle;

/**
 * The Class RoleDataitem holds the data of a role.
 *
 * @author Matthias Wegner
 */
public class RoleDataitem extends DefaultDataitem {

	/** The synonyms. */
	@JsonSchemaTitle("Synonyms")
	@JsonSchemaDescription("Synonym keywords for the role")
	private List<String> synonyms = new ArrayList<String>();

	/** The abbreviation. */
	@JsonSchemaTitle("Abbreviation")
	@JsonSchemaDescription("Abbreviation of the role")
	private String abbreviation;

	/** The parent. */
	@JsonSchemaTitle("Parent")
	@JsonSchemaDescription("Parent role of the role. Set as identifier.")
	private String parent;

	/** The purpose. */
	@JsonSchemaTitle("Purpose")
	@JsonSchemaDescription("Purpose of the role. Optional.")
	private String purpose;

	/** The rolegroup. */
	@JsonSchemaTitle("Rolegroup")
	@JsonSchemaDescription("Rolegroup of the role. Set as identifier.")
	private String rolegroup;

	/** The responsibilities. */
	@JsonSchemaTitle("Responsibilities")
	@JsonSchemaDescription("List of responsibilities.")
	private List<String> responsibilities = new ArrayList<String>();

	/** The opportunities. */
	@JsonSchemaTitle("Opportunities")
	@JsonSchemaDescription("List of opportunities.")
	private List<String> opportunities = new ArrayList<String>();

	/** The guidelines. */
	@JsonSchemaTitle("Guidelines")
	@JsonSchemaDescription("List of guidelines.")
	private List<String> guidelines = new ArrayList<String>();

	/** The competences. */
	@JsonSchemaTitle("Competences")
	@JsonSchemaDescription("List of competences.")
	private List<String> competences = new ArrayList<String>();

	/** The activities. */
	@JsonSchemaTitle("Activities")
	@JsonSchemaDescription("List of activities. Identifier allowed as uuid-prefix in [].")
	private List<String> activities = new ArrayList<String>();

	/** The persons. */
	@JsonSchemaTitle("Persons")
	@JsonSchemaDescription("List of responsibilities. Identifiers allowed. Could have as prefix [skill,status], where skill=0,25,50,75,100 and status=active,inactive,teporary,fallback")
	private List<String> persons = new ArrayList<String>();

	/** The situational. */
	private boolean situational = false;

	/**
	 * Checks if is situational.
	 *
	 * @return true, if is situational
	 */
	public boolean getSituational() {
		return this.situational;
	}

	/**
	 * Sets the situational.
	 *
	 * @param situational the new situational
	 */
	public void setSituational(boolean situational) {
		this.situational = situational;
	}

	/**
	 * Instantiates a new role data.
	 */
	public RoleDataitem() {
		this.setCreated(new Date());
		this.setModified(new Date());
	}

	/**
	 * Gets the activities.
	 *
	 * @return the activities
	 */
	public List<String> getActivities() {
		List<String> list = new ArrayList<String>();

		for (String activity : activities) {
			StringBuilder sb = new StringBuilder();

			sb.append(activity);

			if (this.hasActivityId(activity)) {
				sb.append(" [" + this.getActivityId(activity) + "]");
			}

			list.add(sb.toString());
		}

		return list;
	}

	/** The activity ids. */
	@JsonIgnore
	private Map<String, String> activityIds = new HashMap<String, String>();

	/**
	 * Checks for activity id.
	 *
	 * @param activityIdentifier the activity identifier
	 * @return true, if successful
	 */
	public boolean hasActivityId(String activityIdentifier) {
		return activityIds.containsKey(activityIdentifier);
	}

	/**
	 * Gets the activity id.
	 *
	 * @param activityIdentifier the activity identifier
	 * @return the activity id
	 */
	public String getActivityId(String activityIdentifier) {
		if (activityIds.containsKey(activityIdentifier)) {
			return activityIds.get(activityIdentifier);
		}
		return "";
	}

	/**
	 * Sets the activities.
	 *
	 * @param activities the new activities
	 */
	public void setActivities(List<String> activities) {

		List<String> list = new ArrayList<String>();

		for (String activity : activities) {
			int idx = activity.indexOf("[");
			if (idx != -1) {
				String id = activity.substring(idx, activity.length()).replace("[", "").replace("]", "").trim();
				String title = activity.substring(0, idx).trim();

				activityIds.put(title, id);

				list.add(title);

			} else {
				list.add(activity);
			}
		}

		this.activities = list;
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
	 * @param abbreviation the new abbreviation
	 */
	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation.trim();
	}

	/**
	 * Gets the responsibilities.
	 *
	 * @return the responsibilities
	 */
	public List<String> getResponsibilities() {
		return responsibilities;
	}

	/**
	 * Gets the purpose.
	 *
	 * @return the purpose
	 */
	public String getPurpose() {
		return purpose;
	}

	/**
	 * Sets the purpose.
	 *
	 * @param purpose the new purpose
	 */
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	/**
	 * Sets the responsibilities.
	 *
	 * @param responsibilities the new responsibilities
	 */
	public void setResponsibilities(List<String> responsibilities) {
		this.responsibilities = responsibilities;
	}

	/** The organisation. */
	private String organisation;

	/**
	 * Gets the organisation.
	 *
	 * @return the organisation
	 */
	public String getOrganisation() {
		return organisation;
	}

	/**
	 * Sets the organisation.
	 *
	 * @param organisation the new organisation
	 */
	public void setOrganisation(String organisation) {
		this.organisation = organisation;
	}

	/**
	 * Gets the opportunities.
	 *
	 * @return the opportunities
	 */
	public List<String> getOpportunities() {
		return opportunities;
	}

	/**
	 * Sets the opportunities.
	 *
	 * @param opportunities the new opportunities
	 */
	public void setOpportunities(List<String> opportunities) {
		this.opportunities = opportunities;
	}

	/**
	 * Gets the guidelines.
	 *
	 * @return the guidelines
	 */
	public List<String> getGuidelines() {
		return guidelines;
	}

	/**
	 * Sets the guidelines.
	 *
	 * @param guidelines the new guidelines
	 */
	public void setGuidelines(List<String> guidelines) {
		this.guidelines = StringUtil.clean(guidelines);
	}

	/**
	 * Gets the competences.
	 *
	 * @return the competences
	 */
	public List<String> getCompetences() {
		return competences;
	}

	/**
	 * Sets the competences.
	 *
	 * @param competences the new competences
	 */
	public void setCompetences(List<String> competences) {
		this.competences = competences;
	}

	/**
	 * Gets the parent.
	 *
	 * @return the parent
	 */
	public String getParent() {
		if (parent == null) {
			return null;
		} else {
			if (parent.trim().equals("")) {
				return null;
			}
			if (parent.trim().replace("-", "").equals("")) {
				return null;
			}
		}
		return parent;
	}

	/**
	 * Sets the parent.
	 *
	 * @param parentRole the new parent
	 */
	public void setParent(String parentRole) {
		this.parent = parentRole.trim();
	}

	/**
	 * Gets the rolegroup.
	 *
	 * @return the rolegroup
	 */
	public String getRolegroup() {
		if (rolegroup != null) {
			if (rolegroup.trim().equals("")) {
				return null;
			}
		}
		return rolegroup;
	}

	/**
	 * Sets the rolegroup.
	 *
	 * @param rolegroup the new rolegroup
	 */
	public void setRolegroup(String rolegroup) {
		this.rolegroup = rolegroup;
	}

	/**
	 * Gets the persons (without skills and status).
	 *
	 * @return the persons
	 */
	@JsonIgnore
	public List<String> getPersonIdentifiers() {
		Collections.sort(persons);
		return persons;
	}

	/**
	 * Gets the persons (with skills and status).
	 *
	 * @return the persons
	 */
	public List<String> getPersons() {
		List<String> list = new ArrayList<String>();

		for (String person : persons) {
			StringBuilder sb = new StringBuilder();

			sb.append(person);

			if (this.hasSkill(person) || this.hasRepresentation(person) || this.hasRecurrenceRule(person)) {
				sb.append(" [");
			}

			if (this.hasSkill(person)) {
				sb.append(this.getSkill(person) + "%");
			}

			if (this.hasRepresentation(person)) {

				if (this.hasSkill(person)) {
					sb.append(", ");
				}

				sb.append(this.getRepresentation(person));
			}

			if (this.hasRecurrenceRule(person)) {

				if (this.hasSkill(person) || this.hasRepresentation(person)) {
					sb.append(", ");
				}

				sb.append(this.getRecurrenceRule(person));
			}

			if (this.hasSkill(person) || this.hasRepresentation(person) || this.hasRecurrenceRule(person)) {
				sb.append("]");
			}

			if (this.hasComment(person)) {
				sb.append(" ## " + this.getComment(person));
			}

			list.add(sb.toString());
		}

		return list;
	}

	/** The skills. */
	@JsonIgnore
	private Map<String, String> skills = new HashMap<String, String>();

	/** The comments. */
	@JsonIgnore
	private Map<String, String> comments = new HashMap<String, String>();

	/** The recurrence rules. */
	@JsonIgnore
	private Map<String, String> recurrenceRules = new HashMap<String, String>();

	/** The representations. */
	@JsonIgnore
	private Map<String, String> representations = new HashMap<String, String>();

	/**
	 * Checks for representation.
	 *
	 * @param personIdentifier the person identifier
	 * @return true, if successful
	 */
	@JsonIgnore
	public boolean hasRepresentation(String personIdentifier) {
		if (representations.containsKey(personIdentifier)) {
			return true;
		}
		return false;
	}

	/**
	 * Gets the recurrence rule.
	 *
	 * @param personIdentifier the person identifier
	 * @return the recurrence rule
	 */
	@JsonIgnore
	public String getRecurrenceRule(String personIdentifier) {
		if (recurrenceRules.containsKey(personIdentifier)) {
			return recurrenceRules.get(personIdentifier);
		}
		return "";
	}

	/**
	 * Gets the representation.
	 *
	 * @param personIdentifier the person identifier
	 * @return the representation
	 */
	@JsonIgnore
	public String getRepresentation(String personIdentifier) {
		if (representations.containsKey(personIdentifier)) {
			return representations.get(personIdentifier);
		}
		return "";
	}

	/**
	 * Checks for recurrence rule.
	 *
	 * @param personIdentifier the person identifier
	 * @return true, if successful
	 */
	@JsonIgnore
	public boolean hasRecurrenceRule(String personIdentifier) {
		if (recurrenceRules.containsKey(personIdentifier)) {
			return true;
		}
		return false;
	}

	/**
	 * Checks for comment.
	 *
	 * @param personIdentifier the person identifier
	 * @return true, if successful
	 */
	@JsonIgnore
	public boolean hasComment(String personIdentifier) {
		if (comments.containsKey(personIdentifier)) {
			return true;
		}
		return false;
	}

	/**
	 * Checks for skill.
	 *
	 * @param personIdentifier the person identifier
	 * @return true, if successful
	 */
	@JsonIgnore
	public boolean hasSkill(String personIdentifier) {
		if (skills.containsKey(personIdentifier)) {
			return true;
		}
		return false;
	}

	/**
	 * Gets the comment.
	 *
	 * @param personIdentifier the person identifier
	 * @return the comment
	 */
	@JsonIgnore
	public String getComment(String personIdentifier) {
		if (comments.containsKey(personIdentifier)) {
			return comments.get(personIdentifier);
		}
		return null;
	}

	/**
	 * Gets the skill.
	 *
	 * @param personIdentifier the person identifier
	 * @return the skill
	 */
	@JsonIgnore
	public String getSkill(String personIdentifier) {
		if (skills.containsKey(personIdentifier)) {
			return skills.get(personIdentifier);
		}
		return null;
	}

	/**
	 * Sets the recurrence rule match.
	 *
	 * @param personFullname the person fullname
	 * @param value          the value
	 */
	@JsonIgnore
	private void setRecurrenceRuleMatch(String personFullname, String value) {
		value = value.trim();
		if (value.startsWith("R=")) {
			recurrenceRules.put(personFullname, value);
		}
	}

	/**
	 * Sets the status match.
	 *
	 * @param personFullname the person fullname
	 * @param value          the value
	 */
	@JsonIgnore
	private void setStatusMatch(String personFullname, String value) {
		if (!value.contains("%")) {
			WorkitemStatusParameter status = WorkitemStatusParameter.get(value);
			if (status != null) {
				value = status.getName();
				representations.put(personFullname, value.toLowerCase());
			}
		}
	}

	/**
	 * Sets the comment match.
	 *
	 * @param personFullname the person fullname
	 * @param value          the value
	 */
	@JsonIgnore
	private void setCommentMatch(String personFullname, String value) {
		value = value.trim();

		if (value.startsWith("##")) {
			value = value.substring(2, value.length());
		}

		value = value.trim();

		if (StringUtil.isNotNullAndNotEmpty(value)) {
			comments.put(personFullname, value);
		}
	}

	/**
	 * Sets the skill match.
	 *
	 * @param personFullname the person fullname
	 * @param value          the value
	 */
	@JsonIgnore
	private void setSkillMatch(String personFullname, String value) {
		if (value.contains("%")) {
			value = value.replace("%", "");
		}

		value = value.trim();

		if (value.equals("100") || value.equals("75") || value.equals("50") || value.equals("25")
				|| value.equals("0")) {
			skills.put(personFullname, value);
		}
	}

	/**
	 * Sets the persons as list which take role. Defined Syntax is
	 * 
	 * <pre>personIdentifier [x%, status, Rx=...] ## comment</pre>
	 *
	 * <ul>
	 * <li> <pre>personIdentifier</pre>Defines person which takes role. Is similar to fullname
	 * of person. This i the only mandatory value.
	 * <li><pre>x%</pre>Defines skill-level of person. Could be 0%, 25%, 50%, 75% or 100%
	 * <li> <pre>Rx=</pre>CircleadRecurrenceRule as String Representation. Could be
	 * Circlead-Standard starting with R= or Google-RecurrenceRule-Pattern starting
	 * with RRULE=
	 * <li> <pre>##</pre>Defines comment-line right from ##.
	 * </ul>
	 * 
	 * @param persons the new persons
	 */
	public void setPersons(List<String> persons) {
		List<String> list = new ArrayList<String>();

		for (String person : persons) {

			String comment = null;
			if (person.contains("##")) {
				int idx = person.indexOf("##");
				comment = person.substring(idx + 2, person.length()).trim();
				person = person.substring(0, idx).trim();
			}

			int idx = person.indexOf("[");
			if (idx != -1) {
				String skillStatus = person.substring(idx, person.length()).replace("[", "").replace("]", "").trim();

				String fullname = person.substring(0, idx).trim();

				if (skillStatus.contains(",")) {
					String[] values = skillStatus.split(",");
					for (String value : values) {
						value = value.trim();
						setSkillMatch(fullname, value);
						setStatusMatch(fullname, value);
						setRecurrenceRuleMatch(fullname, value);
					}
				} else {
					String value = skillStatus.trim();
					setSkillMatch(fullname, value);
					setStatusMatch(fullname, value);
					setRecurrenceRuleMatch(fullname, value);
				}

				if (StringUtil.isNotNullAndNotEmpty(comment)) {
					setCommentMatch(fullname, comment);
				}

				list.add(fullname);
			} else {
				if (StringUtil.isNotNullAndNotEmpty(comment)) {
					setCommentMatch(person, comment);
				}
				list.add(person);
			}
		}

		this.persons = list;

	}

	/**
	 * Gets the synonyms of the role title.
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
	 * Sets the synonyms of a role.
	 *
	 * @param synonyms the new synonyms
	 */
	public void setSynonyms(List<String> synonyms) {
		this.synonyms = StringUtil.clean(synonyms);
	}

}