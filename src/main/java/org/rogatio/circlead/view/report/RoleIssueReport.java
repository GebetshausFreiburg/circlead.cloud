package org.rogatio.circlead.view.report;

//import static org.rogatio.circlead.control.synchronizer.atlassian.Constant.DEDICATEDSERVER;
//import static org.rogatio.circlead.control.synchronizer.atlassian.Constant.PASSWORD;
//import static org.rogatio.circlead.control.synchronizer.atlassian.Constant.URLJIRA;
//import static org.rogatio.circlead.control.synchronizer.atlassian.Constant.USER;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.nodes.Element;
import org.rogatio.circlead.control.synchronizer.ISynchronizer;
import org.rogatio.circlead.control.synchronizer.atlassian.AtlassianSynchronizer;
import org.rogatio.circlead.control.synchronizer.atlassian.JiraClient;
import org.rogatio.circlead.control.synchronizer.atlassian.jira.Issue;
import org.rogatio.circlead.util.ObjectUtil;
import org.rogatio.circlead.util.PropertyUtil;

/**
 * The Class RoleIssueReport.
 */
public class RoleIssueReport extends DefaultReport {

	/** The urljira. */
	private final String URLJIRA = PropertyUtil.getInstance().getValue(PropertyUtil.ATLASSIAN_JIRA_URL);
	
	/** The user. */
	private final String USER = PropertyUtil.getInstance().getValue(PropertyUtil.ATLASSIAN_LOGIN_USER);
	
	/** The password. */
	private final String PASSWORD = PropertyUtil.getInstance().getValue(PropertyUtil.ATLASSIAN_LOGIN_PASSWORD);
	
	/** The dedicatedserver. */
	private final boolean DEDICATEDSERVER = PropertyUtil.getInstance().getBooleanValue(PropertyUtil.ATLASSIAN_SERVER_DEDICATED);
	
	/** The Constant LOGGER. */
	final static Logger LOGGER = LogManager.getLogger(RoleIssueReport.class);

	/**
	 * Instantiates a new role issue report.
	 */
	public RoleIssueReport() {
		this.setName("Issue by Role-Label Report");
		this.setDescription("Jira-Vorgangsübersicht nach Rollen klassifiziert");
	}
	
	/* (non-Javadoc)
	 * @see org.rogatio.circlead.view.report.DefaultReport#render(org.rogatio.circlead.control.synchronizer.ISynchronizer)
	 */
	@Override
	public Element render(ISynchronizer synchronizer) {
		Element element = new Element("p");

		if (synchronizer.getClass().getSimpleName().equals(AtlassianSynchronizer.class.getSimpleName())) {

			if (DEDICATEDSERVER) {
				element.appendText("Not implemented for Dedicated Server");
				return element;
			}
			
			JiraClient c = new JiraClient(URLJIRA, USER, PASSWORD, DEDICATEDSERVER);

			String q = "project in (ASSET, GEB)";

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

			Element ul = element.appendElement("ul");
			
			for (String key : keys) {
				boolean foundRole = false;
				if (key.startsWith("Role:") || key.startsWith("Rolle:")) {
					foundRole = true;
				}

				if (foundRole) {
//				logger.info("Label: " + key + " (" + issueMap.get(key).size() + ")");
//				List<Issue> list = issueMap.get(key);
//				for (Issue issue : list) {
//					logger.info("   Issue '" + issue.getKey() + "' (type=" + issue.getFields().getIssuetype().getName()
//							+ ", id=" + issue.getId() + ") " + issue.getFields().getSummary());
//				}
					ul.append(addIssueList(q, key));
				}
			}

//		logger.info("Label: None");
//		for (Issue issue : issuesWithoutLabels) {
//			logger.info("   Issue '" + issue.getKey() + "' (type=" + issue.getFields().getIssuetype().getName()
//					+ ", id=" + issue.getId() + ") " + issue.getFields().getSummary());
//		}

		} else {
			element.appendText("Not implemented for FileSynchronizer");
		}

		return element;
	}

	/**
	 * Adds the issue list.
	 *
	 * @param query the query
	 * @param roleLabel the role label
	 * @return the string
	 */
	private String addIssueList(String query, String roleLabel) {
		return  "<li>" + roleLabel + " (<ac:structured-macro ac:name=\"jira\" ac:schema-version=\"1\" "
				+ "ac:macro-id=\"0eded1d6-9414-4884-b299-b48ac912a317\">"
				+ "<ac:parameter ac:name=\"server\">System Jira</ac:parameter>"
				+ "<ac:parameter ac:name=\"jqlQuery\">"+query+" AND labels = " + roleLabel
				+ " ORDER BY created DESC </ac:parameter>" + "<ac:parameter ac:name=\"count\">true</ac:parameter>"
				+ "<ac:parameter ac:name=\"serverId\">aa483c16-161d-3599-b439-850eba0fbf58</ac:parameter></ac:structured-macro>)"
				+ "</li>";
	}

}
