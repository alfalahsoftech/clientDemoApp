package com.alfalahsoftech.common.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.ListIterator;
import java.util.function.Consumer;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.alfalahsoftech.alframe.AFArrayList;
import com.alfalahsoftech.alframe.AFHashMap;
import com.alfalahsoftech.alframe.util.AFDateUtil;
import com.alfalahsoftech.controller.AFBaseController;
import com.alfalahsoftech.inv.entity.EOItemSold;
import com.alfalahsoftech.inv.entity.EOStuff;
@Path("dboard")
public class DashBoard extends AFBaseController{
	private static final long serialVersionUID = 1L;

	@GET
	@Path("/today")
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public Response todayInfo() {
		System.out.println("todayInfo==");
		List<EOItemSold> soldItemList = this.reqRespObjec().reqEM().createQuery("select e from EOItemSold e").getResultList();
		System.out.println("soldItemList== "+soldItemList.size());

		double ttAmount = 0.0;
		int itemCount=0;
		List<ItemSold> soldItems = new ArrayList<>();
		for(EOItemSold item:soldItemList) {
			ttAmount+= item.getTotalPrice().doubleValue();
			itemCount+=item.getQuantity();

			//need to change use HasMap soldItems will not contain obj of ItemSold
			ItemSold localItem =new ItemSold(item);
			System.out.println("localItem.itemName=>"+localItem.itemName);
			if(!soldItems.contains(localItem)) {
				soldItems.add(localItem);
			}else {
				System.out.println("soldItems= "+soldItems);
				System.out.println("soldItems.indexOf(localItem)== "+soldItems.indexOf(localItem));
				ItemSold it =soldItems.get(soldItems.indexOf(localItem));
				it.ttAmount +=item.getTotalPrice().doubleValue();
				it.itemSoldCount +=item.getQuantity();
			}
		}
		ItemSold item = new ItemSold();
		item.itemSoldCount = itemCount;
		item.ttAmount = ttAmount;
		AFHashMap<String,Object> map = new AFHashMap<>();
		map.put("total", item);
		map.put("items", soldItems);
		map.put("currentDayData", currentDayData());
		map.put("prevDayData", prevDayData());
		map.put("last7DaysData", last7DaysData());
		map.put("agbtStatus",todayNPrevDayAgbtStatus());
		return this.createResponse(map);
	}

	public AFHashMap<String, Object> currentDayData(){
		String q ="select e from EOItemSold e where  soldon>='"+AFDateUtil.currentDate()+"'";
		List<EOItemSold> soldItemList = this.reqRespObjec().reqEM().createQuery(q).getResultList();
		return  calculateSales(soldItemList);
	}
	
	@SuppressWarnings("unchecked")
	public AFHashMap<String, Object> prevDayData(){
		String q ="select e from EOItemSold e where  soldon>='"+AFDateUtil.prevDay()+"' and soldon<='"+AFDateUtil.currentDate()+"'";
		List<EOItemSold> soldItemList = this.reqRespObjec().reqEM().createQuery(q).getResultList();
		return calculateSales(soldItemList);
	}
	
	public AFHashMap<String, Object>  calculateSales(List<EOItemSold> soldItemList) {
		ListIterator<EOItemSold> itr=soldItemList.listIterator();
		AFHashMap<String, Object> currDataHash = new AFHashMap<>();
		int ttQnty=0;
		double ttCash=0;
		while(itr.hasNext()) {
			EOItemSold itmSold=itr.next();
			ttQnty +=itmSold.getQuantity().intValue();
			ttCash +=itmSold.getTotalPrice().doubleValue();
		}
		currDataHash.put("ttQnty", ttQnty);
		currDataHash.put("ttCash", ttCash);
		return currDataHash;
	}


	public List<EOItemSold> last7DaysData(){
		String q ="select e from EOItemSold e where soldon>='"+AFDateUtil.last7Days()+"' and soldon<='"+AFDateUtil.currentDate()+"'";
		List<EOItemSold> soldItemList = this.reqRespObjec().reqEM().createQuery(q).getResultList();
		return soldItemList;
	}


	public AFHashMap<String, Object>  todayNPrevDayAgbtStatus() {
		AFHashMap<String, Object> localHash = new AFHashMap<>();
		
		List<EOStuff> stuffList = this.getObjects(EOStuff.class,"busiDate='"+AFDateUtil.currentDate()+"'");
		CClass newCls=new CClass();
		stuffList.forEach(newCls);
		localHash.put("todayAgbtMade", newCls.ttMade);
		////////////////Previous Day
		stuffList = this.getObjects(EOStuff.class,"busiDate='"+AFDateUtil.prevDay()+"'");
		//to avoid effectively final compile-time error
		int ttAgbtMadeQnt[]= {0};
		
		stuffList.forEach(eo->{
			ttAgbtMadeQnt[0]+=eo.getTtMade().intValue();
		});
		localHash.put("prevDayAgbtMade", ttAgbtMadeQnt[0]);
		return localHash;
	}

	@Override
	public Object callMethod(AFHashMap<String, Object> objectHash) {
		return null;
	}

	@Override
	public void addResponse() {

	}

}
class CClass implements Consumer<EOStuff>{
	int ttMade=0;
	@Override
	public void accept(EOStuff t) {
		ttMade +=t.getTtMade().intValue();

	}


}
class ItemSold{
	String itemName;
	Integer itemSoldCount;
	double ttAmount;
	ItemSold(){}
	ItemSold(EOItemSold item){
		this.itemName = item.getName();
		this.itemSoldCount = item.getQuantity();
		this.ttAmount = item.getTotalPrice().doubleValue();
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		if( obj == null|| getClass()!=obj.getClass())
			return false ;

		if(obj instanceof ItemSold) {
			ItemSold it =(ItemSold)obj;
			if(!it.itemName.equals(this.itemName)) {
				return false;
			}
		}else {
			return false;
		}

		return true;
	}
	@Override
	public int hashCode() {

		return this.itemName.hashCode()*31;
	}

	@Override
	public String toString() {
		return "{"+this.itemName+"<"+this.ttAmount+">}";
	}
}
