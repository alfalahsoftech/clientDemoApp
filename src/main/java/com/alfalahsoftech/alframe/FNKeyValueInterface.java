package com.alfalahsoftech.alframe;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.alfalahsoftech.alframe.factory.AFAnnotationFactory;
import com.alfalahsoftech.web.AFObject;

/**
 * <p>
 * The interface provides a common protocol for all objects for implementing key
 * value coding.It provides methods to set and get property values in an orderly
 * manner without using the object getter setter methods of the class
 * explicitly.
 * </p>
 * <h4>Created
 * <h4>Aug 30, 2013, 7:10:19 PM
 * 
 * @author Altametrics Inc.
 * @Since Foundation 1.0
 * @see FNObject.java It will create link to navigate to the class. all related
 *      class.
 */
public interface FNKeyValueInterface {

	/************************************************************************/
	// To be implemented by the subclasses or classes implementing the interface.

	static final String KeyPathSeparator = "."; // defining the keypath separator.
	static final String KeyPathSeparatorRegEx = "\\."; // used for regex pattern expecially for splitting key path

	void takeValueForKey(Object _value, String _key);

	Object valueForKey(String _key);

	void takeValueForKeyPath(Object _value, String _keypath);

	Object valueForKeyPath(String _keypath);

	void handleTakeValueForUnboundKey(Object _value, String _key);

	Object handleQueryWithUnboundKey(String _key);

	void takeValuesFromDictionary(Map<String, Object> _map);

	Map<String, Object> valuesForKeys(String[] _keys);

	/**********************************************************************/

	/**
	 * <p>
	 * Inner Class provides implementation of key value methods for all objects
	 * which do not implement FNKeyValueInterface interface.
	 * </p>
	 * <h4>Created
	 * <h4>Aug 30, 2013, 7:56:33 PM
	 * 
	 * @author Altametrics Inc.
	 * @Since Foundation 1.0
	 * @see FNObject.java It will create link to navigate to the class. all
	 *      related class.
	 */
	/***********************************************************************/
	/* utility class (use those methods to query objects with KVC) */
	public class Utility { // called KVCWrapper in Marcus' code

		/**
		 * <p>
		 * sets the value for the particular key of the object passed as an
		 * argument.
		 * </p>
		 * <h4>Example</h4>
		 * <p>
		 * Class SimpleClass{ <br/>
		 * private String name; <br/>
		 * public String getName(){ <br/>
		 * return name;<br/>
		 * } <br/>
		 * SimpleClass obj= new SimpleClass(); <br/>
		 * FNKeyValueInterface.Utility.takeValueForkey(obj,"ABC", "name"); <br/>
		 * String nameValue = obj.getName();<br/>
		 * // value nameValue would be ABC
		 * </p>
		 * 
		 * @param _o
		 *            object for which value has to be set
		 * @param _value
		 *            object containing the changed value that is to be set.
		 * @param _key
		 *            String containing the field name of the object class
		 * @since Foundation 1.0
		 */
		public static void takeValueForKey(Object _o, Object _value, String _key) {
			if (_o == null) {
				return;
			}
			if (_o instanceof FNKeyValueInterface) {
				((FNKeyValueInterface) _o).takeValueForKey(_value, _key);
			} else {
				DefaultImplementation.takeValueForKey(_o, _value, _key);
			}
		}

		/**
		 * <p>
		 * the method is used for getting the value for the key of an object
		 * instance. The method is basically uses serialization for getting the
		 * value.
		 * </p>
		 * <h4>Example</h4>
		 * <p>
		 * 
		 * <pre>
		 * 	Person p = new Person();<br/>
		 * 		//  For binary serialization. <br/>
		 * 		FNKeyValueInterface.Util.valueForKey(p,"$$") <br/>
		 * 		 		// Serialization with IID <br/>
		 * 	 FNKeyValueInterface.Util.valueForKey(p,"$MYSERIALIID") <br/>
		 * 	// default serialization. <br/>
		 * 	 FNKeyValueInterface.Util.valueForKey(p,"$") <br/>
		 * </pre>
		 * 
		 * </p>
		 * 
		 * @param _o
		 *            Object whose key value we have to get
		 * @param _key
		 *            String for which value have to be retrieved.
		 * @return Object containing the value of the key
		 * @since Foundation 1.0
		 */
		public static Object valueForKey(Object _o, String _key) {
			if (_o == null) {
				return null;
			}
			/*
			*/ else if (_o instanceof FNKeyValueInterface) {
				return ((FNKeyValueInterface) _o).valueForKey(_key);
			} else {
				return DefaultImplementation.valueForKey(_o, _key);
			}
		}

