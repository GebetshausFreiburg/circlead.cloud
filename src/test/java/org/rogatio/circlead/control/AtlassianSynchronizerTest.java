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

// TODO: Auto-generated Javadoc
/**
 * The Class AtlassianSynchronizerTest.
 */
public class AtlassianSynchronizerTest extends TestCase {

	/** The synchronizer. */
	private ISynchronizer synchronizer;
	
	/** The page id added page. */
	private int pageIdAddedPage;
	
	/** The page id copied page. */
	private int pageIdCopiedPage;

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		synchronizer = new AtlassianSynchronizer("CIRCLEADTEST");
		
		SynchronizerFactory.getInstance().addSynchronizer(synchronizer);
	}
	
	/**
	 * Test space key.
	 */
	public void testSpaceKey() {
		assertEquals("CIRCLEADTEST", ((AtlassianSynchronizer)synchronizer).getSpaceKey());
	}
	
	/**
	 * Test add workitem.
	 *
	 * @throws SynchronizerException the synchronizer exception
	 */
	public void testAddWorkitem() throws SynchronizerException {
		Role role = new Role();
		role.setOrganisationIdentifier("org");
		role.setTitle("Role (Added with Label)");
		role.setStatus("Active");
		SynchronizerResult page = synchronizer.add(role);
		pageIdAddedPage = Parser.getIdFromPage(page.getContent());
		assertTrue(page.getContent().contains("\"title\":\"" + role.getTitle() + "\""));
	}

	/**
	 * Test copy workitem.
	 *
	 * @throws SynchronizerException the synchronizer exception
	 */
	public void testCopyWorkitem() throws SynchronizerException {
		IWorkitem wi = synchronizer.get("234258486");
		wi.setTitle("Role (Copy-Test)");
		SynchronizerResult page = synchronizer.add(wi);
		pageIdCopiedPage = Parser.getIdFromPage(page.getContent());
		System.out.println(page.getContent());
		assertTrue(page.getContent().contains("\"title\":\"" + wi.getTitle() + "\""));
	}
	
	/**
	 * Test load workitem.
	 *
	 * @throws SynchronizerException the synchronizer exception
	 */
	public void testLoadWorkitem() throws SynchronizerException {
		IWorkitem wi = synchronizer.get("234258486");
		
//		System.out.println(wi.toString());
		
		assertEquals(wi.getTitle(), "Simple Page (Data)"); 
	}

	/**
	 * Test load page no data check.
	 */
	public void testLoadPageNoDataCheck() {
		try {
			synchronizer.get("231243902");
		} catch (SynchronizerException e) {
			assertEquals(e.toString(), "org.rogatio.circlead.control.synchronizer.SynchronizerException: Workitem (Page-Id '231243902') contains no data.");
		}
	}

	/**
	 * Test load wrong label.
	 */
	public void testLoadWrongLabel() {
		try {
			synchronizer.get("234422339");
		} catch (SynchronizerException e) {
//			System.out.println(e);
			assertTrue(e.toString().contains("org.rogatio.circlead.control.synchronizer.SynchronizerException: Type of Workitem (Page-Id '234422339') not found in label."));
		}
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	public void tearDown() throws Exception {
		super.tearDown();
		((AtlassianSynchronizer) synchronizer).delete(pageIdAddedPage);
		((AtlassianSynchronizer) synchronizer).delete(pageIdCopiedPage);
	}

}
