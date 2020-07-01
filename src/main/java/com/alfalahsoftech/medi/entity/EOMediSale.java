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
@SequenceGenerator(name="EOMediSale_SEQ", sequenceName="EOMediSale_SEQ",allocationSize=50)
public class EOMediSale extends AFMainEntity {

	@Id
	@GeneratedValue(generator="EOMediSale_SEQ",strategy=GenerationType.SEQUENCE)
	private Long primaryKey;
	private String mediName;
	@Temporal(TemporalType.DATE)
	private Date expDate = new Date();
	private Double discount =  Double.valueOf(0.0);
	private Double mrp  = Double.valueOf(0.0);
	private Integer scheme;
	private Double netRate;
	private String batchNo;
	private String UOM;
	@Temporal(TemporalType.TIMESTAMP)
	public Date addedOn = new Date();
	
	@Override
	public Long primaryKey() {
		return this.primaryKey;
	}

	public void setPrimaryKey(Long primaryKey) {
		this.primaryKey = primaryKey;
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

	public Integer getScheme() {
		return scheme;
	}

	public void setScheme(Integer scheme) {
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
	
	

}
