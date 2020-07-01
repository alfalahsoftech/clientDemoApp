package com.alfalahsoftech.web;

import java.io.File;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONObject;

import com.alfalahsoftech.alframe.AFArrayList;
import com.alfalahsoftech.alframe.AFHashMap;
import com.alfalahsoftech.alframe.FNBaseSerialInterface;
import com.alfalahsoftech.alframe.FNIPropertyAccessor;
import com.alfalahsoftech.alframe.FNKVCWrapper;
import com.alfalahsoftech.alframe.FNKeyValueInterface;
import com.alfalahsoftech.alframe.FNMissingAccessorException;
import com.alfalahsoftech.alframe.FNMissingPropertyException;
import com.alfalahsoftech.alframe.FNObjectInterface;
import com.alfalahsoftech.alframe.ajax.AFBaseReqRespObject;
import com.alfalahsoftech.alframe.factory.AFAnnotationFactory;
import com.alfalahsoftech.alframe.factory.AFReqRespThreadFactory;
import com.alfalahsoftech.alframe.util.AFJsonParserUtil;
import com.alfalahsoftech.common.file.AFJsonParser;
import com.alfalahsoftech.inv.entity.AFMainEntity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

public abstract class AFObject implements FNBaseSerialInterface, FNObjectInterface, Serializable {

	
	public static final String CONTEXTPATH= AFWebContextListener.contextPath;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public boolean isEmptyStr(String _v) {
		return _v == null || _v.trim().length() == 0 || _v.equalsIgnoreCase("null");
	}
	public static final String PK= "primaryKey";
	// After deserialization this method will be called
	public void initObject() {
	}
	public void print(String str) {
		System.out.println(str);
	}
	public void printObj(Object obj) {
		System.out.println(obj);
	}
	public void printMsg(String msg, Object... vararg) {
		this.print(this.formatMsg(msg, vararg));
	}

	public String formatMsg(String msg, Object... vararg) {
		return String.format(msg, vararg);
	}

	public boolean isNonEmptyStr(String _v) {
		return !this.isEmptyStr(_v);
	}

	public AFBaseReqRespObject baseReqRespObject(String requestPacket) {
		return AFReqRespThreadFactory.factory().getReqRespObject(requestPacket);
	}

	public void appendAttributesToDescription(StringBuilder _d) {
		/* this is what should be overridden by subclasses */
	}

	@Override
	public void takeValuesFromDictionary(final Map<String, Object> _map) {
		if (_map == null) {
			return;
		}
		for(Entry<String, Object> entry : _map.entrySet()) {
			this.takeValueForKeyPath(entry.getValue(), entry.getKey());
		}
	}
	public Gson gson() {
		Gson gSon=  new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		return gSon;
	}
	public <T> T getObjFromStr(Class<T> t,String req){
		return (T)this.gson().fromJson(req, t);
	}


	/**
	 * <p>
	 * This method is used to take specified value associated with the specified
	 * key.
	 *
	 * <pre>
	 * e.g.FNObject.takeValueForKey(&quot;delhi&quot;, &quot;place&quot;);
	 * </pre>
	 *
	 * </p>
	 *
	 * @param _value
	 *            value associated with the specified key.
	 * @param _key
	 *            key associated with the specified value.
	 * @since Foundation 1.0
	 *
	 */
	@Override
	public void takeValueForKey(Object _value, String _key) {
		try { // : avoid this exception handler (COSTLY!)
			FNIPropertyAccessor accessor = FNKVCWrapper.forClass(this.getClass()).getAccessor(this, _key);
			if (accessor == null) {
				this.handleTakeValueForUnboundKey(_value, _key);
				return;
			}
			Class<?> type = accessor.getWriteType();
			if (_value != null && type != _value.getClass()) {
				_value = AFAnnotationFactory.factoryObj().castObject(_value, type);
			}
			accessor.set(this, _value);
		} catch (FNMissingPropertyException e) {
			this.handleTakeValueForUnboundKey(_value, _key);
			return;
		} catch (FNMissingAccessorException e) {
			/* this is when a setX method is missing (but a get is available) */
			this.handleTakeValueForUnboundSetKey(_value, _key);
			return;
		}
	}