		// called KVCWrapper in Marcus' code
		/**
		 * <p>
		 * sets the value for the particular key of the object's reference of
		 * another class passed as an argument.
		 * </p>
		 * <h4>Example</h4>
		 * <p>
		 * Class SimpleClass{ <br/>
		 * private String name; <br/>
		 * public Address address; public String getName(){ <br/>
		 * return name;<br/>
		 * } <br/>
		 * SimpleClass obj= new SimpleClass(); <br/>
		 * FNKeyValueInterface.Utility.takeValueForKeyPath(obj,"DELHI",
		 * "address.city"); <br/>
		 * String address = obj.address.getCity();<br/>
		 * // value := DELHI
		 * </p>
		 * 
		 * @param _o
		 *            object for which value has to be set
		 * @param _value
		 *            object containing the changed value that is to be set.
		 * @param _keyPath
		 *            String containing the path of the key to be set
		 * @since Foundation 1.0
		 */
		public static void takeValueForKeyPath(Object _o, Object _value, String _keyPath) {
			if (_o == null) {
				return;
			}
			if (_o instanceof FNKeyValueInterface) {
				((FNKeyValueInterface) _o).takeValueForKeyPath(_value, _keyPath);
			} else {
				DefaultImplementation.takeValueForKeyPath(_o, _value, _keyPath);
			}
		}

		/**
		 * <p>
		 * the method is used for getting the value for the key of an object's
		 * reference instance.
		 * </p>
		 * <h4>Example</h4>
		 * <p>
		 * 
		 * <pre>
		 * 	Person p = new Person();<br/>
		 * 	 FNKeyValueInterface.Utility.valueForKeyPath(p,"address.city") <br/>
		 * </pre>
		 * 
		 * </p>
		 * 
		 * @param _o
		 *            Object whose key value we have to get
		 * @param _keyPath
		 *            String for which value have to be retrieved.
		 * @return Object containing the value of the keyPath
		 * @since Foundation 1.0
		 */
		public static Object valueForKeyPath(Object _o, String _keyPath) {
			if (_o == null) {
				return null;
			}
			if (_o instanceof FNKeyValueInterface) {
				return ((FNKeyValueInterface) _o).valueForKeyPath(_keyPath);
			}

			return DefaultImplementation.valueForKeyPath(_o, _keyPath);
		}

		/**
		 * <p>
		 * method sets the values of various keys of the object instance in a
		 * single go.
		 * </p>
		 * <h4>Example</h4>
		 * <p>
		 * HashMap map = new HashMap();<br/>
		 * map.put("firstName", "ABS");<br/>
		 * map.put("age",10);<br/>
		 * Person person= new Person(); <br/>
		 * FNKeyValueInterface.Utility.takeValueFromDictionary(person,map)<br/>
		 * </p>
		 * 
		 * @param _o
		 *            object whose key values have to be set.
		 * @param _map
		 *            Map containing the key value pair of the object that is to
		 *            be set.
		 * @since Foundation 1.0
		 */
		public static void takeValuesFromDictionary(Object _o, Map<String, Object> _map) {
			if (_o == null) {
				return;
			}
			if (_o instanceof FNKeyValueInterface) {
				((FNKeyValueInterface) _o).takeValuesFromDictionary(_map);
			} else {
				DefaultImplementation.takeValuesFromDictionary(_o, _map);
			}
		}

		/**
		 * <p>
		 * method retrieves values for a key set of an object instance .
		 * </p>
		 * <h4>Example</h4>
		 * <p>
		 * String[] keyList = {"firstName", "age"};<br/>
		 * Person person= new Person(); <br/>
		 * HashMap<String,object>
		 * keyValueMap=FNKeyValueInterface.Utility.valuesForKeys(person,keyList)
		 * <br/>
		 * </p>
		 * 
		 * @param _o
		 *            object whose key values have to be retrieved.
		 * @param _keys
		 *            String array containing the keys of the object
		 * @param Map
		 *            <String,Object> containing the key value set for the
		 *            described keys.
		 * @since Foundation 1.0
		 */

		public static Map<String, Object> valuesForKeys(Object _o, String[] _keys) {
			if (_o == null) {
				return null;
			}
			if (_o instanceof FNKeyValueInterface) {
				return ((FNKeyValueInterface) _o).valuesForKeys(_keys);
			}

			return FNKeyValueInterface.DefaultImplementation.valuesForKeys(_o, _keys);
		}

