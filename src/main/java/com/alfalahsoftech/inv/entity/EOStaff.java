package com.alfalahsoftech.inv.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(name="EOStaff_SEQ", allocationSize=1,sequenceName="EOStaff_SEQ")
public class EOStaff extends AFMainEntity{

	private static final long serialVersionUID = 1L;
//	@Column(updatable=false,insertable=false)
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator="EOStaff_SEQ")
	private	Long primaryKey;
	private String userName;
	private String firstName;
	private String lastName;
	private String address;
	private Integer contactNo;
	private Boolean isActive;
	
	@ManyToOne
	@JoinColumn(name="eoStorePK")
	private EOStore eoStore;
	
	public Long primaryKey() {
		return primaryKey;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Integer getContactNo() {
		return contactNo;
	}
	public void setContactNo(Integer contactNo) {
		this.contactNo = contactNo;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	

	public EOStore getEoStore() {
		return eoStore;
	}

	public void setEoStore(EOStore eoStore) {
		this.eoStore = eoStore;
	}

	public String getDetails() {
		return "EOStaff [primaryKey=" + primaryKey + ", userName=" + userName + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", address=" + address + ", contactNo=" + contactNo + ", isActive="
				+ isActive + "]";
	}
	
	
	
	public EOStaff newEOStaff(Long pk) {
		EOStaff staff =  new EOStaff();
		staff.setPrimaryKey(pk);
		return staff;
	}

	public static EOStaff newStaff(long pk) {
		EOStaff staff  =  new EOStaff();
		staff.setPrimaryKey(pk);
		return staff;
	}
	
	
}