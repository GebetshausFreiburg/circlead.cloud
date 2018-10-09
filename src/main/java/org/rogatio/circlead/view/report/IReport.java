/*
 * Circlead - Develop and structure evolutionary Organisations
 * 
 * @author Matthias Wegner
 * @version 0.1
 * @since 01.07.2018
 * 
 */
package org.rogatio.circlead.view.report;

import java.util.List;

import org.rogatio.circlead.view.IWorkitemRenderer;

/**
 * The Interface IReport.
 */
public interface IReport extends IWorkitemRenderer {

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName();
	
	public List<String> getHead();
	
}
