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
		
		repository.loadRoles();
		repository.loadRolegroups();
		repository.loadPersons();
		
		for (IWorkitem workitem : repository.getWorkitems()) {

			repository.getConnector().update(workitem);
			
//			if (workitem instanceof Role) {
//				Role r = (Role)workitem;
//				if (r.getOrganisationIdentifier()!=null) {
//					if (r.getOrganisationIdentifier().equalsIgnoreCase("JobRad")) {
						//repository.getConnector().delete(workitem);
//					} 
//				}
//			}
			
			//			repository.getConnector().update(workitem);
		}

		repository.validate();
		
//		Person p = repository.getPerson("Matthias Wegner");
//		System.out.println(p);
		
//		IWorkitem wi = repository.get("422116fe-6bf5-4c34-9c81-1186d52a60e6");
		
//		IWorkitem wi = repository.get("efb4afed-3a49-4ca4-8971-658255297b07");

//		IWorkitem wi = repository.get("dbe87e5b-f9d2-4121-80be-c1bce9672553");

//		System.out.println(wi);
		
//		List<String> results = repository.getConnector().add(wi);
//		System.out.println(results);

		/*ISynchronizer is = SynchronizerFactory.getInstance().getSynchronizer("AtlassianSynchronizer");
		
		IWorkitem wi;
		try {
			
			wi = is.get("256671745");
			System.out.println(wi);
			
		} catch (SynchronizerException e) {
		}*/
		
	}

}
