package org.rogatio.circlead.view.items.graph;

import java.awt.Color;

import org.rogatio.circlead.util.ColorUtil;
import org.rogatio.circlead.view.items.CellType;
import org.rogatio.circlead.view.items.DefaultCell;

import com.yworks.yfiles.geometry.PointD;
import com.yworks.yfiles.geometry.SizeD;
import com.yworks.yfiles.graph.INode;
import com.yworks.yfiles.graph.labelmodels.ExteriorLabelModel;
import com.yworks.yfiles.graph.styles.ShapeNodeShape;
import com.yworks.yfiles.graph.styles.ShapeNodeStyle;
import com.yworks.yfiles.view.GraphComponent;

/**
 * The Class RoleCell.
 */
public class RoleCell extends GraphCell {

	/**
	 * Instantiates a new role cell.
	 *
	 * @param canvas the canvas
	 */
	public RoleCell(GraphCanvas canvas) {
		super(canvas);
		style = new ShapeNodeStyle();
		style.setPaint(Color.decode("#0000DD"));
		style.setShape(ShapeNodeShape.ELLIPSE);
	}

	/* (non-Javadoc)
	 * @see org.rogatio.circlead.view.items.DefaultCell#getType()
	 */
	@Override
	public CellType getType() {
		return CellType.ROLE;
	}
	
	/* (non-Javadoc)
	 * @see org.rogatio.circlead.view.items.graph.GraphCell#create()
	 */
	@Override
	public Object create() {
		
		ShapeNodeStyle sns = this.getStyle().clone();
		Color color = ColorUtil.createRandomColor();
		setData("color", color);
		sns.setPaint(color);

		canvas.setNodeSize(10);
		INode n = graph.createNode(new PointD(0, 0), sns);
		setData("node", n);

		graph.addLabel(n, getName(), ExteriorLabelModel.SOUTH);
		
		canvas.setNodeSizeDefault();
		
		return n;
	}
}
