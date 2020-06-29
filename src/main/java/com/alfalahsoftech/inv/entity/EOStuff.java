package com.alfalahsoftech.inv.entity;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
@Entity
@SequenceGenerator(name="EOStuff_SEQ", allocationSize=1,sequenceName="EOStuff_SEQ")
public class EOStuff extends AFMainEntity {
//	@Column(insertable=false,updatable=false)
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator="EOStuff_SEQ")
	private Long primaryKey;
	@Temporal(TemporalType.DATE)
	private Date busiDate;
	private Double ttMade =new Double(0.0);
	@Transient
	public int workersCount;
	@Transient
	public int workersPresent;
	private Integer isCloseDay= new Integer(0);
	private String closeDayReason="N/A";
	@OneToMany(mappedBy="eoStuff",cascade = CascadeType.PERSIST, fetch=FetchType.EAGER)
	private Set<EOStuffDetail> stuffDedtail = new LinkedHashSet<>(); 
	
	@ManyToOne
	@JoinColumn(name="eoStorePK")
	private EOStore eoStore;
	
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

	public Set<EOStuffDetail> getStuffDedtail() {
		return stuffDedtail;
	}
	public Integer getIsCloseDay() {
		return isCloseDay;
	}
	public void setIsCloseDay(Integer isCloseDay) {
		this.isCloseDay = isCloseDay;
	}
	public String getCloseDayReason() {
		return closeDayReason;
	}
	public void setCloseDayReason(String closeDayReason) {
		this.closeDayReason = closeDayReason;
	}
		
	public EOStore getEoStore() {
		return eoStore;
	}
	public void setEoStore(EOStore eoStore) {
		this.eoStore = eoStore;
	}
	
	public static EOStuff newEOStuff(Long pk) {
		EOStuff stf = new EOStuff();
		stf.setPrimaryKey(pk);
		return stf;
	}
	
	public static EOStuff copyOFEOStuff(EOStuff copyFrom) {
		EOStuff stuff = new EOStuff();
		if(copyFrom != null) {
			stuff.setBusiDate(copyFrom.getBusiDate());
			stuff.setPrimaryKey(copyFrom.primaryKey());
			stuff.setTtMade(copyFrom.getTtMade());
			stuff.workersPresent=0;
			stuff.workersCount=copyFrom.getStuffDedtail().size();
			copyFrom.getStuffDedtail().forEach(eo->{
				if(eo.getIsPresent().intValue() == 1) {
					stuff.workersPresent++;
				}
			});
			stuff.setIsCloseDay(copyFrom.getIsCloseDay());
			stuff.setCloseDayReason(copyFrom.getCloseDayReason());
			stuff.setEoStore(copyFrom.getEoStore());
		}
		return stuff;
	}

}
