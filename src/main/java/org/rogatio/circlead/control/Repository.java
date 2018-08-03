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
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.rogatio.circlead.control.ValidationMessage.Type;
import org.rogatio.circlead.control.synchronizer.AtlassianSynchronizer;
import org.rogatio.circlead.control.synchronizer.Connector;
import org.rogatio.circlead.control.synchronizer.FileSynchronizer;
import org.rogatio.circlead.control.synchronizer.ISynchronizer;
import org.rogatio.circlead.model.WorkitemType;
import org.rogatio.circlead.model.data.HowTo;
import org.rogatio.circlead.model.data.RoleDataitem;
import org.rogatio.circlead.model.work.Activity;
import org.rogatio.circlead.model.work.IWorkitem;
import org.rogatio.circlead.model.work.Person;
import org.rogatio.circlead.model.work.Role;
import org.rogatio.circlead.model.work.Rolegroup;
import org.rogatio.circlead.util.ObjectUtil;
import org.rogatio.circlead.util.StringUtil;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

// TODO: Auto-generated Javadoc
/**
 * The Class Repository.
 */
public class Repository {

	/** The Constant logger. */
	final static Logger logger = LogManager.getLogger(Repository.class);

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
	 * Instantiates a new repository.
	 */
	private Repository() {
		connector = new Connector();

		ISynchronizer asynchronizer = new AtlassianSynchronizer("CIRCLEAD");
		connector.addSynchronizer(asynchronizer);

		ISynchronizer fsynchronizer = new FileSynchronizer("data");
		connector.addSynchronizer(fsynchronizer);
	}

	/**
	 * Gets the workitems.
	 *
	 * @return the workitems
	 */
	public List<IWorkitem> getWorkitems() {
		return workitems;
	}

	/** The workitems. */
	private List<IWorkitem> workitems = new ArrayList<IWorkitem>();

	/**
	 * Adds the items.
	 *
	 * @param workitems
	 *            the workitems
	 */
	private void addItems(List<IWorkitem> workitems) {
		for (IWorkitem workitem : workitems) {
			if (!this.workitems.contains(workitem)) {
				this.workitems.add(workitem);
			}
		}
	}

	/**
	 * Gets the connector.
	 *
	 * @return the connector
	 */
	public Connector getConnector() {
		return connector;
	}

	/**
	 * Load rolegroups.
	 *
	 * @return the list
	 */
	public List<Rolegroup> loadRolegroups() {
		List<IWorkitem> rolegroups = connector.load(WorkitemType.ROLEGROUP);
		this.addItems(rolegroups);
		return ObjectUtil.castList(Rolegroup.class, rolegroups);
	}

	/**
	 * Load roles.
	 *
	 * @return the list
	 */
	public List<Role> loadRoles() {
		List<IWorkitem> roles = connector.load(WorkitemType.ROLE);
		this.addItems(roles);
		return ObjectUtil.castList(Role.class, roles);
	}

	public List<Activity> loadActivities() {
		List<IWorkitem> activities = connector.load(WorkitemType.ACTIVITY);
		this.addItems(activities);
		return ObjectUtil.castList(Activity.class, activities);
	}

	/**
	 * Load persons.
	 *
	 * @return the list
	 */
	public List<Person> loadPersons() {
		List<IWorkitem> persons = connector.load(WorkitemType.PERSON);
		this.addItems(persons);
		return ObjectUtil.castList(Person.class, persons);
	}

	private List<String> indexHowtos = new ArrayList<String>();

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

	public void loadIndexHowTos() {
		indexHowtos = connector.loadIndex(WorkitemType.HOWTO);
	}

