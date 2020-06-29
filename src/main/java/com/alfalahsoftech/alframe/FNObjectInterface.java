package com.alfalahsoftech.alframe;

public interface FNObjectInterface extends FNKeyValueInterface {
	public <T> T createObjectKeyArg(Class<T> classObject, String destKey, String argKey, Object... objects);
	// public Object[] createObjArrayFromKeyArg(String argKey, Object...
	// paramObjects); // Don't know where it is required
}