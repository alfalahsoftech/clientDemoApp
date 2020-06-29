package com.alfalahsoftech.web;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.alfalahsoftech.alframe.ajax.AFBaseReqRespObject;

public class AFWBSessionObjectFactory extends AFObject {

	private static AFWBSessionObjectFactory factory;
	
	
	public static AFWBSessionObjectFactory factory() {
		if(factory == null) {
			synchronized (AFWBSessionObjectFactory.class) {
				if(factory == null) {
					factory = new AFWBSessionObjectFactory();
				}
			}
		}
		return factory;
	}
	
	private ConcurrentHashMap<Object, AFBaseSessionObject> sessionObjectMap = new ConcurrentHashMap<>();
	private ConcurrentHashMap<Object, ArrayList<AFBaseSessionObject>> sessionObjectArrayMap = new ConcurrentHashMap<>();

	
	public AFBaseSessionObject createSession(HttpSession session, HttpServletRequest httpRequest) {
		AFBaseSessionObject sessionObject = new AFBaseSessionObject();//this.objectUtil().objectForClass(sessionObjectClassName);
		sessionObject.session = session;
		sessionObject.uniqueID = session.getId();
		sessionObject.remoteAddress = httpRequest.getHeader(AFBaseReqRespObject.X_FWD);
		if (sessionObject.remoteAddress == null) {
			sessionObject.remoteAddress = httpRequest.getRemoteAddr();
		}
		this.sessionObjectMap.put(sessionObject.uniqueID, sessionObject);
		session.setAttribute(AFWebReqListener.SESS_OBJ, sessionObject);
		return sessionObject;
	}
}