		/**
		 * <p>
		 * The method is used to split the key path on the basis of dot
		 * separator and returns a string array of key paths.
		 * </p>
		 * <h4>Example</h4>
		 * <p>
		 * String[] keyPathArray=
		 * FNKeyValueInterface.Utility.splitKeyPath("address.city");<br/>
		 * </p>
		 * 
		 * @param _path
		 *            String containing the keypath separated by dot.
		 * @return String[] containing the keys of the key path.
		 * @since Foundation 1.0
		 */
		public static String[] splitKeyPath(String _path) {
			if (_path == null) {
				return null;
			}
			return _path.split(KeyPathSeparatorRegEx);
		}

	}

	/*******************************************************************************/

	/**
	 * <p>
	 * Inner Class provides a common platform for key value coding
	 * implementation.Provides default implementation of the key value interface
	 * methods.
	 * <h4>Created
	 * <h4>Sep 1, 2013, 4:09:15 AM
	 * 
	 * @author Altametrics Inc.
	 * @Since Foundation 1.0
	 */
	public class DefaultImplementation {

		/**
		 * <p>
		 * sets the value for the particular key of the object passed as an
		 * argument.
		 * </p>
		 * <h4>Example</h4>
		 * <p>
		 * FNKeyValueInterface.DefaultImplementation.takeValueForkey(new
		 * Person(),"ABC", "name"); <br/>
		 * String nameValue = obj.getName();<br/>
		 * // value nameValue would be ABC
		 * </p>
		 * 
		 * @param _o
		 *            object for which value has to be set
		 * @param _value
		 *            object containing the changed value that is to be set.
		 * @param _key
		 *            String containing the field name of the object class
		 * @since Foundation 1.0
		 */

		public static void takeValueForKey(Object _o, Object _value, String _key) {
			// IMPORTANT: keep consistent with NSObject.takeValueForKey()!!
			if (_o == null) {
				return;
			}
			try { // : avoid this exception handler (COSTLY!)
				FNIPropertyAccessor accessor = FNKVCWrapper.forClass(_o.getClass()).getAccessor(_o, _key);
				if (accessor == null) {
					if (_o instanceof FNKeyValueInterface) {
						((FNKeyValueInterface) _o).handleTakeValueForUnboundKey(_value, _key);
						return;
					}

					throw new FNMissingPropertyException(_o, _key);
				}

				/* found accessor */

				Class<?> type = accessor.getWriteType();
				//changes in the code for using the method for casting the object incase the type of value and write type of field
				if (_value != null) {
					_value = AFAnnotationFactory.factoryObj().castObject(_value, type);//FNApplicationObject.factory().objectUtil().castObject(_value, type);
				}

				//code changes done till here
				accessor.set(_o, _value);
			} catch (FNMissingPropertyException e) {
				if (_o instanceof FNKeyValueInterface) {
					((FNKeyValueInterface) _o).handleTakeValueForUnboundKey(_value, _key);
					return;
				}

				throw e; // : better just return?
			} catch (FNMissingAccessorException e) {
				/*
				 * this is when a setX method is missing (but a get is available)
				 */
				if (_o instanceof FNKeyValueInterface) {
					((FNKeyValueInterface) _o).handleTakeValueForUnboundKey(_value, _key);
					return;
				}

				throw e; // : better just return?
			}
		}

		/**
		 * <p>
		 * the method is used for getting the value for the key of an object
		 * instance.
		 * </p>
		 * <h4>Example</h4>
		 * <p>
		 * 
		 * <pre>
		 * Object obj = FNKeyValueInterface.DefaultImplementation.valueForKey(new Person(), &quot;age&quot;);
		 * </pre>
		 * 
		 * </p>
		 * 
		 * @param _o
		 *            Object whose key value we have to get
		 * @param _key
		 *            String for which value have to be retrieved.
		 * @return Object containing the value of the key
		 * @since Foundation 1.0
		 */
		public static Object valueForKey(Object _o, String _key) {
			// IMPORTANT: keep consistent with NSObject.valueForKey()!!
			if (_o == null) {
				return null;
			}
			try { // : avoid this exception handler (COSTLY!)

				FNIPropertyAccessor accessor = FNKVCWrapper.forClass(_o.getClass()).getAccessor(_o, _key);
				if (accessor == null) {
					if (_o instanceof FNKeyValueInterface) {
						return ((FNKeyValueInterface) _o).handleQueryWithUnboundKey(_key);
					}
					throw new FNMissingPropertyException(_o, _key);
				}
				return accessor.get(_o);

			} catch (FNMissingPropertyException e) {
				if (_o instanceof FNKeyValueInterface) {
					return ((FNKeyValueInterface) _o).handleQueryWithUnboundKey(_key);
				}
				throw e; // : better return null?
			} catch (FNMissingAccessorException e) {
				if (_o instanceof FNKeyValueInterface) {
					return ((FNKeyValueInterface) _o).handleQueryWithUnboundKey(_key);
				}

				throw e; // : better return null?
			}
		}

