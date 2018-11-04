/*
 * Circlead - Develop and structure evolutionary Organisations
 * 
 * @author Matthias Wegner
 * @version 0.1
 * @since 01.07.2018
 * 
 */
package org.rogatio.circlead.control.synchronizer.atlassian;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.rogatio.circlead.control.synchronizer.SynchronizerResult;
import org.rogatio.circlead.control.synchronizer.atlassian.jira.Issue;
import org.rogatio.circlead.control.synchronizer.atlassian.jira.Results;
import org.rogatio.circlead.util.PropertyUtil;
import org.rogatio.circlead.util.StringUtil;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * The Class JiraClient.
 * 
 * @author Matthias Wegner
 */
public class JiraClient extends HttpClient {

	private final int LIMIT = PropertyUtil.getInstance().getIntValue(PropertyUtil.ATLASSIAN_QUERY_LIMIT);
	
	/** The rest prefix. Is different for cloud to dedicated server */
	private String restPrefix = null;

	public JiraClient() {
		this.baseUrl = PropertyUtil.getInstance().getJiraUrl();
		this.user = PropertyUtil.getInstance().getAtlassianUser();
		this.password = PropertyUtil.getInstance().getAtlassianPassword();

		if (PropertyUtil.getInstance().isDedicatedServer()) {
			/* Set rest-prefix if atlassian-dedicated-server */
			restPrefix = "rest/api/2/";
			//LOGGER.info("JiraClient ist set to dedicated server");
		} else {
			/* Set rest-prefix if atlassian-cloud-server */
			restPrefix = "rest/api/3/";
			//LOGGER.info("JiraClient ist set to cloud server");
		}
	}
	
	/**
	 * Authentification is enabled with basic auth. Check "curl -v
	 * http://url-to-atlassian?os_authType=basic If basic auth enabled, the
	 * "WWW-Authenticate: Basic realm="protected-area" is returned
	 * 
	 * @param baseUrl  Url to the server, could be http or https
	 * @param user     Plain-Text of user to log in
	 * @param password Plain text of password
	 * @param server   true if dedicated server, false if cloud server
	 */
	public JiraClient(String baseUrl, String user, String password, boolean server) {
		this.baseUrl = baseUrl;
		this.user = user;
		this.password = password;

		if (server) {
			/* Set rest-prefix if atlassian-dedicated-server */
			restPrefix = "rest/api/2/";
			//LOGGER.info("JiraClient ist set to dedicated server");
		} else {
			/* Set rest-prefix if atlassian-cloud-server */
			restPrefix = "rest/api/3/";
			//LOGGER.info("JiraClient ist set to cloud server");
		}
	}

	/**
	 * Gets the total found issues.
	 *
	 * @param jql the jql
	 * @return the total found issues
	 */
	public int getTotalFoundIssues(String jql) {
		SynchronizerResult results = search(jql, null, 0);
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.setSerializationInclusion(Include.NON_NULL);
			Results p = mapper.readValue(results.getContent(), Results.class);

			return p.getTotal();
		} catch (IOException e) {
			LOGGER.error(e);
		}
		return 0;
	}

	/**
	 * Gets the issues.
	 *
	 * @param jql the jql
	 * @return the issues
	 */
	public List<Issue> getIssues(String jql) {
		int max = this.getTotalFoundIssues(jql);
		int step = 10;

		if (max > 100) {
			step = 100;
		}

		List<Issue> issuesList = new ArrayList<Issue>();
		for (int i = 0; i < max; i += step) {
			List<Issue> issues = getIssues(jql, null, step, i);
			issuesList.addAll(issues);
		}
		return issuesList;
	}

	/**
	 * Gets the issues.
	 *
	 * @param jql the jql
	 * @param max the max
	 * @param step the step
	 * @return the issues
	 */
	public List<Issue> getIssues(String jql, int max, int step) {
		List<Issue> issuesList = new ArrayList<Issue>();
		for (int i = 0; i < max; i += step) {
			List<Issue> issues = getIssues(jql, null, step, i);
			issuesList.addAll(issues);
		}
		return issuesList;
	}

	/**
	 * Gets the issues.
	 *
	 * @param jql the jql
	 * @param fields the fields
	 * @param max the max
	 * @param start the start
	 * @return the issues
	 */
	public List<Issue> getIssues(String jql, String fields, int max, int start) {
		SynchronizerResult results = search(jql, fields, max, start);
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.setSerializationInclusion(Include.NON_NULL);
			Results p = mapper.readValue(results.getContent(), Results.class);
			LOGGER.debug("Load Issues from " + (start + 1) + " to " + (start + p.getIssues().size()));
			return p.getIssues();
		} catch (IOException e) {
			LOGGER.error(e);
		}
		return null;
	}

	/**
	 * Gets the issues.
	 *
	 * @param jql the jql
	 * @param fields the fields
	 * @return the issues
	 */
	public List<Issue> getIssues(final String jql, final String fields) {
		return getIssues(jql, fields, LIMIT, -1);
	}

	/**
	 * Search.
	 *
	 * @param jql the jql
	 * @return the synchronizer result
	 */
	public SynchronizerResult search(final String jql) {
		return search(jql, null, LIMIT, 0);
	}

	/**
	 * Search.
	 *
	 * @param jql the jql
	 * @param fields the fields
	 * @param maxResults the max results
	 * @return the synchronizer result
	 */
	public SynchronizerResult search(String jql, String fields, int maxResults) {
		return search(jql, fields, maxResults, -1);
	}

	/**
	 * Search.
	 *
	 * @param jql the jql
	 * @param fields the fields
	 * @param maxResults the max results
	 * @param startAt the start at
	 * @return the synchronizer result
	 */
	public SynchronizerResult search(String jql, String fields, int maxResults, int startAt) {
		String prefix = "";
		if (startAt <= 0) {
			prefix = "maxResults=" + maxResults + "&";
		} else {
			prefix = "maxResults=" + maxResults + "&startAt=" + startAt + "&";
		}
		try {
			String encoded = URLEncoder.encode(jql, "UTF-8");
			if (StringUtil.isNotNullAndNotEmpty(fields)) {
				return this.get(restPrefix + "search?" + prefix + "jql=" + encoded + "&fields=" + fields);
			} else {
				return this.get(restPrefix + "search?" + prefix + "jql=" + encoded);
			}
		} catch (UnsupportedEncodingException e1) {
			return null;
		} catch (IOException e) {
			return null;
		}
	}

}
