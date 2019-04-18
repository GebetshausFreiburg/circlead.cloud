package org.rogatio.circlead.view.items.voronoi;

import org.rogatio.circlead.view.items.ICell;

import de.alsclo.voronoi.graph.Point;

// TODO: Auto-generated Javadoc
/**
 * The Class CellPoint.
 */
public class CellPoint extends Point {

	/** The graph cell. */
	private ICell graphCell;
	
	/**
	 * Instantiates a new cell point.
	 *
	 * @param x the x
	 * @param y the y
	 * @param offset the offset
	 */
	public CellPoint(double x, double y, double offset) {
		super(x+offset, y+offset);
	}
	
	/**
	 * Instantiates a new cell point.
	 *
	 * @param x the x
	 * @param y the y
	 */
	public CellPoint(double x, double y) {
		super(x, y);
	}
	
	public void addCell(ICell node) {
		this.graphCell = node;
	}
	
	public ICell getCell() {
		return graphCell;
	}

}
