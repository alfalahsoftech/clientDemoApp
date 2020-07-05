package com.alfalahsoftech.medi.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.alfalahsoftech.inv.entity.AFMainEntity;

@Entity
@SequenceGenerator(name="EOMedicine_SEQ", sequenceName="EOMedicine_SEQ")
public class EOMedicine extends AFMainEntity {

	@Id
	@GeneratedValue(generator="EOMedicine_SEQ",strategy=GenerationType.SEQUENCE)
	private Long primaryKey;
	private String itemID;
	private String mediName;
	@Temporal(TemporalType.DATE)
	private Date expDate;
	private Double discount =  Double.valueOf(0.0);
	private Double mrp  = Double.valueOf(0.0);
	private String scheme;
	private Double netRate;
	private String batchNo;
	private String UOM;
	@Temporal(TemporalType.TIMESTAMP)
	private Date addedOn = new Date();
	private Boolean isActive;
	private String notes;
	
	//
    private Integer onHand;
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedOn;
    private Double purchasePrice;
    private String mfgBy;
	@Override
	public Long primaryKey() {
		return this.primaryKey;
	}

	public void setPrimaryKey(Long primaryKey) {
		this.primaryKey = primaryKey;
	}
	
	public String getItemID() {
		return itemID;
	}

	public void setItemID(String itemID) {
		this.itemID = itemID;
	}

	public String getMediName() {
		return mediName;
	}

	public void setMediName(String mediName) {
		this.mediName = mediName;
	}

	public Date getExpDate() {
		return expDate;
	}

	public void setExpDate(Date expDate) {
		this.expDate = expDate;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public Double getMrp() {
		return mrp;
	}

	public void setMrp(Double mrp) {
		this.mrp = mrp;
	}

	public String getScheme() {
		return scheme;
	}

	public void setScheme(String scheme) {
		this.scheme = scheme;
	}

	public Double getNetRate() {
		return netRate;
	}

	public void setNetRate(Double netRate) {
		this.netRate = netRate;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getUOM() {
		return UOM;
	}

	public void setUOM(String uOM) {
		UOM = uOM;
	}

	public Date getAddedOn() {
		return addedOn;
	}

	public void setAddedOn(Date addedOn) {
		this.addedOn = addedOn;
	}
	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Integer getOnHand() {
		return onHand;
	}

	public void setOnHand(Integer onHand) {
		this.onHand = onHand;
	}

	public Date getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	public Double getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(Double purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	public Long getPrimaryKey() {
		return primaryKey;
	}

	public String getMfgBy() {
		return mfgBy;
	}

	public void setMfgBy(String mfgBy) {
		this.mfgBy = mfgBy;
	}
	
	

}
