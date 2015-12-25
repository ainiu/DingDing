package com.ghsh.code.bean;

import java.io.Serializable;
/**
 * 支付方式表
 * */
public class TPayment implements Serializable{

	private static final long serialVersionUID = 1L;
	private String paymentID;//支付自增ID
	private String paymentCode	;//  支付代码
	private String paymentName;//  支付名称
	private String paymentDesc;//支付简介
	private boolean isOnline;//是否在线支付
	
	private boolean isSelect=false;//是否选择
	
	
	public String getPaymentID() {
		return paymentID;
	}
	public void setPaymentID(String paymentID) {
		this.paymentID = paymentID;
	}
	public String getPaymentCode() {
		return paymentCode;
	}
	public void setPaymentCode(String paymentCode) {
		this.paymentCode = paymentCode;
	}
	public String getPaymentName() {
		return paymentName;
	}
	public void setPaymentName(String paymentName) {
		this.paymentName = paymentName;
	}
	public String getPaymentDesc() {
		return paymentDesc;
	}
	public void setPaymentDesc(String paymentDesc) {
		this.paymentDesc = paymentDesc;
	}
	public boolean isSelect() {
		return isSelect;
	}
	public void setSelect(boolean isSelect) {
		this.isSelect = isSelect;
	}
	public boolean isOnline() {
		return isOnline;
	}
	public void setOnline(boolean isOnline) {
		this.isOnline = isOnline;
	}

}
