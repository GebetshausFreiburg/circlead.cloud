/*
 * Circlead - Develop and structure evolutionary Organisations
 * 
 * @author Matthias Wegner
 * @version 0.1
 * @since 01.07.2018
 * 
 */
package org.rogatio.circlead.control.synchronizer.atlassian.parser;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.rogatio.circlead.util.ObjectUtil;
import org.rogatio.circlead.util.StringUtil;

/**
 * The Class ListParserElement.
 * 
 * @author Matthias Wegner
 */
public class ListParserElement implements IParserElement {

	/** The list. */
	private List<String> list = new ArrayList<>();

	/**
	 * Instantiates a new list parser element.
	 *
	 * @param text the text
	 */
	public ListParserElement(Object text) {
		parse(text);
	}

	/**
	 * Checks if is situational.
	 *
	 * @return true, if is situational
	 */
	public boolean isSituational() {
		if (ObjectUtil.isListNotNullAndEmpty(list)) {
			if (list.size()==1) {
				if (list.get(0).equalsIgnoreCase("Situativ")||list.get(0).equalsIgnoreCase("Situative Besetzung")||list.get(0).equalsIgnoreCase("Situativ Besetzt")) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Gets the list.
	 *
	 * @return the list
	 */
	public List<String> getList() {
		return list;
	}

	/**
	 * Adds the items.
	 *
	 * @param elements the elements
	 */
	private void addItems(Elements elements) {
		if (elements.size() > 0) {
			Elements liElements = elements.get(0).getElementsByTag("li");
			if (liElements.size() > 0) {
				for (Element element : liElements) {
					list.add(element.text());
				}

			}
		}
	}

	/**
	 * Contains list elements.
	 *
	 * @param element the element
	 * @return true, if successful
	 */
	private boolean containsListElements(Element element) {
		Elements elements = element.getElementsByTag("li");
		return elements.size() > 0;
	}

	/**
	 * Parses the.
	 *
	 * @param text the text
	 */
	private void parse(Object text) {
		if (text instanceof Element) {
			Element e = (Element) text;

			if (containsListElements(e)) {
				if (e.children().size() > 0) {
					addItems(e.children());
				} else {
					list.add(e.text());
				}
			} else {
				e.removeAttr("colspan");
				String econtent = e.toString().replace("<br>", "").replace("<td>", "").replace("</td>", "");
				String[] values = econtent.split(",");

				for (int i = 0; i < values.length; i++) {
					String x = values[i].trim();
					if (StringUtil.isNotNullAndNotEmpty(x)) {
						if (x.contains("<ac:link>")) {
							Document doc = Jsoup.parse(x);
							String s = doc.getElementsByAttribute("ri:content-title").get(0).attr("ri:content-title");
							list.add(s);
						} else {
							list.add(x);
						}
					}

				}
			}
		} else if (text instanceof Elements) {
			addItems((Elements) text);
		} else {
			list.add(text.toString());
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return list.toString();
	}

}
