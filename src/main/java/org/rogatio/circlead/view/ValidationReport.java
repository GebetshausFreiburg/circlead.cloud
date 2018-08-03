package org.rogatio.circlead.view;

import java.util.List;

import org.jsoup.nodes.Element;
import org.rogatio.circlead.control.Repository;
import org.rogatio.circlead.control.ValidationMessage;
import org.rogatio.circlead.control.synchronizer.ISynchronizer;

public class ValidationReport extends DefaultReport {

	public ValidationReport() {
		this.setName("Validation Report");
	}

	@Override
	public Element render(ISynchronizer synchronizer) {
		ISynchronizerRenderer renderer = synchronizer.getRenderer();
		Element element = new Element("p");

		List<ValidationMessage> messages = Repository.getInstance().validate();
		
		renderer.addValidationList(element, messages);

		return element;
	}

}
