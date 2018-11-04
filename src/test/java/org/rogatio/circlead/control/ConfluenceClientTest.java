package org.rogatio.circlead.control;

//import static org.rogatio.circlead.control.synchronizer.atlassian.Constant.PASSWORD;
//import static org.rogatio.circlead.control.synchronizer.atlassian.Constant.URLCONFLUENCE;
//import static org.rogatio.circlead.control.synchronizer.atlassian.Constant.USER;

import org.rogatio.circlead.control.synchronizer.SynchronizerResult;
import org.rogatio.circlead.control.synchronizer.atlassian.ConfluenceClient;
import org.rogatio.circlead.control.synchronizer.atlassian.parser.Parser;
import org.rogatio.circlead.util.PropertyUtil;

import junit.framework.TestCase;

/**
 * The Class ConfluenceClientTest.
 */
public class ConfluenceClientTest extends TestCase {

	private final String URLCONFLUENCE = PropertyUtil.getInstance().getValue(PropertyUtil.ATLASSIAN_CONFLUENCE_URL);
	private final String USER = PropertyUtil.getInstance().getValue(PropertyUtil.ATLASSIAN_LOGIN_USER);
	private final String PASSWORD = PropertyUtil.getInstance().getValue(PropertyUtil.ATLASSIAN_LOGIN_PASSWORD);
	
	/** The client. */
	private ConfluenceClient client;

	/** The page id. */
	private int pageId; 
	
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		client = new ConfluenceClient(URLCONFLUENCE, USER, PASSWORD, false);
		SynchronizerResult page = client.newPage("JUNIT Add Label Test", "CIRCLEADTEST", "tbd");
		pageId = Parser.getIdFromPage(page.getContent());
	}

	/**
	 * Test system info.
	 */
	public void testSystemInfo() {
		@SuppressWarnings("unused")
		String s = "{\"cloudId\":\"66709d16-7978-485f-9461-387711ced4b9\",\"commitHash\":\"dbd53831988249326fc09786b14a9b8c2f96f6e8\"}";
		assertTrue(client.getSysteminfo().getContent().contains("66709d16-7978-485f-9461-387711ced4b9"));
	}

	/**
	 * Test add label.
	 */
	public void testAddLabel() {
		assertTrue(client.addLabel(pageId, "SomeLabel").getContent().contains("\"name\":\"somelabel\""));
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	public void tearDown() throws Exception {
		super.tearDown();	
		client.deletePage(pageId);
	}

}
