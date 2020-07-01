package com.alfalahsoftech.medi.controller;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.alfalahsoftech.alframe.AFHashMap;
import com.alfalahsoftech.controller.AFBaseController;
import com.alfalahsoftech.medi.entity.EOMedicine;

@Path("medi")
public class AFMediController extends AFBaseController {

	Response response;
	
	@POST
	@Path("/addMedi")
	@Consumes(value= { MediaType.APPLICATION_JSON})

	public String addMedi(String reqStr) {
		printObj("Adding request:  "+reqStr);
		EOMedicine medi =this.getObjFromStr(EOMedicine.class	, reqStr);
		this.saveObject(medi);
		this.commit();
		return "{msg:Successfully added!}";
	}
	
	@POST
	@Path("/dispMedi")
	@Consumes(value= { MediaType.APPLICATION_JSON})
	@Produces(value= { MediaType.APPLICATION_JSON})
	public Response dispMedi(String reqStr) {
		printObj("dispMedi request:  "+reqStr);
		List list =  this.getAllData(EOMedicine.class);
		String retData = "{msg:Nothing to display !}";
		 response = this.createResponse(list);
		return response;
	}
	
	@POST
	@Path("/editMedi/{mediPK}")
	@Consumes(value= { MediaType.APPLICATION_JSON})
	@Produces(value= { MediaType.APPLICATION_JSON})
	public Response editMedi(@PathParam("mediPK") String mediPK) {
		printObj("edit request:  "+mediPK);
		Object obj = this.reqRespObject().reqEM().createQuery("SELECT e FROM EOMedicine e where primaryKey="+mediPK).getResultList().get(0);//this.getUniqueObject("EOMedicine", mediPK);
		 response = this.createResponse(obj);
		return response;
	}
	
	@POST
	@Path("/updateMedi")
	@Consumes(value= { MediaType.APPLICATION_JSON})
	@Produces(value= { MediaType.APPLICATION_JSON})
	public Response updateMedi(String reqStr) {
		printObj("updateMedi request:  "+reqStr);
		EOMedicine obj = this.updateObject(EOMedicine.class,reqStr);
		 response = this.createResponse(obj);
		return response;
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
