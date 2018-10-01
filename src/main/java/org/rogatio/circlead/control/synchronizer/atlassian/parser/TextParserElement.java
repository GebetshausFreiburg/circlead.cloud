/*
 * Circlead - Develop and structure evolutionary Organisations
 * 
 * @author Matthias Wegner
 * @version 0.1
 * @since 01.07.2018
 * 
 */
package org.rogatio.circlead.control.synchronizer.atlassian.parser;

import org.jsoup.nodes.Element;

/**
 * The Class TextParserElement.
 */
public class TextParserElement implements IParserElement {

	/** The data. */
	private String data;

	/**
	 * Instantiates a new text parser element.
	 *
	 * @param text the text
	 */
	public TextParserElement(Object text) {
		parse(text);
	}
	
	/** The element. */
	private Element element = null;
	
	/**
	 * Gets the element.
	 *
	 * @return the element
	 */
	public Element getElement() {
		return element;
	}

	/**
	 * Parses the.
	 *
	 * @param text the text
	 */
	private void parse(Object text) {
		if (text instanceof Element) {
			data = ((Element) text).text();
			element = (Element)text;
		} else {
			data = text.toString();
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return data;
	}

}
