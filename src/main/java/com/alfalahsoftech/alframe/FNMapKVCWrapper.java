package com.alfalahsoftech.alframe;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A subclass of {@link PropertyHelper} that allows values of a
 * <code>java.util.Map</code> to be accessed as if they were JavaBean properties
 * of the <code>java.util.Map</code> itself.
 *
 * <p>
 * This requires that the keys of the <code>Map</code> be valid JavaBeans
 * property names.
 *
 * <p>
 * This class includes a static initializer that invokes
 * {@link PropertyHelper#register(Class,Class)}.
 *
 * </p>
 * <h4>Created
 * <h4>Sep 2, 2013, 2:55:29 PM
 *
 * @author Altametrics Inc.
 * @Since Foundation 1.0
 * @see FNKVCWrapper
 */
public class FNMapKVCWrapper extends FNKVCWrapper {

	public static class MapAccessor implements FNIPropertyAccessor {
		private String name; // name of the key in the map that is to be accessed.

		private MapAccessor(String _name) {
			this.name = _name;
		}

		@Override
		@SuppressWarnings("rawtypes")
		public Object get(Object instance) {
			return ((Map) instance).get(this.name);
		}

		@Override
		public String getName() {
			return this.name;
		}

		/**
		 * Returns {@link Object}.class, because we never know the type of
		 * objects stored in a {@link Map}.
		 */

		@Override
		public Class<?> getReadType() {
			return Object.class;
		}

		@Override
		public Class<?> getWriteType() {
			return Object.class;
		}

		@Override
		@SuppressWarnings("unchecked")
		public void set(Object _target, Object _value) {
			((Map<String, Object>) _target).put(this.name, _value);
		}

		@Override
		public String toString() {
			return "MapKVCWrapper.MapAccessor[" + this.name + "]";
		}
	}

	/** Map of MapAccessor, keyed on property name. */

	private static final Map<String, FNIPropertyAccessor> accessorMap = new ConcurrentHashMap<>();

	public FNMapKVCWrapper(Class<?> _class) {
		super(_class);
	}

	/**
	 * <p>
	 * Finds an accessor for the given property name. Returns the accessor if
	 * the class has the named property, or null otherwise.
	 * </p>
	 * <h4>Example</h4>
	 *
	 * @param _target
	 * @param _key
	 *            String for which accessor object have to be made.
	 * @return {@link FNIPropertyAccessor} object of the key of the specified
	 *         class.
	 * @since Foundation 1.0
	 * @see {@link FNKVCWrapper#getAccessor(Object, String)}
	 */
	@Override
	public FNIPropertyAccessor getAccessor(Object _target, String _name) {
		FNIPropertyAccessor result;

		result = super.getAccessor(_target, _name);

		if (result == null) {
			result = accessorMap.get(_name);

			if (result == null) {
				result = new MapAccessor(_name);
				accessorMap.put(_name, result);
			}
		}

		return result;
	}

}
