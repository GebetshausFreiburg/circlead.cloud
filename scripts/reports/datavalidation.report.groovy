name = "Validation Report"
description = "Bericht über invalide Daten des Rollenmodells."

import java.util.List;
import org.jsoup.nodes.Element;
import org.rogatio.circlead.control.synchronizer.ISynchronizer;
import org.rogatio.circlead.control.validator.ValidationMessage;
import org.rogatio.circlead.view.renderer.ISynchronizerRendererEngine;

ISynchronizerRendererEngine renderer = synchronizer.getRenderer();
Element element = new Element("p");

List<ValidationMessage> messages = R.validate();
		
renderer.addValidationList(element, messages);

return element
