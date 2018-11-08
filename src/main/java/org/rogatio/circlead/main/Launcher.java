package org.rogatio.circlead.main;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.rogatio.circlead.control.Repository;
import org.rogatio.circlead.control.synchronizer.SynchronizerResult;
import org.rogatio.circlead.control.synchronizer.atlassian.AtlassianSynchronizer;
import org.rogatio.circlead.control.synchronizer.file.FileSynchronizer;
import org.rogatio.circlead.control.webserver.Webserver;
import org.rogatio.circlead.model.WorkitemType;
import org.rogatio.circlead.util.FileUtil;
import org.rogatio.circlead.util.PropertyUtil;
import org.rogatio.circlead.util.StringUtil;
import org.rogatio.circlead.view.report.OverviewReport;
import org.rogatio.circlead.view.report.PersonListReport;
import org.rogatio.circlead.view.report.PersonListReportDetails;
import org.rogatio.circlead.view.report.ReworkReport;
import org.rogatio.circlead.view.report.RoleHolderReport;
import org.rogatio.circlead.view.report.RoleListReportDetails;
import org.rogatio.circlead.view.report.RoleTreeReport;
import org.rogatio.circlead.view.report.RolegroupReport;
import org.rogatio.circlead.view.report.RolegroupSummaryReport;
import org.rogatio.circlead.view.report.TeamCategegoryInternalReport;
import org.rogatio.circlead.view.report.TeamCategoryReport;
import org.rogatio.circlead.view.report.ValidationReport;

/**
 * The Class Launcher.
 */
public class Launcher {

