package org.rogatio.circlead.main;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.rogatio.circlead.control.Repository;
import org.rogatio.circlead.control.synchronizer.SynchronizerResult;
import org.rogatio.circlead.control.synchronizer.atlassian.AtlassianSynchronizer;
import org.rogatio.circlead.control.synchronizer.file.FileSynchronizer;
import org.rogatio.circlead.control.webserver.Webserver;
import org.rogatio.circlead.model.WorkitemType;
import org.rogatio.circlead.util.FileUtil;
import org.rogatio.circlead.util.GroovyUtil;
import org.rogatio.circlead.util.PropertyUtil;
import org.rogatio.circlead.util.StringUtil;
import org.rogatio.circlead.view.report.OverviewReport;
import org.rogatio.circlead.view.report.PersonListReport;
import org.rogatio.circlead.view.report.PersonListReportDetails;
import org.rogatio.circlead.view.report.PersonRoleReport;
import org.rogatio.circlead.view.report.ReworkReport;
import org.rogatio.circlead.view.report.RoleHolderReport;
import org.rogatio.circlead.view.report.RoleListReportDetails;
import org.rogatio.circlead.view.report.RoleNeedReport;
import org.rogatio.circlead.view.report.RoleTreeReport;
import org.rogatio.circlead.view.report.RolegroupReport;
import org.rogatio.circlead.view.report.RolegroupSummaryReport;
import org.rogatio.circlead.view.report.TeamCategegoryInternalReport;
import org.rogatio.circlead.view.report.TeamCategoryReport;
import org.rogatio.circlead.view.report.ValidationReport;

/**
 * The Main Console Launcher for Circlead Application
 * 
 * @author Matthias Wegner
 */
public class Launcher {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LogManager.getLogger(Launcher.class);

	/**
	 * The main method of circlead
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

		/*
		 * Print help-text and skip program if no arguments are set
		 */
		if (args == null) {
			System.out.println(helpText());
			System.exit(0);
		}

		/*
		 * Print help-text and skip program if arguments are to less
		 */
		if (args.length == 0) {
			System.out.println(helpText());
			System.exit(0);
		}

		/*
		 * Go forward if arguments are set
		 */
		if (args != null && args.length > 0) {
			/*
			 * StringBuilder to summarize corresponding argument and value together
			 */
			StringBuilder sb = new StringBuilder();

			/*
			 * Iterate over arguments
			 */
			for (String arg : args) {
				/*
				 * set webserver-flag if argument is set
				 */
				if (StringUtil.containsInsensitive("webserver", arg)) {
					sb = new StringBuilder();
					webserver = true;
					clean = false;
					pojoJql = false;
					pojoRole = false;
					load = false;
				}
				/*
				 * set report-flag of report is set
				 */
				if (StringUtil.containsInsensitive("reports", arg)) {
					sb = new StringBuilder();
					reports = true;
					clean = false;
					pojoJql = false;
					pojoRole = false;
					load = false;
				}
				/*
				 * set directory-deletion-flag on file-synchronizer if clean set
				 */
				if (StringUtil.containsInsensitive("clean", arg)) {
					sb = new StringBuilder();
					clean = true;
					pojoJql = false;
					pojoRole = false;
					load = false;
				}
				/*
				 * set load-flag of load is set
				 */
				if (StringUtil.containsInsensitive("load", arg)) {
					sb = new StringBuilder();
					clean = false;
					load = true;
					pojoJql = false;
					pojoRole = false;
				}
				/*
				 * set jql-pojo of argument set
				 */
				if (StringUtil.containsInsensitive("pojoJql", arg)) {
					sb = new StringBuilder();
					clean = false;
					load = false;
					pojoJql = true;
					pojoRole = false;
				}
				/*
				 * set roleId for pojo if argument set
				 */
				if (StringUtil.containsInsensitive("pojoRoleId", arg)) {
					sb = new StringBuilder();
					clean = false;
					pojoJql = false;
					pojoRole = true;
				}
				/*
				 * build load-argument
				 */
				if (load) {
					loadArgs = buildArgument(arg, "-load", sb);
				}
				/*
				 * build cean-argument
				 */
				if (clean) {
					cleanArgs = buildArgument(arg, "-clean", sb);
				}
				/*
				 * build pojo jql argument
				 */
				if (pojoJql) {
					pojoJqlArgs = buildArgument(arg, "-pojoJQL", sb);
				}
				/*
				 * build pojo roleid argument
				 */
				if (pojoRole) {
					pojoRoleArgs = buildArgument(arg, "-pojoRoleId", sb);
				}
			}
		}

		/*
		 * Instanciate repository
		 */
		Repository repository = Repository.getInstance();

		AtlassianSynchronizer asynchronizer = null;
		FileSynchronizer fsynchronizer = null;

