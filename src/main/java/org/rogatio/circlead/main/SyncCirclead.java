/*
 * Circlead - Develop and structure evolutionary Organisations
 * 
 * @author Matthias Wegner
 * @version 0.1
 * @since 01.07.2018
 * 
 */
package org.rogatio.circlead.main;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.rogatio.circlead.control.Repository;
import org.rogatio.circlead.control.synchronizer.SynchronizerResult;
import org.rogatio.circlead.control.synchronizer.atlassian.AtlassianSynchronizer;
import org.rogatio.circlead.control.synchronizer.file.FileSynchronizer;
import org.rogatio.circlead.control.webserver.Webserver;
import org.rogatio.circlead.model.WorkitemType;
import org.rogatio.circlead.util.DropboxUtil;
import org.rogatio.circlead.util.FileUtil;
import org.rogatio.circlead.util.PropertyUtil;
import org.rogatio.circlead.view.report.OverviewReport;
import org.rogatio.circlead.view.report.PersonListReport;
import org.rogatio.circlead.view.report.PersonListReportDetails;
import org.rogatio.circlead.view.report.PersonRoleReport;
import org.rogatio.circlead.view.report.ReworkReport;
import org.rogatio.circlead.view.report.RoleHolderReport;
import org.rogatio.circlead.view.report.RoleListReportDetails;
import org.rogatio.circlead.view.report.RoleTreeReport;
import org.rogatio.circlead.view.report.RolegroupReport;
import org.rogatio.circlead.view.report.RolegroupSummaryReport;
import org.rogatio.circlead.view.report.TeamCategegoryInternalReport;
import org.rogatio.circlead.view.report.TeamCategoryReport;
import org.rogatio.circlead.view.report.ValidationReport;

import com.dropbox.core.v2.DbxTeamClientV2;

/**
 * Start-Class for Synchronizing Systems. Merge of workitems is not ready
 * implemented yet, so FileSynchronizer-Datastorage is deleted on every runtime.
 * Atlassian is main-database and FileSynchronizer is only Json-Workitem-Storage
 * as actual "copy" of Atlassian-Storage
 */
public class SyncCirclead {
	// TODO Rollenname erstezen als Agorithmus einbauen, Bsp. Gebetstundenleiter ist falsch
	// TODO Comment for person in team
	// TODO Validation role status vs. person-holder status (i.e. paused, closed)
	// TODO Validation "empty" (<10 chars) text in role
	// TODO Atlassian User-Check (for Image-Usage or User-Link)
	// TODO Velocity als Render-Changer nutzen
	// TODO Activity-Process-Builder (yWorks) erzeugen

	/** The Constant REPORTS. */
	public static final boolean REPORTS = true;

	/** The Constant HOWTOS. */
	public static final boolean HOWTOS = true;

	/** The Constant ROLES. */
	public static final boolean ROLES = true;

	/** The Constant ROLEGROUPS. */
	public static final boolean ROLEGROUPS = true;

	/** The Constant PERSONS. */
	public static final boolean PERSONS = true;

	/** The Constant ACTIVITIES. */
	public static final boolean ACTIVITIES = true;

	public static final boolean TEAMS = true;

	public static final boolean EXPORT = true;

	public static final boolean COMPETENCIES = true;

	public static final boolean WRITE_UPDATE = true;

	public static final boolean USEWEBSERVER = false;

	/** The Constant logger. */ 
	final static Logger LOGGER = LogManager.getLogger(SyncCirclead.class);

