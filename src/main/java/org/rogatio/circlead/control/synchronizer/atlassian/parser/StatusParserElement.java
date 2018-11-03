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
import org.jsoup.select.Elements;

/**
 * The Class StatusParserElement.
 * 
 * @author Matthias Wegner
 */
public class StatusParserElement implements IParserElement {

	/** The status. */
	private String status;

	/**
	 * Instantiates a new status parser element.
	 *
	 * @param text the text
	 */
	public StatusParserElement(Object text) {
		parse(text);
	}

	/**
	 * Parses the.
	 *
	 * @param text the text
	 */
	private void parse(Object text) {
		if (text instanceof Element) {
			Element e = (Element) text;

			Elements tag = e.getElementsByAttributeValue("ac:name", "title");

			if (tag.size() > 0) {
				status = tag.text().trim();
			} else {
				status = e.text();
			}
		} else {
			status = text.toString().trim();
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return status;
	}

}
