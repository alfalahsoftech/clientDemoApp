package com.alfalahsoftech.alframe.factory;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Map;

import com.alfalahsoftech.alframe.AFArrayList;
import com.alfalahsoftech.alframe.AFConstant;
import com.alfalahsoftech.alframe.AFHashMap;
import com.alfalahsoftech.alframe.FNKeyValueInterface;
import com.alfalahsoftech.alframe.annotation.AFControllerSetup;
import com.alfalahsoftech.alframe.annotation.ReqMthdDgntr;
import com.alfalahsoftech.controller.AFBaseController;
import com.alfalahsoftech.fd.controller.FoodBaseController;
import com.alfalahsoftech.web.AFObject;

public class AFAnnotationFactory {
	private static volatile AFAnnotationFactory factoryObj;
	private AFHashMap<String, Object> actionMap = new AFHashMap<>();
	private static AFArrayList<AFControllerSetup> glbAFList = new AFArrayList<>();

	public AFArrayList<AFControllerSetup> glbAFList() {
		return glbAFList;
	}

	public static void executeFactory() {
		if (factoryObj == null) {
			synchronized (AFAnnotationFactory.class) {
				if (factoryObj == null) {
					factoryObj = new AFAnnotationFactory();
					factoryObj.loadData();
				}
			}
		}
	}

	public void addObject(String key, Object obj) {
		this.actionMap.put(key, obj);
	}

	public AFHashMap<String, Object> actionMap() {
		return this.actionMap;
	}

	public static AFAnnotationFactory factoryObj() {
		return factoryObj;
	}

	public void loadData() {
		for(String cls : classNamesForPackage("com.alfalahsoftech", false)) {
			//System.out.println("cls===== " + cls);
			this.loadAjaxSetup(classForClassName(cls));
		}
	}

	public void inokeAjax() {

	}

	@SuppressWarnings("unchecked")
	public <T> T objectForClass(Class<T> classObj) {
		try {
			System.out.println("classObj.getModifiers() = " + classObj.getModifiers());
			if (classObj.getModifiers() == 0) {
				Constructor<?>[] ctors = classObj.getDeclaredConstructors();
				Constructor<?> ctor = null;
				for(Constructor<?> ctor2 : ctors) {
					ctor = ctor2;
					if (ctor.getGenericParameterTypes().length == 0) {
						break;
					}
				}
				ctor = classObj.getDeclaredConstructor();
				ctor.setAccessible(true);
				return (T) ctor.newInstance();
			} else {
				return classObj.newInstance();
			}
		} catch (NoSuchMethodException | IllegalArgumentException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
			throw new RuntimeException("Exception occured during object creation : AFAnnotationFactory~objectForClass");
		}
	}

	@SuppressWarnings("static-access")
	public Object createAndLoad(String className) {
		return this.objectForClass(this.classForClassName(className));
	}

	/**
	 *
	 * <p>
	 * In this method object will be loaded from map and serial objects are
	 * created for specified serial id class.
	 * </p>
	 *
	 * @param AFObject
	 *            <code>Object</code>
	 * @param dataMap
	 *            <code>AFHashMap</code>
	 * @param serialID
	 *            <code>String</code>
	 * @param <name><space><Description>
	 * @return <code>Object</code>
	 * @exception Description
	 *                exception description
	 * @see {@link FNSerialUtil#loadFromMap(Object, AFHashMap, String)}
	 * @since Foundation 1.0
	 */
	/*	public Object loadFromMap(Object AFObject, AFHashMap<String, Object> dataMap, String serialID) {
			if (AFObject instanceof FNGlobalDir || AFObject instanceof FNSerialSetup || AFObject instanceof FNSerialToManySetup || AFObject instanceof FNSerialToOneSetup) {
				FNSerialSetup serialSetup = FNAnnotationUtil.serialObjectUsingAnot(AFObject.getClass());
				return serialSetup.loadFromMap(AFObject, dataMap, this);
			}
			FNSerialSetup serialSetup = FNSerialObjectFactory.factory().serialObject(AFObject.getClass(), serialID);
			return serialSetup.loadFromMap(AFObject, dataMap, this);
		}*/

	/**
	 *
	 * <p>
	 * To load the object from map. if keys == null then keyset in the datamap
	 * is added in keys. for AFObject those keys will be put and get their
	 * associated values from hashMap. by this method all the object are
	 * created. if nullkeys are not equal to null then for that key null value
	 * will be associated and put in the AFObject.
	 * </p>
	 *
	 * <pre>
	 * personObject.
	 * hashMap = {firstName = adya; lastName=rajput;}
	 * nullKeys = [lastName]
	 * then,
	 * personObject = {firstName = adya; lastName=null;}
	 * </pre>
	 *
	 * @param _AFObject
	 *            <code>Object</code>
	 * @param dataMap
	 *            <code>AFHashMap</code>
	 * @param keys
	 *            <code>AFArrayList</code>
	 * @param nullKeys
	 *            <code>AFArrayList</code>
	 * @return <code>Object</code>
	 * @since Foundation 1.0
	 *
	 */
	public Object loadFromMap(Object AFObject, AFHashMap<String, Object> dataMap, AFArrayList<String> keys, AFArrayList<String> nullKeys) {
		String[] keysArray = null;
		if (keys != null) {
			keysArray = Arrays.copyOf(keys.toArray(), keys.size(), String[].class);
		}
		return this.loadFromMap(AFObject, dataMap, keysArray, nullKeys);
	}

