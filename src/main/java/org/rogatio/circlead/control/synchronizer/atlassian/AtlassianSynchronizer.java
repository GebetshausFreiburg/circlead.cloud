/*
 * Circlead - Develop and structure evolutionary Organisations
 * 
 * @author Matthias Wegner
 * @version 0.1
 * @since 01.07.2018
 * 
 */
package org.rogatio.circlead.control.synchronizer.atlassian;

import static org.rogatio.circlead.control.synchronizer.atlassian.Constant.PASSWORD;
import static org.rogatio.circlead.control.synchronizer.atlassian.Constant.URL;
import static org.rogatio.circlead.control.synchronizer.atlassian.Constant.USER;
import static org.rogatio.circlead.control.synchronizer.atlassian.Constant.DEDICATEDSERVER;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.rogatio.circlead.control.Repository;
import org.rogatio.circlead.control.synchronizer.DefaultSynchronizer;
import org.rogatio.circlead.control.synchronizer.SynchronizerException;
import org.rogatio.circlead.control.synchronizer.SynchronizerFactory;
import org.rogatio.circlead.control.synchronizer.SynchronizerResult;
import org.rogatio.circlead.control.synchronizer.atlassian.content.Ancestor;
import org.rogatio.circlead.control.synchronizer.atlassian.content.Metadata;
import org.rogatio.circlead.control.synchronizer.atlassian.content.Page;
import org.rogatio.circlead.control.synchronizer.atlassian.content.Version;
import org.rogatio.circlead.control.synchronizer.atlassian.parser.HeadTableParserElement;
import org.rogatio.circlead.control.synchronizer.atlassian.parser.IParserElement;
import org.rogatio.circlead.control.synchronizer.atlassian.parser.ListParserElement;
import org.rogatio.circlead.control.synchronizer.atlassian.parser.Parser;
import org.rogatio.circlead.control.synchronizer.atlassian.parser.StatusParserElement;
import org.rogatio.circlead.control.synchronizer.atlassian.parser.PairTableParserElement;
import org.rogatio.circlead.control.synchronizer.atlassian.parser.TextParserElement;
import org.rogatio.circlead.control.synchronizer.atlassian.search.Result;
import org.rogatio.circlead.control.synchronizer.atlassian.search.Results;
import org.rogatio.circlead.model.WorkitemParameter;
import org.rogatio.circlead.model.WorkitemType;
import org.rogatio.circlead.model.data.HowTo;
import org.rogatio.circlead.model.data.Report;
import org.rogatio.circlead.model.work.Activity;
import org.rogatio.circlead.model.work.IWorkitem;
import org.rogatio.circlead.model.work.Person;
import org.rogatio.circlead.model.work.Role;
import org.rogatio.circlead.model.work.Rolegroup;
import org.rogatio.circlead.util.StringUtil;
import org.rogatio.circlead.view.AtlassianRendererEngine;
import org.rogatio.circlead.view.ISynchronizerRendererEngine;
import org.rogatio.circlead.view.report.IReport;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * The Class AtlassianSynchronizer.
 */
public class AtlassianSynchronizer extends DefaultSynchronizer {

	/** The Constant logger. */
	private final static Logger logger = LogManager.getLogger(AtlassianSynchronizer.class);

	/** The circlead space. */
	private String circleadSpace = null;

	/**
	 * Instantiates a new atlassian synchronizer.
	 */
	public AtlassianSynchronizer() {

	}

	/**
	 * Gets the set key of circlead-space .
	 *
	 * @return the space key
	 */
	public String getSpaceKey() {
		return this.circleadSpace;
	}

	/**
	 * Instantiates a new atlassian synchronizer.
	 *
	 * @param spaceKey
	 *            the key of the circlead space
	 */
	public AtlassianSynchronizer(String spaceKey) {
		circleadSpace = spaceKey;
	}

