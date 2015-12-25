/**   
* @Title: ItemBean.java 
* @Package com.example.test 
* @Description: TODO(用一句话描述该文件做什么) 
* @date 2014-6-25 上午9:45:29    
*/
package com.example.test;

import java.util.List;

public class GroupItem {

	private String id;
	private String name;
	private List<ChildrenItem> childrenItems;
	
	
	public GroupItem() {
	}

	
	public GroupItem(String id,String name,List<ChildrenItem> childrenItems) {
		this.id = id;
		this.name = name;
		this.childrenItems = childrenItems;
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


	public List<ChildrenItem> getChildrenItems() {
		return childrenItems;
	}


	public void setChildrenItems(List<ChildrenItem> childrenItems) {
		this.childrenItems = childrenItems;
	}

	
	
	
}
