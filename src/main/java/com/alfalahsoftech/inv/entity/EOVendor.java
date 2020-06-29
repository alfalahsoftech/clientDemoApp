package com.alfalahsoftech.inv.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

//@Entity

public class EOVendor extends AFMainEntity {
	/*@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator="EOUser_SEQ")*/
	private Long primaryKey;
	private String vendorName;
	private String contactPerson;
	private String address1;
	private String address2;
	private String city;
	private String state;
	private String emailAddress;
	private String fedralTaxNo;
	private String vendorID;
	private String zipCode;
	private String phoneNumber;
	private String otherNumber;

	@Override
	public Long primaryKey() {
		return null;
	}

	public String getVendorName() {
		return this.vendorName;
	}

	public String getContactPerson() {
		return this.contactPerson;
	}

	public String getAddress1() {
		return this.address1;
	}

	public String getAddress2() {
		return this.address2;
	}

	public String getCity() {
		return this.city;
	}

	public String getState() {
		return this.state;
	}

	public String getEmailAddress() {
		return this.emailAddress;
	}

	public String getFedralTaxNo() {
		return this.fedralTaxNo;
	}

	public String getVendorID() {
		return this.vendorID;
	}

	public String getZipCode() {
		return this.zipCode;
	}

	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public String getOtherNumber() {
		return this.otherNumber;
	}

}
