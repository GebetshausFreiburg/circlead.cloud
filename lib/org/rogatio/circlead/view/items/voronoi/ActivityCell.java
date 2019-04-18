package org.rogatio.circlead.view.items.voronoi;

import java.awt.BasicStroke;
import java.awt.Color;
import java.util.ArrayList;

import de.alsclo.voronoi.graph.Edge;
import de.alsclo.voronoi.graph.Point;

public class ActivityCell extends VoronoiCell {

	public ActivityCell(Point center, ArrayList<Edge> edges) {
		super(center, edges);
	}
	

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
		
		this.graphics.setPaint(c.brighter());
		graphics.fillRect((int) Math.round(this.getCenter().x - POINT_SIZE / 2), (int) Math.round(this.getCenter().y - POINT_SIZE / 2),
				(int) POINT_SIZE, (int) POINT_SIZE);

		this.graphics.setPaint(Color.WHITE);
		this.graphics.drawRect((int) Math.round(this.getCenter().x - POINT_SIZE / 2), (int) Math.round(this.getCenter().y - POINT_SIZE / 2),
				(int) POINT_SIZE, (int) POINT_SIZE);
		
		return null;
	}

}
