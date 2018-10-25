/*
 * Circlead - Develop and structure evolutionary Organisations
 * 
 * @author Matthias Wegner
 * @version 0.1
 * @since 01.07.2018
 * 
 */
package org.rogatio.circlead.control.synchronizer.atlassian.parser;

import static org.rogatio.circlead.model.Parameter.CATEGORY;
import static org.rogatio.circlead.model.Parameter.FTE;
import static org.rogatio.circlead.model.Parameter.TEAMFRACTION;
import static org.rogatio.circlead.model.Parameter.RECURRENCERULE;
import static org.rogatio.circlead.model.Parameter.ABBREVIATION;
import static org.rogatio.circlead.model.Parameter.TEAMROLES;
import static org.rogatio.circlead.model.Parameter.ABBREVIATION2;
import static org.rogatio.circlead.model.Parameter.IMAGE;
import static org.rogatio.circlead.model.Parameter.ACTIVITIES;
import static org.rogatio.circlead.model.Parameter.ACTIVITY;
import static org.rogatio.circlead.model.Parameter.ACTIVITYID;
import static org.rogatio.circlead.model.Parameter.ADRESS;
import static org.rogatio.circlead.model.Parameter.BPMN;
import static org.rogatio.circlead.model.Parameter.COMPETENCIES;
import static org.rogatio.circlead.model.Parameter.CONSULTANT;
import static org.rogatio.circlead.model.Parameter.CONTACTPERSON;
import static org.rogatio.circlead.model.Parameter.CONTACTS2;
import static org.rogatio.circlead.model.Parameter.DATA;
import static org.rogatio.circlead.model.Parameter.DESCRIPTION;
import static org.rogatio.circlead.model.Parameter.HOWTOS;
import static org.rogatio.circlead.model.Parameter.ID;
import static org.rogatio.circlead.model.Parameter.INFORMED;
import static org.rogatio.circlead.model.Parameter.MAIL;
import static org.rogatio.circlead.model.Parameter.MOBILE;
import static org.rogatio.circlead.model.Parameter.NAME;
import static org.rogatio.circlead.model.Parameter.ROLE;
import static org.rogatio.circlead.model.Parameter.NEEDED;
import static org.rogatio.circlead.model.Parameter.LEVEL;
import static org.rogatio.circlead.model.Parameter.PERSON;
import static org.rogatio.circlead.model.Parameter.OPPORTUNITIES;
import static org.rogatio.circlead.model.Parameter.ORGANISATION;
import static org.rogatio.circlead.model.Parameter.PERSONS;
import static org.rogatio.circlead.model.Parameter.PHONE;
import static org.rogatio.circlead.model.Parameter.PREDECESSOR;
import static org.rogatio.circlead.model.Parameter.PURPOSESHORT;
import static org.rogatio.circlead.model.Parameter.RESPONSIBILITY;
import static org.rogatio.circlead.model.Parameter.RESPONSIBLE;
import static org.rogatio.circlead.model.Parameter.RESPONSIBLE2;
import static org.rogatio.circlead.model.Parameter.RESULT;
import static org.rogatio.circlead.model.Parameter.ROLEGROUP;
import static org.rogatio.circlead.model.Parameter.RULES;
import static org.rogatio.circlead.model.Parameter.STATUS;
import static org.rogatio.circlead.model.Parameter.SUBACTIVITIES;
import static org.rogatio.circlead.model.Parameter.SUBTYPE;
import static org.rogatio.circlead.model.Parameter.SUCCESSOR;
import static org.rogatio.circlead.model.Parameter.SUMMARY;
import static org.rogatio.circlead.model.Parameter.SUPPORTER;
import static org.rogatio.circlead.model.Parameter.SYNONYMS;
import static org.rogatio.circlead.model.Parameter.TYPE;
import static org.rogatio.circlead.model.Parameter.UNKNOWN;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Vector;

