package com.alfalahsoftech.alframe;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * <p>
 * Streamlines dynamic access to one of a single class's properties as defined
 * by key/value coding (KVC). Access to properties may happen via a pair of
 * getter/setter methods or in the absence of one of those two an optional
 * FieldAccessor (if there is any provided).
 * </p>
 * <h4>Created
 * <h4>Sep 2, 2013, 12:33:04 PM
 * 
 * @author Altametrics Inc.
 * @Since Foundation 1.0
 */
public class FNPropertyAccessor implements FNIPropertyAccessor {
	private static final Log logger = LogFactory.getLog(FNPropertyAccessor.class);

	public static boolean accesJavaSetterForBoolVar = true;

	protected String name; // name of the property to be accessed.
	protected Class<?> type; // data type of the property
	protected Method getter; // java.lang.reflect.Method :- representing getFieldName();
	protected Method setter; // java.lang.reflect.Method :- representing setFieldName(Datatype variable)

	/**
	 * 
	 * <p>
	 * Constructor constructs the instance of this class by initializing the
	 * name of the property and its data type.
	 * </p>
	 * <h4>Example</h4>
	 * <p>
	 * FNPropertyAccessor acessor= new FNPropertyAccessor("age",Integer.class);
	 * // default access so cannot be used outside<br/>
	 * </p>
	 * 
	 * @param _name
	 *            String specifying the name of the property.
	 * @param _type
	 *            Class specifying the data type of the property
	 * @since Foundation 1.0
	 * 
	 */
	FNPropertyAccessor(final String _name, final Class<?> _type) {
		this.name = _name;
		this.type = _type;
	}

	/**
	 * 
	 * <p>
	 * Constructor constructs the instance of this class by initializing the
	 * name of the property and its data type.
	 * </p>
	 * <h4>Example</h4>
	 * <p>
	 * 
	 * Method getter=Person.getClass().getDeclaredMethod("getAge",null); Method
	 * setter=Person.getClass().getDeclaredMethod("setAge",Integer.class);
	 * FNPropertyAccessor acessor= new
	 * FNPropertyAccessor("age",Integer.class,getter,setter); // default access
	 * so cannot be used outside<br/>
	 * </p>
	 * 
	 * @param _name
	 *            String specifying the name of the property.
	 * @param _type
	 *            Class specifying the data type of the property
	 * @param _getter
	 *            Method object containing the reference of the getter method of
	 *            the property.
	 * @param _setter
	 *            Method object containing the reference of the setter method of
	 *            the property.
	 * @since Foundation 1.0
	 * 
	 */
	FNPropertyAccessor(String _name, Class<?> _type, Method _getter, Method _setter) {
		this(_name, _type);
		this.getter = _getter;
		this.setter = _setter;
	}

	/**
	 * <p>
	 * Abstract Inner class which provides a common platform for accessing and
	 * configuring class properties.
	 * </p>
	 * <h4>Created
	 * <h4>Sep 2, 2013, 12:34:18 PM
	 * 
	 * @author Altametrics Inc.
	 * @Since Foundation 1.0
	 * @see {@link FNPropertyAccessor}
	 */

	private static abstract class MixedAccessor extends FNPropertyAccessor {
		protected FNFieldAccessor fa; // accessor of the field to be accessed.

		MixedAccessor(final String _name, final Class<?> _type, FNFieldAccessor _fa) {
			super(_name, _type);
			this.fa = _fa;
		}
	}

	/**
	 * 
	 * <p>
	 * static inner class which is subclass of {@linkplain MixedAccessor}
	 * provides implementation for changing the value of the class property.
	 * </p>
	 * <h4>Created
	 * <h4>Sep 2, 2013, 12:36:49 PM
	 * 
	 * @author Altametrics Inc.
	 * @Since Foundation 1.0
	 */
	private static class PropertyGetterAndFieldSetterAccessor extends MixedAccessor {
		/**
		 * 
		 * <p>
		 * Constructor for defining the object of the class by initializing name
		 * of the class property , its getter method and object of
		 * {@link FNFieldAccessor} for respective class property.
		 * </p>
		 * 
		 * @param _name
		 *            String depicting class property name.
		 * @param _type
		 *            Class depicting the data type of the class property.
		 * @param _getter
		 *            Method object for the class property for getting the
		 *            value.
		 * @param _fa
		 *            {@link FNFieldAccessor} object for the class property.
		 * @since Foundation 1.0
		 * 
		 */
		PropertyGetterAndFieldSetterAccessor(String _name, Class<?> _type, Method _getter, FNFieldAccessor _fa) {
			super(_name, _type, _fa);
			this.getter = _getter;
		}

