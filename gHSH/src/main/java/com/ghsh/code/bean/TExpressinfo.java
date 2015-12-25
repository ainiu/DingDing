package com.ghsh.code.bean;

import java.util.ArrayList;
import java.util.List;


/**
 * 物流信息
 * */
public class TExpressinfo {

	private String stateText;
	
	private String shippingName;
	
	private String invoiceNO;
	
	private List<Info> infoList=new ArrayList<Info>();
	
	public String getStateText() {
		return stateText;
	}

	public void setStateText(String stateText) {
		this.stateText = stateText;
	}

	public String getShippingName() {
		return shippingName;
	}

	public void setShippingName(String shippingName) {
		this.shippingName = shippingName;
	}

	public String getInvoiceNO() {
		return invoiceNO;
	}

	public void setInvoiceNO(String invoiceNO) {
		this.invoiceNO = invoiceNO;
	}

	public List<Info> getInfoList() {
		return infoList;
	}

	public void setInfoList(List<Info> infoList) {
		this.infoList = infoList;
	}

	public static class Info{
		public String time;
		public String context;
	}
}
