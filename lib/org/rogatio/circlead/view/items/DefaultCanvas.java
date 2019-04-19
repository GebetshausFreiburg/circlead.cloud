package org.rogatio.circlead.view.items;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

/**
 * The Class DefaultCanvas.
 */
public class DefaultCanvas implements ICanvas {

	/** The cells. */
	protected List<ICell> cells = new ArrayList<ICell>();
	
	/** The links. */
	protected List<ILink> links = new ArrayList<ILink>();
	
	/** The bounds. */
	protected Rectangle2D bounds;
	
	/* (non-Javadoc)
	 * @see org.rogatio.circlead.view.items.ICanvas#addCell(org.rogatio.circlead.view.items.ICell)
	 */
	@Override
	public void addCell(ICell cell) {
		if (cell != null) {
			cells.add(cell);
		}
	}

	/* (non-Javadoc)
	 * @see org.rogatio.circlead.view.items.ICanvas#addLink(org.rogatio.circlead.view.items.ICell, org.rogatio.circlead.view.items.ICell)
	 */
	@Override
	public ILink addLink(ICell source, ICell target) {
		ILink link = new DefaultLink();
		link.setSource(source);
		link.setTarget(target);
		links.add(link);
		return link;
	}

	/* (non-Javadoc)
	 * @see org.rogatio.circlead.view.items.ICanvas#createCell(org.rogatio.circlead.view.items.CellType)
	 */
	@Override
	public ICell createCell(CellType type) {
		return new DefaultCell();
	}

	/* (non-Javadoc)
	 * @see org.rogatio.circlead.view.items.ICanvas#addLink(org.rogatio.circlead.view.items.ILink)
	 */
	@Override
	public void addLink(ILink link) {
		links.add(link);
	}

	/* (non-Javadoc)
	 * @see org.rogatio.circlead.view.items.ICanvas#setCells(java.util.List)
	 */
	@Override
	public void setCells(List<ICell> cells) {
		this.cells = cells;
	}

	/* (non-Javadoc)
	 * @see org.rogatio.circlead.view.items.ICanvas#getBounds()
	 */
	@Override
	public Rectangle2D getBounds() {
		return bounds;
	}

	/* (non-Javadoc)
	 * @see org.rogatio.circlead.view.items.ICanvas#setBounds(java.awt.geom.Rectangle2D)
	 */
	@Override
	public void setBounds(Rectangle2D bounds) {
		this.bounds = bounds;
	}

	/* (non-Javadoc)
	 * @see org.rogatio.circlead.view.items.ICanvas#layout()
	 */
	@Override
	public void layout() {
	}

}