import org.jsoup.nodes.Element;
import org.rogatio.circlead.control.Repository;
import org.rogatio.circlead.control.synchronizer.ISynchronizer;
import org.rogatio.circlead.control.synchronizer.atlassian.AtlassianSynchronizer;
import org.rogatio.circlead.control.synchronizer.atlassian.content.Body;
import org.rogatio.circlead.control.synchronizer.atlassian.content.Labels;
import org.rogatio.circlead.control.synchronizer.atlassian.content.Metadata;
import org.rogatio.circlead.control.synchronizer.atlassian.content.Page;
import org.rogatio.circlead.control.synchronizer.atlassian.content.Space;
import org.rogatio.circlead.control.synchronizer.atlassian.content.Storage;
import org.rogatio.circlead.control.synchronizer.atlassian.search.Results;
import org.rogatio.circlead.control.synchronizer.file.FileSynchronizer;
import org.rogatio.circlead.model.WorkitemStatusParameter;
import org.rogatio.circlead.model.data.ActivityDataitem;
import org.rogatio.circlead.model.data.ContactDataitem;
import org.rogatio.circlead.model.data.PersonDataitem;
import org.rogatio.circlead.model.data.RoleDataitem;
import org.rogatio.circlead.model.data.RolegroupDataitem;
import org.rogatio.circlead.model.data.TeamDataitem;
import org.rogatio.circlead.model.data.TeamEntry;
import org.rogatio.circlead.model.data.Timeslice;
import org.rogatio.circlead.model.work.Activity;
import org.rogatio.circlead.model.work.IWorkitem;
import org.rogatio.circlead.model.work.Person;
import org.rogatio.circlead.model.work.Role;
import org.rogatio.circlead.model.work.Rolegroup;
import org.rogatio.circlead.model.work.Team;
import org.rogatio.circlead.util.ObjectUtil;
import org.rogatio.circlead.util.StringUtil;
import org.rogatio.circlead.view.IWorkitemRenderer;
import org.rogatio.circlead.view.report.IReport;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * The Class Parser.
 */
public class Parser {

	/**
	 * Gets the page properties macro.
	 *
	 * @param content the content
	 * @return the page properties macro
	 */
	public static Element getPagePropertiesMacro(Element content) {
		Element macro = new Element("ac:structured-macro");
		macro.attr("ac:name", "details");
		macro.attr("ac:schema-version", "1");
		macro.attr("ac:macro-id", UUID.randomUUID().toString());
		Element body = macro.appendElement("ac:rich-text-body");
		content.appendTo(body);
		return macro;
	}

	/**
	 * Gets the jira macro.
	 *
	 * @param columns       the columns
	 * @param maximumIssues the maximum issues
	 * @param jqlQuery      the jql query
	 * @param serverId      the server id
	 * @return the jira macro
	 */
	public static Element getJiraMacro(String columns, int maximumIssues, String jqlQuery, String serverId) {
		Element macro = new Element("ac:structured-macro");
		macro.attr("ac:name", "jira");
		macro.attr("ac:schema-version", "1");
		macro.attr("ac:macro-id", UUID.randomUUID().toString());

		Element param = macro.appendElement("ac:parameter");
		param.attr("ac:name", "server");
		param.appendText("System JIRA");

		param = macro.appendElement("ac:parameter");
		param.attr("ac:name", "columns");
		param.appendText(columns);

		param = macro.appendElement("ac:parameter");
		param.attr("ac:name", "maximumIssues");
		param.appendText("" + maximumIssues);

		param = macro.appendElement("ac:parameter");
		param.attr("ac:name", "jqlQuery");
		param.appendText(jqlQuery);

		param = macro.appendElement("ac:parameter");
		param.attr("ac:name", "serverId");
		param.appendText(serverId);

		return macro;
	}

	/**
	 * Load attached images from page "BPMN". Images are converted from svg to png.
	 *
	 * @param page      the page
	 * @param imageFile the image file
	 * @param size      the size
	 * @param version   the version
	 * @return the string
	 * @see https://github.com/bpmn-io
	 */
	public static String addImageFromOtherPage(String page, String imageFile, int size, int version) {
//		if (imageFile==null) {
//			return "";
//		}
//		if (page==null) {
//			return "";
//		}
//		if (version==0) {
//			return "";
//		}
//		if (size<0) {
//			return "";
//		}
		String html = "<div class=\"content-wrapper\">" + "<p><ac:image ac:thumbnail=\"true\" ac:width=\"" + size
				+ "\"><ri:attachment ri:filename=\"" + imageFile
				+ "\" ri:version-at-save=\"1\"><ri:page ri:content-title=\"" + page + "\" ri:version-at-save=\""
				+ version + "\" /></ri:attachment></ac:image></p></div>";
		return html;
	}

	/**
	 * Adds the image.
	 *
	 * @param filename the filename
	 * @param size     the size
	 * @param version  the version
	 * @return the string
	 */
	public static String addImage(String filename, int size, int version) {
		return "<ac:image ac:height=\"" + size + "\"><ri:attachment ri:filename=\"" + filename
				+ "\" ri:version-at-save=\"" + version + "\" /></ac:image>";
	}

