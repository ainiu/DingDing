package com.baidu.dingding.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class TUsers implements Serializable{

	private static final long serialVersionUID = 1L;

	private String userID;
	
	private String userName;
	
	private String mobile;
	
	private String email;
	
	private String nickName;

	private Integer sex;//性别 0保密，1为男，2为女
	
	private String birthDay;//生日
	
	private String portraitURL;//头像照片地址
	
	private String regTime;//注册时间
	
	private String lastTime;//最后登录时间
	
	private BigDecimal money;//余额
	
	private int point;//积分
	
	
	private String shareOpenID;
	
	private String shareType;
	
	private String shareNickName;
	
	private String sharePortrait;
	
	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(String birthDay) {
		this.birthDay = birthDay;
	}

	public String getPortraitURL() {
		return portraitURL;
	}

	public void setPortraitURL(String portraitURL) {
		this.portraitURL = portraitURL;
	}

	public String getRegTime() {
		return regTime;
	}

	public void setRegTime(String regTime) {
		this.regTime = regTime;
	}

	public String getLastTime() {
		return lastTime;
	}

	public void setLastTime(String lastTime) {
		this.lastTime = lastTime;
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

	public String getShareOpenID() {
		return shareOpenID;
	}

	public void setShareOpenID(String shareOpenID) {
		this.shareOpenID = shareOpenID;
	}

	public String getShareType() {
		return shareType;
	}

	public void setShareType(String shareType) {
		this.shareType = shareType;
	}

	public String getShareNickName() {
		return shareNickName;
	}

	public void setShareNickName(String shareNickName) {
		this.shareNickName = shareNickName;
	}

	public String getSharePortrait() {
		return sharePortrait;
	}

	public void setSharePortrait(String sharePortrait) {
		this.sharePortrait = sharePortrait;
	}
}
