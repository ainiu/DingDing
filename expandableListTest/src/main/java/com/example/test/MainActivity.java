package com.example.test;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ExpandableListView;

public class MainActivity extends Activity {

	private ExpandableListView expandableList;
	private Button showBtn;
	private ListViewAdapter adapter;
	private List<GroupItem> dataList = new ArrayList<GroupItem>();
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		expandableList = (ExpandableListView)findViewById(R.id.expandable_list);
		showBtn = (Button)findViewById(R.id.showBtn);
		
		showBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showCheckedItems();
			}
		});
		
		initData();
		
		
		adapter = new ListViewAdapter(this, dataList);
		expandableList.setAdapter(adapter);
		
	}

	private void showCheckedItems(){
		String checkedItems = "";
		List<String> checkedChildren = adapter.getCheckedChildren();
		if (checkedChildren!=null && !checkedChildren.isEmpty()) {
			for (String child : checkedChildren) {
				if (checkedItems.length()>0) {
					checkedItems += "\n";
				}
				
				checkedItems += child;
			}
		}
		
		final Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("已选中项(无排序)：");
		builder.setMessage(checkedItems);
		builder.setPositiveButton("关闭", null);
		builder.setCancelable(true);
		builder.create().show();
	}
	
	/**
	 * 初始化Adapter数据
	 * @Title: 
	 * @Description: 
	 * @return void    返回类型 
	 * @author zhangxiaolei 
	 * @throws
	 */
	private void initData(){
		List<ChildrenItem> list1 = new ArrayList<ChildrenItem>();
		list1.add(new ChildrenItem("childrenItem1","childrenItem1"));
		list1.add(new ChildrenItem("childrenItem2","childrenItem2"));
		list1.add(new ChildrenItem("childrenItem3","childrenItem3"));
		
		GroupItem groupItem1 = new GroupItem("groupItem1","groupItem1",list1);
		dataList.add(groupItem1);
		
		List<ChildrenItem> list2 = new ArrayList<ChildrenItem>();
		list2.add(new ChildrenItem("childrenItem4","childrenItem4"));
		list2.add(new ChildrenItem("childrenItem5","childrenItem5"));
		
		GroupItem groupItem2 = new GroupItem("groupItem2","groupItem2",list2);
		dataList.add(groupItem2);
		
		List<ChildrenItem> list3 = new ArrayList<ChildrenItem>();
		list3.add(new ChildrenItem("childrenItem6","childrenItem6"));
		list3.add(new ChildrenItem("childrenItem7","childrenItem7"));
		list3.add(new ChildrenItem("childrenItem8","childrenItem8"));
		
		GroupItem groupItem3 = new GroupItem("groupItem3","groupItem3",list3);
		dataList.add(groupItem3);
		
	}
	
}
