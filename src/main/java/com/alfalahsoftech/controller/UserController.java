/**
 * 
 */
package com.alfalahsoftech.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.hibernate.Session;
import org.json.JSONObject;

import com.alfalahsoftech.alframe.AFHashMap;
import com.alfalahsoftech.alframe.util.AFJsonParserUtil;
import com.alfalahsoftech.entity.User;
import com.alfalahsoftech.inv.entity.AFMainEntity;
import com.alfalahsoftech.inv.entity.EOClient;
import com.alfalahsoftech.inv.entity.EOItem;
import com.alfalahsoftech.inv.entity.EORoute;
import com.alfalahsoftech.inv.entity.EOStaff;
import com.alfalahsoftech.inv.entity.EOStore;
import com.alfalahsoftech.inv.entity.EOUser;
import com.alfalahsoftech.service.UserService;
import com.alfalahsoftech.service.UserSrvcImpl;
import com.alfalahsoftech.ui.object.EOStaffUIO;
import com.google.gson.Gson;


/**
 * @author malam
 * @date Sep 25, 2016
 */
@Path("UserService")
public class UserController extends AFBaseController {

	/*	@Context
		UriInfo uri;//this gets injected after the class is instantiated by Jersey

		 @Path("a/b")
		  @GET
		  public Response method1(){
		    return Response.ok("blah blah").build();
		  }

		 @Path("a/b/c")
		  @GET
		  public Response method2(){
		    UriBuilder addressBuilder = uri.getBaseUriBuilder();
		    addressBuilder.path("a/b");
		    return Response.seeOther(addressBuilder.build()).build();
		  }


		public UserController(@Context Object context,@PathParam("") String kk) {
			System.out.println("sdjfsdjbfsdjfbdsjfbdsjbf"+this);
			System.out.println(context);
		}
		public UserController(@QueryParam(value="name") String h) {
			System.out.println("6666666666666666666 "+h);
		}*/
	//	static UserService userSrvc = new UserSrvcImpl(); 
	UserService userSrvc = new UserSrvcImpl(); //removed static keyword
	@GET
	@Path("/cli")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getClientDetails() {
		//		java.util.List<LKClient> clientList=userSrvc.getClientDetails();
		java.util.List<EOClient> clientList	=userSrvc.getClientDetails();
		java.util.List list= new ArrayList();
		clientList.stream().forEach(eo->{
			list.add(eo.getUIEOClient());
		});
		return createResponse(list);
	}



	@POST
	@Path("/cli")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response putClientDetails( String req) {
		print("REeeeeeeeee= " +req);
		Map<String, Object>	 map=new JSONObject(req).toMap();
		int pk=(int)	map.remove("eoRoute");

		print("############# PK has been removed = "+pk);
		String  modifiedReq = new JSONObject(map).toString();
		print("modifiedReq============!!!==========>" +modifiedReq);
		EOClient lkclient = getObjFromStr(EOClient.class, modifiedReq);
		Session ses = this.getSession();
		//EORoute route=this.reqRespObjec().reqEM().getEntityManager().find(EORoute.class, Long.valueOf(pk));
		//No need to fetch as above simply create an object
		EORoute rt= new EORoute();
		rt.setPrimaryKey(Long.valueOf(pk));
		lkclient.takeValueForKey(this.getOrderDays(req), "orderDays");
		lkclient.setEoRoute(rt);
		long primaryKey =this.saveObject(lkclient);
		lkclient.setClientID(this.getUniqueID(primaryKey,"****"));
		ses.update(lkclient);
		this.commit();

		//userSrvc.insertEntity(lkclient);
		//this.reqRespObject().endTransaction(lkclient);
		System.out.println("EOClient=== " +lkclient.getState());
		java.util.List<EOClient> clientList=userSrvc.getClientDetails();
		java.util.List list= new ArrayList();
		clientList.stream().forEach(eo->{
			list.add(eo.getUIEOClient());
		});

		return createResponse(list);
	}

	@POST
	@Path("/updateCli")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public Response updateClient(String req) {
		print("req--->>>> "+req);	
		EOClient  cli = updateObject(EOClient.class,req);
		this.reqRespObject().startTransaction();
		cli.takeValueForKey(this.getOrderDays(req), "orderDays");
		this.reqRespObject().endTransaction();
		return this.createResponse(cli.getUIEOClient());
	}
	public void doTransaction() {
		this.reqRespObject().startTransaction();

		this.reqRespObject().endTransaction();
	}

	@Path("/cust/{custPK}")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSingleCustomer(@PathParam("custPK") String pk) {
		EOClient client =this.reqRespObject().reqEM().getUniqueResult(EOClient.class,"SELECT e FROM EOClient e WHERE primaryKey="+pk);
		printObj(client);
		return this.createResponse(client);
	}



	@POST
	@Path("/allUsers")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getUsers() {
		System.out.println("11111111111 getUsers ");
		List<EOUser> userList = userSrvc.findAllUsers();
		print("Users record===============> "+userList);
		return this.createResponse(userList);
	}

	@GET
	@Path("/msg")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getMessage() {
		System.out.println("11111111111 getUsers ");
		return this.createResponse("{msg:Hi this new message}");
	}

	@GET
	@Path("/user/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getUserById(@PathParam("id") String id) {
		System.out.println("22222222222222");
		EOUser user = userSrvc.findUserById(Integer.valueOf(id));
		System.out.println("User.name = " + user.getFirstName());
		return this.createResponse(user);
	}

	@POST
	@Path("/addUser")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response addUser(String user) {
		System.out.println("333333333333333" + user);
		EOUser usr = this.getObjFromStr(EOUser.class,user);
		this.reqRespObject().startTransaction();
		boolean isUserAdded = userSrvc.addUser(usr);
		this.reqRespObject().endTransaction();
		System.out.println("User.name = " + usr.getFirstName() + "   isUserAdded= " + isUserAdded);

		return this.createResponse(usr);
	}

	@POST
	@Path("/updUser")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response updateUser(String userJson) {
		System.out.println("444444444444" + userJson);

		EOUser usr =updateObject(EOUser.class,userJson);

		System.out.println("User.name = " + usr.getFirstName());
		//String str = g.toJson(user);
		return this.createResponse(usr);
	}


	//////////////////////////////Staff
	@POST
	@Path("/addStaff")
	@Produces({MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_JSON})
	public Response addStaffs(String reqStr){
		EOStaff staff = null;
		try {
			Map<String, Object> map = this.jsonObject(reqStr).toMap();
		
			int pk=(int)	map.remove("eoStore");
			print( new JSONObject(map).toString());
			 staff = this.getObjFromStr(EOStaff.class	, new JSONObject(map).toString());
			EOStore store  = new EOStore();
			store.setPrimaryKey(Long.valueOf(pk));
			staff.setEoStore(store);
			this.saveObject(staff);
			this.commit();
			//userSrvc.insertEntity(staff);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		return this.createResponse(staff);
	}

	@POST
	@Path("/editStaff")
	@Produces({MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_JSON})
	public Response editStaffs(String reqStr){
		EOStaff staff =updateObject(EOStaff.class,reqStr);
		print(staff.getFirstName()+" updated!!!");
		return this.createResponse(staff);
	}

	@POST
	@Path("/dispStaff")
	@Produces({MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_JSON})
	public Response allStaffs(){
		print("disp staff");
		List<AFMainEntity> entity = this.userSrvc.getData(EOStaff.class);
		List<EOStaffUIO> staffList = new  ArrayList<>();
		entity.forEach(staff->{
			staffList.add(new EOStaffUIO((EOStaff)staff));
		});
		return this.createResponse(staffList);
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
