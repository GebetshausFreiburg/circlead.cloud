package org.rogatio.circlead.model;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The Enum Parameter. Holds german names in enum to avoid spelling error.
 * Multilinguality not implemented.
 * 
 * @author Matthias Wegner
 */
public enum Parameter {

	/** The abbreviation of a workitem role and rolegroup. */
	ABBREVIATION("Abkürzung"),

	/** The timeslice 01. */
	TIMESLICE_01("M01"),
	/** The timeslice 02. */
	TIMESLICE_02("M02"),
	/** The timeslice 03. */
	TIMESLICE_03("M03"),
	/** The timeslice 04. */
	TIMESLICE_04("M04"),
	/** The timeslice 05. */
	TIMESLICE_05("M05"),

	/** The timeslice 06. */
	TIMESLICE_06("M06"),
	/** The timeslice 07. */
	TIMESLICE_07("M07"),
	/** The timeslice 08. */
	TIMESLICE_08("M08"),
	/** The timeslice 09. */
	TIMESLICE_09("M09"),
	/** The timeslice 10. */
	TIMESLICE_10("M10"),

	/** The timeslice 11. */
	TIMESLICE_11("M11"),
	/** The timeslice 12. */
	TIMESLICE_12("M12"),

	/** The title of a workitem. */
	TITLE("Titel"),

	/** The root. */
	ROOT("Wurzel"),

	/** The name of a bussiness contact. */
	BUSSINESS("Geschäftlich"),

	/** The name of a private contact. */
	PRIVATE("Privat"),

	/** The second name of a person. */
	SECONDNAME("Zweitname"),

	/** The image of a person. */
	IMAGE("Bild"),

	/** The short term of workitem. */
	ABBREVIATION2("Kürzel"),

	/** The persons which takes a role. */
	ROLEPERSONS("Rollenträger"),

	/** The persons which take roles in organisation. */
	ROLEPERSONSINORGANISATION("Rollenträger in Organisation"),

	/** The persons which take roles in team. */
	ROLEPERSONSINTEAM("Rollenträger im Team"),

	/** The tasks. */
	TASKS("Aufgaben"),

	/** The id. */
	ID("Id"),

	/** The status of unrelated role. */
	UNRELATED("Unbesetzt"),

	/** The situational. */
	SITUATIONAL("Situativ"),

	/** The purpose of a role. */
	PURPOSE("Zweck / Kurzbeschreibung"),

	/** The purpoe of a role (short). */
	PURPOSESHORT("Zweck"),

	/** The childs of role and rolegroup. */
	CHILDS("Erben"),

	/** The version of a workitem. */
	VERSION("Version"),

	/** The modified date of a workitem. */
	MODIFIED("Geändert"),

	/** The created date of a workitem. */
	CREATED("Erstellt"),

	/** The contactperson of a rolegroup. */
	CONTACTPERSON("Ansprechpartner"),

	/** The id of an activity. */
	ACTIVITYID("AID"),

	/** The type. Is used in contact-dataitem and team */
	TYPE("Typ"),

	/** The subtype. Is used in contact-dataitem and team */
	SUBTYPE("Subtyp"),

	/** The activity name. */
	ACTIVITY("Aktivität"),

	/** The name of activities. */
	ACTIVITIES("Aktivitäten"),

	/** The name of roles. */
	ROLES("Rollen"),

	/** The name of roles in team. */
	ROLESINTEAM("Rollen im Team"),

	/** The name of roles in organisation. */
	ROLESINORGANISATION("Rollen in der Organisation"),

	/** The responsible. */
	RESPONSIBLE("Durchführender"),

	/** The responsible2. */
	RESPONSIBLE2("Verantwortlicher"),

	/** The parent of an activity. */
	PARENTACTIVITY("Übergeordnete Aktivität"),

	/** The result. */
	RESULT("Erwartetes Ergebnis"),

	/** The description. */
	DESCRIPTION("Beschreibung"),

	/** The recurrencerule for a team. */
	RECURRENCERULE("Wiederholungsmuster"),

	/** The category. */
	CATEGORY("Kategorie"),

	/** The startdate of a team. */
	STARTDATE("Start"),

	/** The enddate of a team. */
	ENDDATE("Ende"),

	/** The bpmn name of an activity. */
	BPMN("BPMN"),