	/**
	 *
	 * <p>
	 * In this method object will be loaded from map and serial objects are
	 * created for specified serial id class.
	 * </p>
	 *
	 * @param fnObject
	 *            <code>Object</code>
	 * @param dataMap
	 *            <code>AFHashMap</code>
	 * @param serialID
	 *            <code>String</code>
	 * @param <name><space><Description>
	 * @return <code>Object</code>
	 * @exception Description
	 *                exception description
	 * @see {@link FNSerialUtil#loadFromMap(Object, AFHashMap, String)}
	 * @since Foundation 1.0
	 */
	public Object loadFromMap(Object fnObject, AFHashMap<String, Object> dataMap, String serialID) {
		/*if (fnObject instanceof FNGlobalDir || fnObject instanceof FNSerialSetup || fnObject instanceof FNSerialToManySetup || fnObject instanceof FNSerialToOneSetup) {
			FNSerialSetup serialSetup = FNAnnotationUtil.serialObjectUsingAnot(fnObject.getClass());
			return serialSetup.loadFromMap(fnObject, dataMap, this);
		}
		FNSerialSetup serialSetup = FNSerialObjectFactory.factory().serialObject(fnObject.getClass(), serialID);
		return serialSetup.loadFromMap(fnObject, dataMap, this);*/
		return null;
	}

//	@SuppressWarnings("rawtypes")
	public Object loadFromMap(Object AFObject, AFHashMap<String, Object> dataMap, String[] keys, AFArrayList<String> nullKeys) {
		System.out.println("==================@@@@@@@@@@@@@@@@@@=====loadFromMap-dataMap=> " + dataMap);
		if (AFObject != null && dataMap != null) {
			if (keys == null || keys.length == 0) {
				AFArrayList<String> keyList = new AFArrayList<>(dataMap.keySet());
				keys = Arrays.copyOf(keyList.toArray(), keyList.size(), String[].class);
			}
			for(String key : keys) {
				Object value = dataMap.get(key);
				if (value != null && !key.contains(".")) {
					/*
					
					if (value.getClass() == AFArrayList.class) {
					value = ((AFArrayList) value).altaClone();
					} else if (value.getClass() == AFHashMap.class) {
					value = ((AFHashMap) value).altaClone();
					}
					FNKeyValueInterface.Utility.takeValueForKey(AFObject, value, key);
					*/}
			}
		}
		if (AFObject != null && nullKeys != null) {
			for(String key : nullKeys) {
				if (!key.contains(".")) {
					FNKeyValueInterface.Utility.takeValueForKey(AFObject, null, key);
				}
			}
		}
		return AFObject;
	}

	/**
	 *
	 * <p>
	 * this method is used to load object key value from map.
	 * </p>
	 *
	 * @param className
	 *            name of the class
	 * @param arrayOfDataMap
	 *            array of map containing key value pair.
	 * @param serialID
	 *            serial id of the class.
	 * @return <code>AFArrayList</code>
	 * @since Foundation 1.0
	 *
	 */
	public AFArrayList<Object> loadFromArrayOfMap(String className, AFArrayList<AFHashMap<String, Object>> arrayOfDataMap, String serialID) {
		Class<?> classObj = classForClassName(className);
		AFArrayList<Object> objectArray = new AFArrayList<>();
		for(AFHashMap<String, Object> AFHashMap : arrayOfDataMap) {
			objectArray.add(this.createAndLoad(classObj, AFHashMap, serialID));
		}
		return objectArray;
	}

	/**
	 * <p>
	 * This method create an object which will contain destKey values like
	 * firstName='Jack', lastName='el', and cityName='California'.
	 * </p>
	 *
	 * @param classObj
	 *            An object of specified class.
	 * @param destKey
	 *            destKey like "firstName~lastName,city.cityName" same as argKey
	 *            or may be different.
	 * @param argKey
	 *            argkeys like "firstName~lastName,city.cityName".
	 * @param paramObjects
	 *            contains objects like person address.
	 * @return Returns an object.
	 * @see {@link #updateObjectFromMap(Object, Map)}
	 */

	@SuppressWarnings("unchecked")
	public <T> T createObjectKeyArg(Class<T> classObj, String destKey, String argKey, Object... paramObjects) {
		return (T) this.createAndLoad(classObj, this.createMapFromKeyArg(destKey, argKey, paramObjects), null, null);
	}

	/**
	 * <p>
	 * This method create {@link AFHashMap} which contains destkeys as keys and
	 * value corresponding of these keys are value of argKeys after fetching on
	 * paramObjects.
	 * </p>
	 *
	 * @param destKey
	 *            destKey like "firstName,dummyLastName,cityName" always be
	 *            com!same as argKey or may be different.
	 * 
	 * 
	 * @param argKey
	 *            argkeys like "firstName~lastName,city.cityName".
	 * @param paramObjects
	 *            contains objects like person address.
	 * @return Return {@link AFHashMap}.
	 * @exception FNException
	 *                Destination keys count doesn't match to source keys count.
	 *                createMapFromKeyArg ("Person" ,
	 *                "firstName,lastName,dob,height", null,
	 *                aFirstName,aLastName, new Date(),new Double (5.6));
	 * @see {@link FNStrHashFactory#arrayofArrayofKeys(String)}
	 * @see {@link com.altametrics.foundation.thirdparty.kvc.FNKeyValueInterface.Utility#valueForKeyPath(Object, String)}
	 *      For example createMapFromKeyArg("firstName,dummyLastName,cityName",
	 *      "firstName~lastName,cityName", new Person("Joe", "Doe"), new
	 *      City("LA")); { firstName = "Joe"; dummyLastName = "Doe"; cityName =
	 *      "LA"; }
	 */

