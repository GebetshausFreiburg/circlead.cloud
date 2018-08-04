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
import java.util.List;

import org.jsoup.nodes.Element;
import org.rogatio.circlead.control.IValidator;
import org.rogatio.circlead.control.Repository;
import org.rogatio.circlead.control.ValidationMessage;
import org.rogatio.circlead.control.synchronizer.ISynchronizer;
import org.rogatio.circlead.control.synchronizer.atlassian.parser.ListParserElement;
import org.rogatio.circlead.model.WorkitemType;
import org.rogatio.circlead.model.data.IDataitem;
import org.rogatio.circlead.model.data.RolegroupDataitem;
import org.rogatio.circlead.util.StringUtil;
import org.rogatio.circlead.view.IWorkitemRenderer;
import org.rogatio.circlead.view.AtlassianRenderer;
import org.rogatio.circlead.view.ISynchronizerRenderer;

// TODO: Auto-generated Javadoc
/**
 * The Class Rolegroup.
 */
public class Rolegroup extends DefaultWorkitem implements IWorkitemRenderer, IValidator {

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
	 *            the dataitem
	 */
	public Rolegroup(IDataitem dataitem) {
		super(dataitem);
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
	//
	// /**
	// * Gets the role identifiers.
	// *
	// * @return the role identifiers
	// */
	// public List<String> getRoleIdentifiers() {
	// return getDataitem().getRoles();
	// }
	//
	// /**
	// * Adds the role.
	// *
	// * @param role
	// * the role
	// */
	// public void addRole(Role role) {
	// this.getDataitem().addRole(role);
	// }
	//
	// /**
	// * Sets the role identifiers.
	// *
	// * @param identifiers
	// * the new role identifiers
	// */
	// public void setRoleIdentifiers(List<String> identifiers) {
	// this.getDataitem().setRoles(identifiers);
	// }

	// public void setRoleIdentifiers(ListParserElement element) {
	// this.setRoleIdentifiers(element.getList());
	// }

	/*
	 * public List<Person> getPersons() { List<String> roleNames = getRoleIdentifiers(); List<Person> persons = new ArrayList<Person>(); for (String roleName :
	 * roleNames) { Role r = Roles.getInstance().getRole(roleName); if (r != null) { List<String> ps = r.getPersonIdentifiers(); for (String p : ps) { if
	 * (!persons.contains(p)) { Person per = Persons.getInstance().getPerson(p); if (per != null) { persons.add(per); } } } } }
	 * 
	 * return persons; }
	 * 
	 * public List<String> getPersonIdentifiers() { List<String> roleNames = getRoleIdentifiers(); List<String> persons = new ArrayList<String>(); for (String
	 * roleName : roleNames) { Role r = Roles.getInstance().getRole(roleName); if (r != null) { List<String> ps = r.getPersonIdentifiers(); for (String p : ps)
	 * { if (!persons.contains(p)) { persons.add(p); } } } }
	 * 
	 * return persons; }
	 * 
	 * public int getPersonSize() { List<String> roleNames = getRoleIdentifiers(); List<String> persons = new ArrayList<String>(); for (String roleName :
	 * roleNames) { Role r = Roles.getInstance().getRole(roleName); if (r != null) { List<String> ps = r.getPersonIdentifiers(); for (String p : ps) { if
	 * (!persons.contains(p)) { persons.add(p); } } } }
	 * 
	 * return persons.size(); }
	 */

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
		ISynchronizerRenderer renderer = synchronizer.getRenderer();
		
		Element element = new Element("p");

		if (StringUtil.isNotNullAndNotEmpty(this.getResponsibleIdentifier())) {
			Role role = Repository.getInstance().getRole(this.getResponsibleIdentifier());
			if (role != null) { 
				renderer.addRoleItem(element, "Verantwortliche Rolle", this.getResponsibleIdentifier());

				List<String> personIdentifiers = role.getPersonIdentifiers();
				renderer.addPersonList(element, personIdentifiers, role, this.getLeadIdentifier());
			}
		}

		if (StringUtil.isNotNullAndNotEmpty(this.getParentIdentifier())) {
			renderer.addRolegroupItem(element, "Vererber: ", this.getParentIdentifier());
		}

		renderer.addH2(element, "Zusammenfassung");
		renderer.addItem(element, this.getSummary());

		List<Role> roles = Repository.getInstance().getRoles(this.getTitle());
		if (roles.size() > 0) {
			renderer.addH2(element, "Merkmalgebende Rollen");
			renderer.addRoleList(element, roles);
		}

		List<Rolegroup> childRolegroups = Repository.getInstance().getRolegroupChildren(this.getTitle());
		if (childRolegroups != null) {
			if (childRolegroups.size() > 0) {
				renderer.addH2(element, "Erben");
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
			Role rg = Repository.getInstance().getRole(this.getResponsibleIdentifier());
			if (rg == null) {
				ValidationMessage m = new ValidationMessage(this);
				m.warning("Responsible role not found",
						"Responsible role '" + this.getResponsibleIdentifier() + "' not found for Rolegroup '" + this.getTitle() + "'");
				messages.add(m);
			}
		}

		List<Role> roles = Repository.getInstance().getRoles(this.getTitle());
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
			Role role = Repository.getInstance().getRole(this.getResponsibleIdentifier());
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

}
