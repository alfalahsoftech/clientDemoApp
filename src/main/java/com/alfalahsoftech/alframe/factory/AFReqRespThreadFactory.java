package com.alfalahsoftech.alframe.factory;

import com.alfalahsoftech.alframe.ajax.AFBaseReqRespObject;
import com.alfalahsoftech.alframe.security.AFReqRespThread;

public class AFReqRespThreadFactory {
	private AFReqRespThreadFactory() {
	}

	private static AFReqRespThreadFactory factory = new AFReqRespThreadFactory();

	public static AFReqRespThreadFactory factory() {
		return factory;
	}

	private AFReqRespThread reqRespThread = new AFReqRespThread();

	public AFBaseReqRespObject getReqRespObject() {
		return this.reqRespThread != null ? this.reqRespThread.get() : null;
	}

	public AFBaseReqRespObject getReqRespObject(String requestJson) {
		if (this.reqRespThread == null) {
			return null;
		}
		AFBaseReqRespObject baseReqRespObject = this.reqRespThread.get();
		baseReqRespObject.setRequestJson(requestJson);
		return baseReqRespObject;
	}

	public void removeReqRespObj() {
		this.removeReqRespObj(this.getReqRespObject());
	}

	public void removeReqRespObj(AFBaseReqRespObject baseReqRespObject) {
		baseReqRespObject.closeRequest();
		this.reqRespThread.remove();
	}
}
