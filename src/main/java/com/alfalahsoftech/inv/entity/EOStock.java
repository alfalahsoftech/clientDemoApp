package com.alfalahsoftech.inv.entity;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.OneToMany;

public class EOStock extends AFMainEntity {
	public String stockName;
	public int quantity;
	@OneToMany
	public Set<LKProduct> productArray = new LinkedHashSet<>();
	private Long primaryKey;

	@Override
	public Long primaryKey() {
		return this.primaryKey;
	}
}
