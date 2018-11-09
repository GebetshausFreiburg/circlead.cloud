/*
 * Circlead - Develop and structure evolutionary Organisations
 * 
 * @author Matthias Wegner
 * @version 0.1
 * @since 01.07.2018
 * 
 */
package org.rogatio.circlead.control.synchronizer.atlassian;

import static org.rogatio.circlead.model.WorkitemType.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
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
import org.rogatio.circlead.control.synchronizer.atlassian.parser.ActivityTableParserElement;
import org.rogatio.circlead.control.synchronizer.atlassian.parser.IParserElement;
import org.rogatio.circlead.control.synchronizer.atlassian.parser.ImageParserElement;
import org.rogatio.circlead.control.synchronizer.atlassian.parser.ListParserElement;
import org.rogatio.circlead.control.synchronizer.atlassian.parser.PairTableParserElement;
import org.rogatio.circlead.control.synchronizer.atlassian.parser.Parser;
import org.rogatio.circlead.control.synchronizer.atlassian.parser.StatusParserElement;
import org.rogatio.circlead.control.synchronizer.atlassian.parser.HeaderTableParserElement;
import org.rogatio.circlead.control.synchronizer.atlassian.parser.TextParserElement;
import org.rogatio.circlead.control.synchronizer.atlassian.search.Result;
import org.rogatio.circlead.control.synchronizer.atlassian.search.Results;
import org.rogatio.circlead.model.WorkitemParameter;
import org.rogatio.circlead.model.WorkitemType;
import org.rogatio.circlead.model.data.HowTo;
import org.rogatio.circlead.model.data.Report;
import org.rogatio.circlead.model.work.Activity;
import org.rogatio.circlead.model.work.Competence;
import org.rogatio.circlead.model.work.IWorkitem;
import org.rogatio.circlead.model.work.Person;
import org.rogatio.circlead.model.work.Role;
import org.rogatio.circlead.model.work.Rolegroup;
import org.rogatio.circlead.model.work.Team;
import org.rogatio.circlead.util.PropertyUtil;
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
 * The Class AtlassianSynchronizer synchronizes Circlead with Atlassian
 * Confluence Server. Could be used for CLoud- and Dedicated-Server.
 * 
 * @author Matthias Wegner
 */
public class AtlassianSynchronizer extends DefaultSynchronizer {

	/** The urlconfluence. */
	private final String URLCONFLUENCE = PropertyUtil.getInstance().getValue(PropertyUtil.ATLASSIAN_CONFLUENCE_URL);
	
	/** The dedicatedserver. */
	private final boolean DEDICATEDSERVER = PropertyUtil.getInstance()
			.getBooleanValue(PropertyUtil.ATLASSIAN_SERVER_DEDICATED);

	/** Name of the page in space which holds the roles. */
	private final String ROLESPAGE = "Roles";

	/** Name of the page in space which holds the activities. */
	private final String ACTIVITIESPAGE = "Activities";

	/** Name of the page in space which holds the rolegroups. */
	private final String ROLEGROUPSPAGE = "Rolegroups";

	/** Name of the page in space which holds the persons. */
	private final String PERSONSPAGE = "Persons";

	/** Name of the page in space which holds the teams. */
	private final String TEAMSPAGE = "Teams";

	/** Name of the page in space which holds the competencies. */
	private final String COMPETENCIESPAGE = "Competencies";

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LogManager.getLogger(AtlassianSynchronizer.class);

	/** The name of the circlead space. */
	private String circleadSpace = null;

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
	 * @param spaceKey the key of the circlead space
	 */
	public AtlassianSynchronizer(String spaceKey) {
		circleadSpace = spaceKey;
		confluenceClient = new ConfluenceClient();
	}

	/** The confluence client. */
	private ConfluenceClient confluenceClient;

