package com.alfalahsoftech.alframe.ajax;

import org.json.JSONObject;

import com.alfalahsoftech.alframe.AFHashMap;
import com.alfalahsoftech.alframe.util.AFJsonParserUtil;
import com.alfalahsoftech.web.AFObject;

public class AFRequestObject extends AFObject {
	public static final String QP_HASH = "queryParamHash";
	private static final String SEL_HASH = "selectedObjectHash";
	private static final String EXT_HASH = "extraParamHash";
	private static final String META_HASH = "metaDataHash";

	AFHashMap<String, Object> requestMap;

	AFRequestObject() {
		this.requestMap = new AFHashMap<>();
		System.out.println("requestMap default =>>>>  " + this.requestMap);
	}

	AFRequestObject(String arg) {
		this.requestMap = this.isEmptyStr(arg) ? new AFHashMap<>() : AFJsonParserUtil.parsToAFHashMap(new JSONObject(arg));
		System.out.println("requestMap =>>>>  " + this.requestMap);
	}

	public AFHashMap<String, Object> requestMap() {
		return this.requestMap;
	}

	public AFHashMap<String, Object> queryParamHash() {
		return this.requestMap.hashValue(QP_HASH);
	}

	public AFHashMap<String, Object> selectedObjectHash() {
		return this.requestMap.hashValue(SEL_HASH);
	}

	public AFHashMap<String, Object> metaDataHash() {
		return this.requestMap.hashValue(META_HASH);
	}

}
