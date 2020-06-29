package com.alfalahsoftech.inv.entity;

import java.text.ParseException;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.swing.text.MaskFormatter;

import com.alfalahsoftech.ui.object.EOOrderDayUIO;
@Entity
@SequenceGenerator(name="EOOrderDay_SEQ", allocationSize=1,sequenceName="EOOrderDay_SEQ")
public class EOOrderDay extends AFMainEntity{
	private static final long serialVersionUID = 1L;
	//	@Column(insertable=false,updatable=false)
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator="EOOrderDay_SEQ")
	private Long primaryKey;
	private Date orderDate;
	@Temporal(TemporalType.DATE)
	private Date orderedOn;
	private Integer ttItem;
	private String orderNo;
	private String orderStatus;
	public String notes;


	@OneToMany(mappedBy="orderDay",cascade = CascadeType.PERSIST, fetch=FetchType.EAGER)
	Set<EOOrderDayDetail> orderDayDetail = new LinkedHashSet<EOOrderDayDetail>();

	@OneToOne
	@JoinColumn(name="eoClientPK")
	private EOClient eoClient;

	@Override
	public Long primaryKey() {
		return this.primaryKey;
	}

	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	public Integer getTtItem() {
		return ttItem;
	}
	public void setTtItem(Integer ttItem) {
		this.ttItem = ttItem;
	}
	public Set<EOOrderDayDetail> getOrderDayDetail() {
		return orderDayDetail;
	}
	public void setOrderDayDetail(Set<EOOrderDayDetail> orderDayDetail) {
		this.orderDayDetail = orderDayDetail;
	}


	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Date getOrderedOn() {
		return orderedOn;
	}

	public void setOrderedOn(Date orderedOn) {
		this.orderedOn = orderedOn;
	}
	public EOClient getEoClient() {
		return eoClient;
	}

	public void setEoClient(EOClient eoClient) {
		this.eoClient = eoClient;
	}
	public  String orderNo() {
		return maskString(this.primaryKey() + "", '0', "**********");
	}

	public  String maskString(String rawStr, char c, String format) {
		StringBuilder buffer = new StringBuilder();
		while (rawStr.length() + buffer.length() < 10) {
			buffer.append(c);
		}
		buffer.append(rawStr);
		try {
			MaskFormatter maskFormatter = new MaskFormatter(format);
			maskFormatter.setValueContainsLiteralCharacters(false);
			return maskFormatter.valueToString(buffer);
		} catch (ParseException e) {
			System.out.println("ParsingException=>" +e.getMessage());
		}
		return buffer.toString();
	}
	
	public EOOrderDayUIO  getUIEOOrderDay(EOOrderDay eo){
		return new EOOrderDayUIO(eo);	
	}
	
	
}