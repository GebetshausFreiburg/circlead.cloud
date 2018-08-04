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
import org.rogatio.circlead.view.IReport;
import org.rogatio.circlead.view.ISynchronizerRenderer;

/**
 * The Class DefaultSynchronizer.
 */
public class DefaultSynchronizer implements ISynchronizer {

	/** The Constant logger. */
	private final static Logger logger = LogManager.getLogger(DefaultSynchronizer.class);
	
	/**
	 * Instantiates a new default synchronizer.
	 */
	public DefaultSynchronizer() {
		init();
	}
	
	/* (non-Javadoc)
	 * @see org.rogatio.circlead.control.synchronizer.ISynchronizer#init()
	 */
	@Override
	public void init() {
		
	}
	
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
		String DESCRIPTOR = this.getClass().getSimpleName();
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DefaultSynchronizer other = (DefaultSynchronizer) obj;
		if (DESCRIPTOR == null) {
			if (other.getClass().getSimpleName() != null)
				return false;
		} else if (!DESCRIPTOR.equals(other.getClass().getSimpleName()))
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
	public ISynchronizerRenderer getRenderer() {
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

}
