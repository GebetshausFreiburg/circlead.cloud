/*
 * Circlead - Develop and structure evolutionary Organisations
 * 
 * @author Matthias Wegner
 * @version 0.1
 * @since 01.07.2018
 * 
 */
package org.rogatio.circlead.view.renderer;

import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.dmfs.rfc5545.recur.Freq;
import org.jsoup.nodes.Element;
import org.rogatio.circlead.control.Repository;
import org.rogatio.circlead.control.synchronizer.ISynchronizer;
import org.rogatio.circlead.control.validator.ValidationMessage;
//import org.rogatio.circlead.control.synchronizer.atlassian.parser.Parser;
import org.rogatio.circlead.model.WorkitemStatusParameter;
import org.rogatio.circlead.model.data.ActivityDataitem;
import org.rogatio.circlead.model.data.HowTo;
import org.rogatio.circlead.model.data.Timeslice;
import org.rogatio.circlead.model.work.Activity;
import org.rogatio.circlead.model.work.IWorkitem;
import org.rogatio.circlead.model.work.Person;
import org.rogatio.circlead.model.work.Role;
import org.rogatio.circlead.model.work.Rolegroup;
import org.rogatio.circlead.model.work.Team;
import org.rogatio.circlead.util.ObjectUtil;
import org.rogatio.circlead.util.StringUtil;
import org.rogatio.circlead.view.ColorPalette;
import org.rogatio.circlead.view.report.IReport;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * The Class FileRenderEngine supports rendering of data to flat-file
 * html-representation.
 *
 * @author Matthias Wegner
 */
public class FileRendererEngine implements ISynchronizerRendererEngine {

	/** The synchronizer. */
	private ISynchronizer synchronizer;

	/**
	 * Instantiates a new file renderer.
	 *
	 * @param synchronizer the synchronizer
	 */
	public FileRendererEngine(ISynchronizer synchronizer) {
		this.synchronizer = synchronizer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.rogatio.circlead.view.ISynchronizerRenderer#getSynchronizer()
	 */
	public ISynchronizer getSynchronizer() {
		return synchronizer;
	}

	/**
	 * Adds the chart.
	 *
	 * @param element the element
	 * @param colors  the colors
	 * @param dataMap the data map
	 */
	private void addChart(Element element, String colors, Map<String, List<Timeslice>> dataMap) {

		ArrayList<String> keys = new ArrayList<String>(dataMap.keySet());

		if (dataMap.keySet().size() == 0) {
			return;
		}

		ArrayList<ChartData> data = new ArrayList<ChartData>();

		for (String key : keys) {
			ChartData d = new ChartData();
			d.setKey(key);
			List<Timeslice> dataset = dataMap.get(key);
			for (Timeslice ts : dataset) {
				int uv = ts.getUnitValue();
				int v = (int) ts.getAllokation();
				d.addValue("" + uv, v);
			}
			data.add(d);
		}

		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_NULL);
		mapper.enable(SerializationFeature.INDENT_OUTPUT);

		colors = colors.replace("#", "'#").replace(",", "',") + "'";

		try {
			String json = mapper.writeValueAsString(data);
			element.append(chartCode(colors, json));
		} catch (JsonProcessingException e) {
		}

	}

