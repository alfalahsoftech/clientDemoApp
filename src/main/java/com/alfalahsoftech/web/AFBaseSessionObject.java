package com.alfalahsoftech.web;

import java.sql.Timestamp;

import javax.servlet.http.HttpSession;

import com.alfalahsoftech.alframe.AFUser;


public class AFBaseSessionObject extends AFObject {
	private static final long serialVersionUID = 1L;
	public static int sessionNumberGen = 0;
	public static int activeSessionNumber = 0;
	public static int activeAuthWebSessionNumber = 0;

	public int sessionNumber;
	public int transcNumber;
	public Object uniqueID;
	public boolean isAuthenticated;
	public boolean isSupportUserAuthenticated;
	public AFUser sessionUser;

	public Timestamp loggedInTime;
	public Timestamp lastAccessTime;
	public String remoteAddress;
	public String remoteHost;
	public String remoteString;
	public HttpSession session;
	public String redirectPath;
	public boolean isCookieBasedLogin;
	public boolean isNonUserDeviceLogin;// it will distinguingh if device session is like for StandAlone Device
	public boolean isWebBasedLogin;//true if logged in from web app
	public boolean isSessionStaled;
}
