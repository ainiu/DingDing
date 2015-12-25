/**   
* @Title: ItemBean.java 
* @Package com.example.test 
* @Description: TODO(用一句话描述该文件做什么) 
* @date 2014-6-25 上午9:45:29    
*/
package com.example.test;

public class ChildrenItem {

	private String id;
	private String name;
	
	public ChildrenItem() {
	}

	
	public ChildrenItem(String id,String name) {
		this.id = id;
		this.name = name;
	}
	
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
	
	
	
}
