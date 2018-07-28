package org.rogatio.circlead.control.synchronizer.atlassian.parser;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class StatusParserElement implements IParserElement {

	private String status;

	public StatusParserElement(Object text) {
		parse(text);
	}

	private void parse(Object text) {
		if (text instanceof Element) {
			Element e = (Element) text;

			Elements tag = e.getElementsByAttributeValue("ac:name", "title");

//			System.out.println("T" + tag.toString());

			if (tag.size() > 0) {
//				System.out.println("D " + e.text());
				status = tag.text().trim();
			} else {
//				System.out.println("E " + e.text());
				status = e.text();
			}
		} else {
			status = text.toString().trim();
		}
	}

	public String toString() {
		return status;
	}

}
