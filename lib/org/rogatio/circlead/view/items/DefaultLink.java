package org.rogatio.circlead.view.items;

import java.util.HashMap;
import java.util.Map;

// TODO: Auto-generated Javadoc
/**
 * The Class DefaultLink.
 */
public class DefaultLink implements ILink {

	/** The source. */
	protected ICell source;
	
	/** The target. */
	protected ICell target;

	/** The data map. */
	protected Map<String, Object> dataMap = new HashMap<String, Object>();

	/* (non-Javadoc)
	 * @see org.rogatio.circlead.view.items.ILink#setData(java.lang.String, java.lang.Object)
	 */
	@Override
	public void setData(String key, Object data) {
		this.dataMap.put(key.toLowerCase(), data);
	}

	/* (non-Javadoc)
	 * @see org.rogatio.circlead.view.items.ILink#getData(java.lang.String)
	 */
	@Override
	public Object getData(String key) {
		return dataMap.get(key.toLowerCase());
	}

	/**
	 * Instantiates a new default link.
	 */
	public DefaultLink() {

	}

	/* (non-Javadoc)
	 * @see org.rogatio.circlead.view.items.ILink#setSource(org.rogatio.circlead.view.items.ICell)
	 */
	@Override
	public void setSource(ICell source) {
		this.source = source;
	}

	/* (non-Javadoc)
	 * @see org.rogatio.circlead.view.items.ILink#setTarget(org.rogatio.circlead.view.items.ICell)
	 */
	@Override
	public void setTarget(ICell target) {
		this.target = target;
	}

	/* (non-Javadoc)
	 * @see org.rogatio.circlead.view.items.ILink#getSource()
	 */
	@Override
	public ICell getSource() {
		return source;
	}

	/* (non-Javadoc)
	 * @see org.rogatio.circlead.view.items.ILink#getTarget()
	 */
	@Override
	public ICell getTarget() {
		return target;
	}

	/* (non-Javadoc)
	 * @see org.rogatio.circlead.view.items.ILink#create()
	 */
	@Override
	public Object create() {
		return null;
	}

}
