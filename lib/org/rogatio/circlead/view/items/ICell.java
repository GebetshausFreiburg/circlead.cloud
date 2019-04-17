package org.rogatio.circlead.view.items;

import java.awt.geom.Point2D;

// TODO: Auto-generated Javadoc
/**
 * The Interface ICell.
 */
public interface ICell {

	/**
	 * Sets the name.
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
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName();
	
	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public CellType getType();
	
	/**
	 * Creates the.
	 *
	 * @return the object
	 */
	public Object create();
	
	/**
	 * Gets the position.
	 *
	 * @return the position
	 */
	public Point2D getPosition();
	
}