	public AFHashMap<String, Object> createMapFromKeyArg(String destKey, String argKey, Object... paramObjects) {
		AFHashMap<String, Object> returnMap = new AFHashMap<>();
		if (destKey == null || destKey.trim().length() == 0) {
			if (paramObjects != null && paramObjects.length > 0) {
				throw new RuntimeException("FNSerialUtil~createMapFromKeyArg~DestKeyIsNull");
			} else {
				return returnMap;
			}
		}
		String[] destKeySetArray = destKey.split("~"); //FNStrHashFactory.factory().parseTildeCSVToArray(destKey); // Convert "firstName,dummyLastName,cityName" into array of strings - like a split
		Object[] objectArray = this.paramObjectUsingArg(argKey, paramObjects); // person, city is converted into person.firstName, person.lastName, address.city.cityName = Joe, Doe, LA
		if (objectArray.length != destKeySetArray.length) {
			throw new RuntimeException("FNSerialUtil~createMapFromKeyArg~KeyArrayLengthDoesNotMatch");
		}

		int count = 0;
		for(Object object : objectArray) {
			returnMap.put(destKeySetArray[count++], object); // Making AFHashMap on destination keys and values from objectArray
		}
		System.out.println("createMapFromKeyArg==== " + returnMap);
		return returnMap;
	}

	/**
	 * <p>
	 * This method creates an array of objects based on specified paramObjects
	 * and argKey.
	 * </p>
	 *
	 * @param argKey
	 *            like "firstName~lastName,city.cityName". or if it is "~," or
	 *            if it is "self~self,self" this means put arguments back into
	 *            the array
	 * @param paramObjects
	 *            contains objects like person, address etc.
	 * @return An array of objects.
	 * @see {@link FNStrHashFactory#arrayofArrayofKeys(String)}
	 * @see {@link com.altametrics.foundation.thirdparty.kvc.FNKeyValueInterface.Utility#valueForKeyPath(Object, String)}
	 *      For example paramObjectUsingArg("firstName~lastName,city.cityName",
	 *      new Person("Joe", "Doe"), new City("LA")); "Joe", "Doe", "LA"
	 * @since Foundation 1.0
	 */
	public Object[] paramObjectUsingArg(String argKey, Object... paramObjects) {
		AFArrayList<Object> returnArray = new AFArrayList<>();
		if (argKey == null) {
			return paramObjects;
		} else {
			AFArrayList<String[]> keysArray = this.parseKey2(argKey);
			int count = 0;
			for(Object object : paramObjects) { // loops on paramObjects : Person and City
				String[] keyArray = keysArray.get(count);
				for(String element : keyArray) { // loops on "firstName" "lastName" for Person and  city.cityName on City
					if (element == null || FNC.SELF.equals(element) || FNC.EMPTYSTR.equals(element)) {
						returnArray.add(object);
					} else {
						returnArray.add(FNKeyValueInterface.Utility.valueForKeyPath(object, element));
					}
				}
				count++;
			}
		}
		return returnArray.toArray();
	}

	/**
	 *
	 * <p>
	 * This method is used to create object graph of specified class with
	 * specified serial id. e.g. Suppose there is a class person, which has
	 * variable firstName lastName and department which is an object of class
	 * Department. if we need to generate a report in Integration Manager then
	 * we will provide the name of the class and serialId this will load the
	 * object from the file and create the object of that class.
	 *
	 * </p>
	 *
	 * @param className
	 *            name of the class whose serial id you are providing
	 * @param dataMap
	 *            <code>AFHashMap</code>
	 * @param _serialId
	 *            serialID of the specified class.
	 * @return <code>Object</code> containing hashes for the objects of the
	 *         specified class.
	 * @see {@link com.altametrics.foundation.AFHashMap#createAndLoad(String, String)}
	 * @since Foundation 1.0
	 *
	 */
	public Object createAndLoad(String className, AFHashMap<String, Object> dataMap, String serialID) {
		return this.createAndLoad(classForClassName(className), dataMap, serialID);
	}

	/**
	 *
	 * <p>
	 * This method is used to create object graph of specified class with
	 * specified serial id. e.g. Suppose there is a class person, which has
	 * variable firstName lastName and department which is an object of class
	 * Department. if we need to generate a report in Integration Manager then
	 * we will provide the name of the class and serialId this will load the
	 * object from the file and create the object of that class.
	 *
	 * </p>
	 *
	 * @param className
	 *            name of the class whose serial id you are providing
	 * @param dataMap
	 *            <code>AFHashMap</code>
	 * @param _serialId
	 *            serialID of the specified class.
	 * @return <code>Object</code> containing hashes for the objects of the
	 *         specified class.
	 * @see {@link com.altametrics.foundation.AFHashMap#createAndLoad(String, String)}
	 * @since Foundation 1.0
	 *
	 */
	@SuppressWarnings("unchecked")
	public <T> T createAndLoad(Class<T> myClass, AFHashMap<String, Object> dataMap, String serialID) {
		Object object = this.loadFromMap(this.objectForClass(myClass), dataMap, serialID);
		if (object instanceof AFObject) {
			((AFObject) object).initObject();
		}
		return (T) object;
	}

	public Object createAndLoad(Class<?> classObj, AFHashMap<String, Object> dataMap, AFArrayList<String> keys, AFArrayList<String> nullKeys) {
		Object object = this.loadFromMap(this.objectForClass(classObj), dataMap, keys, nullKeys);
		if (object instanceof AFObject) {
			((AFObject) object).initObject();
		}
		return object;
	}

