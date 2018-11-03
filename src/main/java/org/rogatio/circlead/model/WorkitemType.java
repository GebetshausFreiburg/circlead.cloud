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

/**
 * The Enum WorkitemType is a little helper for building io.logic in the
 * synchronizer. Could also be used by instanceof, but for a easier handling the
 * enum is used
 * 
 * @author Matthias Wegner
 */
public enum WorkitemType {

	/** The report-workitem. Is similar to class.getSimpleName() */
	REPORT("Report"),
	/** The howto-workitem. Is similar to class.getSimpleName() */
	HOWTO("HowTo"),
	/** The activity-workitem. Is similar to class.getSimpleName() */
	ACTIVITY("Activity"),
	/** The role-workitem. Is similar to class.getSimpleName() */
	ROLE("Role"),
	/** The rolegroup-workitem. Is similar to class.getSimpleName() */
	ROLEGROUP("Rolegroup"),
	COMPETENCE("Competence"),
	TEAM("Team"),
	/** The person-workitem. Is similar to class.getSimpleName() */
	PERSON("Person");

	/** The simple name of the workitem-class. */
	private final String text;

	/**
	 * Instantiates a new workitem type.
	 *
	 * @param text the simple class-name
	 */
	WorkitemType(final String text) {
		this.text = text;
	}

	/**
	 * Checks if object is type of workitem
	 *
	 * @param workitem  first workitem
	 * @param workitem2 second workitem
	 * @return true, if workitem ist type of enum-name
	 */
	public boolean isTypeOf(IWorkitem workitem, IWorkitem workitem2) {
		boolean w1 = isTypeOf(workitem);
		boolean w2 = isTypeOf(workitem2);
		return w1 && w2;
	}

	/**
	 * Checks if workitem is type enum-name.
	 *
	 * @param workitem the workitem
	 * @return true, if workitem ist type of enum-name
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
