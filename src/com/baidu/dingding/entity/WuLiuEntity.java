package com.baidu.dingding.entity;

public class WuLiuEntity {
	//" currentPlace ":"深圳站点",
	//"message":"已经揽货"," 
	//datetime ":"2015-08-26 11:26:08"
	//" currentPlace ":"广州站点",
	//"message":"中转",
	//" datetime ":"2015-08-27 11:26:08"
	private String currentPlace;
	private String message;
	private String detetime;	
	public WuLiuEntity() {
		super();
	}
	public WuLiuEntity(String currentPlace, String message, String detetime) {
		super();
		this.currentPlace = currentPlace;
		this.message = message;
		this.detetime = detetime;
	}
	public String getCurrentPlace() {
		return currentPlace;
	}
	public void setCurrentPlace(String currentPlace) {
		this.currentPlace = currentPlace;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getDetetime() {
		return detetime;
	}
	public void setDetetime(String detetime) {
		this.detetime = detetime;
	}
	@Override
	public String toString() {
		return "WuLiuEntity [currentPlace=" + currentPlace + ", message="
				+ message + ", detetime=" + detetime + "]";
	}	
}
