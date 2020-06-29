package com.alfalahsoftech.inv.entity;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.alfalahsoftech.alframe.AFHashMap;
import com.alfalahsoftech.alframe.FNIPropertyAccessor;
import com.alfalahsoftech.alframe.FNKVCWrapper;
import com.alfalahsoftech.alframe.FNMissingAccessorException;
import com.alfalahsoftech.alframe.FNMissingPropertyException;
import com.alfalahsoftech.alframe.ajax.AFBaseReqRespObject;
import com.alfalahsoftech.alframe.factory.AFAnnotationFactory;
import com.alfalahsoftech.alframe.factory.AFReqRespThreadFactory;
import com.alfalahsoftech.web.AFObject;

@MappedSuperclass
public abstract class AFMainEntity extends AFObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5448457619922238232L;
//	@Id
//	@GeneratedValue
	//private Long primaryKey;

	public abstract Long primaryKey();

	public void setPrimaryKey(Long primaryKey) {
		try {
			Field e = this.getClass().getDeclaredField("primaryKey");
			e.setAccessible(true);
			e.set(this, primaryKey);
		} catch (IllegalAccessException | NoSuchFieldException | SecurityException | IllegalArgumentException arg2) {
			/*	this.handleTakeValueForUnboundKey(primaryKey, "primaryKey");
				FNExceptionUtil.logException(arg2);*/
		}

	}

	/*public void setPrimaryKey(long primaryKey) {
		this.primaryKey = primaryKey;
	}
	*/
	public void takeValueForKey(Object _value, String _key) {
		super.takeValueForKey(_value, _key);
	}
	
	public Object valueForKey(String key) {
		return super.valueForKey(key);
	}
	
	public void takeValueForKey1(Object obj, String key) {
		Field field = null;
		Class o = null;
		try {
			o = Class.forName(this.getClass().getName());
			field = o.getDeclaredField(key);
			System.out.println("filed=== " + field.getName());
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (NoSuchFieldException exception) {
			exception.printStackTrace();
		}

		// MZ: Find the correct method
		for(Method method : o.getMethods()) {
			if ((method.getName().startsWith("set")) && (method.getName().length() == (field.getName().length() + 3))) {
				if (method.getName().toLowerCase().endsWith(field.getName().toLowerCase())) {
					
					try {
						System.out.println("this.takeValueFor........" + this.getClass().getName());
						field.setAccessible(true);

						if (field.getType().getName().equals("java.lang.Integer")) {
							field.setInt(this, Integer.parseInt(obj.toString()));

						} else if (field.getType().getName().equals("java.lang.Long")) {
							System.out.println("1111111");
							Long LVal= Long.parseLong(obj.toString());
							Long  lg = new Long(34l);
							 field.setLong(this, lg);
							 
							 
							
						}else if (field.getType().getName().equals("java.lang.Double")) {
							Double DVal= Double.parseDouble(obj.toString());
							field.setDouble(this, new Double(DVal));
						}
						else if (field.getType().getName().equals("java.lang.String")) {
							field.set(this, obj);
						}

					} catch (Exception e) {
						System.out.println("----------------------exception---------------");
						e.printStackTrace();
						//Logger.getLogger(this.getClass()).fine("Could not determine method: " + method.getName());
					}

				}
			}
		}
	}

	
	public Object valueForKey1(String key) {
		try {
			String firstChar = key.substring(0, 1).toUpperCase();
			key = key.substring(1);
			key = firstChar + key;

			Method method = this.getClass().getDeclaredMethod("get" + key, null);
			System.out.println("ClassName: " + this.getClass().getName());
			return method.invoke(this, null);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	
	
//	public AFHashMap<String, Object> objectAsMap() {
//		return null;
//	}
//
//	public AFHashMap<String, Object> objectAsMap(String serialIID) {
//		return null;
//	}
//	
//	public AFBaseReqRespObject reqRespObject() {
//		return AFReqRespThreadFactory.factory().getReqRespObject();
//	}

}
