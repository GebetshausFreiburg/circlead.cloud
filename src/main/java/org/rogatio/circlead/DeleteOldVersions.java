package org.rogatio.circlead;

import static org.rogatio.circlead.control.synchronizer.atlassian.Constant.DEDICATEDSERVER;
import static org.rogatio.circlead.control.synchronizer.atlassian.Constant.PASSWORD;
import static org.rogatio.circlead.control.synchronizer.atlassian.Constant.URL;
import static org.rogatio.circlead.control.synchronizer.atlassian.Constant.USER;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.rogatio.circlead.control.synchronizer.ISynchronizer;
import org.rogatio.circlead.control.synchronizer.SynchronizerException;
import org.rogatio.circlead.control.synchronizer.SynchronizerFactory;
import org.rogatio.circlead.control.synchronizer.SynchronizerResult;
import org.rogatio.circlead.control.synchronizer.atlassian.AtlassianSynchronizer;
import org.rogatio.circlead.control.synchronizer.atlassian.ConfluenceClient;
import org.rogatio.circlead.control.synchronizer.atlassian.content.Page;
import org.rogatio.circlead.control.synchronizer.atlassian.version.Result;
import org.rogatio.circlead.control.synchronizer.atlassian.version.Results;
import org.rogatio.circlead.model.WorkitemType;
import org.rogatio.circlead.model.work.IWorkitem;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DeleteOldVersions {

	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException, SynchronizerException {
		AtlassianSynchronizer asynchronizer = new AtlassianSynchronizer("CIRCLEAD");
//		asynchronizer.deleteVersions(364216321);

		asynchronizer.deleteVersions(WorkitemType.ACTIVITY);
		asynchronizer.deleteVersions(WorkitemType.ROLE);
		asynchronizer.deleteVersions(WorkitemType.ROLEGROUP);
		asynchronizer.deleteVersions(WorkitemType.PERSON);
		
	}
	
}
