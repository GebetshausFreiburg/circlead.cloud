package org.rogatio.circlead.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.dmfs.rfc5545.recur.Freq;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.rogatio.circlead.control.Repository;
import org.rogatio.circlead.model.data.TeamEntry;
import org.rogatio.circlead.model.work.Person;
import org.rogatio.circlead.model.work.Role;
import org.rogatio.circlead.model.work.Team;

/**
 * The Class GanttProjectUtil generates a xml-file for the software GanttProject.
 * 
 * @author Matthias Wegner
 */
public class GanttUtil {

	/** The Constant DATEFORMATTER. */
	private final static SimpleDateFormat DATEFORMATTER = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * Creates the role.
	 *
	 * @param id the id
	 * @param name the name
	 * @return the element
	 */
	private static Element createRole(int id, String name) {
		Element field = new Element("role");
		field.setAttribute("id", "" + id);
		field.setAttribute("name", name);
		return field;
	}

	/**
	 * Creates the field.
	 *
	 * @param id the id
	 * @param name the name
	 * @param width the width
	 * @param order the order
	 * @return the element
	 */
	private static Element createField(String id, String name, int width, int order) {
		Element field = new Element("field");
		field.setAttribute("id", id);
		field.setAttribute("name", name);
		field.setAttribute("width", "" + width);
		field.setAttribute("order", "" + order);

		return field;
	}

	/**
	 * Creates the task.
	 *
	 * @param id the id
	 * @param name the name
	 * @param color the color
	 * @param start the start
	 * @param duration the duration
	 * @return the element
	 */
	private static Element createTask(String id, String name, String color, Date start, int duration) {
		Element task = new Element("task");
		task.setAttribute("id", id);
		task.setAttribute("name", name);
		task.setAttribute("color", color);
		task.setAttribute("meeting", "false");
		task.setAttribute("start", DATEFORMATTER.format(start));
		task.setAttribute("duration", "" + duration);
		task.setAttribute("complete", "0");
		task.setAttribute("thirdDate", DATEFORMATTER.format(new Date()));
		task.setAttribute("thirdDate-constraint", "0");
		task.setAttribute("expand", "true");
		return task;
	}

	/**
	 * Creates the resource.
	 *
	 * @param id the id
	 * @param name the name
	 * @param function the function
	 * @param mail the mail
	 * @param phone the phone
	 * @return the element
	 */
	private static Element createResource(int id, String name, int function, String mail, String phone) {
		Element prop = new Element("resource");
		prop.setAttribute("id", "" + id);
		prop.setAttribute("name", name);
		if (function > 0) {
			prop.setAttribute("function", "" + function);
		} else {
			prop.setAttribute("function", "Default:0");
		}
		prop.setAttribute("contacts", mail);
		prop.setAttribute("type", "default");
		prop.setAttribute("phone", phone);
		return prop;
	}

	/**
	 * Creates the allocation.
	 *
	 * @param taskId the task id
	 * @param resourceId the resource id
	 * @param function the function
	 * @param responsible the responsible
	 * @param load the load
	 * @return the element
	 */
	private static Element createAllocation(String taskId, String resourceId, String function, boolean responsible,
			double load) {
		Element prop = new Element("allocation");
		prop.setAttribute("task-id", taskId);
		prop.setAttribute("resource-id", resourceId);
		if (function != null) {
			prop.setAttribute("function", function);
		} else {
			prop.setAttribute("function", "Default:0");
		}
		prop.setAttribute("responsible", "" + responsible);
		prop.setAttribute("load", "" + load);
		return prop;
	}

	/**
	 * Creates the property.
	 *
	 * @param id the id
	 * @param name the name
	 * @param valuetype the valuetype
	 * @return the element
	 */
	private static Element createProperty(String id, String name, String valuetype) {
		Element prop = new Element("taskproperty");
		prop.setAttribute("id", id);
		prop.setAttribute("name", name);
		prop.setAttribute("type", "default");
		prop.setAttribute("valuetype", valuetype);
		return prop;
	}

	/**
	 * Creates the tasks.
	 *
	 * @param teams the teams
	 * @return the list
	 */
	private static List<Element> createTasks(List<Team> teams) {
		List<Element> elements = new ArrayList<Element>();

		int duration = 0;
		Date start = new Date();

		for (Team team : teams) {
			elements.add(createTask("" + (teams.indexOf(team) + 1), team.getTitle(), "#8cb6ce", team.getStartDate(),
					team.getDuration()));

			if (start.after(team.getStartDate())) {
				start = team.getStartDate();
			}
			duration = Math.max(team.getDuration(), duration);
		}

		return elements;
	}

	/**
	 * Creates the default week.
	 *
	 * @return the element
	 */
	private static Element createDefaultWeek() {
		Element defaultWeek = new Element("default-week");
		defaultWeek.setAttribute("id", "1");
		defaultWeek.setAttribute("name", "default");
		defaultWeek.setAttribute("sun", "1");
		defaultWeek.setAttribute("mon", "0");
		defaultWeek.setAttribute("tue", "0");
		defaultWeek.setAttribute("wed", "0");
		defaultWeek.setAttribute("thu", "0");
		defaultWeek.setAttribute("fri", "0");
		defaultWeek.setAttribute("sat", "1");
		return defaultWeek;
	}

