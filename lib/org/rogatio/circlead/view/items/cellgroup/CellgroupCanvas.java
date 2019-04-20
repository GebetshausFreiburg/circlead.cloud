package org.rogatio.circlead.view.items.cellgroup;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.rogatio.circlead.view.items.CellType;
import org.rogatio.circlead.view.items.DefaultCanvas;
import org.rogatio.circlead.view.items.ICell;
import org.rogatio.circlead.view.items.ILink;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Element;
import org.w3c.dom.svg.SVGDocument;

import com.yworks.yfiles.geometry.InsetsD;
import com.yworks.yfiles.geometry.MutableRectangle;
import com.yworks.yfiles.geometry.RectD;
import com.yworks.yfiles.geometry.SizeD;
import com.yworks.yfiles.graph.IGraph;
import com.yworks.yfiles.graph.INode;
import com.yworks.yfiles.layout.organic.OrganicLayout;
import com.yworks.yfiles.layout.organic.RemoveOverlapsStage;
import com.yworks.yfiles.view.CanvasComponent;
import com.yworks.yfiles.view.ContextConfigurator;
import com.yworks.yfiles.view.GraphComponent;
import com.yworks.yfiles.view.IRenderContext;

/**
 * The Class GraphCanvas.
 */
public class CellgroupCanvas extends DefaultCanvas {

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

	/** The default node size. */
	private int defaultNodeSize = 10;

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

	/**
	 * Gets the graph component.
	 *
	 * @return the graph component
	 */
	public GraphComponent getGraphComponent() {
		return graphComponent;
	}

	/**
	 * Gets the graph.
	 *
	 * @return the graph
	 */
	public IGraph getGraph() {
		return graph;
	}

	/** The size mode. */
	private SizeMode sizeMode = SizeMode.USE_ORIGINAL_SIZE;

	/** The graph component. */
	private GraphComponent graphComponent;

	/** The export rect. */
	private MutableRectangle exportRect;

	/** The graph. */
	private IGraph graph;

	/** The map node 2 cell. */
	private Map<INode, ICell> map_Node2Cell = new HashMap<INode, ICell>();

	/** The map activity 2 cell. */
	private Map<ActivityDataitem, ICell> map_Activity2Cell = new HashMap<ActivityDataitem, ICell>();

	/**
	 * Instantiates a new graph canvas.
	 */
	public CellgroupCanvas() {
		exportRect = new MutableRectangle(0, 0, width, height);
		graphComponent = new GraphComponent();
		graph = graphComponent.getGraph();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.rogatio.circlead.view.items.DefaultCanvas#createCell(org.rogatio.circlead
	 * .view.items.CellType)
	 */
	@Override
	public ICell createCell(CellType type) {

		if (type == CellType.DEFAULT) {
			return new CellgroupCell(this);
		}

		if (type == CellType.ROLE) {
			return new RoleCell(this);
		}

		if (type == CellType.ACTIVITY) {
			return new ActivityCell(this);
		}

		if (type == CellType.GATEWAY) {
			return new GatewayCell(this);
		}

		if (type == CellType.EVENT_START) {
			return new EventStartCell(this);
		}

		if (type == CellType.EVENT_END) {
			return new EventEndCell(this);
		}

		return new CellgroupCell(this);
	}

	/**
	 * Sets the node size default.
	 */
	public void setNodeSizeDefault() {
		graph.getNodeDefaults().setSize(new SizeD(defaultNodeSize, defaultNodeSize));
	}

	/**
	 * Sets the node size.
	 *
	 * @param size the new node size
	 */
	public void setNodeSize(int size) {
		graph.getNodeDefaults().setSize(new SizeD(size, size));
	}

	/**
	 * Gets the cell of role.
	 *
	 * @param title the title
	 * @return the cell of role
	 */
	public ICell getCellOfRole(String title) {
		for (ICell iCell : cells) {
			if (iCell.getType() == CellType.ROLE) {
				if (iCell.getName().equals(title)) {
					return iCell;
				}
			}
		}
		return null;
	}

	/**
	 * Gets the color of role.
	 *
	 * @param title the title
	 * @return the color of role
	 */
	public Color getColorOfRole(String title) {
		for (ICell iCell : cells) {
			if (iCell.getType() == CellType.ROLE) {
				if (iCell.getName().equals(title)) {
					return (Color) iCell.getData("color");
				}
			}
		}
		return null;
	}

