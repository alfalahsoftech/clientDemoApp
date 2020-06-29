package com.alfalahsoftech.alframe;

/**
 * <p>
 * Interface which will provide methods to get the information about a property
 * of a class i.e variable of the class . Information like name , its type and
 * also provides provision for setting the value of the property for particular
 * instance.
 * </p>
 * <h4>Created
 * <h4>Aug 30, 2013, 6:44:02 PM
 * 
 * @author Altametrics Inc.
 * @Since Foundation 1.0
 */
public interface FNIPropertyAccessor {

	public String getName(); // to get the property name

	public Class<?> getReadType(); // to get the datatype of the property in which
	// it is read.

	public Object get(Object _instance); // to get the value for the particular
	// instance.

	public Class<?> getWriteType(); // to get the datatype of the property in which
	// it is written.

	public void set(Object _instance, Object _value); // sets the property value
	// in the instance.
}
