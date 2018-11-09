/*
 * Circlead - Develop and structure evolutionary Organisations
 * 
 * @author Matthias Wegner
 * @version 0.1
 * @since 01.07.2018
 * 
 */
package org.rogatio.circlead.control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dmfs.rfc5545.recur.Freq;
import org.rogatio.circlead.control.synchronizer.Connector;
import org.rogatio.circlead.control.synchronizer.ISynchronizer;
import org.rogatio.circlead.control.synchronizer.SynchronizerResult;
import org.rogatio.circlead.control.validator.IValidator;
import org.rogatio.circlead.control.validator.ValidationMessage;
import org.rogatio.circlead.control.validator.ValidationMessage.Type;
import org.rogatio.circlead.model.Parameter;
import org.rogatio.circlead.model.WorkitemStatusParameter;
import org.rogatio.circlead.model.WorkitemType;
import org.rogatio.circlead.model.data.ActivityDataitem;
import org.rogatio.circlead.model.data.CompetenceDataitem;
import org.rogatio.circlead.model.data.HowTo;
import org.rogatio.circlead.model.data.IDataRow;
import org.rogatio.circlead.model.data.Report;
import org.rogatio.circlead.model.data.TeamEntry;
import org.rogatio.circlead.model.work.Activity;
import org.rogatio.circlead.model.work.Competence;
import org.rogatio.circlead.model.work.IWorkitem;
import org.rogatio.circlead.model.work.Person;
import org.rogatio.circlead.model.work.Role;
import org.rogatio.circlead.model.work.Rolegroup;
import org.rogatio.circlead.model.work.Team;
import org.rogatio.circlead.util.ExcelUtil;
import org.rogatio.circlead.util.ObjectUtil;
import org.rogatio.circlead.util.StringUtil;
import org.rogatio.circlead.view.report.IReport;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * The Class Repository is a singleton-representation of all loaded and handled
 * data while runtime. This is the real core of the circlead-application.
 * 
 * @author Matthias Wegner
 */
public final class Repository {

	/** The Constant logger. */
	private final static Logger LOGGER = LogManager.getLogger(Repository.class);

	/**
	 * Gets the single instance of Repository.
	 *
	 * @return single instance of Repository
	 */
	public static Repository getInstance() {
		if (instance == null) {
			instance = new Repository();
		}
		return instance;
	}

	/** The instance. */
	private static Repository instance;

	/** The connector. */
	private Connector connector;

	/**
	 * Gets the roles.
	 *
	 * @param status the status
	 * @return the roles
	 */
	public List<Role> getRoles(WorkitemStatusParameter status) {
		List<Role> roles = new ArrayList<Role>();
		if (Repository.getInstance().getRolegroups().size() > 0) {
			for (Role role : getRoles()) {
				WorkitemStatusParameter s = WorkitemStatusParameter.get(role.getStatus());
				if (s == status) {
					roles.add(role);
				}
			}
		}
		return roles;
	}

	/**
	 * Gets the rolegroups with defined WorkitemStatusParameter.
	 *
	 * @param status the status of workitems
	 * @return the rolegroups which have the status
	 */
	public List<Rolegroup> getRolegroups(WorkitemStatusParameter status) {
		List<Rolegroup> rolegroups = new ArrayList<Rolegroup>();
		if (Repository.getInstance().getRolegroups().size() > 0) {
			for (Rolegroup rolegroup : getRolegroups()) {
				WorkitemStatusParameter s = WorkitemStatusParameter.get(rolegroup.getStatus());
				if (s == status) {
					rolegroups.add(rolegroup);
				}
			}
		}
		return rolegroups;
	}