	public Object createAndLoad(String className, AFHashMap<String, Object> dataMap, AFArrayList<String> keys, AFArrayList<String> nullKeys) {
		return this.createAndLoad(classForClassName(className), dataMap, keys, nullKeys);
	}

	public static Class<?> classForClassName(String className) {
		Class<?> object = null;
		try {
			object = Class.forName(className);
		} catch (ClassNotFoundException e) {
			//			throw new FNException(logger, FNLogLevel.TRACE, "FNSerialUtil~classForClassName~ClassNotFound", FNStaticUtil.class, e, "className", null, className);
		}
		return object;
	}

	public AFControllerSetup ctrlSetupForID(String id) {
		ListIterator<AFControllerSetup> itr = this.glbAFList().listIterator();
		while (itr.hasNext()) {
			AFControllerSetup ctrlSetup = itr.next();
			System.out.println("ctrlSetup.id= " + ctrlSetup.id + "  SEtup for id= " + id);
			if (ctrlSetup.id.equals(id)) {
				return ctrlSetup;
			}
		}
		return null;
	}

	abstract class A {
		public String name;

		abstract public void add();
	}

	class B extends A {
		public String name;

		@Override
		public void add() {
			// TODO Auto-generated method stub

		}
	}

	public static void main(String ar[]) {
		executeFactory();
		System.out.println(classNamesForPackage("com.alflah", false));
		AFBaseController obj = (AFBaseController) AFAnnotationFactory.factoryObj().createAndLoad(FoodBaseController.class.getClass().getName());
		//		A a = (A) AFAnnotationFactory.factoryObj().objectForClass(classForClassName("A"));
		obj.request();
	}

	public static ArrayList<String> classNamesForPackage(String packageName, boolean includeSubCLasses) {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		ArrayList<String> classNamesOfPackage = new ArrayList<>();
		String packageNameOrig = packageName;
		packageName = packageName.replace(DOT, SLASH);
		URL packageURL = classLoader.getResource(packageName);
		if (!packageURL.getProtocol().equals("jar")) {
			URI uri = null;
			try {
				uri = new URI(packageURL.toString());
			} catch (URISyntaxException e) {
				//FNExceptionUtil.logException(e);
			}
			if (uri != null) {
				File folder = new File(uri);
				File[] contenuti = folder.listFiles();
				if (contenuti != null) {
					for(File actual : contenuti) {
						if (actual.isDirectory()) {
							classNamesOfPackage.addAll(classNamesForPackage(packageNameOrig + DOT + actual.getName(), true));
							continue;
						}
						String entryName = actual.getName();
						entryName = entryName.substring(0, entryName.lastIndexOf(DOT));
						classNamesOfPackage.add(packageNameOrig + DOT + entryName);
					}
				}
			}
		}
		return classNamesOfPackage;
	}

	public static boolean isEmptyStr(final String v) {
		if (v == null) {
			return true;
		}
		final String s = v.trim();
		return s.length() == 0;
	}

	public static boolean isNonEmptyStr(final String v) {
		return !isEmptyStr(v);
	}

	public void loadAjaxSetup(Class<?> classObj) {
		//		AFArrayList<AFControllerSetup> actionArray = new AFArrayList<>();
		
		Method[] methods = classObj.getDeclaredMethods();
		//		FNCustomBean customBean = classObj.getAnnotation(FNCustomBean.class);
		//		FNRequestProcessor requestProcessor = new FNRequestProcessor();
		for(Method method : methods) {
			if (method.isAnnotationPresent(ReqMthdDgntr.class) && Modifier.isPublic(method.getModifiers())) {
				System.out.println("classObj.getName(===)" + classObj.getName());
				ReqMthdDgntr ajax = method.getAnnotation(ReqMthdDgntr.class);
				AFControllerSetup ajaxSetup = new AFControllerSetup();
				ajaxSetup.id = ajax.id();
				if (isEmptyStr(ajaxSetup.id)) {
					ajaxSetup.id = classObj.getSimpleName() + AFConstant.UNDERSCORE + method.getName();
				}
				ajaxSetup.methodName = method.getName();
				ajaxSetup.controllerName = classObj.getName();
				System.out.println("ajaxSetup.controllerName = " + ajaxSetup.controllerName);
				this.glbAFList().add(ajaxSetup);
				//requestProcessor.addAjaxMethodToCache(classObj, method.getName());
			}
		}
		//		return actionArray;
	}

	public Object invokeMethodOnObjectWithArgs(Object object, String methodName, Object... args) {
		Class<?>[] classes = null;
		if (args != null && args.length > 0) {// creating unque Key for caching
			classes = new Class[args.length];
			int i = 0;
			for(Object obj : args) {
				classes[i++] = obj.getClass();
			}
		}
		try {
			Method method = this.getMethod(object.getClass(), methodName, classes);
			Object object2 = method.invoke(object, args);
			return object2;
		} catch (Exception ex) {
			return null;
			/*
			Throwable e1 = ExceptionUtils.getRootCause(ex);
			if (FNExceptionUtil.isFNPanelException(e1)) {
			((FNPanelException) e1).addPanelErrorMessage();
			return null;
			} else if (e1 != null && e1.getClass() == FNMsgIgnoreException.class) {
			try {
			throw e1;
			} catch (Throwable e) {
			}
			}
			throw new FNException(logger, FNLogLevel.TRACE, "FNMethodExecutor~invokeMethodOnObjectWithArgs~MethodInvokationFailed", this, ex, "className~methodName", null, object.getClass().getName(), methodName);
			 */}
	}

