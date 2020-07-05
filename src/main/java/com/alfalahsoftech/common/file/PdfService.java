package com.alfalahsoftech.common.file;

import java.io.File;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.hibernate.Session;
import org.json.JSONObject;

import com.alfalahsoftech.alframe.AFHashMap;
import com.alfalahsoftech.controller.AFBaseController;
import com.alfalahsoftech.inv.entity.EOClient;
import com.alfalahsoftech.pdf.PDFGenerator;
import com.alfalahsoftech.web.AFObject;
import com.alfalahsoftech.web.AFWebContextListener;

@Path("/pdf")
public class PdfService extends AFBaseController{

	private static final String FILE_PATH = AFWebContextListener.contextPath+"Invoice.pdf";

	@POST
	@Path("/genPDF")
	@Produces("application/pdf")
	public Response getFile(String str) {
		//System.out.println("genPDF======================="+str);
		JSONObject jsonObj  = this.jsonObject(str);
		Long clientPK= jsonObj.getLong("clientPK");
		
		Session session=  this.getSession();
		EOClient client =	(EOClient) session.get(EOClient.class,Long.valueOf(clientPK));
		PDFGenerator.generatePDF(jsonObj.getString("pdfDetails"),client);
		File file = new File(FILE_PATH);

		ResponseBuilder response = Response.ok((Object) file);
		response.header("Content-Disposition",
				"attachment; filename=generatedbill.pdf");
		return response.build();

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