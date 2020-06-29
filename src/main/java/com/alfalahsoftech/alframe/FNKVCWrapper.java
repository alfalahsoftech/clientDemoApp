package com.alfalahsoftech.alframe;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Streamlines access to all the properties of a given JavaBean. Static methods
 * acts as a factory for PropertyHelper instances, which are specific to a
 * particular Bean class.
 * 
 * <p>
 * A <code>PropertyHelper</code> for a bean class simplifies getting and setting
 * properties on the bean, handling (and caching) the lookup of methods as well
 * as the dynamic invocation of those methods. It uses an instance of
 * {@link FNIPropertyAccessor} for each property.
 * 
 * <p>
 * PropertyHelper allows properties to be specified in terms of a path. A path
 * is a series of property names seperate by periods. So a property path of
 * 'visit.user.address.street' is effectively the same as the code
 * <code>getVisit().getUser().getAddress().getStreet()</code> (and just as
 * likely to throw a <code>NullPointerException</code>).
 * 
 * <p>
 * Typical usage:
 * 
 * <pre>
 * ProperyHelper helper = PropertyHelper.forInstance(instance);
 * helper.set(instance, &quot;propertyName&quot;, newValue);
 * </pre>
 * 
 * <p>
 * Only single-valued properties (not indexed properties) are supported, and a
 * minimum of type checking is performed.
 * 
 * <p>
 * A mechanism exists to register custom <code>PropertyHelper</code> subclasses
 * for specific classes. The two default registrations are
 * {@link PublicBeanPropertyHelper} for the {@link IPublicBean} interface, and
 * {@link MapHelper} for the {@link Map} interface.
 * 
 * <h4>Created
 * <h4>Sep 2, 2013, 2:55:29 PM
 * 
 * @author Altametrics Inc.
 * @Since Foundation 1.0
 **/

public class FNKVCWrapper extends Object {

	public static boolean isDebugEnabled = true; // This flag is used to
	public static boolean isDefaultAccessible = true; // This flag is used to
	// give access to fields
	// of the classes with
	// default access
	// specifier.

	/** Cache of helpers, keyed on the Class of the bean. **/
	private static ConcurrentHashMap<Class<?>, FNKVCWrapper> helpers = new ConcurrentHashMap<>(16);

	// static {
	// register(Map.class, MapKVCWrapper.class);
	// }

	private static final Log logger = LogFactory.getLog(FNKVCWrapper.class);

	private static ConcurrentHashMap<Class<?>, Method[]> declaredMethodCache = new ConcurrentHashMap<>(16);

	/**
	 * Map of PropertyAccessors for the helper's bean class. The keys are the
	 * names of the properties.
	 **/

	protected ConcurrentHashMap<String, FNIPropertyAccessor> accessors;

	/** The Java Beans class for which this helper is configured. **/
	protected Class<?> clazz;

	/**
	 * The separator character used to divide up different properties in a
	 * nested property name.
	 **/

	/**
	 * <p>
	 * Constructor constructs the object of this class by initializing the class
	 * object for which you want all properties.
	 * </p>
	 * 
	 * @param _class
	 *            Class Object for which properties have to be accessed.
	 * @since Foundation 1.0
	 */
	protected FNKVCWrapper(Class<?> _class) {
		this.clazz = _class;
	}

