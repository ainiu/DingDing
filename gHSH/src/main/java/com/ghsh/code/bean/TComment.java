package com.ghsh.code.bean;

import java.io.Serializable;

/**
 * 评论
 * */
public class TComment implements Serializable {

	private static final long serialVersionUID = 1L;

	private String commentID;// id

	private String comment;// 评论内容
	
	private String addTime;// 评论时间

	private String repleContent;// 回复内容
	
	private String repleTime;// 回复时间

	private String userName;// 评论人

	private int evaluation;// 服务分数

	public String getCommentID() {
		return commentID;
	}

	public void setCommentID(String commentID) {
		this.commentID = commentID;
	}

	public String getAddTime() {
		return addTime;
	}

	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}


	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getEvaluation() {
		return evaluation;
	}

	public void setEvaluation(int evaluation) {
		this.evaluation = evaluation;
	}

	public String getRepleContent() {
		return repleContent;
	}

	public void setRepleContent(String repleContent) {
		this.repleContent = repleContent;
	}

	public String getRepleTime() {
		return repleTime;
	}

	public void setRepleTime(String repleTime) {
		this.repleTime = repleTime;
	}
}