	/**
	 * Chart code.
	 *
	 * @param colors the colors
	 * @param data   the data
	 * @return the string
	 */
	private String chartCode(String colors, String data) {
		return " <link href=\"javascript/nvd3/v1.8.6/build/nv.d3.css\" rel=\"stylesheet\" type=\"text/css\">\n"
				+ "<script src=\"javascript/d3/v3.5.17/d3.min.js\" charset=\"utf-8\"></script>\n"
				+ "    <script src=\"javascript/nvd3/v1.8.6/build/nv.d3.js\"></script>\n" + "   \n" + "    <style>\n" +
//				"        text {\n" + 
//				"            font: 12px sans-serif;\n" + 
//				"        }\n" + 
//				"        svg {\n" + 
//				"            display: block;\n" + 
//				"        }\n" + 
				"        #chart {\n" + "            margin: 0px;\n" + "            padding: 0px;\n"
				+ "            height: 100%;\n" + "            width: 100%;\n" + "        }\n" + "    </style>\n" + "\n"
				+ "<div id=\"chart\">\n" + "    <svg></svg>\n" + "</div>\n" + "\n" + "<script>\n" + "\n"
				+ "  var colors = [" + colors + "];\n" + "\n" + "  var data = " + data + "\n" + "    nv.addGraph({\n"
				+ "        generate: function() {\n" + "            var width = nv.utils.windowSize().width,\n"
				+ "                height = nv.utils.windowSize().height;\n" + "\n"
				+ "            var chart = nv.models.multiBarChart()\n" + "                .width(width)\n"
				+ "                .height(height)\n" + "                .stacked(true)\n"
				+ "                .color(colors);\n" + "\n"
				+ "            chart.dispatch.on('renderEnd', function(){\n"
				+ "                console.log('Render Complete');\n" + "            });\n" + "\n"
				+ "            var svg = d3.select('#chart svg').datum(data);\n" + "\n"
				+ "            console.log('calling chart');\n"
				+ "            svg.transition().duration(0).call(chart);\n" + "\n" + "            return chart;\n"
				+ "        },\n" + "        callback: function(graph) {\n"
				+ "            nv.utils.windowResize(function() {\n"
				+ "                var width = nv.utils.windowSize().width;\n"
				+ "                var height = nv.utils.windowSize().height;\n"
				+ "                graph.width(width).height(height);\n" + "\n"
				+ "                d3.select('#chart svg')\n" + "                    .attr('width', width)\n"
				+ "                    .attr('height', height)\n" + "                    .transition().duration(0)\n"
				+ "                    .call(graph);\n" + "\n" + "            });\n" + "        }\n" + "    });\n"
				+ "\n" + "</script>\n" + "</body>\n" + "</html>";
	}

	/**
	 * The Class ChartData.
	 */
	@SuppressWarnings("unused")
	private class ChartData {

		/** The key. */
		private String key;

		/** The x. */
		private String x;

		/** The y. */
		private double y;

		/**
		 * The Class P.
		 */
		private class P {

			/**
			 * Instantiates a new p.
			 *
			 * @param x the x
			 * @param y the y
			 */
			public P(String x, double y) {
				this.x = x;
				this.y = y;
			}

			/**
			 * Gets the x.
			 *
			 * @return the x
			 */
			public String getX() {
				return x;
			}

			/**
			 * Sets the x.
			 *
			 * @param x the new x
			 */
			public void setX(String x) {
				this.x = x;
			}

			/**
			 * Gets the y.
			 *
			 * @return the y
			 */
			public double getY() {
				return y;
			}

			/**
			 * Sets the y.
			 *
			 * @param y the new y
			 */
			public void setY(double y) {
				this.y = y;
			}

		}

		/** The values. */
		private List<P> values = new ArrayList<P>();

		/**
		 * Gets the key.
		 *
		 * @return the key
		 */
		public String getKey() {
			return key;
		}

		/**
		 * Adds the value.
		 *
		 * @param x the x
		 * @param y the y
		 */
		@JsonIgnore
		public void addValue(String x, double y) {
			values.add(new P(x, y));
		}

		/**
		 * Sets the key.
		 *
		 * @param key the new key
		 */
		public void setKey(String key) {
			this.key = key;
		}

		/**
		 * Gets the values.
		 *
		 * @return the values
		 */
		public List<P> getValues() {
			return values;
		}