	public static Element createTeamEntryTable(List<TeamEntry> teamentries, ISynchronizer synchronizer,
			boolean activatedLinks) {

		if (!ObjectUtil.isListNotNullAndEmpty(teamentries)) {
			Element e = new Element("p");
			return e.appendText("-");
		}

		Element table = new Element("table");
		table.attr("class", "wrapped");
		Element tbody = table.appendElement("tbody");

		boolean containsLevel = false;
		for (TeamEntry entry : teamentries) {
			if (StringUtil.isNotNullAndNotEmpty(entry.getLevel())) {
				containsLevel = true;
			}
		}

		Element tr = tbody.appendElement("tr");
		tr.appendElement("th").attr("colspan", "1").appendText(ROLE.toString());
		tr.appendElement("th").attr("colspan", "1").appendText(NEEDED.toString());
		if (containsLevel) {
			tr.appendElement("th").attr("colspan", "1").appendText(LEVEL.toString());
		}
		tr.appendElement("th").attr("colspan", "1").appendText(PERSON.toString());

		for (TeamEntry entry : teamentries) {
			tr = tbody.appendElement("tr");
			if (StringUtil.isNotNullAndNotEmpty(entry.getRoleIdentifier())) {
				Element td = tr.appendElement("td").attr("colspan", "1");
				Role r = Repository.getInstance().getRole(entry.getRoleIdentifier());
				if ((r != null) && activatedLinks) {
					synchronizer.getRenderer().addRoleItem(td, null, entry.getRoleIdentifier());
				} else {
					td.attr("colspan", "1").appendText(entry.getRoleIdentifier());
				}
			} else {
				tr.appendElement("td").attr("colspan", "1").appendText("");
			}

			tr.appendElement("td").attr("colspan", "1").appendText(entry.getNeeded() + "");

			if (containsLevel) {
				if (StringUtil.isNotNullAndNotEmpty(entry.getLevel())) {
					if (activatedLinks) {
						Element s = Parser.getStatus(entry.getLevel() + "%", "Grey");
						Element td = tr.appendElement("td");
						s.appendTo(td);
					} else {
						tr.appendElement("td").attr("colspan", "1").appendText(entry.getLevel() + "%");
					}
				} else {
					tr.appendElement("td").attr("colspan", "1").appendText("");
				}
			}

			if (ObjectUtil.isListNotNullAndEmpty(entry.getPersonIdentifiers())) {
				if (!activatedLinks) {
					tr.appendElement("td").attr("colspan", "1")
							.appendText(StringUtil.join(entry.getPersonIdentifiers()));
				} else {
					addPersonListToTableCell(tr, entry.getPersons(), synchronizer);
				}
			} else {
				tr.appendElement("td").attr("colspan", "1").appendText("");
			}
		}

		return table;
	}