	private Method getMethod(Class<?> classObj, String methodName, Class<?>[] params) {
		StringBuilder key = new StringBuilder();
		if (params != null && params.length > 0) {// creating unique Key for caching
			for(Class<?> obj : params) {
				key.append(AFConstant.TILDE);
				key.append(obj.getName());
			}
		}
		String methodKey = classObj.getName() + AFConstant.TILDE + methodName + key;// unique key for Method Caching - className~methodName~Tilde seperated Class Name of Arguments
		if (classObj.getSimpleName().equalsIgnoreCase("AjaxBean")) {
			System.out.println("methodKey======= " + methodKey);
			System.out.println("keyMethodMap= " + this.keyMethodMap);
		}
		Method method = (Method) this.keyMethodMap.get(methodKey);
		if (method == null) {
			method = this.searchAndCacheMethod(methodKey, classObj, methodName, params);
		}
		return method;
	}

	AFHashMap<String, Method> keyMethodMap = new AFHashMap<>();

	private Method searchAndCacheMethod(String methodKey, Class<?> classObj, String methodName, Class<?>[] params) {
		Method method = (Method) this.keyMethodMap.get(methodKey);
		if (method == null) {
			AFArrayList<Method> methodList = this.methodListOfClassForName(classObj, methodName);
			if (methodList.size() == 1) {// if Single method found by name
				method = methodList.get(0);
			} else { // Find Method by name
				method = this.searchMethod(classObj, methodName, params);
			}
			if (method == null && params != null) {//Find Methods in Super Classes
				for(Object superClass : this.superClasses(classObj)) {
					try {
						method = superClass.getClass().getDeclaredMethod(methodName, params);
					} catch (NoSuchMethodException | SecurityException ignored) {
					}
					if (method != null) {
						break;
					}
				}
			}
			if (method == null) {
				System.out.println("No such method. MethodKey : " + methodKey);
				//throw new FNException(logger, FNLogLevel.TRACE, "FNMethodFactory~parseKey~NoSuchMethodException", this, "className~methodName", null, classObj.getName(), methodName);
			}
			this.keyMethodMap.put(methodKey, method);
		}
		return method;
	}

	AFHashMap<String, AFArrayList<Method>> classMethodListMap = new AFHashMap<>();

	@SuppressWarnings("unchecked")
	private AFArrayList<Method> methodListOfClassForName(Class<?> classObj, String methodName) {
		String keyString = classObj.getName() + AFConstant.TILDE + methodName;
		AFArrayList<Method> methodList = (AFArrayList<Method>) this.classMethodListMap.get(keyString);// this.classMethodListMap.get(keyString);
		if (methodList == null) {
			methodList = new AFArrayList<>();
			for(Method method : classObj.getMethods()) {
				if (method.getName().equals(methodName)) {
					methodList.add(method);
				}
			}
			this.classMethodListMap.put(keyString, methodList);
		}
		return methodList;

	}

	// returns all superclasses andInterface of Class
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private AFArrayList<Class<?>> superClasses(Class<?> classObj) {
		AFArrayList<Class<?>> superClasses = new AFArrayList<>();
		while (classObj.getSuperclass() != Object.class) {
			classObj = classObj.getSuperclass();
			superClasses.add(classObj);
			superClasses.addAll(new AFArrayList(classObj.getInterfaces()));
		}
		return superClasses;
	}

	/*
	 * Search method name with or without arguments
	 */
	private Method searchMethod(Class<?> classObj, String methodName, Class<?>[] paramTypes) {
		Method method = null;
		try {
			if (paramTypes == null || paramTypes.length == 0) {
				method = classObj.getDeclaredMethod(methodName);// searching method by Name
			} else {
				method = classObj.getDeclaredMethod(methodName, paramTypes);// searching method by Name and Parameters
			}
		} catch (NoSuchMethodException ignore) {
		}
		return method;
	}

	/**
	 * <p>
	 * This method will cast the specified object to the specified class type.
	 * The classes in which the object can be casted are Integer, Float, String,
	 * Double, Long, Boolean, FNDate and FNTimestamp. For rest all the cases,
	 * the method will return same object.
	 * </p>
	 * <h4>Example</h4>
	 * <p>
	 * castObject("22",java.lang.Integer); In this case this method will return
	 * 22 as Integer type.
	 * </p>
	 *
	 * @param _object
	 *            : the object that is to be casted.
	 * @param _type
	 *            : {@link Class} object specifying the Class to which the
	 *            object is to be casted.
	 * @return object in specified type.
	 * @since Foundation 1.0
	 *
	 */
	public Object castObject(Object object, Class<?> type) {
		if (object == null || type == null || object.getClass() == type) {
			return object;
		}
		Object returnedObject = object;
		if (type.equals(Integer.class) || type.equals(int.class) || type == Integer.TYPE) {
			returnedObject = this.intValue(object);
		} else if (type.equals(Boolean.class) || type.equals(boolean.class) || type == Boolean.TYPE) {
			returnedObject = this.boolValue(object);
		} else if (type.equals(String.class)) {
			returnedObject = this.stringValue(object);
		} else if (type.equals(Double.class) || type.equals(double.class) || type == Double.TYPE) {
			returnedObject = this.doubleValue(object);
		} else if (type.equals(Float.class) || type.equals(float.class) || type == Float.TYPE) {
			returnedObject = this.floatValue(object);
		} else if (type.equals(Long.class) || type.equals(long.class) || type == Long.TYPE) {
			returnedObject = this.longValue(object);
		} else if (type.equals(Number.class)) {
			returnedObject = this.intOrLongValue(object);
		} /*else if (type.equals(FNDate.class) && object.getClass() == String.class) {
			returnedObject = this.getDate((String) object);
			} else if (type.equals(java.sql.Date.class) && object.getClass() == String.class) {
			returnedObject = FNEntityDateFactory.factory().sqlDate(this.dateValue(object).getTime());
			} else if (type.equals(java.sql.Timestamp.class) && object.getClass() == String.class) {
			returnedObject = FNEntityDateFactory.factory().timeStamp(this.getTimestamp((String) object).getTime());
			} else if (type.equals(FNDate.class)) {
			returnedObject = this.getFNDateValue(object);
			} else if (type.equals(FNTimestamp.class)) {
			returnedObject = this.getFNTimestampValue(object);
			} else if (type.equals(Date.class)) {// java.util.Date
			returnedObject = this.dateValue(object);
			} else if (type.equals(AFArrayList.class)) {
			returnedObject = this.toList(object);
			} else if (type.equals(AFHashMap.class)) {
			returnedObject = this.toMap(object);
			} else if (type.equals(Blob.class)) {
			returnedObject = object;
			}*/
		return returnedObject;
	}

