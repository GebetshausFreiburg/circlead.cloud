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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.rogatio.circlead.model.data.ActivityDataitem;
import org.rogatio.circlead.model.work.Activity;

/**
 * The Class HeadTableParserElement.
 */
public class TeamTableParserElement implements IParserElement {

	/**
	 * Instantiates a new head table parser element.
	 *
	 * @param text the text
	 */
	public TeamTableParserElement(Object text) {
		parse(text);
	}

	private List<Map<String, String>> dataList = new ArrayList<Map<String, String>>();
	
	public List<Map<String, String>> getDataList() {
		return dataList;
	}

	/**
	 * Parses the.
	 *
	 * @param text the text
	 */
	private void parse(Object text) {

		if (text instanceof Element) {
			Map<Integer, String> header = new HashMap<Integer, String>();

			Element element = (Element) text;
			Elements tables = element.getElementsByTag("tbody");
			if (tables.size() > 0) {
				Element table = tables.get(0);

				int columnCounter = 1;
				for (Element th : table.getElementsByTag("th")) {
					header.put(columnCounter, th.text().trim());
					columnCounter++;
				}
				
				for (Element row : table.getElementsByTag("tr")) {
					Map<String, String> data = new HashMap<String, String>();

					columnCounter = 1;
					for (Element td : row.getElementsByTag("td")) {
						String key = header.get(columnCounter);
						String value = td.text();
						data.put(key, value);
						columnCounter++;
					}
					
					dataList.add(data);
				}
			}
		}
	}

}