	/** The firstname of a person. */
	FIRSTNAME("Vorname"),

	/** The familyname of a person. */
	FAMILYNAME("Nachname"),

	/** The successor of an activity. */
	SUCCESSOR("Nachfolger"),

	/** The rolegroup of a role. */
	ROLEGROUP("Rollengruppe"),

	/** The name of role. */
	ROLE("Rolle"),

	/** The predecessor of a workitem. */
	PREDECESSOR("Vorgänger"),

	/** The accountable in a activity or subactivity. */
	ACCOUNTABLE("Rechenschaftsgebender"),

	/** The supporter in a activity or subactivity. */
	SUPPORTER("Unterstützer"),

	/** The consultant in a activity or subactivity. */
	CONSULTANT("Berater"),

	/** The informed in a activity or subactivity. */
	INFORMED("Informierte"),

	/** The teamfraction of a person. */
	TEAMFRACTION("Teamanteil"),

	/** The full-time-equivalent of a person. */
	FTE("Vollzeitäquivalent"),

	/** The subactivities of an activity. */
	SUBACTIVITIES("Teilaktivitäten"),

	/** The allocation. */
	ALLOCATION("Allokation"),

	/** The name of persons. */
	PERSONS("Personen"),

	/** The rolegroups. */
	ROLEGROUPS("Rollengruppen"),

	/** The teams. */
	TEAMS("Teams"),

	/** The reports. */
	REPORTS("Reports"),

	/** The name of teamroles. */
	TEAMROLES("Teamrollen"),

	/** The name of howtos. */
	HOWTOS("HowTos"),

	/** The name of used roles. */
	USEDROLES("Beteiligte Rollen"),

	/** The name of a responsible role. */
	RESPONSIBLEROLE("Verantwortliche Rolle"),

	/** The persondata. */
	PERSONDATA("Stammdaten"),

	/** The summary. */
	SUMMARY("Zusammenfassung"),

	/** The data of contacts. */
	CONTACTS("Kontaktdaten"),

	/** The contact. */
	CONTACTS2("Kontakte"),

	/** The data. */
	DATA("Daten"),

	/** The name. */
	NAME("Name"),

	/** The responsibilities. */
	RESPONSIBILITIES("Verantwortungen"),

	/** The responsibility. */
	RESPONSIBILITY("Verantwortung"),

	/** The competencies. */
	COMPETENCETREE("Kompetenzbaum"),

	/** The competencies. */
	COMPETENCIES("Kompetenzen"),

	/** The competence. */
	COMPETENCE("Kompetenz"),

	/** The opportunities. */
	OPPORTUNITIES("Befugnisse"),

	/** The rules. */
	RULES("Regeln"),

	/** The adress. */
	ADRESS("Adresse"),

	/** The mail. */
	MAIL("Mail"),

	/** The mobile. */
	MOBILE("Mobil"),

	/** The unknown. */
	UNKNOWN("Unknown"),

	/** The phone. */
	PHONE("Festnetz"),

	/** The rolegroup which is defined by role. */
	CARRYROLEGROUP("Merkmalstragende Rollengruppe"),

	/** The roles which define a rolegroup. */
	BRANDROLE("Merkmalsgebende Rolle"),

	/** The parent. */
	PARENT("Vererber"),

	/** The synonyms. */
	SYNONYMS("Synonyme"),

	/** The status. */
	STATUS("Status"),

	/** The needed persons for a team. */
	NEEDED("Mindestanzahl"),

	/** The competence level of person for role. */
	LEVEL("Mindestlevel"),

	/** The person. */
	PERSON("Person"),

	/** The organisation. */
	ORGANISATION("Organisation");

	/** The param. */
	private final String param;

	/**
	 * Instantiates a new parameter.
	 *
	 * @param param the param
	 */
	Parameter(final String param) {
		this.param = param;
	}

	/**
	 * Gets the.
	 *
	 * @param value the value
	 * @return the parameter
	 */
	public static Parameter get(String value) {
		Parameter[] params = values();
		for (Parameter parameter : params) {
			if (value.contains(parameter.toString())) {
				return parameter;
			}
		}
		return null;
	}
	
	private String detail;
	
	public String getDetail() {
		return detail;
	}
	
	public void setDetail(String detail) {
		this.detail = detail;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return param;
	}

}
