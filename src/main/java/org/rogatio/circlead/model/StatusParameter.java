/*
 * Circlead - Develop and structure evolutionary Organisations
 * 
 * @author Matthias Wegner
 * @version 0.1
 * @since 01.07.2018
 * 
 */
package org.rogatio.circlead.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The Enum StatusParameter allows different status values which are normalized to a given state and color.
 */
public enum StatusParameter {

	/** The active. */
	ACTIVE("Green", "Aktiv", "Active"),
	
	/** The deprecated. */
	DEPRECATED("Grey", "Veraltet", "Deprecated"),
	
	/** The inactive. */
	INACTIVE("Grey", "Inaktiv", "Inactive"),
	
	/** The temporary. */
	TEMPORARY("Yellow", "Temporär", "Temporary"),
	
	/** The inprogress. */
	INPROGRESS("Yellow", "In Bearbeitung", "In Progress"),
	
	/** The critical. */
	CRITICAL("Red", "Unklar", "Undefined", "Kritisch", "Critical", "Widersprüchlich"),
	
	/** The draft. */
	DRAFT("Blue", "Entwurf", "Draft");
	
	/** The color. */
	private final String color;
    
    /** The name. */
    private final String name;
    
    /** The aliases. */
    private final List<String> aliases = new ArrayList<String>();
  
    /**
     * Instantiates a new status parameter.
     *
     * @param color the color
     * @param name the name
     * @param aliases the aliases
     */
    private StatusParameter(String color, String name, String... aliases) {
        this.color = color;
        this.name = name;
        this.aliases.add(name.toUpperCase());
        List<String> aliasesList = Arrays.asList(aliases);
		for (String string : aliasesList) {
			this.aliases.add(string.toUpperCase());
		}
    }
    
    /**
     * Checks if is equals.
     *
     * @param value the value
     * @return true, if is equals
     */
    public boolean isEquals(String value) {
    	return aliases.contains(value.toUpperCase());
	}
    
    /**
     * Gets the color.
     *
     * @return the color
     */
    public String getColor() {
    	return this.color;
    }
    
    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
    	return this.name;
    }
    
    /**
     * Gets the.
     *
     * @param statusValue the status value
     * @return the status parameter
     */
    public static StatusParameter get(String statusValue) {
    	StatusParameter[] statusParameters = StatusParameter.values();
		for (StatusParameter statusParameter : statusParameters) {
			if (statusParameter.isEquals(statusValue)) {
				return statusParameter;
			}
		}
		return null;
    }
	
}
