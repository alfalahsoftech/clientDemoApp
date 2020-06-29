package com.alfalahsoftech.alframe.persistence;

import com.alfalahsoftech.alframe.AFArrayList;
import com.alfalahsoftech.alframe.AFHashMap;

public class PSEntityManagerFactory {

	private static PSEntityManagerFactory factory = new PSEntityManagerFactory();
	private AFArrayList<PSSchema> schemaArray = new AFArrayList<>();

	public static PSEntityManagerFactory factory() {
		return factory;
	}

	private boolean isSingleSchemaApp;

	public boolean isSingleSchemaApp() {
		return this.isSingleSchemaApp;
	}

	public void addSchemaAndResetStatus(PSSchema schema) {
		this.schemaArray.add(schema);
		this.isSingleSchemaApp = this.schemaArray.size() == 1;
		//this.reqRespObject().takeValueForKeyPath(schema.persistenceUnitName, "cliSelectedOption.selectedPUName");
		//this.reqRespObject().takeValueForKeyPath(schema.uniqueID(), "cliSelectedOption.selectedSchemaID");
	}

	public void addSchema(PSSchema schema) {
		this.schemaArray.add(schema);
	}

	public PSSchema defaultSchema() {
		return this.schemaArray.objectAtIndex(0);
	}
	public PSEntityManager newEntityManager() {
		return defaultSchema().newEntityMgr();
	}

	/*public PSSchema schemaForID(String id) {
		return this.schemaArray.objectForFirstLevelMap(id);
	}*/

	/*public PSSchema getSchema(String persistenceUnitName) {
		return this.schemaArray.evaluateQualHashToObject(new AFHashMap()<String, Object>("persistenceUnitName"," persistenceUnitName"));
	}

	public PSSchema currentSchema() {
		return this.isSingleSchemaApp ? this.defaultSchema() : this.selectedSchema();
	}

	public PSSchema safeSelectedSchema() {
		try {
			return this.selectedSchema();
		} catch (RuntimeException e) {
			return null;
		}
	}

	public PSSchema selectedSchema() {
		if (this.isSingleSchemaApp) {
			return this.defaultSchema();
		}
		PSSchema selectedSchema = this.schemaArray.objectForFirstLevelMap(this.reqRespObject().cliSelectedOption().selectedSchema());
		if (selectedSchema != null) {
			return selectedSchema;
		} else {
			throw new RuntimeException("Need to override selectedSchema() method in xxCliSelectedOption class in implementing project");
		}
	}

	public FNArrayList<?> globalSearchResults(PSModelEntitySetup modelEntitySetup, PSQualSetup qualSetup, FNHashMap<String, Object> qualValueMap) {
		return modelEntitySetup.getResults(FNReqRespThreadFactory.factory().getReqRespObject().reqEM(), qualSetup, qualValueMap);
	}

	public FNArrayList<?> searchResults(PSEntityManager psEntityManager, PSModelEntitySetup modelEntitySetup, PSQualSetup qualSetup, FNHashMap<String, Object> qualValueMap) {
		return modelEntitySetup.getResults(psEntityManager, qualSetup, qualValueMap);
	}

	// This method will generally be not required. May be required only in special situations
	public PSEntityManager newEntityManager() {
		return this.isSingleSchemaApp() ? this.defaultSchema().verifiedNewEntityManager() : this.selectedSchema().verifiedNewEntityManager(); // return new entity manager here. Return value must be cached at calling end to do their operations
	}

	public Query createQueryUsingReqEM(String sql) {
		return FNReqRespThreadFactory.factory().getReqRespObject().reqEM().createQuery(sql);
	}

	public void checkLiveConnection() {
		for (PSSchema psSchema : this.schemaArray) {
			if (FNConstants.isKeepLiveSchema) {
				psSchema.isLive();
			}
		}
	}

	public boolean removeSchema(PSSchema psSchema) {
		return this.schemaArray.remove(psSchema);
	}*/

}
