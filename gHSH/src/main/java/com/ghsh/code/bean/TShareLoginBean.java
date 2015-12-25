package com.ghsh.code.bean;

import java.io.Serializable;
/**第三方登录bean*/
public class TShareLoginBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private String openID;
	private String type;
	private String userID;
	private String nickName;//昵称
	private String userPortrait;//头像路径
	
	public TShareLoginBean() {
		super();
	}
	public TShareLoginBean(String openID, String type) {
		super();
		this.openID = openID;
		this.type = type;
	}
	public String getOpenID() {
		return openID;
	}
	public void setOpenID(String openID) {
		this.openID = openID;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getUserPortrait() {
		return userPortrait;
	}
	public void setUserPortrait(String userPortrait) {
		this.userPortrait = userPortrait;
	}
}
