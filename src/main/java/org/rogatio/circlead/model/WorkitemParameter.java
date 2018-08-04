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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.management.relation.Role;

// TODO: Auto-generated Javadoc
/**
 * The Enum WorkitemParameter.
 */
public enum WorkitemParameter {
	
	/** The activity. */
	ACTIVITY("Aktivität", "Aktivitäten", "Activity", "Activities", "Aufgaben"), 
	
	/** The howtos. */
	HOWTOS("HowTos", "Arbeitsanweisungen", "Anleitungen", "Erläuterungen", "Knowledgebase"), 
	
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
	
	/** The contacts. */
	CONTACTS("Kontakte", "Contacts"),  
	
	/** The responsible. */
	RESPONSIBLE("Verantwortlicher", "Responsible"),  
	
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
	 * @param value the value
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
	 * @param aliases the aliases
	 */
	private WorkitemParameter(String... aliases) {
		List<String> aliasesList = Arrays.asList(aliases);
		for (String string : aliasesList) {
			this.aliases.add(string.toUpperCase());
		}
	}
}
