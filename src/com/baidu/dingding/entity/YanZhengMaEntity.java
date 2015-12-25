package com.baidu.dingding.entity;

public class YanZhengMaEntity {
	private String sessionId;
	private String logPath;
	private String code;

	public YanZhengMaEntity() {
		super();
	}

	public YanZhengMaEntity(String sessionId, String logPath, String code) {
		super();
		this.sessionId = sessionId;
		this.logPath = logPath;
		this.code = code;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getLogPath() {
		return logPath;
	}

	public void setLogPath(String logPath) {
		this.logPath = logPath;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return "YanZhengMaEntity [sessionId=" + sessionId + ", logPath="
				+ logPath + ", code=" + code + "]";
	}
}
