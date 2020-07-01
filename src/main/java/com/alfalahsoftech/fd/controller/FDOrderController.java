package com.alfalahsoftech.fd.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.json.JSONArray;
import org.json.JSONObject;

import com.alfalahsoftech.alframe.AFArrayList;
import com.alfalahsoftech.alframe.AFHashMap;
import com.alfalahsoftech.alframe.annotation.AFController;
import com.alfalahsoftech.alframe.annotation.ReqMthdDgntr;
import com.alfalahsoftech.alframe.setup.AFGlobalSetup;
import com.alfalahsoftech.alframe.util.AFJsonParserUtil;
import com.alfalahsoftech.controller.UserController;
import com.alfalahsoftech.controller.WidgetsResource;
import com.alfalahsoftech.exception.AFExceptionMapper;
import com.alfalahsoftech.inv.entity.AFMainEntity;
import com.alfalahsoftech.inv.entity.EOClient;
import com.alfalahsoftech.inv.entity.EOClientItem;
import com.alfalahsoftech.inv.entity.EOItem;
import com.alfalahsoftech.inv.entity.EOItemSold;
import com.alfalahsoftech.inv.entity.EOOrderDay;
import com.alfalahsoftech.inv.entity.EOOrderDayDetail;
import com.alfalahsoftech.inv.entity.EORoute;
import com.alfalahsoftech.inv.entity.EOStaff;
import com.alfalahsoftech.inv.entity.EOStore;
import com.alfalahsoftech.inv.entity.EOStuff;
import com.alfalahsoftech.inv.entity.EOStuffDetail;
import com.alfalahsoftech.inv.entity.LKJobCode;
import com.alfalahsoftech.medi.entity.EOMedicine;
import com.alfalahsoftech.service.UserService;
import com.alfalahsoftech.service.UserSrvcImpl;
import com.alfalahsoftech.ui.object.DailyLbrDetail;
import com.alfalahsoftech.ui.object.EOItemUIO;
import com.alfalahsoftech.ui.object.EOOrderDayDetailUIO;
import com.alfalahsoftech.ui.object.EOStuffUIO;
import com.alfalahsoftech.web.AFObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@AFController
@Path("food")
@SuppressWarnings({"unchecked","rawtypes","serial"})
public class FDOrderController extends FoodBaseController {

	Logger log= Logger.getLogger(FDOrderController.class);
	static UserService userSrvc = new UserSrvcImpl();

	@Context
	SecurityContext securityCtx;

	@Context
	Application application;

	@Path("/addItem")
	@POST
	@Consumes({ MediaType.APPLICATION_JSON})
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response addItem(String itemDetails) {
		print("Request===> "+itemDetails);
		EOItem item = this.getObjFromStr(EOItem.class, itemDetails);

		userSrvc.insertEntity(item);

		System.out.println("item " +item.primaryKey());
		System.out.println("item== "+this.gson().toJson(item));
		return this.createResponse(item);	
	}


	@POST
	@Path("/allItems")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public Response allItems() {
		//this.reqRespObject().startTransaction();
		List<EOItem> itemList = this.reqRespObject().reqEM().createQuery("SELECT e FROM EOItem e").getResultList();
		//	this.reqRespObject().endTransaction();
		List<EOItem> finalList = new ArrayList<>();

		itemList.stream().forEach(it->{
			it.setIsActive(0);
			finalList.add(it);

		});
		System.out.println("this.createResponse(itemList); = " +this.createResponse(finalList));
		return this.createResponse(finalList);
	}

	@POST
	@Path("/allClientItems")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public Response allClientItems() {
		List<EOClientItem> itemList = this.reqRespObject().reqEM().createQuery("FROM EOClientItem ").getResultList();
		List<EOItemUIO> finalList = new ArrayList<>();
		itemList.stream().forEach(it->{
			EOItemUIO eo = new EOItemUIO(it);
			finalList.add(eo);

		});
		System.out.println("this.createResponse(itemList); = " +this.createResponse(finalList));
		return this.createResponse(finalList);
	}
	//	/allClientItems

