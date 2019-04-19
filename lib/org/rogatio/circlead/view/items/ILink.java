package org.rogatio.circlead.view.items;

/**
 * The Interface ILink. Represents a link/edge between two cells/nodes
 */
public interface ILink {

	/**
	 * Sets the source cell/node
	 *
	 * @param source the new source cell
	 */
	public void setSource(ICell source);

	/**
	 * Sets the target cell/node
	 *
	 * @param target the new target cell
	 */
	public void setTarget(ICell target);

	/**
	 * Gets the source cell
	 *
	 * @return the source
	 */
	public ICell getSource();

	/**
	 * Gets the target cell
	 *
	 * @return the target
	 */
	public ICell getTarget();

	/**
	 * Sets data with a key to the link
	 *
	 * @param key  the key
	 * @param data the data
	 */
	public void setData(String key, Object data);

	/**
	 * Gets the data stored with key
	 *
	 * @param key the key
	 * @return the data
	 */
	public Object getData(String key);

	/**
	 * Creates the visible representation of the object
	 *
	 * @return the object
	 */
	public Object create();

}
