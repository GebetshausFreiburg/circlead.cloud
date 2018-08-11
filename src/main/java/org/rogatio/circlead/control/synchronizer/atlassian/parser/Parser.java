package org.rogatio.circlead.control.synchronizer.atlassian.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.jsoup.nodes.Element;
import org.rogatio.circlead.control.synchronizer.ISynchronizer;
import org.rogatio.circlead.control.synchronizer.atlassian.content.Body;
import org.rogatio.circlead.control.synchronizer.atlassian.content.Labels;
import org.rogatio.circlead.control.synchronizer.atlassian.content.Metadata;
import org.rogatio.circlead.control.synchronizer.atlassian.content.Page;
import org.rogatio.circlead.control.synchronizer.atlassian.content.Space;
import org.rogatio.circlead.control.synchronizer.atlassian.content.Storage;
import org.rogatio.circlead.control.synchronizer.atlassian.search.Results;
import org.rogatio.circlead.model.StatusParameter;
import org.rogatio.circlead.model.data.ActivityDataitem;
import org.rogatio.circlead.model.data.ContactDataitem;
import org.rogatio.circlead.model.data.PersonDataitem;
import org.rogatio.circlead.model.data.RoleDataitem;
import org.rogatio.circlead.model.data.RolegroupDataitem;
import org.rogatio.circlead.model.work.Activity;
import org.rogatio.circlead.model.work.IWorkitem;
import org.rogatio.circlead.model.work.Person;
import org.rogatio.circlead.model.work.Role;
import org.rogatio.circlead.model.work.Rolegroup;
import org.rogatio.circlead.view.IReport;
import org.rogatio.circlead.view.IWorkitemRenderer;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Parser {

	public static Element getPagePropertiesMacro(Element content) {
		Element macro = new Element("ac:structured-macro");
		macro.attr("ac:name", "details");
		macro.attr("ac:schema-version", "1");
		macro.attr("ac:macro-id", UUID.randomUUID().toString());
		Element body = macro.appendElement("ac:rich-text-body");
		content.appendTo(body);
		return macro;
	}
	
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
		param.appendText(""+maximumIssues);
		
		param = macro.appendElement("ac:parameter");
		param.attr("ac:name", "jqlQuery");
		param.appendText(jqlQuery);
		
		param = macro.appendElement("ac:parameter");
		param.attr("ac:name", "serverId");
		param.appendText(serverId);
		
		return macro;
	}

	private static Element createDataTable(IWorkitem workitem, ISynchronizer synchronizer) {
		Element table = new Element("table");

		if (workitem instanceof Role) {
			Role w = (Role) workitem;
			RoleDataitem d = w.getDataitem();
			addDataPair("Id", d.getIds(), table);
			addDataPair("Abkürzung", d.getAbbreviation(), table);
			addDataPair("Rollengruppe", d.getRolegroup(), table);
			addDataPair("Vorgänger", d.getParent(), table);
			addDataPair("Status", Parser.getStatus(d.getStatus()), table);
			addDataPair("Organisation", w.getOrganisationIdentifier(), table);
			addCommaList("Synonyme", d.getSynonyms(), table);
			addBulletList("Personen", d.getPersons(), table);
			addBulletList("Aktivitäten", d.getActivities(), table);
			addBulletList("Verantwortung", d.getResponsibilities(), table);
			addBulletList("Befugnisse", d.getOpportunities(), table);
			addBulletList("Regeln", d.getGuidelines(), table);
			addBulletList("Kompetenzen", d.getCompetences(), table);
		}

		if (workitem instanceof Activity) {
			Activity w = (Activity) workitem;
			ActivityDataitem d = w.getDataitem();
			addDataPair("Id", d.getIds(), table);
			addDataPair("Rolle", d.getRole(), table);
			addCommaList("HowTos", d.getHowtos(), table);
			addDataPair("Status", Parser.getStatus(d.getStatus()), table);
		}
		
		if (workitem instanceof Rolegroup) {
			Rolegroup w = (Rolegroup) workitem;
			RolegroupDataitem d = w.getDataitem();
			addDataPair("Id", d.getIds(), table);
			addDataPair("Abkürzung", d.getAbbreviation(), table);
			addDataPair("Ansprechpartner", d.getLead(), table);
			addCommaList("Synonyme", d.getSynonyms(), table);
			addDataPair("Vorgänger", d.getParent(), table);
			addDataPair("Verantwortlicher", d.getResponsible(), table);
			addDataPair("Zusammenfassung", d.getSummary(), table);
//			addBulletList("Rollen", d.getRoles(), table);
			addDataPair("Status", Parser.getStatus(d.getStatus()), table);
		}

		if (workitem instanceof Person) {
			Person w = (Person) workitem;
			PersonDataitem d = w.getDataitem();
			addDataPair("Id", d.getIds(), table);
			addDataPair("Name", d.getFullname(), table);
			addDataPair("Kontakte", Parser.getContacts(d.getContacts()), table);
			// addDataPair("Verantwortlicher", d.getResponsible(), table);
			// addDataPair("Zusammenfassung", d.getSummary(), table);
			// addBulletList("Rollen", d.getRoles(), table);
			addDataPair("Status", Parser.getStatus(d.getStatus()), table);
			addDataPair("Daten", Parser.getDataTable(d.getData()), table);
		}

		return table;
	}

	public static Element getDataTable(Map<String, String> map) {
		if (map!=null) {
			if (map.size()>0) {
				Element table = new Element("table");
				for (String key : map.keySet()) {
					String value = map.get(key);
					addDataPair(key, value, table);
				}
				return table;
			}
		}
		return new Element("div").appendText("-");
	}
	
	
	private static void addDataPair(String key, Element value, Element table) {
		Element tr = table.appendElement("tr");
		tr.appendElement("th").appendText(key.trim());
		tr.appendElement("td").append(value.toString().trim());
	}

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

	private static void addDataPair(String key, String value, Element table) {
		if (value != null) {
			Element tr = table.appendElement("tr");
			tr.appendElement("th").appendText(key.trim());
			tr.appendElement("td").appendText(value.trim());
		}
	}

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

	
	public static int getIdFromResult(String result) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.setSerializationInclusion(Include.NON_NULL);
			Results r = mapper.readValue(result, Results.class);

			// System.out.println("IIID"+p.getId());

			return Integer.parseInt(r.getResults().get(0).getContent().getId());
		} catch (JsonParseException e) {
		} catch (JsonMappingException e) {
		} catch (IOException e) {
		} catch (IndexOutOfBoundsException e) {
		} catch (NumberFormatException e) {
		}
		return 0;
	}

	public static int getIdFromPage(String page) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.setSerializationInclusion(Include.NON_NULL);
			Page p = mapper.readValue(page, Page.class);

			// System.out.println("IIID"+p.getId());

			return Integer.parseInt(p.getId());
		} catch (JsonParseException e) {
		} catch (JsonMappingException e) {
		} catch (IOException e) {
		} catch (NumberFormatException e) {

		}
		return 0;
	}

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

	public static String clean(Element element) {
		String c = "";
		String[] lines = element.toString().split(System.getProperty("line.separator"));
		for (String string : lines) {
			c += string.trim();
		}
		return c;
	}

	private static StatusParameter getStatusParameter(String statusId) {
		StatusParameter[] params = StatusParameter.values();
		for (StatusParameter statusParameter : params) {
			if (statusParameter.isEquals(statusId)) {
				return statusParameter;
			}
		}
		return null;
	}

	public static Element getContacts(List<ContactDataitem> contacts) {
		Element div = new Element("div");

		for (ContactDataitem contact : contacts) {
			Element table = div.appendElement("table");
			addDataPair("Typ", contact.getType(), table);
			addDataPair("Subtyp", contact.getSubtype(), table);
			addDataPair("Organisation", contact.getOrganisation(), table);
			addDataPair("Adresse", contact.getAddress(), table);
			addDataPair("Mail", contact.getMail(), table);
			addDataPair("Mobil", contact.getMobile(), table);
			addDataPair("Festnetz", contact.getPhone(), table);
			// div.appendElement("br");
		}

		return div;
	}

	public static Element getStatus(String title) {

		String color = "Red";
		String name = "Unknown";

		StatusParameter s = getStatusParameter(title);
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
}
