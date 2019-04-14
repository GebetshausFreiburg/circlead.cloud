package org.rogatio.circlead.view.graph;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.batik.anim.dom.SVGDOMImplementation;
import org.apache.batik.constants.XMLConstants;
import org.apache.batik.svggen.SVGGraphics2D;
import org.rogatio.circlead.control.Repository;
import org.rogatio.circlead.model.data.ActivityDataitem;
import org.rogatio.circlead.model.work.Activity;
import org.rogatio.circlead.model.work.Role;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Element;
import org.w3c.dom.svg.SVGDocument;

import com.yworks.yfiles.geometry.InsetsD;
import com.yworks.yfiles.geometry.MutableRectangle;
import com.yworks.yfiles.geometry.PointD;
import com.yworks.yfiles.geometry.RectD;
import com.yworks.yfiles.geometry.SizeD;
import com.yworks.yfiles.graph.IEdge;
import com.yworks.yfiles.graph.IGraph;
import com.yworks.yfiles.graph.INode;
import com.yworks.yfiles.graph.labelmodels.ExteriorLabelModel;
import com.yworks.yfiles.graph.styles.Arrow;
import com.yworks.yfiles.graph.styles.IEdgeStyle;
import com.yworks.yfiles.graph.styles.PolylineEdgeStyle;
import com.yworks.yfiles.graph.styles.ShapeNodeShape;
import com.yworks.yfiles.graph.styles.ShapeNodeStyle;
import com.yworks.yfiles.layout.circular.CircularLayout;
import com.yworks.yfiles.layout.organic.OrganicLayout;
import com.yworks.yfiles.layout.organic.RemoveOverlapsStage;
import com.yworks.yfiles.layout.organic.StarSubstructureStyle;
import com.yworks.yfiles.utils.IListEnumerable;
import com.yworks.yfiles.view.CanvasComponent;
import com.yworks.yfiles.view.ContextConfigurator;
import com.yworks.yfiles.view.GraphComponent;
import com.yworks.yfiles.view.IRenderContext;
import com.yworks.yfiles.view.Pen;

import de.alsclo.voronoi.graph.Point;

/**
 * The Class VoronoiGraph.
 */
public class ProcessGraph {

	/** The use rectangle. */
	private boolean useRectangle = true;

	/** The insets. */
	private int insets = 10;

	/** The width. */
	private int width = 500;

	/** The height. */
	private int height = 500;

	/** The show decorations. */
	private boolean showDecorations = true;

	/** The left margin. */
	private int leftMargin = 0;

	/** The top margin. */
	private int topMargin = 0;

	/** The right margin. */
	private int rightMargin = 0;

	/** The bottom margin. */
	private int bottomMargin = 0;

	/** The scale. */
	private double scale = 1.0;

	/**
	 * The Enum SizeMode.
	 */
	private enum SizeMode {

		/** The use original size. */
		USE_ORIGINAL_SIZE,
		/** The specify width. */
		SPECIFY_WIDTH,
		/** The specify height. */
		SPECIFY_HEIGHT
	}

	/** The size mode. */
	private SizeMode sizeMode = SizeMode.USE_ORIGINAL_SIZE;

	/** The graph component. */
	private GraphComponent graphComponent;

	/** The export rect. */
	protected MutableRectangle exportRect;

	/**
	 * Instantiates a new voronoi graph.
	 */
	public ProcessGraph() {
		exportRect = new MutableRectangle(0, 0, width, height);
		graphComponent = new GraphComponent();

		IGraph g = graphComponent.getGraph();

		defaultNodeStyle = new ShapeNodeStyle();
		defaultNodeStyle.setPaint(Color.decode("#CCCCCC"));
		defaultNodeStyle.setShape(ShapeNodeShape.ROUND_RECTANGLE);
		g.getNodeDefaults().setStyle(defaultNodeStyle);
		g.getNodeDefaults().setSize(new SizeD(5, 5));

		roleNucleusNodeStyle = new ShapeNodeStyle();
		roleNucleusNodeStyle.setPaint(Color.decode("#0000DD"));
		roleNucleusNodeStyle.setShape(ShapeNodeShape.ELLIPSE);

		roleCellgroupNodeStyle = new ShapeNodeStyle();
		roleCellgroupNodeStyle.setPaint(Color.decode("#FFFFFF"));
		roleCellgroupNodeStyle.setPen(Pen.getWhite());
		roleCellgroupNodeStyle.setShape(ShapeNodeShape.ELLIPSE);

		activityNodeStyle = new ShapeNodeStyle();
		activityNodeStyle.setPaint(Color.decode("#FFFFFF"));
		activityNodeStyle.setShape(ShapeNodeShape.RECTANGLE);

		defaultEdgeStyle = new PolylineEdgeStyle();
		defaultEdgeStyle.setPen(Pen.getGray());
		defaultEdgeStyle.setTargetArrow(Arrow.NONE);
		g.getEdgeDefaults().setStyle(defaultEdgeStyle);

		processEdgeStyle = new PolylineEdgeStyle();
		processEdgeStyle.setPen(Pen.getBlack());
		processEdgeStyle.setTargetArrow(Arrow.SHORT);

	}

