/*
 * Circlead - Develop and structure evolutionary Organisations
 * 
 * @author Matthias Wegner
 * @version 0.1
 * @since 01.07.2018
 * 
 */
package org.rogatio.circlead.view.report;

import java.util.List;

import org.jsoup.nodes.Element;
import org.rogatio.circlead.control.synchronizer.ISynchronizer;
import org.rogatio.circlead.control.validator.ValidationMessage;
import org.rogatio.circlead.view.ISynchronizerRendererEngine;

/**
 * The ValidationReport shows all Messages which are thrown from the validation engine, so the consistence of the role-model could be optimized.
 */
public class ValidationReport extends DefaultReport {

	/**
	 * Instantiates a new validation report.
	 */
	public ValidationReport() {
		this.setName("Validation Report");
	}

	/* (non-Javadoc)
	 * @see org.rogatio.circlead.view.DefaultReport#render(org.rogatio.circlead.control.synchronizer.ISynchronizer)
	 */
	@Override
	public Element render(ISynchronizer synchronizer) {
		ISynchronizerRendererEngine renderer = synchronizer.getRenderer();
		Element element = new Element("p");

		List<ValidationMessage> messages = R.validate();
		
		renderer.addValidationList(element, messages);

		return element;
	}

}
