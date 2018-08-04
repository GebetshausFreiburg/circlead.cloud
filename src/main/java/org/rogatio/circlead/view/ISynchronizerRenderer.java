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
import org.rogatio.circlead.control.Repository;
import org.rogatio.circlead.control.ValidationMessage;
import org.rogatio.circlead.control.synchronizer.ISynchronizer;
import org.rogatio.circlead.control.synchronizer.atlassian.parser.Parser;
import org.rogatio.circlead.model.StatusParameter;
import org.rogatio.circlead.model.work.Activity;
import org.rogatio.circlead.model.work.Person;
import org.rogatio.circlead.model.work.Role;
import org.rogatio.circlead.model.work.Rolegroup;

public interface ISynchronizerRenderer {

	public ISynchronizer getSynchronizer();

	public void addActivityList(Element element, List<Activity> list);

	public void addRolegroupList(Element element, List<Rolegroup> list);

	public void addRoleList(Element element, List<Role> list);

	public void addRoleList(Element element, List<Role> list, Person person);

	public void addStatus(Element element, String statusValue);
	
	public void addHowToItem(Element element, String description, String content);
	
	public void addTable(Element element, Map<String, String> map);

	public void addPersonList(Element element, List<String> list, Role role);

	public void addPersonList(Element element, List<String> list, Role role, String leadPerson);

	public void addList(Element element, List<String> list, String underlinedElement);

	public void addValidationList(Element element, List<ValidationMessage> list);
	
	public void addList(Element element, List<String> list);

	public void addItem(Element element, String description);

	public void addItem(Element element, String description, List<String> list);

	public void addRoleItem(Element element, String description, String content);

	public void addRolegroupItem(Element element, String description, String content);

	public void addItem(Element element, String description, String content);

	public void addH1(Element element, String header);

	public void addH2(Element element, String header);

	public void addH3(Element element, String header);
}
