/*
 * Circlead - Develop and structure evolutionary Organisations
 * 
 * @author Matthias Wegner
 * @version 0.1
 * @since 01.07.2018
 * 
 */
package org.rogatio.circlead.control.validator;

import java.util.List;

/**
 * The Interface IValidator allows data-validation
 */
public interface IValidator {
	
	/**
	 * Validation method
	 *
	 * @return the list
	 */
	public abstract List<ValidationMessage> validate();
}
