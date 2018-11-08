/*
 * Circlead - Develop and structure evolutionary Organisations
 * 
 * @author Matthias Wegner
 * @version 0.1
 * @since 01.07.2018
 * 
 */
package org.rogatio.circlead.control.synchronizer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.rogatio.circlead.model.WorkitemType;
import org.rogatio.circlead.model.work.IWorkitem;
import org.rogatio.circlead.model.work.Person;
import org.rogatio.circlead.model.work.Role;
import org.rogatio.circlead.model.work.Rolegroup;
import org.rogatio.circlead.util.StringUtil;
import org.rogatio.circlead.view.report.IReport;

/**
 * The Class Connector is used to bind all set synchronizers to the repository
 * 
 * @author Matthias Wegner
 */
public class Connector {

	/** The Constant logger. */
	private final static Logger LOGGER = LogManager.getLogger(Connector.class);

	public Collection<ISynchronizer> getSynchronizer() {
		return SynchronizerFactory.getInstance().getSynchronizer();
	}

	/**
	 * Gets the synchronizer.
	 *
	 * @param synchronizerName the synchronizer name
	 * @return the synchronizer
	 */
	public ISynchronizer getSynchronizer(String synchronizerName) {
		return SynchronizerFactory.getInstance().getSynchronizer(synchronizerName);
	}

	/**
	 * Adds the synchronizer.
	 *
	 * @param synchronizer the synchronizer
	 */
	public void addSynchronizer(ISynchronizer synchronizer) {
		SynchronizerFactory.getInstance().addSynchronizer(synchronizer);
	}

	/**
	 * Sets the actual synchronizer.
	 *
	 * @param synchronizer the new actual synchronizer
	 */
	public void setActualSynchronizer(ISynchronizer synchronizer) {
		SynchronizerFactory.getInstance().setActual(synchronizer);
	}

	/**
	 * Gets the from actual synchronizer.
	 *
	 * @param id the id
	 * @return the from actual synchronizer
	 * @throws SynchronizerException the synchronizer exception
	 */
	public IWorkitem getFromActualSynchronizer(String id) throws SynchronizerException {
		ISynchronizer synchronizer = SynchronizerFactory.getInstance().getActual();
		return synchronizer.get(id);
	}

	/**
	 * Update to actual synchronizer.
	 *
	 * @param workitem the workitem
	 * @return the synchronizer result
	 * @throws SynchronizerException the synchronizer exception
	 */
	public SynchronizerResult updateToActualSynchronizer(IWorkitem workitem) throws SynchronizerException {
		ISynchronizer synchronizer = SynchronizerFactory.getInstance().getActual();
		return synchronizer.update(workitem);
	}

	/**
	 * Adds the to actual synchronizer.
	 *
	 * @param workitem the workitem
	 * @return the synchronizer result
	 * @throws SynchronizerException the synchronizer exception
	 */
	public SynchronizerResult addToActualSynchronizer(IWorkitem workitem) throws SynchronizerException {
		ISynchronizer synchronizer = SynchronizerFactory.getInstance().getActual();
		return synchronizer.add(workitem);
	}

	/**
	 * Load workitems of given type
	 *
	 * @param type the type
	 * @return the list
	 */
	public List<IWorkitem> load(WorkitemType type) {
		List<IWorkitem> workitems = new ArrayList<IWorkitem>();
		List<String> list = loadIndex(type);
		for (String index : list) {
			IWorkitem wi = null;
			wi = get(index);
			if (wi != null) {
				if (!workitems.contains(wi)) {
					workitems.add(wi);
				} else {
					int idx = workitems.indexOf(wi);
					IWorkitem r = workitems.get(idx);

					/* Merge-method ist not finally implemented Not used. */
					@SuppressWarnings("unused")
					IWorkitem merged = merge(r, wi);
				}
			}
		}
		return workitems;
	}

