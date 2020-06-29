package com.alfalahsoftech.inv.entity;

public class EOEmployee extends AFMainEntity {
	public Long primaryKey;

	@Override
	public Long primaryKey() {
		return this.primaryKey;
	}

}
