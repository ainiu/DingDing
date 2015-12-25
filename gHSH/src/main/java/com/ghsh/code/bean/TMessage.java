package com.ghsh.code.bean;

import java.io.Serializable;

public class TMessage implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String meaasgeID;
	
	private String title;
	
	private String desc;

	private String time;
	
	private int status;//删除 -1  已读 1

	public String getMeaasgeID() {
		return meaasgeID;
	}

	public void setMeaasgeID(String meaasgeID) {
		this.meaasgeID = meaasgeID;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
