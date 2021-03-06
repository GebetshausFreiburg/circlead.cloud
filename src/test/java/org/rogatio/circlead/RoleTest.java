package org.rogatio.circlead;

import org.rogatio.circlead.model.work.Role;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

// TODO: Auto-generated Javadoc
/**
 * Unit test for simple App.
 */
public class RoleTest extends TestCase {

	/** The role. */
	private Role role;
	
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();

		role = new Role();
	}

	/**
	 * Create the test case.
	 *
	 * @param testName            name of the test case
	 */
	public RoleTest(String testName) {
		super(testName);
	}

	/**
	 * Suite.
	 *
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(RoleTest.class);
	}

	/**
	 * Rigourous Test :-).
	 */
	public void testSomething() {
		assertEquals(role.getType(), "Role");
	}
}
