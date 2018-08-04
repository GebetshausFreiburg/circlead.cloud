/*
 * Circlead - Develop and structure evolutionary Organisations
 * 
 * @author Matthias Wegner
 * @version 0.1
 * @since 01.07.2018
 * 
 */
package org.rogatio.circlead.view;

import java.util.List;

import org.jsoup.nodes.Element;
import org.rogatio.circlead.control.Repository;
import org.rogatio.circlead.control.ValidationMessage;
import org.rogatio.circlead.control.synchronizer.ISynchronizer;

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
		ISynchronizerRenderer renderer = synchronizer.getRenderer();
		Element element = new Element("p");

		List<ValidationMessage> messages = Repository.getInstance().validate();
		
		renderer.addValidationList(element, messages);

		return element;
	}

}
