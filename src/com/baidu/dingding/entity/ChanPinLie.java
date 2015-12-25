package com.baidu.dingding.entity;

//产品列的实体类
public class ChanPinLie {
	private String laogPath;
	private String textMiao;
	private String pice;
	private int image;
	private String imagePath;
	private String xiaoImage;
	private String contury;

	public ChanPinLie() {
		super();
	}

	public ChanPinLie(String laogPath, String textMiao, String pice, int image,
			String imagePath, String xiaoImage, String contury) {
		super();
		this.laogPath = laogPath;
		this.textMiao = textMiao;
		this.pice = pice;
		this.image = image;
		this.imagePath = imagePath;
		this.xiaoImage = xiaoImage;
		this.contury = contury;
	}

	public String getLaogPath() {
		return laogPath;
	}

	public void setLaogPath(String laogPath) {
		this.laogPath = laogPath;
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

	public int getImage() {
		return image;
	}

	public void setImage(int image) {
		this.image = image;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getXiaoImage() {
		return xiaoImage;
	}

	public void setXiaoImage(String xiaoImage) {
		this.xiaoImage = xiaoImage;
	}

	public String getContury() {
		return contury;
	}

	public void setContury(String contury) {
		this.contury = contury;
	}

	@Override
	public String toString() {
		return "ChanPinLie [laogPath=" + laogPath + ", textMiao=" + textMiao
				+ ", pice=" + pice + ", image=" + image + ", imagePath="
				+ imagePath + ", xiaoImage=" + xiaoImage + ", contury="
				+ contury + "]";
	}

}
