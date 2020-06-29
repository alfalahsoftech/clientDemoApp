package com.alfalahsoftech.alframe;

import java.util.ArrayList;
import java.util.Collection;

import com.alfalahsoftech.alframe.persistence.PSSchema;

public class AFArrayList<E> extends ArrayList<E> {
	private static final long serialVersionUID = 1L;

	public AFArrayList() {
		super();
	}

	@SuppressWarnings("unchecked")
	public AFArrayList(Object obj[]) {
		super();
		//this.addAll(Arrays.asList(obj));
	}

	public AFArrayList(Collection<? extends E> c) {
		super(c);
	}

	public E objectAtIndex(int index) {
		try {
			return super.get(index);
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}
	
	  

}
