package org.rogatio.circlead.control;

import static org.rogatio.circlead.control.synchronizer.atlassian.Constant.PASSWORD;
import static org.rogatio.circlead.control.synchronizer.atlassian.Constant.URLCONFLUENCE;
import static org.rogatio.circlead.control.synchronizer.atlassian.Constant.USER;

import org.rogatio.circlead.control.synchronizer.SynchronizerResult;
import org.rogatio.circlead.control.synchronizer.atlassian.ConfluenceClient;
import org.rogatio.circlead.control.synchronizer.atlassian.parser.Parser;

import junit.framework.TestCase;

public class ConfluenceClientTest extends TestCase {

	private ConfluenceClient client;

	private int pageId; 
	
	protected void setUp() throws Exception {
		super.setUp();
		client = new ConfluenceClient(URLCONFLUENCE, USER, PASSWORD, false);
		SynchronizerResult page = client.newPage("JUNIT Add Label Test", "CIRCLEADTEST", "tbd");
		pageId = Parser.getIdFromPage(page.getContent());
	}

	public void testSystemInfo() {
		@SuppressWarnings("unused")
		String s = "{\"cloudId\":\"66709d16-7978-485f-9461-387711ced4b9\",\"commitHash\":\"dbd53831988249326fc09786b14a9b8c2f96f6e8\"}";
		assertTrue(client.getSysteminfo().getContent().contains("66709d16-7978-485f-9461-387711ced4b9"));
	}

	public void testAddLabel() {
		assertTrue(client.addLabel(pageId, "SomeLabel").getContent().contains("\"name\":\"somelabel\""));
	}

	public void tearDown() throws Exception {
		super.tearDown();	
		client.deletePage(pageId);
	}

}