	@POST
	@Path("/clientWiseItems")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public Response clientWiseItems(String extraParam) {
		JSONObject obj = jsonObject(extraParam);
		List<EOClientItem> itemList = this.reqRespObject().reqEM().createQuery("FROM EOClientItem e where e.eoClient.primaryKey = " +obj.getLong("clientPK")).getResultList();

		List<EOItemUIO> finalList = new ArrayList<>();

		itemList.stream().forEach(it->{
			EOItemUIO eo = new EOItemUIO(it);
			finalList.add(eo);

		});
		System.out.println("this.createResponse(itemList); = " +this.createResponse(finalList));
		return this.createResponse(finalList);
	}

	@POST
	@Path("/item/{itemPK}")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public Response dispItem(@PathParam("itemPK") String itemPK) {
		EOItem item = (EOItem)this.reqRespObject().reqEM().createQuery("SELECT e FROM EOItem e where primaryKey="+itemPK).getResultList().get(0);
		return this.createResponse(item);
	}

	@POST
	@Path("/updateItem")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public Response updateItem(String req) {
		print("req--->>>> "+req);	
		EOItem  item = updateObject(EOItem.class,req);
		return this.createResponse(item);
	}

	@POST
	@Path("/delItem/{itemPK}")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public Response deActivateOrDelItem(@PathParam("itemPK") String itemPK) {
		EOItem item = (EOItem)this.reqRespObject().reqEM().createQuery("SELECT e FROM EOItem e where primaryKey="+itemPK).getResultList().get(0);
		this.reqRespObject().reqEM().remove(item);
		return this.createResponse(item);
	}

	public <T> T updateObject(Class<T> t,String req) {
		AFHashMap<String, Object> map =AFJsonParserUtil.toMap(new JSONObject(req));
		printObj(map);
		final T  obj = (T)this.reqRespObject().reqEM().createQuery("SELECT e FROM "+t.getSimpleName()+" e where primaryKey="+map.get(PK),t).getResultList().get(0);
		map.remove(PK);
		AFMainEntity entity = (AFMainEntity)obj;
		map.keySet().forEach(key->{
			print("KEY: "+key+"    VALUE:  "+map.get(key));
			entity.takeValueForKey(map.get(key),key);
		});
		this.reqRespObject().startTransaction();
		this.reqRespObject().reqEM().merge(entity);
		this.reqRespObject().endTransaction();
		return (T)entity;
	}