	/** The process edge style. */
	private PolylineEdgeStyle processEdgeStyle;

	/** The default edge style. */
	private PolylineEdgeStyle defaultEdgeStyle;

	/** The default node style. */
	private ShapeNodeStyle defaultNodeStyle;

	/** The activity node style. */
	private ShapeNodeStyle activityNodeStyle;

	/** The role node style. */
	private ShapeNodeStyle roleNucleusNodeStyle;

	private ShapeNodeStyle roleCellgroupNodeStyle;

	/** The nodes 2 roles. */
	private Map<INode, Role> nodes2roles = new HashMap<INode, Role>();

	/** The nodes 2 color. */
	private Map<INode, Color> nodes2color = new HashMap<INode, Color>();

	/** The roles 2 nodes. */
	private Map<Role, INode> roles2nodes = new HashMap<Role, INode>();

	/** The nodes 2 activities. */
	private Map<INode, ActivityDataitem> nodes2activities = new HashMap<INode, ActivityDataitem>();

	/** The activities 2 nodes. */
	private Map<ActivityDataitem, INode> activities2nodes = new HashMap<ActivityDataitem, INode>();

	/**
	 * Map activity.
	 *
	 * @param activity the activity
	 * @param node     the node
	 */
	private void mapActivity(ActivityDataitem activity, INode node) {
		activities2nodes.put(activity, node);
		nodes2activities.put(node, activity);
	}

	/**
	 * Gets the bounds.
	 *
	 * @return the bounds
	 */
	public RectD getBounds() {
		return graphComponent.getContentRect();
	}

	/**
	 * Gets the node at point.
	 *
	 * @param x the x
	 * @param y the y
	 * @return the node at point
	 */
	public INode getNodeAtPoint(double x, double y) {
		List<Point> points = new ArrayList<Point>();
		IListEnumerable e = getNodes();
		for (Iterator iterator = e.iterator(); iterator.hasNext();) {
			INode node = (INode) iterator.next();
			PointD pn = node.getLayout().getCenter();
			if (x == pn.x && y == pn.y) {
				return node;
			}
		}
		return null;
	}

	/**
	 * Checks if is role.
	 *
	 * @param n the n
	 * @return true, if is role
	 */
	public boolean isRole(INode n) {
		Role r = this.nodes2roles.get(n);
		if (r != null) {
			return true;
		}
		return false;
	}

	/**
	 * Gets the node points.
	 *
	 * @param offset the offset
	 * @return the node points
	 */
	public List<Point> getNodePoints(int offset) {
		List<Point> points = new ArrayList<Point>();
		IListEnumerable e = getNodes();
		for (Iterator iterator = e.iterator(); iterator.hasNext();) {
			INode node = (INode) iterator.next();
			PointD pn = node.getLayout().getCenter();
			CellPoint p = new CellPoint(pn.x, pn.y, offset);
			p.addNode(node);
			points.add(p);
		}
		return points;
	}

	/**
	 * Gets the nodes.
	 *
	 * @return the nodes
	 */
	private IListEnumerable getNodes() {
		IGraph g = this.graphComponent.getGraph();
		return g.getNodes();
	}

	/**
	 * Gets the activity node.
	 *
	 * @param activity the activity
	 * @return the activity node
	 */
	public INode getActivityNode(ActivityDataitem activity) {
		if (activity == null) {
			return null;
		}
		return activities2nodes.get(activity);
	}

