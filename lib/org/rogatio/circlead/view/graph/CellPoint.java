package org.rogatio.circlead.view.graph;

import com.yworks.yfiles.graph.INode;

import de.alsclo.voronoi.graph.Point;

/**
 * The Class CellPoint.
 */
public class CellPoint extends Point {

	/** The node. */
	private INode node;
	
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
	
	/**
	 * Adds the node.
	 *
	 * @param node the node
	 */
	public void addNode(INode node) {
		this.node = node;
	}
	
	/**
	 * Gets the node.
	 *
	 * @return the node
	 */
	public INode getNode() {
		return node;
	}

}
