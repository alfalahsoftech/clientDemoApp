package com.alfalahsoftech.alframe.ajax;

import java.sql.Timestamp;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alfalahsoftech.alframe.persistence.PSEntityManager;
import com.alfalahsoftech.alframe.persistence.PSEntityManagerFactory;
import com.alfalahsoftech.alframe.persistence.PSSchema;
import com.alfalahsoftech.inv.entity.AFMainEntity;
import com.alfalahsoftech.web.AFBaseSessionObject;
import com.alfalahsoftech.web.AFObject;

public class AFBaseReqRespObject extends AFObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	AFRequestObject requestObject;
	public String remoteAddress;
	public static final String X_FWD = "X-FORWARDED-FOR";
	private static final String USR_AGENT = "User-Agent";
	protected AFResponseObject responseObject = new AFResponseObject();
	private PSEntityManager reqEM;
	private PSSchema schema;
	private boolean isDBApplication =true; //need initialize by configuration
	public HttpServletRequest servletRequest;
	public HttpServletResponse servletResponse;
	AFBaseSessionObject sessionObject;
	public Timestamp startTime;
	public boolean isTransactionStarted;
	private boolean isTransactionEnded;
	private EntityTransaction transaction;
	public String userAgent;
	public AFBaseReqRespObject() {
	}

	public AFBaseReqRespObject(String req) {

	}
	public void setHttpRequestData(HttpServletRequest httpRequest) {
		this.servletRequest = httpRequest;
		this.remoteAddress = httpRequest.getHeader(X_FWD);
		if (this.remoteAddress == null) {
			this.remoteAddress = httpRequest.getRemoteAddr();
		}
		this.userAgent = httpRequest.getHeader(USR_AGENT);
	}

	public void setRequestJson(String requestJson) {
		this.requestObject = new AFRequestObject(requestJson);

	}

	public AFRequestObject request() {
		return this.requestObject;
	}

	public AFResponseObject response() {
		return this.responseObject;
	}
	public PSEntityManager reqEM() {
		System.out.println("this.reqEM " +this.reqEM );
		//System.out.println("this.reqEM.isOpen()=== "+ this.reqEM.isOpen());
		if (/*this.isDBApplication && */(this.reqEM == null || !this.reqEM.isOpen())) { // creates new entity manager here for request
			System.out.println("111111111111111111111");
			this.reqEM = PSEntityManagerFactory.factory().newEntityManager();
			this.schema = this.reqEM.getSchema();
		}
		return this.reqEM;
	}


	public PSSchema getSchema() {
		return this.schema;
	}
	public void closeReqEM() {
		if (this.isDBApplication && this.reqEM != null) {
			try {
				this.reqEM.close();
			} catch (Exception ignored) {
			}
			/*try {
				this.dbEM.close();
			} catch (Exception ignored) {
			}*/
			this.reqEM = null;
			//this.dbEM = null;
		}
	}
	public void closeRequest() {
		print("Closing EntityManager::");
		try {
			//this.logInfo();
			this.closeReqEM();
			//this.messageArray = null;
			//this.requestProcessor = null;
			//	this.endTime = FNTimestamp.currentTime();
		} catch (Exception e) {
			//			FNExceptionUtil.logException(e);
			e.printStackTrace();
		}
	}

	public void startTransaction() {
		this.isTransactionStarted = true;
		this.isTransactionEnded = false;
		this.transaction = this.reqEM().getTransaction();
		if (!this.transaction.isActive()) {
			this.transaction.begin();
		}
	}
	public void persit(AFMainEntity obj) {
		this.startTransaction();
		this.reqEM().persist(obj);
		this.endTransaction();
	}	
	@SuppressWarnings({ "rawtypes", "unchecked" }) 
	public <T> T getUniqueResult(Class c,Long pk) {
		Object obj= this.reqEM().getEntityManager().find(c, pk);
		return (T)obj;
	}
	public void endTransaction() {
		this.endTransaction(null, false, false);
	}

	public void endTransaction(AFMainEntity eoObject) {
		this.endTransaction(eoObject, false, false);
	}
	// transaction termination  for any action should be done here
	public void endTransaction(AFMainEntity eoObject, boolean doNotSendMail, boolean closeEM) {
		try {
			if (this.isTransactionEnded) {
				return;
			}
			if (this.transaction != null && this.transaction.isActive()) {
				//long st = System.currentTimeMillis();
				this.commit();
				//	this.addTimeTakenForSqlWrites(System.currentTimeMillis() - st);
				/*if (eoObject != null) {
						eoObject.endTransaction();
					}*/
			}
			/*	if (!doNotSendMail && FNApplicationHelper.isSendMsgOnCommitEnabled()) {
					this.sendMessages();
				}*/
			if (closeEM) {
				this.closeReqEM();
			}
		} catch (Exception e) {
			//				this.isRolledBack = true;
			//				this.handleIdleTimeOutOracle(e);
			//				if (this.isApiRequest()) {
			//					this.addApiException(e, "COMMIT_ERROR");
			//				}
			try {
				if (this.transaction != null && this.transaction.isActive()) {
					this.transaction.rollback();
				}
			} catch (Exception ignored) {
				//FNExceptionUtil.logException(ignored);
			}
			//this.logDBCommitExceptionUI(e);
		}
		this.isTransactionStarted = false;
		this.isTransactionEnded = true;
	}
	private void commit() {
		//this.reqEM().writeOpsDoneOnDB();
		this.transaction.commit();
	}
	public void initSessionObject(AFBaseSessionObject sessionObject) {
		this.sessionObject = sessionObject;
		if (sessionObject != null) {
			sessionObject.lastAccessTime = this.startTime;
		}
	}
}

