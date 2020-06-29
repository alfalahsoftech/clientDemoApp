package com.alfalahsoftech.alframe.setup;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;

import com.alfalahsoftech.inv.entity.AFMainEntity;
@Entity
//@SequenceGenerator(name="AFGlobalSetup_SEQ",sequenceName="AFGlobalSetup_SEQ",allocationSize=1)
public class AFSetup extends AFMainEntity{
	
	@Id
//	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="AFGlobalSetup_SEQ")
	public Long primaryKey;
	public String iid;
	public String frndlyName;
	@Lob
	public String detail;
	@Override
	public Long primaryKey() {
		return this.primaryKey;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	
	public void setPrimaryKey(Long primaryKey) {
		this.primaryKey = primaryKey;
	}
	public AFSetup setup() {
		return null;
	}
	public String getIid() {
		return iid;
	}
	public void setIid(String iid) {
		this.iid = iid;
	}
	public String getFrndlyName() {
		return frndlyName;
	}
	public void setFrndlyName(String frndlyName) {
		this.frndlyName = frndlyName;
	}
	
	
	

}
