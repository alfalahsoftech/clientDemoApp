package com.alfalahsoftech.inv.entity;

import java.text.ParseException;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.swing.text.MaskFormatter;

import com.alfalahsoftech.ui.object.EOOrderDayDetailUIO;
import com.alfalahsoftech.web.AFObject;
@Entity
@SequenceGenerator(name="EOOrderDayDetail_SEQ", allocationSize=1,sequenceName="EOOrderDayDetail_SEQ")
public class EOOrderDayDetail extends AFMainEntity{

	private static final long serialVersionUID = 1L;
	//	@Column(insertable=false,updatable=false)
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator="EOOrderDayDetail_SEQ")
	private Long primaryKey;
	private String orderNo;
	private String itemID;
	private String orderStatus;
	public String notes;
	private Integer quantity=  Integer.valueOf(0);
	@Temporal(TemporalType.DATE)
	private Date orderDate;
	private Double unitCost;
	private Double gstPerc;
	private String name;

	//Relations
	@OneToOne
	@JoinColumn(name="eoClientPK")
	private EOClient eoClient;
	@OneToOne
	@JoinColumn(name="eoItemPK")
	private EOItem eoItem;
	@ManyToOne()
	@JoinColumn(name="eoOrderDayPK")
	private EOOrderDay orderDay;

	@Override
	public Long primaryKey() {
		return this.primaryKey;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	/*public String getItemID() {
		return itemID;
	}

	public void setItemID(String itemID) {
		this.itemID = itemID;
	}
	 */
	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public EOClient getEoClient() {
		return eoClient;
	}

	public void setEoClient(EOClient eoClient) {
		this.eoClient = eoClient;
	}

	public EOItem getEoItem() {
		return eoItem;
	}

	public void setEoItem(EOItem eoItem) {
		this.eoItem = eoItem;
	}

	public EOOrderDay getOrderDay() {
		return orderDay;
	}

	public void setOrderDay(EOOrderDay orderDay) {
		this.orderDay = orderDay;
	}

	public String getItemID() {
		return itemID;
	}

	public void setItemID(String itemID) {
		this.itemID = itemID;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	



	///////////////////////////



	public Double getUnitCost() {
		return unitCost;
	}

	public void setUnitCost(Double unitCost) {
		this.unitCost = unitCost;
	}

	public Double getGstPerc() {
		return gstPerc;
	}

	public void setGstPerc(Double gstPerc) {
		this.gstPerc = gstPerc;
	}
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
	public EOOrderDayDetailUIO getUIEOOrderDayDetail() {
		return new EOOrderDayDetailUIO(this);
	}








}