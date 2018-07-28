package org.rogatio.circlead.control.synchronizer.atlassian.parser;

import org.jsoup.nodes.Element;

public class TextParserElement implements IParserElement {

	private String data;

	public TextParserElement(Object text) {
		parse(text);
	}
	
	private Element element = null;
	
	public Element getElement() {
		return element;
	}

	private void parse(Object text) {
		if (text instanceof Element) {
			data = ((Element) text).text();
			element = (Element)text;
		} else {
			data = text.toString();
		}
	}

	public String toString() {
		return data;
	}

}