	/**
	 * Gets the role node.
	 *
	 * @param role the role
	 * @return the role node
	 */
	public INode getRoleNode(Role role) {
		if (role == null) {
			return null;
		}
		return roles2nodes.get(role);
	}

	/**
	 * Map role.
	 *
	 * @param role the role
	 * @param node the node
	 */
	private void mapRole(Role role, INode node) {
		roles2nodes.put(role, node);
		nodes2roles.put(node, role);
	}

	private boolean USEGROUPING = false;

	/**
	 * Adds the role.
	 *
	 * @param role the role
	 */
	public void addRole(Role role) {
		INode roleNode = addRoleNode(role.getTitle());

		mapRole(role, roleNode);

		INode groupNode = null;
		if (USEGROUPING) {
			groupNode = this.graphComponent.getGraph().createGroupNode();
			this.graphComponent.getGraph().setStyle(groupNode, roleCellgroupNodeStyle);
			this.graphComponent.getGraph().setParent(roleNode, groupNode);
		}

//		List<String> localRoleActivities = role.getActivities();
		// List<Activity> globalRoleActivities =
		// Repository.getInstance().getActivities(role.getTitle());

		TreeMap<Activity, List<ActivityDataitem>> subactivities = Repository.getInstance()
				.getSubactivitiesWithResponsible(role.getTitle());
		Set<Activity> processes = subactivities.keySet();
		for (Activity process : processes) {
			List<ActivityDataitem> processActivities = subactivities.get(process);
			for (ActivityDataitem activityDataitem : processActivities) {
				INode activityNode = this.addActivityNode();

				if (USEGROUPING) {
					this.graphComponent.getGraph().setParent(activityNode, groupNode);
				}

				this.mapActivity(activityDataitem, activityNode);

				ShapeNodeStyle sns = this.activityNodeStyle.clone();

				Color roleColor = nodes2color.get(roleNode);

				sns.setPaint(roleColor);
				this.graphComponent.getGraph().setStyle(activityNode, sns);
				nodes2color.put(activityNode, roleColor);

				this.addEdge(roleNode, activityNode, defaultEdgeStyle);
			}
		}
	}

	/**
	 * Gets the color.
	 *
	 * @param node the node
	 * @return the color
	 */
	public Color getColor(INode node) {
		return this.nodes2color.get(node);
	}

	/**
	 * Adds the activity edge.
	 *
	 * @param a1 the a 1
	 * @param a2 the a 2
	 */
	public void addActivityEdge(ActivityDataitem a1, ActivityDataitem a2) {
		INode n1 = this.activities2nodes.get(a1);
		INode n2 = this.activities2nodes.get(a2);
		if (n1 != null && n2 != null) {
			addEdge(n1, n2, processEdgeStyle);
		}
	}

	private List<IEdge> activityEdges = new ArrayList<IEdge>();

	public List<IEdge> getActivityEdges() {
		return activityEdges;
	}

	/**
	 * Adds the edge.
	 *
	 * @param n1    the n 1
	 * @param n2    the n 2
	 * @param style the style
	 * @return the i edge
	 */
	public IEdge addEdge(INode n1, INode n2, IEdgeStyle style) {
		IGraph g = graphComponent.getGraph();
		IEdge e = g.createEdge(n1, n2, style);

//		if (!this.isRole(n1)) {
		activityEdges.add(e);
//		}

		return e;
	}

	/**
	 * Sets the node size default.
	 */
	public void setNodeSizeDefault() {
		setNodeSize(5);
	}

	/**
	 * Sets the node size.
	 *
	 * @param size the new node size
	 */
	public void setNodeSize(int size) {
		IGraph g = graphComponent.getGraph();
		g.getNodeDefaults().setSize(new SizeD(size, size));
	}

	/**
	 * Adds the processes.
	 *
	 * @param activities the activities
	 */
	public void addProcesses(List<Activity> activities) {
		for (Activity activity : activities) {

			ActivityDataitem source = null;
			ActivityDataitem target = null;

			List<ActivityDataitem> subactivities = activity.getSubactivities();
			for (ActivityDataitem sub : subactivities) {
				Role responsibleRole = Repository.getInstance().getRole(sub.getResponsible());
				if (responsibleRole != null) {
					if (sub.getTitle() != null) {
						if (source == null) {
							source = sub;
						}
						if (target != null) {
							source = target;
						}
						target = sub;

						if (source != target) {
							addActivityEdge(source, target);
						}
					}
				}
			}

		}
	}