	/**
	 * Creates the header table for activity-subactivties. Is a critical method,
	 * because it renders master-data-table in atlassian confluence and
	 * render-result. If this is changed or not correct it deletes master-data.
	 *
	 * @param activities     the activities
	 * @param synchronizer   the synchronizer
	 * @param activatedLinks the activated links
	 * @return the element
	 */
	public static Element createActivityTable(List<ActivityDataitem> activities, ISynchronizer synchronizer,
			boolean activatedLinks) {

		if (!ObjectUtil.isListNotNullAndEmpty(activities)) {
			Element e = new Element("p");
			return e.appendText("-");
		}

		Element table = new Element("table");
		table.attr("class", "wrapped");
		Element tbody = table.appendElement("tbody");

		Element tr = tbody.appendElement("tr");
		tr.appendElement("th").attr("colspan", "1").appendText(ACTIVITYID.toString());
		tr.appendElement("th").attr("colspan", "1").appendText(BPMN.toString());
		tr.appendElement("th").attr("colspan", "1").appendText(SUCCESSOR.toString());
		tr.appendElement("th").attr("colspan", "1").appendText(ACTIVITY.toString());
		tr.appendElement("th").attr("colspan", "1").appendText(DESCRIPTION.toString());
		tr.appendElement("th").attr("colspan", "1").appendText(RESULT.toString());
		tr.appendElement("th").attr("colspan", "1").appendText(RESPONSIBLE.toString());
		tr.appendElement("th").attr("colspan", "1").appendText(SUPPORTER.toString());
		tr.appendElement("th").attr("colspan", "1").appendText(CONSULTANT.toString());
		tr.appendElement("th").attr("colspan", "1").appendText(INFORMED.toString());

		for (ActivityDataitem activity : activities) {
			tr = tbody.appendElement("tr");
			if (StringUtil.isNotNullAndNotEmpty(activity.getAid())) {
				tr.appendElement("td").attr("colspan", "1").appendText(activity.getAid());
			} else {
				tr.appendElement("td").attr("colspan", "1").appendText("");
			}
			if (StringUtil.isNotNullAndNotEmpty(activity.getBpmn())) {
				if (activatedLinks) {
					if (synchronizer.getClass().getSimpleName().equals(AtlassianSynchronizer.class.getSimpleName())) {
						tr.appendElement("td").attr("colspan", "1")
								.append(addImageFromOtherPage("BPMN", activity.getBpmn() + ".png", 32, 1));
					} else if (synchronizer.getClass().getSimpleName().equals(FileSynchronizer.class.getSimpleName())) {
						tr.appendElement("td").attr("colspan", "1").append("<img src=\"..\\data\\images\\bpmn\\"
								+ activity.getBpmn() + ".png\" alt=\"" + activity.getBpmn() + "\" width=\"32px\">");

					} else {
						tr.appendElement("td").attr("colspan", "1").appendText(activity.getBpmn());
					}
				} else {
					tr.appendElement("td").attr("colspan", "1").appendText(activity.getBpmn());
				}
			} else {
				tr.appendElement("td").attr("colspan", "1").appendText("");
			}
			if (StringUtil.isNotNullAndNotEmpty(activity.getChild())) {
				tr.appendElement("td").attr("colspan", "1").appendText(activity.getChild());
			} else {
				tr.appendElement("td").attr("colspan", "1").appendText("");
			}
			if (StringUtil.isNotNullAndNotEmpty(activity.getTitle())) {
				Element td = tr.appendElement("td").attr("colspan", "1");
				Activity a = Repository.getInstance().getActivity(activity.getTitle());
				if ((a != null) && activatedLinks) {
					synchronizer.getRenderer().addActivityItem(td, null, activity.getTitle());
				} else {
					td.attr("colspan", "1").appendText(activity.getTitle());
				}
			} else {
				tr.appendElement("td").attr("colspan", "1").appendText("");
			}
			if (StringUtil.isNotNullAndNotEmpty(activity.getDescription())) {
				tr.appendElement("td").attr("colspan", "1").appendText(activity.getDescription());
			} else {
				tr.appendElement("td").attr("colspan", "1").appendText("");
			}
			if (StringUtil.isNotNullAndNotEmpty(activity.getResults())) {
				tr.appendElement("td").attr("colspan", "1").appendText(activity.getResults());
			} else {
				tr.appendElement("td").attr("colspan", "1").appendText("");
			}
			if (StringUtil.isNotNullAndNotEmpty(activity.getResponsible())) {
				Element td = tr.appendElement("td").attr("colspan", "1");
				Role r = Repository.getInstance().getRole(activity.getResponsible());
				if ((r != null) && activatedLinks) {
					synchronizer.getRenderer().addRoleItem(td, null, activity.getResponsible());
				} else {
					td.attr("colspan", "1").appendText(activity.getResponsible());
				}
			} else {
				tr.appendElement("td").attr("colspan", "1").appendText("");
			}
			if (ObjectUtil.isListNotNullAndEmpty(activity.getSupplier())) {
				if (!activatedLinks) {
					tr.appendElement("td").attr("colspan", "1").appendText(StringUtil.join(activity.getSupplier()));
				} else {
					addRoleListToTableCell(tr, activity.getSupplier(), synchronizer);
				}
			} else {
				tr.appendElement("td").attr("colspan", "1").appendText("");
			}
			if (ObjectUtil.isListNotNullAndEmpty(activity.getConsultant())) {
				if (!activatedLinks) {
					tr.appendElement("td").attr("colspan", "1").appendText(StringUtil.join(activity.getConsultant()));
				} else {
					addRoleListToTableCell(tr, activity.getConsultant(), synchronizer);
				}
			} else {
				tr.appendElement("td").attr("colspan", "1").appendText("");
			}
			if (ObjectUtil.isListNotNullAndEmpty(activity.getInformed())) {
				if (!activatedLinks) {
					tr.appendElement("td").attr("colspan", "1").appendText(StringUtil.join(activity.getInformed()));
				} else {
					addRoleListToTableCell(tr, activity.getInformed(), synchronizer);
				}
			} else {
				tr.appendElement("td").attr("colspan", "1").appendText("");
			}
		}

		return table;
	}

	private static Element addParameter(String name, String parameter, Element macro) {
		Element param = macro.appendElement("ac:parameter");
		param.attr("ac:name", name).appendText(parameter);
		return param;
	}

	public static Element addChartMacro(String title, String xLabel, String yLabel, String colors,
			Map<String, List<Timeslice>> dataMap) {
		Element macro = new Element("ac:structured-macro");
		macro.attr("ac:name", "chart").attr("ac:schema-version", "1").attr("ac:macro-id", UUID.randomUUID().toString());
		addParameter("orientation", "vertical", macro);
		addParameter("stacked", "true", macro);
		if (StringUtil.isNotNullAndNotEmpty(title)) {
			addParameter("title", title, macro);
		}
//		addParameter("subTitle", "in Kalenderwochen", macro);
		addParameter("opacity", "85", macro);
		if (StringUtil.isNotNullAndNotEmpty(colors)) {
			addParameter("colors", colors, macro);
		}
		addParameter("height", "480", macro);
		addParameter("width", "640", macro);
		addParameter("type", "bar", macro);
		addParameter("yLabel", yLabel, macro);
		addParameter("xLabel", xLabel, macro);
		Element body = macro.appendElement("ac:rich-text-body");

		Element table = body.appendElement("table");
		table.attr("class", "wrapped");
		Element tbody = table.appendElement("tbody");
		Element tr = tbody.appendElement("tr");
		tr.appendElement("th");

		if (dataMap == null) {
			return null;
		}

		if (dataMap.size() == 0) {
			return null;
		}

		ArrayList<String> keys = new ArrayList<String>(dataMap.keySet());

		if (dataMap.keySet().size() == 0) {
			return null;
		}

		List<Timeslice> dataset = dataMap.get(keys.get(0));
		for (Timeslice timeslice : dataset) {
			tr.appendElement("th").appendText("" + timeslice.getUnitValue());
		}

		for (String key : keys) {
			tr = tbody.appendElement("tr");
			tr.appendElement("td").appendText(key);
			dataset = dataMap.get(key);
			for (Timeslice timeslice : dataset) {
				tr.appendElement("td").appendText("" + timeslice.getAllokation());
			}
		}

		return macro;
	}

