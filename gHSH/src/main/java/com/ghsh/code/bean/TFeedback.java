package com.ghsh.code.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 留言
 * */
public class TFeedback {

	private String msgID;
	
	private String userName;
	
	private String userEmail;
	
	private String msgTitle;//标题
	
	private String msgContent;//内容
	
	private String msgTime;//添加时间
	
	private String msgType;//类型
	
	private List<TFeedback> feedbackList=new ArrayList<TFeedback>();

	public String getMsgID() {
		return msgID;
	}

	public void setMsgID(String msgID) {
		this.msgID = msgID;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getMsgTitle() {
		return msgTitle;
	}

	public void setMsgTitle(String msgTitle) {
		this.msgTitle = msgTitle;
	}

	public String getMsgContent() {
		return msgContent;
	}

	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}

	public String getMsgTime() {
		return msgTime;
	}

	public void setMsgTime(String msgTime) {
		this.msgTime = msgTime;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public List<TFeedback> getFeedbackList() {
		return feedbackList;
	}

	public void setFeedbackList(List<TFeedback> feedbackList) {
		this.feedbackList = feedbackList;
	}
}