	/**
	 * Adds the roles.
	 *
	 * @param roles the roles
	 */
	public void addRoles(List<Role> roles) {
		for (Role role : roles) {
			addRole(role);
		}
	}

	/**
	 * Adds the role node.
	 *
	 * @param label the label
	 * @return the i node
	 */
	public INode addRoleNode(String label) {
		IGraph g = graphComponent.getGraph();
		setNodeSize(10);

		Random randomGenerator = new Random();
		int red = randomGenerator.nextInt(256);
		int green = randomGenerator.nextInt(256);
		int blue = randomGenerator.nextInt(256);

		Color randomColour = new Color(red, green, blue);

		ShapeNodeStyle sns = roleNucleusNodeStyle.clone();
		sns.setPaint(randomColour);

		INode n = g.createNode(new PointD(0, 0), sns);

		nodes2color.put(n, randomColour);

		g.addLabel(n, label, ExteriorLabelModel.SOUTH);

		setNodeSizeDefault();
		return n;
	}

	/**
	 * Adds the activity node.
	 *
	 * @return the i node
	 */
	public INode addActivityNode() {
		IGraph g = graphComponent.getGraph();
		setNodeSize(3);
		INode n = g.createNode(new PointD(0, 0), activityNodeStyle);
		setNodeSizeDefault();
		return n;
	}

	/**
	 * Adds the default node.
	 *
	 * @return the i node
	 */
	public INode addDefaultNode() {
		IGraph g = graphComponent.getGraph();
		setNodeSizeDefault();
		INode n = g.createNode(new PointD(0, 0), defaultNodeStyle);
		return n;
	}

	/**
	 * Layout.
	 */
	public void layout() {
		IGraph g = graphComponent.getGraph();

		OrganicLayout layout = new OrganicLayout();
		layout.setCompactnessFactor(1.0);
		layout.setMinimumNodeDistance(20.0);
		layout.setStarSubstructureStyle(layout.getStarSubstructureStyle().RADIAL);
		layout.setGroupNodeCompactness(1.0);
		layout.setAutomaticGroupNodeCompactionEnabled(true);
		layout.setClusteringQuality(1.0);
		layout.setSmartComponentLayoutEnabled(true);
		g.applyLayout(layout);
		
		RemoveOverlapsStage r = new RemoveOverlapsStage(10);
		g.applyLayout(r);
	}

	/**
	 * Export svg.
	 *
	 * @param filename the filename
	 */
	public void exportSvg(String filename) {
		graphComponent.fitGraphBounds(new InsetsD(insets, insets, insets, insets));
		exportRect.reshape(graphComponent.getContentRect());
		this.writeSVG2File(filename);
	}

	/**
	 * Export to SVG element.
	 *
	 * @return the element
	 */
	private Element convertCanvas2SVG() {
		// Create a SVG document.
		DOMImplementation impl = SVGDOMImplementation.getDOMImplementation();
		String svgNS = SVGDOMImplementation.SVG_NAMESPACE_URI;
		SVGDocument doc = (SVGDocument) impl.createDocument(svgNS, "svg", null);

		doc.getDocumentElement().setAttribute("width", exportRect.getWidth() + "px");
		doc.getDocumentElement().setAttribute("height", exportRect.getHeight() + "px");

		// Create a converter for this document.
		SVGGraphics2D svgGraphics2D = new SVGGraphics2D(doc);

		// paint the content of the exporting graph component to the Graphics object
		paint(createExportGraphComponent(), svgGraphics2D);

		svgGraphics2D.dispose();
		Element svgRoot = svgGraphics2D.getRoot(doc.getDocumentElement());
		svgRoot.setAttributeNS(XMLConstants.XMLNS_NAMESPACE_URI,
				XMLConstants.XMLNS_PREFIX + ":" + XMLConstants.XLINK_PREFIX, XMLConstants.XLINK_NAMESPACE_URI);
		return svgRoot;
	}

