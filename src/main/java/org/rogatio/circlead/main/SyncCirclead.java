/*
 * Circlead - Develop and structure evolutionary Organisations
 * 
 * @author Matthias Wegner
 * @version 0.1
 * @since 01.07.2018
 * 
 */
package org.rogatio.circlead.main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.rogatio.circlead.control.Repository;
import org.rogatio.circlead.control.synchronizer.SynchronizerResult;
import org.rogatio.circlead.control.synchronizer.atlassian.AtlassianSynchronizer;
import org.rogatio.circlead.control.synchronizer.file.FileSynchronizer;
import org.rogatio.circlead.control.webserver.Webserver;
import org.rogatio.circlead.model.work.Person;
import org.rogatio.circlead.util.GroovyUtil;
import org.rogatio.circlead.util.PropertyUtil;
import org.rogatio.circlead.view.report.DefaultReport;

/**
 * Start-Class for Synchronizing Systems. Merge of workitems is not ready
 * implemented yet, so FileSynchronizer-Datastorage is deleted on every runtime.
 * Atlassian is main-database and FileSynchronizer is only Json-Workitem-Storage
 * as actual "copy" of Atlassian-Storage
 */
public class SyncCirclead {
	// TODO Rollenname erstetzen als Algorithmus einbauen, Bsp. Gebetstundenleiter
	// ist falsch
	// TODO Validation role status vs. person-holder status (i.e. paused, closed)
	// TODO Validation "empty" (<10 chars) text in role
	// TODO Atlassian User-Check (for Image-Usage or User-Link)
	// TODO Velocity als Render-Changer nutzen
	// TODO Activity-Process-Builder (yWorks) erzeugen
	// TODO Double-Value-Usage in Chart-Macro seems dependend from localisation,
	// because 1.3 is not valid in german and could be read in english. Diagram
	// shows value 13 in germany locale. QuickFix was normalization to int.

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

	public static final boolean IMPORTIMAGES = false;

//	public static final boolean EXPORT = true;

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
		AtlassianSynchronizer asynchronizer = new AtlassianSynchronizer();
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
			try {
				Files.walk(Paths.get("scripts")).filter(p -> p.toString().endsWith(".report.groovy"))
						.filter(Files::isRegularFile).forEach(file -> {
							LOGGER.debug("Add report '" + file.toFile().getName() + "'");
							repository.addReport(new DefaultReport(file.toFile().toString()));
						});
			} catch (IOException e) {
				e.printStackTrace();
			}

			/* Rewrite Reports */
			repository.addReports();
			results = repository.updateReports();
		}

		repository.writeIndex();

		if (IMPORTIMAGES) {
			List<Person> avatarPersons = repository.getPersonsWithAvatar();
			for (Person person : avatarPersons) {
				asynchronizer.saveImageAttachmentOfPage(person.getId(asynchronizer), person.getAvatar());
			}
		}

		// Set last modified Date
		PropertyUtil.getInstance().setRuntimeModifiedDateToActual();

		Map<Object, String> gr = GroovyUtil.loadAndRunScripts();
		for (Object object : gr.keySet()) {
			LOGGER.debug(object + " " + gr.get(object));
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
