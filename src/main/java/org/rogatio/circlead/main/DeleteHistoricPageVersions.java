package org.rogatio.circlead.main;

import java.io.IOException;

import org.rogatio.circlead.control.synchronizer.SynchronizerException;
import org.rogatio.circlead.control.synchronizer.atlassian.AtlassianSynchronizer;
import org.rogatio.circlead.model.WorkitemType;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

/**
 * The Class DeleteOldVersions allows to delete all historic page-version in Atlassian Confluence.
 *
 * @author Matthias Wegner
 */
public class DeleteHistoricPageVersions {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws JsonParseException the json parse exception
	 * @throws JsonMappingException the json mapping exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws SynchronizerException the synchronizer exception
	 */
	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException, SynchronizerException {
		AtlassianSynchronizer asynchronizer = new AtlassianSynchronizer("CIRCLEAD");
 
		asynchronizer.deleteVersions(WorkitemType.ACTIVITY);
		asynchronizer.deleteVersions(WorkitemType.ROLE);
		asynchronizer.deleteVersions(WorkitemType.ROLEGROUP);
		asynchronizer.deleteVersions(WorkitemType.PERSON);
		asynchronizer.deleteVersions(WorkitemType.TEAM);
		asynchronizer.deleteVersions(WorkitemType.COMPETENCE);
	}
	
}