	private static void addPersonListToTableCell(Element tr, List<String> personIdentifiers,
			ISynchronizer synchronizer) {
		Element td = tr.appendElement("td").attr("colspan", "1");
		Element ul = td.appendElement("ul");
		for (String personIdentifier : personIdentifiers) {
			Element li = ul.appendElement("li");
			synchronizer.getRenderer().addPersonItem(li, null, personIdentifier);
		}
	}

	/**
	 * Adds the role list to table cell.
	 *
	 * @param tr              the tr
	 * @param roleIdentifiers the role identifiers
	 * @param synchronizer    the synchronizer
	 */
	private static void addRoleListToTableCell(Element tr, List<String> roleIdentifiers, ISynchronizer synchronizer) {
		Element td = tr.appendElement("td").attr("colspan", "1");
		Element ul = td.appendElement("ul");
		for (String roleIdentifier : roleIdentifiers) {
			Element li = ul.appendElement("li");
			synchronizer.getRenderer().addRoleItem(li, null, roleIdentifier);
		}
	}

	/**
	 * Creates the data table.
	 *
	 * @param workitem     the workitem
	 * @param synchronizer the synchronizer
	 * @return the element
	 */
	private static Element createDataTable(IWorkitem workitem, ISynchronizer synchronizer) {
		Element table = new Element("table");

		if (workitem instanceof Team) {
			Team w = (Team) workitem;
			TeamDataitem d = w.getDataitem();
			addDataPair(ID.toString(), d.getIds(), table);
			addDataPair(DESCRIPTION.toString(), d.getDescription(), table);
			addDataPair(TEAMROLES.toString(), Parser.createTeamEntryTable(d.getTeamEntries(), synchronizer, false),
					table);
			addDataPair(RECURRENCERULE.toString(), d.getRecurrenceRule(), table);
			addDataPair(CATEGORY.toString(), d.getCategory(), table);
			addDataPair(TYPE.toString(), d.getType(), table);
			addDataPair(SUBTYPE.toString(), d.getSubtype(), table);
			addDataPair(STATUS.toString(), Parser.getStatus(d.getStatus()), table);
		}

		if (workitem instanceof Role) {
			Role w = (Role) workitem;
			RoleDataitem d = w.getDataitem();
			addDataPair(ID.toString(), d.getIds(), table);
			addDataPair(ABBREVIATION.toString(), d.getAbbreviation(), table);
			addDataPair(ROLEGROUP.toString(), d.getRolegroup(), table);
			addDataPair(PREDECESSOR.toString(), d.getParent(), table);
			addDataPair(PURPOSESHORT.toString(), d.getPurpose(), table);
			addDataPair(STATUS.toString(), Parser.getStatus(d.getStatus()), table);
			addDataPair(ORGANISATION.toString(), w.getOrganisationIdentifier(), table);
			addCommaList(SYNONYMS.toString(), d.getSynonyms(), table);
			addBulletList(PERSONS.toString(), d.getPersons(), table);
			addBulletList(ACTIVITIES.toString(), d.getActivities(), table);
			addBulletList(RESPONSIBILITY.toString(), d.getResponsibilities(), table);
			addBulletList(OPPORTUNITIES.toString(), d.getOpportunities(), table);
			addBulletList(RULES.toString(), d.getGuidelines(), table);
			addBulletList(COMPETENCIES.toString(), d.getCompetences(), table);
		}

		if (workitem instanceof Activity) {
			Activity w = (Activity) workitem;
			ActivityDataitem d = w.getDataitem();
			addDataPair(ID.toString(), d.getIds(), table);
			addDataPair(ACTIVITYID.toString(), d.getAid(), table);
			// addDataPair("Vorgänger", d.getParent(), table);
			addDataPair(DESCRIPTION.toString(), d.getDescription(), table);
			addDataPair(RESULT.toString(), d.getResults(), table);
			addDataPair(RESPONSIBLE.toString(), d.getResponsible(), table);
			addCommaList(SUPPORTER.toString(), d.getSupplier(), table);
			addCommaList(CONSULTANT.toString(), d.getConsultant(), table);
			addCommaList(INFORMED.toString(), d.getInformed(), table);
			addCommaList(HOWTOS.toString(), d.getHowtos(), table);
			addDataPair(SUBACTIVITIES.toString(), Parser.createActivityTable(d.getSubactivities(), synchronizer, false),
					table);
			addDataPair(STATUS.toString(), Parser.getStatus(d.getStatus()), table);
		}

		if (workitem instanceof Rolegroup) {
			Rolegroup w = (Rolegroup) workitem;
			RolegroupDataitem d = w.getDataitem();
			addDataPair(ID.toString(), d.getIds(), table);
			addDataPair(ABBREVIATION.toString(), d.getAbbreviation(), table);
			addDataPair(CONTACTPERSON.toString(), d.getLead(), table);
			addCommaList(SYNONYMS.toString(), d.getSynonyms(), table);
			addDataPair(PREDECESSOR.toString(), d.getParent(), table);
			addDataPair(RESPONSIBLE2.toString(), d.getResponsible(), table);
			addDataPair(SUMMARY.toString(), d.getSummary(), table);
			// addBulletList("Rollen", d.getRoles(), table);
			addDataPair(STATUS.toString(), Parser.getStatus(d.getStatus()), table);
		}

		if (workitem instanceof Person) {
			Person w = (Person) workitem;
			PersonDataitem d = w.getDataitem();
			addDataPair(ID.toString(), d.getIds(), table);
			addDataPair(NAME.toString(), d.getFullname(), table);
			// addDataPair(IMAGE.toString(), Parser.addImage(d.getAvatar(), 150, 1), table);

			if (StringUtil.isNotNullAndNotEmpty(d.getAvatar())) {
				Element tr = table.appendElement("tr");
				tr.appendElement("th").appendText(IMAGE.toString());
				tr.appendElement("td").append(Parser.addImage(d.getAvatar(), 100, 1));
			}

			addDataPair(CONTACTS2.toString(), Parser.getContacts(d.getContacts()), table);
			addDataPair(STATUS.toString(), Parser.getStatus(d.getStatus()), table);
			addDataPair(FTE.toString(), ""+Math.round(d.getFullTimeEquivalent())+"%", table);
			addDataPair(TEAMFRACTION.toString(), ""+Math.round(d.getTeamFraction())+"%", table);
			addDataPair(DATA.toString(), Parser.getDataTable(d.getData()), table);
		}

		return table;
	}

