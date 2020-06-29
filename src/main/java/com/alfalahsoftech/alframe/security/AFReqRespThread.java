package com.alfalahsoftech.alframe.security;

import com.alfalahsoftech.alframe.ajax.AFBaseReqRespObject;

public class AFReqRespThread extends ThreadLocal<AFBaseReqRespObject> {

	@Override
	protected AFBaseReqRespObject initialValue() {
		AFBaseReqRespObject baseReqRespObject = new AFBaseReqRespObject();
		return baseReqRespObject;
	}

	@Override
	public AFBaseReqRespObject get() {
		return super.get();
	}
}