	/**
	 * <p>
	 * retrieves all public methods which are declared in the class specified.
	 * </p>
	 * <h4>Example</h4>
	 * <p>
	 * Method[] methods = FNKVCWrapper.getPublicDeclaredMethods(Person.class);
	 * </p>
	 * 
	 * @param _class
	 *            Class object whose public methods would be sought out.
	 * @return Method[] array of public method objects declared in the class
	 * @since Foundation 1.0
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static Method[] getPublicDeclaredMethods(Class<?> _class) {
		Method methods[] = declaredMethodCache.get(_class);
		if (methods != null) {
			return methods;
		}

		final Class<?> fclz = _class;

		methods = (Method[]) AccessController.doPrivileged(new PrivilegedAction() {
			@Override
			public Object run() {
				return fclz.getMethods();
			}
		});

		for(int i = 0; i < methods.length; i++) {
			Method method = methods[i];
			int j = method.getModifiers();
			if (!Modifier.isPublic(j)) {
				methods[i] = null;
			}
		}

		declaredMethodCache.put(_class, methods);
		return methods;
	}

	/**
	 * <p>
	 * retrieves property descriptor array for all the property of the class
	 * specified.
	 * </p>
	 * <h4>Example</h4>
	 * <p>
	 * FNKVCWrapper wrapper = FNKVCWrapper.forClass(Person.class);<br/>
	 * PropertyDescriptor[] propertDescriptors =
	 * wrapper.getPropertyDescriptors(Person.class);<br/>
	 * </p>
	 * 
	 * @param _class
	 *            Class object for which property descriptor have to be sought
	 *            out
	 * @return PropertyDescriptor array for the specified Class object.
	 * @exception Exception
	 * @since Foundation 1.0
	 * @see PropertyDescriptor
	 */
	public PropertyDescriptor[] getPropertyDescriptors(Class<?> _class) throws Exception {
		/**
		 * Our idea of KVC differs from what the Bean API proposes. Instead of
		 * having get<name> and set<name> methods, we expect <name> and
		 * set<name> methods.
		 * 
		 * HH: changed to allow for getXYZ style accessors.
		 */

		Map<String, Method> settersMap = new HashMap<>();
		Map<String, Method> gettersMap = new HashMap<>();

		Method methods[] = getPublicDeclaredMethods(_class);

		for(int i = 0; i < methods.length; i++) {
			Method method = methods[i];
			if (method == null) {
				continue;
			}

			String name = method.getName();
			int nameLen = name.length();
			int paraCount = method.getParameterTypes().length;

			if (name.startsWith("set")) {
				if (method.getReturnType() != Void.TYPE) {
					continue;
				}
				if (paraCount != 1) {
					continue;
				}
				if (nameLen == 3) {
					continue;
				}

				char[] chars = name.substring(3).toCharArray();
				chars[0] = Character.toLowerCase(chars[0]);
				String decapsedName = new String(chars);

				if (isDebugEnabled) {
					logger.debug("Recording setter method [" + method + "] for name \"" + decapsedName + "\"");
				}
				settersMap.put(decapsedName, method);
			} else {
				/* register as a getter */
				if (method.getReturnType() == Void.TYPE) {
					continue;
				}
				if (paraCount > 0) {
					continue;
				}

				/*if (name.startsWith("get")) {
					char[] chars = name.substring(3).toCharArray();
					chars[0] = Character.toLowerCase(chars[0]);
					name = new String(chars);
				}

				if (isDebugEnabled) {
					logger.debug("Recording getter method [" + method + "] for name \"" + name + "\"");
				}
				if (Modifier.isAbstract(method.getModifiers()) || (gettersMap.get(name) != null && !this.isOverridenMethod(method, gettersMap.get(name)))) {
					continue;
				}
				gettersMap.put(name, method);*/

				//After Change 28 May 2019: Samdani: getterMap.pu and above code inside startWith.("get") block

				if (name.startsWith("get")) {
					char[] chars = name.substring(3).toCharArray();
					chars[0] = Character.toLowerCase(chars[0]);
					name = new String(chars);


					if (isDebugEnabled) {
						logger.debug("Recording getter method [" + method + "] for name \"" + name + "\"");
					}
					if (Modifier.isAbstract(method.getModifiers()) || (gettersMap.get(name) != null && !this.isOverridenMethod(method, gettersMap.get(name)))) {
						continue;
					}
					gettersMap.put(name, method);
				}



			}

		}
		System.out.println("gettersMap======= "+gettersMap);
		System.out.println("settersMap======= "+settersMap);

		Set<PropertyDescriptor> pds = new HashSet<>();

		/* merge all names from getters and setters */
		Set<String> names = new HashSet<>(gettersMap.keySet());
		names.addAll(settersMap.keySet());

		for(String name : names) {
			Method getter = gettersMap.get(name);
			Method setter = settersMap.get(name);
			if (getter == null && setter == null) {
				continue;
			}

			/* this is JavaBeans stuff */
			PropertyDescriptor descriptor = new PropertyDescriptor(name, getter, setter);
			pds.add(descriptor);
		}
		return pds.toArray(new PropertyDescriptor[0]);
	}

