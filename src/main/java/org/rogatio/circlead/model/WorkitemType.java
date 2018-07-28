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
 * The Enum WorkitemType.
 */
public enum WorkitemType {

	/** The role. */
	ROLE("Role"), /** The rolegroup. */
 ROLEGROUP("Rolegroup"), /** The person. */
 PERSON("Person");

	/** The text. */
	private final String text;

	/**
	 * Instantiates a new workitem type.
	 *
	 * @param text the text
	 */
	WorkitemType(final String text) {
		this.text = text;
	}

	/**
	 * Checks if is type of.
	 *
	 * @param workitem the workitem
	 * @param workitem2 the workitem 2
	 * @return true, if is type of
	 */
	public boolean isTypeOf(IWorkitem workitem, IWorkitem workitem2) {
		boolean w1 = isTypeOf(workitem);
		boolean w2 = isTypeOf(workitem2);
		return w1&&w2;
	}
	
	/**
	 * Checks if is type of.
	 *
	 * @param workitem the workitem
	 * @return true, if is type of
	 */
	public boolean isTypeOf(IWorkitem workitem) {
		if (workitem==null) {
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
