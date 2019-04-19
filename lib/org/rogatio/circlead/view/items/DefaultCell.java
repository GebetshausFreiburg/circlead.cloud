package org.rogatio.circlead.view.items;

import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.Map;

/**
 * The Class DefaultCell.
 */
public class DefaultCell implements ICell {

	/** The name. */
	protected String name;
	
	/** The data map. */
	protected Map<String, Object> dataMap = new HashMap<String, Object>();
	
	/* (non-Javadoc)
	 * @see org.rogatio.circlead.view.items.ICell#setData(java.lang.String, java.lang.Object)
	 */
	@Override
	public void setData(String key, Object data) {
		this.dataMap.put(key.toLowerCase(), data);
	}
	
	/* (non-Javadoc)
	 * @see org.rogatio.circlead.view.items.ICell#getData(java.lang.String)
	 */
	@Override
	public Object getData(String key) {
		return dataMap.get(key.toLowerCase());
	}
	
	/* (non-Javadoc)
	 * @see org.rogatio.circlead.view.items.ICell#setName(java.lang.String)
	 */
	@Override
	public void setName(String name) {
		this.name = name;
	}

	/* (non-Javadoc)
	 * @see org.rogatio.circlead.view.items.ICell#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

	/* (non-Javadoc)
	 * @see org.rogatio.circlead.view.items.ICell#getType()
	 */
	@Override
	public CellType getType() {
		return CellType.DEFAULT;
	}

	/* (non-Javadoc)
	 * @see org.rogatio.circlead.view.items.ICell#create()
	 */
	@Override
	public Object create() {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.rogatio.circlead.view.items.ICell#getPosition()
	 */
	@Override
	public Point2D getPosition() {
		return null;
	}

}
