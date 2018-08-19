/*
 * Circlead - Develop and structure evolutionary Organisations
 * 
 * @author Matthias Wegner
 * @version 0.1
 * @since 01.07.2018
 * 
 */
package org.rogatio.circlead;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.rogatio.circlead.control.Repository;
import org.rogatio.circlead.control.synchronizer.SynchronizerResult;
import org.rogatio.circlead.control.synchronizer.atlassian.AtlassianSynchronizer;
import org.rogatio.circlead.control.synchronizer.file.FileSynchronizer;
import org.rogatio.circlead.model.work.Rolegroup;
import org.rogatio.circlead.view.report.OverviewReport;
import org.rogatio.circlead.view.report.PersonListReport;
import org.rogatio.circlead.view.report.ReworkReport;
import org.rogatio.circlead.view.report.RolegroupReport;
import org.rogatio.circlead.view.report.ValidationReport;

/**
 * Synchronize-Starter
 */
public class Sync {

	/** The Constant logger. */
	final static Logger logger = LogManager.getLogger(Sync.class);

	/**
	 * The main method of the application
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		List<SynchronizerResult> results = null;

		Repository repository = Repository.getInstance();

		/* Add both syncronizers. One for atlassian-confluence for space 'CIRCLEAD' and one for the Filesystem in folder 'data' */
		FileSynchronizer fsynchronizer = new FileSynchronizer("data");
		AtlassianSynchronizer asynchronizer = new AtlassianSynchronizer("CIRCLEAD");
		repository.addSynchronizer(asynchronizer);
		repository.addSynchronizer(fsynchronizer);

		/* Delete all workitem-folders, so the merging is not needed because confluence is in lead of the data. Gives speed to the data-synchronization. */
		fsynchronizer.deleteAll();

		/* Loads all data (from confluence, because the folders are emtpy) */
		repository.loadRoles();
		repository.loadRolegroups();
		repository.loadPersons();
		repository.loadActivities();

		/* Loads the index of howtos and reports from both interfaces */
		repository.loadIndexHowTos();
		repository.loadIndexReports();

		/* Re-Render loaded data back to set interfaces. Update pages in confluence and writes html-pages to local folder 'web' */
		results = repository.updateWorkitems();

		/* Add report-handler */
		if (repository.getRolegroups().size() > 0) {
			List<Rolegroup> rolegroups = repository.getRolegroups();
			for (Rolegroup rolegroup : rolegroups) {
				repository.addReport(new RolegroupReport(rolegroup));
			}
		}
		repository.addReport(new OverviewReport());
		repository.addReport(new ValidationReport());
		repository.addReport(new ReworkReport());
		repository.addReport(new PersonListReport());

		/* Rewrite Reports */
		// repository.addReports();
		results = repository.updateReports();
	}

}
