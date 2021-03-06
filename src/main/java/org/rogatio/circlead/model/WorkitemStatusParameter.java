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
 * 
 * @author Matthias Wegner
 */
public enum WorkitemStatusParameter {

	/** Not knows the topic. Kennt das Thema nicht. */
	UNSKILLED("Grey", true, "Unwissender", "Untrainiert", "Untrainierter", "Unskilled", "0%"),

	/** The situational status. Allows a role to be empty and no role-carrier is needed */
	SITUATIONAL("Green", "Situative Besetzung", "Situational", "Situativ", "Situative", "Situative Teilnehmer", "Situativ Besetzt"),
	
	/**
	 * Kann die Aufgabenstellung nachvollziehen, aber noch nicht in allen Teilen
	 * umsetzen.
	 */
	STARTER("Red", true, "Anfänger", "Starter", "25%"),

	/** The substitute. */
	SUBSTITUTE("Grey", "Vertretung", "Substitute", "Vertreter", "Vertreterin"),

	/** Kann die Aufgabe in Teile und mit Hilfe umsetzen. */
	BEGINNER("Yellow", true, "Beginner", "Beginner", "50%"),

	/** Kann die Aufgabe vollständig und selbständig umsetzen. */
	EXPERT("Green", true, "Experte", "Expert", "75%"),

	/** Kann Andere anleiten. */
	SKILLED("Blue", true, "Unterstützer", "Skilled", "100%"),

	/** The active. */
	ACTIVE("Green", "Aktiv", "Active"),

	/** The external. */
	EXTERNAL("Green", "Öffentlich", "Extern", "External"),

	/** The internal. */
	INTERNAL("Yellow", "Intern", "Internal"),

	/** The deprecated. Should not be used or picked any more. */
	DEPRECATED("Red", "Veraltet", "Deprecated"),

	/**
	 * Closed Status. Means this item is in no wy used any more. Could be also
	 * deleted.
	 */
	CLOSED("Grey", "Archiviert", "Closed", "Geschlossen", "Abgeschlossen"),

	/** The inactive. */
	INACTIVE("Grey", "Inaktiv", "Inactive"),

	/** The paused. */
	PAUSED("Blue", "Pausiert", "Paused", "Pause", "Elternzeit", "Auslandsaufenthalt"),

	/** The temporary. */
	TEMPORARY("Yellow", "Temporär", "Temporary"),

	/** The need. */
	NEED("Yellow", "Unterbesetzt", "Need", "Teammitgliedmangel"),

	/** The inprogress. */
	INPROGRESS("Yellow", "InBearbeitung", "InProgress", "InBearbeitung"),

	/** The critical. */
	CRITICAL("Red", "Unklar", "Undefined", "Kritisch", "Critical", "Widersprüchlich"),

	/** The unassigned. */
	UNASSIGNED("Red", "Unbesetzt", "Unassigned"), 
	
	/** The overassigned. */
	OVERASSIGNED("Green", "Mehrfachbesetzung"),
	
	/** The adequate. */
	ADEQUATE("Green", "Ausreichend besetzt"), 
	
	/** The assigned. */
	ASSIGNED("Yellow", "Einfachbesetzung"), 
	
	/** The draft. */
	DRAFT("Blue", "Entwurf", "Draft");

	/** The color. */
	private final String color;

	/** The name. */
	private final String name;

	/** The aliases. */
	private final List<String> aliases = new ArrayList<String>();

	/** The inverted color. */
	private final boolean invertedColor;

	/**
	 * Checks if is inverted color.
	 *
	 * @return true, if is inverted color
	 */
	public boolean isInvertedColor() {
		return invertedColor;
	}

	/**
	 * Instantiates a new workitem status parameter.
	 *
	 * @param color the color
	 * @param invertedColor the inverted color
	 * @param name the name
	 * @param aliases the aliases
	 */
	private WorkitemStatusParameter(String color, boolean invertedColor, String name, String... aliases) {
		this.color = color;
		this.name = name;
		this.invertedColor = invertedColor;
		this.aliases.add(name.toUpperCase());
		List<String> aliasesList = Arrays.asList(aliases);
		for (String string : aliasesList) {
			this.aliases.add(string.toUpperCase());
		}
	}

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
		invertedColor = false;
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
