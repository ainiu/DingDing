package com.ghsh.code.bean;

import java.io.Serializable;

/**
 * 商品咨询表
 * */
public class TConsult implements Serializable{
	private static final long serialVersionUID = 1L;

	private String consultID;//

	private String userName;// 用户名
	
	private String questionContent;// 咨询内容

	private String addTime;// 咨询时间

	private String replyTime;// 回复时间
	
	private String replyContent;// 回复内容
	
	private int satisfied;//满意数量
	
	private int satisfiedNot;//不满意数量
	
	private boolean anonymous;//匿名

	public String getConsultID() {
		return consultID;
	}

	public void setConsultID(String consultID) {
		this.consultID = consultID;
	}

	public String getQuestionContent() {
		return questionContent;
	}

	public void setQuestionContent(String questionContent) {
		this.questionContent = questionContent;
	}

	public String getAddTime() {
		return addTime;
	}

	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}

	public String getReplyTime() {
		return replyTime;
	}

	public void setReplyTime(String replyTime) {
		this.replyTime = replyTime;
	}

	public String getReplyContent() {
		return replyContent;
	}

	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getSatisfied() {
		return satisfied;
	}

	public void setSatisfied(int satisfied) {
		this.satisfied = satisfied;
	}

	public int getSatisfiedNot() {
		return satisfiedNot;
	}

	public void setSatisfiedNot(int satisfiedNot) {
		this.satisfiedNot = satisfiedNot;
	}

	public boolean isAnonymous() {
		return anonymous;
	}

	public void setAnonymous(boolean anonymous) {
		this.anonymous = anonymous;
	}
}
