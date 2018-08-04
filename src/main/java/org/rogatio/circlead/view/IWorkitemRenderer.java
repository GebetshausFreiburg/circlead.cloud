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
 * The Interface IRenderer.
 */
public interface IWorkitemRenderer {

	/**
	 * Render.
	 *
	 * @return the element
	 */
	public Element render(ISynchronizer synchronizer);
	
}