	public AFArrayList<?> toList(Object object) {/*
													if (object instanceof AFArrayList) {
													return (AFArrayList<?>) object;
													} else if (object instanceof String) {// if string then we assumes its json string
													return FNParserUtil.altaListFromJsonArray((String) object);
													} else if (object instanceof List<?>) {
													return new AFArrayList<>((List<?>) object);
													} else {
													return AFArrayList.EMPTY_ARRAY;
													}
													*/
		return null;
	}

	public AFHashMap<?, ?> toMap(Object object) {/*
													if (object instanceof AFHashMap) {
													return (AFHashMap<?, ?>) object;
													} else if (object instanceof String) {// if string then we assumes its json string
													return FNParserUtil.jsonStringToAltaMap((String) object);
													} else if (object instanceof Map<?, ?>) {
													return new AFHashMap<>((Map<?, ?>) object);
													} else {
													return new AFHashMap<>();
													}
													*/
		return null;
	}

	public int intValue(final Object v) {
		if (v == null) {
			return 0;
		}
		if (v instanceof Number) {
			return ((Number) v).intValue();
		}
		if (v instanceof String) {
			String s = (String) v;
			if (s.trim().length() == 0) {
				return 0;
			}
			try {
				if (s.indexOf('.') >= 0) {
					return new BigDecimal(s).intValue();
				}
				return Integer.parseInt(s);
			} catch (NumberFormatException e) {
				return 0;
			}
		}
		return this.intValue(v.toString());
	}

	/**
	 * Returns an int value for the given object. It follows this sequence:
	 * <ul>
	 * <li>null - 0
	 * <li>Number - call intValue() of number
	 * <li>String - if len=0, returns 0. If the string contains a '.', parse as
	 * BigDecimal and return the intValue(). Otherwise return the value of
	 * Integer.parseInt(). If a NumberFormatException occurs, return 0.
	 * <li>for all other objects, intValue() is called with the toString()
	 * representation of the object.
	 * </ul>
	 *
	 * @param v
	 *            - some value object, can be null
	 * @return the int value represented by the object
	 */
	public double doubleValue(final Object v) {
		if (v == null) {
			return 0.0;
		}
		if (v instanceof Number) {
			return ((Number) v).doubleValue();
		}
		if (v instanceof String) {
			String s = (String) v;
			if (s.trim().length() == 0) {
				return 0.0;
			}
			try {
				if (s.indexOf('.') >= 0) {
					return new BigDecimal(s).doubleValue();
				}
				return Double.parseDouble(s);
			} catch (NumberFormatException e) {
				return 0.0;
			}
		}
		return this.doubleValue(v.toString());
	}

	public float floatValue(final Object v) {
		if (v == null) {
			return 0.0f;
		}
		if (v instanceof Number) {
			return ((Number) v).floatValue();
		}
		if (v instanceof String) {
			String s = (String) v;
			if (s.length() == 0) {
				return 0.0f;
			}
			try {
				if (s.indexOf('.') >= 0) {
					return new BigDecimal(s).floatValue();
				}
				return Float.parseFloat(s);
			} catch (NumberFormatException e) {
				return 0.0f;
			}
		}
		return this.floatValue(v.toString());
	}

	/**
	 * Returns an long value for the given object. It follows this sequence:
	 * <ul>
	 * <li>null - 0
	 * <li>Number - call longValue() of number
	 * <li>String - if len=0, returns 0. If the string contains a '.', parse as
	 * BigDecimal and return the longValue(). Otherwise return the value of
	 * Long.parseLong(). If a NumberFormatException occurs, return 0.
	 * <li>for all other objects, longValue() is called with the toString()
	 * representation of the object.
	 * </ul>
	 *
	 * @param v
	 *            - some value object, can be null
	 * @return the int value represented by the object
	 */
	public long longValue(final Object v) {
		if (v == null) {
			return 0;
		}
		if (v instanceof Number) {
			return ((Number) v).longValue();
		}
		if (v instanceof String) {
			String s = (String) v;
			if (s.length() == 0) {
				return 0;
			}
			try {
				if (s.indexOf('.') >= 0) {
					return new BigDecimal(s).longValue();
				}
				return Long.parseLong(s);
			} catch (NumberFormatException e) {
				return 0;
			}
		}
		return this.longValue(v.toString());
	}

