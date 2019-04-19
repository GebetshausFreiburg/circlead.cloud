package org.rogatio.circlead.view.items;

import java.awt.geom.Point2D;

/**
 * The Interface ICell represents a cell/node
 */
public interface ICell {

	/**
	 * Sets the name of the cell
	 *
	 * @param name the new name
	 */
	public void setName(String name);
	
	/**
	 * Sets the data.
	 *
	 * @param key the key
	 * @param data the data
	 */
	public void setData(String key, Object data);
	
	/**
	 * Gets the data.
	 *
	 * @param key the key
	 * @return the data
	 */
	public Object getData(String key);
	
	/**
	 * Gets the name of the cell
	 *
	 * @return the name
	 */
	public String getName();
	
	/**
	 * Gets the type of a cell
	 *
	 * @return the type
	 */
	public CellType getType();
	
	/**
	 * Creates the visible representation of the cell/node
	 *
	 * @return the object
	 */
	public Object create();
	
	/**
	 * Gets the position of the cell
	 *
	 * @return the position
	 */
	public Point2D getPosition();
	
}
