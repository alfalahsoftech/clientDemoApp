package com.alfalahsoftech.controller;
import javax.inject.Singleton;
import javax.ws.rs.*;

import com.alfalahsoftech.entity.Widget;
import com.alfalahsoftech.entity.WidgetList;

@Singleton
@Path("widgets")
public class WidgetsResource implements Widget {
	@PathParam("id") String newID;
	@GET
	public WidgetList getWidgets() {
		// id is null here
		return null;
	}
	public WidgetsResource() {
		
	}
	public WidgetsResource(String str) {
		
	}
	@GET
	@Path("{id}")
	public Widget findWidget(@PathParam("id") String id) {
		System.out.println("id = " + id);
	System.out.println("newID = " + newID);
		return new WidgetsResource(id);
	}
}