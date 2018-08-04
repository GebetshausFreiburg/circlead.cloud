package org.rogatio.circlead.control;

import org.rogatio.circlead.control.synchronizer.AtlassianSynchronizer;
import org.rogatio.circlead.control.synchronizer.FileSynchronizer;
import org.rogatio.circlead.control.synchronizer.SynchronizerException;
import org.rogatio.circlead.control.synchronizer.SynchronizerFactory;
import org.rogatio.circlead.control.synchronizer.SynchronizerResult;
import org.rogatio.circlead.control.synchronizer.atlassian.parser.Parser;
import org.rogatio.circlead.model.work.IWorkitem;

import junit.framework.TestCase;

public class DualSynchronizerTest extends TestCase {

	private FileSynchronizer fsynchronizer;
	private AtlassianSynchronizer asynchronizer;

	protected void setUp() throws Exception {
		super.setUp();
		fsynchronizer = new FileSynchronizer(("data-test"));
		SynchronizerFactory.getInstance().addSynchronizer(fsynchronizer);
		asynchronizer = new AtlassianSynchronizer("CIRCLEADTEST");
		SynchronizerFactory.getInstance().addSynchronizer(asynchronizer);
	}

	private int itemWhichWasTransfered;

	public void testLoadFileAndWriteCloud() throws SynchronizerException {
		IWorkitem wi = fsynchronizer.get("data-test/roles/292573e4-7885-4d2c-be3e-800ff4233abc.role.json");

		SynchronizerResult page = asynchronizer.add(wi);
		int id = Parser.getIdFromPage(page.getContent());
		itemWhichWasTransfered = id;

		IWorkitem wi2 = asynchronizer.get("" + id);

		wi2.setCreated(wi.getCreated());
		wi2.setModified(wi.getModified());

		asynchronizer.update(wi2);

		wi.setVersion("1");
		wi.setId(id + "", asynchronizer);

		assertEquals(wi.toString(), wi2.toString());
	}

	public void tearDown() throws Exception {
		super.tearDown();
		asynchronizer.delete(itemWhichWasTransfered);
	}

}