		/**
		 * Sets the values.
		 *
		 * @param values the new values
		 */
		public void setValues(List<P> values) {
			this.values = values;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.rogatio.circlead.view.ISynchronizerRenderer#addActivityList(org.jsoup.
	 * nodes.Element, java.util.List)
	 */
	public void addActivityList(Element element, List<Activity> list) {
		if (list != null) {
			if (list.size() > 0) {
				Element ul = element.appendElement("div").appendElement("ul");
				for (Activity activity : list) {
					Element li = ul.appendElement("li");
					li.appendElement("a").attr("href", "" + activity.getId(synchronizer) + ".html")
							.appendText(activity.getTitle());
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.rogatio.circlead.view.ISynchronizerRendererEngine#addWorkitemTable(org.
	 * jsoup.nodes.Element, java.util.List)
	 */
	public void addWorkitemTable(Element element, List<IWorkitem> workitem) {
		if (ObjectUtil.isListNotNullAndEmpty(workitem)) {
			Element table = element.appendElement("div").appendElement("table");

			Element tr = table.appendElement("tr");
			tr.appendElement("th").appendText("Title");
			tr.appendElement("th").appendText("Typ");
			tr.appendElement("th").appendText("Status");

			for (IWorkitem w : workitem) {
				tr = table.appendElement("tr");
				tr.appendElement("td").appendElement("a").attr("href", "" + w.getId(synchronizer) + ".html")
						.appendText(w.getTitle());
				;
				tr.appendElement("td").appendText(w.getType());
				Element td = tr.appendElement("td");
				addStatus(td, w.getStatus());
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.rogatio.circlead.view.ISynchronizerRenderer#addRolegroupList(org.jsoup.
	 * nodes.Element, java.util.List)
	 */
	public void addRolegroupList(Element element, List<Rolegroup> list) {
		if (list != null) {
			if (list.size() > 0) {
				Element ul = element.appendElement("div").appendElement("ul");
				for (Rolegroup rolegroup : list) {
					Element li = ul.appendElement("li");
					li.appendElement("a").attr("href", "" + rolegroup.getId(synchronizer) + ".html")
							.appendText(rolegroup.getTitle());
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.rogatio.circlead.view.ISynchronizerRendererEngine#addSubActivityList(org.
	 * jsoup.nodes.Element, java.util.List,
	 * org.rogatio.circlead.model.work.Activity,
	 * org.rogatio.circlead.model.work.Role)
	 */
	public void addSubActivityList(Element element, List<ActivityDataitem> list, Activity activity, Role role) {
		List<Activity> a = Repository.getInstance().getActivities(role.getTitle());

		if (ObjectUtil.isListNotNullAndEmpty(list)) {
			// Open html-list
			Element ul = element.appendElement("div").appendElement("ul");
			for (ActivityDataitem activitydataitem : list) {
				// Create html-List-item
				Element li = ul.appendElement("li");

				// Add subactivity-title to list with valid link

				boolean found = false;
				for (Activity act : a) {
					if (act.getTitle().equals(activitydataitem.getTitle())) {
						found = true;
					}
				}

				if (found) {
					for (Activity act : a) {
						if (act.getTitle().equals(activitydataitem.getTitle())) {
							li.appendText("").appendElement("a")
									.attr("href", "" + activitydataitem.getId(synchronizer) + ".html")
									.appendText(activitydataitem.getTitle());
							li.appendText("");
						}
					}
				} else {
					li.appendText(activitydataitem.getTitle());
				}

				li.appendText(" (").appendElement("a").attr("href", "" + activity.getId(synchronizer) + ".html")
						.appendText(activity.getTitle());
				li.appendText(")");

			}
		}
	}

	/**
	 * Adds the role list.
	 *
	 * @param element the element
	 * @param list    the list
	 */
	public void addRoleList(Element element, List<Role> list) {
		if (list != null) {
			if (list.size() > 0) {
				Element ul = element.appendElement("div").appendElement("ul");
				for (Role role : list) {
					Element li = ul.appendElement("li");
					@SuppressWarnings("unused")
					Role r = Repository.getInstance().getRole(role.getTitle());
					li.appendElement("a").attr("href", "" + role.getId(synchronizer) + ".html")
							.appendText(role.getTitle());
				}
			}
		}
	}

	/**
	 * Adds the role list.
	 *
	 * @param element the element
	 * @param list    the list
	 * @param person  the person
	 */
	public void addRoleList(Element element, List<Role> list, Person person) {
		if (list != null) {
			if (list.size() > 0) {
				Element ul = element.appendElement("div").appendElement("ul");
				for (Role role : list) {
					Element li = ul.appendElement("li");
					Role r = Repository.getInstance().getRole(role.getTitle());
					if (r != null) {
						li.appendElement("a").attr("href", "" + role.getId(synchronizer) + ".html")
								.appendText(role.getTitle());
					} else {
						li.appendText(role.getTitle());
					}

					if (role.getDataitem().hasRepresentation(person.getFullname())) {
						String representation = role.getDataitem().getRepresentation(person.getFullname());
						WorkitemStatusParameter status = WorkitemStatusParameter.get(representation);
						if (status != null) {
							li.append("&nbsp;").appendElement("div").attr("id", "status" + status.getColor())
									.appendText(status.getName());
						}
					}
					if (role.getDataitem().hasSkill(person.getFullname())) {
						String skill = role.getDataitem().getSkill(person.getFullname());
						WorkitemStatusParameter status = WorkitemStatusParameter.get(skill + "%");
						if (status != null) {
							li.append("&nbsp;").appendElement("div").attr("id", "status" + status.getColor())
									.appendText(status.getName());
						}
					}
					if (role.getDataitem().hasRecurrenceRule(person.getFullname())) {
						String rule = role.getDataitem().getRecurrenceRule(person.getFullname());
						li.append("&nbsp;<span style=\"color: rgb(192,192,192);\">"
								+ rule.replace("R=", "").replace("RRULE=", "") + "</span>");
					}
					if (role.getDataitem().hasComment(person.getFullname())) {
						String comment = role.getDataitem().getComment(person.getFullname());
						li.append("&nbsp;|&nbsp;<span style=\"color: rgb(192,192,192);\">" + comment + "</span>");
					}
				}
			}
		}
	}

	/**
	 * Adds the data pair.
	 *
	 * @param key   the key
	 * @param value the value
	 * @param table the table
	 */
	private void addDataPair(String key, String value, Element table) {
		if (value != null) {
			Element tr = table.appendElement("tr");
			tr.appendElement("th").appendText(key.trim());
			tr.appendElement("td").appendText(value.trim());
		}
	}

	/**
	 * Adds the table.
	 *
	 * @param element the element
	 * @param map     the map
	 */
	public void addTable(Element element, Map<String, String> map) {
		if (map != null) {
			if (map.size() > 0) {
				Element table = element.appendElement("div").appendElement("table");
				for (String key : map.keySet()) {
					String value = map.get(key);
					addDataPair(key, value, table);
				}
			}
		}
	}

	/**
	 * Adds the person list.
	 *
	 * @param element the element
	 * @param list    the list
	 * @param role    the role
	 */
	public void addPersonList(Element element, List<String> list, Role role) {
		Element ul = element.appendElement("div").appendElement("ul");
		for (String identifier : list) {
			Person person = Repository.getInstance().getPerson(identifier);
			Element li = ul.appendElement("li");
			if (person != null) {
				li.appendElement("a").attr("href", "" + person.getId(synchronizer) + ".html")
						.appendText(person.getTitle());
			} else {
				li.appendText(identifier);
			}
			if (role.getDataitem().hasRepresentation(identifier)) {
				String representation = role.getDataitem().getRepresentation(identifier);
				WorkitemStatusParameter status = WorkitemStatusParameter.get(representation);
				if (status != null) {
					li.append("&nbsp;").appendElement("div").attr("id", "status" + status.getColor())
							.appendText(status.getName());
				}
			}
			if (role.getDataitem().hasSkill(identifier)) {
				String skill = role.getDataitem().getSkill(identifier);
				WorkitemStatusParameter status = WorkitemStatusParameter.get(skill + "%");
				if (status != null) {
					li.append("&nbsp;").appendElement("div").attr("id", "status" + status.getColor())
							.appendText(status.getName());
				}
			}
			if (role.getDataitem().hasRecurrenceRule(identifier)) {
				String rule = role.getDataitem().getRecurrenceRule(identifier);
				li.append("&nbsp;<span style=\"color: rgb(192,192,192);\">"
						+ rule.replace("R=", "").replace("RRULE=", "") + "</span>");
			}
			if (role.getDataitem().hasComment(identifier)) {
				String comment = role.getDataitem().getComment(identifier);
				li.append("&nbsp;|&nbsp;<span style=\"color: rgb(192,192,192);\">" + comment + "</span>");
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.rogatio.circlead.view.ISynchronizerRenderer#addStatus(org.jsoup.nodes.
	 * Element, java.lang.String)
	 */
	public void addStatus(Element element, String statusValue) {
		WorkitemStatusParameter status = WorkitemStatusParameter.get(statusValue);
		if (status != null) {
			element.append("&nbsp;").appendElement("div").attr("id", "status" + status.getColor())
					.appendText(status.getName());
		}
	}

	/**
	 * Adds the person list.
	 *
	 * @param element    the element
	 * @param list       the list
	 * @param role       the role
	 * @param leadPerson the lead person
	 */
	public void addPersonList(Element element, List<String> list, Role role, String leadPerson) {
		Element ul = element.appendElement("div").appendElement("ul");
		for (String identifier : list) {
			Person person = Repository.getInstance().getPerson(identifier);
			Element li = ul.appendElement("li");

			if (person != null) {
				if (identifier.equals(leadPerson)) {
					li.appendElement("u").appendElement("a").attr("href", "" + person.getId(synchronizer) + ".html")
							.appendText(person.getTitle());
				} else {
					li.appendElement("a").attr("href", "" + person.getId(synchronizer) + ".html")
							.appendText(person.getTitle());
				}
			} else {
				if (identifier.equals(leadPerson)) {
					li.appendElement("u").appendText(identifier);
				} else {
					li.appendText(identifier);
				}
			}
			if (role.getDataitem().hasRepresentation(identifier)) {
				String representation = role.getDataitem().getRepresentation(identifier);
				WorkitemStatusParameter status = WorkitemStatusParameter.get(representation);
				if (status != null) {
					li.append("&nbsp;").appendElement("div").attr("id", "status" + status.getColor())
							.appendText(status.getName());
				}
			}
			if (role.getDataitem().hasSkill(identifier)) {
				String skill = role.getDataitem().getSkill(identifier);
//				li.append("&nbsp;");
//				li.appendText("" + skill + "%");
				WorkitemStatusParameter status = WorkitemStatusParameter.get(skill + "%");
				if (status != null) {
					li.append("&nbsp;").appendElement("div").attr("id", "status" + status.getColor())
							.appendText(status.getName());
				}
			}
			if (role.getDataitem().hasRecurrenceRule(identifier)) {
				String rule = role.getDataitem().getRecurrenceRule(identifier);
				li.append("&nbsp;<span style=\"color: rgb(192,192,192);\">"
						+ rule.replace("R=", "").replace("RRULE=", "") + "</span>");
			}
			if (role.getDataitem().hasComment(identifier)) {
				String comment = role.getDataitem().getComment(identifier);
				li.append("&nbsp;|&nbsp;<span style=\"color: rgb(192,192,192);\">" + comment + "</span>");
			}
		}

	}

	/**
	 * Adds the list.
	 *
	 * @param element           the element
	 * @param list              the list
	 * @param underlinedElement the underlined element
	 */
	public void addList(Element element, List<String> list, String underlinedElement) {
		if (list != null) {
			if (list.size() > 0) {
				Element ul = element.appendElement("div").appendElement("ul");
				for (String item : list) {

					if (item.equals(underlinedElement)) {
						ul.appendElement("li").appendElement("u").appendText(item);
					} else {
						ul.appendElement("li").appendText(item);
					}
				}
			}
		}
	}

	/**
	 * Adds the list.
	 *
	 * @param element the element
	 * @param list    the list
	 */
	public void addList(Element element, List<String> list) {
		if (list != null) {
			if (list.size() > 0) {
				Element ul = element.appendElement("div").appendElement("ul");
				for (String item : list) {
					ul.appendElement("li").appendText(item);
				}
			}
		}
	}

	/**
	 * Adds the item.
	 *
	 * @param element     the element
	 * @param description the description
	 */
	public void addItem(Element element, String description) {
		if (description != null) {
			Element div = element.appendElement("div");
			div.appendText(description);
		}
	}

	/**
	 * Adds the item.
	 *
	 * @param element     the element
	 * @param description the description
	 * @param list        the list
	 */
	public void addItem(Element element, String description, List<String> list) {
		Element div = element.appendElement("div");
		div.appendElement("b").appendText(description);
		div.appendText(": ");
		if (list != null) {
			div.appendText(String.join(", ", list));
		} else {
			div.appendText("-");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.rogatio.circlead.view.ISynchronizerRenderer#addHowToItem(org.jsoup.nodes.
	 * Element, java.lang.String, java.lang.String)
	 */
	public void addHowToItem(Element element, String description, String content) {
		HowTo r = Repository.getInstance().getHowTo(content);

		Element div = element.appendElement("div");
		div.appendElement("b").appendText(description);
		div.appendText(":").append("&nbsp;");
		if (content != null) {
			if (r != null) {
				div.appendElement("a").attr("href", "howtos/" + r.getUrl()).appendText(r.getTitle());
			} else {
				div.appendText(content);
			}
		} else {
			div.appendText("-");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.rogatio.circlead.view.ISynchronizerRendererEngine#addActivityItem(org.
	 * jsoup.nodes.Element, java.lang.String, java.lang.String)
	 */
	public void addActivityItem(Element element, String description, String content) {
		Activity r = Repository.getInstance().getActivity(content);

		Element div = element.appendElement("div");
		if (description != null) {
			div.appendElement("b").appendText(description);
			div.appendText(":").append("&nbsp;");
		}
		if (content != null) {
			if (r != null) {
				div.appendElement("a").attr("href", "" + r.getId(synchronizer) + ".html").appendText(r.getTitle());
			} else {
				div.appendText(content);
			}
		} else {
			div.appendText("-");
		}
	}

	/**
	 * Adds the role item.
	 *
	 * @param element     the element
	 * @param description the description
	 * @param content     the content
	 */
	public void addRoleItem(Element element, String description, String content) {
		Role r = Repository.getInstance().getRole(content);

		Element div = element.appendElement("div");
		if (description != null) {
			div.appendElement("b").appendText(description);
			div.appendText(":").append("&nbsp;");
		}
		if (content != null) {
			if (r != null) {
				div.appendElement("a").attr("href", "" + r.getId(synchronizer) + ".html").appendText(r.getTitle());
			} else {
				div.appendText(content);
			}
		} else {
			div.appendText("-");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.rogatio.circlead.view.ISynchronizerRendererEngine#addTeamItem(org.jsoup.
	 * nodes.Element, java.lang.String, java.lang.String)
	 */
	public void addTeamItem(Element element, String description, String content) {
		Team r = Repository.getInstance().getTeam(content);

		Element div = element.appendElement("div");
		if (description != null) {
			div.appendElement("b").appendText(description);
			div.appendText(":").append("&nbsp;");
		}
		if (content != null) {
			if (r != null) {
				div.appendElement("a").attr("href", "" + r.getId(synchronizer) + ".html").appendText(r.getTitle());
			} else {
				div.appendText(content);
			}
		} else {
			div.appendText("-");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.rogatio.circlead.view.ISynchronizerRendererEngine#addPersonItem(org.jsoup
	 * .nodes.Element, java.lang.String, java.lang.String)
	 */
	public void addPersonItem(Element element, String description, String content) {
		Person r = Repository.getInstance().getPerson(content);

		Element div = element.appendElement("div");
		if (description != null) {
			div.appendElement("b").appendText(description);
			div.appendText(":").append("&nbsp;");
		}
		if (content != null) {
			if (r != null) {
				div.appendElement("a").attr("href", "" + r.getId(synchronizer) + ".html").appendText(r.getTitle());
			} else {
				div.appendText(content);
			}
		} else {
			div.appendText("-");
		}
	}

	/**
	 * Adds the rolegroup item.
	 *
	 * @param element     the element
	 * @param description the description
	 * @param content     the content
	 */
	public void addRolegroupItem(Element element, String description, String content) {
		Rolegroup rg = Repository.getInstance().getRolegroup(content);

		Element div = element.appendElement("div");
		div.appendElement("b").appendText(description);
		div.appendText(": ");
		if (content != null) {
			if (rg != null) {
				div.appendElement("a").attr("href", "" + rg.getId(synchronizer) + ".html").appendText(rg.getTitle());
			} else {
				div.appendText(content);
			}
		} else {
			div.appendText("-");
		}
	}

	/**
	 * Adds the item.
	 *
	 * @param element     the element
	 * @param description the description
	 * @param content     the content
	 */
	public void addItem(Element element, String description, String content) {
		Element div = element.appendElement("div");
		div.appendElement("b").appendText(description);
		div.appendText(": ");
		if (content != null) {
			div.appendText(content);
		} else {
			div.appendText("-");
		}
	}

	/**
	 * Adds the H 1.
	 *
	 * @param element the element
	 * @param header  the header
	 */
	public void addH1(Element element, String header) {
		element.appendElement("h1").appendText(header);
	}

	/**
	 * Adds the H 2.
	 *
	 * @param element the element
	 * @param header  the header
	 */
	public void addH2(Element element, String header) {
		element.appendElement("h2").appendText(header);
	}

	/**
	 * Adds the H 3.
	 *
	 * @param element the element
	 * @param header  the header
	 */
	public void addH3(Element element, String header) {
		element.appendElement("h3").appendText(header);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.rogatio.circlead.view.ISynchronizerRenderer#addValidationList(org.jsoup.
	 * nodes.Element, java.util.List)
	 */
	@Override
	public void addValidationList(Element element, List<ValidationMessage> list) {
		Element table = element.appendElement("div").appendElement("table");
		for (ValidationMessage vm : list) {
			addDataPair(vm.getType().name(), vm.getMessage(), table);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.rogatio.circlead.view.ISynchronizerRendererEngine#addTeamList(org.jsoup.
	 * nodes.Element, java.util.List)
	 */
	@Override
	public void addTeamList(Element element, List<Team> list) {
		if (list != null) {
			if (list.size() > 0) {
				Element ul = element.appendElement("div").appendElement("ul");
				for (Team team : list) {
					Element li = ul.appendElement("li");
					li.appendElement("a").attr("href", "" + team.getId(synchronizer) + ".html")
							.appendText(team.getTitle());
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.rogatio.circlead.view.ISynchronizerRendererEngine#addPersonList(org.jsoup
	 * .nodes.Element, java.util.List)
	 */
	@Override
	public void addPersonList(Element element, List<Person> list) {
		if (list != null) {
			if (list.size() > 0) {
				Element ul = element.appendElement("div").appendElement("ul");
				for (Person person : list) {
					Element li = ul.appendElement("li");
					li.appendElement("a").attr("href", "" + person.getId(synchronizer) + ".html")
							.appendText(person.getTitle());
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.rogatio.circlead.view.ISynchronizerRendererEngine#addReportList(org.jsoup
	 * .nodes.Element, java.util.List)
	 */
	@Override
	public void addReportList(Element element, List<IReport> list) {
		if (list != null) {
			if (list.size() > 0) {
				Element ul = element.appendElement("div").appendElement("ul");
				for (IReport report : list) {
					if (report != null) {
						if (report.getName() != null) {
							Element li = ul.appendElement("li");
							li.appendElement("a").attr("href", "" + report.getName() + ".html")
									.appendText(report.getName());

							if (StringUtil.isNotNullAndNotEmpty(report.getDescription())) {
								li.appendText(": " + report.getDescription());
							}
						}
					}
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.rogatio.circlead.view.ISynchronizerRendererEngine#addHowToList(org.jsoup.
	 * nodes.Element, java.util.List)
	 */
	@Override
	public void addHowToList(Element element, List<HowTo> list) {
		if (list != null) {
			if (list.size() > 0) {
				Element ul = element.appendElement("div").appendElement("ul");
				for (HowTo howto : list) {
					if (this.getSynchronizer().getClass().getSimpleName().equals(howto.getSynchronizer())) {
						Element li = ul.appendElement("li");
						li.appendElement("a").attr("href", "" + howto.getTitle() + ".html")
								.appendText(howto.getTitle());
					}
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.rogatio.circlead.view.renderer.ISynchronizerRendererEngine#addImage(org.jsoup.nodes.Element, java.lang.String, int)
	 */
	@Override
	public void addImage(Element element, String filename, int size) {
		File f = new File("images"+File.separatorChar+"profile"+File.separatorChar+filename);
		
		if (f.exists()) {
			element.append(
					"<img src=\"images\\profile\\" + filename + "\" alt=\"" + filename + "\" width=\"" + size + "px\">");			
		}

	}

	/* (non-Javadoc)
	 * @see org.rogatio.circlead.view.renderer.ISynchronizerRendererEngine#addTeamLink(org.jsoup.nodes.Element, org.rogatio.circlead.model.work.Team)
	 */
	@Override
	public void addTeamLink(Element element, Team team) {
		String c = "";
		if (StringUtil.isNotNullAndNotEmpty(team.getCategory())) {
			c = " (" + team.getCategory() + ")";
		}
		element.appendElement("a").attr("href", "" + team.getId(synchronizer) + ".html")
				.appendText(team.getTitle() + c);
	}

	/* (non-Javadoc)
	 * @see org.rogatio.circlead.view.renderer.ISynchronizerRendererEngine#addRessourceChart(org.jsoup.nodes.Element, org.rogatio.circlead.model.work.Person)
	 */
	@Override
	public void addRessourceChart(Element element, Person person) {
		final Map<String, List<Timeslice>> map = person.getOrganisationalTimeslices(Freq.MONTHLY, true);

		String colors = "";
		String colorArray[] = { "#989898", "#A8A8A8", "#B8B8B8", "#C0C0C0" };

		for (int i = 0; i < map.size(); i++) {
			if (colors == null) {
				colors = new String("");
			}
			colors += colorArray[i];

			if ((i + 1) < map.size()) {
				colors += ", ";
			}
		}

		Map<String, List<Timeslice>> m = person.getTeamTimeslices(Freq.MONTHLY);
		m.forEach((k, v) -> map.put(k, v));

		Color[] c = ColorPalette.rainbow(m.size() + 2);
		for (int i = 0; i < m.size(); i++) {
			if (colors.length() > 0) {
				colors += ", " + ObjectUtil.convertToHtmlColor(c[i + 1]);
			} else {
				colors += "" + ObjectUtil.convertToHtmlColor(c[i + 1]);
			}
		}

		addH2(element, "Ressourcenallokation");
		addChart(element, colors, map);
	}

	/* (non-Javadoc)
	 * @see org.rogatio.circlead.view.renderer.ISynchronizerRendererEngine#addRoleLink(org.jsoup.nodes.Element, org.rogatio.circlead.model.work.Role)
	 */
	@Override
	public void addRoleLink(Element element, Role role) {
		element.appendElement("a").attr("href", "" + role.getId(this.getSynchronizer()) + ".html")
				.appendText(role.getTitle());
	}
}
