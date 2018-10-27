/*
 * Circlead - Develop and structure evolutionary Organisations
 * 
 * @author Matthias Wegner
 * @version 0.1
 * @since 01.07.2018
 * 
 */
package org.rogatio.circlead.control.synchronizer;

import java.util.List;

import org.rogatio.circlead.model.WorkitemType;
import org.rogatio.circlead.model.work.IWorkitem;
import org.rogatio.circlead.view.ISynchronizerRendererEngine;
import org.rogatio.circlead.view.report.IReport;

/**
 * The Interface of ISynchronizer.
 */
public interface ISynchronizer {
	
	/**
	 * Load index for all workitems which where found by synchronizer of named workitem-type
	 *
	 * @param workitemType the workitem type
	 * @return the list of query results of found items
	 */
	public List<String> loadIndex(WorkitemType workitemType);
	
	/**
	 * Gets the workitem of named id
	 *
	 * @param id the workitem of specific synchronizer. Every synchronizer could have own id-logic
	 * @return the loaded workitem
	 * @throws SynchronizerException the synchronizer exception
	 */
	public IWorkitem get(String id) throws SynchronizerException;
	
	/**
	 * Delete workitem in system which is connected through synchronizer
	 *
	 * @param workitem the workitem to delete
	 * @return the result and response of deletion
	 * @throws SynchronizerException the synchronizer exception
	 */
	public String delete(IWorkitem workitem) throws SynchronizerException ;
	
	/**
	 * Adds workitem to synchronized system
	 *
	 * @param workitem the workitem to add
	 * @return the synchronizer result of adding item
	 * @throws SynchronizerException the synchronizer exception
	 */
	public SynchronizerResult add(IWorkitem workitem) throws SynchronizerException ;
	
	/**
	 * Adds the report to synchronized system
	 *
	 * @param report the report to add
	 * @return the synchronizer result
	 * @throws SynchronizerException the synchronizer exception
	 */
	public SynchronizerResult add(IReport report) throws SynchronizerException;
	
	/**
	 * Update report on synchronized system
	 *
	 * @param report the report to update
	 * @return the synchronizer result for update
	 * @throws SynchronizerException the synchronizer exception
	 */
	public SynchronizerResult update(IReport report) throws SynchronizerException;
	
	/**
	 * Update workitem on synchronized system
	 *
	 * @param workitem the workitem to update
	 * @return the synchronizer result
	 * @throws SynchronizerException the synchronizer exception
	 */
	public SynchronizerResult update(IWorkitem workitem) throws SynchronizerException ;
	
	/**
	 * Gets the pattern for valid id as regular expression which is allowed for synchronized system
	 *
	 * @return the id pattern
	 */
	public String getIdPattern();
	
	/**
	 * Gets the renderer which is used to render workitems and reports for target system.
	 *
	 * @return the renderer for the synchronizer
	 */
	public ISynchronizerRendererEngine getRenderer();
}
