package com.alfalahsoftech.fd.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.hibernate.Session;
import org.json.JSONArray;
import org.json.JSONObject;

import com.alfalahsoftech.alframe.AFArrayList;
import com.alfalahsoftech.alframe.AFHashMap;
import com.alfalahsoftech.alframe.annotation.AFController;
import com.alfalahsoftech.alframe.annotation.ReqMthdDgntr;
import com.alfalahsoftech.alframe.util.AFJsonParserUtil;
import com.alfalahsoftech.inv.entity.EOClient;
import com.alfalahsoftech.inv.entity.EOStore;
import com.alfalahsoftech.inv.entity.LKJobCode;
import com.google.gson.Gson;

@AFController
@Path("/settings")
public class FDSettingsController extends FoodBaseController {

	@ReqMthdDgntr(id = "/cust")
	public String customerDetails() {
		ArrayList<EOClient> list = new ArrayList<>();
		EOClient cli = new EOClient();
		cli.setClientName("Mahboob");
		cli.setCity("Aurangabad");
		cli.setAddress("A-08");
		cli.setClientID("CUST_000001");
		cli.setContactNo("1234567890");
		cli.setDistrict("Auranabad");
		cli.setEmrgContactNo("987654321");
		list.add(cli);
		Gson g = new Gson();

		return g.toJson(list);
	}

	@POST
	@Path("/dispStore")
	@Produces(value= {MediaType.APPLICATION_JSON})
	
	public Response storeDetails() {
		print("sotre detail called");
		List storeList = this.getAllData(EOStore.class);
		return this.createResponse(storeList);
	}
	
	@POST
	@Path("/addStore")
	@Produces(value= {MediaType.APPLICATION_JSON})
	@Consumes(value= {MediaType.APPLICATION_JSON})
	public Response addStore(String req) {
		print("Saving store");
		EOStore store=	this.getObjFromStr(EOStore.class, req);
		this.saveObject(store);
		this.commit();
		return this.createResponse("success!!");
	}
	
	
	////Lookup
	

	@POST
	@Path("/addJobCode")
	@Produces(value= {MediaType.APPLICATION_JSON})
	@Consumes(value= {MediaType.APPLICATION_JSON})
	public Response addJobCode(String reqStr) {
		JSONArray j = new JSONArray(reqStr);
	AFArrayList<LKJobCode> jobCodeList = new AFArrayList<>();
		j.forEach(e-> {
			JSONObject jb =(JSONObject)e;
			print("=======jobcode=======>"+jb.toString());
			LKJobCode jbcode =this.getObjFromStr(LKJobCode.class, jb.toString());
			
			this.saveObject(jbcode);
			this.commit();
			jobCodeList.add(jbcode);
		});
		return	this.createResponse(jobCodeList);
	}
	
	@POST
	@Path("/dispJobCode")
	@Produces(value= {MediaType.APPLICATION_JSON})
	@Consumes(value= {MediaType.APPLICATION_JSON})
	public Response dispJobCode() {
	List jobCodeList = this.getAllData(LKJobCode.class);
		return	this.createResponse(jobCodeList);
	}
	@POST
	@Path("/delete")
	@Produces(value= {MediaType.APPLICATION_JSON})
	@Consumes(value= {MediaType.APPLICATION_JSON})
	public Response delete(String req) {
		JSONObject jsonObj = this.jsonObject(req);
		String cls = jsonObj.getString("clsName");
		String pk = jsonObj.getString("pk");
		
		print("Delting object for "+req);
		boolean isDeleted =	this.deleteObject(cls, pk);
		print("Objecte deleted? " + isDeleted);
		return this.createResponse("Delete Success? "+isDeleted);
	}
	
}
