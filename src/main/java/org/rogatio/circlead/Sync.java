package org.rogatio.circlead;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.rogatio.circlead.control.Repository;
import org.rogatio.circlead.control.synchronizer.AtlassianSynchronizer;
import org.rogatio.circlead.control.synchronizer.FileSynchronizer;
import org.rogatio.circlead.control.synchronizer.SynchronizerException;
import org.rogatio.circlead.view.ValidationReport;

public class Sync {

	final static Logger logger = LogManager.getLogger(Sync.class);

	public static void main(String[] args) {
		Repository repository = Repository.getInstance();

		FileSynchronizer fsynchronizer = new FileSynchronizer("data");
		AtlassianSynchronizer asynchronizer = new AtlassianSynchronizer("CIRCLEAD");
		repository.addSynchronizer(asynchronizer);
		repository.addSynchronizer(fsynchronizer);
		fsynchronizer.deleteAll();

		repository.loadRoles();
		repository.loadRolegroups();
		repository.loadPersons();
		repository.loadActivities();
		repository.loadIndexHowTos();
		repository.loadIndexReports();

		repository.updateWorkitems();

		repository.addReport(new ValidationReport());
		repository.updateReports();

	}

}
