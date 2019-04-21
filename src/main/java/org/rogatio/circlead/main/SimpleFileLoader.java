package org.rogatio.circlead.main;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.rogatio.circlead.control.Repository;
import org.rogatio.circlead.control.synchronizer.SynchronizerResult;
import org.rogatio.circlead.control.synchronizer.file.FileSynchronizer;

public class SimpleFileLoader {

	final static Logger LOGGER = LogManager.getLogger(SimpleFileLoader.class);

	public static void main(String[] args) throws SecurityException {
	
		Repository repository = Repository.getInstance();

		FileSynchronizer fsynchronizer = new FileSynchronizer("data");
		repository.addSynchronizer(fsynchronizer);
		repository.loadRoles();
		repository.loadRolegroups();
		repository.loadPersons();
		repository.loadActivities();
		repository.loadTeams();
		repository.loadIndexHowTos();
		repository.loadIndexReports();
		repository.loadCompetencies();
		repository.addOrphanedRoleCompetenciesToRootCompetence();
		
		List<SynchronizerResult> results = repository.updateWorkitems();
		repository.writeIndex();

	}

}
