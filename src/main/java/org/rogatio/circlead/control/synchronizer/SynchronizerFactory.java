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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A factory for holding Synchronizer objects. Is solved as singleton-class, so
 * it can be used from the whole application. Must be initialized before reading
 * or saving data from repository.
 * 
 * @author Matthias Wegner
 */
public final class SynchronizerFactory {

	/** The singelton-instance of the factory. */
	private static SynchronizerFactory instance;

	/** The synchronizers in the factory. */
	public List<ISynchronizer> synchronizers = new ArrayList<ISynchronizer>();

	/**
	 * The synchronizer map holds a redable representation and the synchronizer in a
	 * map.
	 */
	public Map<String, ISynchronizer> synchronizerMap = new HashMap<String, ISynchronizer>();

	/**
	 * Get all synchronizers in the factory.
	 *
	 * @return the synchronizers
	 */
	public List<ISynchronizer> getSynchronizers() {
		return this.synchronizers;
	}

	/**
	 * Gets the synchronizer by id pattern. Uses {@link #getIdPattern()}. Works only
	 * when synchronizers have all different id-patterns.
	 *
	 * @param id the id of a workitem
	 * @return the synchronizer which could handle the id as valid (by pattern)
	 */
	public ISynchronizer getSynchronizerByPattern(String id) {
		for (ISynchronizer synchronizer : synchronizers) {
			String pattern = synchronizer.getIdPattern();
			if (id.matches(pattern)) {
				return synchronizer;
			}
		}
		return null;
	}

	/**
	 * The actual identifier which is used in application.
	 */
	private String actualIdentifier;

	/**
	 * Sets the actual synchronizer. Should be set if synchronizer switches in
	 * application to avoid exception by handling workitem-synchronization with
	 * wrong synchronizer
	 *
	 * @param synchronizer the new actual synchonizer
	 */
	public void setActual(ISynchronizer synchronizer) {
		this.setActual(synchronizer.toString());
	}

	/**
	 * Get the actual identifier which is used in application. Changes if
	 * synchronizer switches in application.
	 *
	 * @return the actual
	 */
	public ISynchronizer getActual() {
		return this.getSynchronizer(this.actualIdentifier);
	}

	/**
	 * Sets the actual synchronizer. Should be set if synchronizer switches in
	 * application to avoid exception by handling workitem-synchronization with
	 * wrong synchronizer
	 *
	 * @param actualIdentifier the new actual
	 */
	public void setActual(String actualIdentifier) {
		this.actualIdentifier = actualIdentifier;
	}

	/**
	 * Instantiates a new synchronizer factory.
	 */
	private SynchronizerFactory() {
	}

	/**
	 * Adds a synchronizer to the factory.
	 *
	 * @param synchronizer the synchronizer
	 */
	public void addSynchronizer(ISynchronizer synchronizer) {
		if (!synchronizers.contains(synchronizer)) {
			synchronizers.add(synchronizer);
			synchronizerMap.put(synchronizer.toString(), synchronizer);
		}
	}

	/**
	 * Gets the synchronizers as collection
	 *
	 * @return the synchronizer
	 */
	public Collection<ISynchronizer> getSynchronizer() {
		return synchronizerMap.values();
	}

	/**
	 * Gets the synchronizer by name. 
	 *
	 * @param synchronizerName the synchronizer name
	 * @return the synchronizer
	 */
	public ISynchronizer getSynchronizer(String synchronizerName) {
		return synchronizerMap.get(synchronizerName);
	}

	/** static block initialization for exception handling */
	static {
		try {
			instance = new SynchronizerFactory();
		} catch (Exception e) {
			throw new RuntimeException("Exception occured in creating singleton instance");
		}
	}

	/**
	 * Gets the single instance of SynchronizerFactory.
	 *
	 * @return single instance of SynchronizerFactory
	 */
	public static SynchronizerFactory getInstance() {
		return instance;
	}

}