	@Path("/soldItems")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)

	public Response  saveSoldItems(String req) {
		print("=======> saveSoldItems\n"+req);
		JSONArray jsonArr=	new JSONArray(req);
		AtomicInteger aIntgQnt = new AtomicInteger(0);

		jsonArr.forEach(itemDetails->{	
			printObj(itemDetails);
			EOItemSold itemSold = this.getObjFromStr(EOItemSold.class, itemDetails.toString());
			int v =itemSold.getQuantity();
			v+=aIntgQnt.get();
			aIntgQnt.set(v);
			userSrvc.insertEntity(itemSold);
		});
		System.out.println("aIntgQnt======>"+aIntgQnt.get());

		return this.createResponse("Sold Items Saved!");
	}
	//Sold Items
	@GET
	@Path("/allSoldItems")
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public Response todayInfo() {
		System.out.println("todayInfo==");
		List<EOItemSold> soldItemList = this.reqRespObjec().reqEM().createQuery("select e from EOItemSold e").getResultList();
		System.out.println("soldItemList== "+soldItemList.size());
		return this.createResponse(soldItemList);
	}


	@Path("/addDailyStuff")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response dailyMadeAgr(String reqStr){
		EOStuff stuff = new EOStuff();
		try {
			UIDailyStuff  d=	this.gson().fromJson(reqStr, UIDailyStuff.class);
			Date date= formatedDate(d.createdDate);
			stuff.takeValueForKey(date, "busiDate");
			stuff.takeValueForKey(this.isEmptyStr(d.closeDayReason)?"N/A":d.closeDayReason, "closeDayReason");
			stuff.takeValueForKey(d.isCloseDay?1:0, "isCloseDay");
			stuff.setEoStore(EOStore.newStore(d.eoStore));
			/*
			List<EOStaff> entity = (List<EOStaff>) userSrvc.genericData(EOStaff.class);
			entity.forEach(eo->{
				printObj(eo.getDetails());
				EOStuffDetail stfDetail = new EOStuffDetail();
				stfDetail.setName(eo.getFirstName());
				stfDetail.setBusiDate(date);
				stuff.getStuffDedtail().add(stfDetail);
				stfDetail.setFkStuff(stuff);
			});*/
			userSrvc.insertEntity(stuff);	

			print("End=>>>>>>>>>>dailyMadeAgr>>>");
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return this.createResponse(EOStuff.copyOFEOStuff(stuff));
	}


	@Path("/dispDailyStuff")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response dailyStuff() {
		List<EOStuff> stuffList = (List<EOStuff>) userSrvc.genericData(EOStuff.class);
		AFArrayList<EOStuffUIO> stf = new AFArrayList<>();
		stuffList.forEach(eo->{
			stf.add(new EOStuffUIO(eo));

		});
		return this.createResponse(stf);
	}


	@Path("/workDayDetail/{fkStuff}")
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response workDayDetail(@PathParam("fkStuff") String fkStuff) {
		List<EOStuffDetail> stuffList = this.getObjects(EOStuffDetail.class," fkStuff="+fkStuff);
		AFArrayList<UIEOStuffDetail> stuffDetailList = new AFArrayList<>();
		stuffList.forEach(stuffDetail->{
			stuffDetailList.add(new UIEOStuffDetail(stuffDetail));
		});

		return this.createResponse(stuffDetailList);

	}

	@Path("/upWorkDayDetail")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updWorkDayDetail(String fkStuff) {
		JSONArray j = new JSONArray(fkStuff);
		this.reqRespObjec().startTransaction();
		System.out.println(AFJsonParserUtil.toList(j));
		ListIterator<Object> it= AFJsonParserUtil.toList(j).listIterator();
		int fkStuffID = 0;
		while(it.hasNext()) {
			AFHashMap<String, Object> hash=(AFHashMap<String, Object>) it.next();
			if(hash.get("fkStuffID")!= null) {
				fkStuffID =(int)hash.get("fkStuffID");
				System.out.println("fkStuffID==> "+fkStuffID);
				break;
			}
		}
		EOStuff eoStuff =(EOStuff) this.getObjects(EOStuff.class,"primaryKey="+fkStuffID).get(0);
		List<EOStuffDetail> stuffList =this.afArrayList(eoStuff.getStuffDedtail());
		AFArrayList<EOStuffDetail> afList = new AFArrayList<>();
		stuffList.forEach(stuffDetail->{
			afList.add(stuffDetail);
		});
		AFHashMap<Object, AFObject> hashObj =getHashForKey("primaryKey",afList);
		System.out.println("hashObj============>"+hashObj);
		it= AFJsonParserUtil.toList(j).listIterator();
		int ttMadeKg = 0;
		while(it.hasNext()) {
			AFHashMap<String, Object> hash=(AFHashMap<String, Object>) it.next();
			if(hash.get("fkStuffID")!= null) {
				continue;
			}
			int ttMade =(int)hash.get("qunatity");
			boolean isPresent =(boolean)hash.get("isPresent");
			int primaryKey  =(int)hash.get("primaryKey");
			System.out.println(ttMade+" "+isPresent+" "+primaryKey);
			EOStuffDetail obj=(EOStuffDetail)hashObj.get( Long.valueOf(primaryKey));
			obj.setTtMade( Double.valueOf(ttMade));
			obj.setIsPresent(isPresent?1:0);
			ttMadeKg +=obj.getTtMade().intValue();

		}
		eoStuff.setTtMade( Double.valueOf(ttMadeKg));
		this.reqRespObjec().endTransaction();
		return this.dailyStuff();

	}
	@Path("/addWorkerDayDetail")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addWorkerDayDetail(String fkStuff) {
		print("==============>addWorkerDayDetail");
		JSONArray j = new JSONArray(fkStuff);
		//this.reqRespObjec().startTransaction();
		System.out.println("Data=======>"+AFJsonParserUtil.toList(j));
		ListIterator<Object> it= AFJsonParserUtil.toList(j).listIterator();
		int fkStuffID = 0;
		while(it.hasNext()) {
			AFHashMap<String, Object> hash=(AFHashMap<String, Object>) it.next();
			print("=======>hash=>"+hash);
			if(hash.get("fkStuffID")!= null) {
				fkStuffID =(int)hash.get("fkStuffID");
				System.out.println("addWorkerDayDetail=>fkStuffID==> "+fkStuffID);
				break;
			}
		}

		it= AFJsonParserUtil.toList(j).listIterator();

		EOStuff eoStuff = EOStuff.newEOStuff(Long.valueOf(fkStuffID));
		while(it.hasNext()) {
			AFHashMap<String, Object> hash=(AFHashMap<String, Object>) it.next();  ///[{jobCodePK:"",staffPK:"",isPresent:"",quantity:"",jobCode:"",fkStuffID:"",name:"",busiDate:""};]
			printObj("hash=========>>>>>>>>>>"+hash);
			EOStuffDetail obj = new EOStuffDetail();

			if(hash.stringValue("jobCode").equals("BN")) {
				obj.setTtMade(hash.doubleValue("quantity"));	
			}

			if(hash.stringValue("jobCode").equals("SK")) {
				obj.setTtDried( hash.doubleValue("quantity"));
			}

			if(hash.stringValue("jobCode").equals("BD")) {
				obj.setTtBundled( hash.doubleValue("quantity"));
			}
			obj.setName(hash.stringValue("name"));

			obj.setBusiDate(this.formatedDate("MMM dd, yyyy",hash.stringValue("busiDate")));

			obj.setEoStuff(eoStuff);
			obj.setJobCode(LKJobCode.newJobCode(hash.longValue(("jobCodePK"))));
			obj.setEoStaff(EOStaff.newStaff(hash.longValue(("staffPK"))));
			obj.setIsPresent(hash.boolValue("isPresent")?1:0);
			this.saveObject(obj);

		}
		this.commit();
		return this.dailyStuff();

	}

	@Path("/lbrDayDetail")
	@POST
	@Consumes({ MediaType.APPLICATION_JSON})
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response lbrDayDetail(String reqPacket) {

		JSONObject  jb = new JSONObject(reqPacket);
		long pk = jb.getLong("eoStuffPK");
		List<EOStuffDetail>  list = this.getObjects(EOStuffDetail.class, " eoStuff="+pk);
		printObj(list);

		List<DailyLbrDetail> returnList = new AFArrayList<>();
		java.util.Map<Long,DailyLbrDetail> has = new AFHashMap<>();

		list.forEach(e->{
			DailyLbrDetail lbr = 	has.get(e.getEoStaff().primaryKey());
			if(Objects.isNull(lbr)) {
				lbr = new DailyLbrDetail();
				has.put(e.getEoStaff().primaryKey(),lbr);
				lbr.name = e.getEoStaff().getFirstName();
			}

			if(e.getJobCode().getJobCode().equals("BN")) {
				lbr.ttMade += e.getTtMade().doubleValue();	
			}
			if(e.getJobCode().getJobCode().equals("BD")) {
				lbr.ttBundled += e.getTtBundled().doubleValue();
			}

			if(e.getJobCode().getJobCode().equals("SK")) {
				lbr.ttDried += e.getTtDried().doubleValue();
			}
			has.put(e.getEoStaff().primaryKey(),lbr);
		});

		has.values().forEach(e->{
			returnList.add(e);
		});

		return this.createResponse(returnList);	
	}


	@Path("/addRoute")
	@POST
	@Consumes({ MediaType.APPLICATION_JSON})
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response addRoute(String itemDetails) {
		print("Request===> "+itemDetails);
		EORoute route = this.getObjFromStr(EORoute.class, itemDetails);
		route.takeValueForKey(this.getOrderDays(itemDetails), "orderDays");
		userSrvc.insertEntity(route);

		System.out.println("item " +route.primaryKey());
		System.out.println("item== "+this.gson().toJson(route));
		return this.createResponse(route);	
	}


	@POST
	@Path("/allRoutes")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public Response allRoutes() {
		List<EORoute> itemList = this.reqRespObject().reqEM().createQuery("SELECT e FROM EORoute e").getResultList();
		java.util.List list= new ArrayList();
		itemList.stream().forEach(eo->{
			list.add(eo.getUIRoute(eo));
		});
		System.out.println("this.createResponse(itemList); = " +this.createResponse(list));
		return this.createResponse(list);
	}

	@POST
	@Path("/route/{routePK}")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public Response dispRoute(@PathParam("itemPK") String itemPK) {
		EORoute item = (EORoute)this.reqRespObject().reqEM().createQuery("SELECT e FROM EORoute e where primaryKey="+itemPK).getResultList().get(0);
		return this.createResponse(item);
	}

	@POST
	@Path("/updateRoute")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public Response updateRoute(String req) {
		print("req--->>>> "+req);	
		EORoute  item = updateObject(EORoute.class,req);
		return this.createResponse(item);
	}

	@POST
	@Path("/orderClient/{routePK}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getClientDetails( @PathParam("routePK") String routePK ) {
		java.util.List<EOClient> resultList	=this.entityMgr().createQuery("SELECT e FROM EOClient e WHERE e.eoRoute.primaryKey="+routePK).getResultList();
		java.util.List list= new ArrayList();
		if(resultList != null) {
			resultList.stream().forEach(eo->{
				list.add(eo.getUIEOClient());
			});
		}
		return createResponse(list);
	}


	@Context
	WidgetsResource wgt;
	@Context
	UserController usr;
	@Context
	AFExceptionMapper exp;

	@POST
	@Path("/orderHistory")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response orderHistory() {
		printObj("Setup=============== "+AFGlobalSetup.flags().isSettingAccess);
		System.out.println("exp========="+exp);
		System.out.println("usr========="+usr);
		System.out.println("wgt========= "+wgt);
		System.out.println("application==== "+application);
		System.out.println("application.getSingl==== "+application.getSingletons());
		System.out.println("application.getSingl==== "+application.getProperties());
		System.out.println("securityCtx11111=== "+securityCtx);
		System.out.println("securityCtx=== "+securityCtx.getUserPrincipal());
		java.util.List<EOOrderDay> resultList	=this.entityMgr().createQuery("SELECT e FROM EOOrderDay e ").getResultList();
		java.util.List list= new ArrayList();
		if(resultList != null) {
			resultList.stream().forEach(eo->{
				list.add(eo.getUIEOOrderDay(eo));
			});
		}
		return createResponse(list);
	}



	@POST
	@Path("/orderedItems")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response  orderHistoryItems(String orderDay) {
		System.out.println("orderDay================ "+orderDay);
		int orderDayPK=	jsonObject(orderDay).getInt("orderDayPK");
		AFHashMap<String, Object> map = new AFHashMap<>();
		java.util.List<EOOrderDayDetail> resultList	=this.entityMgr().createQuery("SELECT e FROM EOOrderDayDetail e where e.orderDay="+orderDayPK).getResultList();
		java.util.List list= new ArrayList();

		AtomicReference<Double> ttlPrice  = new AtomicReference<>(0.0);

		if(resultList != null && resultList.size() > 0) {
			resultList.stream().map(v->{
				return v.getEoItem();
			});
			resultList.stream().forEach(eo->{
				EOOrderDayDetailUIO uieoOrderDayDetail = eo.getUIEOOrderDayDetail();
				list.add(uieoOrderDayDetail);
				double totalPrice=0.0;
				totalPrice = ttlPrice.get().doubleValue()+uieoOrderDayDetail.totalPrice;
				ttlPrice.set(totalPrice);

				//				ttlPrice = uieoOrderDayDetail.totalPrice;
			});
			EOOrderDayDetail orderDetail =	resultList.get(0);
			EOClient eoClient = this.entityMgr().find(EOClient.class, orderDetail.getEoClient().primaryKey());

			System.out.println("ttlPrice.get=============== "+ttlPrice.get());
			map.put("ttlPrice",ttlPrice.get());
			map.put("clientDetails",this.getGsonString(eoClient.getUIEOClient()));
			map.put("itemDetails",this.getGsonString(list));
		}

		return this.createResponse(map);
	}

	final static String SUBMITTED= "Submitted";

	@POST
	@Path("/submitOrder")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response submitOrder(String request) {
		EOOrderDay orderDay =new EOOrderDay();
		orderDay.setOrderDate(new Date());
		orderDay.setOrderedOn(new Date());
		orderDay.setOrderStatus(SUBMITTED);
		Session session = this.getSession();
		long pk=(long) session.save(orderDay);
		log.info("insertedff pk====="+pk + "pkkkk "+orderDay.primaryKey());

		print("Request===> "+request);
		JSONObject js=new JSONObject(request);
		JSONArray j =js.getJSONArray("data");
		JSONObject extraPramJson=js.getJSONObject("extraPram");

		EOClient eoClient=	this.entityMgr().find(EOClient.class,extraPramJson.getLong("clientPK"));
		Iterator	it =j.iterator();
		Set<EOOrderDayDetail> list=new LinkedHashSet<>();
		while(it.hasNext()){
			JSONObject obj =(JSONObject) it.next();
			log.info(obj.get("quantity"));
			if(obj.get("quantity") == null || obj.get("quantity").toString().trim().length()==0) {

				log.info("quantity zero");
				continue;
			}else {
				EOOrderDayDetail order = new EOOrderDayDetail();
				order.takeValueForKey( obj.get("quantity"),"quantity" );
				order.takeValueForKey( SUBMITTED,"orderStatus" );
				order.takeValueForKey( obj.get("itemID"),"itemID" );
				order.takeValueForKey( order.orderNo(),"orderNo" );
				order.takeValueForKey( obj.get("notes"),"notes" );
				order.takeValueForKey( new Date(), "orderDate" );
				order.takeValueForKey(obj.get("gstPerc"), "gstPerc");
				order.takeValueForKey(obj.get("unitCost"), "unitCost");
				order.takeValueForKey(obj.get("name"), "name");
				//EOItem eoItem=	this.entityMgr().find(EOItem.class,obj.getLong("primaryKey"));
				order.setEoClient(eoClient);
				//order.setEoItem(eoItem);
				order.setOrderDay(orderDay);
				order.setOrderNo(orderDay.orderNo());
				list.add(order);
			}
		}
		orderDay.setTtItem(list.size());
		orderDay.setEoClient(eoClient);
		orderDay.setOrderNo(orderDay.orderNo());

		orderDay.setOrderDayDetail(list);
		this.commit();

		String resp= "Order has been submitted. Ordered No:"+orderDay.getOrderNo();
		return this.createResponse(resp);	
	}

	@POST
	@Path("/allClients")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public Response allClients() {
		List<EOClient> itemList = this.reqRespObject().reqEM().createQuery("SELECT e FROM EOClient e").getResultList();
		return this.createResponse(itemList);
	}

	@POST
	@Path("/addCliItems")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public Response clientItems(String  itemDetails) {
		print("Request===> "+itemDetails);
		JSONObject js=new JSONObject(itemDetails);
		JSONArray data =js.getJSONArray("data");
		JSONObject extraPramJson=js.getJSONObject("extraPram");
		Session session = this.getSession();


		EOClient eoClient=	this.entityMgr().find(EOClient.class,extraPramJson.getLong("clientPK"));
		Iterator	it = data.iterator();

		while(it.hasNext()){
			JSONObject obj =(JSONObject) it.next();
			print("obj=11111111==> "+obj.toString());
			EOClientItem clientItem = getObjFromStr(EOClientItem.class, obj.toString());
			clientItem.setEoClient(eoClient);
			log.info(obj.get("quantity"));
			long pk=(long) session.save(clientItem);
			//			userSrvc.insertEntity(clientItem);
		}
		this.commit();
		return this.createResponse("{\"status\":success}");
	}




	@ReqMthdDgntr
	public String getMessage() {
		return "@ReqMthdDegntr";
	}

	@ReqMthdDgntr
	public String getRoutes() {
		return "getRoutes";
	}
	class CConsumer implements Consumer<UIEOStuffDetail>{
		@Override
		public void accept(UIEOStuffDetail t) {

		}
	}
	public static void main(String[] args) throws CloneNotSupportedException {
		
				Gson gSon=  new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		String json="[{\"fkStatus\":19},{\"primaryKey\":26,\"busiDate\":\"2019-05-10\",\"ttMade\":2,\"name\":\"sd\",\"isPresent\":true,\"isEditDisabled\":false},{\"primaryKey\":27,\"busiDate\":\"2019-05-10\",\"ttMade\":2,\"name\":\"Jehan\",\"isPresent\":true,\"isEditDisabled\":false},{\"primaryKey\":28,\"busiDate\":\"2019-05-10\",\"ttMade\":0,\"name\":\"Rabbani\",\"isPresent\":false,\"isEditDisabled\":true},{\"primaryKey\":29,\"busiDate\":\"2019-05-10\",\"ttMade\":0,\"isPresent\":false,\"isEditDisabled\":true},{\"primaryKey\":30,\"busiDate\":\"2019-05-10\",\"ttMade\":0,\"name\":\"Sam\",\"isPresent\":false,\"isEditDisabled\":true}]";
		gSon.fromJson("{\"discount\":2,\"expDate\":\"2020-03-12\"}",EOMedicine.class);
		/*JSONArray j = new JSONArray(json);
		System.out.println(j.get(0));


		System.out.println(AFJsonParserUtil.toList(j));
		ListIterator<Object> it= AFJsonParserUtil.toList(j).listIterator();
		while(it.hasNext()) {
			AFHashMap<String, Object> hash=(AFHashMap<String, Object>) it.next();
			if(hash.get("fkStatus")!= null) {
				//int fkStatus =(int)hash.get("fkStatus");

				break;
			}
			int ttMade =(int)hash.get("ttMade");
			boolean isPresent =(boolean)hash.get("isPresent");
			int primaryKey  =(int)hash.get("primaryKey");
			System.out.println(ttMade+" "+isPresent+" "+primaryKey);
		}
*/
		//		AFArrayList<UIEOStuffDetail> ls = new AFArrayList<>();
		//		for (int i = 0; i < j.length(); i++) {
		//			ls.add(gSon.fromJson(j.getJSONObject(i).toString(),UIEOStuffDetail.class));
		//
		//		}
		//		System.out.println(getHashForKey("primaryKey", ls));

		String s = "MI S";
		System.out.println(s.replaceAll(" ", "%20"));

		List<Integer> l = new AFArrayList<>();
		l.add(1);
		l.add(2);
		l.add(3);

		System.out.println(l.toString().replaceAll("\\[|\\]", ""));
		/*		DailyStuff dailyStuff = new DailyStuff();
		dailyStuff.date="13/23/3011";
		DailyStuff dailyStuff2= (DailyStuff) dailyStuff.clone();
		dailyStuff.date="12/23/3011";
		System.out.println(dailyStuff2);
		DailyObj d  = new DailyObj();
		dailyStuff.a.add(d);
		d.dstf=dailyStuff;

		Gson g =new Gson();
		String str = 	g.toJson(dailyStuff2);
		System.out.println(str);*/
	}



}

