package org.rogatio.circlead.view.items;

// TODO: Auto-generated Javadoc
/**
 * The Interface ILink.
 */
public interface ILink {

	/**
	 * Sets the source.
	 *
	 * @param source the new source
	 */
	public void setSource(ICell source);

	/**
	 * Sets the target.
	 *
	 * @param target the new target
	 */
	public void setTarget(ICell target);

	/**
	 * Gets the source.
	 *
	 * @return the source
	 */
	public ICell getSource();

	/**
	 * Gets the target.
	 *
	 * @return the target
	 */
	public ICell getTarget();

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
	 * Creates the.
	 *
	 * @return the object
	 */
	public Object create();
	
}