	/**
	 * The main method of the application
	 *
	 * @param args the arguments
	 * @author Matthias Wegner
	 */
	public static void main(String[] args) {
		@SuppressWarnings("unused")
		List<SynchronizerResult> results = null;

		Repository repository = Repository.getInstance();

		/*
		 * Add both synchronizers. One for atlassian-confluence for space 'CIRCLEAD' and
		 * one for the Filesystem in folder 'data'
		 */
		FileSynchronizer fsynchronizer = new FileSynchronizer("data");
		AtlassianSynchronizer asynchronizer = new AtlassianSynchronizer("CIRCLEAD");
		repository.addSynchronizer(asynchronizer);
		repository.addSynchronizer(fsynchronizer);

		/*
		 * Delete all workitem-folders, so the merging is not needed because confluence
		 * is in lead of the data. Gives speed to the data-synchronization.
		 */
		fsynchronizer.deleteAll();

		/* Loads all data (from confluence, because the folders are emtpy) */
		if (ROLES) {
			repository.loadRoles();
		}
		if (ROLEGROUPS) {
			repository.loadRolegroups();
		}
		if (PERSONS) {
			repository.loadPersons();
		}
		if (ACTIVITIES) {
			repository.loadActivities();
		}
		if (TEAMS) {
			repository.loadTeams();
		}

		if (COMPETENCIES) {
			repository.loadCompetencies();
		}

		/* Loads the index of howtos and reports from both interfaces */
		if (HOWTOS) {
			repository.loadIndexHowTos();
		}

		if (REPORTS) {
			repository.loadIndexReports();
		}

		if (COMPETENCIES) {
			repository.addOrphanedRoleCompetenciesToRootCompetence();
		}

		/*
		 * Re-Render loaded data back to set interfaces. Update pages in confluence and
		 * writes html-pages to local folder 'web'
		 */
		if (WRITE_UPDATE) {
			results = repository.updateWorkitems();
		}

		/* Add report-handler */
		if (REPORTS) {
			// repository.addReports(RolegroupReport.createReports());
			repository.addReport(new RolegroupReport(PropertyUtil.getInstance().getApplicationDefaultRolegroup()));
			repository.addReport(new RoleHolderReport());
			repository.addReport(new OverviewReport());
			repository.addReport(new ValidationReport());
			repository.addReport(new ReworkReport());
			repository.addReport(new PersonListReport());
			repository.addReport(new PersonListReportDetails());
			repository.addReport(new RoleTreeReport());
			repository.addReport(new PersonRoleReport(PropertyUtil.getInstance().getApplicationDefaultRoleReport()));
			repository.addReport(new RoleListReportDetails());
			repository.addReport(new RolegroupSummaryReport());
			repository.addReport(new TeamCategoryReport(PropertyUtil.getInstance().getApplicationDefaultTeamcategory()));
			repository.addReport(new TeamCategegoryInternalReport(PropertyUtil.getInstance().getApplicationDefaultTeamcategory()));
			// repository.addReport(new RoleIssueReport());

			/* Rewrite Reports */
			repository.addReports();
			results = repository.updateReports();
		}

		repository.writeIndex();

		if (EXPORT) {
			repository.writeExcel("Mitarbeiterliste", WorkitemType.PERSON, null);
			repository.writeExcel("Rollen", WorkitemType.ROLE, null);
			repository.writeExcel("Rollengruppen", WorkitemType.ROLEGROUP, null);

			PrayHourExporter phe = new PrayHourExporter();
			phe.export("Gebetsstundenübersicht");

			DbxTeamClientV2 dbxClient = DropboxUtil.getTeamClientFromAccessToken(PropertyUtil.getInstance().getDropboxAccesstoken());
//			DbxTeamClientV2 dbxClient = DropboxUtil.getTeamClientFromAuthFile("gebetshaus.credentials");
			DropboxUtil.uploadFileToTeamFolder(dbxClient, new File("exports/Gebetsstundenübersicht.xlsx"),
					"/06_GBH_BO_Gebetstundenorga/Gebetsstundenübersicht.xlsx");
		}

		// Set last modified Date
		PropertyUtil.getInstance().setRuntimeModifiedDateToActual();
		
		// Copy ressources to web-dir
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
		
		if (USEWEBSERVER) {
			// Create Webserver
			Webserver server = new Webserver();
			// Open Localhost in Browser
			server.openInBrowser();
			// Start Webserver
			server.run();
		}
	}

}