		/**
		 * 
		 * <p>
		 * is used to get the Class of the data type in which it is be written
		 * </p>
		 * 
		 * @return {@link Class} object of the data type
		 * @since Foundation 1.0
		 * 
		 */
		@Override
		public Class<?> getWriteType() {
			return this.fa.getWriteType();
		}

		/**
		 * 
		 * <p>
		 * is used to set the value of the property for the instance.
		 * </p>
		 * 
		 * @param _target
		 *            instance of the class for the which the field value have
		 *            to be set
		 * @param _value
		 *            object representing the value that is to be set for the
		 *            field
		 * @since Foundation 1.0
		 * @see {@link FNFieldAccessor#set(Object, Object)} set
		 * 
		 */
		@Override
		public void set(Object _target, Object _value) {
			this.fa.set(_target, _value);
		}
	}

	/**
	 * 
	 * <p>
	 * static inner class which is subclass of {@linkplain MixedAccessor}
	 * provides implementation for retrieving the value of the class property.
	 * </p>
	 * <h4>Created
	 * <h4>Sep 2, 2013, 12:36:49 PM
	 * 
	 * @author Altametrics Inc.
	 * @Since Foundation 1.0
	 */
	private static class PropertySetterAndFieldGetterAccessor extends MixedAccessor {

		/**
		 * 
		 * <p>
		 * Constructor for defining the object of the class by initializing name
		 * of the class property , its setter method and object of
		 * {@link FNFieldAccessor} for respective class property.
		 * </p>
		 * 
		 * @param _name
		 *            String depicting class property name.
		 * @param _type
		 *            Class depicting the data type of the class property.
		 * @param _setter
		 *            Method object for the class property for setting the
		 *            value.
		 * @param _fa
		 *            {@link FNFieldAccessor} object for the class property.
		 * @since Foundation 1.0
		 * 
		 */
		PropertySetterAndFieldGetterAccessor(String _name, Class<?> _type, Method _setter, FNFieldAccessor _fa) {
			super(_name, _type, _fa);
			this.setter = _setter;
		}

		/**
		 * 
		 * <p>
		 * is used to get the Class of the data type in which it is be read
		 * </p>
		 * 
		 * @return {@link Class} object of the data type
		 * @since Foundation 1.0
		 * 
		 */
		@Override
		public Class<?> getReadType() {
			return this.fa.getReadType();
		}

		/**
		 * 
		 * <p>
		 * returns the value of the field represented by the instance of this
		 * class for a a particular instance of the class in which it is a
		 * public property.
		 * </p>
		 * 
		 * @param _target
		 *            instance of the class for which the field value has be
		 *            retrieved.
		 * @return {@link Object} containing the value of the field.
		 * @since Foundation 1.0
		 * @see {@link FNFieldAccessor#get(Object)} get
		 */
		@Override
		public Object get(Object _target) {
			return this.fa.get(_target);
		}
	}

	/**
	 * 
	 * <p>
	 * static method which provides the instance of of
	 * {@link FNPropertyAccessor} or its subclasses to access or set the value
	 * of the class property.
	 * </p>
	 * <h4>Example</h4>
	 * <p>
	 * Method getter = Person.class.getDeclaredMethod("getFirstName",null);<br/>
	 * Method setter = Person.class.getDeclaredMethod("setFirstName",new
	 * Class[]{String.clas});<br/>
	 * Field cityField = Person.class.getDeclaredField("firstName");<br/>
	 * Class fielAccessorClass =Class.forName(
	 * "com.altametrics.foundation.thirdparty.kvc.FNFieldAccessor");<br/>
	 * Constructor constructor = fieldAccessorClass.getDeclaredConstrunctor(new
	 * Class[]{Field.class});<br/>
	 * FNFieldAccessor fieldAccessor =(FNFieldAcessor)
	 * constructor.newInstance(new Object[]{cityField});<br/>
	 * FNPropertyAcessor accessor =
	 * FNPropertyAccessor.getpropertyAccessor("firstName"
	 * ,fieldAcessor.getReadType(),getter,setter,fieldAcessor);<br/>
	 * </p>
	 * 
	 * @param _name
	 *            String depicting the name of the class property.
	 * @param _type
	 *            Class depicting the data type of the class property.
	 * @param _getter
	 *            Method object depicting the getter method of the class
	 *            property defined in the class.
	 * @param _setter
	 *            Method object depicting the setter method of the class
	 *            property defioned in the class.
	 * @param _fa
	 *            FNFieldAccessor Object of the field of the class property.
	 * @return {@link FNPropertyAccessor} of the class property.
	 * @since Foundation 1.0
	 * 
	 */
	static FNPropertyAccessor getPropertyAccessor(String _name, Class<?> _type, Method _getter, Method _setter, FNFieldAccessor _fa) {
		// assert(_name != null, "_name parameter MUST NOT be null!");
		// assert(_type != null, "_type parameter MUST NOT be null!");
		if (_getter == null && _fa != null) {
			return new PropertySetterAndFieldGetterAccessor(_name, _type, _setter, _fa);
		}
		if (_setter == null && _fa != null) {
			return new PropertyGetterAndFieldSetterAccessor(_name, _type, _getter, _fa);
		}

		return new FNPropertyAccessor(_name, _type, _getter, _setter);
	}

