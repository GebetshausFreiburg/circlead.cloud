package org.rogatio.circlead.model.data;

import java.util.Map;

import org.rogatio.circlead.model.Parameter;

/**
 * The Interface IDataRow.
 * 
 * @author Matthias Wegner
 */
public interface IDataRow {

	/**
	 * Gets the data row.
	 *
	 * @return the data row
	 */
	public Map<Parameter, Object> getDataRow();
	
}
