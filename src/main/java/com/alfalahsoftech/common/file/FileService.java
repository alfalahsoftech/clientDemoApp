package com.alfalahsoftech.common.file;

import java.io.File;
import java.io.FileInputStream;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import com.alfalahsoftech.web.AFWebContextListener;
import com.alfalahsoftech.web.AFWebReqListener;

import javax.ws.rs.core.UriInfo;

@Path("file")
public class FileService {

	@Context
	UriInfo uriInfo;
	private static final String FILE_PATH = AFWebContextListener.contextPath+"/src/com/alflah/common/file/FileService.java";
	//public FileService() {
//		System.out.println("  FileService  uriInfo= " +uriInfo.getAbsolutePath());// you cannot access uriInfo because it is initialized after constructor invocation
	//}
	
	//java.lang.NoSuchMethodException: Could not find a suitable constructor in com.alflah.common.file.FileService class.
//	public FileService(String value) {
//		System.out.println("  FileService  uriInfo= " +uriInfo.getAbsolutePath());
//	}
	
	@GET
	@Path("/test")
	public void testService(String value) {
		System.out.println("  FileService  uriInfo= " +uriInfo.getAbsolutePath());
	}
	@GET
	@Path("/download")
	@Produces("text/plain")
	public Response getFile() {
		
		File file = new File(FILE_PATH);

		ResponseBuilder response = Response.ok((Object) file);
		response.header("Content-Disposition",
			"attachment; filename=\"FileService.java\"");
		return response.build();

	}
	@POST
	@Path("/pdf")
	@Produces("application/pdf")
	public javax.ws.rs.core.Response getPdf() throws Exception
	{
		System.out.println("downloading file ");
	    File file = new File(AFWebContextListener.contextPath+"HelloWorld.pdf");
//	    FileInputStream fileInputStream = new FileInputStream(file);
//	    javax.ws.rs.core.Response.ResponseBuilder responseBuilder = javax.ws.rs.core.Response.ok((Object) fileInputStream);
//	    responseBuilder.type("application/pdf");
//	    responseBuilder.header("Content-Disposition", "filename=test.pdf");
	    ResponseBuilder response = Response.ok((Object) file);
	    response.header("Content-Disposition",  "attachment; filename=bill.pdf");
	    
	    return response.build();
	}
}