package org.rogatio.circlead.view.items.cellgroup;

import java.awt.Color;
import java.awt.geom.Point2D;

import org.rogatio.circlead.view.items.DefaultCell;

import com.yworks.yfiles.geometry.SizeD;
import com.yworks.yfiles.graph.IGraph;
import com.yworks.yfiles.graph.INode;
import com.yworks.yfiles.graph.styles.ShapeNodeShape;
import com.yworks.yfiles.graph.styles.ShapeNodeStyle;
import com.yworks.yfiles.view.GraphComponent;

/**
 * The Class GraphCell.
 */
public class CellgroupCell extends DefaultCell {

	/** The style. */
	protected ShapeNodeStyle style;
	
	/** The graph component. */
	protected GraphComponent graphComponent;
	
	/** The graph. */
	protected IGraph graph;
	
	/** The canvas. */
	protected CellgroupCanvas canvas;

	/**
	 * Instantiates a new graph cell.
	 *
	 * @param canvas the canvas
	 */
	public CellgroupCell(CellgroupCanvas canvas) {
		this.graphComponent = canvas.getGraphComponent();
		this.graph = graphComponent.getGraph();
		this.canvas = canvas;

		style = new ShapeNodeStyle();
		style.setPaint(Color.decode("#CCCCCC"));
		style.setShape(ShapeNodeShape.ROUND_RECTANGLE);

		graph.getNodeDefaults().setStyle(style);
		graph.getNodeDefaults().setSize(new SizeD(10, 10));
	}

	/**
	 * Gets the style.
	 *
	 * @return the style
	 */
	public ShapeNodeStyle getStyle() {
		return style;
	}

	/* (non-Javadoc)
	 * @see org.rogatio.circlead.view.items.DefaultCell#create()
	 */
	@Override
	public Object create() {
		return graph.createNode();
	}

	/* (non-Javadoc)
	 * @see org.rogatio.circlead.view.items.DefaultCell#getPosition()
	 */
	@Override
	public Point2D getPosition() {
		INode n = (INode) this.getData("node");
		double x = n.getLayout().getCenter().x;
		double y = n.getLayout().getCenter().y;
		Point2D p = new Point2D.Double(x, y);
		return p;
	}

}
