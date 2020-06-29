package com.alfalahsoftech.inv.entity;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import com.alfalahsoftech.alframe.annotation.AFSerialize;
import com.alfalahsoftech.ui.object.EOClientUIO;
import com.alfalahsoftech.web.AFObject;

@Entity
//@AFSerialize(iid = "EOClient_001", keys = { "clientID", "clientName", "contactNo", "city" })
@SequenceGenerator(name="EOClient_SEQ", allocationSize=1,sequenceName="EOClient_SEQ")
public class EOClient extends AFMainEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator="EOClient_SEQ")
	private Long primaryKey;
	private String clientName;
	private String clientID;
	private String contactNo;
	private String emrgContactNo;
	private String address;
	private String city;
	private String district;
	private Integer stateCode;
	private String gstNo;
	private String emailID;
	private Integer isActive;
	private String notes;
	private String panCardNo;
	private String state;
	private Date lastOrderDate = new Date();
	private String orderDays;
	private Integer noOfProducts;
	private String routeName;

	//days
	private boolean  sun;
	private boolean  mon;
	private boolean  tue;
	private boolean  wed;
	private boolean  thu;
	private boolean  fri;
	private boolean  sat;


	//relations
	@ManyToOne()
	@JoinColumn(name="eoRoutePK")
	private EORoute eoRoute;

	/*@OneToMany(mappedBy = "eoClient")
	private Set<EOBrand> brandList = new LinkedHashSet<>();*/

	@OneToMany(mappedBy = "eoClient")
	private Set<EOItemSold> itemSoldList = new LinkedHashSet<>();
	@Override
	public Long primaryKey() {

		return this.primaryKey;
	}
	public void setPrimaryKey(long primaryKey) {
		this.primaryKey = primaryKey;
	}


	public EORoute getEoRoute() {
		return eoRoute;
	}

	public void setEoRoute(EORoute eoRoute) {
		this.eoRoute = eoRoute;
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

	public String getEmailID() {
		return emailID;
	}

	public void setEmailID(String emailID) {
		this.emailID = emailID;
	}

	public Integer getIsActive() {
		return isActive;
	}

	public void setIsActive(Integer isActive) {
		this.isActive = isActive;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getPanCardNo() {
		return panCardNo;
	}

	public void setPanCardNo(String panCardNo) {
		this.panCardNo = panCardNo;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Date getLastOrderDate() {
		return lastOrderDate;
	}

	public void setLastOrderDate(Date lastOrderDate) {
		this.lastOrderDate = lastOrderDate;
	}

	public String getOrderDays() {
		return orderDays;
	}

	public void setOrderDays(String orderDays) {
		this.orderDays = orderDays;
	}

	public Integer getNoOfProducts() {
		return noOfProducts;
	}

	public void setNoOfProducts(Integer noOfProducts) {
		this.noOfProducts = noOfProducts;
	}

	public String getRouteName() {
		return routeName;
	}

	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}

	/*	public Set<EOBrand> getBrandList() {
		return brandList;
	}

	public void setBrandList(Set<EOBrand> brandList) {
		this.brandList = brandList;
	}*/

	public Set<EOItemSold> getItemSoldList() {
		return itemSoldList;
	}

	public void setItemSoldList(Set<EOItemSold> itemSoldList) {
		this.itemSoldList = itemSoldList;
	}

	public String getClientName() {
		return this.clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getClientID() {
		return this.clientID;
	}

	public void setClientID(String clientID) {
		this.clientID = clientID;
	}

	public String getContactNo() {
		return this.contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return this.district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getEmrgContactNo() {
		return this.emrgContactNo;
	}

	public void setEmrgContactNo(String emrgContactNo) {
		this.emrgContactNo = emrgContactNo;
	}

	public boolean isSun() {
		return sun;
	}

	public void setSun(boolean sun) {
		this.sun = sun;
	}

	public boolean isMon() {
		return mon;
	}

	public void setMon(boolean mon) {
		this.mon = mon;
	}

	public boolean isTue() {
		return tue;
	}

	public void setTue(boolean tue) {
		this.tue = tue;
	}

	public boolean isWed() {
		return wed;
	}

	public void setWed(boolean wed) {
		this.wed = wed;
	}

	public boolean isThu() {
		return thu;
	}

	public void setThu(boolean thu) {
		this.thu = thu;
	}

	public boolean isFri() {
		return fri;
	}

	public void setFri(boolean fri) {
		this.fri = fri;
	}

	public boolean isSat() {
		return sat;
	}

	public void setSat(boolean sat) {
		this.sat = sat;
	}

	public EOClientUIO getUIEOClient() {
		EOClientUIO ui=	new EOClientUIO(this);
		return ui;

	}


}
