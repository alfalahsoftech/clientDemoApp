package com.alfalahsoftech.alframe.annotation;

public class AFControllerSetup {
	public String id;
	public String actionID;
	public String controllerName;
	public String actionOnKey;
	public String methodName;

	@Override
	public String toString() {
		return "AFControllerSetup [id=" + this.id + ", actionID=" + this.actionID + ", controllerName=" + this.controllerName + ", actionOnKey=" + this.actionOnKey + ", methodName=" + this.methodName + "]";
	}

}
