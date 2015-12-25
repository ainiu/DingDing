package com.ghsh.code.bean;

import java.io.Serializable;


/**
 * 红包
 * */
public class TMyBonus implements Serializable{
	private static final long serialVersionUID = 1L;

	private String bonusID;//优惠劵编号
	
	private String typeName;//名称
	
	private String typeMoney;//红包金额
	
	private String minGoodsAmount;//订单最小使用金额
	
	private String time;//红包使用时间
	
	private String status;//1:未使用 2:已使用 3:已过期
	
	private boolean selected;

	public String getBonusID() {
		return bonusID;
	}

	public void setBonusID(String bonusID) {
		this.bonusID = bonusID;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getTypeMoney() {
		return typeMoney;
	}

	public void setTypeMoney(String typeMoney) {
		this.typeMoney = typeMoney;
	}
	
	public String getMinGoodsAmount() {
		return minGoodsAmount;
	}

	public void setMinGoodsAmount(String minGoodsAmount) {
		this.minGoodsAmount = minGoodsAmount;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
}
