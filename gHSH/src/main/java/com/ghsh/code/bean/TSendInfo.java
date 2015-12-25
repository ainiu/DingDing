package com.ghsh.code.bean;

import java.io.Serializable;

public class TSendInfo implements Serializable{
	private static final long serialVersionUID = 1L;

	private String sendInfoID;
	
	private String consignee;//收货人姓名
	
	private String zipcode;//邮编
	
	private String mobile;//手机号码
	
	private String tel;//座机号码
	
	private String regionName;//省市区
	
	private String address;//详细地址
	
	private String country;//国家
	
	private String province;//省
	
	private String city;//市
	
	private String district;//区
	
	private boolean isDefault;//是否默认
	
	public String getSendInfoID() {
		return sendInfoID;
	}

	public void setSendInfoID(String sendInfoID) {
		this.sendInfoID = sendInfoID;
	}

	public String getConsignee() {
		return consignee;
	}

	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public boolean isDefault() {
		return isDefault;
	}

	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
}
