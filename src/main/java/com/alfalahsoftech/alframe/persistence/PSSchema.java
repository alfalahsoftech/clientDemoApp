package com.alfalahsoftech.alframe.persistence;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.alfalahsoftech.alframe.AFConstant;
import com.alfalahsoftech.alframe.ajax.AFBaseReqRespObject;
import com.alfalahsoftech.alframe.factory.AFReqRespThreadFactory;
import com.alfalahsoftech.common.file.AFJsonParser;
import com.alfalahsoftech.web.AFObject;
import com.alfalahsoftech.web.AFWebContextListener;

public class PSSchema extends AFObject {

	private static final long serialVersionUID = 1L;
	private transient EntityManagerFactory entityManagerFactory;
	public PSEntityManager globalEntityManager;
	public static void main(String[] args) {
		PSSchema f= new PSSchema();
		f.createNewEntityManager();

	}

	public boolean createNewEntityManager() {
		HashMap<String, String> map = new HashMap<>();
		boolean isConnectionEshtablished =false;
		try {
			DBConfig dbConfig= DBConfig.getDBConfig();
			map.put("javax.persistence.jdbc.driver", dbConfig.getDriver_Class());
			map.put("javax.persistence.jdbc.url",dbConfig.getCompleteDbURL());
			map.put("javax.persistence.jdbc.user", dbConfig.getDbUserName());
			map.put("javax.persistence.jdbc.password", dbConfig.getDbPassword());	
			map.put("hibernate.hbm2ddl.auto",dbConfig.getHbm2ddl());
			
			System.out.println(dbConfig);
			entityManagerFactory = Persistence.createEntityManagerFactory(dbConfig.persistenceUnitName,map);
			System.out.println("entityManagerFactory========== " +entityManagerFactory);
			globalEntityManager = new PSEntityManager(entityManagerFactory.createEntityManager(), this);
			isConnectionEshtablished = true;
			//		newEntityMgr().getTransaction().begin();
		}catch(Exception e){
			e.printStackTrace();
		}
		//		this.entityManager = emFactory.createEntityManager();
		return isConnectionEshtablished;
	}

	public String driver_Class = "org.postgresql.Driver";//"com.mysql.jdbc.Driver" ;
	public String host="localhost";
	public String port="5432";
	public String catalogName = "SAMITech";
	public PSDatabase psDatabase;
	public String dbUserName ="postgres";
	public String dbPassword="postgres";
	public String persistenceUnitName;
	public boolean isNeedGlobalEntityManager;

	public String getMySqlDbConnectionString() {
		return "jdbc:mysql://" + this.host + ":" + this.port + "/";
	}
	public static void mySQLConn(){
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/SAMITech","root","admin");
			//con.createStatement().execute("create table Admin(userName char(55),primaryKey int)");
		}catch(Exception e){
			e.printStackTrace();
		}

	}

	public String getPostgresSqlDbConnectionString() {
		return "jdbc:postgresql://" + this.host + ":" + this.port + "/";
	}

	public PSEntityManager newEntityMgr() {
		System.out.println("entityManagerFactory "+entityManagerFactory);
		return new PSEntityManager( entityManagerFactory.createEntityManager(),this); //createNewEntityManager();
	}
	public void close() {
		try {
			this.entityManagerFactory.getCache().evictAll();
		} catch (Exception ignored) {
		}
		try {
			this.entityManagerFactory.close();
		} catch (Exception ignored) {
		}
		/*try {
			this.requestProcessor().clearCache();
		} catch (Exception ignore) {
		}*/
		this.printMsg("Disconnected from schema %s", "nohtinnnnnnnnnnn");
	}
	
	/*
	 * When no one is accessing application,connection get lost.So let's connect database every after 10 minutes
	 */
	public void avoidIdleConnection() {
		Timer t= new Timer();
		TimerTask  idleTask = new TimerTask() {
			
			@Override
			public void run() {
				
				PSEntityManager em=	AFReqRespThreadFactory.factory().getReqRespObject().reqEM();
				em.createQuery("select e from EOUser e");
			}
		};
		t.schedule(idleTask, 10,60*10);
		
	}
}


