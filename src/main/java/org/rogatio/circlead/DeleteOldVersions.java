package org.rogatio.circlead;

import java.io.IOException;

import org.rogatio.circlead.control.synchronizer.SynchronizerException;
import org.rogatio.circlead.control.synchronizer.atlassian.AtlassianSynchronizer;
import org.rogatio.circlead.model.WorkitemType;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class DeleteOldVersions {

	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException, SynchronizerException {
		AtlassianSynchronizer asynchronizer = new AtlassianSynchronizer("CIRCLEAD");
 
		asynchronizer.deleteVersions(WorkitemType.ACTIVITY);
		asynchronizer.deleteVersions(WorkitemType.ROLE);
		asynchronizer.deleteVersions(WorkitemType.ROLEGROUP);
		asynchronizer.deleteVersions(WorkitemType.PERSON);
		
	}
	
}
