package org.rogatio.circlead.model;

public enum Parameter {

	ABBREVIATION("Abkürzung"),
	IMAGE("Bild"),
	ABBREVIATION2("Kürzel"),
	ROLEPERSONS("Rollenträger"),
	TASKS("Aufgaben"),
	ID("Id"),
	UNRELATED("Unbesetzt"),
	PURPOSE("Zweck / Kurzbeschreibung"),
	PURPOSESHORT("Zweck"),
	CHILDS("Erben"),
	VERSION("Version"),
	MODIFIED("Geändert"),
	CREATED("Erstellt"),
	CONTACTPERSON("Ansprechpartner"),
	ACTIVITYID("AID"),
	TYPE("Typ"),
	SUBTYPE("Subtyp"),
	ACTIVITY("Aktivität"),
	ACTIVITIES("Aktivitäten"),
	ROLES("Rollen"),
	RESPONSIBLE("Durchführender"),
	RESPONSIBLE2("Verantwortlicher"),
	PARENTACTIVITY("Übergeordnete Aktivität"),
	RESULT("Erwartetes Ergebnis"),
	DESCRIPTION("Beschreibung"),
	BPMN("BPMN"),
	FIRSTNAME("Vorname"),
	FAMILYNAME("Nachname"),
	SUCCESSOR("Nachfolger"),
	ROLEGROUP("Rollengruppe"),
	PREDECESSOR("Vorgänger"),
	ACCOUNTABLE("Rechenschaftsgebender"),
	SUPPORTER("Unterstützer"),
	CONSULTANT("Berater"),
	INFORMED("Informierte"),
	SUBACTIVITIES("Teilaktivitäten"),
	PERSONS("Personen"),
	HOWTOS("HowTos"),
	USEDROLES("Beteiligte Rollen"),
	RESPONSIBLEROLE("Verantwortliche Rolle"),
	PERSONDATA("Stammdaten"),
	SUMMARY("Zusammenfassung"),
	CONTACTS("Kontaktdaten"),
	CONTACTS2("Kontakte"),
	DATA("Daten"),
	NAME("Name"),
	RESPONSIBILITIES("Verantwortungen"),
	RESPONSIBILITY("Verantwortung"),
	COMPETENCIES("Kompetenzen"),
	OPPORTUNITIES("Befugnisse"),
	RULES("Regeln"),
	ADRESS("Adresse"),
	MAIL("Mail"),
	MOBILE("Mobil"),
	UNKNOWN("Unknown"),
	PHONE("Festnetz"),
	CARRYROLEGROUP("Merkmalstragende Rollengruppe"),
	BRANDROLE("Merkmalsgebende Rolle"),
	PARENT("Vererber"),
	SYNONYMS("Synonyme"),
	STATUS("Status"),
	ORGANISATION("Organisation");
	
	private final String param;
	
	Parameter(final String param) {
		this.param = param;
	}
	
	@Override
	public String toString() {
		return param;
	}
	
}
