package org.rogatio.circlead.control;

import java.util.List;
import java.util.UUID;

import org.rogatio.circlead.control.synchronizer.ISynchronizer;
import org.rogatio.circlead.control.synchronizer.SynchronizerException;
import org.rogatio.circlead.control.synchronizer.SynchronizerFactory;
import org.rogatio.circlead.control.synchronizer.SynchronizerResult;
import org.rogatio.circlead.control.synchronizer.file.FileSynchronizer;
import org.rogatio.circlead.model.WorkitemType;
import org.rogatio.circlead.model.work.IWorkitem;

import junit.framework.TestCase;

// TODO: Auto-generated Javadoc
/**
 * The Class FileSynchronizerTest.
 */
public class FileSynchronizerTest extends TestCase {

	/** The synchronizer. */
	private ISynchronizer synchronizer;

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		FileSynchronizer fsynchronizer = new FileSynchronizer("data-test");
		synchronizer = fsynchronizer;
		
		SynchronizerFactory.getInstance().addSynchronizer(synchronizer);
	}

	/**
	 * Test data dir set.
	 */
	public void testDataDirSet() {
		assertEquals("data-test", ((FileSynchronizer)synchronizer).getDataDirectory());
	}
	
	/*
	 * public void NOTIMPLEMENTED_testAddWorkitem() throws SynchronizerException { String result = synchronizer.add(new DefaultWorkitem()); assertTrue(result,
	 * result!=null); }
	 */
	
	/**
	 * Test load role index.
	 */
	public void testLoadRoleIndex() {
		List<String> list = synchronizer.loadIndex(WorkitemType.ROLE);
		assertTrue(list.size()>0);
	}

	/**
	 * Test load workitem with inconsistend id.
	 *
	 * @throws SynchronizerException the synchronizer exception
	 */
	public void testLoadWorkitemWithInconsistendId() throws SynchronizerException {
		try {
			@SuppressWarnings("unused")
			IWorkitem wi = synchronizer.get("data-test/roles/c3f62836-575d-4a6c-a2fb-a757cab75ffa.role.json");
		} catch (SynchronizerException e) {
			assertTrue(e.getMessage().contains(
					"Error loading role 'data-test/roles/c3f62836-575d-4a6c-a2fb-a757cab75ffa.role.json'. Id 'c3f62836-575d-4a6c-a2fb-a757cab75fff' is not similar to filename-uid."));
		}
	}
	
	/** The added item. */
	public IWorkitem addedItem;

	/**
	 * Test copy and save workitem.
	 *
	 * @throws SynchronizerException the synchronizer exception
	 */
	public void testCopyAndSaveWorkitem() throws SynchronizerException {
		IWorkitem wi = synchronizer.get("data-test/roles/c3f62836-575d-4a6c-a2fb-a757cab75fff.role.json");

		wi.setId(UUID.randomUUID().toString(), synchronizer);
		
		@SuppressWarnings("unused")
		SynchronizerResult result = synchronizer.add(wi);
		
		addedItem = wi;
	} 
	
	/**
	 * Test load workitem.
	 *
	 * @throws SynchronizerException the synchronizer exception
	 */
	public void testLoadWorkitem() throws SynchronizerException {
		IWorkitem wi = synchronizer.get("data-test/roles/c3f62836-575d-4a6c-a2fb-a757cab75fff.role.json");
		assertEquals(wi.getId(synchronizer), "c3f62836-575d-4a6c-a2fb-a757cab75fff");
	}

	/**
	 * Test load workitem with wrong param.
	 */
	public void testLoadWorkitemWithWrongParam() {
		try {
			@SuppressWarnings("unused")
			IWorkitem wi = synchronizer.get("data-test/roles/c3f62836-575d-4a6c-a2fb-a757cab75ffx.role.json");
		} catch (SynchronizerException e) {
			assertTrue(e.getMessage().contains("org.rogatio.circlead.model.data.RoleDataitem[\"wrongparameter\"]"));
		}
	}

	/**
	 * Test load non existing workitem.
	 */
	public void testLoadNonExistingWorkitem() {
		try {
			@SuppressWarnings("unused")
			IWorkitem wi = synchronizer.get("data-test/roles/file-not-exists.role.json");
		} catch (SynchronizerException e) {
			assertEquals(e.getMessage(), "Item with id 'data-test/roles/file-not-exists.role.json' could not be loaded with File Synchronizer.");
		}
	}
	
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	public void tearDown() throws Exception {
		super.tearDown();
		((FileSynchronizer) synchronizer).delete(addedItem);
	}
}