	/**
	 * <p>
	 * is used to get the property name of the field.
	 * </p>
	 * <h4>Example</h4>
	 * <p>
	 * Method getter = Person.class.getDeclaredMethod("getFirstName",null);<br/>
	 * Method setter = Person.class.getDeclaredMethod("setFirstName",new
	 * Class[]{String.clas});<br/>
	 * Field cityField = Person.class.getDeclaredField("firstName");<br/>
	 * FNPropetyAccessor accessor = new
	 * FNPropertyAccessor("firstName",field.getType(),getter,setter);<br/>
	 * String name=accessor.getName();<br/>
	 * </p>
	 * 
	 * @return String variable containing the property name
	 * @since Foundation 1.0
	 * 
	 */
	@Override
	public String getName() {
		return this.name;
	}

	/**
	 * 
	 * <p>
	 * returns the value of the field represented by the instance of this class
	 * for a a particular instance of the class in which it is a public
	 * property.
	 * </p>
	 * <h4>Example</h4>
	 * <p>
	 * Method getter = Person.class.getDeclaredMethod("getFirstName",null);<br/>
	 * Method setter = Person.class.getDeclaredMethod("setFirstName",new
	 * Class[]{String.clas});<br/>
	 * Field cityField = Person.class.getDeclaredField("firstName");<br/>
	 * FNPropetyAccessor accessor = new
	 * FNPropertyAccessor("firstName",field.getType(),getter,setter);<br/>
	 * Object object=accessor.get(new Person());<br/>
	 * </p>
	 * </p>
	 * 
	 * @param _target
	 *            instance of the class for which the field value has be
	 *            retrieved.
	 * @return {@link Object} containing the value of the field.
	 * @exception FNDynamicInvocationException
	 *                runtime exception for any invocation call error
	 * @exception FNMissingAccessorException
	 *                invalid property for the class.
	 * @since Foundation 1.0
	 * 
	 */
	@Override
	public Object get(final Object _target) {
		/*
		 * if (logger.isDebugEnabled()) { logger.debug("Getting property " + this.getName() + " from " + _target); }
		 */
		if (this.getter == null) {
			final String propertyName = this.getName();
			if (accesJavaSetterForBoolVar && this.type.getName().equalsIgnoreCase("boolean")) {
				try {
					if (!(propertyName.toLowerCase().startsWith("is"))) {
						String methodName = "is" + Character.toUpperCase(propertyName.charAt(0)) + propertyName.substring(1);
						this.getter = _target.getClass().getMethod(methodName);
					}
				} catch (Exception ignore) {
					throw new FNMissingAccessorException("No mutator method for property: " + propertyName, _target, propertyName);
				}
			} else {
				throw new FNMissingAccessorException("Missing access for property '" + propertyName + "' on object of class " + _target.getClass() + ", accessor: " + this, _target, propertyName);
			}
		}
		if (this.getter == null) {
			final String propertyName = this.getName();
			throw new FNMissingAccessorException("Missing access for property '" + propertyName + "' on object of class " + _target.getClass() + ", accessor: " + this, _target, propertyName);
		}
		final Object result;
		try {
			result = this.getter.invoke(_target, (Object[]) null);
		} catch (RuntimeException e) { /* just reraise runtime exceptions */
			throw e;
		} catch (java.lang.reflect.InvocationTargetException exx) {
			Throwable e = exx.getTargetException();
			if (e instanceof RuntimeException) {
				throw ((RuntimeException) e);
			}

			throw new FNDynamicInvocationException(this.getter, _target, e);
		} catch (java.lang.IllegalAccessException iae) {
			/**
			 * TBD: This happens if we do KVC on a Collections.SingletonSet (as
			 * returned by Collections.singleton(). The SingletonSet is marked
			 * 'private' inside Collections, which is probably why the call
			 * fails. Though technically it shouldn't, I guess its a Java bug?
			 * Maybe we need to lookup the method on the interface object, not
			 * on the class.
			 */
			if (logger.isWarnEnabled()) {
				logger.warn("illegal access:\n" + "  property: " + this.getName() + "\n" + "  method:   " + this.getter + "\n" + "  target:   " + _target.getClass(), iae);
			}
			throw new FNDynamicInvocationException(this.getter, _target, iae);
		} catch (Exception ex) {
			throw new FNDynamicInvocationException(this.getter, _target, ex);
		}

		return result;

	}

