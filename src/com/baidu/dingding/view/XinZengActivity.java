package com.baidu.dingding.view;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.dingding.CityJsonParse;
import com.baidu.dingding.R;
import com.baidu.dingding.activity.BaseActivity;
import com.baidu.dingding.code.DResponseListener;
import com.baidu.dingding.code.DVolleyConstans;
import com.baidu.dingding.code.ReturnBean;
import com.baidu.dingding.code.model.AddressSaveModel;
import com.baidu.dingding.entity.AddressBean.City;
import com.baidu.dingding.entity.AddressBean.Country;
import com.baidu.dingding.entity.AddressBean.Provinces;
import com.baidu.dingding.entity.CityJsonEntity;
import com.baidu.dingding.entity.ProvinceBean;
import com.baidu.dingding.until.LogUtil;
import com.baidu.dingding.until.SharedPreferencesUtils;
import com.baidu.dingding.until.Toasttool;
import com.baidu.dingding.view.MyView.dialog.MyProgressDialog;
import com.bigkoo.pickerview.OptionsPickerView;
import com.sevenheaven.iosswitch.ShSwitchView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

public class XinZengActivity extends BaseActivity implements DResponseListener{
	private EditText name,tel,mobile,youbian,xiangxidizhi;
	private TextView selectaddress;
	private RelativeLayout relativeLayout;
	private Button save;
	private OptionsPickerView pvOptions;
	private Dialog prodialog;
	private ArrayList<ProvinceBean> options1Items = new ArrayList<ProvinceBean>();                                    //省
	private ArrayList<ArrayList<String>> options2Items = new ArrayList<ArrayList<String>>();                          //市
	private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<ArrayList<ArrayList<String>>>();    // 区
	private CityJsonEntity cityjsonEntity;
	private AddressSaveModel addressSaveModel;
	private ShSwitchView shSwitchView;
	Handler myHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case 1:
					selectaddress.setText((CharSequence) msg.obj);
					break;
			}
			super.handleMessage(msg);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_xin_zeng);
		initDate();
		initModel();
		initView();
		initLener();
	}
	String userNumber="",token="";
	private void initDate() {
		userNumber = (String) SharedPreferencesUtils.get(this, "userNumber", "");
		token = (String) SharedPreferencesUtils.get(this, "token", "");
	}

	private void initModel() {
		addressSaveModel=new AddressSaveModel(this);
		addressSaveModel.addResponseListener(this);
	}

	public Boolean  Istoandthree(int m,int  n,int l){
		Provinces provinces = cityjsonEntity.getProvinces().get(m);
		City city=provinces.getCities().get(n);
		if(city.getCountries().size()==0) return false;
		Country country=city.getCountries().get(l);
		return true;
	}

	public String  getAddress(int i,int  j,int k){
		Provinces provinces = cityjsonEntity.getProvinces().get(i);
		City city=provinces.getCities().get(j);
		if(city.getCountries().size()==0) return city.getCity_post();
		Country country=city.getCountries().get(k);
		return country.getCountry_post();
	}
	String xiugaiquyuid,province_city_area;
	private void initLener() {
		//保存
		save.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String receiveName=name.getText().toString().trim();
				if(TextUtils.isEmpty(receiveName)){
					Toasttool.MyToast(XinZengActivity.this,"请输入名字");
					return;
				}
				String receiveTel=tel.getText().toString().trim();
				if(TextUtils.isEmpty(receiveTel)){
					Toasttool.MyToast(XinZengActivity.this,"请输入电话号码");
					return;
				}
				String receiveMobile=mobile.getText().toString().trim();
				if(TextUtils.isEmpty(receiveMobile)){
					Toasttool.MyToast(XinZengActivity.this,"请输入手机号码");
					return;
				}
				String receivePost=youbian.getText().toString().trim();
				if(TextUtils.isEmpty(receivePost)){
					Toasttool.MyToast(XinZengActivity.this,"请输入邮编");
					return;
				}
				String receiveAddress=xiangxidizhi.getText().toString().trim();
				if(TextUtils.isEmpty(receiveAddress)){
					Toasttool.MyToast(XinZengActivity.this,"请输入详细");
					return;
				}
				String istrue;
				if(shSwitchView.isOn()){
					istrue=1+"";
				}else{
					istrue=0+"";
				}
				try {
					addressSaveModel.findConsultList(token,userNumber, URLEncoder.encode(receiveName,"utf-8"),URLEncoder.encode(receiveTel,"utf-8"),receiveMobile,receivePost,
							URLEncoder.encode(receiveAddress,"utf-8"),xiugaiquyuid,istrue,URLEncoder.encode(province_city_area,"utf-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		});
		//显示三级联动
		relativeLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				pvOptions.show();
			}
		});
		//根据选中的位置的值
		pvOptions.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {

			@Override
			public void onOptionsSelect(int options1, int option2, int options3) {
				LogUtil.i("选中位置", "options1=" + options1 + "options1=" + option2 + "options1=" + options3);

				xiugaiquyuid = getAddress(options1, option2, options3);
				//返回的分别是三个级别的选中位置
				String tx = "";
				if (Istoandthree(options1, option2, options3)) {
					tx = options1Items.get(options1).getPickerViewText() + "_"
							+ options2Items.get(options1).get(option2)
							+ options3Items.get(options1).get(option2).get(options3);
				} else {
					tx = options1Items.get(options1).getPickerViewText() + "_"
							+ options2Items.get(options1).get(option2);
				}
				String str1=tx.substring(0,tx.indexOf("_"));
				String str2=tx.substring(tx.indexOf("_")+1);
				province_city_area=str1+str2;
				Message message=new Message();
				message.what=1;
				message.obj=str1+str2;
				myHandler.sendMessage(message);
				LogUtil.i("修改后的id","xiugaiquyuid="+xiugaiquyuid);

			}
		});
	}

	private void initView() {
		shSwitchView= (ShSwitchView) this.findViewById(R.id.switch_view);
		pvOptions = new OptionsPickerView(this);
		prodialog = MyProgressDialog.createLoadingDialog(this, "Loding...");
		name= (EditText) this.findViewById(R.id.xinzeng_edit_name);
		tel= (EditText) this.findViewById(R.id.xinzeng_edit_title);
		mobile= (EditText) this.findViewById(R.id.xinzeng_edit_phone);
		youbian= (EditText) this.findViewById(R.id.xinzeng_edit_Post);
		relativeLayout= (RelativeLayout) this.findViewById(R.id.relativeLayout);
		selectaddress= (TextView) this.findViewById(R.id.xinzeng_edit_suozaiqu);
		xiangxidizhi= (EditText) this.findViewById(R.id.xinzeng_edit_xiangxidizhi);
		save= (Button) this.findViewById(R.id.xinzeng_button_01);
		cityjsonEntity = CityJsonParse.initConfige(this);
		initData();
		//三级联动效果
		pvOptions.setPicker(options1Items, options2Items, options3Items, true);
		//设置选择的三级单位
		// pwOptions.setLabels("省", "市", "区");
		pvOptions.setTitle(this.getResources().getString(R.string.dizhixuanzhe));
		pvOptions.setCyclic(false, false, false);
		//设置默认选中的三级项目
		pvOptions.setSelectOptions(0, 0, 0);
	}

	private void initData() {
		/**遍历出数据*/
		for (int i = 0; i < cityjsonEntity.getProvinces().size(); i++) {
			Provinces provinces = cityjsonEntity.getProvinces().get(i);
			//选项一
			options1Items.add(new ProvinceBean(provinces.getShen_Poat(),provinces.getProvinceName(),"",""));
			ArrayList<String> options2Items_01 = new ArrayList<String>();
			ArrayList<ArrayList<String>> options3Items_01 = new ArrayList<ArrayList<String>>();
			for (int j = 0; j < provinces.getCities().size(); j++) {
				City city = provinces.getCities().get(j);
				options2Items_01.add(city.getCityName());
				// options2Items_01.add(city.getCity_post());
				//选项2
				ArrayList<String> options3Items_01_02 = new ArrayList<String>();
				if (city.getCountries().size()==0) {
					options3Items_01_02.add("");
					options3Items_01.add(options3Items_01_02);
					continue;
				}
				ArrayList<String> options3Items_01_01 = new ArrayList<String>();
				for (int k = 0; k < city.getCountries().size(); k++) {
					Country country = city.getCountries().get(k);
					// hasmap.put(country.getCountry_post(),country.getCountryNmae());
					options3Items_01_01.add(country.getCountryNmae());
				}
				options3Items_01.add(options3Items_01_01);
			}
			options3Items.add(options3Items_01);
			options2Items.add(options2Items_01);
		}
	}

	public void doClick(View view){
		this.finish();
	}

	@Override
	public void onMessageResponse(ReturnBean bean, int result, String message, Throwable error) {
		LogUtil.i("添加地址", "bean=" + bean.getObject() + "result=" + result + "mesage=" + message);
		prodialog.dismiss();
		// progressDialog.dismiss();
		if (result == DVolleyConstans.RESULT_FAIL) {
			Toasttool.MyToastLong(this, message);
			return;
		}
		if(result == DVolleyConstans.RESULT_OK){
			if(bean.getType()==DVolleyConstans.SAVERECEIVEADDRESS){
				Toasttool.MyToast(this,bean.getString());
				this.finish();
			}
		}
	}
}