	/**
	 * Creates the context configurator.
	 *
	 * @return the context configurator
	 */
	private ContextConfigurator createContextConfigurator() {
		exportRect.reshape(graphComponent.getContentRect());
		// check if the rectangular region or the whole view port should be printed
		RectD regionToExport = useRectangle ? exportRect.toRectD() : createExportGraphComponent().getViewport();

		// create a configurator with the settings of the option panel
		ContextConfigurator configurator = new ContextConfigurator(regionToExport.getEnlarged(-1));
		setScale(configurator);
		// get the margins
		configurator.setMargins(new InsetsD(topMargin, leftMargin, bottomMargin, rightMargin));
		return configurator;
	}

	/**
	 * Sets the scale.
	 *
	 * @param configurator the new scale
	 */
	private void setScale(ContextConfigurator configurator) {
		// consider the zoom level
		double zoomedScale = scale * graphComponent.getZoom();

		// look if a fixed size has been specified
		switch (sizeMode) {
		case SPECIFY_WIDTH:
			zoomedScale = configurator.calculateScaleForWidth(zoomedScale * width);
			break;
		case SPECIFY_HEIGHT:
			zoomedScale = configurator.calculateScaleForHeight(zoomedScale * height);
			break;
		}
		configurator.setScale(zoomedScale);
	}

	/**
	 * Paint.
	 *
	 * @param canvas the canvas
	 * @param gfx    the gfx
	 */
	private void paint(CanvasComponent canvas, Graphics2D gfx) {
		gfx.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		final ContextConfigurator cnfg = createContextConfigurator();
		final Graphics2D graphics = (Graphics2D) gfx.create();
		try {
			// fill background. Could be also Colors.TRANSPARENT;
			Paint fill = canvas.getBackground();
			if (fill != null) {
				final Paint oldPaint = graphics.getPaint();
				graphics.setPaint(fill);
				graphics.fill(new Rectangle2D.Double(0, 0, cnfg.getViewWidth(), cnfg.getViewHeight()));
				graphics.setPaint(oldPaint);
			}

			// configure the Graphics transform
			final InsetsD margins = cnfg.getMargins();
			graphics.translate(margins.getLeft(), margins.getTop());
			IRenderContext paintContext = cnfg.createRenderContext(canvas);
			graphics.transform(paintContext.getToWorldTransform());

			// set the graphics clip
			final RectD clip = paintContext.getClip();
			if (clip != null) {
				graphics.clip(new Rectangle2D.Double(clip.getX(), clip.getY(), clip.getWidth(), clip.getHeight()));
			}

			// export the canvas content
			canvas.exportContent(paintContext).paint(paintContext, graphics);
		} finally {
			graphics.dispose();
		}
	}

	/**
	 * Gets the exporting graph component.
	 *
	 * @return the exporting graph component
	 */
	private GraphComponent createExportGraphComponent() {
		GraphComponent component = graphComponent;
		// check whether decorations (selection, handles, ...) should be hidden
		if (!showDecorations) {
			// if so, create a new GraphComponent with the same graph
			component = new GraphComponent();
			component.setSize(graphComponent.getSize());
			component.setGraph(graphComponent.getGraph());
			component.setViewPoint(graphComponent.getViewPoint());
			component.setBackground(graphComponent.getBackground());
			component.repaint();
		}
		return component;
	}

	/**
	 * Save to svg file.
	 *
	 * @param filename the filename
	 */
	private void writeSVG2File(String filename) {
		// append the correct file extension if it is missing
		if (!filename.endsWith(".svg")) {
			filename += ".svg";
		}

		// export to an SVG element
		Element svgRoot = convertCanvas2SVG();
		DocumentFragment svgDocumentFragment = svgRoot.getOwnerDocument().createDocumentFragment();
		svgDocumentFragment.appendChild(svgRoot);

		// write the SVG Document into the specified file
		try (FileOutputStream stream = new FileOutputStream(filename)) {
			OutputStreamWriter writer = new OutputStreamWriter(stream, "UTF-8");
			transformDocumentFragement2Writer(svgDocumentFragment, writer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Write document.
	 *
	 * @param svgDocument the svg document
	 * @param writer      the writer
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void transformDocumentFragement2Writer(DocumentFragment svgDocument, Writer writer) throws IOException {
		try {
			Source source = new DOMSource(svgDocument);
			Result result = new StreamResult(writer);
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer xformer = tf.newTransformer();
			xformer.transform(source, result);
		} catch (TransformerException e) {
			throw new IOException(e.getMessage());
		}
	}

}
