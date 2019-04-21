package org.rogatio.circlead.view.items.voronoi;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.util.ArrayList;

import de.alsclo.voronoi.graph.Edge;
import de.alsclo.voronoi.graph.Point;

/**
 * The Class GatewayCell.
 */
public class GatewayCell extends VoronoiCell {

	/**
	 * Instantiates a new gateway cell.
	 *
	 * @param center the center
	 * @param edges the edges
	 */
	public GatewayCell(Point center, ArrayList<Edge> edges) {
		super(center, edges);
	}

	/**
	 * Creates the diamond.
	 *
	 * @param s the s
	 * @return the general path
	 */
	public static GeneralPath createDiamond(final float s) {
	      final GeneralPath p0 = new GeneralPath();
	      p0.moveTo(0.0f, -s);
	      p0.lineTo(s, 0.0f);
	      p0.lineTo(0.0f, s);
	      p0.lineTo(-s, 0.0f);
	      p0.closePath();
	      return p0;
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
		
		GeneralPath diamond = createDiamond(4);
		
		AffineTransform tr = new AffineTransform();
		tr.translate(this.getCenter().x, this.getCenter().y);
		diamond.transform(tr);
		
		this.graphics.fill(diamond);
		
		this.graphics.setPaint(Color.WHITE);
		this.graphics.draw(diamond);

		return null;
	}

}
