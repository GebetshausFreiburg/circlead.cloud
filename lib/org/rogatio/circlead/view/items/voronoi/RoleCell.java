package org.rogatio.circlead.view.items.voronoi;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;

import de.alsclo.voronoi.graph.Edge;
import de.alsclo.voronoi.graph.Point;

/**
 * The Class RoleCell.
 */
public class RoleCell extends VoronoiCell {

	/**
	 * Instantiates a new role cell.
	 *
	 * @param center the center
	 * @param edges the edges
	 */
	public RoleCell(Point center, ArrayList<Edge> edges) {
		super(center, edges);
	}

	/* (non-Javadoc)
	 * @see org.rogatio.circlead.view.items.voronoi.VoronoiCell#create()
	 */
	@Override
	public Object create() {

		Color c = background;

		if (this.getCenter() instanceof CellPoint) {
			c = (Color) this.getDataCell().getData("color");
		}

		this.graphics.setStroke(new BasicStroke());
		this.graphics.setPaint(c);
		this.graphics.fill(this);
		
		this.graphics.setPaint(c.brighter());
		int size = 15;

		this.graphics.fillOval((int) Math.round(this.getCenter().x - (int) (size / 2)),
				(int) Math.round(this.getCenter().y - (int) (size / 2)), (int) (size), (int) (size));

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
