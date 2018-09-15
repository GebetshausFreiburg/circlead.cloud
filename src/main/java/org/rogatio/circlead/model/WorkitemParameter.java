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
 * The Enum WorkitemParameter allows rendering of different workitem-fields which map al values to a defined set of parameters.
 */
public enum WorkitemParameter {

	/** The activity. */
	ACTIVITY("Aktivität", "Aktivitäten", "Activity", "Activities", "Aufgaben"),

	/** The howtos. */
	HOWTOS("HowTos", "Arbeitsanweisungen", "Anleitungen", "Erläuterungen", "Knowledgebase"),

	/** The subactivity. */
	SUBACTIVITY("Teilaktivitäten", "Subactivities"),

	/** The activityid. */
	ACTIVITYID("AID"),

	/** The description. */
	DESCRIPTION("Beschreibung", "Description"),

	/** The created. */
	CREATED("Created"),

	/** The result. */
	RESULT("Ergebnis", "Result"),

	/** The modified. */
	MODIFIED("Modified"),

	/** The version. */
	VERSION("Version"),
	
	NAME("Name"),
	
	/** The id. */
	ID("Id"),

	/** The synonym. */
	SYNONYM("Synonyme", "Synonym"),

	/** The status. */
	STATUS("status", "Status"),

	/** The firstname. */
	FIRSTNAME("Vorname", "First name", "firstname"),

	/** The familyname. */
	FAMILYNAME("Nachname", "Family name", "familyname", "Familienname", "Family Name"),

	/** The summary. */
	SUMMARY("Summary", "summary", "Zusammenfassung", "Kurzfassung"),

	/** The fullname. */
	FULLNAME("Name", "name"),

	/** The lead. */
	LEAD("Lead", "lead", "Ansprechpartner"),

	/** The organisation. */
	ORGANISATION("Organisation"),

	/** The data. */
	DATA("Daten", "Data"),

	/** The parent. */
	PARENT("Parent", "Vorgänger"),

	/** The purpose. */
	PURPOSE("Zweck", "Kurzbeschreibung", "Purpose", "Bedeutung"),

	/** The contacts. */
	CONTACTS("Kontakte", "Contacts"),

	/** The responsible. */
	RESPONSIBLE("Verantwortlicher", "Rolle", "Durchführender", "Responsible"),

	/** The accountable. */
	ACCOUNTABLE("Rechenschaftsverantwortlicher", "Accountable"),
	/** The supporter. */
	SUPPORTER("Unterstützer", "Supporter"),
	/** The consultant. */
	CONSULTANT("Berater", "Consultant"),
	/** The informed. */
	INFORMED("Informierte", "Informed", "Informierte"),

	IGNORE("Adresse", "Typ", "Subtype", "Mobil", "Mail", "Festnetz"),
	
	/** The roles. */
	ROLES("Rollen", "Roles", "roles"),

	/** The abbreviation. */
	ABBREVIATION("Abbreviation", "Kürzel", "Abkürzung", "Kurzzeichen"),

	/** The rolegroup. */
	ROLEGROUP("Rollengruppe", "Funktionsbereich", "Rolegroup"),

	/** The competences. */
	COMPETENCES("Kompetenzen", "Fähigkeiten", "Kompetenz"),

	/** The persons. */
	PERSONS("Personen", "Rollenträger", "Person"),

	/** The guidelines. */
	GUIDELINES("Regeln", "Regel", "Richtlinien", "Spielregel"),

	/** The opportunities. */
	OPPORTUNITIES("Befugnis", "Befugnisse", "Opportunity", "Opportunities"),

	/** The responsibility. */
	RESPONSIBILITY("Verantwortung", "Responsibility", "Pflicht", "Responsibilities");

	/**
	 * Checks for.
	 *
	 * @param value
	 *            the value
	 * @return true, if successful
	 */
	public boolean has(String value) {
		return aliases.contains(value.toUpperCase());
	}

	/** The aliases. */
	private List<String> aliases = new ArrayList<String>();

	/**
	 * Instantiates a new workitem parameter.
	 *
	 * @param aliases
	 *            the aliases
	 */
	private WorkitemParameter(String... aliases) {
		List<String> aliasesList = Arrays.asList(aliases);
		for (String string : aliasesList) {
			this.aliases.add(string.toUpperCase());
		}
	}
}