	/**
	 * Checks if name is rolename.
	 *
	 * @param roleName the name to check
	 * @return true, if is role name
	 */
	public boolean isRoleName(String roleName) {
		for (String name : this.getRoleNames()) {
			if (name.equalsIgnoreCase(roleName)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Gets the subactivities with responsible role.
	 *
	 * @param roleTitle the role title
	 * @return the subactivities which have responsible role. Map which uses
	 *         activity as key and found subactivities as valueF
	 */
	public TreeMap<Activity, List<ActivityDataitem>> getSubactivitiesWithResponsible(String roleTitle) {
		TreeMap<Activity, List<ActivityDataitem>> map = new TreeMap<Activity, List<ActivityDataitem>>();
		List<Activity> allActivities = Repository.getInstance().getActivities();
		for (Activity activity : allActivities) {
			List<ActivityDataitem> ac = activity.getSubactivitiesWithResponsible(roleTitle);
			if (ObjectUtil.isListNotNullAndEmpty(ac)) {
				map.put(activity, ac);
			}
		}

		return map;
	}

	/**
	 * Gets the roles with competence.
	 *
	 * @param person     the person
	 * @param competence the competence
	 * @return the roles with competence
	 */
	public List<Role> getRolesWithCompetence(Person person, String competence) {
		List<Role> found = new ArrayList<Role>();
		ArrayList<Role> roles = this.getRolesWithPerson(person);
		for (Role role : roles) {
			List<String> temp = role.getCompetences();
			if (ObjectUtil.isListNotNullAndEmpty(temp)) {
				for (String c : temp) {
					if (competence.equals(c)) {
						found.add(role);
					}
				}
			}
		}

		return found;
	}

	/**
	 * Gets the skill of person competence.
	 *
	 * @param person     the person
	 * @param competence the competence
	 * @return the skill of person competence
	 */
	public int getSkillOfPersonCompetence(Person person, String competence) {
		ArrayList<Role> roles = this.getRolesWithPerson(person);
		String skill = "";
		for (Role role : roles) {
			List<String> temp = role.getCompetences();
			if (ObjectUtil.isListNotNullAndEmpty(temp)) {
				for (String c : temp) {
					if (competence.equals(c)) {
						skill = role.getDataitem().getSkill(person.getFullname());
					}
				}
			}
		}

		int i = 0;

		try {
			i = Integer.parseInt(skill);
		} catch (Exception e) {

		}

		return i;
	}

	/**
	 * Gets the parent roles.
	 *
	 * @param role the role
	 * @return the parent roles
	 */
	public List<Role> getParentRoles(Role role) {
		List<Role> parents = new ArrayList<Role>();
		getParentRoles(role, parents);
		return parents;
	}

	/**
	 * Gets the parent roles.
	 *
	 * @param role    the role
	 * @param parents the parents
	 * @return the parent roles
	 */
	public void getParentRoles(Role role, List<Role> parents) {
		if (role.getParentIdentifier() != null) {
			if (!role.getParentIdentifier().equals(role.getTitle())) {
				Role p = getRole(role.getParentIdentifier());
				if (p != null) {
					parents.add(0, p);
					getParentRoles(p, parents);
				}
			}
		}
	}

	/**
	 * Gets the competencies of person.
	 *
	 * @param person the person
	 * @return the competencies of person
	 */
	public List<String> getCompetenciesOfPerson(Person person) {
		List<String> list = new ArrayList<String>();

		ArrayList<Role> roles = this.getRolesWithPerson(person);
		for (Role role : roles) {
			List<String> temp = role.getCompetences();
			if (ObjectUtil.isListNotNullAndEmpty(temp)) {
				for (String c : temp) {
					if (!list.contains(c)) {
						list.add(c);
					}
				}
			}
		}

		return list;
	}
	
	/**
	 * Unfinished.
	 *
	 * @return the role names
	 */
/*	public void changeNameOfRole(String roleIdentifierOld, String roleIdentifierNew) {
		Role role = this.getRole(roleIdentifierOld);
		if (role!=null) {
			role.setTitle(roleIdentifierNew);
		}
		
		List<Team> teams = this.getTeamsWithRole(roleIdentifierOld);
		for (Team team : teams) {
			List<TeamEntry> entries = team.getTeamEntries();
			for (TeamEntry entry : entries) {
				if  (StringUtil.isNotNullAndNotEmpty(entry.getRoleIdentifier())) {
					if (entry.getRoleIdentifier().equals(roleIdentifierOld)) {
						entry.setRoleIdentifier(roleIdentifierNew);
					}
				}
			}
		}
		
		List<Activity> activities = this.getActivities();
		for (Activity activity : activities) {
			if (StringUtil.isNotNullAndNotEmpty(activity.getResponsibleIdentifier())) {
				if (activity.getResponsibleIdentifier().equals(roleIdentifierOld)) {
					activity.setResponsibleIdentifier(roleIdentifierNew);
				}
			}
			if (StringUtil.isNotNullAndNotEmpty(activity.getAccountableIdentifier())) {
				if (activity.getAccountableIdentifier().equals(roleIdentifierOld)) {
					activity.setAccountableIdentifier(roleIdentifierNew);
				}
			}
			if (ObjectUtil.isListNotNullAndEmpty(activity.getSupplierIdentifiers())) {
				
				for (String s : activity.getSupplierIdentifiers()) {
					
				}
				if (activity.getAccountableIdentifier().equals(roleIdentifierOld)) {
					activity.setAccountableIdentifier(roleIdentifierNew);
				}
			}
		}
	}*/

	/**
	 * Gets the names of all roles.
	 *
	 * @return the list of role names
	 */
	public List<String> getRoleNames() {
		List<Role> roles = this.getRoles();
		List<String> roleNames = new ArrayList<String>();
		for (Role role : roles) {
			roleNames.add(role.getTitle());
		}
		return roleNames;
	}

	/**
	 * Adds the orphaned role competencies to root competence.
	 */
	public void addOrphanedRoleCompetenciesToRootCompetence() {
		Map<String, List<Role>> competencies = getCompetencesFromRoles();
		for (String competence : competencies.keySet()) {

			Competence rc = getRootCompetence();
			if (!rc.containsCompetence(competence)) {
				CompetenceDataitem cd = new CompetenceDataitem();
				cd.setTitle(competence);

				StringBuilder sb = new StringBuilder();
				for (Role r : competencies.get(competence)) {
					sb.append(r.getTitle() + ", ");
				}

				cd.setDescription(sb.toString());
				rc.addCompetence(cd);
			}

		}
	}

	/**
	 * Adds a synchronizer to the Connector.
	 *
	 * @param synchronizer the synchronizer
	 */
	public void addSynchronizer(ISynchronizer synchronizer) {
		connector.addSynchronizer(synchronizer);
	}

	/**
	 * Instantiates a new repository.
	 */
	private Repository() {
		connector = new Connector();
	}

	/**
	 * Gets the list of workitems in repository.
	 *
	 * @return the workitems
	 */
	public List<IWorkitem> getWorkitems() {
		return workitems;
	}

	/** The list of all loaded or added workitems. */
	private List<IWorkitem> workitems = new ArrayList<IWorkitem>();

	/**
	 * Adds the items.
	 *
	 * @param workitems the workitems
	 */
	private void addItems(List<IWorkitem> workitems) {
		for (IWorkitem workitem : workitems) {
			if (!this.workitems.contains(workitem)) {
				this.workitems.add(workitem);
			}
		}
	}

	/**
	 * Gets the connector of the repository.
	 *
	 * @return the connector
	 */
	public Connector getConnector() {
		return connector;
	}

	/**
	 * Load rolegroup-workitems from synchronized system with Connector.
	 *
	 * @return the list of rolegroups
	 */
	public List<Rolegroup> loadRolegroups() {
		List<IWorkitem> rolegroups = connector.load(WorkitemType.ROLEGROUP);
		this.addItems(rolegroups);
		return ObjectUtil.castList(Rolegroup.class, rolegroups);
	}

	/**
	 * Load role-workitems from synchronized system with Connector.
	 *
	 * @return the list
	 */
	public List<Role> loadRoles() {
		List<IWorkitem> roles = connector.load(WorkitemType.ROLE);
		this.addItems(roles);
		return ObjectUtil.castList(Role.class, roles);
	}

	/**
	 * Load competencies.
	 *
	 * @return the list
	 */
	public List<Competence> loadCompetencies() {
		List<IWorkitem> competencies = connector.load(WorkitemType.COMPETENCE);
		this.addItems(competencies);
		return ObjectUtil.castList(Competence.class, competencies);
	}

	/**
	 * Load team-workitems from synchronized system with Connector.
	 *
	 * @return the list of teams
	 */
	public List<Team> loadTeams() {
		List<IWorkitem> teams = connector.load(WorkitemType.TEAM);
		this.addItems(teams);
		return ObjectUtil.castList(Team.class, teams);
	}

	/**
	 * Load activities from synchronized system with Connector.
	 *
	 * @return the list of activities
	 */
	public List<Activity> loadActivities() {
		List<IWorkitem> activities = connector.load(WorkitemType.ACTIVITY);
		this.addItems(activities);
		return ObjectUtil.castList(Activity.class, activities);
	}

	/**
	 * Load persons from synchronized system with Connector.
	 *
	 * @return the list of persons
	 */
	public List<Person> loadPersons() {
		List<IWorkitem> persons = connector.load(WorkitemType.PERSON);
		this.addItems(persons);
		return ObjectUtil.castList(Person.class, persons);
	}

	/**
	 * The index of all loaded reports. Is a json-representation of the metadata of
	 * the howtos
	 */
	private List<String> indexReports = new ArrayList<String>();

	/**
	 * The index of all loaded howtos. Is a json-representation of the metatdata of
	 * the reports
	 */
	private List<String> indexHowtos = new ArrayList<String>();

	/**
	 * Gets the index how tos.
	 *
	 * @return the index how tos
	 */
	public List<HowTo> getIndexHowTos() {

		List<HowTo> howtos = new ArrayList<HowTo>();

		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_NULL);

		for (String ht : indexHowtos) {
			try {
				HowTo howto = mapper.readValue(ht, HowTo.class);
				howtos.add(howto);
			} catch (JsonParseException e) {
			} catch (JsonMappingException e) {
			} catch (IOException e) {
			}
		}

		return howtos;
	}

	/**
	 * Write excel.
	 *
	 * @param filename the filename
	 * @param type     the type
	 * @param fields   the fields
	 */
	public void writeExcel(String filename, WorkitemType type, List<Parameter> fields) {
		List<IWorkitem> list = getWorkitems();
		List<Map<Parameter, Object>> dataMap = new ArrayList<Map<Parameter, Object>>();

		for (IWorkitem wi : list) {
			if (type.isTypeOf(wi) && wi instanceof IDataRow) {
				dataMap.add(((IDataRow) wi).getDataRow());
			}
		}

		LOGGER.debug("Write " + dataMap.size() + " Workitems (" + type.getName() + ") to '" + filename + "'");

		ExcelUtil.writeExcel(filename, dataMap, fields);
	}

	/**
	 * Gets the index reports.
	 *
	 * @return the index reports
	 */
	public List<Report> getIndexReports() {

		List<Report> rs = new ArrayList<Report>();

		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_NULL);

		for (String ht : indexReports) {
			try {
				Report howto = mapper.readValue(ht, Report.class);
				rs.add(howto);
			} catch (JsonParseException e) {
			} catch (JsonMappingException e) {
			} catch (IOException e) {
			}
		}

		return rs;
	}

	/**
	 * Load index reports.
	 */
	public void loadIndexReports() {
		indexReports = connector.loadIndex(WorkitemType.REPORT);
	}

	/**
	 * Load index how tos.
	 */
	public void loadIndexHowTos() {
		indexHowtos = connector.loadIndex(WorkitemType.HOWTO);
	}

	/**
	 * Gets the.
	 *
	 * @param id the id
	 * @return the i workitem
	 */
	public IWorkitem get(String id) {
		for (IWorkitem workitem : workitems) {
			if (workitem.getId().toString().contains(id)) {
				return workitem;
			}
		}
		return null;
	}

	/**
	 * Gets the rolegroups.
	 *
	 * @return the rolegroups
	 */
	public List<Rolegroup> getRolegroups() {
		List<Rolegroup> rolegroups = new ArrayList<Rolegroup>();
		for (IWorkitem workitem : workitems) {
			if (WorkitemType.ROLEGROUP.isTypeOf(workitem)) {
				rolegroups.add((Rolegroup) workitem);
			}
		}
		Collections.sort(rolegroups);
		return rolegroups;
	}

	/**
	 * Checks if is person data value entity.
	 *
	 * @param key   the key
	 * @param value the value
	 * @return true, if is person data value entity
	 */
	public boolean isPersonDataValueEntity(String key, String value) {
		for (String v : getPersonDataValues(key)) {
			if (v.equalsIgnoreCase(value)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Gets the person data values.
	 *
	 * @param key the key
	 * @return the person data values
	 */
	public List<String> getPersonDataValues(String key) {
		List<String> abbr = new ArrayList<String>();
		for (Person person : getPersons()) {
			String a = person.getDataValue(key);
			if (a != null) {
				abbr.add(a);
			}
		}
		return abbr;
	}

	/**
	 * Gets the teams with category.
	 *
	 * @param category the category
	 * @return the teams with category
	 */
	public List<Team> getTeamsWithCategory(String category) {
		List<Team> teams = new ArrayList<Team>();

		for (Team team : getTeams()) {
			if (StringUtil.isNotNullAndNotEmpty(team.getCategory())) {
				if (team.getCategory().equals(category)) {
					teams.add(team);
				}
			}
		}

		return teams;
	}

	/**
	 * Gets the teams.
	 *
	 * @return the teams
	 */
	public List<Team> getTeams() {
		List<Team> teams = new ArrayList<Team>();
		for (IWorkitem workitem : workitems) {
			if (WorkitemType.TEAM.isTypeOf(workitem)) {
				teams.add((Team) workitem);
			}
		}
		Collections.sort(teams);
		return teams;
	}

	/**
	 * Gets the persons.
	 *
	 * @return the persons
	 */
	public List<Person> getPersons() {
		List<Person> persons = new ArrayList<Person>();
		for (IWorkitem workitem : workitems) {
//			System.out.println(workitem.getType());
			if (WorkitemType.PERSON.isTypeOf(workitem)) {
				persons.add((Person) workitem);
			}
		}
		Collections.sort(persons);
		return persons;
	}

	/**
	 * Gets the activities.
	 *
	 * @return the activities
	 */
	public List<Activity> getActivities() {
		List<Activity> activities = new ArrayList<Activity>();
		for (IWorkitem workitem : workitems) {
			if (WorkitemType.ACTIVITY.isTypeOf(workitem)) {
				activities.add((Activity) workitem);
			}
		}

		Collections.sort(activities);

		return activities;
	}

	/**
	 * Gets the competencies.
	 *
	 * @return the competencies
	 */
	public List<Competence> getCompetencies() {
		return this.getRootCompetence().getCompetencies();
	}

	/**
	 * Gets the roles.
	 *
	 * @return the roles
	 */
	public List<Role> getRoles() {
		List<Role> roles = new ArrayList<Role>();
		for (IWorkitem workitem : workitems) {
			if (WorkitemType.ROLE.isTypeOf(workitem)) {
				roles.add((Role) workitem);
			}
		}

		Collections.sort(roles);

		return roles;
	}

	/**
	 * Gets the person with given identifier. Identifier could be id and fullname of
	 * person
	 *
	 * @param identifier the identifier
	 * @return the person
	 */
	public Person getPerson(String identifier) {

		for (Person person : getPersons()) {
			if (person.containsId(identifier)) {
				return person;
			}

			String fullname = person.getFullname();
			if (fullname.equals(identifier)) {
				return person;
			}
		}
		return null;
	}

	/**
	 * Adds the workitem.
	 *
	 * @param workitem the workitem
	 * @return the list
	 */
	public List<SynchronizerResult> addWorkitem(IWorkitem workitem) {
		List<SynchronizerResult> results = getConnector().add(workitem);
		return results;
	}

	/**
	 * Update workitems.
	 *
	 * @return the list
	 */
	public List<SynchronizerResult> updateWorkitems() {
		List<SynchronizerResult> results = new ArrayList<SynchronizerResult>();
		for (IWorkitem workitem : getWorkitems()) {
			results.addAll(getConnector().update(workitem));
		}
		return results;
	}

	/**
	 * Gets the roles.
	 *
	 * @param rolegroupIdentifier the rolegroup identifier
	 * @return the roles
	 */
	public List<Role> getRoles(String rolegroupIdentifier) {
		List<Role> roleIdentifiers = new ArrayList<Role>();
		for (IWorkitem workitem : workitems) {
			if (workitem instanceof Role) {
				Role r = (Role) workitem;
				if (StringUtil.isNotNullAndNotEmpty(r.getRolegroupIdentifier())) {
					if (r.getRolegroupIdentifier().equals(rolegroupIdentifier)) {
						if (!roleIdentifiers.contains(r)) {
							roleIdentifiers.add(r);
						}
					}
				}
			}
		}
		return roleIdentifiers;
	}

	/**
	 * Gets the average allokation in teams.
	 *
	 * @param person the person
	 * @param freq   the freq
	 * @return the average allokation in teams
	 */
	public double getAverageAllokationInTeams(Person person, Freq freq) {
		List<Team> foundTeams = Repository.getInstance().getTeamsWithMember(person);
		double sum = 0;
		if (ObjectUtil.isListNotNullAndEmpty(foundTeams)) {
			for (Team team : foundTeams) {
				WorkitemStatusParameter wsp = WorkitemStatusParameter.get(team.getStatus());
				if ((wsp != WorkitemStatusParameter.PAUSED) || (wsp != WorkitemStatusParameter.CLOSED)
						|| (wsp != WorkitemStatusParameter.INACTIVE)) {
					sum += team.getAverageAllokation(person, freq);
				}
			}
		}
		return sum;
	}

	/**
	 * Gets the average allokation in organisation.
	 *
	 * @param personIdentifier the person identifier
	 * @param freq             the freq
	 * @return the average allokation in organisation
	 */
	public double getAverageAllokationInOrganisation(String personIdentifier, Freq freq) {
		List<Role> orgRoles = Repository.getInstance().getOrganisationalRolesWithPerson(personIdentifier);
		double sumR = 0;
		for (Role role : orgRoles) {
			boolean skip = false;
			if (role.getDataitem().hasRepresentation(personIdentifier)) {
				String representation = role.getDataitem().getRepresentation(personIdentifier);
				WorkitemStatusParameter status = WorkitemStatusParameter.get(representation);
				if (status != null) {
					if ((status == WorkitemStatusParameter.PAUSED) || (status == WorkitemStatusParameter.CLOSED)
							|| (status == WorkitemStatusParameter.INACTIVE)) {
						skip = true;
					}
				}
			}

			if (!skip) {
				String rule = role.getRecurrenceRule(personIdentifier);
				if (StringUtil.isNotNullAndNotEmpty(rule)) {
					CircleadRecurrenceRule crr = new CircleadRecurrenceRule(rule);
					double allok = crr.getAverageAllokation(freq);
					sumR += allok;
				}
			}
		}
		return sumR;
	}

	/**
	 * Gets the activities.
	 *
	 * @param roleIdentifier the responsible role identifier
	 * @return the activities
	 */
	public List<Activity> getActivities(String roleIdentifier) {
		List<Activity> activityIdentifiers = new ArrayList<Activity>();
		for (IWorkitem workitem : workitems) {
			if (workitem instanceof Activity) {
				Activity a = (Activity) workitem;
				if (StringUtil.isNotNullAndNotEmpty(a.getResponsibleIdentifier())) {
					if (a.getResponsibleIdentifier().equals(roleIdentifier)) {
						if (!activityIdentifiers.contains(a)) {
							activityIdentifiers.add(a);
						}
					}
				}
			}
		}
		return activityIdentifiers;
	}

	/**
	 * Gets the role identifiers.
	 *
	 * @param rolegroupIdentifier the rolegroup identifier
	 * @return the role identifiers
	 */
	public List<String> getRoleIdentifiers(String rolegroupIdentifier) {
		List<String> roleIdentifiers = new ArrayList<String>();
		for (IWorkitem workitem : workitems) {
			if (workitem instanceof Role) {
				Role r = (Role) workitem;
				if (StringUtil.isNotNullAndNotEmpty(r.getRolegroupIdentifier())) {
					if (r.getRolegroupIdentifier().equals(rolegroupIdentifier)) {
						if (!roleIdentifiers.contains(r.getTitle())) {
							roleIdentifiers.add(r.getTitle());
						}
					}
				}
			}
		}
		return roleIdentifiers;
	}

	/**
	 * Gets the rolegroup with given identifier. Identifier could be id and title.
	 *
	 * @param identifier the identifier of the rolegroup
	 * @return the rolegroup
	 */
	public Rolegroup getRolegroup(String identifier) {
		for (IWorkitem workitem : workitems) {
			if (WorkitemType.ROLEGROUP.isTypeOf(workitem)) {
				Rolegroup rolegroup = (Rolegroup) workitem;

				if (rolegroup.containsId(identifier)) {
					return rolegroup;
				}
				if (rolegroup.getTitle().equals(identifier)) {
					return rolegroup;
				}
			}
		}

		return null;
	}

	/**
	 * Checks for unique role abbreviation. The id is depended from synchronized
	 * system and not a usefull entity.
	 *
	 * @param r the role which is checked for unique abbreviation
	 * @return true, if role has unique abbreviation
	 */
	public boolean hasUniqueRoleAbbreviation(Role r) {

		String abr = null;

		if (r == null) {
			return false;
		} else {
			if (!r.hasAbbreviation()) {
				return false;
			} else {
				abr = r.getAbbreviation();
			}
		}

		int counter = 0;

		for (Role role : this.getRoles()) {
			if (role.hasAbbreviation()) {
				if (role.getAbbreviation().equals(abr)) {
					counter++;
				}
			}
		}

		return counter == 1;
	}

	/**
	 * Checks for unique role title. The id is depended from synchronized system and
	 * not a usefull entity.
	 *
	 * @param r the role which is checked for unique title
	 * @return true, if role has unique title
	 */
	public boolean hasUniqueRoleTitle(Role r) {

		String title = null;

		if (r == null) {
			return false;
		} else {
			if (!r.hasTitle()) {
				return false;
			} else {
				title = r.getTitle();
			}
		}

		int counter = 0;

		for (Role role : this.getRoles()) {
			if (role.hasTitle()) {
				if (role.getTitle().equals(title)) {
					counter++;
				}
			}
		}

		return counter == 1;
	}

	/**
	 * Gets the roles of a list of identifiers.
	 *
	 * @param roleIdentifiers the role identifiers list
	 * @return the roles which are found by identifiers
	 */
	public List<Role> getRoles(List<String> roleIdentifiers) {
		List<Role> foundRoles = new ArrayList<Role>();
		if (roleIdentifiers == null) {
			return foundRoles;
		}
		for (String identifier : roleIdentifiers) {
			Role r = getRole(identifier);
			if (r != null) {
				foundRoles.add(r);
			}
		}
		return foundRoles;
	}

	/**
	 * Gets the role by role-identifier.
	 *
	 * @param identifier the identifier of the role. Could be id, abbreviation,
	 *                   title or synonym
	 * @return the role
	 */
	public Role getRole(String identifier) {
		for (IWorkitem workitem : workitems) {
			if (WorkitemType.ROLE.isTypeOf(workitem)) {
				Role role = (Role) workitem;

				if (role.containsId(identifier)) {
					return role;
				}
				if (StringUtil.isNotNullAndNotEmpty(role.getAbbreviation())) {
					if (role.getAbbreviation().equals(identifier)) {
						return role;
					}
				}
				if (identifier != null) {
					if (role.getTitle().equals(identifier.trim())) {
						return role;
					}
				}
				if (role.getSynonyms() != null) {
					for (String synonym : role.getSynonyms()) {
						if (synonym.equals(identifier)) {
							return role;
						}
					}
				}

			}
		}

		return null;
	}

	/**
	 * Gets the team persons with role.
	 *
	 * @param role the role
	 * @return the team persons with role
	 */
	public List<String> getTeamPersonsWithRole(Role role) {
		List<Team> teams = getTeamsWithRole(role);
		List<String> persons = new ArrayList<String>();
		for (Team team : teams) {
			List<String> teamMembers = team.getTeamMembers(role);
			for (String personIdentifier : teamMembers) {
				if (!persons.contains(personIdentifier)) {
					persons.add(personIdentifier);
				}
			}
		}
		return persons;
	}

	/**
	 * Gets the teams with role in team.
	 *
	 * @param role the role which should be in team
	 * @return the teams with role in team
	 */
	public List<Team> getTeamsWithRole(Role role) {
		return getTeamsWithRole(role.getTitle());
	}
	
	/**
	 * Gets the teams with role.
	 *
	 * @param roleIdentifier the role identifier
	 * @return the teams with role
	 */
	public List<Team> getTeamsWithRole(String roleIdentifier) {
		List<Team> list = new ArrayList<Team>();

		for (Team team : this.getTeams()) {
			List<TeamEntry> entries = team.getTeamEntries();
			if (ObjectUtil.isListNotNullAndEmpty(entries)) {
				for (TeamEntry entry : entries) {
					if (StringUtil.isNotNullAndNotEmpty(entry.getRoleIdentifier())) {
						if (entry.getRoleIdentifier().equals(roleIdentifier)) {
							list.add(team);
						}
					}
				}
			}
		}

		return list;
	}

	/**
	 * Find all teams which have named person in team of given team-list.
	 *
	 * @param person   the person which should be in team
	 * @param teamList the team list which should be queried
	 * @return the teams with person in team
	 */
	public List<Team> getTeamsWithMember(Person person, List<Team> teamList) {
		List<Team> list = new ArrayList<Team>();

		for (Team team : teamList) {
			List<TeamEntry> entries = team.getTeamEntries();
			if (ObjectUtil.isListNotNullAndEmpty(entries)) {
				for (TeamEntry entry : entries) {
					if (entry.getPersons() != null) {
						if (entry.getPersons().contains(person.getFullname())) {
							if (!list.contains(team)) {
								list.add(team);
							}
						}
					}
				}
			}
		}

		return list;
	}

	/**
	 * Gets the roles with person.
	 *
	 * @param person the person
	 * @return the roles with person
	 */
	public ArrayList<Role> getRolesWithPerson(Person person) {
		ArrayList<Role> found = getOrganisationalRolesWithPerson(person);
		ArrayList<Role> found2 = getTeamRolesWithPerson(person);
		for (Role role : found2) {
			if (!found.contains(role)) {
				found.add(role);
			}
		}
		return found;
	}

	/**
	 * Gets the team roles.
	 *
	 * @param person the person
	 * @return the team roles
	 */
	public ArrayList<Role> getTeamRolesWithPerson(Person person) {
		List<Team> teams = getTeamsWithMember(person);
		ArrayList<Role> foundRoles = new ArrayList<Role>();

		for (Team team : teams) {
			List<TeamEntry> entries = team.getTeamEntries();
			for (TeamEntry entry : entries) {
				if (entry.containsPerson(person)) {
					Role r = getRole(entry.getRoleIdentifier());
					if (r != null) {
						if (!foundRoles.contains(r)) {
							foundRoles.add(r);
						}
					}
				}
			}

		}

		return foundRoles;
	}

	/**
	 * Gets the teams with the person in team.
	 *
	 * @param person the person which should be in the team
	 * @return the teams with member
	 */
	public List<Team> getTeamsWithMember(Person person) {
		return getTeamsWithMember(person, this.getTeams());
	}

	/**
	 * Gets the team with given identifier.
	 *
	 * @param identifier the identifier of a team
	 * @return the team
	 */
	public Team getTeam(String identifier) {
		for (IWorkitem workitem : workitems) {
			if (WorkitemType.TEAM.isTypeOf(workitem)) {
				Team team = (Team) workitem;

				if (team.containsId(identifier)) {
					return team;
				}

				if (identifier != null) {
					if (team.getTitle().equals(identifier.trim())) {
						return team;
					}
				}

			}
		}

		return null;
	}

	/**
	 * Gets the activity with subactivity.
	 *
	 * @param identifier the identifier
	 * @return the activity with subactivity
	 */
	public Activity getActivityWithSubactivity(String identifier) {
		for (IWorkitem workitem : workitems) {
			if (WorkitemType.ACTIVITY.isTypeOf(workitem)) {
				Activity activity = (Activity) workitem;
				List<ActivityDataitem> subactivities = activity.getSubactivities();
				if (subactivities != null) {
					for (ActivityDataitem activityDataitem : subactivities) {
						if (activityDataitem.containsId(identifier)) {
							return activity;
						}
						if (StringUtil.isNotNullAndNotEmpty(activityDataitem.getAid())) {
							if (activityDataitem.getAid().equals(identifier)) {
								return activity;
							}
						}
						if (activityDataitem.getTitle().equals(identifier)) {
							return activity;
						}
					}
				}
			}
		}

		return null;
	}

	/**
	 * Gets the activity with given identifier.
	 *
	 * @param identifier the identifier of an activity
	 * @return the activity
	 */
	public Activity getActivity(String identifier) {
		for (IWorkitem workitem : workitems) {
			if (WorkitemType.ACTIVITY.isTypeOf(workitem)) {
				Activity activity = (Activity) workitem;

				if (activity.containsId(identifier)) {
					return activity;
				}
				if (StringUtil.isNotNullAndNotEmpty(activity.getAid())) {
					if (activity.getAid().equals(identifier)) {
						return activity;
					}
				}
				if (activity.getTitle().equals(identifier)) {
					return activity;
				}
			}
		}

		return null;
	}

	/**
	 * Gets the report with given identifier.
	 *
	 * @param identifier the identifier of a report
	 * @return the report
	 */
	public Report getReport(String identifier) {
		for (Report ht : this.getIndexReports()) {

			if (identifier.equalsIgnoreCase(ht.getId())) {
				return ht;
			}

			if (identifier.equalsIgnoreCase(ht.getTitle())) {
				return ht;
			}
		}

		return null;
	}

	/**
	 * Gets the how to of given identifier.
	 *
	 * @param identifier the identifier of an howto
	 * @return the how to
	 */
	public HowTo getHowTo(String identifier) {
		for (HowTo ht : this.getIndexHowTos()) {

			if (identifier.equalsIgnoreCase(ht.getId())) {
				return ht;
			}

			if (identifier.equalsIgnoreCase(ht.getTitle())) {
				return ht;
			}
		}

		return null;
	}

	/**
	 * Gets the root competence.
	 *
	 * @return the root competence
	 */
	public Competence getRootCompetence() {
		for (IWorkitem workitem : workitems) {
			if (WorkitemType.COMPETENCE.isTypeOf(workitem)) {
				return (Competence) workitem;
			}
		}
		return null;
	}

	/**
	 * Gets the root rolegroups.
	 *
	 * @return the root rolegroups
	 */
	public List<Rolegroup> getRootRolegroups() {
		List<Rolegroup> rootRoles = new ArrayList<Rolegroup>();
		for (Rolegroup role : this.getRolegroups()) {
			if (role.getParentIdentifier() == null) {
				rootRoles.add(role);
			}
		}
		return rootRoles;
	}

	/**
	 * Gets the root roles.
	 *
	 * @return the root roles
	 */
	public List<Role> getRootRoles() {
		List<Role> rootRoles = new ArrayList<Role>();
		for (Role role : this.getRoles()) {
			if (role.getParentIdentifier() == null) {
				rootRoles.add(role);
			}
		}
		return rootRoles;
	}

	/**
	 * Gets the root roles.
	 *
	 * @param comparator the comparator
	 * @return the root roles
	 */
	public List<Role> getRootRoles(Comparator<Role> comparator) {
		List<Role> rootRoles = new ArrayList<Role>();
		for (Role role : this.getRoles()) {
			if (role.getParentIdentifier() == null) {
				rootRoles.add(role);
			}
		}

		Collections.sort(rootRoles, comparator);

		return rootRoles;
	}

	/**
	 * Gets the competences from roles.
	 *
	 * @return the competences from roles
	 */
	public Map<String, List<Role>> getCompetencesFromRoles() {
		List<String> competencies = new ArrayList<String>();
		for (Role role : this.getRoles()) {
			List<String> temp = role.getCompetences();
			if (ObjectUtil.isListNotNullAndEmpty(temp)) {
				for (String c : temp) {
					if (!competencies.contains(c)) {
						competencies.add(c);
					}
				}
			}
		}
		Collections.sort(competencies);
		Map<String, List<Role>> map = new TreeMap<String, List<Role>>();
		for (String competence : competencies) {
			List<Role> foundRoles = findRolesWithCompetence(competence);
			map.put(competence, foundRoles);
		}
		return map;
	}

	/**
	 * Find roles with competence.
	 *
	 * @param competence the competence
	 * @return the list
	 */
	public List<Role> findRolesWithCompetence(String competence) {
		List<Role> foundRoles = new ArrayList<Role>();
		for (Role r : this.getRoles()) {
			if (r.getCompetences() != null) {
				if (r.getCompetences().contains(competence)) {
					foundRoles.add(r);
				}
			}
		}
		return foundRoles;
	}

	/**
	 * Gets the role children of a role.
	 *
	 * @param roleIdentifier the role identifier of the parent role
	 * @return the role children which have given parent
	 */
	public List<Role> getRoleChildren(String roleIdentifier) {
		List<Role> childRoles = new ArrayList<Role>();

		if (roleIdentifier == null) {
			return null;
		}

		for (Role role : this.getRoles()) {

			if (roleIdentifier.equalsIgnoreCase(role.getParentIdentifier())) {
				childRoles.add(role);
			}

		}

		return childRoles;
	}

	/**
	 * Gets the role children.
	 *
	 * @param roleIdentifier the role identifier
	 * @param comparator     the comparator
	 * @return the role children
	 */
	public List<Role> getRoleChildren(String roleIdentifier, Comparator<Role> comparator) {
		List<Role> childRoles = new ArrayList<Role>();

		if (roleIdentifier == null) {
			return null;
		}

		for (Role role : this.getRoles()) {

			if (roleIdentifier.equalsIgnoreCase(role.getParentIdentifier())) {
				childRoles.add(role);
			}

		}

		Collections.sort(childRoles, comparator);

		return childRoles;
	}

	/**
	 * Adds the reports.
	 */
	public void addReports() {
		for (IReport report : this.reports) {
			this.getConnector().add(report);
		}
	}

	/**
	 * Write index.
	 */
	public void writeIndex() {
		this.getConnector().writeIndex();
	}

	/**
	 * Update reports.
	 *
	 * @return the list
	 */
	public List<SynchronizerResult> updateReports() {
		List<SynchronizerResult> results = new ArrayList<SynchronizerResult>();
		for (IReport report : this.reports) {
			results.addAll(this.getConnector().update(report));
		}
		return results;
	}

	/** The reports. */
	private List<IReport> reports = new ArrayList<IReport>();

	/**
	 * Adds the report.
	 *
	 * @param report the report
	 */
	public void addReport(IReport report) {
		reports.add(report);
	}

	/**
	 * Adds the reports.
	 *
	 * @param reports the reports
	 */
	public void addReports(List<IReport> reports) {
		if (reports != null) {
			for (IReport iReport : reports) {
				reports.add(iReport);
			}
		}
	}

	/**
	 * Gets the reports.
	 *
	 * @return the reports
	 */
	public List<IReport> getReports() {
		return reports;
	}

	/**
	 * Gets the competence children.
	 *
	 * @param competence the competence
	 * @return the competence children
	 */
	public List<Competence> getCompetenceChildren(String competence) {
		List<Competence> childs = new ArrayList<Competence>();

		Competence c = this.getRootCompetence();

		if (c != null) {
			return c.getChildren(competence);
		}

		return childs;
	}

	/**
	 * Gets the rolegroup children.
	 *
	 * @param rolegroupIdentifier the rolegroup identifier
	 * @return the rolegroup children
	 */
	public List<Rolegroup> getRolegroupChildren(String rolegroupIdentifier) {
		List<Rolegroup> childRolegroups = new ArrayList<Rolegroup>();

		if (rolegroupIdentifier == null) {
			return null;
		}

		for (Rolegroup rolegroup : this.getRolegroups()) {
			if (rolegroupIdentifier.equalsIgnoreCase(rolegroup.getParentIdentifier())) {
				childRolegroups.add(rolegroup);
			}
		}

		return childRolegroups;
	}

	/**
	 * Validate all instances of IValidator. If available all synchronizers and
	 * workitems
	 *
	 * @return the list
	 */
	public List<ValidationMessage> validate() {
		List<IValidator> validators = new ArrayList<IValidator>();

		Collection<ISynchronizer> s = this.getConnector().getSynchronizer();
		for (Iterator<ISynchronizer> iterator = s.iterator(); iterator.hasNext();) {
			ISynchronizer iSynchronizer = (ISynchronizer) iterator.next();
			if (iSynchronizer instanceof IValidator) {
				validators.add((IValidator) iSynchronizer);
			}
		}

		for (IWorkitem workitem : workitems) {
			if (workitem instanceof IValidator) {
				validators.add((IValidator) workitem);
			}
		}

		List<ValidationMessage> allMessages = new ArrayList<ValidationMessage>();

		for (IValidator validator : validators) {
			List<ValidationMessage> messages = validator.validate();
			for (ValidationMessage m : messages) {
				allMessages.add(m);
				if (m.getType() == Type.INFO) {

					if (m.getSolution() != null) {
						LOGGER.info("" + m.getMessage() + " -> " + m.getSolution());
					} else {
						LOGGER.info("" + m.getMessage());
					}

				}
				if (m.getType() == Type.WARNING) {
					if (m.getSolution() != null) {
						LOGGER.warn("" + m.getMessage() + " -> " + m.getSolution());
					} else {
						LOGGER.warn(m.getCause() + ": " + m.getMessage());
					}
				}
				if (m.getType() == Type.ERROR) {
					if (m.getSolution() != null) {
						LOGGER.error("" + m.getMessage() + " -> " + m.getSolution());
					} else {
						LOGGER.error(m.getCause() + ": " + m.getMessage());
					}
				}
				if (m.getType() == Type.DEBUG) {
					if (m.getSolution() != null) {
						LOGGER.debug("" + m.getMessage() + " -> " + m.getSolution());
					} else {
						LOGGER.debug(m.getMessage());
					}
				}
			}
		}
		return allMessages;

	}

	/**
	 * Gets the organisational roles with person.
	 *
	 * @param person the person
	 * @return the organisational roles with person
	 */
	public ArrayList<Role> getOrganisationalRolesWithPerson(Person person) {
		if (person == null) {
			return null;
		}
		return getOrganisationalRolesWithPerson(person.getFullname());
	}

	/**
	 * Gets the organisational (not team) roles with are taken by person.
	 *
	 * @param person the personIdentifier which should be identify taken roles
	 * @return the roles with person who holds a role
	 */
	public ArrayList<Role> getOrganisationalRolesWithPerson(String person) {
		ArrayList<Role> foundRoles = new ArrayList<Role>();
		for (Role role : getRoles()) {
			List<String> persons = role.getPersonIdentifiers();
			for (String p : persons) {
				if (p.equals(person)) {
					foundRoles.add(role);
				}
			}
		}
		return foundRoles;
	}

}
