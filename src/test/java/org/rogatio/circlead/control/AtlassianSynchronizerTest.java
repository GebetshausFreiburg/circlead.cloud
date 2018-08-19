package org.rogatio.circlead.control;

import org.rogatio.circlead.control.synchronizer.ISynchronizer;
import org.rogatio.circlead.control.synchronizer.SynchronizerException;
import org.rogatio.circlead.control.synchronizer.SynchronizerFactory;
import org.rogatio.circlead.control.synchronizer.SynchronizerResult;
import org.rogatio.circlead.control.synchronizer.atlassian.AtlassianSynchronizer;
import org.rogatio.circlead.control.synchronizer.atlassian.parser.Parser;
import org.rogatio.circlead.model.work.IWorkitem;
import org.rogatio.circlead.model.work.Role;

import junit.framework.TestCase;

public class AtlassianSynchronizerTest extends TestCase {

	private ISynchronizer synchronizer;
	private int pageIdAddedPage;
	private int pageIdCopiedPage;

	protected void setUp() throws Exception {
		super.setUp();
		synchronizer = new AtlassianSynchronizer("CIRCLEADTEST");
		
		SynchronizerFactory.getInstance().addSynchronizer(synchronizer);
	}
	
	public void testSpaceKey() {
		assertEquals("CIRCLEADTEST", ((AtlassianSynchronizer)synchronizer).getSpaceKey());
	}
	
	public void testAddWorkitem() throws SynchronizerException {
		Role role = new Role();
		role.setOrganisationIdentifier("org");
		role.setTitle("Role (Added with Label)");
		role.setStatus("Active");
		SynchronizerResult page = synchronizer.add(role);
		pageIdAddedPage = Parser.getIdFromPage(page.getContent());
		assertTrue(page.getContent().contains("\"title\":\"" + role.getTitle() + "\""));
	}

	public void testCopyWorkitem() throws SynchronizerException {
		IWorkitem wi = synchronizer.get("234258486");
		wi.setTitle("Role (Copy-Test)");
		SynchronizerResult page = synchronizer.add(wi);
		pageIdCopiedPage = Parser.getIdFromPage(page.getContent());
		System.out.println(page.getContent());
		assertTrue(page.getContent().contains("\"title\":\"" + wi.getTitle() + "\""));
	}
	
	public void testLoadWorkitem() throws SynchronizerException {
		IWorkitem wi = synchronizer.get("234258486");
		
//		System.out.println(wi.toString());
		
		assertEquals(wi.getTitle(), "Simple Page (Data)"); 
	}

	public void testLoadPageNoDataCheck() {
		try {
			synchronizer.get("231243902");
		} catch (SynchronizerException e) {
			assertEquals(e.toString(), "org.rogatio.circlead.control.synchronizer.SynchronizerException: Workitem (Page-Id '231243902') contains no data.");
		}
	}

	public void testLoadWrongLabel() {
		try {
			synchronizer.get("234422339");
		} catch (SynchronizerException e) {
//			System.out.println(e);
			assertTrue(e.toString().contains("org.rogatio.circlead.control.synchronizer.SynchronizerException: Type of Workitem (Page-Id '234422339') not found in label."));
		}
	}

	public void tearDown() throws Exception {
		super.tearDown();
		((AtlassianSynchronizer) synchronizer).delete(pageIdAddedPage);
		((AtlassianSynchronizer) synchronizer).delete(pageIdCopiedPage);
	}

}
