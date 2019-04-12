package org.rogatio.circlead.main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.filter.ElementFilter;
import org.jdom2.input.DOMBuilder;
import org.rogatio.circlead.control.Comparators;
import org.rogatio.circlead.control.Repository;
import org.rogatio.circlead.control.synchronizer.file.FileSynchronizer;
import org.rogatio.circlead.model.WorkitemType;
import org.rogatio.circlead.model.data.ActivityDataitem;
import org.rogatio.circlead.model.work.Activity;
import org.rogatio.circlead.model.work.Role;
import org.rogatio.circlead.util.StringUtil;
import org.rogatio.circlead.view.report.IndexWorkitems;
import org.xml.sax.SAXException;

/**
 * The Class YedGraphImporter.
 * 
 * @author Matthias Wegner
 */
public class YedGraphImporter {

	/** The Constant logger. */
	private final static Logger LOGGER = LogManager.getLogger(YedGraphImporter.class);

	/** The Constant Default Namespace of GraphML */
	private static final Namespace ns = Namespace.getNamespace("http://graphml.graphdrawing.org/xmlns");

	/** The Constant Namespace of yWorks-Graph */
	private static final Namespace y = Namespace.getNamespace("http://www.yworks.com/xml/graphml");

	/** The table rows, which are process-swimlanes */
	private List<TableRow> tableRows = new ArrayList<TableRow>();

	/** The home dir of the GraphML-Files */
	private String homeDir;

	/** The document. */
	private Document document;

	/** The nodes of the graph */
	private List<TableNode> tableNodes = new ArrayList<TableNode>();

	/** The edges of the graph */
	private List<TableEdge> tableEdges = new ArrayList<TableEdge>();

	private static Map<String, List<String>> roleSynonyms = new HashMap<String, List<String>>();

