package com.alfalahsoftech.alframe.util;

import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.alfalahsoftech.alframe.AFArrayList;
import com.alfalahsoftech.alframe.AFHashMap;

public class AFJsonParserUtil {

	public static AFHashMap<String, Object> parsToAFHashMap(JSONObject jsonObj) {
		AFHashMap<String, Object> jsonMap = new AFHashMap<>();
		try {
			jsonMap.putAll(toMap(jsonObj));
			return jsonMap;
		} catch (Exception e) {
			throw new RuntimeException("AFJsonParserUtil~parsToAFHashMap");
		}

	}

	@SuppressWarnings("rawtypes")
	public static AFHashMap<String, Object> toMap(JSONObject object) throws JSONException {
		AFHashMap<String, Object> map = new AFHashMap<>();
		Iterator keys = object.keys();
		while (keys.hasNext()) {
			String key = (String) keys.next();
			map.put(key, fromJson(object.get(key)));
		}
		return map;
	}

	public static AFArrayList<Object> toList(JSONArray array) throws JSONException {
		AFArrayList<Object> list = new AFArrayList<>();
		for(int i = 0; i < array.length(); i++) {
			list.add(fromJson(array.get(i)));
		}
		return list;
	}

	private static Object fromJson(Object json) throws JSONException {
		if (json == JSONObject.NULL) {
			return null;
		} else if (json instanceof JSONObject) {
			return toMap((JSONObject) json);
		} else if (json instanceof JSONArray) {
			return toList((JSONArray) json);
		} else {
			return json;
		}
	}
}