	/**
	 * <p>
	 * This method is used to get value associated with the specified key.
	 * </p>
	 *
	 * @param key
	 *            for which associated value is to be found.
	 * @return value for specified key will be returned.
	 * @since Foundation 1.0
	 *
	 */
	@Override
	public Object valueForKey(final String key) {
		/*if (key.startsWith("currentDay")) {//currentDay+10,currentDay-10
			return FNDate.currentDate().valueForKey("offsetDay_" + key.substring(10));
		} else if (key.equals("userObject")) {
			return this.userObject();
		} else if (key.startsWith(EO_PK)) {//eoForPK_Person_5
			return this.findEOWithPK(key);
		} else if (key.startsWith(EO_UK)) {//eoForUK_Person^firstName~lastName_Sudheer~Verma
			return this.findEOWithUniqueKey(key);
		}*/
		try { // : avoid this exception handler
			final FNIPropertyAccessor accessor = FNKVCWrapper.forClass(this.getClass()).getAccessor(this, key);
			if (accessor == null) {
				return this.handleQueryWithUnboundKey(key);
			}
			return accessor.get(this);
		} catch (FNMissingPropertyException | FNMissingAccessorException e) {
			return this.handleQueryWithUnboundKey(key);
		}
	}

	/**
	 * <p>
	 * To associate value with key path.
	 * </p>
	 *
	 * <pre>
	 * store = {milk={price = 32};};
	 * </pre>
	 *
	 * Where, store is an object of Store class. store.milk.price is the
	 * keyPath.
	 *
	 * @param value
	 *            which is associated to the keypath.
	 * @param _keypath
	 *            to which value is to be associated.
	 * @since Foundation 1.0
	 *
	 */
	@Override
	public void takeValueForKeyPath(final Object value, final String keyPath) {
		FNKeyValueInterface.DefaultImplementation.takeValueForKeyPath(this, value, keyPath);
	}

	/**
	 * <p>
	 * To associate value with key path.
	 * </p>
	 *
	 * <pre>
	 * store = {milk = {price = 32}};
	 * </pre>
	 *
	 * Where, store is an object of Store class. keyPath = store.milk.price.
	 * value can be retrived by specifying the keypath.
	 *
	 * @param _keypath
	 *            keypath of which value is to be found.
	 * @return value which is associated with the keypath.
	 * @since Foundation 1.0
	 *
	 */
	@Override
	public Object valueForKeyPath(final String keyPath) {
		return FNKeyValueInterface.DefaultImplementation.valueForKeyPath(this, keyPath);
	}

	/**
	 * Calls valueForKey() for each key in the array. If there is no value for
	 * the given key (method returned 'null'), we do NOT add the value to the
	 * Map.
	 * <p>
	 * If the key array is empty, we still return an empty map. If the key array
	 * is null, we return null.
	 *
	 * @param keys
	 *            - keys to be extracted
	 * @return a Map containing the values for the keys, null if _keys is null
	 */
	@Override
	public Map<String, Object> valuesForKeys(final String[] keys) {
		if (keys == null) {
			return null;
		}
		final Map<String, Object> vals = new HashMap<>(keys.length);
		if (keys.length == 0) {
			return vals;
		}
		for(String _key : keys) {
			Object v = this.valueForKey(_key);
			if (v != null) {
				vals.put(_key, v);
			}
		}
		return vals;
	}

	@Override
	public void handleTakeValueForUnboundKey(Object _value, String _key) {
		
		throw new RuntimeException("handleTakeValueForUnboundKey~NoSuchKey:" + _key+ "_key~className"+ this.getClass().getName()+"  _value:  "+_value);//FNUnboundKeyException(logger, FNLogLevel.TRACE, this.getReqRespObject(), "FNObject~handleTakeValueForUnboundKey~NoSuchKey:" + this.getClass().getName() + ":" + _key, this, "_key~className", null, _key, this.getClass().getName());
	}

	public void handleTakeValueForUnboundSetKey(Object _value, String _key) {
		throw new RuntimeException("");//FNUnboundKeyException(logger, FNLogLevel.TRACE, this.getReqRespObject(), "FNObject~handleTakeValueForUnboundKey~NoSetMethod:" + this.getClass().getName() + ":" + _key, this, "_key~className", null, _key, this.getClass().getName());

	}

	/**
	 * <p>
	 * when this method is called <code>FNException</code> is thrown.
	 * </p>
	 *
	 * @param key
	 *            key
	 * @exception FNException
	 *                this exception is thrown if this method is called.
	 * @since Foundation 1.0
	 *
	 */
	@Override
	public Object handleQueryWithUnboundKey(String key) {
		throw new RuntimeException("FNObject~handleQueryWithUnboundKey~NoSuchKey:");//(logger, FNLogLevel.TRACE, this.getReqRespObject(), "FNObject~handleQueryWithUnboundKey~NoSuchKey:" + this.getClass().getName() + ":" + key, this, "_key~className", null, key, this.getClass().getName());
	}