	/**
	 * Creates the task properties.
	 *
	 * @return the element
	 */
	private static Element createTaskProperties() {
		Element properties = new Element("taskproperties");
		properties.addContent(createProperty("tpd0", "type", "icon"));
		properties.addContent(createProperty("tpd1", "priority", "icon"));
		properties.addContent(createProperty("tpd2", "info", "icon"));
		properties.addContent(createProperty("tpd3", "name", "text"));
		properties.addContent(createProperty("tpd4", "begindate", "date"));
		properties.addContent(createProperty("tpd5", "enddate", "date"));
		properties.addContent(createProperty("tpd6", "duration", "int"));
		properties.addContent(createProperty("tpd7", "completion", "int"));
		properties.addContent(createProperty("tpd8", "coordinator", "text"));
		properties.addContent(createProperty("tpd9", "predecessorsr", "text"));
		return properties;
	}

	/**
	 * Write.
	 *
	 * @param targetXml the target xml
	 * @param teams the teams
	 * @param roles the roles
	 * @param persons the persons
	 */
	public static void write(String targetXml, List<Team> teams, List<Role> roles, List<Person> persons) {

		try {
			Element root = new Element("project");
			root.setAttribute("name", "Circlead Project");
			root.setAttribute("company", "");
			root.setAttribute("webLink", "http://");

			root.setAttribute("view-date", DATEFORMATTER.format(new Date()));
			root.setAttribute("view-index", "0");
			root.setAttribute("gantt-divider-location", "418");
			root.setAttribute("resource-divider-location", "417");
			root.setAttribute("version", "2.8.9");
			root.setAttribute("locale", Locale.getDefault().toString());
			Document doc = new Document(root);

			root.addContent(new Element("description"));

			Element view = new Element("view");
			view.setAttribute("zooming-state", "default:8");
			view.setAttribute("id", "gantt-chart");
			view.addContent(createField("tpd3", "Vorgang", 250, 0));
			view.addContent(createField("tpd4", "Anfang", 100, 1));
			view.addContent(createField("tpd5", "Ende", 100, 2));
			root.addContent(view);

			view = new Element("view");
			view.setAttribute("id", "resource-table");
			view.addContent(createField("0", "Ressource", 250, 0));
			view.addContent(createField("1", "Rolle", 150, 1));
			root.addContent(view);

			Element calendars = new Element("calendars");
			Element dayTypes = new Element("day-types");
			Element dayType = new Element("day-type");
			dayType.setAttribute("id", "0");
			dayTypes.addContent(dayType);
			dayType = new Element("day-type");
			dayType.setAttribute("id", "1");
			dayTypes.addContent(dayType);
			calendars.addContent(dayTypes);
			dayTypes.addContent(createDefaultWeek());
			Element onlyShowWeekends = new Element("only-show-weekends");
			onlyShowWeekends.setAttribute("value", "false");
			dayTypes.addContent(onlyShowWeekends);
			dayTypes.addContent(new Element("overriden-day-types"));
			dayTypes.addContent(new Element("days"));
			root.addContent(calendars);

			Element tasks = new Element("tasks");
			tasks.setAttribute("empty-milestones", "true");
			tasks.addContent(createTaskProperties());

			List<Element> t = createTasks(teams);
			for (Element element : t) {
				tasks.addContent(element);
			}
			root.addContent(tasks);

			Element resources = new Element("resources");
			for (Person person : persons) {
				resources.addContent(createResource((persons.indexOf(person) + 1), person.getFullname(), 0, "", ""));
			}
			root.addContent(resources);

			Element allocations = new Element("allocations");

			for (Team team : teams) {
				int teamIndex = teams.indexOf(team) + 1;
				List<TeamEntry> entries = team.getTeamEntries();
				for (TeamEntry entry : entries) {
					Role r = Repository.getInstance().getRole(entry.getRoleIdentifier());
					int roleIndex = 0;
					if (r != null) {
						roleIndex = roles.indexOf(r) + 1;
					}
					if (ObjectUtil.isListNotNullAndEmpty(entry.getPersonIdentifiers())) {
						for (String pi : entry.getPersonIdentifiers()) {
							Person p = Repository.getInstance().getPerson(pi);
							if (p != null) {
								int personIndex = persons.indexOf(p) + 1;
								double alloc = 100.0 * team.getAverageAllokation(p, Freq.WEEKLY) / 40.0 / ((double)team.getRoles(p).size());
								allocations.addContent(createAllocation("" + teamIndex, personIndex + "",
										roleIndex + "", false, alloc));
							}
						}
					}
				}

			}

			for (Person person : persons) {
				tasks.addContent(createTask("" + (persons.indexOf(person) + teams.size() + 1),
						"Allokation - " + person.getFullname() + "", "#cccccc", new Date(), 365));
			
						boolean realisticAlloc = false;
						if (!realisticAlloc) {
							allocations.addContent(createAllocation("" + (persons.indexOf(person) + teams.size() + 1),
									(persons.indexOf(person) + 1) + "", null, false, 100.0 - person.getFullTimeEquivalent()*person.getTeamFraction()/100.0));	
						} else {
							allocations.addContent(createAllocation("" + (persons.indexOf(person) + teams.size() + 1),
									(persons.indexOf(person) + 1) + "", null, false,
									Repository.getInstance().getAverageAllokationInOrganisation(person.getFullname(), Freq.WEEKLY)
									/ 40. * 100.0));
						}
			}

			root.addContent(allocations);

			root.addContent(new Element("vacations"));
			root.addContent(new Element("previous"));

			Element rolesElement = new Element("roles");
			rolesElement.setAttribute("roleset-name", "Default");
			root.addContent(rolesElement);

			rolesElement = new Element("roles");
			for (Role role : roles) {
				rolesElement.addContent(createRole((roles.indexOf(role) + 1), role.getTitle()));
			}
			root.addContent(rolesElement);

			XMLOutputter xmlOutput = new XMLOutputter();
			Format f = Format.getPrettyFormat();
			f.setEncoding("UTF-8");
			xmlOutput.setFormat(f);
			xmlOutput.output(doc, new FileOutputStream(targetXml));

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