	/**
	 * Returns an 'Integer' object if the value in v is small enough to fit,
	 * otherwise a 'Long' object. Note: this downsizes Long objects!
	 *
	 * @param v
	 *            - some value, usually a Number
	 * @return null, an Integer or a Long object
	 */
	public Number intOrLongValue(final Object v) {
		if (v == null) {
			return null;
		}
		if (v instanceof Integer) {
			return (Number) v;
		}
		if (v instanceof Number) {
			long lv = ((Number) v).longValue();
			return (lv >= Integer.MIN_VALUE && lv <= Integer.MAX_VALUE) ? (int) lv : lv;
		}
		if (v instanceof String) {
			String s = ((String) v).trim();
			if (s.length() == 0) {
				return null;
			}
			long lv = this.longValue(s);
			return (lv >= Integer.MIN_VALUE && lv <= Integer.MAX_VALUE) ? (int) lv : lv;
		}
		return this.intOrLongValue(v.toString());
	}

	/**
	 * Returns true if the given object represents a discrete number, that is, a
	 * number w/o digits after the decimal point. (TBD: is discrete the correct
	 * word? I don't remember ;-))
	 *
	 * @param v
	 *            - some object, usually a Number
	 * @return true if the object is a discrete number, eg Integer or Long
	 */
	public boolean isDiscreteNumber(final Object v) {
		if (!(v instanceof Number)) {
			return false;
		}
		if (v instanceof Integer || v instanceof Long || v instanceof BigInteger || v instanceof Short || v instanceof Byte) {
			return true;
		}
		return false;
	}

	/**
	 * Returns a String representing the object. This has special processing for
	 * arrays, which are rendered using the stringValueForArray method.
	 *
	 * @param value
	 *            - value to convert to a String
	 * @return a String representing the object _value
	 */
	public String stringValue(final Object value) {
		if (value == null) {
			return null;
		}
		if (value instanceof String) {
			return String.valueOf(value);
		}
		if (value instanceof Object[]) {
			return this.stringValueForArray((Object[]) value);
		}
		return value.toString();
	}

	/**
	 *
	 * <p>
	 * This method will return string for the specified array.
	 * </p>
	 *
	 * <pre>
	 * AFObjectUtil.stringValueForArray(new String[]{"adya", "rajput"});
	 * result will be:
	 * ( adya, rajput )
	 * </pre>
	 *
	 * @param array
	 * @return String value by appendin all the elements in a string.
	 * @since Foundation 1.0
	 *
	 */
	public String stringValueForArray(final Object[] array) {
		if (array == null) {
			return null;
		}
		StringBuilder sb = new StringBuilder(array.length * 16);
		sb.append("( ");
		boolean isFirst = true;
		for(Object o : array) {
			String s = this.stringValue(o);
			if (s == null) {
				s = "[null]";
			}
			if (isFirst) {
				isFirst = false;
			} else {
				sb.append(", ");
			}
			sb.append(s);
		}
		sb.append(" )");
		return sb.toString();
	}

	/**
	 * Returns a java.util.Date for the given object. This method checks:
	 * <ul>
	 * <li>for null, which is returned as null
	 * <li>for Date, which is returned as-is
	 * <li>for java.util.Calendar, getTime() will be called and returned
	 * <li>for String's. Which will get parsed using the default DateFormat.
	 * <li>for Number's, which are treated like ms since 1970
	 * </ul>
	 * All other objects are checked for a 'dateValue' method, which is then
	 * called.
	 *
	 * @param value
	 *            - some object
	 *
	 * @return a java.util.Date or null
	 */
	public Date dateValue(final Object value) {
		return this.dateValue(value, DateFormat.SHORT, Locale.US);
	}

	public Date timeStampValue(final Object value) {
		return this.timeStampValue(value, DateFormat.SHORT, Locale.US);
	}

	/**
	 * Returns a java.util.Date for the given object. This method checks:
	 * <ul>
	 * <li>for null, which is returned as null
	 * <li>for Date, which is returned as-is
	 * <li>for java.util.Calendar, getTime() will be called and returned
	 * <li>for String's. Which will get parsed using the default DateFormat.
	 * <li>for Number's, which are treated like ms since 1970
	 * </ul>
	 * All other objects are checked for a 'dateValue' method, which is then
	 * called.
	 *
	 * @param value
	 *            - some object
	 * @param style
	 *            the given formatting style. For example, SHORT for "M/d/yy" in
	 *            the US locale.
	 * @param locale
	 *            the given locale.
	 *
	 * @return a java.util.Date or null
	 */
	public Date dateValue(final Object value, final int style, final Locale locale) {
		if (value == null) {
			return null;
		}
		if (value instanceof Date) {
			return (Date) value;
		}
		if (value instanceof Calendar) {
			return ((Calendar) value).getTime();
		}
		if (value instanceof String) {
			String s = ((String) value).trim();
			if (s.length() == 0) {
				return null;
			}
			/* Rhino hack */
			if (FNC.UN_DEFINED.equals(s)) {
				return null;
			}
			if (this.isValidLong(s)) {
				return new Date(Long.parseLong(s));
			}
			DateFormat df = DateFormat.getDateInstance(style, locale);
			try {
				return df.parse(s.replaceAll(FNC.UNDERSCORE, FNC.FWD_SLASH));
			} catch (Exception e) {
				throw new RuntimeException("Could not parse string as datevalue");
			}
		}
		if (value instanceof Number) {
			return new Date(((Number) value).longValue());
		}
		try {
			Method m = value.getClass().getMethod("dateValue");
			if (m != null) {
				Object v = m.invoke(value);
				if (v == null) {
					return null;
				}
				if (v != value) {
					return this.dateValue(v, style, locale);
				}
				return null;
			}
		} catch (Exception e) {
		}
		System.err.println("WARN: unexpected object in AFObject.dateValue(): " + value);
		return null;
	}

