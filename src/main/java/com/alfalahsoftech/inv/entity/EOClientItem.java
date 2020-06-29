package com.alfalahsoftech.inv.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="EOClientItem_SEQ",allocationSize=1, sequenceName="EOClientItem_SEQ")
public class EOClientItem extends AFMainEntity{
	@Id
	@GeneratedValue(generator="EOClientItem_SEQ", strategy=GenerationType.SEQUENCE)
	private Long primaryKey;
	private String itemID;
	private String name;
	private String type;
	private Double weight;
	private String baseUnit;
	private Double unitCost;
	//	private Date orderDate = new Date(); //its a lookup so no need of orderDate,it will required on DailyOrder
	private Double gstPerc;
	private Double costPrice;//Rename with onHand
	private Double salingPrice;
	public Integer isActive; //boolean does create column in db
	public Integer onHand;
	@Temporal(TemporalType.TIMESTAMP)
	public Date addedOn = new Date();
	public String notes;
	private Integer quantity= new Integer(0);
	
	
	@ManyToOne
	@JoinColumn(name="FKEOITEMID")
	private EOItem eoItem;
	@ManyToOne
	@JoinColumn(name="FKEOCLIENTID")
	private  EOClient eoClient;
	
	@Override
	public Long primaryKey() {
		return this.primaryKey;
	}

	public String getItemID() {
		return itemID;
	}

	public void setItemID(String itemID) {
		this.itemID = itemID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public String getBaseUnit() {
		return baseUnit;
	}

	public void setBaseUnit(String baseUnit) {
		this.baseUnit = baseUnit;
	}

	public Double getUnitCost() {
		return unitCost;
	}

	public void setUnitCost(Double unitCost) {
		this.unitCost = unitCost;
	}

	public Double getGstPerc() {
		return gstPerc;
	}

	public void setGstPerc(Double gstPerc) {
		this.gstPerc = gstPerc;
	}

	public Double getCostPrice() {
		return costPrice;
	}

	public void setCostPrice(Double costPrice) {
		this.costPrice = costPrice;
	}

	public Double getSalingPrice() {
		return salingPrice;
	}

	public void setSalingPrice(Double salingPrice) {
		this.salingPrice = salingPrice;
	}

	public Integer getIsActive() {
		return isActive;
	}

	public void setIsActive(Integer isActive) {
		this.isActive = isActive;
	}

	public Integer getOnHand() {
		return onHand;
	}

	public void setOnHand(Integer onHand) {
		this.onHand = onHand;
	}

	public Date getAddedOn() {
		return addedOn;
	}

	public void setAddedOn(Date addedOn) {
		this.addedOn = addedOn;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public EOItem getEoItem() {
		return eoItem;
	}

	public void setEoItem(EOItem eoItem) {
		this.eoItem = eoItem;
	}

	public EOClient getEoClient() {
		return eoClient;
	}

	public void setEoClient(EOClient eoClient) {
		this.eoClient = eoClient;
	}	
	
	


}