package com.alfalahsoftech.ui.object;

import java.util.Date;

import com.alfalahsoftech.inv.entity.EOOrderDayDetail;
import com.alfalahsoftech.web.AFObject;

@SuppressWarnings("serial")
public class EOOrderDayDetailUIO extends AFObject{
	public Long primaryKey;
	public String orderNo;
	public String itemID;
	public String orderStatus;
	public String notes;
	public Integer quantity= Integer.valueOf(0);
	public Date orderDate;
	public Double gstPerc;
	public Double unitCost;
	public String clientName;
	public String name;
	public Double totalPrice;
	
	EOOrderDayDetailUIO() {}
	public 	EOOrderDayDetailUIO(EOOrderDayDetail eo) {
		this.primaryKey = eo.primaryKey();
		this.orderNo = eo.getOrderNo();
		this.orderStatus = eo.getOrderStatus();
		this.notes = eo.getNotes();
		this.quantity = eo.getQuantity();
		this.orderDate = eo.getOrderDate();
		this.clientName = eo.getEoClient().getClientName();
		this.name= eo.getName();
		this.gstPerc = eo.getGstPerc();
		this.unitCost = eo.getUnitCost();
		this.totalPrice = eo.getUnitCost()*this.quantity;

	}
}