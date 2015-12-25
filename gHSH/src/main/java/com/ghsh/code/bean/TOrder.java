package com.ghsh.code.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 订单
 * */
public class TOrder implements Serializable{
	private static final long serialVersionUID = 1L;

	private String orderID;//订单编号
	
	private String orderSN;//订单流水号
	
	private String goodsAmount;//合计总金额
	
	private String goodsNumber;//合计商品数量
	
	private String shippingFee;//运费
	
	private String addTime;//下单时间
	
	private String payTime;//支付时间
	
	private String shipTime;//发货时间
	
	private String confirmTime;//确认时间
	
	private TShipping shipping;//配送方式
	
	private TPayment payment;//支付方式编号
	
	private TCoupon coupon;//优惠劵
	
	private TMyBonus myBonus;//红包
	
	private boolean evaluationStatus;//评论状态
	
	private String evaluationTime;//评论时间
	
	private String orderAmount;//订单金额
	
	private String discountAmount;//折扣金额
	
	private TSendInfo sendInfo;//地址
	
	private String cmment;//下单留言
	
	private String userID;//用户ID
	
	private String orderStatus;//订单状态
	
	private String shippingStatus;//配送状态
	
	private String payStatus;//支付状态
	
	private String orderType;//订单类型
	
	private String invType;//发票类型
	
	private String invPayee;//发票抬头
	
	private String invContent;//发票内容
	 
	private List<TOrderItem> orderItemList=new ArrayList<TOrderItem>();
	
	private String shopName;
	
	private String shopPhone;

	public String getOrderID() {
		return orderID;
	}

	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}

	public String getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(String orderAmount) {
		this.orderAmount = orderAmount;
	}

	public String getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(String discountAmount) {
		this.discountAmount = discountAmount;
	}

	public TSendInfo getSendInfo() {
		return sendInfo;
	}

	public void setSendInfo(TSendInfo sendInfo) {
		this.sendInfo = sendInfo;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public List<TOrderItem> getOrderItemList() {
		return orderItemList;
	}

	public void setOrderItemList(List<TOrderItem> orderItemList) {
		this.orderItemList = orderItemList;
	}

	public String getOrderSN() {
		return orderSN;
	}

	public void setOrderSN(String orderSN) {
		this.orderSN = orderSN;
	}

	public String getAddTime() {
		return addTime;
	}

	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}

	public String getGoodsAmount() {
		return goodsAmount;
	}

	public void setGoodsAmount(String goodsAmount) {
		this.goodsAmount = goodsAmount;
	}

	
	public String getPayTime() {
		return payTime;
	}

	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}

	public String getShipTime() {
		return shipTime;
	}

	public void setShipTime(String shipTime) {
		this.shipTime = shipTime;
	}

	public String getConfirmTime() {
		return confirmTime;
	}

	public void setConfirmTime(String confirmTime) {
		this.confirmTime = confirmTime;
	}

	public TShipping getShipping() {
		return shipping;
	}

	public void setShipping(TShipping shipping) {
		this.shipping = shipping;
	}

	public TPayment getPayment() {
		return payment;
	}

	public void setPayment(TPayment payment) {
		this.payment = payment;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getShippingStatus() {
		return shippingStatus;
	}

	public void setShippingStatus(String shippingStatus) {
		this.shippingStatus = shippingStatus;
	}

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public boolean isEvaluationStatus() {
		return evaluationStatus;
	}

	public void setEvaluationStatus(boolean evaluationStatus) {
		this.evaluationStatus = evaluationStatus;
	}

	public String getEvaluationTime() {
		return evaluationTime;
	}

	public void setEvaluationTime(String evaluationTime) {
		this.evaluationTime = evaluationTime;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getShopPhone() {
		return shopPhone;
	}

	public void setShopPhone(String shopPhone) {
		this.shopPhone = shopPhone;
	}

	public TCoupon getCoupon() {
		return coupon;
	}

	public void setCoupon(TCoupon coupon) {
		this.coupon = coupon;
	}

	public String getCmment() {
		return cmment;
	}

	public void setCmment(String cmment) {
		this.cmment = cmment;
	}

	public String getGoodsNumber() {
		return goodsNumber;
	}

	public void setGoodsNumber(String goodsNumber) {
		this.goodsNumber = goodsNumber;
	}

	public String getShippingFee() {
		return shippingFee;
	}

	public void setShippingFee(String shippingFee) {
		this.shippingFee = shippingFee;
	}

	public TMyBonus getMyBonus() {
		return myBonus;
	}

	public void setMyBonus(TMyBonus myBonus) {
		this.myBonus = myBonus;
	}

	public String getInvType() {
		return invType;
	}

	public void setInvType(String invType) {
		this.invType = invType;
	}

	public String getInvPayee() {
		return invPayee;
	}

	public void setInvPayee(String invPayee) {
		this.invPayee = invPayee;
	}

	public String getInvContent() {
		return invContent;
	}

	public void setInvContent(String invContent) {
		this.invContent = invContent;
	}
}
