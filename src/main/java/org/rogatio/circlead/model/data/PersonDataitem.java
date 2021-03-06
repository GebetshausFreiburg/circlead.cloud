/*
 * Circlead - Develop and structure evolutionary Organisations
 * 
 * @author Matthias Wegner
 * @version 0.1
 * @since 01.07.2018
 * 
 */
package org.rogatio.circlead.model.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The Class PersonDataitem holds the data for a person.
 *
 * @author Matthias Wegner
 */
public class PersonDataitem extends DefaultDataitem {

	/** The firstname. */
	private String firstname;

	/** The secondname. */
	private String secondname;

	/** The avatar. */
	private String avatar;

	/** The familyname. */
	private String familyname;

	/** The contacts. */
	private List<ContactDataitem> contacts = new ArrayList<ContactDataitem>();

	/** The data. */
	private Map<String, String> data = new HashMap<String, String>();

	/** The full time equivalent. */
	private double fullTimeEquivalent;

	/** The team fraction. */
	private double teamFraction;

	/**
	 * Sets the fullname of a person. Splits name in three parts by whitespace.
	 * First part is firstname, second part is secondname, third part is familyname.
	 * Other parts are ignored.
	 *
	 * @param fullname the new fullname
	 */
	public void setFullname(String fullname) {
		if (fullname == null) {
			return;
		}
		String parts[] = fullname.split(" ");
		if (parts.length == 1) {
			this.setFirstname(parts[0]);
		}
		if (parts.length == 2) {
			this.setFirstname(parts[0]);
			this.setFamilyname(parts[1]);
		}
		if (parts.length == 3) {
			this.setFirstname(parts[0]);
			this.setSecondname(parts[1]);
			this.setFamilyname(parts[2]);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.rogatio.circlead.model.data.DefaultDataitem#setTitle(java.lang.String)
	 */
	@Override
	public void setTitle(String title) {
		this.setFullname(title);
	}

	/**
	 * Gets the firstname.
	 *
	 * @return the firstname
	 */
	public String getFirstname() {
		return firstname;
	}

	/**
	 * Sets the firstname.
	 *
	 * @param firstname the new firstname
	 */
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	/**
	 * Gets the secondname.
	 *
	 * @return the secondname
	 */
	public String getSecondname() {
		return secondname;
	}

	/**
	 * Gets the avatar.
	 *
	 * @return the avatar
	 */
	public String getAvatar() {
		return avatar;
	}

	/**
	 * Sets the avatar.
	 *
	 * @param avatar the new avatar
	 */
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	/**
	 * Sets the secondname.
	 *
	 * @param secondname the new secondname
	 */
	public void setSecondname(String secondname) {
		this.secondname = secondname;
	}

	/**
	 * Gets the familyname.
	 *
	 * @return the familyname
	 */
	public String getFamilyname() {
		return familyname;
	}

	/**
	 * Sets the familyname.
	 *
	 * @param familyname the new familyname
	 */
	public void setFamilyname(String familyname) {
		this.familyname = familyname;
	}

	/**
	 * Gets the contacts.
	 *
	 * @return the contacts
	 */
	public List<ContactDataitem> getContacts() {
		return contacts;
	}

	/**
	 * Sets the contacts.
	 *
	 * @param contacts the new contacts
	 */
	public void setContacts(List<ContactDataitem> contacts) {
		this.contacts = contacts;
	}

	/**
	 * Gets the data.
	 *
	 * @return the data
	 */
	public Map<String, String> getData() {
		return data;
	}

	/**
	 * Sets the data.
	 *
	 * @param data the data
	 */
	public void setData(Map<String, String> data) {
		this.data = data;
	}

	/**
	 * Sets the data.
	 *
	 * @param data the data
	 */
	@JsonIgnore
	public void setData(List<Map<String, String>> data) {
		this.data = new HashMap<String, String>();

		for (Map<String, String> map : data) {
			for (String key : map.keySet()) {
				this.data.put(key, map.get(key));
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.rogatio.circlead.model.data.DefaultDataitem#getTitle()
	 */
	@Override
	public String getTitle() {
		return this.getFullname();
	}

	/**
	 * Gets the fullname.
	 *
	 * @return the fullname
	 */
	@JsonIgnore
	public String getFullname() {
		StringBuilder sb = new StringBuilder();
		if (this.getFirstname() != null) {
			sb.append(this.getFirstname() + " ");
		}
		if (this.getSecondname() != null) {
			sb.append(this.getSecondname() + " ");
		}
		if (this.getFamilyname() != null) {
			sb.append(this.getFamilyname() + " ");
		}
		return sb.toString().trim();
	}

	/**
	 * Gets the full time equivalent.
	 *
	 * @return the full time equivalent
	 */
	public double getFullTimeEquivalent() {
		return fullTimeEquivalent;
	}

	/**
	 * Sets the full time equivalent.
	 *
	 * @param fullTimeEquivalent the new full time equivalent
	 */
	public void setFullTimeEquivalent(double fullTimeEquivalent) {
		this.fullTimeEquivalent = fullTimeEquivalent;
	}

	/**
	 * Gets the team fraction.
	 *
	 * @return the team fraction
	 */
	public double getTeamFraction() {
		return teamFraction;
	}

	/**
	 * Sets the team fraction.
	 *
	 * @param teamFraction the new team fraction
	 */
	public void setTeamFraction(double teamFraction) {
		this.teamFraction = teamFraction;
	}

}