	private boolean isOverridenMethod(Method newMethod, Method oldMethod) {
		Class<?> class1 = newMethod.getDeclaringClass();//glkState
		Class<?> class2 = oldMethod.getDeclaringClass();//EOOBject
		if (class2.isAssignableFrom(class1)) {
			return true;
		}
		return false;
	}

	/**
	 * <p>
	 * Uses JavaBeans introspection to find all the properties of the bean
	 * class. This method sets the {@link #accessors} variable (it will have
	 * been null), and adds all the well-defined JavaBeans properties.
	 * </p>
	 * 
	 * @since Foundation 1.0
	 */
	protected void buildPropertyAccessors() {
		/* Acquire all usable field accessors first. */

		if (this.accessors != null) {
			return;
		}

		/**
		 * Construct field accessors for names which aren't occupied by
		 * properties, yet. Imagine this as a "last resort".
		 */

		Map<String, FNFieldAccessor> propertyFieldAccessorMap = new HashMap<String, FNFieldAccessor>();
		Field fields[] = this.clazz.getFields();
		System.out.println("this.clazz"+this.clazz);
		for(Field field : fields) {
			int mods = field.getModifiers();

			// Skip static variables and non-public instance variables.
			if ((Modifier.isPublic(mods) == false) || (Modifier.isStatic(mods))) {
				continue;
			}
			// If the class is with default access specifier and
			// isDefaultAccessor flag is set to true than the fields of that
			// class can be accessed.
			if (this.clazz.getModifiers() == 0) { // checks whether the access
				// specifier of the class is
				// default
				propertyFieldAccessorMap.put(field.getName(), new FNFieldAccessor(field, isDefaultAccessible));
			} else {
				propertyFieldAccessorMap.put(field.getName(), new FNFieldAccessor(field));
			}
		}
		/** Retrieve all property descriptors now */
		PropertyDescriptor[] props;

		try {
			props = this.getPropertyDescriptors(this.clazz);
			System.out.println("11111111111 "+props);
		} catch (Exception e) {
			logger.error("Error during getPropertyDescriptors()", e);
			throw new FNDynamicInvocationException(e);
		}

		this.accessors = new ConcurrentHashMap<>(16);

		if (isDebugEnabled) {
			logger.debug("Recording properties for \"" + this.clazz.getName() + "\"");
		}

		for(PropertyDescriptor pd : props) {
			String name = pd.getName();

			if (isDebugEnabled) {
				logger.debug("Recording property \"" + name + "\"");
			}

			Method getter = pd.getReadMethod();
			Method setter = pd.getWriteMethod();
			FNFieldAccessor fa = propertyFieldAccessorMap.get(name);
			Class<?> type = pd.getPropertyType();

			FNPropertyAccessor pa = FNPropertyAccessor.getPropertyAccessor(name, type, getter, setter, fa);
			this.accessors.put(name, pa);
		}

		/**
		 * Use field accessors for names which are not occupied, yet. This is
		 * the default fallback.
		 */
		for(String name : propertyFieldAccessorMap.keySet()) {
			this.accessors.putIfAbsent(name, propertyFieldAccessorMap.get(name));
		}
	}

