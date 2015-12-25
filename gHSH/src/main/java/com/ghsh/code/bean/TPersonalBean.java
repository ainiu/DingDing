package com.ghsh.code.bean;

import java.math.BigDecimal;

/**
 * 个人中心
 * */
public class TPersonalBean {

	private String numberWaitPay;//待付款
	private String numberDeliveryPay;//待发货
	private String numberReceiptPay;//待收货
	private String numberCommentPay;//待评论
	
	private String portraitURL;//头像照片地址
	private String levleName;//会员级别名称
	private int growthPoint;//会员成长值
	
	private BigDecimal money;//余额
	
	private int point;//积分
	
	public String getNumberWaitPay() {
		return numberWaitPay;
	}
	public void setNumberWaitPay(String numberWaitPay) {
		this.numberWaitPay = numberWaitPay;
	}
	public String getNumberDeliveryPay() {
		return numberDeliveryPay;
	}
	public void setNumberDeliveryPay(String numberDeliveryPay) {
		this.numberDeliveryPay = numberDeliveryPay;
	}
	public String getNumberReceiptPay() {
		return numberReceiptPay;
	}
	public void setNumberReceiptPay(String numberReceiptPay) {
		this.numberReceiptPay = numberReceiptPay;
	}
	public String getNumberCommentPay() {
		return numberCommentPay;
	}
	public void setNumberCommentPay(String numberCommentPay) {
		this.numberCommentPay = numberCommentPay;
	}
	public String getPortraitURL() {
		return portraitURL;
	}
	public void setPortraitURL(String portraitURL) {
		this.portraitURL = portraitURL;
	}
	public String getLevleName() {
		return levleName;
	}
	public void setLevleName(String levleName) {
		this.levleName = levleName;
	}
	public int getGrowthPoint() {
		return growthPoint;
	}
	public void setGrowthPoint(int growthPoint) {
		this.growthPoint = growthPoint;
	}
	public BigDecimal getMoney() {
		return money;
	}
	public void setMoney(BigDecimal money) {
		this.money = money;
	}
	public int getPoint() {
		return point;
	}
	public void setPoint(int point) {
		this.point = point;
	}
}
