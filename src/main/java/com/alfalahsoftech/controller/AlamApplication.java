package com.alfalahsoftech.controller;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.glassfish.jersey.server.ResourceConfig;

import com.alfalahsoftech.alframe.AFHashMap;
import com.alfalahsoftech.alframe.ajax.AFAjaxController;
import com.alfalahsoftech.alframe.security.AFRequestFilter;
import com.alfalahsoftech.common.controller.DashBoard;
import com.alfalahsoftech.common.file.FileService;
import com.alfalahsoftech.common.file.PdfService;
import com.alfalahsoftech.exception.AFExceptionMapper;
import com.alfalahsoftech.fd.controller.FDOrderController;
import com.alfalahsoftech.fd.controller.FDSettingsController;
import com.alfalahsoftech.fd.controller.FoodBaseController;
import com.alfalahsoftech.web.AFWBFirstPageFilter;
import com.alfalahsoftech.web.AFWBSecurityFilter;
import com.alfalahsoftech.web.AFWebReqListener;
import com.alfalahsoftech.web.LoginLogoutController;

//if this is commented then use web.xml configuration
//if you use this then add all resources class and also class annotated with @Provider
//if you use web.xml & you must provide package name in which all classes is to be scanned
/*@ApplicationPath("/rest")
public class AlamApplication extends Application {

	@Override
	public Set<Class<?>> getClasses() {
		System.out.println(" =======> AlamApplication  is going to load... ");
		Set<Class<?>> classes = new HashSet<>();
		classes.add(UserController.class);
		classes.add(ParamController.class);
		classes.add(AFExceptionMapper.class);
		classes.add(FileService.class);
		classes.add(WidgetsResource.class);
		classes.add(AFAjaxController.class);
		classes.add(AFRequestFilter.class);
		classes.add(AFWebReqListener.class);
		return classes;
	}

}*/

@ApplicationPath("/rest")
public class AlamApplication extends Application {

	@Override
	public Set<Class<?>> getClasses() {
		System.out.println(" =======> AlamApplication  is going to load... ");
		
		Set<Class<?>> classes = new HashSet<>();
		//Controller Start
		classes.add(UserController.class);
		classes.add(ParamController.class);
		classes.add(AFAjaxController.class);
		classes.add(LoginLogoutController.class);
		classes.add(FDSettingsController.class);
		classes.add(FDOrderController.class);
		//Controller End
	
		//Filter Start
		//classes.add(AFWBSecurityFilter.class);
		//classes.add(AFWBFirstPageFilter.class);
		//Filter End
		classes.add(AFExceptionMapper.class);
		classes.add(FileService.class);
		classes.add(PdfService.class);
		classes.add(DashBoard.class);
		return classes;
	}
	@Override
	public Map<String, Object> getProperties() {
		AFHashMap<String, Object> map = new AFHashMap<>();
		map.put("defaultUser", "samdani");
		map.put("defaultPassword", "samdani");
		return map;
	}

	@Override
	public Set<Object> getSingletons() {
		/*
		 * 
	java.lang.UnsupportedOperationException
		at java.util.AbstractCollection.add(AbstractCollection.java:262)
	Above exception thrown when uncomment below code
		 */
		Set<Object> singltonObjects = new HashSet<>();
		singltonObjects.add(WidgetsResource.class);
		return singltonObjects;
	}

}

/*@ApplicationPath("/rest")
public class AlamApplication extends Application {

	//left Empty

  	It is also stated in the JAX-RS spec that if there are empty sets returned in the getClasses() and getSingletons(),
   implicit package scanning should occur.
   (provider) Classes annotated withe @Provider will by default be added as singletons and
    a resource classes annotated with @Path will be per-request objects (meaning a new object is created each request).



}
 */