	/**
	 * Gets the.
	 *
	 * @param id
	 *            the id
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
		return rolegroups;
	}

	public List<Person> getPersons() {
		List<Person> roles = new ArrayList<Person>();
		for (IWorkitem workitem : workitems) {
			if (WorkitemType.PERSON.isTypeOf(workitem)) {
				roles.add((Person) workitem);
			}
		}
		return roles;
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
		return roles;
	}

	/**
	 * Gets the person.
	 *
	 * @param identifier
	 *            the identifier
	 * @return the person
	 */
	public Person getPerson(String identifier) {

		// if (identifier.contains("arsten")) {
		// logger.debug("i: " + identifier + " (" + identifier.length() + ")");
		// }

		for (Person person : getPersons()) {
			// if (WorkitemType.PERSON.isTypeOf(workitem)) {
			// Person person = (Person) workitem;
			if (person.containsId(identifier)) {
				return person;
			}

			String fullname = person.getFullname();

			// if (fullname.contains("arsten")) {
			// logger.debug("p: " + fullname + " (" + fullname.length() + ")");
			// }

			/*
			 * String match = "arsten ";
			 * 
			 * if (identifier.contains(match) && person.getFullname().contains(match)) { logger.debug("identifier=" + identifier+ " "+identifier.length());
			 * String fullname = person.getFullname(); logger.debug("fullname=" + person.getFullname()+" "+fullname.length());
			 * logger.debug(identifier.equals(person.getFullname())); for (int i = 0; i < identifier.length(); i++) { char c = identifier.charAt(i);
			 * logger.debug("id: "+c); } for (int i = 0; i < fullname.length(); i++) { char c = fullname.charAt(i); logger.debug("fu: "+c); } }
			 */

			if (fullname.equals(identifier)) {
				// if (identifier.contains("arsten")) {
				// logger.debug(identifier + " = " + fullname);
				// }
				return person;
			}
			// }
		}
		return null;
	}

	/**
	 * Gets the roles.
	 *
	 * @param rolegroupIdentifier
	 *            the rolegroup identifier
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

	public List<Activity> getActivities(String roleIdentifier) {
		List<Activity> activityIdentifiers = new ArrayList<Activity>();
		for (IWorkitem workitem : workitems) {
			if (workitem instanceof Activity) {
				Activity a = (Activity) workitem;
				if (StringUtil.isNotNullAndNotEmpty(a.getRoleIdentifier())) {
					if (a.getRoleIdentifier().equals(roleIdentifier)) {
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
	 * @param rolegroupIdentifier
	 *            the rolegroup identifier
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
	 * Gets the rolegroup.
	 *
	 * @param identifier
	 *            the identifier
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

	public boolean hasUniqueRoleIdentity(Role r) {

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

		// List<String> abbreviations = new ArrayList<String>();

		int counter = 0;

		for (Role role : this.getRoles()) {
			if (role.hasAbbreviation()) {
				// abbreviations.add(role.getAbbreviation());
				if (role.getAbbreviation().equals(abr)) {
					counter++;
				}
			}
		}

		return counter == 1;
	}

	/**
	 * Gets the role.
	 *
	 * @param identifier
	 *            the identifier
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
				if (role.getTitle().equals(identifier)) {
					return role;
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

	public HowTo getHowTo(String identifier) {
		for (HowTo ht : this.getIndexHowTos()) {
			// if (WorkitemType.ROLE.isTypeOf(workitem)) {
			// Role role = (Role) workitem;

			if (identifier.equalsIgnoreCase(ht.getId())) {
				return ht;
			}

			if (identifier.equalsIgnoreCase(ht.getTitle())) {
				return ht;
			}

			// if (role.containsId(identifier)) {
			// return role;
			// }
			// if (StringUtil.isNotNullAndNotEmpty(role.getAbbreviation())) {
			// if (role.getAbbreviation().equals(identifier)) {
			// return role;
			// }
			// }
			// if (role.getTitle().equals(identifier)) {
			// return role;
			// }
			// if (role.getSynonyms() != null) {
			// for (String synonym : role.getSynonyms()) {
			// if (synonym.equals(identifier)) {
			// return role;
			// }
			// }
			// }

			// }
		}

		return null;
	}

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
	 * Validate.
	 *
	 * @return the list
	 */
	public List<ValidationMessage> validate() {
		List<IValidator> validators = new ArrayList<IValidator>();

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
						logger.info("" + m.getMessage() + " -> " + m.getSolution());
					} else {
						logger.info("" + m.getMessage());
					}

				}
				if (m.getType() == Type.WARNING) {
					if (m.getSolution() != null) {
						logger.warn("" + m.getMessage() + " -> " + m.getSolution());
					} else {
						logger.warn(m.getCause() + ": " + m.getMessage());
					}
				}
				if (m.getType() == Type.ERROR) {
					if (m.getSolution() != null) {
						logger.error("" + m.getMessage() + " -> " + m.getSolution());
					} else {
						logger.error(m.getCause() + ": " + m.getMessage());
					}
				}
				if (m.getType() == Type.DEBUG) {
					if (m.getSolution() != null) {
						logger.debug("" + m.getMessage() + " -> " + m.getSolution());
					} else {
						logger.debug(m.getMessage());
					}
				}
			}
		}
		return allMessages;

	}

	/**
	 * Gets the roles with person.
	 *
	 * @param person
	 *            the person
	 * @return the roles with person
	 */
	public ArrayList<Role> getRolesWithPerson(String person) {
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
