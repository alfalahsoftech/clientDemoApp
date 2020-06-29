package com.alfalahsoftech.alframe;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * An exception raised when a dynamic invocation fails with some form of
 * exception. This exception is a {@link RuntimeException} (which prevents
 * anyone from having to declare it) ... it should only get raised as a result
 * of programmer error.
 * 
 * This exception is raised 'on behalf' of a more fundamental exception, which
 * is packaged inside the <code>DynamicInvocationException</code>. This root
 * cause exception may or may not be a runtime exception.
 * 
 * @author Howard Lewis Ship
 * @version $Id: DynamicInvocationException.java,v 1.2 2002/07/24 15:52:09 znek
 *          Exp $
 **/
public class FNDynamicInvocationException extends RuntimeException {
	private static final long serialVersionUID = -6526784320428448859L;
	private Throwable rootCause;

	public FNDynamicInvocationException(String message) {
		super(message);
	}

	public FNDynamicInvocationException(Method _method, Object _target, Throwable _ex) {
		super("An " + _ex.getClass().getName() + " exception occured " + "while executing method " + _method.getName() + " on " + _target + ", " + _ex.getCause());
		this.rootCause = _ex;
	}

	public FNDynamicInvocationException(Method _method, Object _target, InvocationTargetException _ex) {
		super("An error occured while executing method " + _method.getName() + " on " + _target + ", " + _ex.getCause());
		this.rootCause = _ex;
	}

	public FNDynamicInvocationException(String message, Throwable _rootCause) {
		super(message + " RootCause: " + _rootCause);
		this.rootCause = _rootCause;
	}

	public FNDynamicInvocationException(Throwable _rootCause) {
		this.rootCause = _rootCause;
	}

	public Throwable getRootCause() {
		return this.rootCause;
	}
}
