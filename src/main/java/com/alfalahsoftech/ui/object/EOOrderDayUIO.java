package com.alfalahsoftech.ui.object;

import java.util.Date;

import com.alfalahsoftech.inv.entity.EOOrderDay;

public class EOOrderDayUIO{
	public Long primaryKey;
	public Date orderedOn;
	public Integer ttItem;
	public String orderNo;
	public String orderStatus;
	public String clientName;
	public String notes;
	public Long clientPK;
	EOOrderDayUIO(){}
	public EOOrderDayUIO(EOOrderDay eo){
		this.primaryKey = eo.primaryKey();
		this.orderNo = eo.getOrderNo();
		this.orderStatus = eo.getOrderStatus();
		this.notes = eo.getNotes();
		this.orderedOn = eo.getOrderedOn();
		this.clientName = eo.getEoClient().getClientName();
		this.clientPK = eo.getEoClient().primaryKey();
	}
}