	/**
	 * 
	 * <p>
	 * is used to get the Class of the data type in which it is be read
	 * </p>
	 * <h4>Example</h4>
	 * <p>
	 * Method getter = Person.class.getDeclaredMethod("getFirstName",null);<br/>
	 * Method setter = Person.class.getDeclaredMethod("setFirstName",new
	 * Class[]{String.clas});<br/>
	 * Field cityField = Person.class.getDeclaredField("firstName");<br/>
	 * FNPropetyAccessor accessor = new
	 * FNPropertyAccessor("firstName",field.getType(),getter,setter);<br/>
	 * Class type=accessor.getReadType();<br/>
	 * </p>
	 * 
	 * @return {@link Class} object of the data type
	 * @since Foundation 1.0
	 * 
	 */
	@Override
	public Class<?> getReadType() {
		return this.type;
	}

	/**
	 * 
	 * <p>
	 * is used to get the Class of the data type in which it is be written
	 * </p>
	 * <h4>Example</h4>
	 * <p>
	 * Method getter = Person.class.getDeclaredMethod("getFirstName",null);<br/>
	 * Method setter = Person.class.getDeclaredMethod("setFirstName",new
	 * Class[]{String.clas});<br/>
	 * Field cityField = Person.class.getDeclaredField("firstName");<br/>
	 * FNPropetyAccessor accessor = new
	 * FNPropertyAccessor("firstName",field.getType(),getter,setter);<br/>
	 * Class type=accessor.getWriteType();<br/>
	 * </p>
	 * 
	 * @return {@link Class} object of the data type
	 * @since Foundation 1.0
	 * 
	 */
	@Override
	public Class<?> getWriteType() {
		return this.type;
	}

	/**
	 * 
	 * <p>
	 * is used to set the value of the property for the instance. It also have
	 * an internal check for boolean variables starting with is string having
	 * modifier private and setter method has convention setVariableName without
	 * is word. If the boolean variable does not have setIsVariable type setter
	 * method then it checks for setVariableName(boolean variable) method at the
	 * last instance for further dynamicity.
	 * </p>
	 * <h4>Example</h4>
	 * <p>
	 * Method getter = Person.class.getDeclaredMethod("getFirstName",null);<br/>
	 * Method setter = Person.class.getDeclaredMethod("setFirstName",new
	 * Class[]{String.clas});<br/>
	 * Field cityField = Person.class.getDeclaredField("firstName");<br/>
	 * FNPropetyAccessor accessor = new
	 * FNPropertyAccessor("firstName",field.getType(),getter,setter);<br/>
	 * Person person= new Person(); accessor.set(person,"ABC");
	 * </p>
	 * 
	 * @param _target
	 *            instance of the class for the which the field value have to be
	 *            set
	 * @param _value
	 *            object representing the value that is to be set for the field
	 * @exception FNDynamicInvocationException
	 *                runtime exception for any invocation call error
	 * @exception FNMissingAccessorException
	 *                invalid property defined for class
	 * @since Foundation 1.0
	 * 
	 */
	@Override
	public void set(final Object _target, final Object _value) {
		if (this.setter == null) {
			final String propertyName = this.getName();
			//TODO-	Need Approval for it
			// code inserted for handling private boolean isVariableName setter method having convention setVariableName rather than setIsVariableName
			if (accesJavaSetterForBoolVar && this.type.getName().equalsIgnoreCase("boolean")) {
				try {
					if (propertyName.toLowerCase().startsWith("is")) {
						String methodName = "set" + propertyName.substring(2);
						this.setter = _target.getClass().getMethod(methodName, new Class[] { Boolean.TYPE });
					}
				} catch (Exception ignore) {
					throw new FNMissingAccessorException("No mutator method for property: " + propertyName, _target, propertyName);
				}
			} else {
				throw new FNMissingAccessorException("No mutator method for property: " + propertyName, _target, propertyName);
			}
		}
		if (this.setter == null) {
			throw new FNMissingAccessorException("No mutator method for property: " + this.getName(), _target, this.getName());
		} else {
			if (logger.isDebugEnabled()) {
				logger.debug("Setting property " + this.getName() + " of " + _target + " to " + _value);
			}
			final Object[] args = new Object[1];
			args[0] = _value;
			try {
				this.setter.invoke(_target, args);
			} catch (IllegalAccessException ex) {
				throw new FNDynamicInvocationException(this.setter, _target, ex);
			} catch (IllegalArgumentException ex) {
				throw new FNDynamicInvocationException(this.setter, _target, ex);
			} catch (InvocationTargetException ex) {
				throw new FNDynamicInvocationException(this.setter, _target, ex);
			}
		}
	}

}
