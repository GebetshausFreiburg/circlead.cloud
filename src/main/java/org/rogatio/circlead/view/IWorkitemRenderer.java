/*
 * Circlead - Develop and structure evolutionary Organisations
 * 
 * @author Matthias Wegner
 * @version 0.1
 * @since 01.07.2018
 * 
 */
package org.rogatio.circlead.view;

import org.jsoup.nodes.Element;
import org.rogatio.circlead.control.synchronizer.ISynchronizer;

/**
 * The Interface IRenderer allows rendering for all workitems.
 * 
 * @author Matthias Wegner
 */
public interface IWorkitemRenderer {

	/**
	 * Render method.
	 *
	 * @param synchronizer the synchronizer
	 * @return the element
	 */
	public Element render(ISynchronizer synchronizer);
	
}
