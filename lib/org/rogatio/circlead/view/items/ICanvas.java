package org.rogatio.circlead.view.items;

import java.awt.geom.Rectangle2D;
import java.util.List;

/**
 * The Interface ICanvas.
 */
public interface ICanvas {

	/**
	 * Creates the cell.
	 *
	 * @param type the type
	 * @return the i cell
	 */
	public ICell createCell(CellType type);

	/**
	 * Adds the cell.
	 *
	 * @param cell the cell
	 */
	public void addCell(ICell cell);

	/**
	 * Adds the link.
	 *
	 * @param source the source
	 * @param target the target
	 * @return the i link
	 */
	public ILink addLink(ICell source, ICell target);

	/**
	 * Adds the link.
	 *
	 * @param link the link
	 */
	public void addLink(ILink link);

	/**
	 * Sets the cells.
	 *
	 * @param cells the new cells
	 */
	public void setCells(List<ICell> cells);
	
	/**
	 * Gets the bounds.
	 *
	 * @return the bounds
	 */
	public Rectangle2D getBounds();
	
	/**
	 * Sets the bounds.
	 *
	 * @param bounds the new bounds
	 */
	public void setBounds(Rectangle2D bounds);
	
	/**
	 * Layouts the canvas for all cells and links
	 */
	public void layout();
}
