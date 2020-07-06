package com.alfalahsoftech.web;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Enumeration;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.json.JSONException;
import org.json.JSONObject;

import com.alfalahsoftech.common.file.AFJsonParser;

@WebListener
public class AFWebContextListener implements ServletContextListener {
 public static String contextPath=null;
 public static JSONObject appConfig;
	@Override
	public void contextInitialized(ServletContextEvent contextEvent) {
		System.out.println("############  contextInitialization started  ###########");
		contextPath = contextEvent.getServletContext().getRealPath("/");
		String meta =  contextEvent.getServletContext().getRealPath("META-INF");
		System.out.println("MMMMMMMMMMMMMMM= META="+meta);
		System.out.println("AFWebContextListener.contextPath = " + contextPath);
		this.startApplication(contextEvent);
	}

	@Override
	public void contextDestroyed(ServletContextEvent contextEvent) {
		//		FNApplicationObject.factory().closeApplication();
	}

	private void startApplication(ServletContextEvent contextEvent) {
		System.out.println("################ startApplication called ##############");
		AFApplicationObject.executeFactory();
		try {
			appConfig = new JSONObject(AFJsonParser.jsonToSingleString(new FileReader(new File(AFApplicationObject.META_PATH+"glbDir/config/appConfig.json"))));
		} catch (JSONException | FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		AFApplicationObject.factoryObj().servletContext=contextEvent.getServletContext();

		//	FNApplicationObject.factory().loadStartUpConfig();
		Enumeration<String> contextParams = contextEvent.getServletContext().getInitParameterNames();
		while (contextParams.hasMoreElements()) {
			String contextParam = contextParams.nextElement();
			AFApplicationObject.argMap.put(contextParam, contextEvent.getServletContext().getInitParameter(contextParam));
		}
		AFApplicationObject.factoryObj().startApplication();
	//	AFApplicationObject.factoryObj().loadData();
		HitOtherApplication.hitApp();
	}

}