	public Timestamp timeStampValue(final Object val, final int style, final Locale locale) {
		if (val == null) {
			return null;
		}
		if (val instanceof Timestamp) {
			return (Timestamp) val;
		}
		if (val instanceof Calendar) {
			return new Timestamp(((Calendar) val).getTimeInMillis());
		}
		if (val instanceof String) {
			String s = ((String) val).trim();
			if (s.length() == 0) {
				return null;
			}
			/* Rhino hack */
			if (FNC.UN_DEFINED.equals(s)) {
				return null;
			}
			if (this.isValidLong(s)) {
				return new Timestamp(Long.parseLong(s));
			}
			DateFormat df = DateFormat.getDateTimeInstance(style, DateFormat.FULL, locale);
			try {
				return new Timestamp(df.getCalendar().getTimeInMillis());
			} catch (Exception e) {
				throw new RuntimeException("Could not parse string as datevalue");
			}
		}
		if (val instanceof Number) {
			return new Timestamp(((Number) val).longValue());
		}
		/* other object */
		try {
			Method m = val.getClass().getMethod("timeStampValue");
			if (m != null) {
				Object v = m.invoke(val);
				if (v == null) {
					return null;
				}
				if (v != val) {
					return this.timeStampValue(v, style, locale);
				}
				return null;
			}
		} catch (Exception e) {
		}
		System.err.println("WARN: unexpected object in AFObject.dateValue(): " + val);
		return null;
	}

	/**
	 * Checks whether a given object is considered 'empty'. All objects are
	 * considered non-empty except:
	 * <ul>
	 * <li>AFObject's are asked whether they are empty
	 * <li>Collections with a size of 0 are consider empty
	 * <li>Strings with only whitespace are consider empty (trim())
	 * <li>Empty arrays are considered empty
	 * </ul>
	 *
	 * @param v
	 *            - an arbitrary object
	 * @return true if the object is considered 'empty', false if not
	 */
	public boolean isEmpty(final Object v) {
		// Note: do not use parameter overloading, confuses Rhino with null values.
		if (v == null) {
			return true;
		}
		if (v instanceof String) {
			/* we trim for convenience, should be what one usually wants */
			final String s = ((String) v).trim();
			return s.length() == 0;
		}
		if (v instanceof Map) {
			return ((Map<?, ?>) v).size() == 0;
		}
		if (v instanceof Collection) {
			return ((Collection<?>) v).isEmpty();
		}
		if (v.getClass().isArray()) {
			return ((Object[]) v).length == 0;
		}
		return false;
	}

	public boolean boolValue(final Object v) {
		if (v == null) {
			return false;
		}
		if (v instanceof String) {
			if (FNC.EMPTYSTR.equals(v)) {
				return false;
			}
			String s = (String) v;
			char c0 = s.charAt(0);
			if ((c0 == 'N' || c0 == 'n') && (s.equalsIgnoreCase(FNC.NO) || s.equalsIgnoreCase(FNC.NULL_LC))) {
				return false;
			}
			if ((c0 == 'f' || c0 == 'F') && s.equalsIgnoreCase(FNC.FALSE_LC)) {
				return false;
			}
			if ((c0 == 'u' || c0 == 'U') && s.equalsIgnoreCase(FNC.UN_DEFINED)) {
				return false;
			}
			if (s.length() == 1) {
				if (c0 == '0' || c0 == ' ') {
					return false;
				}
			}
			return true;
		}
		if (v instanceof Boolean) {
			return ((Boolean) v).booleanValue();
		}
		if (v instanceof Number) {
			return ((Number) v).intValue() != 0;
		}
		if (v instanceof Collection) {
			return !((Collection<?>) v).isEmpty();
		}
		return true;
	}

	public boolean isNotEmpty(final Object v) {
		return !this.isEmpty(v);
	}

	public boolean isValidLong(final String v) {
		try {
			Long.parseLong(v);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

	static final String DOT = ".";
	static final String SLASH = "/";

	protected AFArrayList<String[]> parseKey2(String _keyString) {
		AFArrayList<String[]> returnKeyArrayofArray = new AFArrayList<>();
		String[] keySetArray = this.parseCSV(_keyString); //_keyString.split(" , ");//splits for different objects
		for(String element : keySetArray) {
			returnKeyArrayofArray.add(this.parseTilde(element)); //put String[] of keys
		}
		return returnKeyArrayofArray;
	}

	public String[] parseCSV(String keyString) {
		return (String[]) this.objectForKey(keyString);
	}

	protected synchronized Object objectForKey(String _keyString) {
		if (_keyString == null) {
			return null;
		}
		Object obj = null;
		if (obj == null) {
			try {
				obj = this.parseKey(_keyString); // sub class responsible --> Formaule Par, Csv --> Sort
			} catch (Exception e) {
				throw new RuntimeException("FNLRUHashFactory~objectForKey~ERROR:Exception in parsing");
			}
		}
		return obj;
	}

	protected synchronized Object objectForKey1(String _keyString) {
		if (_keyString == null) {
			return null;
		}
		Object obj = null;
		if (obj == null) {
			try {
				obj = this.parseKey1(_keyString); // sub class responsible --> Formaule Par, Csv --> Sort
			} catch (Exception e) {
				throw new RuntimeException("FNLRUHashFactory~objectForKey1~ERROR:Exception in parsing");
			}
		}
		return obj;
	}

	protected Object parseKey(String _keyString) {
		return _keyString.split(FNC.COMMA);
	}

	public String[] parseTilde(String keyString) {
		return (String[]) this.objectForKey1(keyString);
	}

	protected Object parseKey1(String _keyString) {
		return _keyString.split(FNC.TILDE);
	}
}
