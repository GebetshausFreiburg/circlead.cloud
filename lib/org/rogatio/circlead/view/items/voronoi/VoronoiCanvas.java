package org.rogatio.circlead.view.items.voronoi;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Rectangle2D;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.apache.batik.anim.dom.SVGDOMImplementation;
import org.apache.batik.svggen.SVGGraphics2D;
import org.rogatio.circlead.view.items.CellType;
import org.rogatio.circlead.view.items.DefaultCanvas;
import org.rogatio.circlead.view.items.ICell;
import org.rogatio.circlead.view.items.ILink;
import org.rogatio.circlead.view.items.graph.GraphCanvas;
import org.rogatio.circlead.view.items.graph.RoleCell;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.svg.SVGDocument;

import com.yworks.yfiles.graph.IEdge;
import com.yworks.yfiles.graph.INode;
import com.yworks.yfiles.view.CanvasComponent;

import de.alsclo.voronoi.graph.Point;

/**
 * The Class VoronoiCanvas.
 */
public class VoronoiCanvas extends DefaultCanvas {

	/** The component. */
	private CanvasComponent component;

	/** The diagram. */
	private VoronoiDiagram diagram;

	/** The graph canvas. */
	private GraphCanvas graphCanvas;

	/** The point size. */
	private final double POINT_SIZE = 5.0;

	/** The points. */
	private List<Point> points = new ArrayList<Point>();

	private double maxX = 0;
	private double maxY = 0;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.rogatio.circlead.view.items.DefaultCanvas#setCells(java.util.List)
	 */
	@Override
	public void setCells(List<ICell> cells) {
		List<ICell> tcells = new ArrayList<ICell>();

		for (ICell cell : cells) {
			double x = cell.getPosition().getX();
			double y = cell.getPosition().getY();

			maxX = Math.max(x, maxX);
			maxY = Math.max(y, maxY);

			double offX = (this.getBounds().getWidth() - maxX) / 2;
			double offY = (this.getBounds().getHeight() - maxY) / 2;

			CellPoint p = new CellPoint(x + offX, y + offY);
			p.addCell(cell);
			cell.setData("point", p);
			points.add(p);
		}

		this.cells = tcells;
	}

