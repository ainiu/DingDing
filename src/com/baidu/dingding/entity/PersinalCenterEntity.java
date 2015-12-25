package com.baidu.dingding.entity;

public class PersinalCenterEntity {
	//"nonPaymentcount ":"6",
	//"paidCount ":"4",
	//"deliveredCount ":"3",
	//"finishedCount ":"5",
	//" returnedCount ":"7"
	private String nonPaymentcount;
	private String paidCount;
	private String deliveredCount;	
	private String finishedCount;
	private String returnedCount;

	public PersinalCenterEntity() {
		super();
	}
	public PersinalCenterEntity(String nonPaymentcount, String paidCount,
			String deliveredCount, String finishedCount, String returnedCount) {
		super();
		this.nonPaymentcount = nonPaymentcount;
		this.paidCount = paidCount;
		this.deliveredCount = deliveredCount;
		this.finishedCount = finishedCount;
		this.returnedCount = returnedCount;
	}
	public String getNonPaymentcount() {
		return nonPaymentcount;
	}
	public void setNonPaymentcount(String nonPaymentcount) {
		this.nonPaymentcount = nonPaymentcount;
	}
	public String getPaidCount() {
		return paidCount;
	}
	public void setPaidCount(String paidCount) {
		this.paidCount = paidCount;
	}
	public String getDeliveredCount() {
		return deliveredCount;
	}
	public void setDeliveredCount(String deliveredCount) {
		this.deliveredCount = deliveredCount;
	}
	public String getFinishedCount() {
		return finishedCount;
	}
	public void setFinishedCount(String finishedCount) {
		this.finishedCount = finishedCount;
	}
	public String getReturnedCount() {
		return returnedCount;
	}
	public void setReturnedCount(String returnedCount) {
		this.returnedCount = returnedCount;
	}
	@Override
	public String toString() {
		return "PersinalCenterEntity [nonPaymentcount=" + nonPaymentcount
				+ ", paidCount=" + paidCount + ", deliveredCount="
				+ deliveredCount + ", finishedCount=" + finishedCount
				+ ", returnedCount=" + returnedCount + "]";
	}
}
