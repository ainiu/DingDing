package com.baidu.dingding.entity;

public class Content {
	private String sessionId;
	private String code;
	private String logPath;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getLogPath() {
		return logPath;
	}

	public void setLogPath(String logPath) {
		this.logPath = logPath;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	@Override
	public String toString() {
		return "Content [sessionId=" + sessionId + ", code=" + code
				+ ", logPath=" + logPath + "]";
	}

	

}
