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
 * The Class GraphLink.
 */
public class CellgroupLink extends DefaultLink {

	/** The style. */
	protected PolylineEdgeStyle style;
	
	/** The graph component. */
	protected GraphComponent graphComponent;
	
	/** The graph. */
	protected IGraph graph;
	
	/** The canvas. */
	protected CellgroupCanvas canvas;

	/**
	 * Instantiates a new graph link.
	 *
	 * @param canvas the canvas
	 */
	public CellgroupLink(CellgroupCanvas canvas) {
		this.graphComponent = canvas.getGraphComponent();
		this.graph = graphComponent.getGraph();
		this.canvas = canvas;

		style = new PolylineEdgeStyle();
		style.setPen(Pen.getSilver());
		style.setTargetArrow(Arrow.NONE);

		graph.getEdgeDefaults().setStyle(style);
	}

	/* (non-Javadoc)
	 * @see org.rogatio.circlead.view.items.DefaultLink#create()
	 */
	@Override
	public Object create() {
		if (this.source != null && this.target != null) {
			IEdge e = graph.createEdge((INode) this.source.getData("node"), (INode) this.target.getData("node"), style);
			this.setData("edge", e);
			return e;
		}
		return null;
	}

}