	/**
	 * <p>
	 * Factory method which returns a <code>KVCWrapper</code> for the given
	 * JavaBean class.<br/>
	 * Finding the right helper class involves a sequential lookup, first for an
	 * exact match, then for an exact match on the superclass, the a search by
	 * interface. If no specific match is found, then <code>KVCWrapper</code>
	 * itself is used, which is the most typical case.
	 * </p>
	 * <h4>Example</h4>
	 * <p>
	 * FNKVCWrapper wrapper = FNKVCWrapper.forClass(Person.class);
	 * </p>
	 * 
	 * @param _class
	 *            Class for which FNKVCWrapper object works upon.
	 * @return FNKVCWrapper object for the class
	 * @since Foundation 1.0
	 */
	public static FNKVCWrapper forClass(Class<?> _class) {
		// TBD: replace this method

		/*
		 * if (logger.isDebugEnabled()) { // TBD: expensive logger.debug("Getting property helper for class " + _class.getName()); }
		 */
		System.out.println(_class+"    helpers11111111111111=== "+helpers);
		FNKVCWrapper helper = helpers.get(_class);
		if (helper != null) {
			return helper;
		}

		// hh: Previously there was a synchronized registry of helpers. I
		// removed
		// that because it only contained MapKVCWrapper in Go ..., hence it
		// was unnecessarily expensive.
		// We might want to replicate this at the Go level.

		if (Map.class.isAssignableFrom(_class)) {
			helper = new FNMapKVCWrapper(_class);
		} else {
			helper = new FNKVCWrapper(_class);
		}

		// We don't want to go through this again, so record permanently the
		// correct
		// helper for this class.
		helpers.put(_class, helper);
		System.out.println(_class+"    helpers2nd times=== "+helpers);
		return helper;
	}

	/**
	 * <p>
	 * Finds an accessor for the given property name. Returns the accessor if
	 * the class has the named property, or null otherwise.
	 * </p>
	 * <h4>Example</h4>
	 * <p>
	 * FNKVCWrapper wrapper = FNKVCWrapper.forClass(Person.class);<br/>
	 * FNIPropertyAccessor accessor = wrapper.getAccessor(new
	 * Person(),"firstName");<br/>
	 * </p>
	 * 
	 * @param _self
	 * @param _key
	 *            String for which accessor object have to be made.
	 * @return {@link FNIPropertyAccessor} object of the key of the specified
	 *         class.
	 * @since Foundation 1.0
	 */
	public FNIPropertyAccessor getAccessor(final Object _self, final String _key) {
		// TODO: self id not used anyWhere in code
		synchronized (this) {
			if (this.accessors == null) {
				this.buildPropertyAccessors();
			}
		}

		// hh: before this was iterating an array over names, hardcoded that for
		// speed (no array creation, no String ops for exact matches)
		// this.accessors is a concurrent hashmap, hence no synchronized
		// necessary
		FNIPropertyAccessor accessor;

		/* first check exact match, eg 'item' */

		if ((accessor = this.accessors.get(_key)) != null) {
			return accessor;
		}

		/* next check 'getItem' */

		final int len = _key.length();
		final char[] chars = new char[3 /* get */ + len];
		chars[0] = 'g';
		chars[1] = 'e';
		chars[2] = 't';

		_key.getChars(0, len, chars, 3 /* skip 'get' */);
		final char c0 = chars[3];
		if (c0 > 96 && c0 < 123 /* lowercase ASCII range */) {
			chars[3] = (char) (c0 - 32); /* make uppercase */
		}

		String s = new String(chars);
		if ((accessor = this.accessors.get(s)) != null) {
			return accessor;
		}

		/* finally with leading underscore */

		chars[3] = c0; /* restore lowercase char */
		chars[2] = '_';
		s = new String(chars, 2, len + 1);
		return this.accessors.get(s);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("<KVCWrapper @");
		sb.append(this.hashCode());
		sb.append(": ");
		sb.append(this.clazz.getName());
		sb.append('>');
		return sb.toString();
	}
}
