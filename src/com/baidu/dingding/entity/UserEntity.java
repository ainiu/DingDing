package com.baidu.dingding.entity;

public class UserEntity {
	private String userNumber;
	private String password;
	private String Image;
	private String iconUrl;
	
	@Override
	public String toString() {
		return "UserEntity [userNumber=" + userNumber + ", password="
				+ password + ", Image=" + Image + ", iconUrl=" + iconUrl + "]";
	}
	public UserEntity() {
		super();
	}
	public UserEntity(String userNumber, String password, String image,
			String iconUrl) {
		super();
		this.userNumber = userNumber;
		this.password = password;
		Image = image;
		this.iconUrl = iconUrl;
	}
	public String getUserNumber() {
		return userNumber;
	}
	public void setUserNumber(String userNumber) {
		this.userNumber = userNumber;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getImage() {
		return Image;
	}
	public void setImage(String image) {
		Image = image;
	}
	public String getIconUrl() {
		return iconUrl;
	}
	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}
	
}
