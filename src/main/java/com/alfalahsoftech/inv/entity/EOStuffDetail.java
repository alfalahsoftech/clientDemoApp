package com.alfalahsoftech.inv.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
@Entity
@SequenceGenerator(name="EOStuffDetail_SEQ", allocationSize=1,sequenceName="EOStuffDetail_SEQ")
public class EOStuffDetail extends AFMainEntity {
//	@Column(insertable=false,updatable=false)
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator="EOStuffDetail_SEQ")
	public Long primaryKey;
	@Temporal(TemporalType.DATE)
	public Date busiDate;
	public Double ttMade=new Double(0.0);
	public Double ttBundled= Double.valueOf(0.0);
	public Double ttDried= Double.valueOf(0.0);
	public String name;
	public Integer isPresent= new Integer(0);
	public String absentReason="N/A";
	
	@ManyToOne()
	@JoinColumn(name="eoStuffPK")
	public EOStuff eoStuff;
	
	@OneToOne
	@JoinColumn(name="lkJobCodePK")
	public LKJobCode jobCode;
	
	@ManyToOne()
	@JoinColumn(name="eoStaffPK")
	public EOStaff eoStaff;
	
	@Override
	public Long primaryKey() {
		return this.primaryKey;
	}
	public Date getBusiDate() {
		return busiDate;
	}
	public void setBusiDate(Date busiDate) {
		this.busiDate = busiDate;
	}
	public Double getTtMade() {
		return ttMade;
	}
	public void setTtMade(Double ttMade) {
		this.ttMade = ttMade;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getIsPresent() {
		return isPresent;
	}
	public void setIsPresent(Integer isPresent) {
		this.isPresent = isPresent;
	}

	public EOStuff getEoStuff() {
		return eoStuff;
	}
	public void setEoStuff(EOStuff eoStuff) {
		this.eoStuff = eoStuff;
	}
	public String getAbsentReason() {
		return absentReason;
	}
	public void setAbsentReason(String absentReason) {
		this.absentReason = absentReason;
	}
	
	public void setPrimaryKey(Long primaryKey) {
		this.primaryKey = primaryKey;
	}
	
	public LKJobCode getJobCode() {
		return jobCode;
	}
	public void setJobCode(LKJobCode jobCode) {
		this.jobCode = jobCode;
	}
	
	public Double getTtDried() {
		return ttDried;
	}
	public void setTtDried(Double ttDried) {
		this.ttDried = ttDried;
	}
	public Double getTtBundled() {
		return ttBundled;
	}
	public void setTtBundled(Double ttBundled) {
		this.ttBundled = ttBundled;
	}
	public EOStaff getEoStaff() {
		return eoStaff;
	}
	public void setEoStaff(EOStaff eoStaff) {
		this.eoStaff = eoStaff;
	}
/*	@Override
	public String toString() {
		return "EOStuffDetail [primaryKey=" + primaryKey + ", busiDate=" + busiDate + ", ttMade=" + ttMade
				+ ", ttBundled=" + ttBundled + ", ttDried=" + ttDried + ", name=" + name + ", isPresent=" + isPresent
				+ ", absentReason=" + absentReason + ", eoStuff=" + eoStuff.getBusiDate()+"_"+eoStuff.getEoStore().getStoreName() + ", jobCode=" + jobCode.getJobCode() + ", eoStaff="
				+ eoStaff.getFirstName() + "]";
	}*/

	
	/*@Override
	public boolean equals(Object obj) {
		if(obj == null || !(obj instanceof EOStuffDetail))
			return false;
		if(this==obj)
			return true;

		EOStuffDetail eo = (EOStuffDetail)obj;
		if( eo.getBusiDate()!= this.getBusiDate()) {
			return  false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		
		return this.primaryKey()!=null?this.primaryKey().intValue()*31:31;
	}*/
}
