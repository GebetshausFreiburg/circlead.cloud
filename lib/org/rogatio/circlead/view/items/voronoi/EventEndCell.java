package org.rogatio.circlead.view.items.voronoi;

import java.awt.BasicStroke;
import java.awt.Color;
import java.util.ArrayList;

import de.alsclo.voronoi.graph.Edge;
import de.alsclo.voronoi.graph.Point;

/**
 * The Class EventEndCell.
 */
public class EventEndCell extends VoronoiCell {

	/**
	 * Instantiates a new event end cell.
	 *
	 * @param center the center
	 * @param edges the edges
	 */
	public EventEndCell(Point center, ArrayList<Edge> edges) {
		super(center, edges);
	}

	/* (non-Javadoc)
	 * @see org.rogatio.circlead.view.items.voronoi.VoronoiCell#create()
	 */
	@Override
	public Object create() {
		
		double POINT_SIZE = 5.0;
		
		Color c = background;
				
		if (this.getCenter() instanceof CellPoint) {
			c = (Color) this.getDataCell().getData("color");
		}

		this.graphics.setStroke(new BasicStroke());
		this.graphics.setPaint(c);
		this.graphics.fill(this);
		
		this.graphics.setPaint(Color.RED);
		double size = 1;
		this.graphics.fillOval((int) Math.round(this.getCenter().x - (int) (size * POINT_SIZE / 2)),
				(int) Math.round(this.getCenter().y - (int) (size * POINT_SIZE / 2)), (int) (size * POINT_SIZE),
				(int) (size * POINT_SIZE));
		
		this.graphics.setPaint(Color.white);
		this.graphics.drawOval((int) Math.round(this.getCenter().x - (int) (size * POINT_SIZE / 2)),
				(int) Math.round(this.getCenter().y - (int) (size * POINT_SIZE / 2)), (int) (size * POINT_SIZE),
				(int) (size * POINT_SIZE));
	
		return null;
	}

}
