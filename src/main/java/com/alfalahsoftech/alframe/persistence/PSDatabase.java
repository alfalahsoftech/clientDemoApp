package com.alfalahsoftech.alframe.persistence;

import com.alfalahsoftech.alframe.AFArrayList;
import com.alfalahsoftech.alframe.AFConstant;
import com.alfalahsoftech.alframe.AFHashMap;
import com.alfalahsoftech.web.AFObject;

public class PSDatabase extends AFObject {
	private static final long serialVersionUID = 1L;
	public String key;
	public String host;
	public int port;
	public String sid;
	public String csDbUserNames;
	public String vendor;
	public String jdbcDriver;
	public boolean isWriteTimeout;
	public String uniqueID;
	public String tnsName;
	public boolean isNeedGlobalEntityManager;
	public String tableNameForKeepLive;
	public String persistenceUnitName;// Model Name
	public int keepLiveIntervalInMinutes = 10;
	public int idleTimeoutInMinutes = 15;
	public boolean isVerifyEMForOracle;

//	@FNClassPpt
	public AFHashMap<String, String> additionalProperties;

//	@FNClassPpt
	public AFArrayList<PSSchema> schemaArray = new AFArrayList<>();

	public PSSchema defaultSchema() {
		return this.schemaArray.isEmpty() ? null : this.schemaArray.get(0);
	}

	@Override
	public void initObject() {
		/*if (this.isEmptyStr(this.key)) {
			this.key = this.isEmptyStr(this.persistenceUnitName) ? AFApplicationObject.factory().appName() : this.persistenceUnitName;
		}*/
		this.setUniqueId();
		for (PSSchema schema : this.schemaArray) {
			schema.psDatabase = this;
			//schema.setDbDefaultsIfNecessary();
			PSEntityManagerFactory.factory().addSchemaAndResetStatus(schema);
		}
		this.setCsDbUserNames();
	}

	public void setCsDbUserNames() {
		if (this.isNonEmptyStr(this.csDbUserNames)) {
			String[] dbUserNames = this.csDbUserNames.split(AFConstant.COMMA);
			if (this.schemaArray == null) {
				this.schemaArray = new AFArrayList<>();
			}
			this.setUniqueId();
			for (String dbUserName : dbUserNames) {
				if (dbUserName.trim().length() > 0) {
					PSSchema schema = this.getNewSchemaWithDefaultProperties(dbUserName.trim());
					this.schemaArray.add(schema);
					PSEntityManagerFactory.factory().addSchemaAndResetStatus(schema);
				}
			}
		}
	}

	public String getOracleDbConnectionString() {
		StringBuilder connectionString = new StringBuilder();
		connectionString.append("jdbc:oracle:thin:");
		connectionString.append("@(DESCRIPTION=(ADDRESS_LIST=(ADDRESS=(PROTOCOL=TCP)(HOST=" + this.host);
		connectionString.append(")(PORT=" + this.port);
		connectionString.append(")))(CONNECT_DATA=(SERVICE_NAME=" + this.sid);
		connectionString.append(")(SERVER=DEDICATED)))");
		return connectionString.toString();
	}

	public String getMySqlDbConnectionString() {
		return "jdbc:mysql://" + this.host + ":" + this.port + "/";
	}

	public String getPostgresSqlDbConnectionString() {
		return "jdbc:postgresql://" + this.host + ":" + this.port + "/";
	}

	public boolean isMySql() {
		return this.vendor == null || this.vendor.equalsIgnoreCase("MySql");
	}

	public boolean isOracle() {
		return this.vendor != null && this.vendor.equalsIgnoreCase("Oracle");
	}

	public boolean isPostgres() {
		return this.vendor != null && this.vendor.equalsIgnoreCase("PostgreSQL");
	}

	private void setUniqueId() {
		this.uniqueID = this.host + AFConstant.TILDE + this.port;
		if (this.sid != null) {
			this.uniqueID +=AFConstant.TILDE + this.sid;
		}
	}

	public PSSchema getNewSchemaWithDefaultProperties(String dbUsername) {
		PSSchema schema = new PSSchema();
		schema.catalogName = dbUsername;
		schema.dbUserName = dbUsername;
		schema.dbPassword = dbUsername;
		schema.persistenceUnitName = this.persistenceUnitName;
		schema.psDatabase = this;
		schema.isNeedGlobalEntityManager = this.isNeedGlobalEntityManager;
		//schema.setDbDefaultsIfNecessary();
		schema.initObject();
		return schema;
	}

	public String getCalcTnsName() {
		if (this.tnsName != null) {
			return this.tnsName;
		}
		return this.sid.contains(".") ? this.sid.substring(0, this.sid.indexOf('.')) : this.sid;
	}

	public boolean isOracleReverifyNeeded() {
		return this.isOracle() && this.isVerifyEMForOracle;
	}
}
