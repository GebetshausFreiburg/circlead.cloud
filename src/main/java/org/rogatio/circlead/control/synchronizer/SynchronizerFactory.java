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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A factory for creating Synchronizer objects.
 */
public class SynchronizerFactory {

	/** The instance. */
	private static SynchronizerFactory instance;

	/** The synchronizers. */
	public List<ISynchronizer> synchronizers = new ArrayList<ISynchronizer>();
	
	/** The synchronizer map. */
	public Map<String, ISynchronizer> synchronizerMap = new HashMap<String, ISynchronizer>();

	/**
	 * Gets the synchronizers.
	 *
	 * @return the synchronizers
	 */
	public List<ISynchronizer> getSynchronizers() {
		return this.synchronizers;
	}

	/**
	 * Gets the synchronizer by pattern.
	 *
	 * @param id the id
	 * @return the synchronizer by pattern
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

	/** The actual identifier. */
	private String actualIdentifier;

	/**
	 * Sets the actual.
	 *
	 * @param synchronizer the new actual
	 */
	public void setActual(ISynchronizer synchronizer) {
		this.setActual(synchronizer.toString());
	}

	/**
	 * Gets the actual.
	 *
	 * @return the actual
	 */
	public ISynchronizer getActual() {
		return this.getSynchronizer(this.actualIdentifier);
	}

	/**
	 * Sets the actual.
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
	 * Adds the synchronizer.
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
	 * Gets the synchronizer.
	 *
	 * @param synchronizerName the synchronizer name
	 * @return the synchronizer
	 */
	public ISynchronizer getSynchronizer(String synchronizerName) {
		return synchronizerMap.get(synchronizerName);
	}

	// static block initialization for exception handling
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