		/*
		 * Enable AtlassianSynchronizer interface, read, write if property set
		 */
		if (PropertyUtil.getInstance().isAtlassianSynchronizerEnabled()) {
			LOGGER.info("AtlassianSynchronizer enabled");
			/*
			 * Set system-location of data to 'CIRCLEAD'-space
			 */
			asynchronizer = new AtlassianSynchronizer("CIRCLEAD");
			repository.addSynchronizer(asynchronizer);

			LOGGER.info("AtlassianSynchronizer - Read-Mode '" + PropertyUtil.getInstance().isFileSynchronizerReadMode()
					+ "'");
			LOGGER.info("AtlassianSynchronizer - Write-Mode '"
					+ PropertyUtil.getInstance().isFileSynchronizerWriteMode() + "'");
		} else {
			LOGGER.info("AtlassianSynchronizer disabled");
		}

		/*
		 * Enable FileSynchronizer interface, read, write if property set
		 */
		if (PropertyUtil.getInstance().isFileSynchronizerEnabled()) {
			LOGGER.info("FileSynchronizer enabled");
			/*
			 * Set system-location of data to 'data'-directory
			 */
			fsynchronizer = new FileSynchronizer("data");
			repository.addSynchronizer(fsynchronizer);

			LOGGER.info(
					"FileSynchronizer - Read-Mode '" + PropertyUtil.getInstance().isFileSynchronizerReadMode() + "'");
			LOGGER.info(
					"FileSynchronizer - Write-Mode '" + PropertyUtil.getInstance().isFileSynchronizerWriteMode() + "'");

		} else {
			LOGGER.info("FileSynchronizer disabled");
		}

		/* Force Deletion of json-data for FileSynchronizer if AtlassianSynchronizer is
		* used
		*/
		if (PropertyUtil.getInstance().isAtlassianSynchronizerEnabled()
				&& PropertyUtil.getInstance().isFileSynchronizerEnabled()) {
			fsynchronizer.deleteAll();
		}

		/*
		 * Load json-data and create POJO-classes for atlassian if argument set
		 */
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

		/*
		 * Delete json-file-database of filesynchronizer if argument set
		 */
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

		/*
		 * Load system-data if argument set
		 */
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

		@SuppressWarnings("unused")
		List<SynchronizerResult> results = repository.updateWorkitems();

		/*
		 * Add reports if argument set
		 */
		if (reports) {
			repository.addReport(new RolegroupReport(PropertyUtil.getInstance().getApplicationDefaultRolegroup()));
			repository.addReport(new RoleHolderReport());
			repository.addReport(new OverviewReport());
			repository.addReport(new ValidationReport());
			repository.addReport(new ReworkReport());
			repository.addReport(new PersonListReport());
			repository.addReport(new PersonListReportDetails());
			repository.addReport(new RoleTreeReport());
			repository.addReport(new RoleNeedReport());
			repository.addReport(new RoleListReportDetails());
			repository.addReport(new PersonRoleReport(PropertyUtil.getInstance().getApplicationDefaultRoleReport()));
			repository.addReport(new RolegroupSummaryReport());
			repository
					.addReport(new TeamCategoryReport(PropertyUtil.getInstance().getApplicationDefaultTeamcategory()));
			repository.addReport(
					new TeamCategegoryInternalReport(PropertyUtil.getInstance().getApplicationDefaultTeamcategory()));

			repository.addReports();
			results = repository.updateReports();
		}

		/*
		 * Set last modified date when application was used
		 */
		PropertyUtil.getInstance().setRuntimeModifiedDateToActual();

		/*
		 * Run scripts (if available)
		 */
		Map<Object, String> gr = GroovyUtil.loadAndRunScripts();
		for (Object object : gr.keySet()) {
			LOGGER.debug(object + " " + gr.get(object));
		}
		
		/*
		 * Clean history version of atlassian if arguments are set
		 */
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

		/*
		 * Copy web-ressources for filesynchronizer from data-repository if file-synchronizer is used
		 */
		if (PropertyUtil.getInstance().isFileSynchronizerEnabled()) {

			repository.writeIndex();

			try {
				FileUtil.copyFileOrFolder(
						new File("data" + File.separatorChar + "ressources" + File.separatorChar + "images"),
						new File("web" + File.separatorChar + "images"));
				FileUtil.copyFileOrFolder(
						new File("data" + File.separatorChar + "ressources" + File.separatorChar + "javascript"),
						new File("web" + File.separatorChar + "javascript"));
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

		/*
		 * Start webserver if argument is set
		 */
		if (webserver) {
			LOGGER.info("Start Webserver through Launcher");
			Webserver server = new Webserver();
			server.run();
		}

	}

	/**
	 * Create help text for console when application is started without arguments
	 *
	 * @return help text
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
	 * Builds arguments when argument has parameter and value
	 *
	 * @param value the value of the argument
	 * @param key the key of the argument
	 * @param sb  the string builder which holds the argument
	 * @return the string of the argument
	 */
	private static String buildArgument(String value, String key, StringBuilder sb) {
		String a = StringUtil.replaceInsensitive(value, key);
		if (a.startsWith("=")) {
			a = a.substring(1, a.length()).trim();
		}

		sb.append(" " + a);
		return sb.toString();
	}

}
