package com.ghsh.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.ghsh.R;
import com.ghsh.activity.base.BaseActivity;
import com.ghsh.code.bean.TRegion;
import com.ghsh.code.volley.DResponseListener;
import com.ghsh.code.volley.DVolleyConstans;
import com.ghsh.code.volley.bean.ReturnBean;
import com.ghsh.code.volley.model.RegionModel;
import com.ghsh.dialog.DProgressDialog;
/**
 * 城市 区域 选择
 * */
public class RegionActivity extends BaseActivity implements OnClickListener ,DResponseListener{
	
	private Spinner provinceView,cityView,areaView;
	private ArrayAdapter<TRegion> provinceAdapter,cityAdapter,areaAdapter;
	private Button confirmButton,cancelButton;
	private RegionModel regionModel;
	private DProgressDialog progressDialog;
	private int cityType=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_region);
		
		WindowManager m = getWindowManager();
		Display d = m.getDefaultDisplay(); // 为获取屏幕宽、高
		LayoutParams p = getWindow().getAttributes(); // 获取对话框当前的参数值
		p.width = d.getWidth(); // 宽度设置为屏幕
		getWindow().setAttributes(p); // 设置生效
		getWindow().setGravity(Gravity.CENTER); // 设置靠右对齐
		
		this.initView();
		this.initListener();
		regionModel=new RegionModel(this);
		regionModel.addResponseListener(this);
		
		progressDialog.show();
		regionModel.getRegionListByID(null);
	}
	
	private void initView() {
		progressDialog=new DProgressDialog(this);
		provinceView=(Spinner)this.findViewById(R.id.region_province);
		provinceAdapter =new ArrayAdapter<TRegion>(this,R.layout.spinner_simple_item,new ArrayList<TRegion>());
		provinceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        provinceView.setAdapter(provinceAdapter);
        
        cityView=(Spinner)this.findViewById(R.id.region_city);
        cityAdapter =new ArrayAdapter<TRegion>(this,R.layout.spinner_simple_item,new ArrayList<TRegion>());
        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cityView.setAdapter(cityAdapter);
        
        areaView=(Spinner)this.findViewById(R.id.region_area);
        areaAdapter =new ArrayAdapter<TRegion>(this,R.layout.spinner_simple_item,new ArrayList<TRegion>());
        areaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        areaView.setAdapter(areaAdapter);
        
        confirmButton=(Button)this.findViewById(R.id.region_confirm);
        cancelButton=(Button)this.findViewById(R.id.region_cancel);
       
       
	}
	
	private void initListener() {
		confirmButton.setOnClickListener(this);
		cancelButton.setOnClickListener(this);
		provinceView.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,int position, long id) {
				cityAdapter.clear();
				areaAdapter.clear();
				cityType=1;
				progressDialog.show();
				regionModel.getRegionListByID(provinceAdapter.getItem(position).getRegioID());
			}
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});
		cityView.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,int position, long id) {
				areaAdapter.clear();
				cityType=2;
				progressDialog.show();
				regionModel.getRegionListByID(cityAdapter.getItem(position).getRegioID());
			}
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});
	}

	@Override
	public void onClick(View v) {
		if(v==confirmButton){
			TRegion province=provinceAdapter.getItem(provinceView.getSelectedItemPosition());
			TRegion city=cityAdapter.getItem(cityView.getSelectedItemPosition());
			TRegion area=areaAdapter.getItem(areaView.getSelectedItemPosition());
			String regionText=province.getRegionName()+" "+city.getRegionName()+" "+area.getRegionName();
			Intent intent = new Intent();
			intent.putExtra("regionText", regionText);
			intent.putExtra("province", province);
			intent.putExtra("city", city);
			intent.putExtra("area", area);
			setResult(Activity.RESULT_OK, intent);
	        finish();
		}else if(v==cancelButton){
			this.finish();
		}
	}

	
	@Override
	public void onMessageResponse(ReturnBean bean,int result,String message,Throwable error) {
		progressDialog.dismiss();
		if(error!=null){
			this.showShortToast(error.getMessage());
			return;
		}
		if(result==DVolleyConstans.RESULT_OK){
			if(bean.getType()==DVolleyConstans.METHOD_QUERYALL){
				List<TRegion> list=(List<TRegion>)bean.getObject();
				if(list!=null&&list.size()!=0){
					if(cityType==0){
						//省
						for(TRegion region:list){
							provinceAdapter.add(region);
						}
						provinceAdapter.notifyDataSetChanged();
					}else if(cityType==1){
						//市
						for(TRegion region:list){
							cityAdapter.add(region);
						}
						cityAdapter.notifyDataSetChanged();
					}else if(cityType==2){
						//区
						for(TRegion region:list){
							areaAdapter.add(region);
						}
						areaAdapter.notifyDataSetChanged();
					}
				}
				return;
			}
		}
		this.showShortToast(message);
	}
}
