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
 * The Interface ISynchronizer.
 */
public interface ISynchronizer {

	/**
	 * Inits the.
	 */
	public void init();
	
	/**
	 * Load index.
	 *
	 * @param workitemType the workitem type
	 * @return the list
	 */
	public List<String> loadIndex(WorkitemType workitemType);
	
	/**
	 * Gets the.
	 *
	 * @param id the id
	 * @return the i workitem
	 * @throws SynchronizerException the synchronizer exception
	 */
	public IWorkitem get(String id) throws SynchronizerException;
	
	/**
	 * Delete.
	 *
	 * @param workitem the workitem
	 * @return the string
	 * @throws SynchronizerException the synchronizer exception
	 */
	public String delete(IWorkitem workitem) throws SynchronizerException ;
	
	/**
	 * Adds the.
	 *
	 * @param workitem the workitem
	 * @return the synchronizer result
	 * @throws SynchronizerException the synchronizer exception
	 */
	public SynchronizerResult add(IWorkitem workitem) throws SynchronizerException ;
	
	/**
	 * Adds the.
	 *
	 * @param report the report
	 * @return the synchronizer result
	 * @throws SynchronizerException the synchronizer exception
	 */
	public SynchronizerResult add(IReport report) throws SynchronizerException;
	
	/**
	 * Update.
	 *
	 * @param report the report
	 * @return the synchronizer result
	 * @throws SynchronizerException the synchronizer exception
	 */
	public SynchronizerResult update(IReport report) throws SynchronizerException;
	
	/**
	 * Update.
	 *
	 * @param workitem the workitem
	 * @return the synchronizer result
	 * @throws SynchronizerException the synchronizer exception
	 */
	public SynchronizerResult update(IWorkitem workitem) throws SynchronizerException ;
	
	/**
	 * Gets the id pattern.
	 *
	 * @return the id pattern
	 */
	public String getIdPattern();
	
	/**
	 * Gets the renderer.
	 *
	 * @return the renderer
	 */
	public ISynchronizerRendererEngine getRenderer();
}
