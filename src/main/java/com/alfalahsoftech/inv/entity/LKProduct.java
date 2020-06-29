package com.alfalahsoftech.inv.entity;

import java.sql.Date;

import javax.persistence.*;

public class LKProduct extends AFMainEntity{
	
	public long primaryKey;
	public String productID;
	public String prodcutName;
	public String description;
	public Number productNumber;
	public Number productQnty;
	public Number price;
//	public Number stock;
	public Date  receivingDate;
	
	@ManyToOne
	public EOStock stock;
	@ManyToOne
	public EOSupplier supplier;
	
	
	public Long primaryKey(){
		return this.primaryKey;
	}

	
}
