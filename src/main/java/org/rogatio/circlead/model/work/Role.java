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
import static org.rogatio.circlead.model.Parameter.CARRYROLEGROUP;
import static org.rogatio.circlead.model.Parameter.CHILDS;
import static org.rogatio.circlead.model.Parameter.COMPETENCIES;
import static org.rogatio.circlead.model.Parameter.OPPORTUNITIES;
import static org.rogatio.circlead.model.Parameter.ORGANISATION;
import static org.rogatio.circlead.model.Parameter.PARENT;
import static org.rogatio.circlead.model.Parameter.PURPOSE;
import static org.rogatio.circlead.model.Parameter.RESPONSIBILITIES;
import static org.rogatio.circlead.model.Parameter.ROLEPERSONS;
import static org.rogatio.circlead.model.Parameter.ROLEPERSONSINORGANISATION;
import static org.rogatio.circlead.model.Parameter.ROLEPERSONSINTEAM;
import static org.rogatio.circlead.model.Parameter.RULES;
import static org.rogatio.circlead.model.Parameter.SYNONYMS;
import static org.rogatio.circlead.model.Parameter.TASKS;
import static org.rogatio.circlead.model.Parameter.UNRELATED;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.jsoup.nodes.Element;
import org.rogatio.circlead.control.synchronizer.ISynchronizer;
import org.rogatio.circlead.control.synchronizer.atlassian.AtlassianSynchronizer;
import org.rogatio.circlead.control.synchronizer.atlassian.parser.ListParserElement;
import org.rogatio.circlead.control.synchronizer.file.FileSynchronizer;
import org.rogatio.circlead.control.validator.IValidator;
import org.rogatio.circlead.control.validator.ValidationMessage;
import org.rogatio.circlead.model.Parameter;
import org.rogatio.circlead.model.WorkitemStatusParameter;
import org.rogatio.circlead.model.data.ActivityDataitem;
import org.rogatio.circlead.model.data.IDataRow;
import org.rogatio.circlead.model.data.IDataitem;
import org.rogatio.circlead.model.data.RoleDataitem;
import org.rogatio.circlead.model.data.TeamEntry;
import org.rogatio.circlead.util.ObjectUtil;
import org.rogatio.circlead.util.StringUtil;
import org.rogatio.circlead.view.ISynchronizerRendererEngine;
import org.rogatio.circlead.view.IWorkitemRenderer;
import org.rogatio.circlead.view.SvgBuilder;

/**
 * The Class Role holds the core information of the role workitem.
 */
public class Role extends DefaultWorkitem implements IWorkitemRenderer, IValidator, IDataRow {

	/**
	 * Instantiates a new emtpy role.
	 */
	public Role() {
		this.dataitem = new RoleDataitem();
	}

	/**
	 * Instantiates a new role from a RoleDataitem
	 *
	 * @param dataitem the dataitem
	 */
	public Role(IDataitem dataitem) {
		super(dataitem);
	}

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
	 * Sets the person identifiers from html-parser
	 *
	 * @param element the new person identifiers
	 */
	public void setPersonIdentifiers(ListParserElement element) {
		this.setPersons(element.getList());
	}

	/**
	 * Sets the person identifiers from single string. Splits the string in
	 * identifier-pieces
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
	 * Sets the competences from list-html-parser
	 *
	 * @param element the new competences
	 */
	public void setCompetences(ListParserElement element) {
		this.setCompetences(element.getList());
	}

	/**
	 * Sets the competences from single string. Splits the string in pieces
	 *
	 * @param competences the new competences
	 */
	public void setCompetences(String competences) {
		List<String> list = Arrays.asList(competences.split("[\\s,]+"));
		this.setCompetences(list);
	}

	/**
	 * Sets the competences from list
	 *
	 * @param competences the new competences
	 */
	public void setCompetences(List<String> competences) {
		this.getDataitem().setCompetences(competences);
	}

