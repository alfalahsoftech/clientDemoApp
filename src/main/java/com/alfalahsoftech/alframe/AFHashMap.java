package com.alfalahsoftech.alframe;

import java.util.Collection;
import java.util.HashMap;

import com.alfalahsoftech.alframe.factory.FNC;


public class AFHashMap<K, V> extends HashMap<K, V> {
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	public AFHashMap<String, Object> hashValue(String key) {
		AFHashMap<String, Object> val = (AFHashMap<String, Object>) this.get(key);
		return val == null ? new AFHashMap<>() : val;
	}

	public Object get(String key) {
		Object _val = super.get(key);
		return _val;
	}

	public Long longValue(String key) {
		return Long.valueOf(super.get(key).toString());
	}
	public Double doubleValue(String key) {
		return Double.valueOf(super.get(key).toString());
	}
	public String stringValue(String keyPath) {
		return this.stringValue(keyPath, null);
	}

	public String stringValue(String keyPath, String defultStr) {
		Object obj = this.get(keyPath);
		return obj == null ? defultStr : String.valueOf(obj).trim();
	}
	public boolean boolValue(final Object key) {
		Object v = this.get(key);
		if (v == null) {
			return false;
		}
		if (v instanceof String) {
			if (FNC.EMPTYSTR.equals(v)) {
				return false;
			}
			String s = (String) v;
			char c0 = s.charAt(0);
			if ((c0 == 'Y' || c0 == 'y') && (s.equalsIgnoreCase(FNC.YES) || s.equalsIgnoreCase(FNC.NULL_LC))) {
				return true;
			}
			if ((c0 == 't' || c0 == 'T') && s.equalsIgnoreCase(FNC.TRUE)) {
				return true;
			}
			if (s.length() == 1 &&  (c0 == '1')) {
				return true;
			}
			return false;
		}
		if (v instanceof Boolean) {
			return ((Boolean) v).booleanValue();
		}
		if (v instanceof Number) {
			return ((Number) v).intValue() != 0;
		}
		if (v instanceof Collection) {
			return !((Collection<?>) v).isEmpty();
		}
		return true;
	}
}
