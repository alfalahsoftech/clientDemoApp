package com.alfalahsoftech.alframe.ajax;

import org.json.JSONObject;

import com.alfalahsoftech.alframe.AFHashMap;
import com.alfalahsoftech.alframe.annotation.AFControllerSetup;
import com.alfalahsoftech.alframe.factory.AFAnnotationFactory;
import com.alfalahsoftech.controller.AFBaseController;
import com.alfalahsoftech.web.AFObject;

public class AFBaseAjaxService extends AFObject {
	//	private AFRequestObject request;
	protected AFBaseReqRespObject baseReqRespObject;

	public String handleReq(String str, boolean isAuth) {
		this.baseReqRespObject = this.baseReqRespObject(str);
		//				AFAnnotationFactory.factoryObj().objectForClass()
		return this.processRequest(str);
	}

	public String processRequest(String req) {
		AFHashMap<String, Object> qryMap = this.baseReqRespObject.request().queryParamHash();
		String actionID = qryMap.stringValue("actionID");
		System.out.println("Request For ID = " + actionID);
		AFControllerSetup setup = AFAnnotationFactory.factoryObj().ctrlSetupForID(actionID);
		String finalResponse = null;
		if (setup == null) {
			throw new RuntimeException(" Invalid action ID");
		} else {
			AFBaseController obj = (AFBaseController) AFAnnotationFactory.factoryObj().createAndLoad(setup.controllerName);
			obj.addResponse();
			finalResponse = this.jsonObject(obj).toString();
			System.out.println("\t Final Response  =============>>>>> ");
			System.out.println(finalResponse);
			

		}

		return finalResponse;
	}

	public AFRequestObject getRequest() {
		return this.baseReqRespObject.request();
	}

	public JSONObject jsonObject(Object obj) {
		try {
			return new JSONObject(obj);
		} catch (Exception e) {
			return null;
		}
	}
}
