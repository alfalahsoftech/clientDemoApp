package com.alfalahsoftech.controller;

import java.text.ParseException;
import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.swing.text.MaskFormatter;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.alfalahsoftech.alframe.AFHashMap;
import com.alfalahsoftech.web.AFObject;
import com.alfalahsoftech.alframe.ajax.AFBaseReqRespObject;
import com.alfalahsoftech.alframe.ajax.AFRequestObject;
import com.alfalahsoftech.alframe.ajax.AFResponseObject;
import com.alfalahsoftech.alframe.annotation.AFControllerSetup;
import com.alfalahsoftech.alframe.factory.AFReqRespThreadFactory;
import com.alfalahsoftech.inv.entity.AFMainEntity;
import com.google.gson.Gson;

public abstract class AFBaseController extends AFObject {

	private static final long serialVersionUID = 8244595474025412830L;

	public abstract Object callMethod(AFHashMap<String, Object> objectHash);

	public abstract void addResponse();

	public AFControllerSetup ajaxSetup;
	public AFBaseReqRespObject reqRespObject;
	public boolean doCommit;
	protected AFRequestObject request;
	protected AFResponseObject response;
	//	private PSEntityManager reqEM;
	public transient Object object;

	@Override
	public void initObject() {
		super.initObject();
		this.request = this.reqRespObject.request();
		this.response = this.reqRespObject.response();
		//		this.reqRespObject.ajaxBean = this;
		//		this.reqEM = this.reqRespObject.reqEM();
	}

	public AFRequestObject request() {
		return this.request;
	}

	public AFResponseObject response() {
		return this.response;
	}
	
	public AFBaseReqRespObject reqRespObjec(){
		this.reqRespObject =  AFReqRespThreadFactory.factory().getReqRespObject();
		return reqRespObject;
	}

	public  List<AFMainEntity> getAllData(Class<?> cls){
		List<AFMainEntity> clientList = this.entityMgr().createQuery("SELECT e FROM "+cls.getSimpleName()+" e").getResultList();
		return clientList;
	}
	public EntityManager entityMgr() {
		return this.reqRespObjec().reqEM().getEntityManager();
	}
	public Response createResponse(Object obj) {
		ResponseBuilder resp =   Response.ok();
		
		return resp.entity(this.toJson(obj)).build();
	}
	
	public String toJson(Object obj) {
		return this.gson().toJson(obj);
	}
	
	private Session session;
	private Transaction txn;
	public Session getSession() {
		if(Objects.isNull(session)) {
			print("Session is null, Going create");
		 session = this.entityMgr().unwrap(Session.class);
		}
		
		if(Objects.nonNull(session) && !session.getTransaction().isActive()) {
			this.txn = session.beginTransaction();
		}
		return session;
	}
	public void commit() {
		if(Objects.nonNull(this.session) && Objects.nonNull(this.txn)) {
			this.txn.commit();
		}
	}
	public Long saveObject(Object object) {
		if(Objects.nonNull(this.getSession()) && Objects.nonNull(this.txn)) {
			print("Going to save objec "+ object);
			return (Long)this.session.save(object);
		}
		
		print("##########3 Returing null in save ");
		return null;
	}
	public boolean deleteObject(String cls,String pk) {
		Object obj  =this.getUniqueObject(cls, pk);
		if(Objects.nonNull(obj)) {
			this.session.delete(obj);
			this.commit();
			return true;
			
		}
		return false;
	}
	public Object getUniqueObject(String cls,String pk) {
		if(Objects.nonNull(this.getSession()) && Objects.nonNull(this.txn)) {
			print("Fetch object for class: "+ cls+"  PK: "+pk);
		return 	this.session.createQuery("From "+cls+" where primaryKey="+ Long.valueOf(pk)).list().get(0);
			
		}
		return null;
	
	}
	public Transaction transaction() {
		return this.txn;
	}
	public String getUniqueID(Object obj,String format) {
		return  maskString(obj + "", '0',  format);
	}
	
	public String getUniqueIDWithPrefix(String prefix, Object obj,String format) {
		return (Objects.isNull(prefix)?prefix:"") +  maskString(obj + "", '0', format);
	}
	
	
	
	public  String maskString(String rawStr, char c, String format) {
		StringBuilder buffer = new StringBuilder();
		while (rawStr.length() + buffer.length() < format.length()) {
			buffer.append(c);
		}
		buffer.append(rawStr);
		try {
			MaskFormatter maskFormatter = new MaskFormatter(format);
			maskFormatter.setValueContainsLiteralCharacters(false);
			return maskFormatter.valueToString(buffer);
		} catch (ParseException e) {
			System.out.println("ParsingException=>" +e.getMessage());
		}
		return buffer.toString();
	}

	
}
