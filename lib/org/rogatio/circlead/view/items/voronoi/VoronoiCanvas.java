package org.rogatio.circlead.view.items.voronoi;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
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
import org.rogatio.circlead.view.items.graph.GraphCell;
import org.rogatio.circlead.view.items.graph.RoleCell;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.svg.SVGDocument;

import com.yworks.yfiles.graph.IEdge;
import com.yworks.yfiles.graph.INode;
import com.yworks.yfiles.view.CanvasComponent;

import de.alsclo.voronoi.graph.Point;

// TODO: Auto-generated Javadoc
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

	/**
	 * Sets the graph canvas.
	 *
	 * @param graphCanvas the new graph canvas
	 */
	public void setGraphCanvas(GraphCanvas graphCanvas) {
		this.graphCanvas = graphCanvas;
	}

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
			CellPoint p = new CellPoint(x, y);
			p.addNode(cell);
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
	public VoronoiCanvas(List<ICell> cells) {
		this.setCells(cells);
		this.diagram = new VoronoiDiagram(this.points);
		component = new CanvasComponent();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.rogatio.circlead.view.items.DefaultCanvas#setBounds(java.awt.geom.
	 * Rectangle2D)
	 */
	@Override
	public void setBounds(Rectangle2D bounds) {
		this.bounds = bounds;
		component.setBounds(new Rectangle((int) bounds.getWidth(), (int) bounds.getHeight()));
		
		int offset = 50;

		int w = (int) this.bounds.getWidth() + offset * 2;
		int h = (int) this.bounds.getHeight() + offset * 2;
		
		for (int i = 0; i < w; i += 50) {
			Point p = new Point(i, 0);
			points.add(p);
		}
		for (int i = 0; i < h; i += 50) {
			Point p = new Point(0, i);
			points.add(p);
		}
		for (int i = 0; i < h - 50; i += 50) {
			Point p = new Point(i, h - 50);
			points.add(p);
		}
		for (int i = 0; i < w - 50; i += 50) {
			Point p = new Point(w - 50, i);
			points.add(p);
		}
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

	/**
	 * Paint.
	 *
	 * @param g the g
	 */
	private void paint(Graphics g) {
		Color background = new Color(255, 255, 255, 0);

		Graphics2D g2 = (Graphics2D) g;

		ArrayList<VoronoiCell> cells = this.diagram.getCells();
		for (VoronoiCell cell : cells) {

			Color c = background;
			if (cell.getCenter() instanceof CellPoint) {
				c = (Color) cell.getDataCell().getData("color");
			}

			g2.setStroke(new BasicStroke());
			g2.setPaint(c);
			g2.fill(cell);

		}

		for (Point site : diagram.getGraph().getSitePoints()) {
			g2.setStroke(new BasicStroke());

			if (site instanceof CellPoint) {
				g2.setPaint(Color.WHITE);

				if (((CellPoint) site).getNode() instanceof RoleCell) {
					int size = 3;
					g2.fillOval((int) Math.round(site.x - (int) (size * POINT_SIZE / 2)),
							(int) Math.round(site.y - (int) (size * POINT_SIZE / 2)), (int) (size * POINT_SIZE),
							(int) (size * POINT_SIZE));
				} else {
					g2.fillRect((int) Math.round(site.x - POINT_SIZE / 2), (int) Math.round(site.y - POINT_SIZE / 2),
							(int) POINT_SIZE, (int) POINT_SIZE);
				}
			} else {
				g2.setPaint(background);
				double size = 0.5;
				g2.fillOval((int) Math.round(site.x - (int) (size * POINT_SIZE / 2)),
						(int) Math.round(site.y - (int) (size * POINT_SIZE / 2)), (int) (size * POINT_SIZE),
						(int) (size * POINT_SIZE));
			}

		}

		/*
		 * g2.setStroke(new BasicStroke(1)); g2.setPaint(Color.DARK_GRAY);
		 * 
		 * diagram.getGraph().edgeStream().filter(e -> e.getA() != null && e.getB() !=
		 * null).forEach(e -> { Point a = e.getA().getLocation(); Point b =
		 * e.getB().getLocation(); g2.drawLine((int) a.x, (int) a.y, (int) b.x, (int)
		 * b.y); });
		 */

		g2.setStroke(new BasicStroke());
		g2.setPaint(Color.WHITE);
		for (Point site : diagram.getGraph().getSitePoints()) {
			if (site instanceof CellPoint) {
				if (((CellPoint) site).getNode().getType() == CellType.ROLE) {
					String label = "";
					try {
						label = ((CellPoint) site).getNode().getName();
					} catch (Exception e) {

					}
					g2.drawString(label, (int) site.x, (int) site.y + 20);
				}
			}
		}

		Polygon arrowHead = new Polygon();
		arrowHead.addPoint(0, -4);
		arrowHead.addPoint(-2, -7);
		arrowHead.addPoint(2, -7);

		g2.setPaint(Color.WHITE);

		List<ILink> links = graphCanvas.getActivityLinks();
		for (ILink iLink : links) {
			IEdge edge = (IEdge) iLink.getData("edge");
			Point a = this.getStart(edge);
			Point b = this.getEnd(edge);

			if (a != null && b != null) {
				Line2D.Double line = new Line2D.Double((int) a.x, (int) a.y, (int) b.x, (int) b.y);
				g2.drawLine((int) a.x, (int) a.y, (int) b.x, (int) b.y);

				AffineTransform tx = new AffineTransform();
				tx.setToIdentity();
				double angle = Math.atan2(line.y2 - line.y1, line.x2 - line.x1);
				tx.translate(line.x2, line.y2);
				tx.rotate((angle - Math.PI / 2d));

				Graphics2D gr = (Graphics2D) g2.create();
				gr.setTransform(tx);
				gr.fill(arrowHead);
				gr.dispose();
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
				INode n = (INode) p.getNode().getData("node");
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
				INode n = (INode) p.getNode().getData("node");
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