	@Override
	public <T> T createObjectKeyArg(Class<T> classObject, String destKey, String argKey, Object... objects) {
		return null;
	}

	@Override
	public Object objectMap() {
		return null;
	}

	@Override
	public Object objectMap(String serialID) {
		return null;
	}
	public AFBaseReqRespObject reqRespObject() {
		return AFReqRespThreadFactory.factory().getReqRespObject();
	}

	public <T> T updateObject(Class<T> t,String reqStr) {
		AFHashMap<String, Object> map =AFJsonParserUtil.toMap(new JSONObject(reqStr));
		printObj(map);
		printObj(map.get(PK)==null?"Primarykey  not found":map.get(PK));
		List<T> list = this.reqRespObject().reqEM().createQuery("SELECT e FROM "+t.getSimpleName()+" e where primaryKey="+map.get(PK),t).getResultList();
		if (list == null || list.size() == 0) {
			return null;
		}
		final T  obj = (T)list.get(0);
		map.remove(PK);
		AFMainEntity entity = (AFMainEntity)obj;
		map.keySet().forEach(key->{

			print("KEY: "+key);
			print(" VALUE:  "+map.get(key));
			entity.takeValueForKey(map.get(key),key);
		});
		this.reqRespObject().startTransaction();
		this.reqRespObject().reqEM().merge(entity);
		this.reqRespObject().endTransaction();
		return (T)entity;
	}

	public Date formatedDate(String format, String dateStr) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format==null?"yyyy-MM-dd":format);
		try {
			return dateFormat.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	public Date formatedDate(String dateStr) {
		return formatedDate(null,dateStr);
	}

	@SuppressWarnings("rawtypes")
	public List getObjects(Class cls){
		return this.getObjects(cls, null);
	}
	@SuppressWarnings("rawtypes")
	public List getObjects(Class cls,String whereClause){
		List list= null;
		if(isEmptyStr(whereClause))
			list = this.reqRespObject().reqEM().createQuery("Select e From "+cls.getSimpleName()+" e").getResultList();
		else
			list = this.reqRespObject().reqEM().createQuery("Select e From "+cls.getSimpleName()+" e where "+whereClause).getResultList();
		return list;
	}
	

	public <T> AFArrayList<T> afArrayList(Collection<T> list) {
		AFArrayList<T> listObj = new AFArrayList<>();
		for (T t : list) {
			listObj.add(t);
		}
		return listObj;

	}

	////////////////////////////////////////////// Utility methods


	public static AFHashMap<Object, AFObject> getHashForKey(String key,AFArrayList list){
		AFHashMap<Object, AFObject>  afHashMap = new AFHashMap();
		ListIterator<Object>  it= list.listIterator();
		while(it.hasNext()) {
			AFObject eo=(AFObject)it.next();
			afHashMap.put(eo.valueForKey(key), eo);
		}
		return afHashMap;
	}

	public String getOrderDays(String req) {
		AFHashMap<String,Object> reqMap = AFJsonParserUtil.toMap(new JSONObject(req));

		StringBuffer ordDays = new StringBuffer();
		if(reqMap.boolValue("sun")) {
			ordDays.append("SUN ");
		}
		if(reqMap.boolValue("mon")) {
			ordDays.append("MON ");
		}
		if(reqMap.boolValue("tue")) {
			ordDays.append("TUE ");
		}
		if(reqMap.boolValue("wed")) {
			ordDays.append("WED ");
		}
		if(reqMap.boolValue("thu")) {
			ordDays.append("THU ");
		} 
		if(reqMap.boolValue("fri")) {
			ordDays.append("FRI ");
		}
		if(reqMap.boolValue("sat")) {
			ordDays.append("SAT" );
		}
		return ordDays.toString();
	}

	public String getGsonString(Object obj) {
		return this.gson().toJson(obj);
	}
	
	public JSONObject jsonObject(String obj) {
		JSONObject jsonObj=new JSONObject(obj);
		return jsonObj;
	}
	public <T>  T   objectFromJsonFile(File file, Class<T> cls) {
		return AFJsonParser.parseFile(file, cls);
	}

}