		/**
		 * <p>
		 * sets the value for the particular key of the object's reference of
		 * another class passed as an argument.
		 * </p>
		 * <h4>Example</h4>
		 * <p>
		 * Class SimpleClass{ <br/>
		 * private String name; <br/>
		 * public Address address; public String getName(){ <br/>
		 * return name;<br/>
		 * } <br/>
		 * SimpleClass obj= new SimpleClass(); <br/>
		 * FNKeyValueInterface.DefaultImplementation.takeValueForKeyPath(obj,
		 * "DELHI", "address.city"); <br/>
		 * String address = obj.address.getCity();<br/>
		 * // value := DELHI
		 * </p>
		 * 
		 * @param _o
		 *            object for which value has to be set
		 * @param _value
		 *            object containing the changed value that is to be set.
		 * @param _keyPath
		 *            String containing the path of the key to be set
		 * @since Foundation 1.0
		 */
		public static void takeValueForKeyPath(Object _o, Object _value, String _keyPath) {
			if (_o == null) {
				return;
			}
			String[] path = _keyPath.split(KeyPathSeparatorRegEx);
			int len = path.length;
			Object current = _o;

			if (len > 1) {
				for(int i = 0; i < (len - 1) && current != null; i++) {
					if (current instanceof FNKeyValueInterface) {
						current = ((FNKeyValueInterface) current).valueForKey(path[i]);
					} else {
						current = FNKeyValueInterface.Utility.valueForKey(current, path[i]);
					}
				}
			}
			if (current == null) {
				return;
			}
			if (current instanceof FNKeyValueInterface) {
				((FNKeyValueInterface) current).takeValueForKey(_value, path[len - 1]);
			} else {
				FNKeyValueInterface.Utility.takeValueForKey(current, _value, path[len - 1]);
			}
		}

		/**
		 * <p>
		 * the method is used for getting the value for the key of an object's
		 * reference instance.
		 * </p>
		 * <h4>Example</h4>
		 * <p>
		 * 
		 * <pre>
		 * 	Person p = new Person();<br/>
		 * 	 FNKeyValueInterface.DefaultImplementation.valueForKeyPath(p,"address.city") <br/>
		 * </pre>
		 * 
		 * </p>
		 * 
		 * @param _o
		 *            Object whose key value we have to get
		 * @param _keyPath
		 *            String for which value have to be retrieved.
		 * @return Object containing the value of the keyPath
		 * @since Foundation 1.0
		 */
		public static Object valueForKeyPath(Object _o, String _keyPath) {
			if (_o == null) {
				return null;
			}
			if (_keyPath == null || _keyPath.trim().equals("")) {
				throw new RuntimeException("Key can not be null in valueForKeyPath for class " + _o.getClass().getName());
			}
			String[] path = _keyPath.split(KeyPathSeparatorRegEx);
			int len = path.length;
			Object current = _o;

			for(int i = 0; i < len && current != null; i++) {
				if (current instanceof FNKeyValueInterface) {
					current = ((FNKeyValueInterface) current).valueForKey(path[i]);
				} else {
					current = FNKeyValueInterface.Utility.valueForKey(current, path[i]);
				}
			}
			return current;
		}

		/**
		 * <p>
		 * method sets the values of various keys of the object instance in a
		 * single go.
		 * </p>
		 * <h4>Example</h4>
		 * <p>
		 * HashMap map = new HashMap();<br/>
		 * map.put("firstName", "ABS");<br/>
		 * map.put("age",10);<br/>
		 * Person person= new Person(); <br/>
		 * FNKeyValueInterface.DefaultImplementation.takeValueFromDictionary(
		 * person,map)<br/>
		 * </p>
		 * 
		 * @param _o
		 *            object whose key values have to be set.
		 * @param _map
		 *            Map containing the key value pair of the object that is to
		 *            be set.
		 * @since Foundation 1.0
		 */
		public static void takeValuesFromDictionary(Object _o, Map<String, Object> _map) {
			if (_o == null || _map == null) {
				return;
			}

			if (_o instanceof FNKeyValueInterface) {
				FNKeyValueInterface o = (FNKeyValueInterface) _o;

				for(String key : _map.keySet()) {
					o.takeValueForKey(_map.get(key), key);
				}
			} else {
				for(String key : _map.keySet()) {
					FNKeyValueInterface.Utility.takeValueForKey(_o, _map.get(key), key);
				}
			}
		}

