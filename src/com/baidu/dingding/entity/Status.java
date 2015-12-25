package com.baidu.dingding.entity;

import java.util.List;

/*
 * ×¢²ábean
 * **/
//{"message":"ÎÞ","content":{"sessionId":"","logPath":"http://183.234.117.210:9090/AppServer/security/createVerifyCodeImage","code":"HJuT"},"result":"0"}
public class Status {
	
	private String message;
	private Content content;
	private String result;
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Content getContent() {
		return content;
	}
	public void setContent(Content content) {
		this.content = content;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	
	@Override
	public String toString() {
		return "Status [message=" + message + ", content=" + content
				+ ", result=" + result + "]";
	}
	
}

