package org.rogatio.circlead;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.rogatio.circlead.control.Repository;
import org.rogatio.circlead.control.synchronizer.AtlassianSynchronizer;
import org.rogatio.circlead.control.synchronizer.Connector;
import org.rogatio.circlead.control.synchronizer.FileSynchronizer;
import org.rogatio.circlead.control.synchronizer.ISynchronizer;
import org.rogatio.circlead.control.synchronizer.SynchronizerException;
import org.rogatio.circlead.control.synchronizer.SynchronizerFactory;
import org.rogatio.circlead.model.WorkitemType;
import org.rogatio.circlead.model.work.IWorkitem;
import org.rogatio.circlead.model.work.Person;
import org.rogatio.circlead.model.work.Role;

public class Sync {

	final static Logger logger = LogManager.getLogger(Sync.class);

	public static void main(String[] args) {

		//TODO Es fehlt die Darstellung 
		// - https://gebetshausfreiburg.atlassian.net/wiki/spaces/CIRCLEAD/pages/261619826/Webshop-Versender
		// - https://gebetshausfreiburg.atlassian.net/wiki/spaces/CIRCLEAD/pages/261750894/2.+Vorstand
		
		Repository repository = Repository.getInstance();

		FileSynchronizer fs = (FileSynchronizer)repository.getConnector().getSynchronizer("FileSynchronizer");
		fs.deleteAll();
		
//		repository.loadRoles();
//		repository.loadRolegroups();
//		repository.loadPersons();
		repository.loadActivities();
		
		repository.loadIndexHowTos();
//		List<String> howtoIndex = repository.getIndexHowTos();
//		for (String index : howtoIndex) {
//			System.out.println(index);
//		}
		
		for (IWorkitem workitem : repository.getWorkitems()) {
			repository.getConnector().update(workitem);
		}

		repository.validate();
				
	}

}
