package com.alfalahsoftech.ui.object;

import java.util.Date;

import com.alfalahsoftech.alframe.AFArrayList;
import com.alfalahsoftech.alframe.AFHashMap;
import com.alfalahsoftech.inv.entity.EOStuff;

public class EOStuffUIO {
	public Long primaryKey;
	public Date busiDate;
	public Double ttMade =new Double(0.0);
	public Double ttBundled =new Double(0.0);
	public Double ttDried =new Double(0.0);
	public int workersCount;
	public int workersPresent;
	public Integer isCloseDay= new Integer(0);
	public String closeDayReason="N/A";
	public String storeName;
	
	public  EOStuffUIO(EOStuff copyFrom) {
		
		if(copyFrom != null) {
			this.busiDate = (copyFrom.getBusiDate());
			this.primaryKey=(copyFrom.primaryKey());
			this.ttMade = (copyFrom.getTtMade());
			this.workersPresent=0;
			this.workersCount=copyFrom.getStuffDedtail().size();
			AFHashMap<Long, Object> hash = new AFHashMap<>();
			AFArrayList list = new  AFArrayList<>();
			copyFrom.getStuffDedtail().forEach(eo->{
				long v = eo.getEoStaff().primaryKey().longValue();
				
				if( !list.contains(v) && eo.getIsPresent().intValue() == 1) {
					this.workersPresent++;
					list.add(v);
				}
				
				this.ttMade += eo.getTtMade().longValue();
				this.ttDried += eo.getTtDried().longValue();
				this.ttBundled += eo.getTtBundled().longValue();
			
			});
			this.isCloseDay = (copyFrom.getIsCloseDay());
			this.closeDayReason = (copyFrom.getCloseDayReason());
			this.storeName = (copyFrom.getEoStore().getStoreName());
		}
		
	}

}