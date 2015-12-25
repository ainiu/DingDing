package com.ghsh.code.bean;

public class TCommissionNumberInfo {
	private String paying;//待付款
	private String hasPay;//已付款
	private String end;//已结束
	public String getPaying() {
		return paying;
	}
	public void setPaying(String paying) {
		this.paying = paying;
	}
	public String getHasPay() {
		return hasPay;
	}
	public void setHasPay(String hasPay) {
		this.hasPay = hasPay;
	}
	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
	}
	public TCommissionNumberInfo(String paying, String hasPay, String end) {
		super();
		this.paying = paying;
		this.hasPay = hasPay;
		this.end = end;
	}
	@Override
	public String toString() {
		return "commissionNumberInfo [paying=" + paying + ", hasPay=" + hasPay
				+ ", end=" + end + "]";
	}
	
}
