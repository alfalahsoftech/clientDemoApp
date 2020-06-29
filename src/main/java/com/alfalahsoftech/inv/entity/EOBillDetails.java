package com.alfalahsoftech.inv.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

//@Entity
@SequenceGenerator(name="EOBillDetails_SEQ", allocationSize=1,sequenceName="EOBillDetails_SEQ")
public class EOBillDetails extends AFMainEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3261447502791482753L;
//	@Column(updatable=false, insertable=false)
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator="EOBillDetails_SEQ")
	private Long primaryKey;
	private Date busiDate;
	private Date dueDate;
	private Integer ttQuantity;
	private Double ttAmount;
	private Double paidAmount;
	private Double dueAmount;
	private String payMode;
	private String clientName;
	private Integer stateCode;
	private String gstNo;
	private String address;
	private String contactNo;
	private String billDetails;
	
	public Long primaryKey() {
		return primaryKey;
	}
	
	public Date getBusiDate() {
		return busiDate;
	}
	public void setBusiDate(Date busiDate) {
		this.busiDate = busiDate;
	}
	public Date getDueDate() {
		return dueDate;
	}
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	public Integer getTtQuantity() {
		return ttQuantity;
	}
	public void setTtQuantity(Integer ttQuantity) {
		this.ttQuantity = ttQuantity;
	}
	public Double getTtAmount() {
		return ttAmount;
	}
	public void setTtAmount(Double ttAmount) {
		this.ttAmount = ttAmount;
	}
	public Double getPaidAmount() {
		return paidAmount;
	}
	public void setPaidAmount(Double paidAmount) {
		this.paidAmount = paidAmount;
	}
	public Double getDueAmount() {
		return dueAmount;
	}
	public void setDueAmount(Double dueAmount) {
		this.dueAmount = dueAmount;
	}
	public String getPayMode() {
		return payMode;
	}
	public void setPayMode(String payMode) {
		this.payMode = payMode;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public Integer getStateCode() {
		return stateCode;
	}
	public void setStateCode(Integer stateCode) {
		this.stateCode = stateCode;
	}
	public String getGstNo() {
		return gstNo;
	}
	public void setGstNo(String gstNo) {
		this.gstNo = gstNo;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getContactNo() {
		return contactNo;
	}
	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}
	
	
}