	/**
	 * Gets the data table.
	 *
	 * @param map the map
	 * @return the data table
	 */
	public static Element getDataTable(Map<String, String> map) {

		Map<String, String> emptyMap = new HashMap<String, String>();

		emptyMap.put(ABBREVIATION2.toString(), "-");

		if (map != null) {
			if (map.size() > 0) {
				Element table = new Element("table");
				for (String key : map.keySet()) {
					String value = map.get(key);
					addDataPair(key, value, table);
				}
				return table;
			} else {
				Element table = new Element("table");
				for (String key : emptyMap.keySet()) {
					String value = emptyMap.get(key);
					addDataPair(key, value, table);
				}
				return table;
			}
		} else {
			Element table = new Element("table");
			for (String key : emptyMap.keySet()) {
				String value = emptyMap.get(key);
				addDataPair(key, value, table);
			}
			return table;
		}
	}

	/**
	 * Adds the data pair.
	 *
	 * @param key   the key
	 * @param value the value
	 * @param table the table
	 */
	private static void addDataPair(String key, Element value, Element table) {
		Element tr = table.appendElement("tr");
		tr.appendElement("th").appendText(key.trim());
		tr.appendElement("td").append(value.toString().trim());
	}

	/**
	 * Adds the comma list.
	 *
	 * @param key      the key
	 * @param dataList the data list
	 * @param table    the table
	 */
	private static void addCommaList(String key, List<String> dataList, Element table) {
		if (dataList == null) {
			dataList = new ArrayList<String>();
		}
		Element tr = table.appendElement("tr");
		tr.appendElement("th").appendText(key.trim());
		Element td = tr.appendElement("td");
		for (int i = 0; i < dataList.size(); i++) {
			td.appendText(dataList.get(i));
			if ((i + 1) < dataList.size()) {
				td.appendText(", ");
			}
		}

	}

	/**
	 * Adds the bullet list.
	 *
	 * @param key      the key
	 * @param dataList the data list
	 * @param table    the table
	 */
	private static void addBulletList(String key, List<String> dataList, Element table) {
		Element tr = table.appendElement("tr");
		tr.appendElement("th").appendText(key.trim());
		Element td = tr.appendElement("td");
		if (dataList.size() > 0) {
			Element ul = td.appendElement("ul");
			for (String data : dataList) {
				ul.appendElement("li").appendText(data.trim());
			}
		}
	}

