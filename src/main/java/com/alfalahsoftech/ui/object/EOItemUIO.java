package com.alfalahsoftech.ui.object;

import com.alfalahsoftech.inv.entity.EOClientItem;
import com.alfalahsoftech.inv.entity.EOItem;
import com.alfalahsoftech.web.AFObject;
//@jsonignoreproperties(ignoreunknown = true)
public class EOItemUIO extends AFObject{
		public Long primaryKey;
		public String itemID;
		public String name;
		public String baseUnit;
		public Double unitCost;
		public Double gstPerc;
		public Double salingPrice;
		public Integer onHand;
		public String notes;
		public Integer quantity= new Integer(0);
		public String clientName;

		EOItemUIO(){}

		public EOItemUIO(EOItem eo){
			this.itemID = eo.getItemID();
			this.name = eo.getName();
			this.baseUnit = eo.getBaseUnit();
			this.unitCost = eo.getUnitCost();
			this.gstPerc = eo.getGstPerc() ;
			this.salingPrice = eo.getSalingPrice();
//			this.onHand = eo.getOnHand();
			this.notes =  eo.getNotes();
			this.quantity = eo.getQuantity();
		}
		
		public EOItemUIO(EOClientItem eo){
			this.itemID = eo.getItemID();
			this.name = eo.getName();
			this.baseUnit = eo.getBaseUnit();
			this.unitCost = eo.getUnitCost();
			this.gstPerc = eo.getGstPerc() ;
			this.salingPrice = eo.getSalingPrice();
			this.onHand = eo.getOnHand();
			this.notes =  eo.getNotes();
			this.quantity = eo.getQuantity();
			this.clientName = eo.getEoClient().getClientName();
			
		}
	}