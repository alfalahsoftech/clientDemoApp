package com.alfalahsoftech.alframe;

/**
 * Exception thrown by {@link PropertyHelper} when an propery is specified which
 * does not exist.
 * 
 * @author Howard Lewis Ship
 * @version $Id: MissingPropertyException.java,v 1.1.1.1 2002/06/25 10:50:55
 *          znek Exp $
 */
public class FNMissingPropertyException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	// Make this transient, since we can't count on it being serializable.
	private transient Object instance;
	private transient Object rootObject;

	private String propertyName;
	private String propertyPath;

	public FNMissingPropertyException(Object _instance, String _propertyName) {
		this(_instance, _propertyName, _instance, _propertyName);
	}

	public FNMissingPropertyException(Object _rootObject, String _propertyPath, Object _instance, String _propertyName) {
		super("Class " + _instance.getClass().getName() + " does not implement the property: " + _propertyName);

		this.instance = _instance;
		this.propertyName = _propertyName;
		this.rootObject = _rootObject;
		this.propertyPath = _propertyPath;
	}

	/** The object in which property access failed. */
	public Object getInstance() {
		return this.instance;
	}

	/** The name of the property the instance fails to provide. */
	public String getPropertyName() {
		return this.propertyName;
	}

	/** The root object, the object which is the root of the property path. */
	public Object getRootObject() {
		return this.rootObject;
	}

	/** The property path (containing the invalid property name). */
	public String getPropertyPath() {
		return this.propertyPath;
	}
}
