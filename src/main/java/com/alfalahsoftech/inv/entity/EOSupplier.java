package com.alfalahsoftech.inv.entity;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.OneToMany;

public class EOSupplier extends AFMainEntity {
	public long primarKey;
	public String name;
	public String address;
	public String phoneNo1;
	public String phoneNo2;
	public String emailID;

	@OneToMany
	public Set<LKProduct> productArray = new LinkedHashSet<>();
	private Long primaryKey;

	@Override
	public Long primaryKey() {
		// TODO Auto-generated method stub
		return this.primaryKey;
	}

}
