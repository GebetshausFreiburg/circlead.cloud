package org.rogatio.circlead.view.items.cellgroup;

import java.awt.Color;

import org.rogatio.circlead.model.data.ActivityDataitem;
import org.rogatio.circlead.view.items.CellType;
import org.rogatio.circlead.view.items.DefaultCell;
import org.rogatio.circlead.view.items.ICell;
import org.rogatio.circlead.view.items.ILink;
import org.rogatio.circlead.view.items.process.bpmn.SmallLabelStyle;

import com.yworks.yfiles.geometry.PointD;
import com.yworks.yfiles.geometry.SizeD;
import com.yworks.yfiles.graph.IEdge;
import com.yworks.yfiles.graph.INode;
import com.yworks.yfiles.graph.labelmodels.ExteriorLabelModel;
import com.yworks.yfiles.graph.styles.Arrow;
import com.yworks.yfiles.graph.styles.PolylineEdgeStyle;
import com.yworks.yfiles.graph.styles.ShapeNodeShape;
import com.yworks.yfiles.graph.styles.ShapeNodeStyle;
import com.yworks.yfiles.view.GraphComponent;
import com.yworks.yfiles.view.Pen;

/**
 * The Class EventStartCell.
 */
public class EventStartCell extends CellgroupCell {

	/**
	 * Instantiates a new event start cell.
	 *
	 * @param canvas the canvas
	 */
	public EventStartCell(CellgroupCanvas canvas) {
		super(canvas);
		style = new ShapeNodeStyle();
		style.setPaint(Color.GREEN);
		style.setPen(Pen.getBlack());
		style.setShape(ShapeNodeShape.ELLIPSE);
	}

	/* (non-Javadoc)
	 * @see org.rogatio.circlead.view.items.DefaultCell#getType()
	 */
	@Override
	public CellType getType() {
		return CellType.EVENT_START;
	}
	
	/* (non-Javadoc)
	 * @see org.rogatio.circlead.view.items.graph.GraphCell#create()
	 */
	@Override
	public Object create() {
	//	ActivityDataitem activityDataitem = (ActivityDataitem) getData("activity");
		
		canvas.setNodeSize(5);

		Color color = canvas.getColorOfRole(this.getData("roletitle").toString());
		setData("color", color);	
		
		INode n = graph.createNode(new PointD(0, 0), style);

		if (getName() != null) {
			graph.addLabel(n, getName(), ExteriorLabelModel.SOUTH, new SmallLabelStyle());
		}

		canvas.setNodeSizeDefault();
		
		setData("node", n);

		ICell rc = canvas.getCellOfRole((String) this.getData("roletitle"));

		CellgroupLink gl = new CellgroupLink(canvas);
		gl.setSource(rc);
		gl.setTarget(this);
		gl.create();
		canvas.addLink(gl);
		
		return n;
	}
}
