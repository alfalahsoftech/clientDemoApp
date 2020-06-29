package com.alfalahsoftech.inv.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

//@Entity
public class EOBrand extends AFMainEntity {

	/*@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)*/
	@Column(insertable=false,updatable=false)
	private Long primaryKey;
	private String brandName;
	private String brandID;
	@ManyToOne
	private EOClient eoClient;

	//private String
	@Override
	public Long primaryKey() {
		return this.primaryKey;
	}

	public String getBrandName() {
		return this.brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getBrandID() {
		return this.brandID;
	}

	public void setBrandID(String brandID) {
		this.brandID = brandID;
	}

}
