package com.alfalahsoftech.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)//Class has two properties of the same name
public class ErrorMessage {
	public String errorMessage;
	public String errorCode;
	public ErrorMessage() {
		
	}
	public ErrorMessage(String msg) {
		this(msg, "N/A");
	}
	public ErrorMessage(String msg,String errorCode) {
		this.setErrorMessage(msg);
		this.setErrorCode(errorCode);
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	
}
