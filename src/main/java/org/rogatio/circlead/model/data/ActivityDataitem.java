/*
 * Circlead - Develop and structure evolutionary Organisations
 * 
 * @author Matthias Wegner
 * @version 0.1
 * @since 01.07.2018
 * 
 */
package org.rogatio.circlead.model.data;

import java.util.List;

import org.rogatio.circlead.util.StringUtil;

/**
 * The Class ActivityDataitem.
 */
public class ActivityDataitem extends DefaultDataitem {

	/** The role. */
	private String role;
	
	/** The howtos. */
	private List<String> howtos;

	/**
	 * Gets the role.
	 *
	 * @return the role
	 */
	public String getRole() {
		return role;
	}

	/**
	 * Sets the role.
	 *
	 * @param role the new role
	 */
	public void setRole(String role) {
		this.role = role;
	}

	/**
	 * Gets the howtos.
	 *
	 * @return the howtos
	 */
	public List<String> getHowtos() {
		return howtos;
	}

	/**
	 * Sets the howtos.
	 *
	 * @param howtos the new howtos
	 */
	public void setHowtos(List<String> howtos) {
		this.howtos = StringUtil.clean(howtos);
	}

}
