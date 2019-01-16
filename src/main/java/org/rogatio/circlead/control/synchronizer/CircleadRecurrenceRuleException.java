/*
 * Circlead - Develop and structure evolutionary Organisations
 * 
 * @author Matthias Wegner
 * @version 0.1
 * @since 01.07.2018
 * 
 */
package org.rogatio.circlead.control.synchronizer;

/**
 * The Class SynchronizerException.
 * 
 * @author Matthias Wegner
 */
public class CircleadRecurrenceRuleException extends Exception {
	
	/**
	 * Serial UID
	 */
	private static final long serialVersionUID = -6293615985786728044L;

	/**
	 * Instantiates a new synchronizer exception.
	 */
	public CircleadRecurrenceRuleException() {
		super();
	}

	/**
	 * Instantiates a new synchronizer exception.
	 *
	 * @param message the message
	 */
	public CircleadRecurrenceRuleException(String message) {
		super(message);
	}

	/**
	 * Instantiates a new synchronizer exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 */
	public CircleadRecurrenceRuleException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Instantiates a new synchronizer exception.
	 *
	 * @param cause the cause
	 */
	public CircleadRecurrenceRuleException(Throwable cause) {
		super(cause);
	}

}