	/**
	 * Adds the data pair.
	 *
	 * @param key   the key
	 * @param value the value
	 * @param table the table
	 */
	private static void addDataPair(String key, String value, Element table) {
		if (value != null) {
			Element tr = table.appendElement("tr");
			tr.appendElement("th").appendText(key.trim());
			tr.appendElement("td").appendText(value.trim());
		}
	}

	/**
	 * Creates the page.
	 *
	 * @param workitem     the workitem
	 * @param spaceKey     the space key
	 * @param synchronizer the synchronizer
	 * @return the page
	 */
	public static Page createPage(IWorkitem workitem, String spaceKey, ISynchronizer synchronizer) {
		Element table = createDataTable(workitem, synchronizer);

		Page page = new Page();
		page.setType("page");
		page.setTitle(workitem.getTitle());

		Space space = new Space();
		space.setKey(spaceKey);
		page.setSpace(space);

		Body body = new Body();
		Storage storage = new Storage();
		storage.setRepresentation("storage");
		body.setStorage(storage);
		Element paragraph = new Element("div");
		Element pageProperties = Parser.getPagePropertiesMacro(table);
		pageProperties = Parser.getExpandMacro(pageProperties);

		pageProperties.appendTo(paragraph);

		Element s = Parser.getStatus(workitem.getStatus());
		s.appendTo(paragraph);

		if (workitem instanceof IWorkitemRenderer) {
			IWorkitemRenderer r = (IWorkitemRenderer) workitem;
			Element e = r.render(synchronizer);
			e.appendTo(paragraph);
		}

		String html = Parser.clean(paragraph);
		storage.setValue(html);
		page.setBody(body);
		return page;
	}

	/**
	 * Creates the page.
	 *
	 * @param report       the report
	 * @param spaceKey     the space key
	 * @param synchronizer the synchronizer
	 * @return the page
	 */
	public static Page createPage(IReport report, String spaceKey, ISynchronizer synchronizer) {
		Page page = new Page();
		page.setType("page");
		page.setTitle(report.getName());

		Space space = new Space();
		space.setKey(spaceKey);
		page.setSpace(space);

		Body body = new Body();
		Storage storage = new Storage();
		storage.setRepresentation("storage");
		body.setStorage(storage);

		Element paragraph = new Element("div");
		if (report instanceof IWorkitemRenderer) {
			IWorkitemRenderer r = (IWorkitemRenderer) report;
			Element e = r.render(synchronizer);
			e.appendTo(paragraph);
		}

		String html = Parser.clean(paragraph);
		storage.setValue(html);
		page.setBody(body);
		return page;
	}

	/**
	 * Gets the id from result.
	 *
	 * @param result the result
	 * @return the id from result
	 */
	public static int getIdFromResult(String result) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.setSerializationInclusion(Include.NON_NULL);
			Results r = mapper.readValue(result, Results.class);

