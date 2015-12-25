package com.baidu.dingding.entity;

public class SouViewPager {
	private String picLogPath;
	private String linked;

	public SouViewPager(String picLogPath, String linked) {
		super();
		this.picLogPath = picLogPath;
		this.linked = linked;
	}
	public String getPicLogPath() {
		return picLogPath;
	}
	public void setPicLogPath(String picLogPath) {
		this.picLogPath = picLogPath;
	}
	public String getLinked() {
		return linked;
	}
	public void setLinked(String linked) {
		this.linked = linked;
	}
	@Override
	public String toString() {
		return "SouViewPager [picLogPath=" + picLogPath + ", linked=" + linked
				+ "]";
	}

}
