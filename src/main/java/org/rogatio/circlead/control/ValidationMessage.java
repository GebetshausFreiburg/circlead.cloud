/*
 * Circlead - Develop and structure evolutionary Organisations
 * 
 * @author Matthias Wegner
 * @version 0.1
 * @since 01.07.2018
 * 
 */
package org.rogatio.circlead.control;

/**
 * The Class ValidationMessage.
 */
public class ValidationMessage {

	/** The validation object. */
	private Object validationObject;

	/**
	 * The Enum Type.
	 */
	public enum Type {
		/** The info. */
		INFO,
		/** The error. */
		ERROR,
		/** The warning. */
		WARNING,
		/** The debug. */
		DEBUG
	};

	/** The type. */
	private Type type;

	/** The cause. */
	private String cause;

	/** The message. */
	private String message;

	/** The solution. */
	private String solution;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cause == null) ? 0 : cause.hashCode());
		result = prime * result + ((message == null) ? 0 : message.hashCode());
		result = prime * result + ((solution == null) ? 0 : solution.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((validationObject == null) ? 0 : validationObject.hashCode());
		return result;
	}
	
	/**
	 * Sets the solution.
	 *
	 * @param solution the new solution
	 */
	public void setSolution(String solution) {
		this.solution = solution;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ValidationMessage other = (ValidationMessage) obj;
		if (cause == null) {
			if (other.cause != null)
				return false;
		} else if (!cause.equals(other.cause))
			return false;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		if (solution == null) {
			if (other.solution != null)
				return false;
		} else if (!solution.equals(other.solution))
			return false;
		if (type != other.type)
			return false;
		if (validationObject == null) {
			if (other.validationObject != null)
				return false;
		} else if (!validationObject.equals(other.validationObject))
			return false;
		return true;
	}

	/**
	 * Gets the validation object.
	 *
	 * @return the validation object
	 */
	public Object getValidationObject() {
		return validationObject;
	}

	/**
	 * Gets the cause.
	 *
	 * @return the cause
	 */
	public String getCause() {
		return cause;
	}

	/**
	 * Gets the message.
	 *
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Gets the solution.
	 *
	 * @return the solution
	 */
	public String getSolution() {
		return solution;
	}

	/**
	 * Instantiates a new validation message.
	 *
	 * @param validationObject
	 *            the validation object
	 */
	public ValidationMessage(Object validationObject) {
		this.validationObject = validationObject;
	}

	/**
	 * Debug.
	 *
	 * @param message
	 *            the message
	 * @return the validation message
	 */
	public ValidationMessage debug(String message) {
		type = Type.DEBUG;
		this.message = message;
		return this;
	}

	/**
	 * Info.
	 *
	 * @param message
	 *            the message
	 * @return the validation message
	 */
	public ValidationMessage info(String message) {
		type = Type.INFO;
		this.message = message;
		return this;
	}

	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public Type getType() {
		return type;
	}

	/**
	 * Warning.
	 *
	 * @param cause
	 *            the cause
	 * @param message
	 *            the message
	 * @return the validation message
	 */
	public ValidationMessage warning(String cause, String message) {
		type = Type.WARNING;
		this.cause = cause;
		this.message = message;
		return this;
	}

	/**
	 * Warning.
	 *
	 * @param cause
	 *            the cause
	 * @param message
	 *            the message
	 * @param solution
	 *            the solution
	 * @return the validation message
	 */
	public ValidationMessage warning(String cause, String message, String solution) {
		type = Type.WARNING;
		this.cause = cause;
		this.message = message;
		this.solution = solution;
		return this;
	}

	/**
	 * Error.
	 *
	 * @param cause
	 *            the cause
	 * @param message
	 *            the message
	 * @return the validation message
	 */
	public ValidationMessage error(String cause, String message) {
		type = Type.ERROR;
		this.cause = cause;
		this.message = message;
		return this;
	}

	/**
	 * Error.
	 *
	 * @param cause
	 *            the cause
	 * @param message
	 *            the message
	 * @param solution
	 *            the solution
	 * @return the validation message
	 */
	public ValidationMessage error(String cause, String message, String solution) {
		type = Type.ERROR;
		this.cause = cause;
		this.message = message;
		this.solution = solution;
		return this;
	}

}