	/**
	 * Merge two workitems to a single workitem. Workitem with newer modified date
	 * overwrites other workitem. Method is lazy programmed. Method not merge all
	 * value and ist not finally tested. Be carefull.
	 *
	 * @param item      the first item
	 * @param otherItem the second item
	 * @return the merged workitem
	 */
	public IWorkitem merge(IWorkitem item, IWorkitem otherItem) {
		Date itemCreated = item.getCreated();
		Date itemModified = item.getModified();
		Date otherItemCreated = otherItem.getCreated();
		Date otherItemModified = otherItem.getModified();
		String itemVersion = item.getVersion();
		String otherItemVersion = otherItem.getVersion();
		HashMap<String, ISynchronizer> itemIds = item.getId();
		HashMap<String, ISynchronizer> otherItemIds = otherItem.getId();

		item.setCreated((Date) null);
		otherItem.setCreated((Date) null);
		item.setModified((Date) null);
		otherItem.setModified((Date) null);
		item.setVersion(null);
		otherItem.setVersion(null);
		for (ISynchronizer is : SynchronizerFactory.getInstance().getSynchronizers()) {
			item.removeId(is);
			otherItem.removeId(is);
		}

		String jsonItem = item.toString();
		String jsonOtherItem = otherItem.toString();

		if (!jsonItem.equals(jsonOtherItem)) {
			item.setCreated(itemCreated);
			otherItem.setCreated(otherItemCreated);
			item.setModified(itemModified);
			otherItem.setModified(otherItemModified);
			item.setVersion(itemVersion);
			otherItem.setVersion(otherItemVersion);

			item.setIds(itemIds);
			otherItem.setIds(otherItemIds);

			if (!jsonItem.equals(jsonOtherItem)) {
				if (item.getModified() != null && otherItem.getModified() != null) {
					if (item.getType().equals(otherItem.getType())) {
						if (WorkitemType.ROLE.isTypeOf(item, otherItem)) {
							Role base = null;
							Role data = null;

							if (item.getModified().before(otherItem.getModified())) {
								base = (Role) item;
								data = (Role) otherItem;
							} else {
								base = (Role) otherItem;
								data = (Role) item;
							}

							base.setId(data.getId());
							base.setSynonyms(data.getSynonyms());
							base.setModified(data.getModified());
							base.setActivities(data.getActivities());
							base.setResponsibilities(data.getResponsibilities());
							base.setGuidelines(data.getGuidelines());
							base.setOpportunities(data.getOpportunities());
							base.setCompetences(data.getCompetences());
							base.setOrganisationIdentifier(data.getOrganisationIdentifier());
							if (StringUtil.isNotNullAndNotEmpty(data.getStatus())) {
								base.setStatus(data.getStatus());
							}
							if (StringUtil.isNotNullAndNotEmpty(data.getTitle())) {
								base.setTitle(data.getTitle());
							}

							if (StringUtil.isNotNullAndNotEmpty(data.getVersion())) {
								base.setVersion(data.getVersion());
							}
							LOGGER.info("Merged: " + base.toString().replace("\n", ""));
							return base;
						}
						if (WorkitemType.ROLEGROUP.isTypeOf(item, otherItem)) {
							Rolegroup base = null;
							Rolegroup data = null;

							if (item.getModified().before(otherItem.getModified())) {
								base = (Rolegroup) item;
								data = (Rolegroup) otherItem;
							} else {
								base = (Rolegroup) otherItem;
								data = (Rolegroup) item;
							}

							base.setId(data.getId());
							base.setModified(data.getModified());
							base.setSummary(data.getSummary());
							base.setLeadIdentifier(data.getLeadIdentifier());

							if (StringUtil.isNotNullAndNotEmpty(data.getStatus())) {
								base.setStatus(data.getStatus());
							}
							if (StringUtil.isNotNullAndNotEmpty(data.getTitle())) {
								base.setTitle(data.getTitle());
							}

							if (StringUtil.isNotNullAndNotEmpty(data.getVersion())) {
								base.setVersion(data.getVersion());
							}
							LOGGER.info("Merged: " + base.toString().replace("\n", ""));
							return base;
						}
						if (WorkitemType.PERSON.isTypeOf(item, otherItem)) {
							Person base = null;
							Person data = null;

							if (item.getModified().before(otherItem.getModified())) {
								base = (Person) item;
								data = (Person) otherItem;
							} else {
								base = (Person) otherItem;
								data = (Person) item;
							}

							base.setId(data.getId());
							base.setModified(data.getModified());
							base.setContacts(data.getContacts());
							base.setFullname(data.getFullname());

							if (StringUtil.isNotNullAndNotEmpty(data.getStatus())) {
								base.setStatus(data.getStatus());
							}
							if (StringUtil.isNotNullAndNotEmpty(data.getTitle())) {
								base.setTitle(data.getTitle());
							}

							if (StringUtil.isNotNullAndNotEmpty(data.getVersion())) {
								base.setVersion(data.getVersion());
							}
							LOGGER.info("Merged: " + base.toString().replace("\n", ""));
							return base;
						}
					}
				}
			}
		}
		return null;
	}

