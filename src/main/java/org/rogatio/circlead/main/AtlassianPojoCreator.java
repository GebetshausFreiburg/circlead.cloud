package org.rogatio.circlead.main;

import org.rogatio.circlead.control.synchronizer.SynchronizerResult;
import org.rogatio.circlead.control.synchronizer.atlassian.ConfluenceClient;
import org.rogatio.circlead.control.synchronizer.atlassian.JiraClient;
import org.rogatio.circlead.model.WorkitemType;
import org.rogatio.circlead.util.ObjectUtil;
import org.rogatio.circlead.util.PropertyUtil;

/**
 * The Class AtlassianPojoCreator creates POJO-Classes from found response to atlassian-server. All sources are saved in folder "lib" in classpath.
 */
public class AtlassianPojoCreator {

	public static void main(String[] args) {
		AtlassianPojoCreator.createAtlassionPojos("project in (ASSET, GEB)", "264700209", "CIRCLEAD");
	}
	
	/**
	 * Creates the atlassion pojos.
	 *
	 * @param jiraJQL the jira JQL. Must find a representative set of issues. Query must return more than one result
	 * @param roleID the role ID. Id of a role in confluence. Page must have at lease two historic page versions
	 * @param spaceID the space ID. Id of the space. If null 'CIRCLEAD' is set as default
	 */
	public static void createAtlassionPojos(String jiraJQL, String roleID, String spaceID) {
		
		if (spaceID==null) {
			spaceID = "CIRCLEAD";
		}
		
		AtlassianPojoCreator.createJiraResults(jiraJQL);
		AtlassianPojoCreator.createPageContent(spaceID, roleID);
		AtlassianPojoCreator.createContentVersions(spaceID, roleID);
		AtlassianPojoCreator.createSearchResults(spaceID, WorkitemType.ROLE);
	}

	/** The Constant URLCONFLUENCE. */
	private static final String URLCONFLUENCE = PropertyUtil.getInstance()
			.getValue(PropertyUtil.ATLASSIAN_CONFLUENCE_URL);
	
	/** The Constant URLJIRA. */
	private static final String URLJIRA = PropertyUtil.getInstance().getValue(PropertyUtil.ATLASSIAN_JIRA_URL);
	
	/** The Constant USER. */
	private static final String USER = PropertyUtil.getInstance().getValue(PropertyUtil.ATLASSIAN_LOGIN_USER);
	
	/** The Constant PASSWORD. */
	private static final String PASSWORD = PropertyUtil.getInstance().getValue(PropertyUtil.ATLASSIAN_LOGIN_PASSWORD);
	
	/** The Constant DEDICATEDSERVER. */
	private static final boolean DEDICATEDSERVER = PropertyUtil.getInstance()
			.getBooleanValue(PropertyUtil.ATLASSIAN_SERVER_DEDICATED);
	
	/**
	 * Creates the jira results.
	 *
	 * @param jql the jql
	 */
	public static void createJiraResults(String jql) {
		JiraClient c = new JiraClient(URLJIRA, USER, PASSWORD, DEDICATEDSERVER);

		SynchronizerResult results = c.search(jql, null, 100, 1);

		String jsonSource = results.getContent();

		ObjectUtil.createPojoDirFromJson(jsonSource, "Results",
				"org.rogatio.circlead.control.synchronizer.atlassian.jira", "lib");
	}

	/**
	 * Creates the content versions.
	 *
	 * @param circleadSpace the circlead space
	 * @param pageId the page id
	 */
	public static void createContentVersions(String circleadSpace, String pageId) {
		ConfluenceClient confluenceClient = new ConfluenceClient(URLCONFLUENCE, USER, PASSWORD, DEDICATEDSERVER);

		SynchronizerResult pageR = confluenceClient.getContentVersions(Integer.parseInt(pageId));
		String jsonSource = pageR.getContent();

		ObjectUtil.createPojoDirFromJson(jsonSource, "Results",
				"org.rogatio.circlead.control.synchronizer.atlassian.version", "lib");
	}

	/**
	 * Creates the page content.
	 *
	 * @param circleadSpace the circlead space
	 * @param pageId the page id
	 */
	public static void createPageContent(String circleadSpace, String pageId) {
		ConfluenceClient confluenceClient = new ConfluenceClient(URLCONFLUENCE, USER, PASSWORD, DEDICATEDSERVER);

		SynchronizerResult pageR = confluenceClient.getPage(Integer.parseInt(pageId));
		String jsonSource = pageR.getContent();

		ObjectUtil.createPojoDirFromJson(jsonSource, "Page",
				"org.rogatio.circlead.control.synchronizer.atlassian.content", "lib");
	}

	/**
	 * Creates the search results.
	 *
	 * @param circleadSpace the circlead space
	 * @param type the type
	 */
	public static void createSearchResults(String circleadSpace, WorkitemType type) {
		ConfluenceClient confluenceClient = new ConfluenceClient(URLCONFLUENCE, USER, PASSWORD, DEDICATEDSERVER);

		SynchronizerResult results = confluenceClient
				.search("space = \"" + circleadSpace + "\" AND label = \"" + type.getLowerName() + "\"");

		String jsonSource = results.getContent();

		ObjectUtil.createPojoDirFromJson(jsonSource, "Results",
				"org.rogatio.circlead.control.synchronizer.atlassian.search", "lib");
	}

}
