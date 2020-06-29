package com.alfalahsoftech.web;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.alfalahsoftech.alframe.ajax.AFBaseReqRespObject;
import com.alfalahsoftech.alframe.factory.AFReqRespThreadFactory;

//@WebListener
public class AFWebReqListener extends AFObject implements ServletRequestListener {
	private static final String IS_REQ_RES_REQ = "isReqResObjCreated";
	public static final String SESS_OBJ = "sessionObject";
	public static final String RESTAPI = "restapi/";
	private static Set<String> urlsForReqResp;

	static {
		urlsForReqResp = new HashSet<>();
		urlsForReqResp.add("/launchDevice");
		urlsForReqResp.add("/rest/");
		urlsForReqResp.add("api");
		urlsForReqResp.add("apiInt");
		urlsForReqResp.add(RESTAPI);
		urlsForReqResp.add("restInt/");

	}

	@Override
	public void requestInitialized(ServletRequestEvent servReqEvent) {
		HttpServletRequest httpRequest = (HttpServletRequest) servReqEvent.getServletRequest();
		print("Rquest received::"+httpRequest.getRequestURI());
		if (this.doCreateReqResObject(httpRequest)) {
			AFBaseReqRespObject baseReqRespObject =  AFReqRespThreadFactory.factory().getReqRespObject();
			baseReqRespObject.setHttpRequestData(httpRequest);
			HttpSession  session = httpRequest.getSession(false);
			System.out.println("session=============>>>" +session);
			if (session != null) {
			AFBaseSessionObject sessionObject = (AFBaseSessionObject) session.getAttribute(SESS_OBJ);
				System.out.println("if session != null=>sessionObject ========>" +sessionObject);
				if (sessionObject != null) {
					sessionObject.uniqueID = session.getId();
				}
				System.out.println("if session != null=>session.getId();======= "+session.getId());
				baseReqRespObject.initSessionObject(sessionObject);
			}
		}
	}

	@Override
	public void requestDestroyed(ServletRequestEvent requestEvent) {
		
		HttpServletRequest request = (HttpServletRequest) requestEvent.getServletRequest();
		if (this.boolValue(request.getAttribute(IS_REQ_RES_REQ))) {
			AFReqRespThreadFactory.factory().removeReqRespObj();
		}
	}

	private boolean doCreateReqResObject(HttpServletRequest request) {
		String requestURI = request.getRequestURI();
		System.out.println("requestURI==============>>>>" +requestURI);
		if (requestURI != null && requestURI.trim().length() > 0 && this.doRequestContainsAnyUrl(requestURI)) {
			request.setAttribute(IS_REQ_RES_REQ, true);
			return true;
		}
		request.setAttribute(IS_REQ_RES_REQ, false);
		return false;
	}

	private boolean doRequestContainsAnyUrl(String requestURI) {
		for (String urlPattern : urlsForReqResp) {
			if (requestURI.contains(urlPattern)) {
				return true;
			}
		}
		return false;
	}

	public boolean boolValue(final Object v) {
		if (v == null) {
			return false;
		}
		if (v instanceof String) {
			if ("".equals(v)) {
				return false;
			}
			String s = (String) v;
			char c0 = s.charAt(0);
			if ((c0 == 'N' || c0 == 'n') && "NO".equalsIgnoreCase(s)) {
				return false;
			}
			if ((c0 == 'f' || c0 == 'F') && "false".equalsIgnoreCase(s)) {
				return false;
			}
			if (s.length() == 1 && (c0 == '0' || c0 == ' ')) {
				return false;
			}
			return true;
		} else if (v instanceof Boolean) {
			return ((Boolean) v).booleanValue();
		} else if (v instanceof Number) {
			return ((Number) v).intValue() != 0;
		}
		return true;
	}
}
