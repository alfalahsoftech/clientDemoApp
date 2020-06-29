package com.alfalahsoftech.inv.entity;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
@SequenceGenerator(sequenceName="EOStrMain_SEQ", name="EOStrMain_SEQ", allocationSize=1 )
public class EOStore extends AFMainEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="EOStrMain_SEQ")
	private Long primaryKey;
	public String storeName;
	public String storeNo;
	public String storeMgr;
	private String storeLocation;  //Banke Gali
	private Boolean isActive;
	

	@Override
	public Long primaryKey() {
		
		return this.primaryKey;
	}
	
	
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public String getStoreNo() {
		return storeNo;
	}
	public void setStoreNo(String storeNo) {
		this.storeNo = storeNo;
	}
	public String getStoreMgr() {
		return storeMgr;
	}
	public void setStoreMgr(String storeMgr) {
		this.storeMgr = storeMgr;
	}
	public String getStoreLocation() {
		return storeLocation;
	}
	public void setStoreLocation(String storeLocation) {
		this.storeLocation = storeLocation;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public static EOStore newStore(Long pk) {
		EOStore eo = new EOStore();
		eo.setPrimaryKey(pk);
		return eo;
	}

}
