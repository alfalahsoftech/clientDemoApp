package com.alfalahsoftech.alframe;

/**
 * Describes a case where the necessary accessor or mutator method could not be
 * located when dynamically getting or setting a property.
 * 
 * @author Howard Lewis Ship
 * @version $Id: MissingAccessorException.java,v 1.1.1.1 2002/06/25 10:50:55
 *          znek Exp $
 **/
public class FNMissingAccessorException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	private transient Object rootObject;
	private transient Object object;

	private String propertyPath;
	private String propertyName;

	/**
	 * @param _rootObject
	 *            the initial object for which a property was being set or
	 *            retrieved.
	 * @param _propertyPath
	 *            the full property name. The failure may occur when processing
	 *            any term within the name.
	 * @param _object
	 *            the specific object being accessed at the time of the failure
	 * @param _propertyName
	 *            the specific property for which no accessor was available
	 **/

	public FNMissingAccessorException(Object _rootObject, String _propertyPath, Object _object, String _propertyName) {
		super("Missing accessor in property path: " + _propertyPath);

		this.rootObject = _rootObject;
		this.propertyPath = _propertyPath;
		this.object = _object;
		this.propertyName = _propertyName;
	}

	public FNMissingAccessorException(String message, Object _object, String _propertyName) {
		super(message);

		this.object = _object;
		this.propertyName = _propertyName;
	}

	public Object getObject() {
		return this.object;
	}

	public String getPropertyName() {
		return this.propertyName;
	}

	public String getPropertyPath() {
		return this.propertyPath;
	}

	public Object getRootObject() {
		return this.rootObject;
	}
}
