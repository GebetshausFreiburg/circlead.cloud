package org.rogatio.circlead.view.items.voronoi;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
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
import org.apache.batik.svggen.SVGGeneratorContext;
import org.rogatio.circlead.util.PropertyUtil;
import org.rogatio.circlead.view.items.DefaultCanvas;
import org.rogatio.circlead.view.items.ICell;
import org.rogatio.circlead.view.items.ILink;
import org.rogatio.circlead.view.items.cellgroup.CellgroupCanvas;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Element;
import org.w3c.dom.svg.SVGDocument;

import com.yworks.yfiles.view.CanvasComponent;

import de.alsclo.voronoi.graph.Point;

/**
 * The Class VoronoiCanvas is used to draw all cells onto it and export them to a svg-image
 */
public class VoronoiCanvas extends DefaultCanvas {
	
	/** The diagram which calculate the voronoi-structure */
	private VoronoiDiagram diagram;

	/** The graph canvas which gives the starting points for the voronoi-diagram */
	private CellgroupCanvas graphCanvas;

	/** The points which are set to the voronoi-diagram */
	private List<Point> points = new ArrayList<Point>();

	/** The max x-value in the diagram */
	private double maxX = 0;
	
	/** The max y-value in the diagram */
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
			// get x and y of a cell
			double x = cell.getPosition().getX();
			double y = cell.getPosition().getY();

			// calculate the maximum point-dimensions
			maxX = Math.max(x, maxX);
			maxY = Math.max(y, maxY);

			// center all points by calculate an offset to the outer canvas
			double offX = (this.getBounds().getWidth() - maxX) / 2;
			double offY = (this.getBounds().getHeight() - maxY) / 2;

			//add new points to cell
			CellPoint p = new CellPoint(x + offX, y + offY);
			p.addCell(cell);
			cell.setData("point", p);
			points.add(p);
		}

		// replace cells with cells and offset-correction
		this.cells = tcells;
	}

	/**
	 * Instantiates a new voronoi canvas.
	 *
	 * @param canvas the canvas
	 */
	public VoronoiCanvas(CellgroupCanvas canvas) {
		this.graphCanvas = canvas;
		this.cells = canvas.getCells();
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
		// caclulate the outer bounds of the canvas
		double hs = bounds.getHeight() + Math.abs(bounds.getX());
		double ws = bounds.getWidth() + Math.abs(bounds.getY());

		// set bounds
		this.bounds = new Rectangle2D.Double(0, 0, ws, hs);

		//set offset-correction to given bounds
		this.setCells(cells);
	
		int w = (int) this.bounds.getWidth();
		int h = (int) this.bounds.getHeight();

		// calculate the outer points in voronoi-diagram to beautify used voronoi-cells
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

		// instantiates voronoi-diagram
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

	/**
	 * Paint method
	 *
	 * @param g the g
	 */
	private void paint(Graphics g) {
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

		List<ILink> links = graphCanvas.getActivityLinks();
		for (ILink iLink : links) {
			ProcessLink link = new ProcessLink(iLink, g2, diagram);
			link.create();
		}

	}

	
	/**
	 * Export to svg
	 *
	 * @param filename the filename
	 */
	public void export(String filename) {

		DOMImplementation domImpl = SVGDOMImplementation.getDOMImplementation();

		// Create a document with the appropriate namespace
		SVGDocument document = (SVGDocument) domImpl.createDocument(SVGDOMImplementation.SVG_NAMESPACE_URI, "svg",
				null);

		// Set context to circlead
		SVGGeneratorContext ctx = SVGGeneratorContext.createDefault(document);
		ctx.setComment("Generated by Circlead v" + PropertyUtil.APPLICATION_VERSION);
		
		// Create an instance of the SVG Generator
		SVGCircleadGraphics2D graphics = new SVGCircleadGraphics2D(ctx, true);

		// set canvas size
		graphics.setSVGCanvasSize(new Dimension((int) this.getBounds().getWidth(), (int) this.getBounds().getHeight()));

//		Element root = graphics.getRoot();
//		root.setAttributeNS(null, "viewBox", "0 0 "+(int) this.getBounds().getWidth()+" "+(int)this.getBounds().getHeight()+""); 
		
		// draw onto the SVG Graphics object
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

}
