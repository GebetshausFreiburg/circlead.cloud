package org.rogatio.circlead.main;

import org.rogatio.circlead.control.synchronizer.SynchronizerResult;
import org.rogatio.circlead.control.synchronizer.atlassian.ConfluenceClient;
import org.rogatio.circlead.control.synchronizer.atlassian.JiraClient;
import org.rogatio.circlead.model.WorkitemType;
import org.rogatio.circlead.util.ObjectUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class AtlassianPojoCreator creates POJO-Classes from found response to
 * atlassian-server. All sources are saved in folder "lib" in classpath.
 * 
 * @author Matthias Wegner
 */
public class AtlassianPojoCreator {

//	public static void main(String[] args) {
	// AtlassianPojoCreator.createAtlassianPojos("project in (ASSET, GEB)",
	// "264700209", "CIRCLEAD");
//	}

	public static void createAtlassianPojos(String jiraJQL, String roleID) {
		createAtlassianPojos(jiraJQL, roleID, null);
	}
	
	/**
	 * Creates the atlassian pojos.
	 *
	 * @param jiraJQL the jira JQL. Must find a representative set of issues. Query
	 *                must return more than one result
	 * @param roleID  the role ID. Id of a role in confluence. Page must have at
	 *                lease two historic page versions
	 * @param spaceID the space ID. Id of the space. If null 'CIRCLEAD' is set as
	 *                default
	 */
	public static void createAtlassianPojos(String jiraJQL, String roleID, String spaceID) {

		if (spaceID == null) {
			spaceID = "CIRCLEAD";
		}

		AtlassianPojoCreator.createJiraResults(jiraJQL);
		AtlassianPojoCreator.createPageContent(spaceID, roleID);
		AtlassianPojoCreator.createContentVersions(spaceID, roleID);
		AtlassianPojoCreator.createSearchResults(spaceID, WorkitemType.ROLE);
	}

	/**
	 * Creates the jira results.
	 *
	 * @param jql the jql
	 */
	public static void createJiraResults(String jql) {
		JiraClient c = new JiraClient();

		SynchronizerResult results = c.search(jql, null, 100, 1);

		String jsonSource = results.getContent();

		ObjectUtil.createPojoDirFromJson(jsonSource, "Results",
				"org.rogatio.circlead.control.synchronizer.atlassian.jira", "lib");
	}

	/**
	 * Creates the content versions.
	 *
	 * @param pageId the page id
	 */
	public static void createContentVersions(String pageId) {
		createContentVersions("CIRCLEAD", pageId);
	}

	/**
	 * Creates the content versions.
	 *
	 * @param circleadSpace the circlead space
	 * @param pageId        the page id
	 */
	public static void createContentVersions(String circleadSpace, String pageId) {
		ConfluenceClient confluenceClient = new ConfluenceClient();

		SynchronizerResult pageR = confluenceClient.getContentVersions(Integer.parseInt(pageId));
		String jsonSource = pageR.getContent();

		ObjectUtil.createPojoDirFromJson(jsonSource, "Results",
				"org.rogatio.circlead.control.synchronizer.atlassian.version", "lib");
	}

	/**
	 * Creates the page content.
	 *
	 * @param pageId the page id
	 */
	public static void createPageContent(String pageId) {
		createPageContent("CIRCLEAD", pageId);
	}

	/**
	 * Creates the page content.
	 *
	 * @param circleadSpace the circlead space
	 * @param pageId        the page id
	 */
	public static void createPageContent(String circleadSpace, String pageId) {
		ConfluenceClient confluenceClient = new ConfluenceClient();

		SynchronizerResult pageR = confluenceClient.getPage(Integer.parseInt(pageId));
		String jsonSource = pageR.getContent();

		ObjectUtil.createPojoDirFromJson(jsonSource, "Page",
				"org.rogatio.circlead.control.synchronizer.atlassian.content", "lib");
	}

	/**
	 * Creates the search results.
	 */
	public static void createSearchResults() {
		createSearchResults(WorkitemType.ROLE);
	}
	
	/**
	 * Creates the search results.
	 *
	 * @param type the type
	 */
	public static void createSearchResults(WorkitemType type) {
		createSearchResults("CIRCLEAD", type);
	}
	
	/**
	 * Creates the search results.
	 *
	 * @param circleadSpace the circlead space
	 * @param type          the type
	 */
	public static void createSearchResults(String circleadSpace, WorkitemType type) {
		ConfluenceClient confluenceClient = new ConfluenceClient();

		SynchronizerResult results = confluenceClient
				.search("space = \"" + circleadSpace + "\" AND label = \"" + type.getLowerName() + "\"");

		String jsonSource = results.getContent();

		ObjectUtil.createPojoDirFromJson(jsonSource, "Results",
				"org.rogatio.circlead.control.synchronizer.atlassian.search", "lib");
	}

}
