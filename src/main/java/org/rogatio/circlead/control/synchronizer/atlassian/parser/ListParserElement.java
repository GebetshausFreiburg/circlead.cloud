package org.rogatio.circlead.control.synchronizer.atlassian.parser;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.rogatio.circlead.util.StringUtil;

public class ListParserElement implements IParserElement {

	List<String> list = new ArrayList<>();

	public ListParserElement(Object text) {
		parse(text);
	}

	public List<String> getList() {
		return list;
	}

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

	private boolean containsListElements(Element element) {
		Elements elements = element.getElementsByTag("li");
		return elements.size() > 0;
	}

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

	public String toString() {
		return list.toString();
	}

}