	/** The Constant LOGGER. */
	final static Logger LOGGER = LogManager.getLogger(Launcher.class);

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {

		boolean webserver = false;
		boolean reports = false;
		boolean clean = false;
		String cleanArgs = "";
		boolean load = false;
		String loadArgs = "";
		boolean pojoJql = false;
		String pojoJqlArgs = "";
		boolean pojoRole = false;
		String pojoRoleArgs = "";

		if (args == null) {
			System.out.println(helpText());
			System.exit(0);
		}
		if (args.length == 0) {
			System.out.println(helpText());
			System.exit(0);
		}

		if (args != null && args.length > 0) {
			StringBuilder sb = new StringBuilder();
			for (String arg : args) {
				if (StringUtil.containsInsensitive("webserver", arg)) {
					sb = new StringBuilder();
					webserver = true;
					clean = false;
					pojoJql = false;
					pojoRole = false;
					load = false;
				}
				if (StringUtil.containsInsensitive("reports", arg)) {
					sb = new StringBuilder();
					reports = true;
					clean = false;
					pojoJql = false;
					pojoRole = false;
					load = false;
				}
				if (StringUtil.containsInsensitive("clean", arg)) {
					sb = new StringBuilder();
					clean = true;
					pojoJql = false;
					pojoRole = false;
					load = false;
				}
				if (StringUtil.containsInsensitive("load", arg)) {
					sb = new StringBuilder();
					clean = false;
					load = true;
					pojoJql = false;
					pojoRole = false;
				}
				if (StringUtil.containsInsensitive("pojoJql", arg)) {
					sb = new StringBuilder();
					clean = false;
					load = false;
					pojoJql = true;
					pojoRole = false;
				}
				if (StringUtil.containsInsensitive("pojoRoleId", arg)) {
					sb = new StringBuilder();
					clean = false;
					pojoJql = false;
					pojoRole = true;
				}
				if (load) {
					loadArgs = buildArgument(arg, "-load", sb);
				}
				if (clean) {
					cleanArgs = buildArgument(arg, "-clean", sb);
				}
				if (pojoJql) {
					pojoJqlArgs = buildArgument(arg, "-pojoJQL", sb);
				}
				if (pojoRole) {
					pojoRoleArgs = buildArgument(arg, "-pojoRoleId", sb);
				}
			}
		}

		Repository repository = Repository.getInstance();
		AtlassianSynchronizer asynchronizer = null;
		FileSynchronizer fsynchronizer = null;

		if (PropertyUtil.getInstance().isAtlassianSynchronizerEnabled()) {
			LOGGER.info("AtlassianSynchronizer enabled");
			asynchronizer = new AtlassianSynchronizer("CIRCLEAD");
			repository.addSynchronizer(asynchronizer);

			LOGGER.info("AtlassianSynchronizer - Read-Mode '" + PropertyUtil.getInstance().isFileSynchronizerReadMode()
					+ "'");
			LOGGER.info("AtlassianSynchronizer - Write-Mode '"
					+ PropertyUtil.getInstance().isFileSynchronizerWriteMode() + "'");
		} else {
			LOGGER.info("AtlassianSynchronizer disabled");
		}

		if (PropertyUtil.getInstance().isFileSynchronizerEnabled()) {
			LOGGER.info("FileSynchronizer enabled");
			fsynchronizer = new FileSynchronizer("data");
			repository.addSynchronizer(fsynchronizer);

			LOGGER.info(
					"FileSynchronizer - Read-Mode '" + PropertyUtil.getInstance().isFileSynchronizerReadMode() + "'");
			LOGGER.info(
					"FileSynchronizer - Write-Mode '" + PropertyUtil.getInstance().isFileSynchronizerWriteMode() + "'");

		} else {
			LOGGER.info("FileSynchronizer disabled");
		}

		if (StringUtil.isNotNullAndNotEmpty(pojoJqlArgs) && StringUtil.isNotNullAndNotEmpty(pojoRoleArgs)) {
			String roleId = pojoRoleArgs.trim();
			String jql = pojoJqlArgs.trim();
			LOGGER.info("Create AtlassianPojos with JQL='" + jql + "' and roleID='" + roleId + "'");
			AtlassianPojoCreator.createAtlassianPojos(roleId, jql);
		} else {
			if (!StringUtil.isNotNullAndNotEmpty(pojoJqlArgs) && StringUtil.isNotNullAndNotEmpty(pojoRoleArgs)) {
				LOGGER.warn("Could NOT create POJO-Source, because pojoJql is mandatory.");
			}
			if (StringUtil.isNotNullAndNotEmpty(pojoJqlArgs) && !StringUtil.isNotNullAndNotEmpty(pojoRoleArgs)) {
				LOGGER.warn("Could NOT create POJO-Source, because pojoRoleId is mandatory.");
			}
		}

		if (StringUtil.isNotNullAndNotEmpty(cleanArgs)) {
			if (cleanArgs.contains("f")) {
				if (fsynchronizer != null) {
					LOGGER.info("Clean FileSynchronizer-Database");
					fsynchronizer.deleteAll();
				} else {
					LOGGER.warn("Could not clean FileSynchronizer-Database. Synchronizer not started.");
				}
			}
		}

		if (StringUtil.isNotNullAndNotEmpty(loadArgs)) {
			if (loadArgs.contains("i")) {
				LOGGER.info("Load Howtos");
				repository.loadIndexHowTos();
			}

			if (loadArgs.contains("x")) {
				LOGGER.info("Load Reports");
				repository.loadIndexReports();
			}
			if (loadArgs.contains("a")) {
				LOGGER.info("Load Activitites");
				repository.loadActivities();
			}
			if (loadArgs.contains("r")) {
				LOGGER.info("Load Roles");
				repository.loadRoles();
			}
			if (loadArgs.contains("g")) {
				LOGGER.info("Load Rolegroups");
				repository.loadRolegroups();
			}
			if (loadArgs.contains("p")) {
				LOGGER.info("Load Persons");
				repository.loadPersons();
			}
			if (loadArgs.contains("t")) {
				LOGGER.info("Load Teams");
				repository.loadTeams();
			}
			if (loadArgs.contains("c")) {
				LOGGER.info("Load Competencies");
				repository.loadCompetencies();
				repository.addOrphanedRoleCompetenciesToRootCompetence();
			}

		}

		List<SynchronizerResult> results = repository.updateWorkitems();

		if (reports) {
			repository.addReport(new RolegroupReport(PropertyUtil.getInstance().getApplicationDefaultRolegroup()));
			repository.addReport(new RoleHolderReport());
			repository.addReport(new OverviewReport());
			repository.addReport(new ValidationReport());
			repository.addReport(new ReworkReport());
			repository.addReport(new PersonListReport());
			repository.addReport(new PersonListReportDetails());
			repository.addReport(new RoleTreeReport());
			repository.addReport(new RoleListReportDetails());
			repository.addReport(new RolegroupSummaryReport());
			repository
					.addReport(new TeamCategoryReport(PropertyUtil.getInstance().getApplicationDefaultTeamcategory()));
			repository.addReport(
					new TeamCategegoryInternalReport(PropertyUtil.getInstance().getApplicationDefaultTeamcategory()));

			repository.addReports();
			results = repository.updateReports();
		}

		if (StringUtil.isNotNullAndNotEmpty(cleanArgs)) {
			if (cleanArgs.contains("a")) {
				LOGGER.info("Clean Atlassian History of Activitites");
				asynchronizer.deleteVersions(WorkitemType.ACTIVITY);
			}
			if (cleanArgs.contains("r")) {
				LOGGER.info("Clean Atlassian History of Roles");
				asynchronizer.deleteVersions(WorkitemType.ROLE);
			}
			if (cleanArgs.contains("g")) {
				LOGGER.info("Clean Atlassian History of Rolegroups");
				asynchronizer.deleteVersions(WorkitemType.ROLEGROUP);
			}
			if (cleanArgs.contains("p")) {
				LOGGER.info("Clean Atlassian History of Persons");
				asynchronizer.deleteVersions(WorkitemType.PERSON);
			}
			if (cleanArgs.contains("t")) {
				LOGGER.info("Clean Atlassian History of Teams");
				asynchronizer.deleteVersions(WorkitemType.TEAM);
			}
			if (cleanArgs.contains("c")) {
				LOGGER.info("Clean Atlassian History of Competencies");
				asynchronizer.deleteVersions(WorkitemType.COMPETENCE);
			}
		}

		if (PropertyUtil.getInstance().isFileSynchronizerEnabled()) {

			repository.writeIndex();

			try {
				FileUtil.copyFileOrFolder(
						new File("data" + File.separatorChar + "ressources" + File.separatorChar + "images"),
						new File("web" + File.separatorChar + "images"));
				FileUtil.copyFileOrFolder(new File("data" + File.separatorChar + "howtos"),
						new File("web" + File.separatorChar + "howtos"));
				FileUtil.copyFileOrFolder(
						new File("data" + File.separatorChar + "ressources" + File.separatorChar + "styles.css"),
						new File("web" + File.separatorChar + "styles.css"));
				FileUtil.copyFileOrFolder(
						new File("data" + File.separatorChar + "ressources" + File.separatorChar
								+ "stylesCategoryReport.css"),
						new File("web" + File.separatorChar + "stylesCategoryReport.css"));
			} catch (IOException e) {
				LOGGER.error(e);
			}
		}

		if (webserver) {
			LOGGER.info("Start Webserver through Launcher");
			Webserver server = new Webserver();
			server.run();
		}

	}

