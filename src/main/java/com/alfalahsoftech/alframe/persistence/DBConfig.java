package com.alfalahsoftech.alframe.persistence;

import java.io.File;

import com.alfalahsoftech.common.file.AFJsonParser;
import com.alfalahsoftech.web.AFApplicationObject;
import com.alfalahsoftech.web.AFWebContextListener;

public class DBConfig{
	String persistenceUnitName="bake";
	String catalogName = "SAMITech";
	String dbName="MySQL";
	String host="localhost";
	String dbUserName="root";
	String dbPassword="admin";
	String dbURL="jdbc:mysql://"+this.host+"/"+this.catalogName;
	String driver_Class="com.mysql.jdbc.Driver";
	String hbm2ddl="update";
//	String hbm2ddl="create";
	boolean isDevMod = true;
	
	@Override
	public String toString() {
		return "DBConfig [persistenceUnitName=" + persistenceUnitName + ", catalogName=" + catalogName + ", dbName="
				+ dbName + ", host=" + host + ", dbUserName=" + dbUserName + ", dbPassword=" + dbPassword + ", dbURL="
				+ dbURL + ", driver_Class=" + driver_Class + ", hbm2ddl=" + hbm2ddl + "]";
	}

	public String getPersistenceUnitName() {
		return persistenceUnitName;
	}

	public void setPersistenceUnitName(String persistenceUnitName) {
		this.persistenceUnitName = persistenceUnitName;
	}

	public String getCatalogName() {
		return catalogName;
	}

	public void setCatalogName(String catalogName) {
		this.catalogName = catalogName;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getDbUserName() {
		return dbUserName;
	}

	public void setDbUserName(String dbUserName) {
		this.dbUserName = dbUserName;
	}

	public String getDbPassword() {
		return dbPassword;
	}

	public void setDbPassword(String dbPassword) {
		this.dbPassword = dbPassword;
	}

	public String getDbURL() {
		return dbURL;
	}

	public void setDbURL(String dbURL) {
		this.dbURL = dbURL;
	}

	public String getDriver_Class() {
		return driver_Class;
	}

	public void setDriver_Class(String driver_Class) {
		this.driver_Class = driver_Class;
	}

	public String getHbm2ddl() {
		return hbm2ddl;
	}

	public void setHbm2ddl(String hbm2ddl) {
		this.hbm2ddl = hbm2ddl;
	}
	public String getCompleteDbURL() {
		return AFWebContextListener.appConfig.getBoolean("isProdEnvorment")?this.dbURL:this.dbURL+"/"+this.catalogName;
	}
	public static  DBConfig getDBConfig() {
		File file = null;
		if(AFWebContextListener.appConfig.getBoolean("isProdEnvorment")) {
			file =new File(AFApplicationObject.META_PATH+"glbDir/data/dbConfig_Prod.json");
		}else {
			System.out.println("###################################### Connection will from localhost ##################################");
			file =new File(AFApplicationObject.META_PATH+"glbDir/data/dbConfig.json");
		}
		DBConfig dbConfig =	AFJsonParser.parseFile(file,DBConfig.class);
		return dbConfig; 
	}
	
	
	
}