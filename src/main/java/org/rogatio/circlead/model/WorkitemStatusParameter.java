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
 * The Enum WorkitemStatusParameter allows different status values which are
 * normalized to a given state and color.
 */
public enum WorkitemStatusParameter {

	/** Kennt das Thema nicht. */
	UNSKILLED("Grey", "Unwissender", "Untrainiert", "Untrainierter", "Unskilled", "0%"),

	/**
	 * Kann die Aufgabenstellung nachvollziehen, aber noch nicht in allen Teilen
	 * umsetzen.
	 */
	STARTER("Red", "Anfänger", "Starter", "25%"),
	
	SUBSTITUTE("Grey", "Vertretung", "Substitute", "Vertreter", "Vertreterin"),

	/** Kann die Aufgabe in Teile und mit Hilfe umsetzen. */
	BEGINNER("Yellow", "Beginner", "Beginner", "50%"),

	/** Kann die Aufgabe vollständig und selbständig umsetzen. */
	EXPERT("Green", "Experte", "Expert", "75%"),

	/** Kann Andere anleiten. */
	SKILLED("Blue", "Unterstützer", "Skilled", "100%"),

	/** The active. */
	ACTIVE("Green", "Aktiv", "Active"),

	/** The external. */
	EXTERNAL("Green", "Öffentlich", "Extern", "External"),

	/** The internal. */
	INTERNAL("Yellow", "Intern", "Internal"),

	/** The deprecated. Should not be used or picked any more. */
	DEPRECATED("Red", "Veraltet", "Deprecated"),
	
	/** Closed Status. Means this item is in no wy used any more. Could be also deleted.*/
	CLOSED("Grey", "Archiviert", "Closed", "Geschlossen", "Abgeschlossen"),

	/** The inactive. */
	INACTIVE("Grey", "Inaktiv", "Inactive"),

	PAUSED("Blue", "Pausiert", "Paused", "Pause", "Elternzeit", "Auslandsaufenthalt"),
	
	/** The temporary. */
	TEMPORARY("Yellow", "Temporär", "Temporary"),

	/** The inprogress. */
	INPROGRESS("Yellow", "InBearbeitung", "InProgress", "InBearbeitung"),

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
	 * @param color   the color
	 * @param name    the name
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
