/*
 * Circlead - Develop and structure evolutionary Organisations
 * 
 * @author Matthias Wegner
 * @version 0.1
 * @since 01.07.2018
 * 
 */
package org.rogatio.circlead.model.work;

import java.util.Date;
import java.util.HashMap;
import java.util.Vector;

import org.rogatio.circlead.control.synchronizer.ISynchronizer;
import org.rogatio.circlead.model.data.IDataitem;
import org.rogatio.circlead.util.StringUtil;

/**
 * The Class DefaultWorkitem.
 */
public class DefaultWorkitem implements IWorkitem {

	/**
	 * Instantiates a new default workitem.
	 */
	public DefaultWorkitem() {
	}

	/** The dataitem is the core-dataelement of the correlated workitem. 
	 * It holds all entitiy-data which could not be calculated and must be set.
	 * */
	protected IDataitem dataitem;

	/**
	 * Instantiates a new default workitem.
	 *
	 * @param dataitem
	 *            the dataitem
	 */
	public DefaultWorkitem(IDataitem dataitem) {
		this.dataitem = dataitem;
	}

	/**
	 * Gets the dataitem.
	 *
	 * @return the dataitem
	 */
	public IDataitem getDataitem() {
		return dataitem;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.rogatio.circlead.model.work.IWorkitem#getType()
	 */
	public String getType() {
		return this.getClass().getSimpleName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return this.dataitem.toString() + ", type=" + getType();// + ", source (" + dataitem.getId() + ")=" + sources.get(dataitem.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.rogatio.circlead.model.work.IWorkitem#getTitle()
	 */
	@Override
	public String getTitle() {
		return dataitem.getTitle();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.rogatio.circlead.model.work.IWorkitem#removeId(java.lang.String)
	 */
	public void removeId(String id) {
		this.getDataitem().removeId(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.rogatio.circlead.model.work.IWorkitem#setTitle(java.lang.String)
	 */
	@Override
	public void setTitle(String title) {
		dataitem.setTitle(title);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.rogatio.circlead.model.work.IWorkitem#getStatus()
	 */
	@Override
	public String getStatus() {
		return dataitem.getStatus();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.rogatio.circlead.model.work.IWorkitem#setStatus(java.lang.String)
	 */
	@Override
	public void setStatus(String status) {
		dataitem.setStatus(status);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.rogatio.circlead.model.work.IWorkitem#setCreated(java.lang.String)
	 */
	@Override
	public void setCreated(String xmlDate) {
		dataitem.setCreated(StringUtil.toDate(xmlDate));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.rogatio.circlead.model.work.IWorkitem#setModified(java.lang.String)
	 */
	@Override
	public void setModified(String xmlDate) {
		dataitem.setModified(StringUtil.toDate(xmlDate));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.rogatio.circlead.model.work.IWorkitem#getModified()
	 */
	@Override
	public Date getModified() {
		return dataitem.getModified();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.rogatio.circlead.model.work.IWorkitem#getCreated()
	 */
	@Override
	public Date getCreated() {
		return dataitem.getCreated();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.rogatio.circlead.model.work.IWorkitem#getVersion()
	 */
	@Override
	public String getVersion() {
		return dataitem.getVersion();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.rogatio.circlead.model.work.IWorkitem#setVersion(java.lang.String)
	 */
	@Override
	public void setVersion(String version) {
		dataitem.setVersion(version);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dataitem == null) ? 0 : dataitem.hashCode());
		return result;
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
		DefaultWorkitem other = (DefaultWorkitem) obj;

		boolean foundId = false;
		HashMap<String, ISynchronizer> id = this.getDataitem().getId();
		if (id != null) {
			if (id.size() > 0) {
				Vector<String> keys = new Vector<String>(id.keySet());
				for (String key : keys) {
					if (other.containsId(key)) {
						foundId = true;
					}
				}
			}
		}

		return foundId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.rogatio.circlead.model.work.IWorkitem#setCreated(java.util.Date)
	 */
	@Override
	public void setCreated(Date date) {
		this.getDataitem().setCreated(date);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.rogatio.circlead.model.work.IWorkitem#setModified(java.util.Date)
	 */
	@Override
	public void setModified(Date date) {
		this.getDataitem().setModified(date);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.rogatio.circlead.model.work.IWorkitem#getId(org.rogatio.circlead.control.synchronizer.ISynchronizer)
	 */
	public String getId(ISynchronizer synchronizer) {
		return this.getDataitem().getId(synchronizer);
	}

	/**
	 * Contains id.
	 *
	 * @param id
	 *            the id
	 * @return true, if successful
	 */
	public boolean containsId(String id) {
		return this.getDataitem().containsId(id);
	}

	/**
	 * Sets the id.
	 *
	 * @param id
	 *            the id
	 */
	public void setId(HashMap<String, ISynchronizer> id) {
		Vector<String> keys = new Vector<String>(id.keySet());
		for (String key : keys) {
			ISynchronizer sync = id.get(key);
			this.setId(key, sync);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.rogatio.circlead.model.work.IWorkitem#setId(java.lang.String, org.rogatio.circlead.control.synchronizer.ISynchronizer)
	 */
	public void setId(String id, ISynchronizer synchronizer) {

		String s = getId(synchronizer);

		if (s != null) {
			this.removeId(s);
		}
		this.getDataitem().setId(id, synchronizer);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.rogatio.circlead.model.work.IWorkitem#getId()
	 */
	@Override
	public HashMap<String, ISynchronizer> getId() {
		return this.getDataitem().getId();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.rogatio.circlead.model.work.IWorkitem#removeId(org.rogatio.circlead.control.synchronizer.ISynchronizer)
	 */
	@Override
	public void removeId(ISynchronizer synchronizer) {
		this.getDataitem().removeId(synchronizer);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.rogatio.circlead.model.work.IWorkitem#setIds(java.util.HashMap)
	 */
	@Override
	public void setIds(HashMap<String, ISynchronizer> ids) {
		this.getDataitem().setIds(ids);
	}

}