	private static void readSynonyms() {
		LineIterator lineIterator = null;
		try {
			lineIterator = FileUtils.lineIterator(new File("role.synonyms"), "utf-8");// second parameter is
																						// optionanl
			while (lineIterator.hasNext()) {
				String currentLine = lineIterator.next();
				List<String> list = StringUtil.toList(currentLine);
				if (list != null) {
					if (list.size() > 0) {
						roleSynonyms.put(list.get(0), list);

						Role role = new Role();
						role.setId(UUID.randomUUID().toString(), new FileSynchronizer(""));
						role.setTitle(list.get(0));
						role.setSynonyms(list);
						Repository.getInstance().addRoleItem(role);

						LOGGER.debug(role.getTitle() + ": " + role.getSynonyms());
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			LineIterator.closeQuietly(lineIterator);
		}
	}

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String... args) {

		readSynonyms();

		YedGraphImporter ygi = new YedGraphImporter("/home/matthias/Schreibtisch/Prozesse/01_Prozessvisualisierungen");

		Repository repository = Repository.getInstance();

		FileSynchronizer fsynchronizer = new FileSynchronizer("yed");

		fsynchronizer.setWebDirectory("yed" + File.separatorChar + "web");

		repository.addSynchronizer(fsynchronizer);

		List<Activity> activities = ygi.iterateDir();
		repository.addActivityItems(activities);

		repository.updateWorkitems();

		fsynchronizer.writeReportRendered(new IndexWorkitems(WorkitemType.ROLE));
		fsynchronizer.writeReportRendered(new IndexWorkitems(WorkitemType.ACTIVITY));
	}

	/**
	 * Instantiates a new yed graph importer.
	 *
	 * @param homeDir the home dir
	 */
	public YedGraphImporter(String homeDir) {
		this.homeDir = homeDir;
	}

	/**
	 * Iterate for graphML-Files through homeDir
	 *
	 * @return the list
	 */
	public List<Activity> iterateDir() {
		List<Activity> activities = new ArrayList<Activity>();
		File[] files = new File(homeDir).listFiles();
		if (files != null) {
			getFile(files, activities);
		}
		return activities;
	}

	/**
	 * Reccursive method to scan for graphML-Files
	 *
	 * @param files      the files
	 * @param activities the activities
	 * @return the file
	 */
	private void getFile(File[] files, List<Activity> activities) {
		for (File file : files) {
			if (file.isDirectory()) {
				getFile(file.listFiles(), activities);
			} else {
				if (file.toString().endsWith("graphml")) {
					this.importFile(file.toString());
					parse();

					Activity a = createActivity(file.toString().replace(homeDir, ""));
					activities.add(a);
				}
			}
		}
	}

	public void addChildren(ActivityDataitem item, List<ActivityDataitem> activities, List<ActivityDataitem> sorted) {
//		List<ActivityDataitem> x = new ArrayList<ActivityDataitem>();

		if (!sorted.contains(item)) {
			sorted.add(item);
		}
		if (item.getChild() != null) {
			List<String> c = StringUtil.toList(item.getChild());
			for (String s : c) {
				for (ActivityDataitem activityDataitem : activities) {
					if (s.trim().equals(activityDataitem.getAid())) {
						// if (!sorted.contains(activityDataitem)) {
						// sorted.add(activityDataitem);
						// }/
						if (activityDataitem.getChild() != null) {
							if (!sorted.contains(activityDataitem)) {
								addChildren(activityDataitem, activities, sorted);
							}
						}
					}
				}
			}
		}

	}

	public List<ActivityDataitem> getStarts(List<ActivityDataitem> activities) {
		List<ActivityDataitem> x = new ArrayList<ActivityDataitem>();

		for (ActivityDataitem activityDataitem : activities) {
			if (activityDataitem.getBpmn() != null) {
				if (activityDataitem.getBpmn().contains("start-event-none")) {
					x.add(activityDataitem);
				}
			}
		}

		for (ActivityDataitem activityDataitem : activities) {
			if (activityDataitem.getBpmn() != null) {
				if (activityDataitem.getBpmn().contains("start")) {
					if (!x.contains(activityDataitem)) {
						x.add(activityDataitem);
					}
				}
			}
		}

		return x;
	}

	/**
	 * Creates the activity.
	 *
	 * @param name the name
	 * @return the activity
	 */
	private Activity createActivity(String name) {
		String rawname = name;

		String[] dirs = name.split("" + File.separatorChar);
		for (String s : dirs) {
			if (!s.trim().equals("")) {
				if (s.contains("graphml")) {
					name = s.replace(".graphml", "");
					s = s.replace(".graphml", "");
				}
			}
		}

		Activity activity = new Activity();
		activity.setId(UUID.randomUUID().toString(), new FileSynchronizer(""));
		activity.setTitle(name);

		HashMap<TableNode, ActivityDataitem> map = new HashMap<TableNode, ActivityDataitem>();
		List<ActivityDataitem> subactivities = new ArrayList<ActivityDataitem>();

		for (TableNode n : tableNodes) {
			ActivityDataitem a = new ActivityDataitem();
			map.put(n, a);
			a.setTitle(n.getLabel());
			if (n.getParent() != null) {
				String res = n.getParent().getName();
				if (res != null) {
					a.setResponsible(res);

					if (Repository.getInstance().getRole(res) == null) {
						Role role = new Role();
						role.setId(UUID.randomUUID().toString(), new FileSynchronizer(""));
						role.setTitle(res);

						Repository.getInstance().addRoleItem(role);
					}
				}
			}

			a.setAid(n.getId());
			a.setBpmn(n.getBpmnTag());

			// Exclude Annotation-Nodes
			if (!n.getConfiguration().contains("Artifact")) {
				subactivities.add(a);
			}

		}

		for (TableEdge tableEdge : tableEdges) {
			TableNode sn = tableEdge.getSource();
			TableNode tn = tableEdge.getTarget();

			if (sn == null) {
				LOGGER.error("Source '" + tableEdge.getSourceId() + "' could not resolved in Edge '" + tableEdge.getId()
						+ "' of file '" + rawname + "'");
			}
			if (tn == null) {
				LOGGER.error("Target '" + tableEdge.getTargetId() + "' could not resolved in Edge '" + tableEdge.getId()
						+ "' of file '" + rawname + "'");
			}

			ActivityDataitem source = map.get(sn);
//			ActivityDataitem target = map.get(tn);

			if (source != null) {
				if (source.getChild() != null) {
					if (tn != null) {
						source.setChild(tn.getId() + ", " + source.getChild());
					}
				} else {
					if (tn != null) {
						source.setChild(tn.getId());
					}
				}
			}

			if (tn != null) {
				if (tn.getConfiguration().contains("Artifact")) {
					if (source != null) {
						source.setDescription(tn.getLabel());
					}
				}
			}

			if (StringUtil.isNotNullAndNotEmpty(tableEdge.getLabel())) {
				source.setTitle(tableEdge.getLabel() + ": " + source.getTitle());
			}

		}

		List<ActivityDataitem> sorted = new ArrayList<ActivityDataitem>();

		List<ActivityDataitem> starts = this.getStarts(subactivities);
		for (ActivityDataitem start : starts) {
			this.addChildren(start, subactivities, sorted);
		}
		// Collections.sort(subactivities, Comparators.SUBACTIVITYFLOW);

		for (ActivityDataitem a : subactivities) {
			if (!sorted.contains(a)) {
				sorted.add(a);
			}
		}

		if (name.equals("0301_Bürobedarfbestellung")) {
			for (ActivityDataitem a : sorted) {
				System.out.println(a.getAid() + ": " + a.getChild() + " (" + a.getBpmn() + ")");
			}
		}

		activity.setSubactivities(sorted);
//		activity.setSubactivities(subactivities);

		return activity;
	}

	/**
	 * Gets the edge.
	 *
	 * @param node the node
	 * @return the edge
	 */
	@SuppressWarnings("unused")
	private TableEdge getEdge(TableNode node) {
		for (TableEdge tableEdge : tableEdges) {
			TableNode sn = tableEdge.getSource();
			TableNode tn = tableEdge.getTarget();
			if (node.getId().equals(tableEdge.getTargetId())) {
				return tableEdge;
			}
			if (node.getId().equals(tableEdge.getSourceId())) {
				return tableEdge;
			}
		}
		return null;
	}

	/**
	 * Parses the nodes.
	 *
	 * @return the map
	 */
	private Map<String, Element> parseNodes() {
		Map<String, Element> elements = new HashMap<String, Element>();
		Iterator<?> nodeIterator = document.getRootElement().getDescendants(new ElementFilter("node"));
		while (nodeIterator.hasNext()) {
			Element node = (Element) nodeIterator.next();
			String id = node.getAttributeValue("id");

			Iterator<?> gNodeIterator = node.getDescendants(new ElementFilter("GenericNode"));
			while (gNodeIterator.hasNext()) {
				Element genericNode = (Element) gNodeIterator.next();
				elements.put(id, genericNode);

			}
		}
		return elements;
	}

	/**
	 * Parses the Graph-XML
	 */
	public void parse() {

		if (document == null) {
			return;
		}

		tableRows = new ArrayList<TableRow>();
		tableNodes = new ArrayList<TableNode>();
		tableEdges = new ArrayList<TableEdge>();

		Element root = document.getRootElement();
		Element graph = root.getChild("graph", ns);
		Element node = graph.getChild("node", ns);
		Element data = node.getChild("data", ns);
		Element table = data.getChild("TableNode", y);
		if (table != null) {
			List<Element> labels = table.getChildren("NodeLabel", y);
			for (Element label : labels) {
				if (isRow(label)) {
					TableRow tableRow = createNewTableRow();
					tableRow.setYOffset(table.getChild("Geometry", y).getAttributeValue("y"));
					tableRow.setId(this.getRowId(label));
					tableRow.setName(label.getText().trim().replace("\n", ""));
					tableRow.setyPos(label.getAttributeValue("y"));
					tableRows.add(tableRow);
				}
			}
			Element rows = table.getChild("Table", y).getChild("Rows", y);
			List<Element> rowElements = rows.getChildren("Row", y);
			for (Element row : rowElements) {
				String id = row.getAttributeValue("id");
				TableRow tableRow = this.getTableRow(id);
				String height = row.getAttributeValue("height");

				if (tableRow != null) {
					tableRow.setHeight(height);
				} else {
					LOGGER.error("Could not find swimlane with id '" + id + "'");
				}
			}
		}

		Map<String, Element> nodes = parseNodes();

		for (String id : nodes.keySet()) {
			Element dataElement = nodes.get(id);

			TableNode n = new TableNode();
			String configuration = dataElement.getAttributeValue("configuration");
			String xPos = dataElement.getChild("Geometry", y).getAttributeValue("x");
			String yPos = dataElement.getChild("Geometry", y).getAttributeValue("y");
			List<Element> elabs = dataElement.getChildren("NodeLabel", y);
			String label = "";
			for (Element element : elabs) {
				if (element.getText() != null) {
					label = dataElement.getChild("NodeLabel", y).getText().trim();
				}
			}

			Element styleProperties = dataElement.getChild("StyleProperties", y);
			if (styleProperties != null) {
				List<Element> styles = styleProperties.getChildren();
				for (Element style : styles) {
					if (style.getAttributeValue("class").equals("com.yworks.yfiles.bpmn.view.EventCharEnum")) {
						String eventValue = style.getAttributeValue("value");
						if (eventValue.equals("EVENT_CHARACTERISTIC_END")) {
							n.setEventcharacteristic("end");
						}
					}
					if (style.getAttributeValue("class").equals("com.yworks.yfiles.bpmn.view.BPMNTypeEnum")) {
						String eventValue = style.getAttributeValue("value");
						if (eventValue.equals("GATEWAY_TYPE_PARALLEL")) {
							n.setEventcharacteristic("parallel");
						}
						if (eventValue.equals("GATEWAY_TYPE_DATA_BASED_EXCLUSIVE")) {
							n.setEventcharacteristic("xor");
						}
					}
					if (style.getAttributeValue("class").equals("com.yworks.yfiles.bpmn.view.BPMNTypeEnum")) {
						String eventValue = style.getAttributeValue("value");
						if (eventValue.equals("EVENT_TYPE_TIMER")) {
							n.setEventtype("timer");
						}
						if (eventValue.equals("EVENT_TYPE_MESSAGE")) {
							n.setEventtype("message");
						}
					}
				}
			}

			n.setId(id);
			n.setyPos(yPos);
			n.setxPos(xPos);
			n.setLabel(label);
			n.setConfiguration(configuration);

			for (TableRow row : tableRows) {
				boolean in = this.isInRow(row, n);
				if (in) {
					n.setParent(row);
				}
			}

			tableNodes.add(n);
		}

		List<Element> edges = graph.getChildren("edge", ns);
		for (Element edgeElement : edges) {

			TableEdge e = new TableEdge();

			String id = edgeElement.getAttributeValue("id");
			String sourceId = edgeElement.getAttributeValue("source");
			String targetId = edgeElement.getAttributeValue("target");
			List<Element> datas = edgeElement.getChildren("data", ns);
			for (Element edata : datas) {
				Element line = edata.getChild("PolyLineEdge", y);
				if (line != null) {
					Element elabel = line.getChild("EdgeLabel", y);
					if (elabel != null) {
						e.setLabel(elabel.getText().trim());
					}
				}
			}

			e.setId(id);
			e.setTargetId(targetId);
			e.setSourceId(sourceId);
			tableEdges.add(e);
		}
	}

	/**
	 * Gets the table node.
	 *
	 * @param id the id
	 * @return the table node
	 */
	public TableNode getTableNode(String id) {
		for (TableNode tableNode : tableNodes) {
			if (tableNode.getId().equals(id)) {
				return tableNode;
			}
		}
		return null;
	}

	/**
	 * Gets the table row.
	 *
	 * @param id the id
	 * @return the table row
	 */
	public TableRow getTableRow(String id) {
		for (TableRow tableRow : tableRows) {
			if (tableRow.getId().equals(id)) {
				return tableRow;
			}
		}
		return null;
	}

	/**
	 * Gets the row id.
	 *
	 * @param element the element
	 * @return the row id
	 */
	private String getRowId(Element element) {
		Element r = element.getChild("ModelParameter", y);
		if (r != null) {
			Element rr = r.getChild("RowNodeLabelModelParameter", y);
			Attribute a = rr.getAttribute("id");
			if (a.getValue().startsWith("row")) {
				return a.getValue();
			}
		}
		return null;
	}

	/**
	 * Checks if is row.
	 *
	 * @param element the element
	 * @return true, if is row
	 */
	private boolean isRow(Element element) {
		Element r = element.getChild("ModelParameter", y);
		if (r != null) {
			Element rr = r.getChild("RowNodeLabelModelParameter", y);
			if (rr != null) {
				Attribute a = rr.getAttribute("id");
				if (a.getValue().startsWith("row")) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Creates the new table row.
	 *
	 * @return the table row
	 */
	private TableRow createNewTableRow() {
		return new TableRow();
	}

	/**
	 * The Class TableNode.
	 */
	class TableNode {

		/** The id. */
		private String id;

		/** The configuration. */
		private String configuration;

		/** The parent. */
		private TableRow parent;

		/** The y pos. */
		private double yPos;

		/** The x pos. */
		private double xPos;

		/** The eventcharacteristic. */
		private String eventcharacteristic;

		/** The eventtype. */
		private String eventtype;

		/** The label. */
		private String label;

		/**
		 * Gets the eventtype.
		 *
		 * @return the eventtype
		 */
		public String getEventtype() {
			return eventtype;
		}

		/**
		 * Sets the eventtype.
		 *
		 * @param eventtype the new eventtype
		 */
		public void setEventtype(String eventtype) {
			this.eventtype = eventtype;
		}

		/**
		 * Gets the eventcharacteristic.
		 *
		 * @return the eventcharacteristic
		 */
		public String getEventcharacteristic() {
			return eventcharacteristic;
		}

		/**
		 * Sets the eventcharacteristic.
		 *
		 * @param eventcharacteristic the new eventcharacteristic
		 */
		public void setEventcharacteristic(String eventcharacteristic) {
			this.eventcharacteristic = eventcharacteristic;
		}

		/**
		 * Gets the parent.
		 *
		 * @return the parent
		 */
		public TableRow getParent() {
			return parent;
		}

		/**
		 * Sets the parent.
		 *
		 * @param parent the new parent
		 */
		public void setParent(TableRow parent) {
			this.parent = parent;
		}

		/**
		 * Gets the id.
		 *
		 * @return the id
		 */
		public String getId() {
			return id;
		}

		/**
		 * Sets the id.
		 *
		 * @param id the new id
		 */
		public void setId(String id) {
			this.id = id;
		}

		/**
		 * Gets the configuration.
		 *
		 * @return the configuration
		 */
		public String getConfiguration() {
			return configuration;
		}

		/**
		 * Sets the configuration.
		 *
		 * @param configuration the new configuration
		 */
		public void setConfiguration(String configuration) {
			this.configuration = configuration;
		}

		/**
		 * Gets the y pos.
		 *
		 * @return the y pos
		 */
		public double getyPos() {
			return yPos;
		}

		/**
		 * Sets the y pos.
		 *
		 * @param yPos the new y pos
		 */
		public void setyPos(double yPos) {
			this.yPos = yPos;
		}

		/**
		 * Sets the y pos.
		 *
		 * @param yPos the new y pos
		 */
		public void setyPos(String yPos) {
			this.yPos = Double.parseDouble(yPos);
		}

		/**
		 * Gets the x pos.
		 *
		 * @return the x pos
		 */
		public double getxPos() {
			return xPos;
		}

		/**
		 * Sets the x pos.
		 *
		 * @param xPos the new x pos
		 */
		public void setxPos(String xPos) {
			this.xPos = Double.parseDouble(xPos);
		}

		/**
		 * Sets the x pos.
		 *
		 * @param xPos the new x pos
		 */
		public void setxPos(double xPos) {
			this.xPos = xPos;
		}

		/**
		 * Gets the label.
		 *
		 * @return the label
		 */
		public String getLabel() {
			return label;
		}

		/**
		 * Gets the bpmn tag.
		 *
		 * @return the bpmn tag
		 */
		public String getBpmnTag() {
			StringBuilder sb = new StringBuilder();

			String type = "task";

			if (getConfiguration().contains("Activity")) {
				type = "task";
			}

			if (getConfiguration().contains("Gateway")) {
				type = "gateway";
			}

			if (getConfiguration().contains("Event")) {
				type = "start-event";
			}

			if (getConfiguration().contains("Artifact")) {
				type = "text-annotation";
			}

			sb.append(type);

			if (getEventtype() != null) {
				String eventtype = getEventtype();
				sb.append("-" + eventtype);
			}

			if (getEventcharacteristic() != null) {
				String eventcharacteristic = getEventcharacteristic();
				sb.append("-" + eventcharacteristic);
			} else {
				if (!(sb.toString().contains("event") || sb.toString().contains("text-annotation"))) {
					sb.append("-none");
				} else {
					if (sb.toString().endsWith("event")) {
						sb.append("-none");
					}
				}
			}
			if (sb.toString().equals("start-event-end")) {
				return "end-event-none";
			}
			return sb.toString();
		}

		/**
		 * Sets the label.
		 *
		 * @param label the new label
		 */
		public void setLabel(String label) {
			this.label = label;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#toString()
		 */
		public String toString() {
			StringBuilder sb = new StringBuilder();

			sb.append("TableNode [id=" + id + ", x=" + xPos + ", y=" + yPos + ", conf=" + configuration);

			if (parent != null) {
				sb.append(", parent=" + parent.getName());
			}

			return sb.toString();
		}

	}

	/**
	 * Checks if is in row.
	 *
	 * @param row  the row
	 * @param node the node
	 * @return true, if is in row
	 */
	public boolean isInRow(TableRow row, TableNode node) {
		double max = row.getYOffset() + row.getyPos() + row.getHeight() / 2;
		double min = row.getYOffset() + row.getyPos() - row.getHeight() / 2;

		if (min < node.getyPos() && max > node.getyPos()) {
			return true;
		}

		return false;
	}

	/**
	 * The Class TableEdge.
	 */
	class TableEdge {

		/** The id. */
		private String id;

		/** The source id. */
		private String sourceId;

		/** The target id. */
		private String targetId;

		/** The label. */
		private String label;

		/**
		 * Gets the id.
		 *
		 * @return the id
		 */
		public String getId() {
			return id;
		}

		/**
		 * Sets the id.
		 *
		 * @param id the new id
		 */
		public void setId(String id) {
			this.id = id;
		}

		/**
		 * Gets the source id.
		 *
		 * @return the source id
		 */
		public String getSourceId() {
			return sourceId;
		}

		/**
		 * Sets the source id.
		 *
		 * @param sourceId the new source id
		 */
		public void setSourceId(String sourceId) {
			this.sourceId = sourceId;
		}

		/**
		 * Gets the target id.
		 *
		 * @return the target id
		 */
		public String getTargetId() {
			return targetId;
		}

		/**
		 * Gets the target.
		 *
		 * @return the target
		 */
		public TableNode getTarget() {
			return getTableNode(targetId);
		}

		/**
		 * Gets the source.
		 *
		 * @return the source
		 */
		public TableNode getSource() {
			return getTableNode(sourceId);
		}

		/**
		 * Sets the target id.
		 *
		 * @param targetId the new target id
		 */
		public void setTargetId(String targetId) {
			this.targetId = targetId;
		}

		/**
		 * Gets the label.
		 *
		 * @return the label
		 */
		public String getLabel() {
			return label;
		}

		/**
		 * Sets the label.
		 *
		 * @param label the new label
		 */
		public void setLabel(String label) {
			this.label = label;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#toString()
		 */
		public String toString() {
			return "TableEdge [id=" + id + ", source=" + sourceId + ", target=" + targetId + ", label=" + label + "]";
		}
	}

	/**
	 * The Class TableRow.
	 */
	class TableRow {

		/** The id. */
		private String id;

		/** The name. */
		private String name;

		/** The height. */
		private double height;

		/** The y pos. */
		private double yPos;

		/** The y offset. */
		private double yOffset;

		/**
		 * Gets the id.
		 *
		 * @return the id
		 */
		public String getId() {
			return id;
		}

		/**
		 * Sets the id.
		 *
		 * @param id the new id
		 */
		public void setId(String id) {
			this.id = id;
		}

		/**
		 * Gets the name.
		 *
		 * @return the name
		 */
		public String getName() {
			return name;
		}

		/**
		 * Sets the name.
		 *
		 * @param name the new name
		 */
		public void setName(String name) {
			this.name = name;
		}

		/**
		 * Gets the height.
		 *
		 * @return the height
		 */
		public double getHeight() {
			return height;
		}

		/**
		 * Sets the height.
		 *
		 * @param height the new height
		 */
		public void setHeight(String height) {
			this.height = Double.parseDouble(height);
			;
		}

		/**
		 * Sets the height.
		 *
		 * @param height the new height
		 */
		public void setHeight(double height) {
			this.height = height;
		}

		/**
		 * Gets the y offset.
		 *
		 * @return the y offset
		 */
		public double getYOffset() {
			return yOffset;
		}

		/**
		 * Sets the y offset.
		 *
		 * @param yOffset the new y offset
		 */
		public void setYOffset(String yOffset) {
			this.yOffset = Double.parseDouble(yOffset);
			;
		}

		/**
		 * Sets the y offset.
		 *
		 * @param yOffset the new y offset
		 */
		public void setYOffset(double yOffset) {
			this.yOffset = yOffset;
		}

		/**
		 * Gets the y pos.
		 *
		 * @return the y pos
		 */
		public double getyPos() {
			return yPos;
		}

		/**
		 * Sets the y pos.
		 *
		 * @param yPos the new y pos
		 */
		public void setyPos(double yPos) {
			this.yPos = yPos;
		}

		/**
		 * Sets the y pos.
		 *
		 * @param yPos the new y pos
		 */
		public void setyPos(String yPos) {
			this.yPos = Double.parseDouble(yPos);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#toString()
		 */
		public String toString() {
			return "TableRow [id=" + id + ", name=" + name + ", y=" + yPos + ", height=" + height + "]";
		}
	}

	/**
	 * Import file.
	 *
	 * @param filename the filename
	 * @return the document
	 */
	public Document importFile(String filename) {
		document = null;

		File f = new File(filename);
		if (f.exists()) {
			LOGGER.debug("Import file '" + filename + "'");

			try {
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				factory.setNamespaceAware(true);
				DocumentBuilder documentBuilder = factory.newDocumentBuilder();
				org.w3c.dom.Document w3cDocument = documentBuilder.parse(f.getAbsoluteFile());
				document = new DOMBuilder().build(w3cDocument);
			} catch (IOException | SAXException | ParserConfigurationException e) {
				LOGGER.error("ERROR - Could NOT read file '" + filename + "'", e);
			}
		} else {
			LOGGER.error("ERROR - Could NOT find file '" + filename + "'");
		}
		return null;
	}

}