	/**
	 * Adds the link.
	 *
	 * @param source the source
	 * @param target the target
	 * @return the i link
	 */
	public ILink addLink(ActivityDataitem source, ActivityDataitem target) {
		ICell s = map_Activity2Cell.get(source);
		ICell t = map_Activity2Cell.get(target);
		return this.addLink(s, t);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.rogatio.circlead.view.items.DefaultCanvas#addLink(org.rogatio.circlead.
	 * view.items.ICell, org.rogatio.circlead.view.items.ICell)
	 */
	@Override
	public ILink addLink(ICell source, ICell target) {
		ILink link = new ProcessLink(this);
		link.setSource(source);
		link.setTarget(target);
		links.add(link);
		link.create();
		return link;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.rogatio.circlead.view.items.DefaultCanvas#getBounds()
	 */
	public Rectangle2D getBounds() {
		Rectangle2D r = new Rectangle2D.Double();
		double x = this.graphComponent.getContentRect().x;
		double y = this.graphComponent.getContentRect().y;
		double w = this.graphComponent.getContentRect().width;
		double h = this.graphComponent.getContentRect().height;
		r.setFrame(x, y, w, h);
		return r;
	}

	/**
	 * Gets the cells.
	 *
	 * @return the cells
	 */
	public List<ICell> getCells() {
		return this.cells;
	}

	/**
	 * Inits the.
	 */
	public void init() {
		Repository repository = Repository.getInstance();
		for (String roleTitle : repository.getRoleTitles()) {
			ICell cell = createCell(CellType.ROLE);
			cell.setName(roleTitle);
			Role role = repository.getRole(roleTitle);
			if (role != null) {
				cell.setData("role", role);
			}
			addCell(cell);
		}

		Map<ActivityDataitem, String> activityMap = repository.getMapOfSubactivitiesToRoleTitle();
		for (ActivityDataitem activity : activityMap.keySet()) {

			ICell cell = null;
			if (activity.getBpmn().equalsIgnoreCase("start-event-none")) {
				cell = createCell(CellType.EVENT_START);
			} else if (activity.getBpmn().equalsIgnoreCase("end-event-none")) {
				cell = createCell(CellType.EVENT_END);
			} else if (activity.getBpmn().contains("gateway")) {
				cell = createCell(CellType.GATEWAY);
			} else {
				cell = createCell(CellType.ACTIVITY);
			}

			cell.setName(activity.getTitle());
			cell.setData("activity", activity);
			cell.setData("roletitle", activityMap.get(activity));
			addCell(cell);
		}

		for (Activity activity : repository.getActivities()) {
			List<ActivityDataitem> subactivities = activity.getSubactivities();
			for (ActivityDataitem subactivity : subactivities) {

				List<ActivityDataitem> childs = activity.getChildSubactivities(subactivity);
				for (ActivityDataitem targetActivity : childs) {
					ILink link = addLink(subactivity, targetActivity);
					link.setData("process", activity);
					if (link.getSource() != null) {
						link.getSource().setData("process", activity);
					}
					if (link.getTarget() != null) {
						link.getTarget().setData("process", activity);
					}
					activityLinks.add(link);
				}
			}
		}
	}

	/** The activity links. */
	private List<ILink> activityLinks = new ArrayList<ILink>();

	/**
	 * Gets the activity links.
	 *
	 * @return the activity links
	 */
	public List<ILink> getActivityLinks() {
		return activityLinks;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.rogatio.circlead.view.items.DefaultCanvas#addCell(org.rogatio.circlead.
	 * view.items.ICell)
	 */
	@Override
	public void addCell(ICell cell) {
		cells.add(cell);
		INode n = (INode) cell.create();
		map_Node2Cell.put(n, cell);
		if (cell.getData("activity") != null) {
			map_Activity2Cell.put((ActivityDataitem) cell.getData("activity"), cell);
		}
	}

	/**
	 * Export.
	 *
	 * @param filename the filename
	 */
	public void export(String filename) {
		graphComponent.fitGraphBounds(new InsetsD(insets, insets, insets, insets));
		exportRect.reshape(graphComponent.getContentRect());
		this.writeSVG2File(filename);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.rogatio.circlead.view.items.DefaultCanvas#layout()
	 */
	@Override
	public void layout() {
		OrganicLayout layout = new OrganicLayout();
		layout.setCompactnessFactor(1.0);
		layout.setMinimumNodeDistance(20.0);
		layout.setStarSubstructureStyle(layout.getStarSubstructureStyle().RADIAL);
		layout.setGroupNodeCompactness(1.0);
		layout.setAutomaticGroupNodeCompactionEnabled(true);
		layout.setClusteringQuality(1.0);
		layout.setSmartComponentLayoutEnabled(true);
		graph.applyLayout(layout);

		RemoveOverlapsStage r = new RemoveOverlapsStage(10);
		graph.applyLayout(r);
	}

	/**
	 * Creates the export graph component.
	 *
	 * @return the graph component
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
	 * Convert canvas 2 SVG.
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
