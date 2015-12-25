package com.ghsh.code.bean;

import java.io.Serializable;

public class TBankAccount implements Serializable{
	private static final long serialVersionUID = 1L;

	private String id;
	
	private String bankID;//银行名称
	
	private String bankName;//银行名称
	
	private String bankSN;//银行卡号
	
	private String name;//姓名
	
	private String mobile;//手机
	
	private String addTime;//添加时间
	
	private int imageRes;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBankID() {
		return bankID;
	}

	public void setBankID(String bankID) {
		this.bankID = bankID;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankSN() {
		return bankSN==null?"":bankSN;
	}

	public void setBankSN(String bankSN) {
		this.bankSN = bankSN;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAddTime() {
		return addTime;
	}

	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}

	public int getImageRes() {
		return imageRes;
	}

	public void setImageRes(int imageRes) {
		this.imageRes = imageRes;
	}
}
