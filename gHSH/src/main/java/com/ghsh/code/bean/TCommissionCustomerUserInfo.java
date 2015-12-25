package com.ghsh.code.bean;
/**分佣客户**/
public class TCommissionCustomerUserInfo {
	private String UserID;
	private String  imgURL;
	private String userName;
	private String time;
	private String order;
	private String money;
	private String mob_number;
	
	public String getMob_number() {
		return mob_number;
	}
	public void setMob_number(String mob_number) {
		this.mob_number = mob_number;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public String getUserID() {
		return UserID;
	}
	public void setUserID(String userID) {
		UserID = userID;
	}
	public String getImgURL() {
		return imgURL;
	}
	public void setImgURL(String imgURL) {
		this.imgURL = imgURL;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public TCommissionCustomerUserInfo(String userName, String time,
			String order, String money, String mob_number) {
		super();
		this.userName = userName;
		this.time = time;
		this.order = order;
		this.money = money;
		this.mob_number = mob_number;
	}
	public TCommissionCustomerUserInfo() {
		super();
	}
	@Override
	public String toString() {
		return "TCommissionCustomerUserInfo [UserID=" + UserID + ", imgURL="
				+ imgURL + ", userName=" + userName + ", time=" + time
				+ ", order=" + order + ", money=" + money + ", mob_number="
				+ mob_number + "]";
	}
}
