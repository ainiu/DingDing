package com.baidu.dingding.view.Btn;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.dingding.R;
import com.baidu.dingding.until.ExceptionUtil;
import com.baidu.dingding.until.LogUtil;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * 商品属性自定义控件
 * ① 可以根据项目个数动态显示
 * ② 实现按钮单选
 * ③ 根据最大宽度值设定自动换行
 * 
 * 
 */
public class AttributeMyWidget extends LinearLayout {
	private Context context;
	 LinearLayout containerLayout;

	/**
	 * 
	 * 以键值对方式存放属性值， 一对多 eg:  颜色 -- 红色，绿色，蓝色，橙色  
	 */
	Map<String, Object> attriDetailMap;

	public AttributeMyWidget(Context context) {
		super(context);
	}

	public AttributeMyWidget(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		LayoutInflater.from(context).inflate(
				R.layout.attribute_mywidget_layout, this, true);
		containerLayout = (LinearLayout) this
				.findViewById(R.id.attri_radiogrouplayout);
		attriDetailMap = new HashMap<String, Object>();
		try {
			this.loadAttriData(attriDetailMap);
		}catch (Exception e){
			LogUtil.i("AttributeMyWidget出错了","");
			ExceptionUtil.handleException(e);
		}


	}


	public void loadAttriData(Map<String, Object> Map) {
		if(Map.isEmpty())return;
		this.attriDetailMap=Map;
		Iterator it = attriDetailMap.entrySet().iterator();
		while (it.hasNext()){
			Map.Entry entry = (Map.Entry) it.next();
			String key = entry.getKey().toString();
			initView(key);
		}
	}


	/**
	 * @param art_id
	 *            商品id 根据id查询属性
	 */
	public void loadAttriData(String art_id) {

		attriDetailMap.put("颜色", Arrays.asList("红色", "深绿色", "蓝绿色", "橙色",
				"四五个字", "五六七个字", "嘿", "很好看的颜色啊", "大大大大灰狼"));
		attriDetailMap.put("尺码", Arrays.asList("40", "50", "41", "42", "43"));
		initView("颜色");
//		initView("尺码");
	}

	/**
	 * 初始化控件布局，以及数据显示
	 */

	private List<LinearLayout> radioGroupList;

	/**
	 * 存放所有new出来的radioButton
	 */
	private List<JWRadioBtn> radioBtnList;

	/**
	 * 初始化界面 radioButton
	 *
	 * @param s
	 */
	public void initView(String s) {

		radioBtnList = new ArrayList<JWRadioBtn>();
		/**
		 * 存放randiobtn LinearLayout
		 */
		LinearLayout radioLayout = null;
		/**
		 * radioLayout 添加layout到radioGroup的索引号
		 */
		int index = 0;
		/**
		 * 界面上最大限定宽度 如何动态获取？？
		 */
		int layoutWidth = 350;
		/**
		 * layout剩余宽度
		 */
		int leftWidth = 0;
		/**
		 * radionButton的宽度
		 */
		int raBtnWidth = 0;

		int viewNum;                                                                        // 一个layout里面添加了几个radiobutton
		int extraWidth;                                                                     // 额外消耗的宽度值，layoutMargin
		radioGroupList = new ArrayList<LinearLayout>();                                      //获取父控件

		JSONArray attriDetailList = (JSONArray) attriDetailMap.get(s);// 所有子属性
		int detailNum = attriDetailList.length();// 子属性个数

		JWRadioBtn radioBtn;
		String name = "";
		LayoutParams params = new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

		LayoutParams radiobtn_params = new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

		for (int i = 0; i < detailNum; i++) {

			radioBtn = new JWRadioBtn(context);
			name = attriDetailList.opt(i).toString();
			radioBtn.setText(name);
			raBtnWidth = radioBtn.getRealWidth();                   //获取控件宽度

			radioBtnList.add(radioBtn) ;//将radioBtn添加进list便于处理点击事件等
			radioBtn.setCallback(new JWRadioBtn.ChangedCheckCallBack() {

				@Override
				public void ChangedCheck() {//处理按下选中操作时，只能多选一

					int size = radioBtnList.size();

					for (int i = 0; i < size; i++) {
						((JWRadioBtn) radioBtnList.get(i)).setChecked(false);
					}
				}
			});


			if (i == 0) {
				radioLayout = new LinearLayout(context);
				radioLayout.setLayoutParams(params);
				radioLayout.setOrientation(LinearLayout.HORIZONTAL);
				radioBtn.setChecked(true);
				// 第一个radiobutton不要左边距
				radiobtn_params.setMargins(0, 5, 5, 5);
				TextView attriName=new TextView(context);
				attriName.setText(s);
				attriName.setLayoutParams(radiobtn_params);
				radioLayout.addView(attriName);
				radioBtn.setLayoutParams(radiobtn_params);
				radioLayout.addView(radioBtn);
				containerLayout.addView(radioLayout, index);
				radioGroupList.add(radioLayout);// 存储radiogoup
				leftWidth = layoutWidth - raBtnWidth;
				index++;
			} else {

				radiobtn_params.setMargins(5, 5, 5, 5);
				radioBtn.setLayoutParams(radiobtn_params);

				if (raBtnWidth < leftWidth) {

					radioLayout.addView(radioBtn);
					viewNum = radioLayout.getChildCount();
					extraWidth = (viewNum - 1) * 10;// 额外距离消耗值

					leftWidth -= (raBtnWidth + extraWidth);
				} else {

					radioLayout = new LinearLayout(context);
					radioLayout.setLayoutParams(params);
					radioLayout.setOrientation(LinearLayout.HORIZONTAL);

					radiobtn_params.setMargins(0, 5, 5, 5);
					radioBtn.setLayoutParams(radiobtn_params); // 新起一行的时候第一个radiobtn不设左边距

					radioLayout.addView(radioBtn);//
					leftWidth = layoutWidth - raBtnWidth;
					radioGroupList.add(radioLayout);// 存储radiogoup
					containerLayout.addView(radioLayout, index);
					index++;
				}

			}

		}

	}
}
