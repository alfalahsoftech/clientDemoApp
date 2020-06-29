package com.alfalahsoftech.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.alfalahsoftech.entity.ErrorMessage;

@Provider
public class AFExceptionMapper  implements  ExceptionMapper<DataNotFoundException>{

	@Override
	public Response toResponse(DataNotFoundException exception) {
		System.out.println("####### Exception occured #######");
		ErrorMessage errorMsg = new ErrorMessage(exception.getMessage(),"404");
		return Response.ok().entity(errorMsg).build();
	}

}
