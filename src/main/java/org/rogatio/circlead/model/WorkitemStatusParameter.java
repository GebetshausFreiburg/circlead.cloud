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

// TODO: Auto-generated Javadoc
/**
 * The Enum WorkitemStatusParameter allows different status values which are normalized to a given state and color.
 */
public enum WorkitemStatusParameter {
	/** Kennt das Thema nicht*/
	UNSKILLED("Grey", "Unwissender", "Untrainiert", "Untrainierter", "Unskilled", "0%"),
	/** Kann die Aufgabenstellung nachvollziehen, aber noch nicht in allen Teilen umsetzen*/
	STARTER("Red", "Anfänger", "Starter", "25%"),
	/** Kann die Aufgabe in Teile und mit Hilfe umsetzen*/
	BEGINNER("Yellow", "Beginner", "Beginner", "50%"),
	/** Kann die Aufgabe vollständig und selbständig umsetzen*/
	EXPERT("Green", "Experte", "Expert", "75%"),
	/** Kann Andere anleiten*/
	SKILLED("Blue", "Unterstützer", "Skilled", "100%"),
	
	/** The active. */
	ACTIVE("Green", "Aktiv", "Active"),
	
	EXTERNAL("Green", "Öffentlich", "Extern", "External"),
	INTERNAL("Yellow", "Intern", "Internal"),
	
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
	
	/** The unassigned. */
	UNASSIGNED("Red", "Unbesetzt", "Unassigned"),
	
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
    private WorkitemStatusParameter(String color, String name, String... aliases) {
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
    public static WorkitemStatusParameter get(String statusValue) {
    	WorkitemStatusParameter[] statusParameters = WorkitemStatusParameter.values();
		for (WorkitemStatusParameter statusParameter : statusParameters) {
			if (statusParameter.isEquals(statusValue)) {
				return statusParameter;
			}
		}
		return null;
    }
	
}
