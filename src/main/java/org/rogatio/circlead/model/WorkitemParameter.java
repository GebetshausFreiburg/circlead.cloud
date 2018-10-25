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
 * The Enum WorkitemParameter allows rendering of different workitem-fields
 * which map al values to a defined set of parameters.
 */
public enum WorkitemParameter {

	/** The activity. */
	ACTIVITY(Parameter.ACTIVITY, "Aktivitäten", "Activity", "Activities", "Aufgaben"),

	/** The howtos. */
	HOWTOS(Parameter.HOWTOS, "Arbeitsanweisungen", "Anleitungen", "Erläuterungen", "Knowledgebase"),

	TEAMROLES(Parameter.TEAMROLES, "Teamroles", "Team"),
	
	/** The subactivity. */
	SUBACTIVITY(Parameter.SUBACTIVITIES, "Subactivities"),

	/** The activityid. */
	ACTIVITYID(Parameter.ACTIVITYID, "Activity-ID", "Aid", "Alternative Id"),

	/** The description. */
	DESCRIPTION(Parameter.DESCRIPTION, "Description", "Beschreibung"),
	
	RECURRENCERULE(Parameter.RECURRENCERULE, "RecurrenceRule", "RecurrencePattern"),
	
	CATEGORY(Parameter.CATEGORY, "Category"),

	/** The created. */
	CREATED(Parameter.CREATED, "Created"),

	/** The result. */
	RESULT(Parameter.RESULT, "Ergebnis", "Result"),

	/** The modified. */
	MODIFIED(Parameter.MODIFIED, "Modified"),

	/** The version. */
	VERSION(Parameter.VERSION),

	/** The name. */
	NAME(Parameter.NAME),

	/** The id. */
	ID(Parameter.ID),

	/** The synonym. */
	SYNONYM(Parameter.SYNONYMS, "Synonym"),
	
	TYPE(Parameter.TYPE, "Type"),
	SUBTYPE(Parameter.SUBTYPE, "Subtype"),

	/** The status. */
	STATUS(Parameter.STATUS, "Status"),

	/** The firstname. */
	FIRSTNAME(Parameter.FIRSTNAME, "Vorname", "First name", "firstname"),

	/** The familyname. */
	FAMILYNAME(Parameter.FAMILYNAME, "Nachname", "Family name", "familyname", "Familienname", "Family Name"),

	/** The summary. */
	SUMMARY(Parameter.SUMMARY, "Summary", "summary", "Zusammenfassung", "Kurzfassung"),

	/** The fullname. */
	FULLNAME(Parameter.NAME, "name"),

	/** The lead. */
	LEAD(Parameter.CONTACTPERSON, "Lead", "lead", "Ansprechpartner"),

	/** The organisation. */
	ORGANISATION(Parameter.ORGANISATION),

	/** The data. */
	DATA(Parameter.DATA, "Data"),

	/** The parent. */
	PARENT(Parameter.PREDECESSOR, "Parent"),

	/** The purpose. */
	PURPOSE(Parameter.PURPOSESHORT, "Kurzbeschreibung", "Purpose", "Bedeutung"),

	/** The contacts. */
	CONTACTS(Parameter.CONTACTS2, "Contacts"),

	/** The responsible. */
	RESPONSIBLE(Parameter.RESPONSIBLE2, "Verantwortlicher", "Rolle", "Durchführender", "Responsible"),

	/** The accountable. */
	ACCOUNTABLE(Parameter.ACCOUNTABLE, "Rechenschaftsverantwortlicher", "Accountable"),
	/** The supporter. */
	SUPPORTER(Parameter.SUPPORTER, "Supporter"),
	/** The consultant. */
	CONSULTANT(Parameter.CONSULTANT, "Consultant"),
	/** The informed. */
	INFORMED(Parameter.INFORMED, "Informierter", "Informed", "Informierte"),

	TEAMFRACTION(Parameter.TEAMFRACTION, "Projektanteil", "Teamfraction", "Projectfraction"),
	
	FTE(Parameter.FTE, "Vollzeitäquivalent", "FTE", "FullTimeEquivalent"),
	
	/** The image. */
	IMAGE(Parameter.IMAGE, "Image", "User", "Avatar"),
	
	/** The ignore. */
	IGNORE(Parameter.ADRESS, "Typ", "Subtype", "Mobil", "Mail", "Festnetz"),

	/** The roles. */
	ROLES(Parameter.ROLES, "Roles", "roles"),

	/** The abbreviation. */
	ABBREVIATION(Parameter.ABBREVIATION, "Kürzel", "Abkürzung", "Kurzzeichen"),

	/** The rolegroup. */
	ROLEGROUP(Parameter.ROLEGROUP, "Rollengruppe", "Funktionsbereich", "Rolegroup"),

	/** The competences. */
	COMPETENCES(Parameter.COMPETENCIES, "Kompetenzen", "Fähigkeiten", "Kompetenz"),

	/** The persons. */
	PERSONS(Parameter.PERSONS, "Personen", "Rollenträger", "Person"),

	/** The guidelines. */
	GUIDELINES(Parameter.RULES, "Regeln", "Regel", "Richtlinien", "Spielregel"),

	/** The opportunities. */
	OPPORTUNITIES(Parameter.OPPORTUNITIES, "Befugnis", "Befugnisse", "Opportunity", "Opportunities"),

	/** The responsibility. */
	RESPONSIBILITY(Parameter.RESPONSIBILITY, "Verantwortung", "Responsibility", "Pflicht", "Responsibilities");
 
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
	 * @param p the p
	 * @param aliases the aliases
	 */
	private WorkitemParameter(Parameter p, String... aliases) {

		if (aliases.length > 0) {
			List<String> aliasesList = Arrays.asList(aliases);
			for (String string : aliasesList) {
				this.aliases.add(string.toUpperCase());
			}
		}

		if (!this.aliases.contains(p.toString())) {
			this.aliases.add(p.toString().toUpperCase());
		}
		
	}

	/*
	 * private WorkitemParameter(String... aliases) { List<String> aliasesList =
	 * Arrays.asList(aliases); for (String string : aliasesList) {
	 * this.aliases.add(string.toUpperCase()); } }
	 */
}