	/**
	 * Sets the guidelines from list-html-parser
	 *
	 * @param element the new guidelines
	 */
	public void setGuidelines(ListParserElement element) {
		this.setGuidelines(element.getList());
	}

	/**
	 * Sets the guidelines from single string. Splits the string in pieces
	 *
	 * @param guidelines the new guidelines
	 */
	public void setGuidelines(String guidelines) {
		List<String> list = Arrays.asList(guidelines.split("[\\s,]+"));
		this.setGuidelines(list);
	}

	/**
	 * Sets the guidelines from list
	 *
	 * @param guidelines the new guidelines
	 */
	public void setGuidelines(List<String> guidelines) {
		this.getDataitem().setGuidelines(guidelines);
	}

	/**
	 * Sets the opportunities from list-html-parser
	 *
	 * @param element the new opportunities
	 */
	public void setOpportunities(ListParserElement element) {
		this.setOpportunities(element.getList());
	}

	/**
	 * Sets the opportunities from single string. Splits the string in pieces
	 *
	 * @param opportunities the new opportunities
	 */
	public void setOpportunities(String opportunities) {
		List<String> list = Arrays.asList(opportunities.split("[\\s,]+"));
		this.setOpportunities(list);
	}

	/**
	 * Sets the opportunities from list
	 *
	 * @param opportunities the new opportunities
	 */
	public void setOpportunities(List<String> opportunities) {
		this.getDataitem().setOpportunities(opportunities);
	}

