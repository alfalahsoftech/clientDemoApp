package com.alfalahsoftech.alframe.setup;


import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Objects;

import org.json.JSONObject;

import com.alfalahsoftech.alframe.util.AFJsonParserUtil;
import com.alfalahsoftech.common.file.AFJsonParser;
import com.alfalahsoftech.web.AFApplicationObject;
import com.alfalahsoftech.web.AFObject;
import com.alfalahsoftech.web.AFWebContextListener;
@SuppressWarnings({"serial", "unused"})
public class AFGlobalSetup extends AFObject{
	public boolean isStoreAdmin;
	public boolean isSettingAccess;
	public String extraEmail;
	public Long primaryKey =1l;

	private static AFGlobalSetup globalSetup;

	private AFGlobalSetup() {
		//		intProperties();

	}
	public static AFGlobalSetup flags() {
		if(globalSetup == null) {
			synchronized (AFGlobalSetup.class) {
				if(globalSetup == null) {
					globalSetup = new AFGlobalSetup();
				}
			}
		}
		return globalSetup;
	}

	@Override
	public void initObject() {
		String str =AFJsonParser.jsonToSingleString(new File(AFApplicationObject.META_PATH+"glbDir/setup/glbSetup.json"));
		JSONObject jDetail = new JSONObject(str);
		str = jDetail.toString();
		AFSetup af = (AFSetup) this.reqRespObject().reqEM().getUniqueResult(AFSetup.class,"SELECT e FROM AFSetup e WHERE primaryKey="+primaryKey);
		if(Objects.isNull(af)) {
			af = new AFSetup();
			af.setDetail(str);
			af.setIid("AF_GLB_SETUP");
			af.setPrimaryKey(primaryKey);
			this.reqRespObject().startTransaction();
			this.reqRespObject().reqEM().persist(af);
			this.reqRespObject().endTransaction();
		}
		if(!str.equalsIgnoreCase(af.getDetail())) {
			print("//////////////// Difference found/////////////////");
//			JSONObject jDetail2 = new JSONObject(af.getDetail());
//			jDetail.keySet().forEach(key->{
//				if(!jDetail2.has(key)) {
//					print("kkkkkkkkkkkkkk "+key);
//					jDetail2.put(key, jDetail.get(key));
//				}
//			});
//			af.setDetail(jDetail2.toString());
//			this.reqRespObject().startTransaction();
//			this.reqRespObject().reqEM().merge(af);
//			this.reqRespObject().endTransaction();
		}
		printObj(globalSetup);
		globalSetup= this.getObjFromStr(AFGlobalSetup.class, af.getDetail());
		printObj(globalSetup);
	}

	private String details(AFGlobalSetup af) {
		JSONObject json = new JSONObject();
		Arrays.stream(this.getClass().getDeclaredFields()).forEach(field->{
			try {
				this.takeValueForKey(field.get(af),field.getName());
				json.accumulate(field.getName(), field.get(af));

			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		});
		return json.toString();
	}


}
