package com.alfalahsoftech.alframe.ajax;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("/ajax")
public class AFAjaxController extends AFBaseAjaxService {

	@POST
	@Path("/auth")
	public String handleAtuhRequest(String str) {
		System.out.println("########## Auth Ajax ");
		return super.handleReq(str, true);
	}

	@POST
	@Path("/unAuth")
	public String handleUnAuthRequest(String str) {
		return super.handleReq(str, false);
	}

	@POST
	@Path("/login")
	public String hanldeReqLogin(String str) {
		return super.handleReq(str, false);
	}
}
