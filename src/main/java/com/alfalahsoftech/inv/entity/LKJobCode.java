package com.alfalahsoftech.inv.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(sequenceName="LKJobCode_SEQ", name="LKJobCode_SEQ", allocationSize=1 )
public class LKJobCode {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="LKJobCode_SEQ")
	private Long primaryKey;
	public String jobCode;
	public String jobName;
	public String payRate;
	private Boolean isActive;


	public Long getPrimaryKey() {
		return primaryKey;
	}
	public void setPrimaryKey(Long primaryKey) {
		this.primaryKey = primaryKey;
	}
	public String getJobCode() {
		return jobCode;
	}
	public void setJobCode(String jobCode) {
		this.jobCode = jobCode;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getPayRate() {
		return payRate;
	}
	public void setPayRate(String payRate) {
		this.payRate = payRate;
	}
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}




	public static LKJobCode newLKJobCode(Long pk) {
		LKJobCode jb = new  LKJobCode();
		jb.setPrimaryKey(pk);
		return jb;
	}
	public static LKJobCode newJobCode(long pk) {
		LKJobCode lk = new LKJobCode();
		lk.setPrimaryKey(pk);
		return lk;
	}

}