	/**
	 * Instantiates a new voronoi canvas.
	 *
	 * @param cells the cells
	 */
	public VoronoiCanvas(GraphCanvas canvas) {
		this.graphCanvas = canvas;
		this.cells = canvas.getCells();
		this.component = new CanvasComponent();
		setBounds(canvas.getBounds());
		this.diagram = new VoronoiDiagram(this.points);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.rogatio.circlead.view.items.DefaultCanvas#setBounds(java.awt.geom.
	 * Rectangle2D)
	 */
	@Override
	public void setBounds(Rectangle2D bounds) {
		double hs = bounds.getHeight() + Math.abs(bounds.getX());
		double ws = bounds.getWidth() + Math.abs(bounds.getY());

		this.bounds = new Rectangle2D.Double(0, 0, ws, hs);

		this.setCells(cells);

		component.setBounds(new Rectangle((int) ws, (int) hs));

		int w = (int) this.bounds.getWidth();
		int h = (int) this.bounds.getHeight();

		int stepWidthBorder = 20;

		for (int i = 0; i < w; i += stepWidthBorder) {
			Point p = new Point(i, 0);
			if (!points.contains(p)) {
				points.add(p);
			}
		}
		for (int i = 0; i < h; i += stepWidthBorder) {
			Point p = new Point(0, i);
			if (!points.contains(p)) {
				points.add(p);
			}
		}
		for (int i = 0; i < h - stepWidthBorder; i += stepWidthBorder) {
			Point p = new Point(w - stepWidthBorder, i);
			if (!points.contains(p)) {
				points.add(p);
			}
		}
		for (int i = 0; i < w - stepWidthBorder; i += stepWidthBorder) {
			Point p = new Point(i, h - stepWidthBorder);
			if (!points.contains(p)) {
				points.add(p);
			}
		}

		this.diagram = new VoronoiDiagram(this.points);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.rogatio.circlead.view.items.DefaultCanvas#layout()
	 */
	@Override
	public void layout() {

		diagram.relax().relax();
	}

	private void paint(Graphics g) {
		Color background = new Color(255, 255, 255, 0);

		Graphics2D g2 = (Graphics2D) g;

		ArrayList<VoronoiCell> cells = this.diagram.getCells();
		for (VoronoiCell cell : cells) {
			cell.setGraphics(g2);
			cell.create();
		}
		
		/*
		 * g2.setStroke(new BasicStroke(1)); g2.setPaint(Color.DARK_GRAY);
		 * 
		 * diagram.getGraph().edgeStream().filter(e -> e.getA() != null && e.getB() !=
		 * null).forEach(e -> { Point a = e.getA().getLocation(); Point b =
		 * e.getB().getLocation(); g2.drawLine((int) a.x, (int) a.y, (int) b.x, (int)
		 * b.y); });
		 */

		Polygon arrowHead = new Polygon();
		arrowHead.addPoint(0, -4);
		arrowHead.addPoint(-3, -9);
		arrowHead.addPoint(3, -9);
		
		g2.setPaint(Color.WHITE);

		List<ILink> links = graphCanvas.getActivityLinks();
		for (ILink iLink : links) {
			IEdge edge = (IEdge) iLink.getData("edge");
			Point a = this.getStart(edge);
			Point b = this.getEnd(edge);
		
			if (a != null && b != null) {
				Line2D.Double line = new Line2D.Double((int) a.x, (int) a.y, (int) b.x, (int) b.y);
				
				AffineTransform tx = new AffineTransform();
				tx.setToIdentity();
				double angle = Math.atan2(line.y2 - line.y1, line.x2 - line.x1);
				tx.translate(line.x2, line.y2);
				tx.rotate((angle - Math.PI / 2d));
				
				Graphics2D gr = (Graphics2D) g2.create();
				gr.setPaint(Color.WHITE);
				gr.setTransform(tx);
				gr.fill(arrowHead);
				
				int dX = (int) (Math.round(0 + (4 * Math.cos(angle))));
				int dY = (int) (Math.round(0 + (4 * Math.sin(angle))));
				gr.dispose();
				
				g2.drawLine((int) a.x+dX, (int) a.y+dY, (int) b.x-dX, (int) b.y-dY);
				
			}
		}
		
	}

	/**
	 * Export.
	 *
	 * @param filename the filename
	 */
	public void export(String filename) {
		DOMImplementation domImpl = SVGDOMImplementation.getDOMImplementation();

		// Create a document with the appropriate namespace
		SVGDocument document = (SVGDocument) domImpl.createDocument(SVGDOMImplementation.SVG_NAMESPACE_URI, "svg",
				null);

		// Create an instance of the SVG Generator
		SVGGraphics2D graphics = new SVGGraphics2D(document);
		// draw onto the SVG Graphics object
		graphics.setSVGCanvasSize(new Dimension((int) this.getBounds().getWidth(), (int) this.getBounds().getHeight()));
		// graphics.setBackground(Color.BLACK);
		this.paint(graphics);

		// Finally, stream out SVG to the standard output using UTF-8
		// character to byte encoding
		boolean useCSS = true; // we want to use CSS style attribute
		try {
			Writer out = new OutputStreamWriter(new FileOutputStream(filename), "UTF-8");
			graphics.stream(out, useCSS);
			out.flush();
			out.close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Gets the start.
	 *
	 * @param edge the edge
	 * @return the start
	 */
	private Point getStart(IEdge edge) {
		if (edge == null) {
			return null;
		}
		for (Point site : this.diagram.getGraph().getSitePoints()) {
			if (site instanceof CellPoint) {
				CellPoint p = (CellPoint) site;
				INode n = (INode) p.getCell().getData("node");
				if (n != null) {
					if (n.equals(edge.getSourceNode())) {
						return site;
					}
				}
			}
		}
		return null;
	}

	/**
	 * Gets the end.
	 *
	 * @param edge the edge
	 * @return the end
	 */
	private Point getEnd(IEdge edge) {
		if (edge == null) {
			return null;
		}
		for (Point site : this.diagram.getGraph().getSitePoints()) {
			if (site instanceof CellPoint) {
				CellPoint p = (CellPoint) site;
				INode n = (INode) p.getCell().getData("node");
				if (n != null) {
					if (n.equals(edge.getTargetNode())) {
						return site;
					}
				}
			}
		}
		return null;
	}

}
