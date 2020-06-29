package com.alfalahsoftech.web;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.alfalahsoftech.alframe.AFHashMap;
import com.alfalahsoftech.controller.AFBaseController;
import com.alfalahsoftech.inv.entity.EOUser;
import com.alfalahsoftech.service.UserService;
import com.alfalahsoftech.service.UserSrvcImpl;

@Path("/login")
public class LoginLogoutController extends AFBaseController{
	static UserService userSrvc = new UserSrvcImpl();

	@SuppressWarnings("unchecked")
	@Path("")
	@POST
	@Produces({ MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response  userLogin(String req) {
		System.out.println("Login successful=============>"+req);
		User user = this.getObjFromStr(User.class, req);
		try {
			List<EOUser> 	userList = this.reqRespObject().reqEM().createQuery("select e from EOUser e where userName='"+user.userName +""
					+ "' and userPassword='"+user.password+"'").getResultList();

			printObj(this.gson().toJson(userList));
			if(userList!=null && userList.size()==1) {
				user.isValidUser="Yes";
			}else {
				user.isValidUser="No";
			}
			user.menuIID="SuperUser";
			user.checkAllow();
		} catch (Exception e) {
			e.printStackTrace();

		}


		return this.createResponse(user);	
	}

	@Override
	public Object callMethod(AFHashMap<String, Object> objectHash) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addResponse() {
		// TODO Auto-generated method stub

	}
}

class User{
	String userName;
	String password;
	String isValidUser;
	String menuIID;
	Boolean isSettingsAllowed;
	String landingPage;
	void checkAllow() {
		if(this.menuIID.equalsIgnoreCase("SuperUser")) {
			this.isSettingsAllowed=true;
		}
	}

}
