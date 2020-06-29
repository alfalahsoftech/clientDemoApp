package com.alfalahsoftech.ui.object;

import java.util.Date;

import com.alfalahsoftech.inv.entity.EOClient;
import com.alfalahsoftech.web.AFObject;

@SuppressWarnings("unused")
public class EOClientUIO extends AFObject{

		public Long primaryKey;
		public String clientName;
		public String clientID;
		public String contactNo;
		public String emrgContactNo;
		public String address;
		public String city;
		public String district;
		public Integer stateCode;
		public String gstNo;
		public String emailID;
		public Integer isActive;
		public String notes;
		public Date lastOrderDate = new Date();
		public String orderDays;
		public Integer noOfProducts;
		public String routeName;
		EOClientUIO(){}
		public EOClientUIO(EOClient eo){
			this.primaryKey = eo.primaryKey();
			this.clientID = eo.getClientID();
			this.orderDays = eo.getOrderDays();
			this.notes = eo.getNotes();
			this.clientName = eo.getClientName();
			this.contactNo = eo.getContactNo();
			this.lastOrderDate = eo.getLastOrderDate();
			this.isActive = eo.getIsActive();
			this.noOfProducts = eo.getNoOfProducts();
			this.routeName = eo.getEoRoute().getRouteName();
			this.gstNo = eo.getGstNo();
			this.address = eo.getAddress();
			this.stateCode =  eo.getStateCode();
			this.emailID = eo.getEmailID();
		}

	}