	/**
	 * Sets the responsibilities from list-html-parser
	 *
	 * @param element the new responsibilities
	 */
	public void setResponsibilities(ListParserElement element) {
		this.setResponsibilities(element.getList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.rogatio.circlead.model.work.DefaultWorkitem#setVersion(java.lang.String)
	 */
	public void setVersion(String version) {
		this.getDataitem().setVersion(version);
	}

	/**
	 * Sets the responsibilities from single string. Splits the string in pieces
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
	 * Sets the responsibilities from list
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.rogatio.circlead.model.work.DefaultWorkitem#getDataitem()
	 */
	@Override
	public RoleDataitem getDataitem() {
		return (RoleDataitem) dataitem;
	}

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
	 * (non-Javadoc)
	 * 
	 * @see org.rogatio.circlead.model.work.DefaultWorkitem#toString()
	 */
	@Override
	public String toString() {
		return this.getDataitem().toString() + ", type=" + getType();
	}

	/**
	 * Gets the recurrence rule.
	 *
	 * @param personIdentifier the person identifier
	 * @return the recurrence rule
	 */
	public String getRecurrenceRule(String personIdentifier) {
		return this.getDataitem().getRecurrenceRule(personIdentifier);
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

		renderer.addItem(element, ABBREVIATION.toString(), this.getAbbreviation());
		renderer.addRolegroupItem(element, CARRYROLEGROUP.toString(), this.getRolegroupIdentifier());
		renderer.addItem(element, ORGANISATION.toString(), this.getOrganisationIdentifier());
		renderer.addRoleItem(element, PARENT.toString(), this.getParentIdentifier());
		renderer.addItem(element, SYNONYMS.toString(), this.getSynonyms());

		List<Role> childRoles = R.getRoleChildren(this.getTitle());
		if (childRoles != null) {
			if (childRoles.size() > 0) {
				renderer.addH2(element, CHILDS.toString());
				renderer.addRoleList(element, childRoles);
			}
		}

		boolean foundSomeRoleResponsible = false;
		if (ObjectUtil.isListNotNullAndEmpty(this.getPersonIdentifiers())) {
			renderer.addH2(element,
					ROLEPERSONSINORGANISATION.toString() + " (" + this.getPersonIdentifiers().size() + ")");
			renderer.addPersonList(element, this.getPersonIdentifiers(), this);
			foundSomeRoleResponsible = true;
		}

		// Only show teamroles in atlassian, not in files because print is to big
		if (synchronizer.getClass().getSimpleName().equals(AtlassianSynchronizer.class.getSimpleName())) {
			List<Team> foundTeams = R.getTeamsWithRole(this);
			if (ObjectUtil.isListNotNullAndEmpty(foundTeams)) {
				renderer.addH2(element,
						ROLEPERSONSINTEAM.toString() + " (" + R.getTeamPersonsWithRole(this).size() + ")");

				Element ul = element.appendElement("ul");
				for (Team team : foundTeams) {
					Element li = ul.appendElement("li");
					String c = "";
					if (StringUtil.isNotNullAndNotEmpty(team.getCategory())) {
						c = " (" + team.getCategory() + ")";
					}

					if (synchronizer.getClass().getSimpleName().equals(AtlassianSynchronizer.class.getSimpleName())) {
						li.append("<ac:link><ri:page ri:content-title=\"" + team.getTitle()
								+ "\" ri:version-at-save=\"1\"/><ac:plain-text-link-body><![CDATA[" + team.getTitle()
								+ "" + c + "]]></ac:plain-text-link-body></ac:link>");
					} else if (synchronizer.getClass().getSimpleName().equals(FileSynchronizer.class.getSimpleName())) {
						li.appendElement("a").attr("href", "../web/" + team.getId(synchronizer) + ".html")
								.appendText(team.getTitle() + c);
					}

					li.append("&nbsp;");
					renderer.addStatus(li, team.getStatus());

					List<TeamEntry> x = team.getTeamEntries();
					Element ul2 = li.appendElement("ul");
					for (TeamEntry e : x) {
						// this.addTeamPerson(ul2, team, e, renderer);
						if (e.getRoleIdentifier().equals(this.getTitle())) {
							List<String> pi = e.getPersons();
							if (ObjectUtil.isListNotNullAndEmpty(pi)) {
								for (String p : pi) {
									Element li2 = ul2.appendElement("li");
									renderer.addPersonItem(li2, null, p);
									foundSomeRoleResponsible = true;
								}
							}
						}
					}
				}
			}
		}

		if (!foundSomeRoleResponsible) {
			renderer.addH2(element, ROLEPERSONS.toString());
			renderer.addStatus(element, UNRELATED.toString());
		}

		if (StringUtil.isNotNullAndNotEmpty(this.getPurpose())) {
			renderer.addH2(element, PURPOSE.toString());
			element.appendText(this.getPurpose());
		}

		renderer.addH2(element, TASKS.toString());

		List<String> divActivities = getActivitiesNotGlobal();
		if (ObjectUtil.isListNotNullAndEmpty(divActivities)) {
			renderer.addList(element, divActivities);
		}

		/*
		 * List<Activity> a = R.getActivities(this.getTitle());
		 * 
		 * if (ObjectUtil.isListNotNullAndEmpty(this.getActivities())) { if
		 * (ObjectUtil.isListNotNullAndEmpty(a)) { List<String> alist = new
		 * ArrayList<String>(); for (String actRole : getActivities()) { boolean found =
		 * false; for (Activity activity : a) { if (activity.getTitle().equals(actRole))
		 * { found = true; } } if (!found) { if (!alist.contains(actRole)) {
		 * alist.add(actRole); } } }
		 * 
		 * renderer.addList(element, alist); } else { renderer.addList(element,
		 * this.getActivities()); } }
		 */

		TreeMap<Activity, List<ActivityDataitem>> map = R.getSubactivitiesWithResponsible(this.getTitle());

		List<Activity> globalActivities = R.getActivities(this.getTitle());
		List<Activity> ga = new ArrayList<Activity>();
		for (Activity activity : globalActivities) {
			if (!containsActivityInSubactivities(activity)) {
				ga.add(activity);
			}
		}

		if (ObjectUtil.isListNotNullAndEmpty(ga)) {
			renderer.addActivityList(element, ga);
		}

		for (Activity activity : map.keySet()) {
			List<ActivityDataitem> subactivities = map.get(activity);
			if (ObjectUtil.isListNotNullAndEmpty(subactivities)) {
				renderer.addSubActivityList(element, subactivities, activity, this);
			}
		}

		/*
		 * List<Activity> allA = R.getActivities(); for (Activity activity : allA) {
		 * List<ActivityDataitem> ac =
		 * activity.getSubactivitiesWithResponsible(this.getTitle()); if
		 * (ObjectUtil.isListNotNullAndEmpty(ac)) { renderer.addSubActivityList(element,
		 * ac, activity); } }
		 */

		if (ObjectUtil.isListNotNullAndEmpty(this.getResponsibilities())) {
			renderer.addH2(element, RESPONSIBILITIES.toString());
			renderer.addList(element, this.getResponsibilities());
		}

		renderer.addH2(element, COMPETENCIES.toString());
		if (synchronizer.getClass().getSimpleName().equals(FileSynchronizer.class.getSimpleName())) {
			element.append(SvgBuilder.createRoleDnaProfile(this, 512).toString());
		}
		if (ObjectUtil.isListNotNullAndEmpty(this.getCompetences())) {
			renderer.addList(element, this.getCompetences());
			renderImplicitCompetence(this.getParentIdentifier(), element, renderer);
		}

		if (ObjectUtil.isListNotNullAndEmpty(this.getOpportunities())) {
			renderer.addH2(element, OPPORTUNITIES.toString());
			renderer.addList(element, this.getOpportunities());
		}

		if (ObjectUtil.isListNotNullAndEmpty(this.getGuidelines())) {
			renderer.addH2(element, RULES.toString());
			renderer.addList(element, this.getGuidelines());
		}

		return element;
	}

	public double getRedundanceTeam() {
		double val = 0;
		List<Team> teams = R.getTeamsWithRole(this);
		for (Team team : teams) {
			List<TeamEntry> entries = team.getTeamEntries();
			for (TeamEntry entry : entries) {
				if (entry.getRoleIdentifier().equals(this.getTitle())) {
					int skillValue = 0;
					try {
						// System.out.println(entry.getLevel());
						skillValue = Integer.parseInt(entry.getLevel());
					} catch (Exception e) {
						skillValue = 0;
					}
					// System.out.println(skillValue);
					val += ((double) skillValue) * entry.getPersonIdentifiers().size();
					// System.out.println(val);
				}
			}

		}

		return val / (double) 100.0;
	}

	public double getRedundance() {
		return Math.max(this.getRedundanceOrganisation(), this.getRedundanceTeam());
	}

	public double getRedundanceOrganisation() {

		double value = 0;

		List<String> pi = this.getPersonIdentifiers();
		for (String personIdentifier : pi) {
			String representation = this.getDataitem().getRepresentation(personIdentifier);
			WorkitemStatusParameter status = WorkitemStatusParameter.get(representation);
			String skill = this.getDataitem().getSkill(personIdentifier);
			int skillValue = 0;
			try {
				skillValue = Integer.parseInt(skill);
			} catch (Exception e) {
				skillValue = 0;
			}
			if ((status == WorkitemStatusParameter.TEMPORARY) || (status == WorkitemStatusParameter.ACTIVE)) {
				value += (skillValue);
			}
		}

		return (value) / 100.0;
	}

	/**
	 * Contains activity in subactivities.
	 *
	 * @param a the a
	 * @return true, if successful
	 */
	private boolean containsActivityInSubactivities(Activity a) {
		TreeMap<Activity, List<ActivityDataitem>> map = R.getSubactivitiesWithResponsible(this.getTitle());

		for (Activity activity : map.keySet()) {
			List<ActivityDataitem> subactivities = map.get(activity);
			if (ObjectUtil.isListNotNullAndEmpty(subactivities)) {
				for (ActivityDataitem x : subactivities) {
					if (x.getTitle().equals(a.getTitle())) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * Adds the team person.
	 *
	 * @param ulElement the ul element
	 * @param team      the team
	 * @param teamEntry the team entry
	 * @param renderer  the renderer
	 * @return true, if successful
	 */
	@SuppressWarnings("unused")
	@Deprecated
	private boolean addTeamPerson(Element ulElement, Team team, TeamEntry teamEntry,
			ISynchronizerRendererEngine renderer) {
		boolean foundSomeRoleResponsible = false;
		if (teamEntry.getRoleIdentifier().equals(this.getTitle())) {
			List<String> pi = teamEntry.getPersons();
			if (ObjectUtil.isListNotNullAndEmpty(pi)) {
				for (String p : pi) {
					Element listElement = ulElement.appendElement("li");

					Person person = R.getPerson(p);

					if (teamEntry.getRoleIdentifier() != null) {
						if (person != null) {
							if (renderer.getSynchronizer().getClass().getSimpleName()
									.equals(AtlassianSynchronizer.class.getSimpleName())) {
								listElement.appendElement("ac:link").append("<ri:page ri:content-title=\""
										+ person.getTitle() + "\" ri:version-at-save=\"1\" />");
							} else if (renderer.getSynchronizer().getClass().getSimpleName()
									.equals(FileSynchronizer.class.getSimpleName())) {
								listElement.appendElement("a")
										.attr("href", "../web/" + person.getId(renderer.getSynchronizer()) + ".html")
										.appendText(person.getTitle());
							}
						} else {
							listElement.appendText(teamEntry.getRoleIdentifier());

							if (teamEntry.hasRecurrenceRule(p)) {
								String rule = teamEntry.getRecurrenceRule(p);
								listElement.append("&nbsp;<span style=\"color: rgb(192,192,192);\">"
										+ rule.replace("R=", "").replace("RRULE=", "") + "</span>");
							} else if (StringUtil.isNotNullAndNotEmpty(team.getRecurrenceRule())) {
								String rule = team.getRecurrenceRule();
								listElement.append("&nbsp;<span style=\"color: rgb(192,192,192);\">"
										+ rule.replace("R=", "").replace("RRULE=", "") + "</span>");
							}
						}
					} else {
						listElement.appendText("-");
					}

					foundSomeRoleResponsible = true;
				}
			}
		}

		return foundSomeRoleResponsible;
	}

	/**
	 * Gets the activities not global.
	 *
	 * @return the activities not global
	 */
	private List<String> getActivitiesNotGlobal() {
		List<Activity> a = R.getActivities(this.getTitle());
		TreeMap<Activity, List<ActivityDataitem>> map = R.getSubactivitiesWithResponsible(this.getTitle());

		List<String> alist = new ArrayList<String>();

		if (ObjectUtil.isListNotNullAndEmpty(this.getActivities()) || ObjectUtil.isListNotNullAndEmpty(a)) {
			for (String actRole : getActivities()) {
				boolean found = false;

				if (map != null) {
					for (Activity activity : map.keySet()) {
						List<ActivityDataitem> subactivities = map.get(activity);
						if (ObjectUtil.isListNotNullAndEmpty(subactivities)) {
							for (ActivityDataitem x : subactivities) {
								if (x.getTitle().equals(actRole)) {
									found = true;
								}
							}
						}
					}
				}

				if (ObjectUtil.isListNotNullAndEmpty(a)) {
					for (Activity activity : a) {
						if (activity.getTitle().equals(actRole)) {
							found = true;
						}
					}
				}

				if (!found) {
					if (!alist.contains(actRole)) {
						alist.add(actRole);
					}
				}
			}
		} else {
			return this.getActivities();
		}

		return alist;
	}

	/**
	 * Render implicit competences. Recursive method which looks for role-parents.
	 * If parent exists and contains competences then render them.
	 *
	 * @param parentIdentifier the parent identifier
	 * @param element          the element
	 * @param renderer         the renderer
	 */
	private void renderImplicitCompetence(String parentIdentifier, Element element,
			ISynchronizerRendererEngine renderer) {
		if (parentIdentifier != null) {
			if (!parentIdentifier.equals(this.getTitle())) {
				Role p = R.getRole(parentIdentifier);
				if (p != null) {
					if (ObjectUtil.isListNotNullAndEmpty(p.getCompetences())) {
						renderer.addRoleItem(element, "Implizit von", p.getTitle());
						renderer.addList(element, p.getCompetences());
					}
					renderImplicitCompetence(p.getParentIdentifier(), element, renderer);
				}
			}
		}
	}

	/**
	 * Sets the purpose.
	 *
	 * @param purpose the new purpose
	 */
	public void setPurpose(String purpose) {
		this.getDataitem().setPurpose(purpose);
	}

	/**
	 * Gets the purpose.
	 *
	 * @return the purpose
	 */
	public String getPurpose() {
		return this.getDataitem().getPurpose();
	}

	/**
	 * Checks for title.
	 *
	 * @return true, if successful
	 */
	public boolean hasTitle() {
		if (this.getDataitem().getTitle() != null) {
			return true;
		}
		return false;
	}

	/**
	 * Checks for abbreviation.
	 *
	 * @return true, if successful
	 */
	public boolean hasAbbreviation() {
		if (this.getDataitem().getAbbreviation() != null) {
			return true;
		}
		return false;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.rogatio.circlead.control.IValidator#validate()
	 */
	@Override
	public List<ValidationMessage> validate() {
		List<ValidationMessage> messages = new ArrayList<ValidationMessage>();

		if (!this.hasTitle()) {
			ValidationMessage m = new ValidationMessage(this);
			m.warning("No title added", "Role '" + this.getId() + "' has no title");
			messages.add(m);
		}

		if (!this.hasAbbreviation()) {
			ValidationMessage m = new ValidationMessage(this);
			m.warning("No abbreviation added", "Role '" + this.getTitle() + "' has no abbreviation");
			messages.add(m);
		}

		if (!R.hasUniqueRoleAbbreviation(this)) {
			ValidationMessage m = new ValidationMessage(this);
			m.error("No unique abbreviation", "Role '" + this.getTitle() + "' has no unique abbreviation");
			messages.add(m);
		}

		if (!R.hasUniqueRoleTitle(this)) {
			ValidationMessage m = new ValidationMessage(this);
			m.error("No unique title", "Role '" + this.getId() + "' has no unique title");
			messages.add(m);
		}

		/*
		 * if (this.getTitle().contains(" ")) { ValidationMessage m = new
		 * ValidationMessage(this); m.warning("Title has Whitespace", "Role '" +
		 * this.getTitle() + "' has whitespace in title"); messages.add(m); }
		 */

		if (!this.hasOrganisationIdentifier()) {
			ValidationMessage m = new ValidationMessage(this);
			m.warning("No organisation added", "Role '" + this.getTitle() + "' has no named organisation");
			messages.add(m);
		}

		if (ObjectUtil.isListNotNullAndEmpty(this.getPersonIdentifiers())) {
			for (String identifier : getPersonIdentifiers()) {
				Person person = R.getPerson(identifier);
				if (person == null) {
					ValidationMessage m = new ValidationMessage(this);
					m.warning("Person not found",
							"Person '" + identifier + "' in role '" + this.getTitle() + "' not found.");
					messages.add(m);
				} else {
					if (this.getDataitem().hasRepresentation(identifier)) {
						String representation = this.getDataitem().getRepresentation(identifier);
						WorkitemStatusParameter status = WorkitemStatusParameter.get(representation);
						if (status != null) {
							if (status == WorkitemStatusParameter.TEMPORARY) {
								ValidationMessage m = new ValidationMessage(this);
								m.info("Person '" + identifier + "' holds role '" + this.getTitle() + "' temporarily.");
								messages.add(m);
							}
						}
					}
				}
			}
		}

		if (!this.hasRolegroupIdentifier()) {
			ValidationMessage m = new ValidationMessage(this);
			m.error("Rolegroup not set", "Rolegroup in role '" + this.getTitle() + "' not set.");
			messages.add(m);
		} else {
			Rolegroup rg = R.getRolegroup(this.getRolegroupIdentifier());
			if (rg == null) {
				ValidationMessage m = new ValidationMessage(this);
				m.error("Rolegroup not found", "Rolegroup '" + this.getRolegroupIdentifier() + "' in role '"
						+ this.getTitle() + "' not found.");
				messages.add(m);
			}
		}

		boolean foundParent = false;
		if (StringUtil.isNotNullAndNotEmpty(this.getParentIdentifier())) {
			foundParent = true;
		}

		boolean foundChildren = false;
		List<Role> childRoles = R.getRoleChildren(this.getTitle());
		if (childRoles != null) {
			if (childRoles.size() > 0) {
				foundChildren = true;
			}
		}

		if (!foundParent && !foundChildren) {
			ValidationMessage m = new ValidationMessage(this);
			m.error("Role lost", "Role '" + this.getTitle() + "' has no predecessor and no siblings.");
			messages.add(m);
		}

		for (String identifier : this.getPersonIdentifiers()) {
			if (getDataitem().hasRepresentation(identifier)) {
				String representation = getDataitem().getRepresentation(identifier);
				WorkitemStatusParameter status = WorkitemStatusParameter.get(representation);
				if (status != null) {
					if (status == WorkitemStatusParameter.CRITICAL) {
						ValidationMessage m = new ValidationMessage(this);
						m.error("Person-Status invalid",
								"Role '" + this.getTitle() + "' has no clear status for person '" + identifier + "'");
						messages.add(m);
					}
				}
			}
		}

		return messages;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.rogatio.circlead.model.data.IDataRow#getDataRow()
	 */
	@Override
	public Map<Parameter, Object> getDataRow() {
		Map<Parameter, Object> map = new TreeMap<Parameter, Object>();

		addDataRowElement(this.getTitle(), Parameter.TITLE, map);
		addDataRowElement(this.getAbbreviation(), Parameter.ABBREVIATION, map);
		addDataRowElement(this.getPurpose(), Parameter.PURPOSE, map);
		addDataRowElement(this.getRolegroupIdentifier(), Parameter.ROLEGROUP, map);
		addDataRowElement(this.getOrganisationIdentifier(), Parameter.ORGANISATION, map);
		addDataRowElement(this.getSynonyms(), Parameter.SYNONYMS, map);
		addDataRowElement(this.getParentIdentifier(), Parameter.PARENT, map);
		addDataRowElement(this.getPersonIdentifiers(), Parameter.PERSONS, map);
		addDataRowElement(this.getActivities(), Parameter.ACTIVITIES, map);
		addDataRowElement(this.getResponsibilities(), Parameter.RESPONSIBILITIES, map);
		addDataRowElement(this.getOpportunities(), Parameter.OPPORTUNITIES, map);
		addDataRowElement(this.getGuidelines(), Parameter.RULES, map);
		addDataRowElement(this.getCompetences(), Parameter.COMPETENCIES, map);

		return map;
	}

	/**
	 * Adds the data row element.
	 *
	 * @param values    the values
	 * @param parameter the parameter
	 * @param map       the map
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
	 * @param value     the value
	 * @param parameter the parameter
	 * @param map       the map
	 */
	private void addDataRowElement(String value, Parameter parameter, Map<Parameter, Object> map) {
		if (StringUtil.isNotNullAndNotEmpty(value)) {
			map.put(parameter, value);
		}
	}

}
