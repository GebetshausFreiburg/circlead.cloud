package org.rogatio.circlead;

import static org.rogatio.circlead.control.synchronizer.atlassian.Constant.DEDICATEDSERVER;
import static org.rogatio.circlead.control.synchronizer.atlassian.Constant.PASSWORD;
import static org.rogatio.circlead.control.synchronizer.atlassian.Constant.URLJIRA;
import static org.rogatio.circlead.control.synchronizer.atlassian.Constant.USER;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.rogatio.circlead.control.synchronizer.SynchronizerException;
import org.rogatio.circlead.control.synchronizer.SynchronizerResult;
import org.rogatio.circlead.control.synchronizer.atlassian.HttpClient;
import org.rogatio.circlead.control.synchronizer.atlassian.JiraClient;
import org.rogatio.circlead.control.synchronizer.atlassian.content.Page;
import org.rogatio.circlead.control.synchronizer.atlassian.jira.Issue;
import org.rogatio.circlead.control.synchronizer.atlassian.jira.Results;
import org.rogatio.circlead.util.ObjectUtil;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JiraIssueLoader {

	final static Logger logger = LogManager.getLogger(JiraIssueLoader.class);

	public static void main(String[] args) {
		JiraClient c = new JiraClient(URLJIRA, USER, PASSWORD, DEDICATEDSERVER);

		String q = "project in (ASSET, GEB)";

//		String q = "project = GEB AND labels = Rolle:Verwaltungsleiter order by created DESC";

		int foundMax = c.getTotalFoundIssues(q);

		logger.debug("Found '" + foundMax + "' results on query '" + q + "'");

		List<Issue> issues = c.getIssues(q);

		logger.debug("Load '" + issues.size() + "' issues on query '" + q + "'");

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
		/*
		 * for (String key : keys) { logger.info("Label: " + key); List<Issue> list =
		 * issueMap.get(key); for (Issue issue : list) { logger.info("   Issue '" +
		 * issue.getKey() + "' (type=" + issue.getFields().getIssuetype().getName() +
		 * ", id=" + issue.getId() + ") " + issue.getFields().getSummary()); } }
		 */


		/*logger.info("Label: None");
		for (Issue issue : issuesWithoutLabels) {
			logger.info("   Issue '" + issue.getKey() + "' (type=" + issue.getFields().getIssuetype().getName()
					+ ", id=" + issue.getId() + ") " + issue.getFields().getSummary());
		}*/
		
		logger.info("Vorgänge ohne Rollen-Label");
		for (Issue issue : issues) {
			boolean foundRole = false;
			
			List<String> labels = issue.getFields().getLabels();
			if (ObjectUtil.isListNotNullAndEmpty(labels)) {
				for (String label : labels) {
					if (label.startsWith("Role:") || label.startsWith("Rolle:")) {
						foundRole = true;
					}
				}				
			}
			
			if (!foundRole) {
				logger.info("   Issue '" + issue.getKey() + "' (type=" + issue.getFields().getIssuetype().getName()
						+ ", id=" + issue.getId() + ") " + issue.getFields().getSummary());
			}

		}

	}

}
