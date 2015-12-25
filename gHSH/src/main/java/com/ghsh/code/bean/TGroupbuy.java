package com.ghsh.code.bean;

import java.io.Serializable;

/**
 * 团购
 * */
public class TGroupbuy implements Serializable {

	private static final long serialVersionUID = 1L;

	private String groupID;// 团购编号

	private String groupName;// 团购名称

	private String groupImage;//团购图片

	private String groupDesc;// 团购描述 
	
	private String goupPrice;//价格
	
	private String sourcePrice;//原价
	
	private String startTime;// 开始时间

	private String endTime;// 结束时间

	private String goodsID;// 商品编号

	private String minQuantity;// 参与团购总数量

	private String maxPerUser;// 每人限制购买数量

	private String state;// 团购状态 0:未开始  1:进行中  2:已结束  3:已完成  4:已取消

	private boolean isRecommended;// 是否推荐

	private String views;// 当前参与数量
	
	private int saleNumber;//销售数量
	public String getGoupPrice() {
		return goupPrice;
	}

	public void setGoupPrice(String goupPrice) {
		this.goupPrice = goupPrice;
	}

	public String getSourcePrice() {
		return sourcePrice;
	}

	public void setSourcePrice(String sourcePrice) {
		this.sourcePrice = sourcePrice;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupImage() {
		return groupImage;
	}

	public void setGroupImage(String groupImage) {
		this.groupImage = groupImage;
	}

	public String getGroupDesc() {
		return groupDesc;
	}

	public void setGroupDesc(String groupDesc) {
		this.groupDesc = groupDesc;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getGoodsID() {
		return goodsID;
	}

	public void setGoodsID(String goodsID) {
		this.goodsID = goodsID;
	}

	public String getMinQuantity() {
		return minQuantity;
	}

	public void setMinQuantity(String minQuantity) {
		this.minQuantity = minQuantity;
	}

	public String getMaxPerUser() {
		return maxPerUser;
	}

	public void setMaxPerUser(String maxPerUser) {
		this.maxPerUser = maxPerUser;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}


	public String getGroupID() {
		return groupID;
	}

	public void setGroupID(String groupID) {
		this.groupID = groupID;
	}

	public boolean isRecommended() {
		return isRecommended;
	}

	public void setRecommended(boolean isRecommended) {
		this.isRecommended = isRecommended;
	}
	
	public String getViews() {
		return views;
	}

	public void setViews(String views) {
		this.views = views;
	}

	public int getSaleNumber() {
		return saleNumber;
	}

	public void setSaleNumber(int saleNumber) {
		this.saleNumber = saleNumber;
	}
}
