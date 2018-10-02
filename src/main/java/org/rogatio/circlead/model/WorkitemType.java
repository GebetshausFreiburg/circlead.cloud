/*
 * Circlead - Develop and structure evolutionary Organisations
 * 
 * @author Matthias Wegner
 * @version 0.1
 * @since 01.07.2018
 * 
 */
package org.rogatio.circlead.model;

import org.rogatio.circlead.model.work.IWorkitem;

// TODO: Auto-generated Javadoc
/**
 * The Enum WorkitemType is a little helper for building io.logic in the synchronizer. Could also be used by instanceof, but for a easier handling the enum is used
 */
public enum WorkitemType {

	/** The report. */
	REPORT("Report"),
	/** The howto. */
	HOWTO("HowTo"),
	/** The activity. */
	ACTIVITY("Activity"),
	/** The role. */
	ROLE("Role"),
	/** The rolegroup. */
	ROLEGROUP("Rolegroup"),
	TEAM("Team"),
	/** The person. */
	PERSON("Person");

	/** The text. */
	private final String text;

	/**
	 * Instantiates a new workitem type.
	 *
	 * @param text
	 *            the text
	 */
	WorkitemType(final String text) {
		this.text = text;
	}

	/**
	 * Checks if is type of.
	 *
	 * @param workitem
	 *            the workitem
	 * @param workitem2
	 *            the workitem 2
	 * @return true, if is type of
	 */
	public boolean isTypeOf(IWorkitem workitem, IWorkitem workitem2) {
		boolean w1 = isTypeOf(workitem);
		boolean w2 = isTypeOf(workitem2);
		return w1 && w2;
	}

	/**
	 * Checks if is type of.
	 *
	 * @param workitem
	 *            the workitem
	 * @return true, if is type of
	 */
	public boolean isTypeOf(IWorkitem workitem) {
		if (workitem == null) {
			return false;
		}

		if (this.getName().equals(workitem.getType())) {
			return true;
		}
		return false;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return text;
	}
	
	/**
	 * Checks if is equals.
	 *
	 * @param type the type
	 * @return true, if is equals
	 */
	public boolean isEquals(String type) {
		return text.equalsIgnoreCase(type);
	}
	
	/**
	 * Gets the lower name.
	 *
	 * @return the lower name
	 */
	public String getLowerName() {
		return text.toLowerCase();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return text;
	}

}
