package com.alfalahsoftech.web;

import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletContext;

import com.alfalahsoftech.alframe.factory.AFAnnotationFactory;
import com.alfalahsoftech.alframe.factory.AFMenuItemFactory;
import com.alfalahsoftech.alframe.persistence.PSEntityManagerFactory;
import com.alfalahsoftech.alframe.persistence.PSSchema;
import com.alfalahsoftech.alframe.setup.AFGlobalSetup;

public class AFApplicationObject extends AFObject {
	
	public ServletContext servletContext;
	private String appName;
	private String contextName;
	public static String META_PATH=AFWebContextListener.contextPath+"WEB-INF/classes/META-INF/";
	public static  ConcurrentHashMap<String,Object> argMap;
	private static volatile AFApplicationObject factoryObj;

	public static void executeFactory() {
		if (factoryObj == null) {
			synchronized (AFMenuItemFactory.class) {
				if (factoryObj == null) {
					factoryObj = new AFApplicationObject();
					//factoryObj.loadData();
				}
			}
		}
	}

	public static AFApplicationObject factoryObj() {
		return factoryObj;
	}

	public void loadData() {
		AFAnnotationFactory.executeFactory();
		//AFGlobalSetup.flags().initObject();
		printObj("##########isSettingAccess==>"+AFGlobalSetup.flags().isSettingAccess);
	}
	
	public void startApplication() {
		connectToSchema();
	}
	

	private String appRootDirPath;

	public String appRootDirPath() {

		return this.appRootDirPath;
	}

	public ServletContext getAppContext() {
		return this.servletContext;
	}
	
	public void  connectToSchema() {
		PSSchema psSchema = new PSSchema();
		psSchema.createNewEntityManager();
		PSEntityManagerFactory.factory().addSchemaAndResetStatus(psSchema);
		//PSEntityManager psEntityMgr = new PSEntityManager(psSchema.createNewEntityManager(), psSchema);
//		AFBaseReqRespObject
	}

}
