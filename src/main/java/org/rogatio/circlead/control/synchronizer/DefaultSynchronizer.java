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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.rogatio.circlead.model.WorkitemType;
import org.rogatio.circlead.model.work.IWorkitem;
import org.rogatio.circlead.view.renderer.ISynchronizerRendererEngine;
import org.rogatio.circlead.view.report.IReport;

/**
 * The Class DefaultSynchronizer.
 * 
 * @author Matthias Wegner
 */
public class DefaultSynchronizer implements ISynchronizer {

	/** The Constant logger. */
	@SuppressWarnings("unused")
	private final static Logger LOGGER = LogManager.getLogger(DefaultSynchronizer.class);
	
	private SynchronizerMode mode = SynchronizerMode.FULL;
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return this.getClass().getSimpleName();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + this.getClass().getSimpleName().hashCode();
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		String descriptor = this.getClass().getSimpleName();
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DefaultSynchronizer other = (DefaultSynchronizer) obj;
		if (descriptor == null) {
			if (other.getClass().getSimpleName() != null)
				return false;
		} else if (!descriptor.equals(other.getClass().getSimpleName()))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see org.rogatio.circlead.control.synchronizer.ISynchronizer#get(java.lang.String)
	 */
	@Override
	public IWorkitem get(String filename) throws SynchronizerException{
		return null;
	}

	/* (non-Javadoc)
	 * @see org.rogatio.circlead.control.synchronizer.ISynchronizer#loadIndex(org.rogatio.circlead.model.WorkitemType)
	 */
	@Override
	public List<String> loadIndex(WorkitemType workitemType) {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.rogatio.circlead.control.synchronizer.ISynchronizer#add(org.rogatio.circlead.model.work.IWorkitem)
	 */
	@Override
	public SynchronizerResult add(IWorkitem workitem) throws SynchronizerException  {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.rogatio.circlead.control.synchronizer.ISynchronizer#update(org.rogatio.circlead.model.work.IWorkitem)
	 */
	@Override
	public SynchronizerResult update(IWorkitem workitem) throws SynchronizerException  {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.rogatio.circlead.control.synchronizer.ISynchronizer#getIdPattern()
	 */
	@Override
	public String getIdPattern() {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.rogatio.circlead.control.synchronizer.ISynchronizer#delete(org.rogatio.circlead.model.work.IWorkitem)
	 */
	@Override
	public String delete(IWorkitem workitem) throws SynchronizerException {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.rogatio.circlead.control.synchronizer.ISynchronizer#getRenderer()
	 */
	@Override
	public ISynchronizerRendererEngine getRenderer() {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.rogatio.circlead.control.synchronizer.ISynchronizer#add(org.rogatio.circlead.view.IReport)
	 */
	@Override
	public SynchronizerResult add(IReport report) throws SynchronizerException {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.rogatio.circlead.control.synchronizer.ISynchronizer#update(org.rogatio.circlead.view.IReport)
	 */
	@Override
	public SynchronizerResult update(IReport report) throws SynchronizerException {
		return null;
	}
	
	/* (non-Javadoc)
	 * @see org.rogatio.circlead.control.synchronizer.ISynchronizer#writeIndex()
	 */
	@Override
	public void writeIndex() {
	}
	
	/* (non-Javadoc)
	 * @see org.rogatio.circlead.control.synchronizer.ISynchronizer#getMode()
	 */
	public SynchronizerMode getMode() {
		return mode;
	}

	/* (non-Javadoc)
	 * @see org.rogatio.circlead.control.synchronizer.ISynchronizer#setMode(org.rogatio.circlead.control.synchronizer.SynchronizerMode)
	 */
	@Override
	public void setMode(SynchronizerMode mode) {
		this.mode = mode;
	}

}
