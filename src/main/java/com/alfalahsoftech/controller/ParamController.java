package com.alfalahsoftech.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.alfalahsoftech.alframe.AFHashMap;
import com.alfalahsoftech.entity.MyMsg;
import com.alfalahsoftech.entity.Validation;
import com.alfalahsoftech.exception.DataNotFoundException;

@Path("paramTest")
public class ParamController extends AFBaseController {

	public static void main(String[] args) {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("/test2");
		target.matrixParam("catagory", "categories;name=foo/objects;name=green");

	}

	@Context
	HttpHeaders headers;

	@Context
	HttpServletRequest servletRequest;

	@Context
	Application application; // JAX-RS 2.0 spec section 9.2.1

	/*//http://localhost:8080/TnD/rest/paramTest/auth?user=sam
	@QueryParam("user")
	Validation validate;
	 */

	@Path("/test2")
	@POST
	public String entityParam(MyMsg entityParam) {
		System.out.println(entityParam);
		return "entity parameter: " + entityParam;
	}

	//http://localhost:8080/TnD/rest/paramTest/test3
	//request body:<myMsg><id>2</id><msg>hello</msg></myMsg>
	@Path("/test3")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_XML)
	public Response entityParamWithJsonResp(MyMsg entityParam) {
		System.out.println(entityParam);
		return Response.ok(entityParam, MediaType.APPLICATION_JSON).build();//"entity parameter consume xml and resp as JSON  :" + entityParam;
	}

	@POST
	@Path("/entity")
	public String ajaxEntity(@QueryParam("user")
	Validation validate) {
		System.out.println("paramValue = " + validate);
		return "successs";
	}

	@POST
	@Path("/auth")
	public String ajax(String paramValue) {
		System.out.println("paramValue = " + paramValue);
		//		System.out.println("validate= " +validate);
		return paramValue;
	}

	public ParamController(@Context
	UriInfo uriInfo) {
		System.out.println(" ######  ParamController constructor ####");
		System.out.println("uriInfo = " + uriInfo.getClass().getName());//uriInfo = org.glassfish.jersey.server.internal.routing.UriRoutingContext
	}

	@Path("/{path}")
	@GET
	//	@Produces(MediaType.APPLICATION_JSON)
	// request:
	/*
	 http://localhost:8080/TnD/rest/paramTest/k/;matrix=gg;matrix2=gg,hh,kk
	
	Get Matrix Parameters with @MatrixParam
	We can also inject the matrix parameters immediately using the @MatrixParam JAX-RS annotation.
	
	Note: when using the @MatrixParam and you have repeating matrix parameters defined in your
		URI that are applied to many different path segments it is uncertain
		which matrix parameter is used. And if this is the case you should go back and use the PathSegment to get the matrix parameters.
	
	 */
	public String paramAction(@HeaderParam("header")
	String headerParam,
			@MatrixParam("matrix")
			String matrixParam,
			@MatrixParam("matrix2")
			String matrixParam2,
			@CookieParam("cookie")
			String cookieParam,
			@PathParam("path")
			String pathParam,
			@QueryParam("query")
			String queryParam,
			@QueryParam("query2")
			String queryParam2,
			@Context
			Request context) {

		System.out.println(" ######  paramAction ####");

		System.out.println("headerParam = > " + headerParam);
		System.out.println("matrixParam = > " + matrixParam);
		System.out.println("matrixParam2 = > " + matrixParam2);
		System.out.println("cookieParam = > " + cookieParam);
		System.out.println("pathParam = > " + pathParam);
		System.out.println("queryParam = > " + queryParam);
		System.out.println("queryParam2 = > " + queryParam2);
		System.out.println("Context => " + context);

		System.out.println("headers = " + this.headers.getHeaderString("abc"));
		System.out.println("servletRequest = " + this.servletRequest.getContextPath()); //write yso to print System.out.println
		System.out.println("application = " + this.application.getClasses());
		if (this.headers.getHeaderString("abc") == null) {
			System.out.println("ooppppppppppppppppssss");
			throw new DataNotFoundException("You are not authorized");
		}
		return "paramAction() called";
	}

	//Matrix Parameters can be placed in every URI segment(like param/catagory;name=ss;age=23;id=2--ek uri segment) using the ; character.

	//	http://localhost:8080/TnD/rest/paramTest/categories;name=foo/objects;name=green
	//	http://localhost:8080/TnD/rest/paramTest/categories;name=foo;age=33/objects;name=green --// ;name=foo;age=33 inside path segment :{name=[foo], age=[33]}
	@GET
	@Path("{categoryVar:categories}/objects")
	public String objectsByCatecory(@PathParam("categoryVar")
	PathSegment categorySegment,
			@MatrixParam("name")
			String objectName) {
		MultivaluedMap<String, String> matrixParameters = categorySegment.getMatrixParameters();
		String categorySegmentPath = categorySegment.getPath();
		String response = String.format("object %s,%n path:%s,%n matrixParams:%s%n", objectName,
				categorySegmentPath, matrixParameters);
		System.out.println("response= " + response);
		return response;
	}

	//http://localhost:8080/TnD/rest/paramTest/all;name=foo/objects;name=green      //attributes;name=size
	//http://localhost:8080/TnD/rest/paramTest/all;name=foo/objects;name=green; lName=Sheikh;age=32
	//PathSegment may be 1.Get Single Path Segment  2.Get Multiple Path Segment, below is Get Multiple Path Segment
	@GET
	@Path("all/{var:.+}")
	public String allSegments(@PathParam("var")
	List<PathSegment> pathSegments) {

		StringBuilder sb = new StringBuilder();

		for(PathSegment pathSegment : pathSegments) {
			sb.append("path: ");
			sb.append(pathSegment.getPath());
			sb.append(", matrix parameters ");
			sb.append(pathSegment.getMatrixParameters());
			sb.append("<br/>");
		}
		String response = sb.toString();
		System.out.println("allSegments =" + response);
		return response;
	}

	//http://localhost:8080/TnD/rest/paramTest/headTest?param1="Testing head annotation"
	//Call by GET or Head- if by head then return no body but when you call by GET, work normally
	@Path("/headTest")
	@GET
	public Response handle(@QueryParam("param1")
	String param1) {
		System.out.printf("param1: %s%n", param1);
		Response r = Response.ok("this body will be ignored")
				.header("someHeader", "someHeaderValue")
				.build();
		return r;
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
