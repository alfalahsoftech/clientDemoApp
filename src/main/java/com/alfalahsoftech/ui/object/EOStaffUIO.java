package com.alfalahsoftech.ui.object;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.alfalahsoftech.inv.entity.EOStaff;
import com.alfalahsoftech.inv.entity.EOStore;

public class EOStaffUIO {
	
	public	Long primaryKey;
	public String userName;
	public String firstName;
	public String lastName;
	public String address;
	public Integer contactNo;
	public Boolean isActive;
	public String storeName;
	
	public EOStaffUIO(EOStaff eoStaff) {
		this.primaryKey = eoStaff.primaryKey();
		this.userName = eoStaff.getUserName();
		this.firstName = eoStaff.getFirstName();
		this.lastName = eoStaff.getLastName();
		this.address = eoStaff.getAddress();
		this.contactNo = eoStaff.getContactNo();
		this.isActive = eoStaff.getIsActive();
		this.storeName = eoStaff.getEoStore().getStoreName();
	}

}
