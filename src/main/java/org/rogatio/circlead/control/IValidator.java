/*
 * Circlead - Develop and structure evolutionary Organisations
 * 
 * @author Matthias Wegner
 * @version 0.1
 * @since 01.07.2018
 * 
 */
package org.rogatio.circlead.control;

import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Interface IValidator.
 */
public interface IValidator {
	
	/**
	 * Validate.
	 *
	 * @return the list
	 */
	public abstract List<ValidationMessage> validate();
}
