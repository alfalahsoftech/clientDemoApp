package com.alfalahsoftech.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alfalahsoftech.alframe.AFConstant;
import com.alfalahsoftech.alframe.ajax.AFBaseReqRespObject;
import com.alfalahsoftech.alframe.factory.AFReqRespThreadFactory;

@WebFilter(filterName= "AFWBFirstPageFilter",urlPatterns= {"/rest/login/*"})
public class AFWBFirstPageFilter extends AFObject implements Filter{

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		try {
			HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
			HttpServletResponse httpResonse = (HttpServletResponse) servletResponse;
			System.out.println("AFWBFirstPageFilter.httpRequest.getRequestURI()===========> "+httpRequest.getRequestURI());
			
			HttpSession session = httpRequest.getSession(false);
			if (session == null) {
				session = httpRequest.getSession(true);
			}

			AFBaseSessionObject sessionObject = (AFBaseSessionObject)session.getAttribute(AFWebReqListener.SESS_OBJ);
			if(sessionObject  == null) {
				sessionObject= AFWBSessionObjectFactory.factory().createSession(session, httpRequest);
			}
			
			
			AFBaseReqRespObject reqRespObject = AFReqRespThreadFactory.factory().getReqRespObject();
			System.out.println("FristPageFilter=>reqRespObject=>>>>>>>>" +reqRespObject);
			reqRespObject.initSessionObject(sessionObject);
			reqRespObject.servletResponse = (HttpServletResponse) servletResponse;
			//pages given in url-pattern will not be stored in browser's cache.
			httpResonse.setHeader(AFConstant.CACHE, AFConstant.CACHE_VALUE);
			httpResonse.setHeader(AFConstant.PRAGMA, AFConstant.NO_CACHE);
			httpResonse.setDateHeader(AFConstant.EXP, 0);
			
			
			httpResonse.setHeader("Access-Control-Allow-Origin", "*");
			httpResonse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,DELETE,PUT");
			httpResonse.setHeader("Access-Control-Allow-Headers","Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");
			httpResonse.setContentType("application/json");
			
			filterChain.doFilter(httpRequest, httpResonse);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}


}
