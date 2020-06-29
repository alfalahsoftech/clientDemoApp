package com.alfalahsoftech.inv.entity;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import com.alfalahsoftech.web.AFObject;

@Entity
@SequenceGenerator(name="EORoute_SEQ", allocationSize=1,sequenceName="EORoute_SEQ")
public class EORoute extends AFMainEntity {

	
//	@Column(insertable=false,updatable=false)
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator="EORoute_SEQ")
	private Long primaryKey;
	private String routeName;
	private String orderDays;
	private String stateName;
	private String notes;
	//days
	private boolean  sun;
	private boolean  mon;
	private boolean  tue;
	private boolean  wed;
	private boolean  thu;
	private boolean  fri;
	private boolean  sat;
	
	@OneToMany(mappedBy="eoRoute")
	private Set<EOClient> clientList = new LinkedHashSet<>();

	public String getOrderDays() {
		return orderDays;
	}

	public void setOrderDays(String orderDays) {
		this.orderDays = orderDays;
	}

	@Override
	public Long primaryKey() {
		// TODO Auto-generated method stub
		return this.primaryKey;
	}

	public String getRouteName() {
		return this.routeName;
	}

	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public boolean isSun() {
		return sun;
	}

	public void setSun(boolean sun) {
		this.sun = sun;
	}

	public boolean isMon() {
		return mon;
	}

	public void setMon(boolean mon) {
		this.mon = mon;
	}

	public boolean isTue() {
		return tue;
	}

	public void setTue(boolean tue) {
		this.tue = tue;
	}

	public boolean isWed() {
		return wed;
	}

	public void setWed(boolean wed) {
		this.wed = wed;
	}

	public boolean isThu() {
		return thu;
	}

	public void setThu(boolean thu) {
		this.thu = thu;
	}

	public boolean isFri() {
		return fri;
	}

	public void setFri(boolean fri) {
		this.fri = fri;
	}

	public boolean isSat() {
		return sat;
	}

	public void setSat(boolean sat) {
		this.sat = sat;
	}

	public Set<EOClient> getClientList() {
		return clientList;
	}

	public void setClientList(Set<EOClient> clientList) {
		this.clientList = clientList;
	}
	
	public int noOfClients() {
		return this.getClientList().size();
	}
	
	public UIRoute getUIRoute(EORoute eo) {
		return new UIRoute( eo);
	}
	
	class UIRoute extends AFObject{
		public Long primaryKey;
		public String routeName;
		public String orderDays;
		public String stateName;
		public String notes;
		//days
		public boolean  sun;
		public boolean  mon;
		public boolean  tue;
		public boolean  wed;
		public boolean  thu;
		public boolean  fri;
		public boolean  sat;
		
		UIRoute(){}
		UIRoute(EORoute eo){
			this.primaryKey = eo.primaryKey();
			this.routeName = eo.getRouteName();
			this.orderDays = eo.getOrderDays();
			this.stateName = eo.getStateName();
			this.notes = eo.getNotes();
			this.sun = eo.isSun();
			this.mon = eo.isMon();
			this.tue = eo.isTue();
			this.wed = eo.isWed();
			this.thu = eo.isThu();
			this.fri = eo.isFri();
			this.sat = eo.isSat();
			
			
		}
		
	}
	
	

}
