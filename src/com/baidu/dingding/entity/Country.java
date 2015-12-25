package com.baidu.dingding.entity;

public class Country {

	private String name;
	private String id;
	public Country(String name, String id) {
		super();
		this.name = name;
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "Country [name=" + name + ", id=" + id + "]";
	}


}