	/**
	 * Adds workitem to synchronized systems
	 *
	 * @param workitem the workitem
	 * @return the list
	 */
	public List<SynchronizerResult> add(IWorkitem workitem) {
		List<SynchronizerResult> results = new ArrayList<SynchronizerResult>();
		List<ISynchronizer> synchronizers = SynchronizerFactory.getInstance().getSynchronizers();
		for (ISynchronizer synchronizer : synchronizers) {
			try {
				SynchronizerResult s = synchronizer.add(workitem);
				if (s != null) {
					results.add(s);
				}
			} catch (SynchronizerException e) {
				LOGGER.error(e);
			}
		}
		return results;
	}

	/**
	 * Adds report to synchronizes systems
	 *
	 * @param report the report
	 * @return the list
	 */
	public List<SynchronizerResult> add(IReport report) {
		List<SynchronizerResult> results = new ArrayList<SynchronizerResult>();
		List<ISynchronizer> synchronizers = SynchronizerFactory.getInstance().getSynchronizers();
		for (ISynchronizer synchronizer : synchronizers) {
			try {
				SynchronizerResult s = synchronizer.add(report);
				if (s != null) {
					results.add(s);
				}
			} catch (SynchronizerException e) {
				LOGGER.error(e);
			}
		}
		return results;
	}

	/**
	 * Update workitem to synchronized system
	 *
	 * @param workitem the workitem
	 * @return the list
	 */
	public List<SynchronizerResult> update(IWorkitem workitem) {
		List<SynchronizerResult> results = new ArrayList<SynchronizerResult>();

		List<ISynchronizer> synchronizers = SynchronizerFactory.getInstance().getSynchronizers();

		/*
		 * It is necessary to add a id to filesynchronizer if it has none. The id must
		 * be set before the atlassian-synhronizer writes the update, so the correlated
		 * file is set with the right uuid
		 */
		for (ISynchronizer synchronizer : synchronizers) {
			if (synchronizer.toString().equals("FileSynchronizer")) {
				if (workitem.getId(synchronizer) == null) {
					workitem.setId(UUID.randomUUID().toString(), synchronizer);
				}
			}
		}

		for (ISynchronizer synchronizer : synchronizers) {
			try {
				results.add(synchronizer.update(workitem));
			} catch (SynchronizerException e) {
				LOGGER.error(e);
			}
		}
		return results;
	}

	public void writeIndex() {
		List<ISynchronizer> synchronizers = SynchronizerFactory.getInstance().getSynchronizers();
		for (ISynchronizer synchronizer : synchronizers) {
			synchronizer.writeIndex();
		}
	}

	/**
	 * Update.
	 *
	 * @param report the report
	 * @return the list
	 */
	public List<SynchronizerResult> update(IReport report) {
		List<SynchronizerResult> results = new ArrayList<SynchronizerResult>();

		List<ISynchronizer> synchronizers = SynchronizerFactory.getInstance().getSynchronizers();
		for (ISynchronizer synchronizer : synchronizers) {
			try {
				results.add(synchronizer.update(report));
			} catch (SynchronizerException e) {
				LOGGER.error(e);
			}
		}
		return results;
	}

	/**
	 * Delete workitem in every synchronized system
	 *
	 * @param workitem the workitem
	 * @return the list
	 */
	public List<String> delete(IWorkitem workitem) {
		List<String> results = new ArrayList<String>();

		List<ISynchronizer> synchronizers = SynchronizerFactory.getInstance().getSynchronizers();
		for (ISynchronizer synchronizer : synchronizers) {
			try {
				results.add(synchronizer.delete(workitem));
			} catch (SynchronizerException e) {
				LOGGER.error(e);
			}
		}
		return results;
	}

	/**
	 * Gets the workitem with given id. Every id must be an entity in all
	 * synchronizers. If id are the same in synchronizers, the this method must be
	 * overwritten
	 *
	 * @param indexId the index id
	 * @return the i workitem
	 */
	public IWorkitem get(String indexId) {
		List<ISynchronizer> synchronizers = SynchronizerFactory.getInstance().getSynchronizers();
		for (ISynchronizer synchronizer : synchronizers) {
			try {
				IWorkitem wi = null;
				wi = synchronizer.get(indexId);
				return wi;
			} catch (SynchronizerException e) {
				LOGGER.error(e);
			}
		}
		return null;
	}

	/**
	 * Load index of workitemtype in all synchronizes systems
	 *
	 * @param workitemType the workitem type
	 * @return the list
	 */
	public List<String> loadIndex(WorkitemType workitemType) {
		List<String> index = new ArrayList<String>();
		List<ISynchronizer> synchronizers = SynchronizerFactory.getInstance().getSynchronizers();
		for (ISynchronizer synchronizer : synchronizers) {
			index.addAll(synchronizer.loadIndex(workitemType));
		}
		return index;
	}

}
