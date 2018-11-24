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

import org.rogatio.circlead.view.renderer.IWorkitemRenderer;

/**
 * The Interface IReport.
 * 
 * @author Matthias Wegner
 */
public interface IReport extends IWorkitemRenderer {

	/**
	 * Gets the name of the report.
	 *
	 * @return the name of the report
	 */
	public String getName();
	
	/**
	 * Gets the description of the report.
	 *
	 * @return the description of the report
	 */
	public String getDescription();
	
	/**
	 * Gets the head of html-page.
	 *
	 * @return the head of the page
	 */
	public List<String> getHead();
	
}
