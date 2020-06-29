package com.alfalahsoftech.alframe.factory;

public class AFMenuItemFactory {
	private static volatile AFMenuItemFactory factoryObj;

	public static void executeFactory() {
		if (factoryObj == null) {
			synchronized (AFMenuItemFactory.class) {
				if (factoryObj == null) {
					factoryObj = new AFMenuItemFactory();
				}
			}
		}
	}

	public AFMenuItemFactory factoryObj() {
		return factoryObj;
	}

	public void loadData() {

	}
}
