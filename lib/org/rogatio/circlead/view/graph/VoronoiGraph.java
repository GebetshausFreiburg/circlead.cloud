package org.rogatio.circlead.view.graph;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.batik.anim.dom.SVGDOMImplementation;
import org.apache.batik.constants.XMLConstants;
import org.apache.batik.dom.GenericDOMImplementation;
import org.apache.batik.svggen.CachedImageHandlerBase64Encoder;
import org.apache.batik.svggen.GenericImageHandler;
import org.apache.batik.svggen.SVGGeneratorContext;
import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.batik.svggen.SVGSyntax;
import org.w3c.dom.CDATASection;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.svg.SVGDocument;
import org.w3c.dom.svg.SVGSVGElement;

import com.yworks.yfiles.geometry.InsetsD;
import com.yworks.yfiles.geometry.RectD;
import com.yworks.yfiles.graph.IEdge;
import com.yworks.yfiles.graph.INode;
import com.yworks.yfiles.view.CanvasComponent;
import com.yworks.yfiles.view.ContextConfigurator;
import com.yworks.yfiles.view.IRenderContext;

import de.alsclo.voronoi.graph.Edge;
import de.alsclo.voronoi.graph.Point;

// TODO: Auto-generated Javadoc
/**
 * The Class VoronoiGraph.
 */
public class VoronoiGraph extends CanvasComponent {

	/** The Constant POINT_SIZE. */
	private static final double POINT_SIZE = 5.0;

	/** The diagram. */
	private final VoronoiExtended diagram;

	/** The process graph. */
	private ProcessGraph processGraph;

	/**
	 * Instantiates a new voronoi graph.
	 *
	 * @param processGraph the process graph
	 */
	public VoronoiGraph(ProcessGraph processGraph) {
		this.processGraph = processGraph;

		int offset = 50;

		List<Point> points = processGraph.getNodePoints(offset);

		RectD r = processGraph.getBounds();

		int w = (int) r.width + offset * 2;
		int h = (int) r.height + offset * 2;

		this.setBounds((int) r.x, (int) r.y, w, h);

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

		boolean drawgrid = false;
		if (drawgrid) {
			int step = 10;
			for (int i = step; i < h - step; i += step) {
				for (int ii = step; ii < w - step; ii += step) {
					Point p = new Point(ii, i);
					points.add(p);
				}
			}
		}

		this.diagram = new VoronoiExtended(points);
		diagram.setProcessGraph(processGraph);
		diagram.relax().relax();

	}

	/**
	 * Export png.
	 *
	 * @param filename the filename
	 */
	public void exportPng(String filename) {
		try {
			BufferedImage image = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
			this.paint(image.getGraphics());
			File outputfile = new File(filename);
			ImageIO.write(image, "png", outputfile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Export svg.
	 *
	 * @param filename the filename
	 */
	public void exportSvg(String filename) {
		DOMImplementation domImpl = SVGDOMImplementation.getDOMImplementation();

		// Create a document with the appropriate namespace
		SVGDocument document = (SVGDocument) domImpl.createDocument(SVGDOMImplementation.SVG_NAMESPACE_URI, "svg",
				null);

		// Create an instance of the SVG Generator
		SVGGraphics2D graphics = new SVGGraphics2D(document);
		// draw onto the SVG Graphics object
		graphics.setSVGCanvasSize(new Dimension(this.getWidth(), this.getHeight()));
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.Component#paint(java.awt.Graphics)
	 */
	public void paint(Graphics g) {

		Color background = new Color(255, 255, 255, 0);

		Graphics2D g2 = (Graphics2D) g;

		ArrayList<VoronoiCell> cells = this.diagram.getCells();
		for (VoronoiCell cell : cells) {

			Color c = background;
			if (cell.getCenter() instanceof CellPoint) {
				c = processGraph.getColor(((CellPoint) cell.getCenter()).getNode());// graph.getColor(diagram.getNode(cell));
			}

			g2.setStroke(new BasicStroke());
			g2.setPaint(c);
			g2.fill(cell);

//			List<Edge> edges = cell.getEdges();
//			for (Edge edge : edges) {
//				g2.setStroke(new BasicStroke(3));
//				g2.setPaint(Color.DARK_GRAY);
//				g2.drawLine((int) edge.getSite1().x, (int) edge.getSite1().y, (int) edge.getSite2().x,
//						(int) edge.getSite2().y);
//			}

		}

		for (Point site : diagram.getGraph().getSitePoints()) {
			g2.setStroke(new BasicStroke());

			if (site instanceof CellPoint) {
				g2.setPaint(Color.WHITE);
				if (processGraph.isRole(((CellPoint) site).getNode())) {
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
				INode n = ((CellPoint) site).getNode();
				if (processGraph.isRole(n)) {
					String label = "";
					try {
						label = n.getLabels().first().getText();
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
		for (IEdge edge : this.processGraph.getActivityEdges()) {
			Point a = this.getStart(edge);
			Point b = this.getEnd(edge);
			if (!processGraph.isRole(edge.getSourceNode())) {
				Line2D.Double line = new Line2D.Double((int) a.x, (int) a.y, (int) b.x, (int) b.y);
				//g2.drawLine(arg0, arg1, arg2, arg3);
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
	 * Gets the start.
	 *
	 * @param edge the edge
	 * @return the start
	 */
	private Point getStart(IEdge edge) {
		for (Point site : this.diagram.getGraph().getSitePoints()) {
			if (site instanceof CellPoint) {
				CellPoint p = (CellPoint) site;
//				if (processGraph.isRole(p.getNode())) {
				if (p.getNode().equals(edge.getSourceNode())) {
					return site;
				}
//				}
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
		for (Point site : this.diagram.getGraph().getSitePoints()) {
			if (site instanceof CellPoint) {
				CellPoint p = (CellPoint) site;
//				if (processGraph.isRole(p.getNode())) {
				if (p.getNode().equals(edge.getTargetNode())) {
					return site;
				}
//				}
			}
		}
		return null;
	}

}
