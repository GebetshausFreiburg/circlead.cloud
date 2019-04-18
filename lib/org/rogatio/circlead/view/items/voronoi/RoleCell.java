package org.rogatio.circlead.view.items.voronoi;

import java.awt.BasicStroke;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;

import de.alsclo.voronoi.graph.Edge;
import de.alsclo.voronoi.graph.Point;

public class RoleCell extends VoronoiCell {

	public RoleCell(Point center, ArrayList<Edge> edges) {
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
		int size = 3;
		this.graphics.fillOval((int) Math.round(this.getCenter().x - (int) (size * POINT_SIZE / 2)),
				(int) Math.round(this.getCenter().y - (int) (size * POINT_SIZE / 2)), (int) (size * POINT_SIZE),
				(int) (size * POINT_SIZE));
		
		String label = "";
		try {
			label = this.getDataCell().getName();
		} catch (Exception e) {

		}
		
		graphics.setFont(new Font("Arial", Font.PLAIN, 8)); 
		graphics.drawString(label, (int) this.getCenter().x, (int) this.getCenter().y + 20);
		
		return null;
	}

}
