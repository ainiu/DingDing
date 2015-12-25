package com.ghsh.code.bean;

import java.io.Serializable;

/***分佣首页列表**/
public class TCommissionUserInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	private String userID;
	private String userImg_URL;
	private String userName;//用户名
	private String userMoney;//余额
	private String userCount;//积分
	
	private String addpeople_day;//今日新增
	private String addpeople_yesterday;//昨日新增
	
	private String allcount;//全部客户
	private String onecount;//一级客户
	private String twocount;//二级客户
	private String threecount;//三级客户
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getUserImg_URL() {
		return userImg_URL;
	}
	public void setUserImg_URL(String userImg_URL) {
		this.userImg_URL = userImg_URL;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserMoney() {
		return userMoney;
	}
	public void setUserMoney(String userMoney) {
		this.userMoney = userMoney;
	}
	public String getUserCount() {
		return userCount;
	}
	public void setUserCount(String userCount) {
		this.userCount = userCount;
	}
	public String getAddpeople_day() {
		return addpeople_day;
	}
	public void setAddpeople_day(String addpeople_day) {
		this.addpeople_day = addpeople_day;
	}
	public String getAddpeople_yesterday() {
		return addpeople_yesterday;
	}
	public void setAddpeople_yesterday(String addpeople_yesterday) {
		this.addpeople_yesterday = addpeople_yesterday;
	}
	public String getAllcount() {
		return allcount;
	}
	public void setAllcount(String allcount) {
		this.allcount = allcount;
	}
	public String getOnecount() {
		return onecount;
	}
	public void setOnecount(String onecount) {
		this.onecount = onecount;
	}
	public String getTwocount() {
		return twocount;
	}
	public void setTwocount(String twocount) {
		this.twocount = twocount;
	}
	public String getThreecount() {
		return threecount;
	}
	public void setThreecount(String threecount) {
		this.threecount = threecount;
	}
	public TCommissionUserInfo() {
		super();
	}
	public TCommissionUserInfo(String userName2, String userMoney2,
			String userCount2, String addpeople_day2,
			String addpeople_yesterday2, String allcount2, String onecount2,
			String twocount2, String threecount2) {
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "TCommissionUserInfo [userImg_URL="
				+ userImg_URL + ", userName=" + userName + ", userMoney="
				+ userMoney + ", userCount=" + userCount + ", addpeople_day="
				+ addpeople_day + ", addpeople_yesterday="
				+ addpeople_yesterday + ", allcount=" + allcount
				+ ", onecount=" + onecount + ", twocount=" + twocount
				+ ", threecount=" + threecount + "]";
	}
	
	
}
