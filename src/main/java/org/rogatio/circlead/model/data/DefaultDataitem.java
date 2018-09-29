/*
 * Circlead - Develop and structure evolutionary Organisations
 * 
 * @author Matthias Wegner
 * @version 0.1
 * @since 01.07.2018
 * 
 */
package org.rogatio.circlead.model.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.rogatio.circlead.control.synchronizer.ISynchronizer;
import org.rogatio.circlead.control.synchronizer.SynchronizerFactory;
import org.rogatio.circlead.util.StringUtil;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.kjetland.jackson.jsonSchema.annotations.JsonSchemaDescription;
import com.kjetland.jackson.jsonSchema.annotations.JsonSchemaTitle;

/**
 * The Class DefaultDataitem.
 */
public class DefaultDataitem implements IDataitem {

	/** The Constant logger. */
	@JsonIgnore
	final static Logger LOGGER = LogManager.getLogger(DefaultDataitem.class);

	/** The temp id is not really used. Only needed for initial datamigration to new version of circlead. */
	@JsonIgnore
	@Deprecated
	protected String tempId;

	/**
	 * Gets the temp id.
	 *
	 * @return the temp id
	 */
	@JsonIgnore
	public String getTempId() {
		return tempId;
	}

	/**
	 * Sets the temp id.
	 *
	 * @param tempId
	 *            the new temp id
	 */
	@JsonProperty("id")
	protected void setTempId(String tempId) {
		this.tempId = tempId;
	}

	/** The version. */
	@JsonSchemaTitle("Version")
	@JsonSchemaDescription("Actual version of role")
	protected String version;

	/** The created. */
	@JsonSchemaTitle("Creationdate")
	@JsonSchemaDescription("Date of creation")
	protected Date created;

	/** The modified. */
	@JsonSchemaTitle("Modificationdate")
	@JsonSchemaDescription("Date of modification")
	protected Date modified;

	/** The title. */
	@JsonSchemaTitle("Title")
	@JsonSchemaDescription("Title of the role")
	protected String title;

	/** The status. */
	@JsonSchemaTitle("Status")
	@JsonSchemaDescription("Status of the role. Allowed are draft, active, inactive, deprecated.")
	protected String status = "active";

	/**
	 * Instantiates a new default dataitem.
	 */
	public DefaultDataitem() {
		this.setCreated(new Date());
		this.setModified(new Date());
	}

	/**
	 * Sets the uid. Is a important concept, because for every synchronizer (aka datainterface) is is possible to use a unique id.
	 * But if an interface or end-system needs a given id the dataitem (or workitem) must not be different
	 *
	 * @param uid
	 *            the new uid
	 */
	@JsonProperty("uid")
	public void setUid(String uid) {
		String[] uids = uid.split(",");
		for (String id : uids) {

			String[] idSegments = id.split(":");
			String i = idSegments[0].trim();

			String s = null;
			try {
				s = idSegments[1].trim();
			} catch (ArrayIndexOutOfBoundsException e) {
				ISynchronizer sync = SynchronizerFactory.getInstance().getSynchronizerByPattern(i);
				// If Id not has named IO-Interface (Synchronizer), then use actual one
				if (sync != null) {
					s = sync.toString();
				} else {
					if (StringUtil.isNotNullAndNotEmpty(i)) {
						LOGGER.warn("Synchronizer of ID '" + i + "' not found for workitem '" + uid + "'");
					}
				}
			}

			ISynchronizer sync = SynchronizerFactory.getInstance().getSynchronizer(s);
			this.setId(i, sync);
		}
	}

	/**
	 * Gets the uid.
	 *
	 * @return the uid
	 */
	@JsonIgnore
	public List<String> getUid() {
		List<String> list = new ArrayList<String>(id.keySet());
		return list;
	}

	/** The id. */
	@JsonIgnore
	protected HashMap<String, ISynchronizer> id = new HashMap<String, ISynchronizer>();

	/**
	 * Gets the ids.
	 *
	 * @return the ids
	 */
	@JsonProperty("uid")
	public String getIds() {
		return id.toString().replace("{", "").replace("}", "").replace("=", ":");// list.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.rogatio.circlead.model.data.IDataitem#getId()
	 */
	public HashMap<String, ISynchronizer> getId() {
		return id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.rogatio.circlead.model.data.IDataitem#containsId(java.lang.String)
	 */
	@JsonIgnore
	public boolean containsId(String id) {
		return this.id.containsKey(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.rogatio.circlead.model.data.IDataitem#getId(org.rogatio.circlead.control.synchronizer.ISynchronizer)
	 */
	@JsonIgnore
	public String getId(ISynchronizer synchronizer) {
		Vector<String> keys = new Vector<String>(this.id.keySet());
		for (String key : keys) {
			ISynchronizer sync = id.get(key);
			if (synchronizer.equals(sync)) {
				return key;
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.rogatio.circlead.model.data.IDataitem#removeId(java.lang.String)
	 */
	@JsonIgnore
	public void removeId(String id) {
		this.id.remove(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.rogatio.circlead.model.data.IDataitem#setId(java.lang.String, org.rogatio.circlead.control.synchronizer.ISynchronizer)
	 */
	@JsonIgnore
	public void setId(String id, ISynchronizer synchronizer) {

		if (synchronizer == null) {
			return;
		}

		this.id.put(id, synchronizer);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.rogatio.circlead.model.data.IDataitem#getVersion()
	 */
	public String getVersion() {
		return version;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.rogatio.circlead.model.data.IDataitem#setVersion(java.lang.String)
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 *
	 * @param status
	 *            the new status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Gets the created.
	 *
	 * @return the created
	 */
	public Date getCreated() {
		return created;
	}

	/**
	 * Sets the created.
	 *
	 * @param created
	 *            the new created
	 */
	public void setCreated(Date created) {
		this.created = created;
	}

	/**
	 * Gets the modified.
	 *
	 * @return the modified
	 */
	public Date getModified() {
		return modified;
	}

	/**
	 * Sets the modified.
	 *
	 * @param modified
	 *            the new modified
	 */
	public void setModified(Date modified) {
		this.modified = modified;
	}

	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title.
	 *
	 * @param title
	 *            the new title
	 */
	public void setTitle(String title) {
		this.title = title.trim();
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
		result = prime * result + ((title == null) ? 0 : title.hashCode());
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
		DefaultDataitem other = (DefaultDataitem) obj;

		boolean foundId = false;
		HashMap<String, ISynchronizer> id = this.getId();
		if (id != null) {
			if (id.size() > 0) {
				Vector<String> keys = new Vector<String>(id.keySet());
				for (String key : keys) {
					if (other.getId().containsKey(key)) {
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
	 * @see java.lang.Object#toString()
	 */
	@JsonIgnore
	@Override
	public String toString() {
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.setSerializationInclusion(Include.NON_NULL);
			mapper.enable(SerializationFeature.INDENT_OUTPUT);
			return mapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.rogatio.circlead.model.data.IDataitem#removeId(org.rogatio.circlead.control.synchronizer.ISynchronizer)
	 */
	@Override
	public void removeId(ISynchronizer synchronizer) {
		String id = this.getId(synchronizer);
		this.removeId(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.rogatio.circlead.model.data.IDataitem#setIds(java.util.HashMap)
	 */
	@Override
	public void setIds(HashMap<String, ISynchronizer> ids) {
		this.id = ids;
	}

}