	/**
	 * Help text.
	 *
	 * @return the string
	 */
	private static String helpText() {
		StringBuilder sb = new StringBuilder();

		sb.append("Command: java -jar circlead.jar [OPTION]...\n");
		sb.append("\n");
		sb.append(" -load=[PARAM]...     Load Dataitems of Database-System\n");
		sb.append("   a                  Load all Activity-Dataitems of enabled Synchronizers\n");
		sb.append("   r                  Load all Role-Dataitems of enabled Synchronizers\n");
		sb.append("   g                  Load all Rolegroup-Dataitems of enabled Synchronizers\n");
		sb.append("   p                  Load all Person-Dataitems of enabled Synchronizers\n");
		sb.append("   t                  Load all Team-Dataitems of enabled Synchronizers\n");
		sb.append("   c                  Load all Competence-Dataitems of enabled Synchronizers\n");
		sb.append(" -clean=[PARAM]...    Clean History or Delete Data on Database-System\n");
		sb.append("   f                  Delete all synchronizable Data in data-Directory through FileSynchronizer\n");
		sb.append(
				"   a                  Delete historic version of Activity-Dataitems through AtlassianSynchronizer\n");
		sb.append("   r                  Delete historic version of Role-Dataitems through AtlassianSynchronizer\n");
		sb.append(
				"   g                  Delete historic version of Rolegroup-Dataitems through AtlassianSynchronizer\n");
		sb.append("   p                  Delete historic version of Person-Dataitems through AtlassianSynchronizer\n");
		sb.append("   t                  Delete historic version of Team-Dataitems through AtlassianSynchronizer\n");
		sb.append(
				"   c                  Delete historic version of Competence-Dataitems through AtlassianSynchronizer\n");
		sb.append(" -reports             Create and write Reports\n");
		sb.append(" -pojoJql=[JQL]       Set Jira Query to receive issue results for POJO-Source-Creation\n");
		sb.append(" -pojoRoleId=[RID]    Set Page-Id of Circlead-Role-Dataitem to for POJO-Source-Creation\n");
		sb.append(" -webserver           Start Webserver\n");

		return sb.toString();
	}

	/**
	 * Builds the argument.
	 *
	 * @param arg the arg
	 * @param key the key
	 * @param sb the sb
	 * @return the string
	 */
	private static String buildArgument(String arg, String key, StringBuilder sb) {
		String a = StringUtil.replaceInsensitive(arg, key);
		if (a.startsWith("=")) {
			a = a.substring(1, a.length()).trim();
		}

		sb.append(" " + a);
		return sb.toString();
	}

}
