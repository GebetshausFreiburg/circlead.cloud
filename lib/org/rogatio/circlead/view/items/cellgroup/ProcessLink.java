package org.rogatio.circlead.view.items.cellgroup;

import org.rogatio.circlead.view.items.DefaultLink;

import com.yworks.yfiles.graph.IEdge;
import com.yworks.yfiles.graph.IGraph;
import com.yworks.yfiles.graph.INode;
import com.yworks.yfiles.graph.styles.Arrow;
import com.yworks.yfiles.graph.styles.PolylineEdgeStyle;
import com.yworks.yfiles.view.GraphComponent;
import com.yworks.yfiles.view.Pen;

/**
 * The Class ProcessLink.
 */
public class ProcessLink extends DefaultLink {

	/** The style. */
	protected PolylineEdgeStyle style;

	/** The graph component. */
	protected GraphComponent graphComponent;

	/** The graph. */
	protected IGraph graph;

	/** The canvas. */
	protected CellgroupCanvas canvas;

	/**
	 * Instantiates a new process link.
	 *
	 * @param canvas the canvas
	 */
	public ProcessLink(CellgroupCanvas canvas) {
		this.graphComponent = canvas.getGraphComponent();
		this.graph = graphComponent.getGraph();
		this.canvas = canvas;

		style = new PolylineEdgeStyle();
		style.setPen(Pen.getBlack());
		style.setTargetArrow(Arrow.DEFAULT);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.rogatio.circlead.view.items.DefaultLink#create()
	 */
	@Override
	public Object create() {
		if (this.source != null && this.target != null) {
			INode n1 = (INode) this.source.getData("node");
			INode n2 = (INode) this.target.getData("node");
			if (n1 != null && n2 != null) {
				IEdge e = graph.createEdge(n1, n2, style);
				this.setData("edge", e);
				return e;
			}
		}
		return null;
	}

}