		/**
		 * <p>
		 * method retrieves values for a key set of an object instance .
		 * </p>
		 * <h4>Example</h4>
		 * <p>
		 * String[] keyList = {"firstName", "age"};<br/>
		 * Person person= new Person(); <br/>
		 * HashMap<String,object>
		 * keyValueMap=FNKeyValueInterface.Utility.valuesForKeys(person,keyList)
		 * <br/>
		 * </p>
		 * 
		 * @param _o
		 *            object whose key values have to be retrieved.
		 * @param _keys
		 *            String array containing the keys of the object
		 * @return Map<String,object> containing the key value set for the
		 *         described keys
		 * @since Foundation 1.0
		 */
		public static Map<String, Object> valuesForKeys(Object _o, String[] _keys) {
			if (_keys == null || _o == null) {
				return null;
			}

			Map<String, Object> vals = new HashMap<>(_keys.length);
			if (_keys.length == 0) {
				return vals;
			}

			if (_o instanceof FNKeyValueInterface) {
				FNKeyValueInterface o = (FNKeyValueInterface) _o;

				for(int i = 0; i < _keys.length; i++) {
					Object v = o.valueForKey(_keys[i]);
					if (v != null) {
						vals.put(_keys[i], v);
					}
				}
			} else {
				for(int i = 0; i < _keys.length; i++) {
					Object v = FNKeyValueInterface.Utility.valueForKey(_o, _keys[i]);
					if (v != null) {
						vals.put(_keys[i], v);
					}
				}
			}
			return vals;
		}

		public static void handleTakeValueForUnboundKey(Object _o, Object _value, String _key) {
			// : raise exception? which one?
		}

		public static Object handleQueryWithUnboundKey(Object _o, String _key) {
			return null;
		}
	}

	/***********************************************************************************/

	/**
	 * <p>
	 * Inner static class providing key value method implementation for Map
	 * Collection
	 * </p>
	 * <h4>Created
	 * <h4>Sep 1, 2013, 4:36:24 AM
	 * 
	 * @author Altametrics Inc.
	 * @Since Foundation 1.0
	 */
	public static class MapImplementation {

		/**
		 * <p>
		 * method retrieves the value for a key from the map defined.
		 * </p>
		 * <h4>Example</h4>
		 * <p>
		 * HashMap<String,Object> map= new HashMap();<br/>
		 * map.put("name", "ABC");<br/>
		 * map.put("person",new Person());<br/>
		 * Object _obj=
		 * FNKeyValueInterface.MapImplementation.valueForKey(map,"name");<br/>
		 * </p>
		 * 
		 * @param map
		 *            Map collection containing key value pair
		 * 
		 * @param key
		 *            String key for which value has to be retrieved from the
		 *            map.
		 * @return Object containing value for the String
		 * @since Foundation 1.0
		 */
		public static Object valueForKey(Map<?, ?> map, String key) {
			return map.get(key);
		}

		/**
		 * <p>
		 * method set the value for a key from the map defined.
		 * </p>
		 * <h4>Example</h4>
		 * <p>
		 * HashMap<String,Object> map= new HashMap();<br/>
		 * map.put("name", "ABC");<br/>
		 * map.put("person",new Person());<br/>
		 * FNKeyValueInterface.MapImplementation.takeValueForKey(map,"newName",
		 * "name");<br/>
		 * </p>
		 * 
		 * @param map
		 *            Map collection containing key value pair
		 * @param value
		 *            object containing the new value for the key
		 * @param key
		 *            String key for which value has to be set in the map.
		 * @since Foundation 1.0
		 */
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public static void takeValueForKey(Map map, Object value, String key) {
			map.put(key, value);
		}

