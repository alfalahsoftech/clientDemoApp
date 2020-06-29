package com.alfalahsoftech.inv.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.alfalahsoftech.ui.object.EOItemUIO;
import com.alfalahsoftech.web.AFObject;

@Entity
@SequenceGenerator(name="EOItem_SEQ", allocationSize=1,sequenceName="EOItem_SEQ")
public class EOItem extends AFMainEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator="EOItem_SEQ")
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

	@Override
	public Long primaryKey() {
		return this.primaryKey;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Double getWeight() {
		return this.weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public String getItemID() {
		return itemID;
	}

	public void setItemID(String itemID) {
		this.itemID = itemID;
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

	/*public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	 */
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

	/*public Date getAddedOn() {
		return addedOn;
	}

	public void setAddedOn(Date addedOn) {
		this.addedOn = addedOn;
	}
	 */
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
	public EOItemUIO getUIEOItem(EOItem eo) {
		return new EOItemUIO(eo);
	}

	

}
