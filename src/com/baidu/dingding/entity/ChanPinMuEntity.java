package com.baidu.dingding.entity;

//产品目录的实体类
public class ChanPinMuEntity {
	private String logPath;
	private String textName;
	private String textMiao;
	private String pice;
	private String logImage;

	public ChanPinMuEntity() {
		super();
	}

	public ChanPinMuEntity(String logPath, String textName, String textMiao,
			String pice, String logImage) {
		super();
		this.logPath = logPath;
		this.textName = textName;
		this.textMiao = textMiao;
		this.pice = pice;
		this.logImage = logImage;
	}

	public String getLogPath() {
		return logPath;
	}

	public void setLogPath(String logPath) {
		this.logPath = logPath;
	}

	public String getTextName() {
		return textName;
	}

	public void setTextName(String textName) {
		this.textName = textName;
	}

	public String getTextMiao() {
		return textMiao;
	}

	public void setTextMiao(String textMiao) {
		this.textMiao = textMiao;
	}

	public String getPice() {
		return pice;
	}

	public void setPice(String pice) {
		this.pice = pice;
	}

	public String getLogImage() {
		return logImage;
	}

	public void setLogImage(String logImage) {
		this.logImage = logImage;
	}

	@Override
	public String toString() {
		return "ChanPinMuEntity [logPath=" + logPath + ", textName=" + textName
				+ ", textMiao=" + textMiao + ", pice=" + pice + ", logImage="
				+ logImage + "]";
	}

}
