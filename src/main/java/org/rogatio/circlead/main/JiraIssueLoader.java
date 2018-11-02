package org.rogatio.circlead.main;

import static org.rogatio.circlead.control.synchronizer.atlassian.Constant.DEDICATEDSERVER;
import static org.rogatio.circlead.control.synchronizer.atlassian.Constant.PASSWORD;
import static org.rogatio.circlead.control.synchronizer.atlassian.Constant.URLJIRA;
import static org.rogatio.circlead.control.synchronizer.atlassian.Constant.USER;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.rogatio.circlead.control.synchronizer.atlassian.JiraClient;
import org.rogatio.circlead.control.synchronizer.atlassian.jira.Issue;
import org.rogatio.circlead.util.ObjectUtil;

/**
 * The Class JiraIssueLoader.
 */
public class JiraIssueLoader {

	/** The Constant LOGGER. */
	final static Logger LOGGER = LogManager.getLogger(JiraIssueLoader.class);

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		JiraClient c = new JiraClient(URLJIRA, USER, PASSWORD, DEDICATEDSERVER);

		String q = "project in (ASSET, GEB)";

//		String q = "project = GEB AND labels = Rolle:Verwaltungsleiter order by created DESC";

		int foundMax = c.getTotalFoundIssues(q);

		LOGGER.debug("Found '" + foundMax + "' results on query '" + q + "'");

		List<Issue> issues = c.getIssues(q);

		LOGGER.debug("Load '" + issues.size() + "' issues on query '" + q + "'");

		List<Issue> issuesWithoutLabels = new ArrayList<Issue>();

		HashMap<String, List<Issue>> issueMap = new HashMap<String, List<Issue>>();

		for (Issue issue : issues) {
			List<String> labels = issue.getFields().getLabels();
			if (ObjectUtil.isListNotNullAndEmpty(labels)) {
				for (String label : labels) {
					if (issueMap.containsKey(label)) {
						List<Issue> list = issueMap.get(label);
						list.add(issue);
						issueMap.put(label, list);
					} else {
						List<Issue> list = new ArrayList<Issue>();
						list.add(issue);
						issueMap.put(label, list);
					}
				}
			} else {
				issuesWithoutLabels.add(issue);
			}
		}

		Vector<String> keys = new Vector<String>(issueMap.keySet());

		Collections.sort(keys);
		
		for (String key : keys) {
			boolean foundRole = false;
			if (key.startsWith("Role:") || key.startsWith("Rolle:")) {
				foundRole = true;
			}

			if (foundRole) {
				LOGGER.info("Label: " + key + " (" + issueMap.get(key).size() + ")");
				List<Issue> list = issueMap.get(key);
				for (Issue issue : list) {
					LOGGER.info("   Issue '" + issue.getKey() + "' (type=" + issue.getFields().getIssuetype().getName()
							+ ", id=" + issue.getId() + ") " + issue.getFields().getSummary());
				}
			}
		}

		LOGGER.info("Label: None");
		for (Issue issue : issuesWithoutLabels) {
			LOGGER.info("   Issue '" + issue.getKey() + "' (type=" + issue.getFields().getIssuetype().getName()
					+ ", id=" + issue.getId() + ") " + issue.getFields().getSummary());
		}

		/*
		 * logger.info("Vorgänge ohne Rollen-Label"); for (Issue issue : issues) {
		 * boolean foundRole = false;
		 * 
		 * List<String> labels = issue.getFields().getLabels(); if
		 * (ObjectUtil.isListNotNullAndEmpty(labels)) { for (String label : labels) { if
		 * (label.startsWith("Role:") || label.startsWith("Rolle:")) { foundRole = true;
		 * } } }
		 * 
		 * if (!foundRole) { logger.info("   Issue '" + issue.getKey() + "' (type=" +
		 * issue.getFields().getIssuetype().getName() + ", id=" + issue.getId() + ") " +
		 * issue.getFields().getSummary()); }
		 * 
		 * }
		 */

	}

}
