package com.ghsh.code.bean;

import java.io.Serializable;

public class TRegion implements Serializable{
	private static final long serialVersionUID = 1L;

	private String regioID;//编号
	
	private String regionName;//地区名称
	
	private String parentID;//父编号
	
	public TRegion(String regioID){
		this.regioID=regioID;
	}
	
	public TRegion(){
		
	}
	
	public String getRegioID() {
		return regioID;
	}

	public void setRegioID(String regioID) {
		this.regioID = regioID;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String getParentID() {
		return parentID;
	}

	public void setParentID(String parentID) {
		this.parentID = parentID;
	}

	@Override
	public String toString() {
		return regionName;
	}
	
}
