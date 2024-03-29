package com.alfalahsoftech.medi.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONObject;

import com.alfalahsoftech.alframe.AFHashMap;
import com.alfalahsoftech.common.file.AFJsonParser;
import com.alfalahsoftech.controller.AFBaseController;
import com.alfalahsoftech.medi.entity.EOMedicine;
import com.alfalahsoftech.service.UserSrvcImpl;

@Path("medi")
@SuppressWarnings("unchecked")
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
	UserSrvcImpl srvc = new UserSrvcImpl();

	@POST
	@Path("/dispMedi")
	@Consumes(value= { MediaType.APPLICATION_JSON})
	@Produces(value= { MediaType.APPLICATION_JSON})
	public Response dispMedi(String reqStr) {
		printObj("dispMedi request:  "+reqStr);
		List<EOMedicine> list = (List<EOMedicine>) this.genericAllData(EOMedicine.class);// srvc.allEOMedicineData();
	
		/*print("Reading medicnie files===============");
		AtomicInteger i = new AtomicInteger(0);
		ArrayList<String> mediList = AFJsonParser.getAllMedi();
		mediList.forEach(mediName->{
			list.get(i.getAndIncrement()).setMediName(mediName);
		});*/
		response = this.createResponse(list);
		//System.out.println(list);
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

		JSONObject jsonObj=new JSONObject(reqStr);
		EOMedicine medi =this.getObjFromStr(EOMedicine.class	, reqStr);

		EOMedicine obj = (EOMedicine)this.reqRespObject().reqEM().createQuery("SELECT e FROM EOMedicine e where primaryKey="+jsonObj.getLong("primaryKey")).getResultList().get(0);
		
//		Object obj =this.updateObject(EOMedicine.class, reqStr);
		/*obj.setItemID(	jsonObj.getString("itemID"));
		obj.setMediName(jsonObj.getString("mediName"));
		obj.setScheme(jsonObj.getString("scheme"));
		obj.setBatchNo(	jsonObj.getString("batchNo"));
		obj.setUOM(	jsonObj.getString("UOM"));
		obj.setDiscount(	jsonObj.getDouble("discount"));
		obj.setNetRate(jsonObj.getDouble("netRate"));
		obj.setMrp(jsonObj.getDouble("mrp"));
		obj.setIsActive(jsonObj.getInt("isActive")==0?false:true);
		obj.setExpDate(this.formatedDate(jsonObj.getString("expDate")));
		obj.setNotes(jsonObj.getString("notes"));*/
		/*	EOMedicine obj= null;
		for (int i = 1; i < 500; i++) {
			 obj = new EOMedicine();*/
		obj.setItemID(medi.getItemID());
		obj.setMediName(medi.getMediName());
		obj.setScheme(medi.getScheme());
		obj.setBatchNo(medi.getBatchNo());
		obj.setUOM(	medi.getUOM());
		obj.setDiscount(medi.getDiscount());
		obj.setNetRate(medi.getNetRate());
		obj.setMrp(medi.getMrp());
		obj.setIsActive(medi.getIsActive());
		obj.setExpDate(medi.getExpDate());
		obj.setNotes(medi.getNotes());
		obj.setMfgBy(medi.getMfgBy());
		obj.setNetRatePerc(medi.getNetRatePerc());
		obj.setPack(medi.getPack());
		//Transaxtion
		this.reqRespObject().startTransaction();
		this.reqRespObject().reqEM().merge(obj);
		this.reqRespObject().endTransaction();
		
		//	}
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
