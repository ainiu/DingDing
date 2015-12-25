package com.ghsh.code.bean;

import java.io.Serializable;

public class TPic implements Serializable {
	private static final long serialVersionUID = 1L;

	private String picID;// 编号

	private String sourceUrl;// 原图

	private String imageUrl;// 大图

	private String href;//单击跳转连接
	
	private String isfixed;
	
	private int imageRes;
	
	public TPic(){
	}
	public TPic(int imageRes){
		this.imageRes=imageRes;
	}

	public String getPicID() {
		return picID;
	}

	public void setPicID(String picID) {
		this.picID = picID;
	}

	public String getSourceUrl() {
		return sourceUrl;
	}
	public void setSourceUrl(String sourceUrl) {
		this.sourceUrl = sourceUrl;
	}
	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public int getImageRes() {
		return imageRes;
	}

	public void setImageRes(int imageRes) {
		this.imageRes = imageRes;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}
	public String getIsfixed() {
		return isfixed;
	}
	public void setIsfixed(String isfixed) {
		this.isfixed = isfixed;
	}
}
