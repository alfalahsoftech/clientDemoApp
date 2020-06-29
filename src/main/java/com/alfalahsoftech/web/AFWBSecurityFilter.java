package com.alfalahsoftech.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;

@WebFilter(filterName="AFWBSecurityFilter", urlPatterns= {"/rest/*"})
public class AFWBSecurityFilter  implements Filter{


	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}



	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		try {

			System.out.println("/////////////  AFWBSecurityFilter _ doFilter ///////////");
			HttpServletResponse httpResonse = (HttpServletResponse)response;
			//If External Client Application will access then we have to provide this
			//httpResonse.setHeader("Access-Control-Allow-Origin", "*");
			//httpResonse.setHeader("Access-Control-Allow-Headers","Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");
			
			httpResonse.setHeader("Access-Control-Allow-Origin", "*");
			httpResonse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,DELETE,PUT");
			httpResonse.setHeader("Access-Control-Allow-Headers","Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");
			httpResonse.setContentType("application/json");
			
			
			System.out.println("AFWBSecurityFilter~doFilter");
			
			
			filterChain.doFilter(request, httpResonse);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public void destroy() {
		System.out.println(" AFWBSecurityFilter _ destroy");
		
	}
}
