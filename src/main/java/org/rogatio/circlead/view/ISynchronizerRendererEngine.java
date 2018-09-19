/*
 * Circlead - Develop and structure evolutionary Organisations
 * 
 * @author Matthias Wegner
 * @version 0.1
 * @since 01.07.2018
 * 
 */
package org.rogatio.circlead.view;

import java.util.List;
import java.util.Map;

import org.jsoup.nodes.Element;
import org.rogatio.circlead.control.synchronizer.ISynchronizer;
import org.rogatio.circlead.control.validator.ValidationMessage;
import org.rogatio.circlead.model.data.ActivityDataitem;
import org.rogatio.circlead.model.work.Activity;
import org.rogatio.circlead.model.work.IWorkitem;
import org.rogatio.circlead.model.work.Person;
import org.rogatio.circlead.model.work.Role;
import org.rogatio.circlead.model.work.Rolegroup;

/**
 * The Interface ISynchronizerRenderer.
 */
public interface ISynchronizerRendererEngine {

	/**
	 * Gets the synchronizer.
	 *
	 * @return the synchronizer
	 */
	public ISynchronizer getSynchronizer();

	/**
	 * Adds the activity list.
	 *
	 * @param element the element
	 * @param list the list
	 */
	public void addActivityList(Element element, List<Activity> list);

	/**
	 * Adds the rolegroup list.
	 *
	 * @param element the element
	 * @param list the list
	 */
	public void addRolegroupList(Element element, List<Rolegroup> list);

	/**
	 * Adds the role list.
	 *
	 * @param element the element
	 * @param list the list
	 */
	public void addRoleList(Element element, List<Role> list);

	/**
	 * Adds the role list.
	 *
	 * @param element the element
	 * @param list the list
	 * @param person the person
	 */
	public void addRoleList(Element element, List<Role> list, Person person);

	/**
	 * Adds the status.
	 *
	 * @param element the element
	 * @param statusValue the status value
	 */
	public void addStatus(Element element, String statusValue);
	
	/**
	 * Adds the how to item.
	 *
	 * @param element the element
	 * @param description the description
	 * @param content the content
	 */
	public void addHowToItem(Element element, String description, String content);
	
	public void addSubActivityList(Element element, List<ActivityDataitem> list, Activity activity);
	
	/**
	 * Adds the table.
	 *
	 * @param element the element
	 * @param map the map
	 */
	public void addTable(Element element, Map<String, String> map);

	/**
	 * Adds the person list.
	 *
	 * @param element the element
	 * @param list the list
	 * @param role the role
	 */
	public void addPersonList(Element element, List<String> list, Role role);

	/**
	 * Adds the person list.
	 *
	 * @param element the element
	 * @param list the list
	 * @param role the role
	 * @param leadPerson the lead person
	 */
	public void addPersonList(Element element, List<String> list, Role role, String leadPerson);

	/**
	 * Adds the list.
	 *
	 * @param element the element
	 * @param list the list
	 * @param underlinedElement the underlined element
	 */
	public void addList(Element element, List<String> list, String underlinedElement);

	/**
	 * Adds the validation list.
	 *
	 * @param element the element
	 * @param list the list
	 */
	public void addValidationList(Element element, List<ValidationMessage> list);
	
	/**
	 * Adds the list.
	 *
	 * @param element the element
	 * @param list the list
	 */
	public void addList(Element element, List<String> list);

	/**
	 * Adds the item.
	 *
	 * @param element the element
	 * @param description the description
	 */
	public void addItem(Element element, String description);

	/**
	 * Adds the item.
	 *
	 * @param element the element
	 * @param description the description
	 * @param list the list
	 */
	public void addItem(Element element, String description, List<String> list);

	public void addActivityItem(Element element, String description, String content);
	
	/**
	 * Adds the role item.
	 *
	 * @param element the element
	 * @param description the description
	 * @param content the content
	 */
	public void addRoleItem(Element element, String description, String content);

	/**
	 * Adds the rolegroup item.
	 *
	 * @param element the element
	 * @param description the description
	 * @param content the content
	 */
	public void addRolegroupItem(Element element, String description, String content);

	/**
	 * Adds the item.
	 *
	 * @param element the element
	 * @param description the description
	 * @param content the content
	 */
	public void addItem(Element element, String description, String content);

	/**
	 * Adds the H 1.
	 *
	 * @param element the element
	 * @param header the header
	 */
	public void addH1(Element element, String header);

	/**
	 * Adds the H 2.
	 *
	 * @param element the element
	 * @param header the header
	 */
	public void addH2(Element element, String header);

	/**
	 * Adds the H 3.
	 *
	 * @param element the element
	 * @param header the header
	 */
	public void addH3(Element element, String header);
	
	public void addWorkitemTable(Element element, List<IWorkitem> workitem);
}
