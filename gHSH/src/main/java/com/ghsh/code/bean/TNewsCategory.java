package com.ghsh.code.bean;

import java.io.Serializable;


/**
 * 新闻分类
 * */
public class TNewsCategory implements Serializable{

	private static final long serialVersionUID = 1L;

	private String id;
	
	private String name;
	
	private String imageUrl;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	
}
