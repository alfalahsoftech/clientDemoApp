package com.alfalahsoftech.alframe;

import java.lang.reflect.Field;

/**
 * <p>
 * Facilitates access to public instance variables as if they were JavaBeans. It
 * implements {@link FNIPropertyAccessor} interface properties. It also caters
 * to private instance variables also.
 * </p>
 * <h4>Created
 * <h4>Aug 30, 2013, 5:52:20 PM
 * 
 * @author Altametrics Inc.
 * @Since Foundation 1.0
 * @see FNIPropertyAccessor
 */
class FNFieldAccessor implements FNIPropertyAccessor {
	private Field field;
	private boolean isDefault;

	/**
	 * 
	 * <p>
	 * Constructor constructs instance of this class with the Field class object
	 * for which you need to access the information. <br/>
	 * Field object must represent the public field/property of a class.The
	 * isDefault boolean variable allows to access the private property of the
	 * class if needed.
	 * </p>
	 * <h4>Example:-</h4>
	 * <p>
	 * Person person= new Person();<br/>
	 * Field field = person.getClass().getDeclaredField("age"); // alternative :
	 * you can also use getField("age"); <br/>
	 * FNFieldAccessor accessor= new FNFieldAccessor(field); // caution : The
	 * constructor has default specifier <br/>
	 * // hence cannot be used outside the package <br/>
	 * </p>
	 * 
	 * @param _field
	 *            {@link Field} object representing the public property of the
	 *            class
	 * @param _isDefault
	 *            boolean variable depicting whether the field is private member
	 *            of the class or not.
	 * @see {@link Field}
	 * @since Foundation 1.0
	 * 
	 */
	public FNFieldAccessor(Field _field, boolean _isDefault) {
		this.field = _field;
		this.isDefault = _isDefault;
	}

	/**
	 * 
	 * <p>
	 * Constructor constructs instance of this class with the Field class object
	 * for which you need to access the information. <br/>
	 * Field object must represent the public field/property of a class
	 * </p>
	 * <h4>Example:-</h4>
	 * <p>
	 * Person person= new Person();<br/>
	 * Field field = person.getClass().getDeclaredField("age"); // alternative :
	 * you can also use getField("age"); <br/>
	 * FNFieldAccessor accessor= new FNFieldAccessor(field); // caution : The
	 * constructor has default specifier <br/>
	 * // hence cannot be used outside the package <br/>
	 * </p>
	 * 
	 * @param _field
	 *            {@link Field} object representing the public property of the
	 *            class
	 * @see {@link Field}
	 * @since Foundation 1.0
	 * 
	 */
	FNFieldAccessor(Field _field) {
		this.field = _field;
	}

	/**
	 * <p>
	 * is used to get the property name of the field.
	 * </p>
	 * <h4>Example</h4>
	 * <p>
	 * Person person= new Person(); <br/>
	 * Field field = person.getClass().getDeclaredField("age");<br/>
	 * FNFieldAccessor accessor= new FNFieldAccessor(field);<br/>
	 * String _propertyName=accessor.getName();<br/>
	 * </p>
	 * 
	 * @return String variable containing the property name
	 * @since Foundation 1.0
	 * 
	 */
	@Override
	public String getName() {
		return this.field.getName();
	}

	// TBD: always building the exception reasons is still costly

	/**
	 * 
	 * <p>
	 * is used to set the value of the property for the instance.
	 * </p>
	 * <h4>Example</h4>
	 * <p>
	 * Person person= new Person(); <br/>
	 * FNFielAccessor accessor = new
	 * FNFieldAccessor(Person.class.getDeclaredField("age"));<br/>
	 * accessor.set(person,20);<br/>
	 * </p>
	 * 
	 * @param _instance
	 *            instance of the class for the which the field value have to be
	 *            set
	 * @param _value
	 *            object representing the value that is to be set for the field
	 * @exception FNDynamicInvocationException
	 *                runtime exception for any invocation call error
	 * @since Foundation 1.0
	 * 
	 */
	@Override
	public void set(Object _instance, Object _value) {
		try {
			if (this.isDefault) {
				this.field.setAccessible(true);
			}
			this.field.set(_instance, _value);
		} catch (IllegalArgumentException iex) {
			throw new FNDynamicInvocationException("Unable to set public attribute " + this.field.getName() + " of " + _instance + " to " + _value + "(" + (_value != null ? _value.getClass() : null) + ")", iex);
		} catch (Exception ex) {
			throw new FNDynamicInvocationException("Unable to set public attribute " + this.field.getName() + " of " + _instance + " to " + _value + ".", ex);
		}
	}

	/**
	 * 
	 * <p>
	 * is used to get the Class of the data type in which it is be read
	 * </p>
	 * <h4>Example</h4>
	 * <p>
	 * Person person= new Person(); <br/>
	 * FNFielAccessor accessor = new
	 * FNFieldAccessor(Person.class.getDeclaredField("age"));<br/>
	 * Class type=accessor.getReadType();<br/>
	 * </p>
	 * 
	 * @return {@link Class} object of the data type
	 * @since Foundation 1.0
	 * 
	 */
	@Override
	public Class<?> getReadType() {
		return this.field.getType();
	}

	/**
	 * 
	 * <p>
	 * is used to get the Class of the data type in which it is be written
	 * </p>
	 * <h4>Example</h4>
	 * <p>
	 * Person person= new Person(); <br/>
	 * FNFielAccessor accessor = new
	 * FNFieldAccessor(Person.class.getDeclaredField("age"));<br/>
	 * Class type=accessor.getWriteType();<br/>
	 * </p>
	 * 
	 * @return {@link Class} object of the data type
	 * @since Foundation 1.0
	 * 
	 */
	@Override
	public Class<?> getWriteType() {
		return this.field.getType();
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
	 * Person person= new Person(); <br/>
	 * FNFielAccessor accessor = new
	 * FNFieldAccessor(Person.class.getDeclaredField("age"));<br/>
	 * Object _obj= accessor.get(person);
	 * </p>
	 * </p>
	 * 
	 * @param _instance
	 *            instance of the class for which the field value has be
	 *            retrieved.
	 * @return {@link Object} containing the value of the field.
	 * @exception FNDynamicInvocationException
	 *                runtime exception for any invocation call error
	 * 
	 * @since Foundation 1.0
	 * 
	 */
	@Override
	public Object get(Object _instance) {
		try {
			if (this.isDefault) {
				this.field.setAccessible(true);
			}
			return this.field.get(_instance);
		} catch (Exception ex) {
			throw new FNDynamicInvocationException("Unable to read public attribute " + this.field.getName() + " of " + _instance, ex);
		}
	}
}