	/** The confluence client. */
	private ConfluenceClient confluenceClient;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.rogatio.circlead.control.synchronizer.DefaultSynchronizer#init()
	 */
	@Override
	public void init() {
		// Initliaize the confluence client interface. Uses static parameters to secure credetials with gitignore
		confluenceClient = new ConfluenceClient(URL, USER, PASSWORD, DEDICATEDSERVER);
	}

	/**
	 * Gets the single acestor list. Is the parent page in confluence.
	 * Page in confluence must be named similar to plural name of holded Workitem-Class
	 *
	 * @param wi
	 *            the wi
	 * @return the single acestor list
	 */
	private List<Ancestor> getSingleAcestorList(IWorkitem wi) {
		// Check if parent-workitem could be found and load id via rest
		String id = this.getAcestorId(wi.getType());
		if (id != null) {
			//if not found, then create one valid json-parent, called acestor-page in confluence 
			if (!id.equals("0")) {
				List<Ancestor> list = new ArrayList<Ancestor>();
				Ancestor a = new Ancestor();
				a.setId(id);

				if (wi instanceof Activity) {
					a.setTitle("Activities");
				}
				if (wi instanceof Role) {
					a.setTitle("Roles");
				}
				if (wi instanceof Rolegroup) {
					a.setTitle("Rolegroups");
				}
				if (wi instanceof Person) {
					a.setTitle("Persons");
				}

				a.setType("page");
				list.add(a);
				return list;
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.rogatio.circlead.control.synchronizer.DefaultSynchronizer#add(org.rogatio .circlead.view.IReport)
	 */
	@Override
	public SynchronizerResult add(IReport report) throws SynchronizerException {
		//Set actual used synchronizer to singleton. Is needed for correct finding and setting of id
		SynchronizerFactory.getInstance().setActual(this);

		// Create confluence-page-object from report
		Page page = Parser.createPage(report, circleadSpace, this);

		try {
			// Create label for confluence-page
			Metadata m = Parser.getLabelMetadata(report);
			page.setMetadata(m);

			// Set parent-oage to report
			List<Ancestor> ancestors = new ArrayList<Ancestor>();
			Ancestor a = new Ancestor();
			a.setId(getAcestorId("report"));
			a.setTitle("Reports");
			a.setType("page");
			ancestors.add(a);
			page.setAncestors(ancestors);

			// Instantiate Jackson-JSON_Mapper and create valid json-string
			ObjectMapper mapper = new ObjectMapper();
			mapper.setSerializationInclusion(Include.NON_NULL);
			String data = mapper.writeValueAsString(page);

			//Post json to rest. Save response
			SynchronizerResult res = confluenceClient.post(confluenceClient.getRestPrefix() + "content/", data);
			
			// return result
			return res;
		} catch (JsonProcessingException e) {
			logger.error(e);
		} catch (IOException e) {
			logger.error(e);
		}
		
		//return null if something went wrong
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.rogatio.circlead.control.synchronizer.DefaultSynchronizer#add(org.rogatio .circlead.model.work.IWorkitem)
	 */
	@Override
	public SynchronizerResult add(IWorkitem workitem) throws SynchronizerException {
		//Set actual used synchronizer to singleton. Is needed for correct finding and setting of id
		SynchronizerFactory.getInstance().setActual(this);

		// Create Confluence-POJO-Object from workitem
		Page page = Parser.createPage(workitem, circleadSpace, this);
		page.setTitle(workitem.getTitle());

		// Add label to confluence-page. Called metadata in confluence
		Metadata m = Parser.getLabelMetadata(workitem);
		page.setMetadata(m);

		// Add parent-page to confluence-page.
		List<Ancestor> ancestors = getSingleAcestorList(workitem);
		if (ancestors != null) {
			page.setAncestors(ancestors);
		}

		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.setSerializationInclusion(Include.NON_NULL);
			// Create valid json from POJO-Object
			String data = mapper.writeValueAsString(page);
			// Catch result from rest-interface writing confluence-page
			SynchronizerResult res = confluenceClient.post(confluenceClient.getRestPrefix() + "content/", data);
			return res;
		} catch (JsonProcessingException e) {
			logger.error(e);
		} catch (IOException e) {
			logger.error(e);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.rogatio.circlead.control.synchronizer.DefaultSynchronizer#update(org. rogatio.circlead.model.work.IWorkitem)
	 */
	@Override
	public SynchronizerResult update(IWorkitem workitem) {
		//Set actual used synchronizer to singleton. Is needed for correct finding and setting of id
		SynchronizerFactory.getInstance().setActual(this);

		// Create Confluence-POJO-Object from workitem
		Page page = Parser.createPage(workitem, circleadSpace, this);

		logger.info("Update '" + URL + confluenceClient.getRestPrefix() + "content/" + workitem.getId(this) + "'");

		// Increment version-number if version and page already exists
		if (workitem.getVersion() != null) {
			Version v = new Version();
			v.setNumber(StringUtil.toInt(workitem.getVersion()) + 1);
			page.setVersion(v);
		} else {
			// If version not exists, then create it with version 1
			Version v = new Version();
			v.setNumber(1);
			page.setVersion(v);
		}

		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.setSerializationInclusion(Include.NON_NULL);
			// Create valid json from POJO-Object
			String data = mapper.writeValueAsString(page);
			String uri = confluenceClient.getRestPrefix() + "content/" + workitem.getId(this);
			// Catch result from rest-interface writing confluence-page
			SynchronizerResult res = confluenceClient.put(uri, data);

			logger.debug(workitem.getTitle() + ": " + res.toString());

			return res;
		} catch (JsonProcessingException e) {
			logger.error(e);
		} catch (IOException e) {
			logger.error(e);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.rogatio.circlead.control.synchronizer.DefaultSynchronizer#update(org. rogatio.circlead.view.IReport)
	 */
	@Override
	public SynchronizerResult update(IReport report) {
		//Set actual used synchronizer to singleton. Is needed for correct finding and setting of id
		SynchronizerFactory.getInstance().setActual(this);

		// Create Confluence-POJO-Object from report
		Page page = Parser.createPage(report, circleadSpace, this);

		Report repo = Repository.getInstance().getReport(report.getName());
		if (repo != null) {

			logger.info("Update '" + URL + confluenceClient.getRestPrefix() + "content/" + repo.getId() + "' (" + report.getName() + ")");

			Integer version = 0;
			try {
				ObjectMapper omapper = new ObjectMapper();
				omapper.setSerializationInclusion(Include.NON_NULL);
				SynchronizerResult pageR = confluenceClient.getPage(Integer.parseInt(repo.getId()));
				Page p = omapper.readValue(pageR.getContent(), Page.class);

				version = p.getVersion().getNumber() + 1;
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			try {
				ObjectMapper mapper = new ObjectMapper();
				mapper.setSerializationInclusion(Include.NON_NULL);

				Metadata m = Parser.getLabelMetadata(report);
				page.setMetadata(m);

				Version v = new Version();
				v.setNumber(version);
				page.setVersion(v);

				String data = mapper.writeValueAsString(page);
				String uri = confluenceClient.getRestPrefix() + "content/" + repo.getId();
				SynchronizerResult res = confluenceClient.put(uri, data);
	
				return res;
			} catch (JsonProcessingException e) {
				logger.error(e);
			} catch (IOException e) {
				logger.error(e);
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.rogatio.circlead.control.synchronizer.DefaultSynchronizer#getIdPattern()
	 */
	@Override
	public String getIdPattern() {
		return "^[0-9]{9}$";
	}

	/**
	 * Gets the acestor id.
	 *
	 * @param title
	 *            the title
	 * @param page
	 *            the page
	 * @return the acestor id
	 */
	private String getAcestorId(String title, Page page) {
		for (Ancestor a : page.getAncestors()) {
			if (a.getTitle().equalsIgnoreCase(title)) {
				return a.getId();
			}
		}
		return null;
	}

	/** The acestor pages. */
	private Map<String, String> acestorPages = new HashMap<String, String>();

	/**
	 * Gets the acestor id.
	 *
	 * @param type
	 *            the type
	 * @return the acestor id
	 */
	public String getAcestorId(String type) {

		String id = acestorPages.get(type.toLowerCase());

		if (id == null) {
			if (type.equalsIgnoreCase("report")) {
				SynchronizerResult page = confluenceClient.search("type=\"page\" and title=\"Reports\"");
				id = "" + Parser.getIdFromResult(page.getContent());
			}
			if (type.equalsIgnoreCase("role")) {
				SynchronizerResult page = confluenceClient.search("type=\"page\" and title=\"Roles\"");
				id = "" + Parser.getIdFromResult(page.getContent());
			}
			if (type.equalsIgnoreCase("activity")) {
				SynchronizerResult page = confluenceClient.search("type=\"page\" and title=\"Activities\"");
				id = "" + Parser.getIdFromResult(page.getContent());
			}
			if (type.equalsIgnoreCase("rolegroup")) {
				SynchronizerResult page = confluenceClient.search("type=\"page\" and title=\"Rolegroups\"");
				id = "" + Parser.getIdFromResult(page.getContent());
			}
			if (type.equalsIgnoreCase("person")) {
				SynchronizerResult page = confluenceClient.search("type=\"page\" and title=\"Persons\"");
				id = "" + Parser.getIdFromResult(page.getContent());
			}
		}

		return id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.rogatio.circlead.control.synchronizer.DefaultSynchronizer#get(java.lang. String)
	 */
	@Override
	public IWorkitem get(String indexId) throws SynchronizerException {
		//Set actual used synchronizer to singleton. Is needed for correct finding and setting of id
		SynchronizerFactory.getInstance().setActual(this);

		// If index is not correct, then throw exception
		if (indexId.endsWith(".json")) {
			throw new SynchronizerException("Item with id '" + indexId + "' could not be loaded with Atlassian Synchronizer.");
		}

		// ignore all prefix, because id in atlassian-confluence is a numeric int. So parse for correct id on right side
		int idx = indexId.lastIndexOf("/");
		if (idx > 0) {
			indexId = indexId.substring(idx + 1, indexId.length());
		}

		IWorkitem wi = null;

		Map<String, IParserElement> pairs = new HashMap<String, IParserElement>();

		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.setSerializationInclusion(Include.NON_NULL);
			SynchronizerResult page = confluenceClient.getPage(Integer.parseInt(indexId));

			if (page == null) {
				throw new SynchronizerException("Item with id '" + indexId + "' could not be founded/loaded with Atlassian Synchronizer.");
			}

			Page p = mapper.readValue(page.getContent(), Page.class);

			String created = p.getHistory().getCreatedDate();
			pairs.put("created", new TextParserElement(created));
			String modified = p.getHistory().getLastUpdated().getWhen();
			pairs.put("modified", new TextParserElement(modified));
			String version = "" + p.getVersion().getNumber();
			pairs.put("version", new TextParserElement(version));

			String type = null;
			String acestorId = null;

			for (org.rogatio.circlead.control.synchronizer.atlassian.content.Result result : p.getMetadata().getLabels().getResults()) {

				String label = null;
				if (result.getName() != null) {
					label = result.getName();
				}
				if (result.getLabel() != null) {
					label = result.getLabel();
				}

				if (label.equalsIgnoreCase("role")) {
					type = "role";
					acestorId = getAcestorId("Roles", p);
				}
				if (label.equalsIgnoreCase("activity")) {
					type = "activity";
					acestorId = getAcestorId("Activities", p);
				}
				if (label.equalsIgnoreCase("rolegroup")) {
					type = "rolegroup";
					acestorId = getAcestorId("Rolegroups", p);
				}
				if (label.equalsIgnoreCase("person")) {
					type = "person";
					acestorId = getAcestorId("Persons", p);
				}
				acestorPages.put(type, acestorId);
			}

			if (type == null) {
				throw new SynchronizerException("Type of Workitem (Page-Id '" + indexId + "') not found in label.");
			}

			Document doc = Jsoup.parse(p.getBody().getStorage().getValue());

			Elements elements = doc.getElementsByAttributeValue("ac:name", "details");

			if (elements.size() == 0) {
				throw new SynchronizerException("Workitem (Page-Id '" + indexId + "') contains no data.");
			}

			for (Element element : elements) {
				if (element.getElementsByTag("tbody").size() > 0) {
					Element table = element.getElementsByTag("tbody").get(0);
					for (Element row : table.getElementsByTag("tr")) {

						// Only allow table with th left and td right, a pair-couple
						if (row.children().size() == 2) {
							String key = row.getElementsByTag("th").get(0).text();
							Element unparsedValue = row.getElementsByTag("td").get(0);

							IParserElement parserElement;

							if (WorkitemParameter.STATUS.has(key)) {
								parserElement = new StatusParserElement(unparsedValue);
							} else if (WorkitemParameter.ACTIVITY.has(key)) {
								parserElement = new ListParserElement(unparsedValue);
							} else if (WorkitemParameter.RESPONSIBILITY.has(key)) {
								parserElement = new ListParserElement(unparsedValue);
							} else if (WorkitemParameter.DATA.has(key)) {
								parserElement = new PairTableParserElement(unparsedValue);
							} else if (WorkitemParameter.ACTIVITY.has(key)) {
								parserElement = new ListParserElement(unparsedValue);
							} else if (key.equalsIgnoreCase("Teilaktivitäten")) {
								parserElement = new HeadTableParserElement(unparsedValue);
							} else if (WorkitemParameter.CONTACTS.has(key)) {
								parserElement = new PairTableParserElement(unparsedValue);
							} else if (WorkitemParameter.OPPORTUNITIES.has(key)) {
								parserElement = new ListParserElement(unparsedValue);
							} else if (WorkitemParameter.GUIDELINES.has(key)) {
								parserElement = new ListParserElement(unparsedValue);
							} else if (WorkitemParameter.COMPETENCES.has(key)) {
								parserElement = new ListParserElement(unparsedValue);
							} else if (WorkitemParameter.PERSONS.has(key)) {
								parserElement = new ListParserElement(unparsedValue);
							} else if (WorkitemParameter.ROLES.has(key)) {
								parserElement = new ListParserElement(unparsedValue);
							} else {
								parserElement = new TextParserElement(unparsedValue);
							}
							pairs.put(key, parserElement);
						} 
					}
				}
			}

			wi = setData(pairs, type, indexId);
			wi.setTitle(p.getTitle());

			logger.debug("Load: code=" + page.getCode() + ", message=" + page.getMessage() + ", source=" + page.getSource() + ", title=" + p.getTitle());

		} catch (JsonParseException e) {
			throw new SynchronizerException("Error (JsonParse) loading item '" + indexId + "'. " + e.getMessage(), e.getCause());
		} catch (JsonMappingException e) {
			throw new SynchronizerException("Error (JsonMapping) loading item '" + indexId + "'. " + e.getMessage(), e.getCause());
		} catch (IOException e) {
			throw new SynchronizerException("Error (IOException) loading item '" + indexId + "'. " + e.getMessage(), e.getCause());
		}

		return wi;
	}

	/**
	 * Sets the data.
	 *
	 * @param pairs
	 *            the pairs
	 * @param type
	 *            the type
	 * @param indexId
	 *            the index id
	 * @return the i workitem
	 */
	private IWorkitem setData(Map<String, IParserElement> pairs, String type, String indexId) {
		Vector<String> keys = new Vector<String>(pairs.keySet());

		if (type.equalsIgnoreCase("activity")) {
			Activity activity = new Activity();

			for (String key : keys) {
				IParserElement value = pairs.get(key);
				if (key.equalsIgnoreCase("Id")) {
					activity.getDataitem().setUid(value.toString());
				} else if (key.equalsIgnoreCase("Created")) {
					activity.setCreated(value.toString());
				} else if (key.equalsIgnoreCase("Modified")) {
					activity.setModified(value.toString());
				} else if (WorkitemParameter.HOWTOS.has(key)) {
					activity.setHowTos(value.toString());
				} else if (key.equalsIgnoreCase("Teilaktivitäten")) {
					activity.setSubactivities((HeadTableParserElement) value);
				} else if (key.equalsIgnoreCase("AID")) {
					activity.setAid(value.toString());
				} else if (key.equalsIgnoreCase("Beschreibung")) {
					activity.setDescription(value.toString());
				} else if (key.contains("Ergebnis")) {
					activity.setResults(value.toString());
				} else if (key.equalsIgnoreCase("Version")) {
					activity.setVersion(value.toString());
				} else if (key.equalsIgnoreCase("Rolle") || key.equalsIgnoreCase("Verantwortlicher") || key.equalsIgnoreCase("Durchführender")) {
					activity.setResponsibleIdentifier(value.toString());
				} else if (key.equalsIgnoreCase("Informierte")) {
					activity.setInformedIdentifier(value.toString());
				} else if (key.equalsIgnoreCase("Unterstützer")) {
					activity.setSupplierIdentifier(value.toString());
				} else if (key.equalsIgnoreCase("Berater")) {
					activity.setConsultantIdentifier(value.toString());
				} else if (WorkitemParameter.STATUS.has(key)) {
					activity.setStatus(value.toString());
				} else {
					logger.debug("Value from parser not set: key=" + key + ", value=" + pairs.get(key));
				}
			}

			activity.setId(indexId, this);

			return activity;
		}

		if (type.equalsIgnoreCase("person")) {
			Person person = new Person();

			for (String key : keys) {
				IParserElement value = pairs.get(key);
				if (key.equalsIgnoreCase("Id")) {
					person.getDataitem().setUid(value.toString());
				} else if (key.equalsIgnoreCase("Created")) {
					person.setCreated(value.toString());
				} else if (key.equalsIgnoreCase("Modified")) {
					person.setModified(value.toString());
				} else if (key.equalsIgnoreCase("Adresse") || key.equalsIgnoreCase("Mobil") || key.equalsIgnoreCase("Mail") || key.equalsIgnoreCase("Festnetz")
						|| key.equalsIgnoreCase("Typ") || key.equalsIgnoreCase("Subtyp")) {
					// Ignore, because its inner table
					// Do explicit nothing
				} else if (key.equalsIgnoreCase("Version")) {
					person.setVersion(value.toString());
				} else if (key.equalsIgnoreCase("Name")) {
					person.setFullname(value.toString());
				} else if (key.equalsIgnoreCase("Kontakte")) {
					person.setContacts((PairTableParserElement) value);
				} else if (key.equalsIgnoreCase("Daten")) {
					person.setData((PairTableParserElement) value);
				} else if (WorkitemParameter.STATUS.has(key)) {
					person.setStatus(value.toString());
				} else {

					IParserElement ipe = pairs.get(key);

					if (ipe instanceof TextParserElement) {
						TextParserElement tpe = (TextParserElement) ipe;
						Element elem = tpe.getElement();
						if (elem != null) {
							if (!elem.parent().parent().parent().tagName().equalsIgnoreCase("table")) {
								logger.debug("Value from parser not set: key=" + key + ", value=" + pairs.get(key));
							}
						}
					}

				}
			}

			person.setId(indexId, this);

			return person;
		}

		if (type.equalsIgnoreCase("rolegroup")) {
			Rolegroup rolegroup = new Rolegroup();

			for (String key : keys) {
				IParserElement value = pairs.get(key);
				if (key.equalsIgnoreCase("Id")) {
					rolegroup.getDataitem().setUid(value.toString());
				} else if (key.equalsIgnoreCase("Created")) {
					rolegroup.setCreated(value.toString());
				} else if (key.equalsIgnoreCase("Modified")) {
					rolegroup.setModified(value.toString());
				} else if (key.equalsIgnoreCase("Version")) {
					rolegroup.setVersion(value.toString());
				} else if (key.equalsIgnoreCase("Ansprechpartner")) {
					rolegroup.setLeadIdentifier(value.toString());
				} else if (WorkitemParameter.ABBREVIATION.has(key)) {
					rolegroup.setAbbreviation(value.toString());
				} else if (WorkitemParameter.SYNONYM.has(key)) {
					rolegroup.setSynonyms(value.toString());
				} else if (key.equalsIgnoreCase("Vorgänger")) {
					rolegroup.setParentIdentifier(value.toString());
				} else if (key.equalsIgnoreCase("Verantwortlicher")) {
					rolegroup.setResponsibleIdentifier(value.toString());
				} else if (key.equalsIgnoreCase("Zusammenfassung")) {
					rolegroup.setSummary(value.toString());
				} else if (WorkitemParameter.STATUS.has(key)) {
					rolegroup.setStatus(value.toString());
				} else {
					logger.debug("Value from parser not set: key=" + key + ", value=" + pairs.get(key));
				}
			}

			rolegroup.setId(indexId, this);

			return rolegroup;
		}

		if (type.equalsIgnoreCase("role")) {
			Role role = new Role();

			for (String key : keys) {
				IParserElement value = pairs.get(key);
				if (key.equalsIgnoreCase("Id")) {
					role.getDataitem().setUid(value.toString());
				} else if (key.equalsIgnoreCase("Created")) {
					role.setCreated(value.toString());
				} else if (key.equalsIgnoreCase("Modified")) {
					role.setModified(value.toString());
				} else if (key.equalsIgnoreCase("Version")) {
					role.setVersion(value.toString());
				} else if (WorkitemParameter.STATUS.has(key)) {
					role.setStatus(value.toString());
				} else if (WorkitemParameter.ORGANISATION.has(key)) {
					role.setOrganisationIdentifier(value.toString());
				} else if (WorkitemParameter.ROLEGROUP.has(key)) {
					role.setRolegroupIdentifier(value.toString());
				} else if (WorkitemParameter.SYNONYM.has(key)) {
					role.setSynonyms(value.toString());
				} else if (WorkitemParameter.ABBREVIATION.has(key)) {
					role.setAbbreviation(value.toString());
				} else if (WorkitemParameter.PURPOSE.has(key)) {
					role.setPurpose(value.toString());
				} else if (WorkitemParameter.PARENT.has(key)) {
					role.setParent(value.toString());
				} else if (WorkitemParameter.ACTIVITY.has(key)) {
					role.setActivities((ListParserElement) value);
				} else if (WorkitemParameter.RESPONSIBILITY.has(key)) {
					role.setResponsibilities((ListParserElement) value);
				} else if (WorkitemParameter.OPPORTUNITIES.has(key)) {
					role.setOpportunities((ListParserElement) value);
				} else if (WorkitemParameter.GUIDELINES.has(key)) {
					role.setGuidelines((ListParserElement) value);
				} else if (WorkitemParameter.COMPETENCES.has(key)) {
					role.setCompetences((ListParserElement) value);
				} else if (WorkitemParameter.PERSONS.has(key)) {
					role.setPersonIdentifiers((ListParserElement) value);
				} else {
					logger.debug("Value from parser not set: key=" + key + ", value=" + pairs.get(key));
				}
			}

			role.setId(indexId, this);

			return role;
		}

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.rogatio.circlead.control.synchronizer.DefaultSynchronizer#delete(org. rogatio.circlead.model.work.IWorkitem)
	 */
	@Override
	public String delete(IWorkitem workitem) throws SynchronizerException {
		String idText = workitem.getId(this);
		int id = Integer.parseInt(idText);
		return "Response-Code " + this.delete(id);
	}

	/**
	 * Delete.
	 *
	 * @param pageId
	 *            the page id
	 * @return the synchronizer result
	 */
	public SynchronizerResult delete(int pageId) {
		//Set actual used synchronizer to singleton. Is needed for correct finding and setting of id
		SynchronizerFactory.getInstance().setActual(this);
		return confluenceClient.deletePage(pageId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.rogatio.circlead.control.synchronizer.DefaultSynchronizer#loadIndex(org. rogatio.circlead.model.WorkitemType)
	 */
	public List<String> loadIndex(WorkitemType workitemType) {
		String type = workitemType.getName().toLowerCase();
		ArrayList<String> fileIndex = new ArrayList<String>();

		try {
			logger.info("Loading Index '" + workitemType.getName() + "' from system '" + confluenceClient.getSysteminfo().getContent() + "'");
		} catch (Exception e) {
			logger.info("Loading Index '" + workitemType.getName() + "' from system '" + URL + "'");
		}
		
		// To Avoid loading of wrong labeld pages (and simplify writing) the loading of index is reduced to space of circlead. 
		// THis must be done here, because the url seems not be always correct. It not always includes space in url. It seems it only works correct on cloud-version. On dedicated server german umlaute have different urls
		SynchronizerResult results = confluenceClient.search("space = \""+circleadSpace+"\" AND label = \"" + type + "\"");

		if (results.getContent() == null) {
			logger.error("Error occured: Loading Index returns no content in result-set.");
		}

		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_NULL);
		try {
			Results queryResults = mapper.readValue(results.getContent(), Results.class);
			for (Result result : queryResults.getResults()) {
//				if (result.getUrl().contains("/" + circleadSpace + "/")) {
					if (type.equals("howto")) {
						HowTo ht = new HowTo();
						ht.setSynchronizer(this.toString());
						ht.setType(type);
						ht.setId(result.getContent().getId());
						ht.setTitle(new String(result.getContent().getTitle().trim().getBytes(), "UTF-8"));
						ht.setUrl(confluenceClient.getRestPrefix() + "content/" + type + "/" + result.getContent().getId());
						fileIndex.add(ht.toString());
						logger.debug("Found HowTo '" + ht.getTitle() + "' with '" + this.toString() + "'");
					} else if (type.equals("report")) {
						Report ht = new Report();
						ht.setSynchronizer(this.toString());
						ht.setType(type);
						ht.setId(result.getContent().getId());
						ht.setTitle(new String(result.getContent().getTitle().trim().getBytes(), "UTF-8"));
						ht.setUrl(confluenceClient.getRestPrefix() + "content/" + type + "/" + result.getContent().getId());
						fileIndex.add(ht.toString());
						logger.debug("Found Report '" + ht.getTitle() + "' with '" + this.toString() + "'");
					} else {
						fileIndex.add(URL + confluenceClient.getRestPrefix() + "content/" + type + "/" + result.getContent().getId());
					}
//				}
			}
		} catch (JsonParseException e) {
			logger.error("Error loading " + type + " from confluence", e);
		} catch (JsonMappingException e) {
			logger.error("Error loading " + type + " from confluence", e);
		} catch (IOException e) {
			logger.error("Error loading " + type + " from confluence", e);
		}

		return fileIndex;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.rogatio.circlead.control.synchronizer.DefaultSynchronizer#getRenderer()
	 */
	@Override
	public ISynchronizerRendererEngine getRenderer() {
		return new AtlassianRendererEngine(this);
	}

}
