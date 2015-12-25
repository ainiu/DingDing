package com.ghsh.code.bean;

import java.io.Serializable;

public class TShare implements Serializable {

	private static final long serialVersionUID = 1L;
	private String id;
	private String text;
	private int imageResID;
	private ShareBean shareBean;
	
	public TShare(String id, String text, int imageResID, ShareBean shareBean) {
		super();
		this.id = id;
		this.text = text;
		this.imageResID = imageResID;
		this.shareBean = shareBean;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public int getImageResID() {
		return imageResID;
	}
	public void setImageResID(int imageResID) {
		this.imageResID = imageResID;
	}
	public ShareBean getShareBean() {
		return shareBean;
	}
	public void setShareBean(ShareBean shareBean) {
		this.shareBean = shareBean;
	}
	public static class ShareBean{
		public String shareId;//分享ID
		public String shareCode;
		public String shareName;
		public String shareDesc;
		public String sortOrder;
		public boolean isLogin;
		public ShareConfig shareConfig;//分享配置
	}
	public static class ShareConfig{
		public String APP_KEY;
		public String APP_ID;
		public String APP_Secret;
	}
}