class DailyStuff implements Cloneable{
	String date;
	Set<DailyObj> a= new LinkedHashSet<>();
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString()+"  ,Date="+this.date;
	}
	protected Object clone() throws CloneNotSupportedException{
		DailyStuff dstf = (DailyStuff) super.clone();
		//		Set<DailyObj> a = (Set<DailyObj>)this.a.clone();
		return dstf;
	}
}

class DailyObj{
	DailyStuff dstf;
}

class UIDailyStuff  extends AFObject{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String  closeDayReason;
	String  createdDate;
	boolean  isCloseDay;
	long eoStore;


}

class UIEOStuffDetailStr  {
	public String primaryKey;
	public String busiDate;
	public String ttMade;
	public String name;
	public String isPresent;
	String isEditDisabled;
	UIEOStuffDetailStr (String arg){}
	UIEOStuffDetailStr (EOStuffDetail detail){
		this.primaryKey = detail.primaryKey().toString();
		this.busiDate = detail.getBusiDate().toString();
		this.ttMade = detail.getTtMade().toString();
		this.name = detail.getName();
		this.isPresent = detail.getIsPresent().toString();
		if(this.isPresent=="1") {
			this.isEditDisabled = "false";
		}

	}
}


class UIEOStuffDetail extends AFObject{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Long primaryKey;
	public Date busiDate;
	public Double ttMade= Double.valueOf(0.0);
	public Double ttBundled= Double.valueOf(0.0);
	public String name;
	public boolean isPresent=false;
	public boolean isEditDisabled=true;
	UIEOStuffDetail(String arg){}
	UIEOStuffDetail(EOStuffDetail detail){
		this.primaryKey = detail.primaryKey();
		this.busiDate = detail.getBusiDate();
		this.ttMade = detail.getTtMade();
		this.name = detail.getName();
		this.isPresent = detail.getIsPresent().intValue()==1?true:false;
		if(this.isPresent) {
			this.isEditDisabled = false;
		}

	}
}