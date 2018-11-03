/*
 * Circlead - Develop and structure evolutionary Organisations
 * 
 * @author Dr. Matthias Wegner
 * @version 0.1
 * @since 01.04.2018
 * 
 */
package org.rogatio.circlead.model.data;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * The Class ContactData holds the data of a contact. Is a subclass of
 * {@link org.rogatio.circlead.model.data.PersonDataitem}
 * 
 * @author Matthias Wegner
 */
public class ContactDataitem {

	/** The type. */
	private String type;

	/** The subtype. */
	private String subtype;

	/** The organisation. */
	private String organisation;

	/** The address. */
	private String address;

	/** The mobile. */
	private String mobile;

	/** The phone. */
	private String phone;

	/** The mail. */
	private String mail;

	/**
	 * Gets the organisation.
	 *
	 * @return the organisation
	 */
	public String getOrganisation() {
		return organisation;
	}

	/**
	 * Sets the organisation.
	 *
	 * @param organisation the new organisation
	 */
	public void setOrganisation(String organisation) {
		this.organisation = organisation;
	}

	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Sets the type.
	 *
	 * @param type the new type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Gets the subtype.
	 *
	 * @return the subtype
	 */
	public String getSubtype() {
		return subtype;
	}

	/**
	 * Sets the subtype.
	 *
	 * @param subtype the new subtype
	 */
	public void setSubtype(String subtype) {
		this.subtype = subtype;
	}

	/**
	 * Gets the address.
	 *
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Sets the address.
	 *
	 * @param address the new address
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * Gets the mobile.
	 *
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * Sets the mobile.
	 *
	 * @param mobile the new mobile
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * Gets the phone.
	 *
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * Sets the phone.
	 *
	 * @param phone the new phone
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * Gets the mail.
	 *
	 * @return the mail
	 */
	public String getMail() {
		return mail;
	}

	/**
	 * Sets the mail.
	 *
	 * @param mail the new mail
	 */
	public void setMail(String mail) {
		this.mail = mail;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	@JsonIgnore
	public String getName() {
		StringBuilder sb = new StringBuilder();
		if (this.getType() != null) {
			sb.append(this.getType());
		}
		if (this.getSubtype() != null) {
			sb.append(" - " + this.getSubtype());
		}
		if (this.getOrganisation() != null) {
			sb.append(" (" + this.getOrganisation() + ")");
		}
		return sb.toString();
	}

	/**
	 * Gets the as list.
	 *
	 * @return the as list
	 */
	@JsonIgnore
	public List<String> getAsList() {
		List<String> list = new ArrayList<String>();

		if (this.getAddress() != null) {
			list.add("Address: " + this.getAddress());
		}
		if (this.getPhone() != null) {
			list.add("Phone: " + this.getPhone());
		}
		if (this.getMobile() != null) {
			list.add("Mobile: " + this.getMobile());
		}
		if (this.getMail() != null) {
			list.add("Mail: " + this.getMail());
		}

		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@JsonIgnore
	@Override
	public String toString() {
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.setSerializationInclusion(Include.NON_NULL);
			mapper.enable(SerializationFeature.INDENT_OUTPUT);
			return mapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}

}