		/**
		 * <p>
		 * method retrieves values for a key set of a map .
		 * </p>
		 * <h4>Example</h4>
		 * <p>
		 * String[] keyList = {"firstName", "age"};<br/>
		 * HashMap<String,Object> map= new HashMap();<br/>
		 * map.put("name", "ABC");<br/>
		 * map.put("person",new Person());<br/>
		 * HashMap<String,object>
		 * keyValueMap=FNKeyValueInterface.MapImplementation
		 * .valuesForKeys(map,keyList)<br/>
		 * </p>
		 * 
		 * @param map
		 *            map from which key set values have to retrieved.
		 * @param _keys
		 *            String array containing the keys of the object
		 * @return Map<String,object> containing the key value set for the
		 *         described keys
		 * @since Foundation 1.0
		 */
		/*	public static Map<String, Object> valuesForKeys(Map<?, ?> map, String[] _keys) {
				Map<String, Object> returnMap = new FNHashMap<>();
				for(int i = 0; i < _keys.length; i++) {
					returnMap.put(_keys[i], valueForKey(map, _keys[i]));
				}
				return returnMap;
			}*/

		/**
		 * <p>
		 * method sets the values of various keys of the map in another map.
		 * </p>
		 * <h4>Example</h4>
		 * <p>
		 * HashMap map = new HashMap();<br/>
		 * map.put("firstName", "ABS");<br/>
		 * map.put("age",10);<br/>
		 * FNKeyValueInterface.MapImplementation.takeValueFromDictionary(map,new
		 * HashMap())<br/>
		 * </p>
		 * 
		 * @param origMap
		 *            Map from which key value have to be copied .
		 * @param _map
		 *            Map to which the original map will be copied to
		 * @since Foundation 1.0
		 */
		public static void takeValuesFromDictionary(Map<?, ?> origMap, Map<String, Object> _map) {
			for(Map.Entry<String, Object> entry : _map.entrySet()) {
				takeValueForKey(origMap, entry.getValue(), entry.getKey());
			}
		}

	}

	/***********************************************************************************/
	/* expose an array as a KVC object, keys are indices into the array */
	// TBD: not exactly pleased with this, might move to a different object,
	// package, whatever. We could even make it a standard wrapper, or
	// hardcode the KVC behaviour for arrays (sounds useful).

	public static class ArrayIndexFascade extends AFObject { // TODO : R we going to use it. Havent checked the functionality as yet
		private static final long serialVersionUID = 1L;

		private static final NumberFormat numFmt = NumberFormat.getInstance(Locale.US);

		protected Object[] array;

		public ArrayIndexFascade(final Object[] _array) {
			this.array = _array;
		}

		public ArrayIndexFascade() {
		}

		/* accessors */

		public void setArray(final Object[] _array) {
			this.array = _array;
		}

		public Object[] array() {
			return this.array;
		}

		/* KVC */

		@Override
		public void takeValueForKey(final Object _value, final String _key) {
			if (this.array == null) {
				return;
			}

			if (_key == null || _key.length() == 0) {
				this.handleTakeValueForUnboundKey(_value, _key);
				return;
			}

			int idx;
			try {
				idx = (numFmt.parse(_key)).intValue();
			} catch (ParseException e) {
				this.handleTakeValueForUnboundKey(_value, _key);
				return;
			}

			if (idx < 0) {
				this.handleTakeValueForUnboundKey(_value, _key);
				return;
			}

			if (idx >= this.array.length) { // do we want to support growing?
				this.handleTakeValueForUnboundKey(_value, _key);
				return;
			}

			this.array[idx] = _value;
		}

		@Override
		public Object valueForKey(final String _key) {
			// TBD: support ranges, like [1:10] to extract a subarray
			if (this.array == null) {
				return null;
			}

			if (_key == null || _key.length() == 0) {
				return this.handleQueryWithUnboundKey(_key);
			}

			char c0 = _key.charAt(0);

			switch (c0) {
				case 'c':
					if (_key.equals("count")) {
						return this.array.length;
					}
					break;
				case 'l':
					if (_key.equals("length")) {
						return this.array.length;
					}
					break;
			}

			int idx;
			try {
				idx = (numFmt.parse(_key)).intValue();
			} catch (ParseException e) {
				return this.handleQueryWithUnboundKey(_key);
			}

			if (idx < 0 || idx > this.array.length) {
				return this.handleQueryWithUnboundKey(_key);
			}

			return this.array[idx];
		}

		@Override
		public void appendAttributesToDescription(final StringBuilder _d) {
			super.appendAttributesToDescription(_d);

			if (this.array == null) {
				_d.append(" no-array");
			} else {
				_d.append(" #items=");
				_d.append(this.array.length);
			}
		}
	}
}

/* Local Variables: c-basic-offset: 2 tab-width: 8 End: */
