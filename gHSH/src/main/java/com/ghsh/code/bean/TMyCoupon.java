package com.ghsh.code.bean;

import java.io.Serializable;

import com.ghsh.code.volley.util.JSONUtil;


/**
 * 优惠劵
 * */
public class TMyCoupon implements Serializable{
	private static final long serialVersionUID = 1L;

	
	private String couponID;//优惠劵编号
	
	private String couponValue;
	
	private String time;
	
	private String minAmount;
	
	private String sendType;
	
	private String status;//1:待领取 2:已过期 3:已领完

	public String getCouponID() {
		return couponID;
	}

	public void setCouponID(String couponID) {
		this.couponID = couponID;
	}

	public String getCouponValue() {
		return couponValue;
	}

	public void setCouponValue(String couponValue) {
		this.couponValue = couponValue;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getMinAmount() {
		return minAmount;
	}

	public void setMinAmount(String minAmount) {
		this.minAmount = minAmount;
	}

	public String getSendType() {
		return sendType;
	}

	public void setSendType(String sendType) {
		this.sendType = sendType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