	/**
	 * Gets the single acestor list. Is the parent page in confluence. Page in
	 * confluence must be named similar to plural name of holded Workitem-Class
	 *
	 * @param wi the wi
	 * @return the single acestor list
	 */
	private List<Ancestor> getSingleAcestorList(IWorkitem wi) {
		/* Check if parent-workitem could be found and load id via rest */
		String id = this.getAcestorId(wi.getType());
		if (id != null) {
			/*
			 * if not found, then create one valid json-parent, called acestor-page in
			 * confluence
			 */
			if (!id.equals("0")) {
				List<Ancestor> list = new ArrayList<Ancestor>();
				Ancestor a = new Ancestor();
				a.setId(id);

				if (wi instanceof Activity) {
					a.setTitle(ACTIVITIESPAGE);
				}
				if (wi instanceof Role) {
					a.setTitle(ROLESPAGE);
				}
				if (wi instanceof Rolegroup) {
					a.setTitle(ROLEGROUPSPAGE);
				}
				if (wi instanceof Person) {
					a.setTitle(PERSONSPAGE);
				}
				if (wi instanceof Team) {
					a.setTitle(TEAMSPAGE);
				}

				if (wi instanceof Competence) {
					a.setTitle(COMPETENCIESPAGE);
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
	 * @see
	 * org.rogatio.circlead.control.synchronizer.DefaultSynchronizer#add(org.rogatio
	 * .circlead.view.IReport)
	 */
	@Override
	public SynchronizerResult add(IReport report) throws SynchronizerException {
		/*
		 * Set actual used synchronizer to singleton. Is needed for correct finding and
		 * setting of id
		 */
		SynchronizerFactory.getInstance().setActual(this);

		if (PropertyUtil.getInstance().isAtlassianSynchronizerEnabled()
				&& PropertyUtil.getInstance().isAtlassianSynchronizerWriteMode()) {

			/* Create confluence-page-object from report */
			Page page = Parser.createPage(report, circleadSpace, this);

			try {
				/* Create label for confluence-page */
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

				// Post json to rest. Save response
				SynchronizerResult res = confluenceClient.post(confluenceClient.getRestPrefix() + "content/", data);

				// return result
				return res;
			} catch (JsonProcessingException e) {
				LOGGER.error(e);
			} catch (IOException e) {
				LOGGER.error(e);
			}
		}

		// return null if something went wrong
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.rogatio.circlead.control.synchronizer.DefaultSynchronizer#add(org.rogatio
	 * .circlead.model.work.IWorkitem)
	 */
	@Override
	public SynchronizerResult add(IWorkitem workitem) throws SynchronizerException {
		// Set actual used synchronizer to singleton. Is needed for correct finding and
		// setting of id
		SynchronizerFactory.getInstance().setActual(this);

		if (PropertyUtil.getInstance().isAtlassianSynchronizerEnabled()
				&& PropertyUtil.getInstance().isAtlassianSynchronizerWriteMode()) {

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
				LOGGER.error(e);
			} catch (IOException e) {
				LOGGER.error(e);
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.rogatio.circlead.control.synchronizer.DefaultSynchronizer#update(org.
	 * rogatio.circlead.model.work.IWorkitem)
	 */
	@Override
	public SynchronizerResult update(IWorkitem workitem) {
		// Set actual used synchronizer to singleton. Is needed for correct finding and
		// setting of id
		SynchronizerFactory.getInstance().setActual(this);

		if (PropertyUtil.getInstance().isAtlassianSynchronizerEnabled()
				&& PropertyUtil.getInstance().isAtlassianSynchronizerWriteMode()) {

			// Create Confluence-POJO-Object from workitem
			Page page = Parser.createPage(workitem, circleadSpace, this);

			LOGGER.info("Update '" + URLCONFLUENCE + confluenceClient.getRestPrefix() + "content/"
					+ workitem.getId(this) + "'");

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

				/* find id of workitem for this synchronizer */
				String id = workitem.getId(this);

				String uri = confluenceClient.getRestPrefix() + "content/" + id;
				/* Catch result from rest-interface writing confluence-page */
				SynchronizerResult res = confluenceClient.put(uri, data);

				LOGGER.debug(workitem.getTitle() + ": " + res.toString());

				return res;
			} catch (JsonProcessingException e) {
				LOGGER.error(e);
			} catch (IOException e) {
				LOGGER.error(e);
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.rogatio.circlead.control.synchronizer.DefaultSynchronizer#update(org.
	 * rogatio.circlead.view.IReport)
	 */
	@Override
	public SynchronizerResult update(IReport report) {
		/*
		 * Set actual used synchronizer to singleton. Is needed for correct finding and
		 * setting of id
		 */
		SynchronizerFactory.getInstance().setActual(this);

		if (PropertyUtil.getInstance().isAtlassianSynchronizerEnabled()
				&& PropertyUtil.getInstance().isAtlassianSynchronizerWriteMode()) {

			/* Create Confluence-POJO-Object from report */
			Page page = Parser.createPage(report, circleadSpace, this);

			/* Only update report if it exists in target system */
			Report repo = Repository.getInstance().getReport(report.getName());
			if (repo != null) {

				LOGGER.info("Update '" + URLCONFLUENCE + confluenceClient.getRestPrefix() + "content/" + repo.getId()
						+ "' (" + report.getName() + ")");

				/* Load version of existing report and increment +1 for update */
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

				/* Build page for report content */
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
					LOGGER.error(e);
				} catch (IOException e) {
					LOGGER.error(e);
				}
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.rogatio.circlead.control.synchronizer.DefaultSynchronizer#getIdPattern()
	 */
	@Override
	public String getIdPattern() {
		return "^[0-9]{9}$";
	}

	/**
	 * Gets the acestor id of page.
	 *
	 * @param title the title of the acestor page
	 * @param page  the page object
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
	 * Delete old page versions of an workitem.
	 *
	 * @param type the type of the workitem
	 */
	public void deleteVersions(WorkitemType type) {
		try {
			List<IWorkitem> items = load(type);
			for (IWorkitem iWorkitem : items) {
				deleteVersions(Integer.parseInt(iWorkitem.getId(this)));
			}
		} catch (SynchronizerException e) {
			LOGGER.error(e);
		}
	}

	/**
	 * Load all workitems of a given type.
	 *
	 * @param type the type of the workitem
	 * @return the list of workitems
	 * @throws SynchronizerException the synchronizer exception
	 */
	private List<IWorkitem> load(WorkitemType type) throws SynchronizerException {
		List<String> list = loadIndex(type);

		List<IWorkitem> workitems = new ArrayList<IWorkitem>();

		for (String index : list) {
			IWorkitem wi = null;
			wi = get(index);
			if (wi != null) {
				if (!workitems.contains(wi)) {
					workitems.add(wi);
				}
			}
		}

		return workitems;
	}

	/**
	 * The delete version counter. Is needed because deletion of old version pages
	 * needs recursive method
	 */
	private int deleteVersionCounter = 0;

	/**
	 * The delete version counter max. Is needed because deletion of old version
	 * pages needs recursive method
	 */
	private int deleteVersionCounterMax = 0;

	/**
	 * Delete all versions of a page by given id.
	 *
	 * @param pageId the page id
	 * @return true, if successful
	 */
	public boolean deleteVersions(Integer pageId) {

		if (PropertyUtil.getInstance().isAtlassianSynchronizerEnabled()) {

			/* Deletion is not possible via rest on dedicated server. Throw warning */
			if (DEDICATEDSERVER) {
				LOGGER.warn("REST-API for deleting page-versions on dedicated server NOT available");
				return false;
			}

			/* Load all versions to delete */
			List<Integer> x = getVersions(pageId);
			x = x.subList(0, x.size() - 1);
			if (x.size() > deleteVersionCounter) {
				deleteVersionCounter = x.size();
				deleteVersionCounterMax = x.size();
			}

			/* Start deletion */
			for (Integer i : x) {
				SynchronizerResult r = deleteVersion(pageId, i);
				/* If deletion is skipped, the start new */
				if (r.getCode() == 400) {
					LOGGER.debug("Restart recursive deleting versions of page '" + pageId + "' because of code 400");
					deleteVersions(pageId);
					break;
				}
				LOGGER.debug("Delete version='" + i + "' (" + (deleteVersionCounter--) + "/" + deleteVersionCounterMax
						+ ") of page '" + pageId + "'. Code " + r.getCode());
			}
		}
		return true;
	}

	/**
	 * Delete version of a page.
	 *
	 * @param pageId  the page id
	 * @param version the version
	 * @return the synchronizer result
	 */
	public SynchronizerResult deleteVersion(Integer pageId, Integer version) {
		/* Deletion is not possible via rest on dedicated server. Throw warning */
		if (DEDICATEDSERVER) {
			LOGGER.warn("REST-API for deleting versions on dedicated server NOT available");
			return null;
		}

		if (PropertyUtil.getInstance().isAtlassianSynchronizerEnabled()) {
			return confluenceClient.deleteVersion(pageId, version);
		}
		return null;
	}

	/**
	 * Gets the versions of a page with given id.
	 *
	 * @param pageId the page id
	 * @return the versions
	 */
	public List<Integer> getVersions(Integer pageId) {
		/* Deletion is not possible via rest on dedicated server. Throw warning */
		if (DEDICATEDSERVER) {
			LOGGER.warn("REST-API for deleting versions on dedicated server NOT available");
			return null;
		}

		SynchronizerResult results = confluenceClient.getContentVersions(pageId);

		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_NULL);

		List<Integer> numbers = new ArrayList<Integer>();

		try {
			org.rogatio.circlead.control.synchronizer.atlassian.version.Results resultsObject = mapper.readValue(
					results.getContent(), org.rogatio.circlead.control.synchronizer.atlassian.version.Results.class);
			if (resultsObject != null) {
				List<org.rogatio.circlead.control.synchronizer.atlassian.version.Result> res = resultsObject
						.getResults();
				for (org.rogatio.circlead.control.synchronizer.atlassian.version.Result result : res) {
					if (result != null) {
						numbers.add(result.getNumber());
					}
				}
			}
		} catch (IOException e) {
			LOGGER.error(e);
		}

		Collections.sort(numbers);

		return numbers;
	}

	/**
	 * Loads the acestor page-id for the page as workitem-representation.
	 *
	 * @param type the type of a workitem as string
	 * @return the acestor id
	 */
	public String getAcestorId(String type) {

		/* Look for id which is loaded on repository initialisation */
		String id = acestorPages.get(type.toLowerCase());

		/* If acestor not found (lazy load), then look for it online */
		if (id == null) {
			if (REPORT.isEquals(type)) {
				SynchronizerResult page = confluenceClient.search("type=\"page\" and title=\"Reports\"");
				id = "" + Parser.getIdFromResult(page.getContent());
			}
			if (ROLE.isEquals(type)) {
				SynchronizerResult page = confluenceClient.search("type=\"page\" and title=\"Roles\"");
				id = "" + Parser.getIdFromResult(page.getContent());
			}
			if (ACTIVITY.isEquals(type)) {
				SynchronizerResult page = confluenceClient.search("type=\"page\" and title=\"Activities\"");
				id = "" + Parser.getIdFromResult(page.getContent());
			}
			if (ROLEGROUP.isEquals(type)) {
				SynchronizerResult page = confluenceClient.search("type=\"page\" and title=\"Rolegroups\"");
				id = "" + Parser.getIdFromResult(page.getContent());
			}
			if (PERSON.isEquals(type)) {
				SynchronizerResult page = confluenceClient.search("type=\"page\" and title=\"Persons\"");
				id = "" + Parser.getIdFromResult(page.getContent());
			}
			if (TEAM.isEquals(type)) {
				SynchronizerResult page = confluenceClient.search("type=\"page\" and title=\"Teams\"");
				id = "" + Parser.getIdFromResult(page.getContent());
			}
			if (COMPETENCE.isEquals(type)) {
				SynchronizerResult page = confluenceClient.search("type=\"page\" and title=\"Competencies\"");
				id = "" + Parser.getIdFromResult(page.getContent());
			}
		}

		return id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.rogatio.circlead.control.synchronizer.DefaultSynchronizer#get(java.lang.
	 * String)
	 */
	@Override
	public IWorkitem get(String indexId) throws SynchronizerException {
		// Set actual used synchronizer to singleton. Is needed for correct finding and
		// setting of id
		SynchronizerFactory.getInstance().setActual(this);

		IWorkitem wi = null;

		if (PropertyUtil.getInstance().isAtlassianSynchronizerEnabled()
				&& PropertyUtil.getInstance().isAtlassianSynchronizerReadMode()) {

			// If index is not correct, then throw exception
			if (indexId.endsWith(".json")) {
				throw new SynchronizerException(
						"Item with id '" + indexId + "' could not be loaded with Atlassian Synchronizer.");
			}

			// ignore all prefix, because id in atlassian-confluence is a numeric int. So
			// parse for correct id on right side
			int idx = indexId.lastIndexOf("/");
			if (idx > 0) {
				indexId = indexId.substring(idx + 1, indexId.length());
			}

			Map<String, IParserElement> pairs = new HashMap<String, IParserElement>();

			try {
				ObjectMapper mapper = new ObjectMapper();
				mapper.setSerializationInclusion(Include.NON_NULL);
				SynchronizerResult page = confluenceClient.getPage(Integer.parseInt(indexId));

				if (page == null) {
					throw new SynchronizerException(
							"Item with id '" + indexId + "' could not be founded/loaded with Atlassian Synchronizer.");
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

				for (org.rogatio.circlead.control.synchronizer.atlassian.content.Result result : p.getMetadata()
						.getLabels().getResults()) {

					String label = null;
					if (result.getName() != null) {
						label = result.getName();
					}
					if (result.getLabel() != null) {
						label = result.getLabel();
					}

					if (ROLE.isEquals(label)) {
						type = ROLE.getLowerName();
						acestorId = getAcestorId(ROLESPAGE, p);
					}
					if (ACTIVITY.isEquals(label)) {
						type = ACTIVITY.getLowerName();
						acestorId = getAcestorId(ACTIVITIESPAGE, p);
					}
					if (ROLEGROUP.isEquals(label)) {
						type = ROLEGROUP.getLowerName();
						acestorId = getAcestorId(ROLEGROUPSPAGE, p);
					}
					if (PERSON.isEquals(label)) {
						type = PERSON.getLowerName();
						acestorId = getAcestorId(PERSONSPAGE, p);
					}
					if (TEAM.isEquals(label)) {
						type = TEAM.getLowerName();
						acestorId = getAcestorId(TEAMSPAGE, p);
					}
					if (COMPETENCE.isEquals(label)) {
						type = COMPETENCE.getLowerName();
						acestorId = getAcestorId(COMPETENCIESPAGE, p);
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
								} else if (WorkitemParameter.COMPETENCETREE.has(key)) {
									parserElement = new HeaderTableParserElement(unparsedValue);
								} else if (WorkitemParameter.TEAMROLES.has(key)) {
									parserElement = new HeaderTableParserElement(unparsedValue);
								} else if (WorkitemParameter.SUBACTIVITY.has(key)) {
									parserElement = new ActivityTableParserElement(unparsedValue);
								} else if (WorkitemParameter.IMAGE.has(key)) {
									parserElement = new ImageParserElement(unparsedValue);
								} else if (WorkitemParameter.CONTACTS.has(key)) {
									parserElement = new PairTableParserElement(unparsedValue);
								} else if (WorkitemParameter.OPPORTUNITIES.has(key)) {
									parserElement = new ListParserElement(unparsedValue);
								} else if (WorkitemParameter.GUIDELINES.has(key)) {
									parserElement = new ListParserElement(unparsedValue);
								} else if (WorkitemParameter.COMPETENCIES.has(key)) {
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

				LOGGER.debug("Load: code=" + page.getCode() + ", message=" + page.getMessage() + ", source="
						+ page.getSource() + ", title=" + p.getTitle());

			} catch (JsonParseException e) {
				throw new SynchronizerException("Error (JsonParse) loading item '" + indexId + "'. " + e.getMessage(),
						e.getCause());
			} catch (JsonMappingException e) {
				throw new SynchronizerException("Error (JsonMapping) loading item '" + indexId + "'. " + e.getMessage(),
						e.getCause());
			} catch (IOException e) {
				throw new SynchronizerException("Error (IOException) loading item '" + indexId + "'. " + e.getMessage(),
						e.getCause());
			}
		}

		return wi;
	}

	/**
	 * Sets the data.
	 *
	 * @param pairs   the pairs
	 * @param type    the type
	 * @param indexId the index id
	 * @return the i workitem
	 */
	private IWorkitem setData(Map<String, IParserElement> pairs, String type, String indexId) {
		Vector<String> keys = new Vector<String>(pairs.keySet());

		if (COMPETENCE.isEquals(type)) {
			Competence competence = new Competence();

			for (String key : keys) {
				IParserElement value = pairs.get(key);
				if (WorkitemParameter.ID.has(key)) {
					competence.getDataitem().setUid(value.toString());
				} else if (WorkitemParameter.CREATED.has(key)) {
					competence.setCreated(value.toString());
				} else if (WorkitemParameter.MODIFIED.has(key)) {
					competence.setModified(value.toString());
				} else if (WorkitemParameter.COMPETENCETREE.has(key)) {
					competence.setCompetenceTable((HeaderTableParserElement) value);
				} else if (WorkitemParameter.DESCRIPTION.has(key)) {
					competence.setDescription(value.toString());
				} else if (WorkitemParameter.VERSION.has(key)) {
					competence.setVersion(value.toString());
				} else if (WorkitemParameter.STATUS.has(key)) {
					competence.setStatus(value.toString());
				} else {
					LOGGER.debug("Value from parser not set: key=" + key + ", value=" + pairs.get(key));
				}
			}

			competence.setId(indexId, this);

			return competence;
		}

		if (TEAM.isEquals(type)) {
			Team team = new Team();

			for (String key : keys) {
				IParserElement value = pairs.get(key);
				if (WorkitemParameter.ID.has(key)) {
					team.getDataitem().setUid(value.toString());
				} else if (WorkitemParameter.CREATED.has(key)) {
					team.setCreated(value.toString());
				} else if (WorkitemParameter.MODIFIED.has(key)) {
					team.setModified(value.toString());
				} else if (WorkitemParameter.DESCRIPTION.has(key)) {
					team.setDescription(value.toString());
				} else if (WorkitemParameter.RECURRENCERULE.has(key)) {
					team.setRecurrenceRule(value.toString());
				} else if (WorkitemParameter.STARTDATE.has(key)) {
					team.setStart(value.toString());
				} else if (WorkitemParameter.ENDDATE.has(key)) {
					team.setEnd(value.toString());
				} else if (WorkitemParameter.CATEGORY.has(key)) {
					team.setCategory(value.toString());
				} else if (WorkitemParameter.TYPE.has(key)) {
					team.setTeamType(value.toString());
				} else if (WorkitemParameter.SUBTYPE.has(key)) {
					team.setTeamSubtype(value.toString());
				} else if (WorkitemParameter.TEAMROLES.has(key)) {
					team.setTeamTable((HeaderTableParserElement) value);
				} else if (WorkitemParameter.VERSION.has(key)) {
					team.setVersion(value.toString());
				} else if (WorkitemParameter.STATUS.has(key)) {
					team.setStatus(value.toString());
				} else {
					LOGGER.debug("Value from parser not set: key=" + key + ", value=" + pairs.get(key));
				}
			}

			team.setId(indexId, this);

			return team;
		}

		if (ACTIVITY.isEquals(type)) {
			Activity activity = new Activity();

			for (String key : keys) {
				IParserElement value = pairs.get(key);
				if (WorkitemParameter.ID.has(key)) {
					activity.getDataitem().setUid(value.toString());
				} else if (WorkitemParameter.CREATED.has(key)) {
					activity.setCreated(value.toString());
				} else if (WorkitemParameter.MODIFIED.has(key)) {
					activity.setModified(value.toString());
				} else if (WorkitemParameter.HOWTOS.has(key)) {
					activity.setHowTos(value.toString());
				} else if (WorkitemParameter.SUBACTIVITY.has(key)) {
					activity.setSubactivities((ActivityTableParserElement) value);
				} else if (WorkitemParameter.ACTIVITYID.has(key)) {
					activity.setAid(value.toString());
				} else if (WorkitemParameter.DESCRIPTION.has(key)) {
					activity.setDescription(value.toString());
				} else if (WorkitemParameter.RESULT.has(key)) {
					activity.setResults(value.toString());
				} else if (WorkitemParameter.VERSION.has(key)) {
					activity.setVersion(value.toString());
				} else if (WorkitemParameter.RESPONSIBLE.has(key)) {
					activity.setResponsibleIdentifier(value.toString());
				} else if (WorkitemParameter.INFORMED.has(key)) {
					activity.setInformedIdentifier(value.toString());
				} else if (WorkitemParameter.SUPPORTER.has(key)) {
					activity.setSupplierIdentifier(value.toString());
				} else if (WorkitemParameter.CONSULTANT.has(key)) {
					activity.setConsultantIdentifier(value.toString());
				} else if (WorkitemParameter.STATUS.has(key)) {
					activity.setStatus(value.toString());
				} else {
					LOGGER.debug("Value from parser not set: key=" + key + ", value=" + pairs.get(key));
				}
			}

			activity.setId(indexId, this);

			return activity;
		}

		if (PERSON.isEquals(type)) {
			Person person = new Person();

			for (String key : keys) {
				IParserElement value = pairs.get(key);
				if (WorkitemParameter.ID.has(key)) {
					person.getDataitem().setUid(value.toString());
				} else if (WorkitemParameter.CREATED.has(key)) {
					person.setCreated(value.toString());
				} else if (WorkitemParameter.MODIFIED.has(key)) {
					person.setModified(value.toString());
				} else if (WorkitemParameter.TEAMFRACTION.has(key)) {
					person.setTeamFraction(value.toString());
				} else if (WorkitemParameter.FTE.has(key)) {
					person.setFullTimeEquivalent(value.toString());
				} else if (WorkitemParameter.IMAGE.has(key)) {
					person.setAvatar(((ImageParserElement) value).toString());
				} else if (WorkitemParameter.IGNORE.has(key)) {
					// Ignore, because its inner table
					// Do explicit nothing
				} else if (WorkitemParameter.VERSION.has(key)) {
					person.setVersion(value.toString());
				} else if (WorkitemParameter.NAME.has(key)) {
					person.setFullname(value.toString());
				} else if (WorkitemParameter.CONTACTS.has(key)) {
					person.setContacts((PairTableParserElement) value);
				} else if (WorkitemParameter.DATA.has(key)) {
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
								LOGGER.debug("Value from parser not set: key=" + key + ", value=" + pairs.get(key));
							}
						}
					}

				}
			}

			person.setId(indexId, this);

			return person;
		}

		if (ROLEGROUP.isEquals(type)) {
			Rolegroup rolegroup = new Rolegroup();

			for (String key : keys) {
				IParserElement value = pairs.get(key);
				if (WorkitemParameter.ID.has(key)) {
					rolegroup.getDataitem().setUid(value.toString());
				} else if (WorkitemParameter.CREATED.has(key)) {
					rolegroup.setCreated(value.toString());
				} else if (WorkitemParameter.MODIFIED.has(key)) {
					rolegroup.setModified(value.toString());
				} else if (WorkitemParameter.VERSION.has(key)) {
					rolegroup.setVersion(value.toString());
				} else if (WorkitemParameter.LEAD.has(key)) {
					rolegroup.setLeadIdentifier(value.toString());
				} else if (WorkitemParameter.ABBREVIATION.has(key)) {
					rolegroup.setAbbreviation(value.toString());
				} else if (WorkitemParameter.SYNONYM.has(key)) {
					rolegroup.setSynonyms(value.toString());
				} else if (WorkitemParameter.PARENT.has(key)) {
					rolegroup.setParentIdentifier(value.toString());
				} else if (WorkitemParameter.RESPONSIBLE.has(key)) {
					rolegroup.setResponsibleIdentifier(value.toString());
				} else if (WorkitemParameter.SUMMARY.has(key)) {
					rolegroup.setSummary(value.toString());
				} else if (WorkitemParameter.STATUS.has(key)) {
					rolegroup.setStatus(value.toString());
				} else {
					LOGGER.debug("Value from parser not set: key=" + key + ", value=" + pairs.get(key));
				}
			}

			rolegroup.setId(indexId, this);

			return rolegroup;
		}

		if (ROLE.isEquals(type)) {
			Role role = new Role();

			for (String key : keys) {
				IParserElement value = pairs.get(key);
				if (WorkitemParameter.ID.has(key)) {
					role.getDataitem().setUid(value.toString());
				} else if (WorkitemParameter.CREATED.has(key)) {
					role.setCreated(value.toString());
				} else if (WorkitemParameter.MODIFIED.has(key)) {
					role.setModified(value.toString());
				} else if (WorkitemParameter.VERSION.has(key)) {
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
				} else if (WorkitemParameter.COMPETENCIES.has(key)) {
					role.setCompetences((ListParserElement) value);
				} else if (WorkitemParameter.PERSONS.has(key)) {
					ListParserElement plist = (ListParserElement) value;
					if (plist.isSituational()) {
						role.setSituational(true);
					} else {
						role.setPersonIdentifiers(plist);
					}
				} else {
					LOGGER.debug("Value from parser not set: key=" + key + ", value=" + pairs.get(key));
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
	 * @see
	 * org.rogatio.circlead.control.synchronizer.DefaultSynchronizer#delete(org.
	 * rogatio.circlead.model.work.IWorkitem)
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
	 * @param pageId the page id
	 * @return the synchronizer result
	 */
	public SynchronizerResult delete(int pageId) {
		// Set actual used synchronizer to singleton. Is needed for correct finding and
		// setting of id
		if (PropertyUtil.getInstance().isAtlassianSynchronizerEnabled()) {
			SynchronizerFactory.getInstance().setActual(this);
			return confluenceClient.deletePage(pageId);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.rogatio.circlead.control.synchronizer.DefaultSynchronizer#loadIndex(org.
	 * rogatio.circlead.model.WorkitemType)
	 */
	public List<String> loadIndex(WorkitemType workitemType) {
		String type = workitemType.getName().toLowerCase();
		ArrayList<String> fileIndex = new ArrayList<String>();

		if (PropertyUtil.getInstance().isAtlassianSynchronizerEnabled()
				&& PropertyUtil.getInstance().isAtlassianSynchronizerReadMode()) {

			try {
				LOGGER.info("Loading Index '" + workitemType.getName() + "' from system '"
						+ confluenceClient.getSysteminfo().getContent() + "'");
			} catch (Exception e) {
				LOGGER.info("Loading Index '" + workitemType.getName() + "' from system '" + URLCONFLUENCE + "'");
			}

			// To Avoid loading of wrong labeld pages (and simplify writing) the loading of
			// index is reduced to space of circlead.
			// THis must be done here, because the url seems not be always correct. It not
			// always includes space in url. It seems it only works correct on
			// cloud-version. On dedicated server german umlaute have different urls
			SynchronizerResult results = confluenceClient
					.search("space = \"" + circleadSpace + "\" AND label = \"" + type + "\"");

			if (results.getContent() == null) {
				LOGGER.error("Error occured: Loading Index returns no content in result-set.");
			}

			ObjectMapper mapper = new ObjectMapper();
			mapper.setSerializationInclusion(Include.NON_NULL);
			try {
				Results queryResults = mapper.readValue(results.getContent(), Results.class);
				for (Result result : queryResults.getResults()) {
					if (HOWTO.isEquals(type)) {
						HowTo ht = new HowTo();
						ht.setSynchronizer(this.toString());
						ht.setType(type);
						ht.setId(result.getContent().getId());
						ht.setTitle(new String(result.getContent().getTitle().trim().getBytes(), "UTF-8"));
						ht.setUrl(confluenceClient.getRestPrefix() + "content/" + type + "/"
								+ result.getContent().getId());
						fileIndex.add(ht.toString());
						LOGGER.debug("Found HowTo '" + ht.getTitle() + "' with '" + this.toString() + "'");
					} else if (REPORT.isEquals(type)) {
						Report ht = new Report();
						ht.setSynchronizer(this.toString());
						ht.setType(type);
						ht.setId(result.getContent().getId());
						ht.setTitle(new String(result.getContent().getTitle().trim().getBytes(), "UTF-8"));
						ht.setUrl(confluenceClient.getRestPrefix() + "content/" + type + "/"
								+ result.getContent().getId());
						fileIndex.add(ht.toString());
						LOGGER.debug("Found Report '" + ht.getTitle() + "' with '" + this.toString() + "'");
					} else {
						fileIndex.add(URLCONFLUENCE + confluenceClient.getRestPrefix() + "content/" + type + "/"
								+ result.getContent().getId());
					}
				}
			} catch (JsonParseException e) {
				LOGGER.error("Error loading " + type + " from confluence", e);
			} catch (JsonMappingException e) {
				LOGGER.error("Error loading " + type + " from confluence", e);
			} catch (IOException e) {
				LOGGER.error("Error loading " + type + " from confluence", e);
			}
		}

		return fileIndex;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.rogatio.circlead.control.synchronizer.DefaultSynchronizer#getRenderer()
	 */
	@Override
	public ISynchronizerRendererEngine getRenderer() {
		return new AtlassianRendererEngine(this);
	}

}
