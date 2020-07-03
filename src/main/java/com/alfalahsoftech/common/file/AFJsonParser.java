package com.alfalahsoftech.common.file;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.alfalahsoftech.alframe.AFConstant;
import com.alfalahsoftech.web.AFApplicationObject;
import com.alfalahsoftech.web.AFObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

public class AFJsonParser extends AFObject{
	private static final long serialVersionUID = 1L;

	public static <T>  T parseFile(File file,Class<T> cls){
		//		JsonParser json = new JsonParser();
		try(FileReader reader =new FileReader(file)) {
			/*	e = json.parse(reader);
			System.out.println(e.isJsonObject());
			JsonObject j = ( JsonObject)e;*/

			Gson g = new Gson();
			T dbConfig =	g.fromJson(reader,cls);
			return dbConfig;

		} catch (JsonIOException e1) {
			e1.printStackTrace();
		} catch (JsonSyntaxException e1) {
			e1.printStackTrace();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		return null;
	}

	static class PayRollAudDetail{
		public String userName;
		public String userID;//##Comment:Mantis:NA:CRNUM:SS2928: Labor Exception Report Enhancements Part 2 (manager name in feed)
		public String userFirstName;
		public String userLastName;
		public String dateTime;
		public String storeDateTimeDST;
		public String description;//
		public String type;//DAILY,WEEKLY,PAYROLL
		public Number fkHeaderId;// TdyMain,TwkMain,PayRollMain
		public Number fkEmpMainId;// Emp Pk
		public Number fkPunchId;
		public String punchType;
		public Integer fkStrMainId;// Temp
		public String busiDateStr;
		public String newValue;
		public String oldValue;
		public String action;
		public boolean isMgrAuth;
		public String mgrAuthName;
		public Number fkBreakPunchId;
		public String mgrAuthPunchType;
		public String mgrTimeCard;
		public String punchBusiDate;
		public String millis = "0";//##Comment:Mantis:29719:CRNUM:NA If user is opening Archived Daily Timekeeping objects then its throwing an exception
		public boolean isModified;
		public Number isAcrossDay=0;//##Comment:Mantis:NA:CRNUM:CR_00290 Split Payroll in eTimekeeping
		
	}
	
	public static final  java.lang.reflect.Type type = new com.google.gson.reflect.TypeToken<List<PayRollAudDetail>>(){}.getType(); //##Comment:Mantis:35613:CRNUM:NA Handling on special characters in the User name
	
	public static List<Object> parse(File file){
		try {
			return stringToJson(jsonToSingleString(new FileReader(file)));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static String  jsonToSingleString(File file ){
		try {
			return jsonToSingleString(new FileReader(file));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static String  jsonToSingleString(Reader reader ){
		StringBuilder jsonString  = new StringBuilder();
		BufferedReader bfrReader = new BufferedReader(reader);
		try{
			for(String line = bfrReader.readLine(); line != null; line= bfrReader.readLine()){
				jsonString.append(line).append(" ");
			}
		}catch(Exception ex){

		}
		return jsonString.toString();
	}

	public static List<Object> stringToJson(String jsonString){
		List<Object> list=null;
		try {
			list =	jsonArrToList(new JSONArray(jsonString));
			System.out.println("Lis===> "+list);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return list;
	}

	public static List<Object> jsonArrToList(JSONArray jsonArray) throws JSONException{
		ArrayList<Object>  list = new  ArrayList<>();
		Object obj = null;
		int len = jsonArray.length();
		if(jsonArray != null){
			for(int i  = 0; i <len; i++){
				obj = jsonArray.get(i);
				if(obj instanceof JSONObject){
					JSONObject  jsonObj = (JSONObject)obj;
					list.add(toMap(jsonObj));
				}else{
					list.add((JSONArray)obj);
				}
			}
		}
		return list;
	}

	public static  HashMap<String,Object> toMap(JSONObject jsonMap) throws JSONException{
		HashMap<String,Object> hashMap  = new  HashMap<>();
		Iterator<String> it  = jsonMap.keys();
		while(it.hasNext()){
			String  key = it.next();
			hashMap.put(key, fromJson(jsonMap.get(key)));

		}
		return hashMap;
	}

	public static  List<Object> toList(JSONArray array) throws JSONException{
		ArrayList<Object> list = new ArrayList<>();
		for (int i = 0; i < array.length(); i++) {
			list.add(array.get(i));
		}
		return list;
	}
	public  static  Object fromJson(Object jsonObj) throws JSONException{
		if(jsonObj != null){
			if(jsonObj instanceof JSONObject){
				return toMap((JSONObject)jsonObj);
			}else if(jsonObj instanceof JSONArray){
				return toList((JSONArray)jsonObj);
			}
		}
		return jsonObj;	
	}
	
	public static ArrayList<String> getAllMedi(){

		File file = new File(AFApplicationObject.META_PATH+"glbDir/medi.txt");
		ArrayList<String> mediList = new ArrayList<>();
		try {
		FileReader fr = new FileReader(file);
		BufferedReader bfr = new BufferedReader(fr);
		String line  = bfr.readLine();
		while (line != null) {
			System.out.println(line);
			if(line.trim().equalsIgnoreCase("<tr>")) {
				line  = bfr.readLine();
				line  = bfr.readLine();
				line = line.replace("<td>", "").replace("</td>","");
				mediList.add(line.trim());
				
			}
			line  = bfr.readLine();
		}
		}catch(Exception e) {
			e.printStackTrace();
		}
		System.out.println(mediList);
		return mediList;
	}

	public static void main(String[] args) {
		
		System.out.println("ssssssssssssparsing----------------");
		
		File file = new File("./src/main/resources/META-INF/glbDir/medi.txt");
		ArrayList<String> mediList = new ArrayList<>();
		try {
		FileReader fr = new FileReader(file);
		BufferedReader bfr = new BufferedReader(fr);
		String line  = bfr.readLine();
		while (line != null) {
			System.out.println(line);
			if(line.trim().equalsIgnoreCase("<tr>")) {
				line  = bfr.readLine();
				line  = bfr.readLine();
				line = line.replace("<td>", "").replace("</td>","");
				mediList.add(line.trim());
				
			}
			line  = bfr.readLine();
		}
		}catch(Exception e) {
			e.printStackTrace();
		}
		System.out.println(mediList);
		
		//		File file = new File(AFWebContextListener.contextPath+"src/main/resources/META-INF/glbDir/menu/menu.txt");
		file = new File("./src/main/resources/META-INF/glbDir/menu/menu.txt");
		//		File file = new File("X:/Other_Workspace/CentralMonitoring/src/main/resources/META-INF/setup/smsAdaptors.json");
		try {

		Object obj=	getGsonObject().fromJson(jsonToSingleString(new File("./src/main/resources/META-INF/glbDir/menu/tdyaudmainjson.json")),type);
		System.out.println(obj);

		/*	String jsonString= jsonToSingleString(new FileReader(file));
			HashMap<String,Object> m = (HashMap<String,Object>)stringToJson(jsonString).get(0);
			System.out.println(m);
			System.out.println("Liiiiii-= ");*/
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public static Gson getGsonObject(){
		GsonBuilder builder = new GsonBuilder();
		builder.disableHtmlEscaping();
		return builder.create();
	}
}


