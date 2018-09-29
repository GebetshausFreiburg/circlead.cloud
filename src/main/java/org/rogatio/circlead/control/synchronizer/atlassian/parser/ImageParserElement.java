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
 * The Class ImageParserElement.
 */
public class ImageParserElement implements IParserElement {

	/**
	 * Instantiates a new image parser element.
	 *
	 * @param text the text
	 */
	public ImageParserElement(Object text) {
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

	/** The data. */
	private String data = null;

	/**
	 * Parses the.
	 *
	 * @param imgElement the img element
	 */
	private void parse(Object imgElement) {
		try {
			if (imgElement instanceof Element) {
				element = (Element) imgElement;

				Elements elements = element.getElementsByAttribute("ri:filename");
				Element e = elements.get(0);
				data = e.attr("ri:filename");
				
				if (data!=null) {
					if (data.equals("null")) {
						data = null;
					}
				}
			}
		} catch (Exception e) {

		}

		// <ac:image ac:height="250"><ri:attachment ri:filename="6125986.jpeg"
		// ri:version-at-save="1" /></ac:image>

//		System.out.println(text.toString());
//			data = text.toString();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return data;
	}

}