			return Integer.parseInt(r.getResults().get(0).getContent().getId());
		} catch (JsonParseException e) {
		} catch (JsonMappingException e) {
		} catch (IOException e) {
		} catch (IndexOutOfBoundsException e) {
		} catch (NumberFormatException e) {
		}
		return 0;
	}

	/**
	 * Gets the id from page.
	 *
	 * @param page the page
	 * @return the id from page
	 */
	public static int getIdFromPage(String page) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.setSerializationInclusion(Include.NON_NULL);
			Page p = mapper.readValue(page, Page.class);

			return Integer.parseInt(p.getId());
		} catch (JsonParseException e) {
		} catch (JsonMappingException e) {
		} catch (IOException e) {
		} catch (NumberFormatException e) {

		}
		return 0;
	}

	/**
	 * Gets the label metadata.
	 *
	 * @param re the re
	 * @return the label metadata
	 */
	public static Metadata getLabelMetadata(IReport re) {
		Metadata m = new Metadata();
		Labels l = new Labels();
		List<org.rogatio.circlead.control.synchronizer.atlassian.content.Result> results = new ArrayList<org.rogatio.circlead.control.synchronizer.atlassian.content.Result>();
		org.rogatio.circlead.control.synchronizer.atlassian.content.Result r = new org.rogatio.circlead.control.synchronizer.atlassian.content.Result();
		r.setPrefix("global");
		r.setName("report");
		results.add(r);
		l.setResults(results);
		m.setLabels(l);
		return m;
	}

	/**
	 * Gets the label metadata.
	 *
	 * @param wi the wi
	 * @return the label metadata
	 */
	public static Metadata getLabelMetadata(IWorkitem wi) {
		Metadata m = new Metadata();
		Labels l = new Labels();
		List<org.rogatio.circlead.control.synchronizer.atlassian.content.Result> results = new ArrayList<org.rogatio.circlead.control.synchronizer.atlassian.content.Result>();
		org.rogatio.circlead.control.synchronizer.atlassian.content.Result r = new org.rogatio.circlead.control.synchronizer.atlassian.content.Result();
		r.setPrefix("global");
		r.setName(wi.getType().toLowerCase());
		results.add(r);
		l.setResults(results);
		m.setLabels(l);
		return m;
	}

	/**
	 * Gets the label metadata.
	 *
	 * @param label the label
	 * @return the label metadata
	 */
	public static Metadata getLabelMetadata(String label) {
		Metadata m = new Metadata();
		Labels l = new Labels();
		List<org.rogatio.circlead.control.synchronizer.atlassian.content.Result> results = new ArrayList<org.rogatio.circlead.control.synchronizer.atlassian.content.Result>();
		org.rogatio.circlead.control.synchronizer.atlassian.content.Result r = new org.rogatio.circlead.control.synchronizer.atlassian.content.Result();
		r.setPrefix("global");
		r.setName(label);
		results.add(r);
		l.setResults(results);
		m.setLabels(l);
		return m;
	}

	/**
	 * Gets the expand macro.
	 *
	 * @param content the content
	 * @return the expand macro
	 */
	public static Element getExpandMacro(Element content) {
		Element macro = new Element("ac:structured-macro");
		macro.attr("ac:name", "expand");
		macro.attr("ac:schema-version", "1");
		macro.attr("ac:macro-id", UUID.randomUUID().toString());
		Element param = macro.appendElement("ac:parameter");
		param.attr("ac:name", "title").appendText("Kopfdaten");
		Element body = macro.appendElement("ac:rich-text-body");
		content.appendTo(body);
		return macro;
	}

	/**
	 * Clean.
	 *
	 * @param element the element
	 * @return the string
	 */
	public static String clean(Element element) {
		String c = "";
		// To avoid linebreak in atlassian html-content the html needs to be "reduced".
		// If not it disable correct status-macro-rendering.
		String[] lines = element.toString().split("[\\r\\n]+");
		for (String string : lines) {
			c += string.trim();
		}
		return c;
	}

	/**
	 * Gets the status parameter.
	 *
	 * @param statusId the status id
	 * @return the status parameter
	 */
	private static WorkitemStatusParameter getStatusParameter(String statusId) {
		WorkitemStatusParameter[] params = WorkitemStatusParameter.values();
		for (WorkitemStatusParameter statusParameter : params) {
			if (statusParameter.isEquals(statusId)) {
				return statusParameter;
			}
		}
		return null;
	}

	/**
	 * Gets the contacts.
	 *
	 * @param contacts the contacts
	 * @return the contacts
	 */
	public static Element getContacts(List<ContactDataitem> contacts) {
		Element div = new Element("div");

		for (ContactDataitem contact : contacts) {
			Element table = div.appendElement("table");
			addDataPair(TYPE.toString(), contact.getType(), table);
			addDataPair(SUBTYPE.toString(), contact.getSubtype(), table);
			addDataPair(ORGANISATION.toString(), contact.getOrganisation(), table);
			addDataPair(ADRESS.toString(), contact.getAddress(), table);
			addDataPair(MAIL.toString(), contact.getMail(), table);
			addDataPair(MOBILE.toString(), contact.getMobile(), table);
			addDataPair(PHONE.toString(), contact.getPhone(), table);
		}

		return div;
	}

	/**
	 * Gets the status.
	 *
	 * @param title the title
	 * @return the status
	 */
	public static Element getStatus(String title) {

		String color = "Red";
		String name = UNKNOWN.toString();

		WorkitemStatusParameter s = getStatusParameter(title);
		if (s != null) {
			color = s.getColor();
			name = s.getName();
		}

		Element macro = new Element("ac:structured-macro");
		macro.attr("ac:name", "status");
		macro.attr("ac:schema-version", "1");
		macro.attr("ac:macro-id", UUID.randomUUID().toString());
		Element param = macro.appendElement("ac:parameter");
		param.attr("ac:name", "colour").appendText(color);
		param = macro.appendElement("ac:parameter");
		param.attr("ac:name", "title").appendText(name);
		return macro;
	}

	public static Element getStatus(String title, String forcedColor) {

		String color = "Red";
		String name = UNKNOWN.toString();

		WorkitemStatusParameter s = getStatusParameter(title);
		if (s != null) {
			color = s.getColor();
			name = s.getName();
		}

		color = forcedColor;

		Element macro = new Element("ac:structured-macro");
		macro.attr("ac:name", "status");
		macro.attr("ac:schema-version", "1");
		macro.attr("ac:macro-id", UUID.randomUUID().toString());
		Element param = macro.appendElement("ac:parameter");
		param.attr("ac:name", "colour").appendText(color);
		param = macro.appendElement("ac:parameter");
		param.attr("ac:name", "title").appendText(name);
		return macro;
	}
}
