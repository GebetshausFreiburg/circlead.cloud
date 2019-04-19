package org.rogatio.circlead.view.items.voronoi;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;

import org.rogatio.circlead.view.items.DefaultLink;
import org.rogatio.circlead.view.items.ILink;

import com.yworks.yfiles.graph.IEdge;
import com.yworks.yfiles.graph.INode;

import de.alsclo.voronoi.graph.Point;

/**
 * The Class ProcessLink.
 */
public class ProcessLink extends DefaultLink {

	/** The graph link. */
	private ILink graphLink;
	
	/** The graphics. */
	private Graphics2D graphics;
	
	/** The diagram. */
	private VoronoiDiagram diagram;

	/**
	 * Instantiates a new process link.
	 *
	 * @param graphLink the graph link
	 * @param graphics the graphics
	 * @param diagram the diagram
	 */
	public ProcessLink(ILink graphLink, Graphics2D graphics, VoronoiDiagram diagram) {
		this.graphLink = graphLink;
		this.graphics = graphics;
		this.diagram = diagram;
	}

	/* (non-Javadoc)
	 * @see org.rogatio.circlead.view.items.DefaultLink#create()
	 */
	@Override
	public Object create() {

		// create simple arrow
		Polygon arrowHead = new Polygon();
		arrowHead.addPoint(0, -4);
		arrowHead.addPoint(-3, -9);
		arrowHead.addPoint(3, -9);

		graphics.setPaint(Color.WHITE);

		IEdge edge = (IEdge) graphLink.getData("edge");
		Point a = this.getStart(edge);
		Point b = this.getEnd(edge);

		if (a != null && b != null) {
			Line2D.Double line = new Line2D.Double((int) a.x, (int) a.y, (int) b.x, (int) b.y);

			AffineTransform tx = new AffineTransform();
			tx.setToIdentity();
			double angle = Math.atan2(line.y2 - line.y1, line.x2 - line.x1);
			tx.translate(line.x2, line.y2);
			tx.rotate((angle - Math.PI / 2d));

			Graphics2D gr = (Graphics2D) graphics.create();
			gr.setPaint(Color.WHITE);
			gr.setTransform(tx);
			gr.fill(arrowHead);

			// cut length of arrow, so it not conflicts with node-cell
			int dX = (int) (Math.round(0 + (4 * Math.cos(angle))));
			int dY = (int) (Math.round(0 + (4 * Math.sin(angle))));
			gr.dispose();

			graphics.drawLine((int) a.x + dX, (int) a.y + dY, (int) b.x - dX, (int) b.y - dY);

		}

		return null;
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
