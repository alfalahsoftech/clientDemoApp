package com.alfalahsoftech.alframe.security;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;

@Provider
//@PreMatching
public class AFRequestFilter implements ContainerRequestFilter {

	@Override
	public void filter(ContainerRequestContext containerRequestContext) throws IOException {
		System.out.println("#########3333 ContainerRequestContext " + containerRequestContext);
	}

}
