package com.alfalahsoftech.inv.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@SequenceGenerator(name="EOUser_SEQ", allocationSize=1,sequenceName="EOUser_SEQ")
public class EOUser extends AFMainEntity{
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator="EOUser_SEQ")
	private Long primaryKey;
	protected String userName = "Enter User Name Here";
	protected String userPassword = "Enter User Password Here";
	protected String address;
	protected String firstName = "Enter User First Name Here";
	protected String lastName = "Enter User Last Name Here";
	protected String emailID;
	@Temporal(TemporalType.TIMESTAMP)
	protected Date lastPassChgDate = new Date() ;
	private Integer contactNo;
	
	@Override
	public Long primaryKey() {
		
		return this.primaryKey;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public String getEmailID() {
		return emailID;
	}

	public void setEmailID(String emailID) {
		this.emailID = emailID;
	}

	public Date getLastPassChgDate() {
		return lastPassChgDate;
	}

	public void setLastPassChgDate(Date lastPassChgDate) {
		this.lastPassChgDate = lastPassChgDate;
	}

	public Integer getContactNo() {
		return contactNo;
	}

	public void setContactNo(Integer contactNo) {
		this.contactNo = contactNo;
	}
	@Override
	public <T> T updateObject(Class<T> t, String req) {
		this.setLastPassChgDate(new Date());
		return super.updateObject(t, req);
	}
	
}
