package com.ghsh.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ghsh.AppManager;
import com.ghsh.R;
import com.ghsh.activity.base.BaseActivity;
import com.ghsh.code.bean.TSetting;
import com.ghsh.code.volley.DResponseListener;
import com.ghsh.code.volley.DVolleyConstans;
import com.ghsh.code.volley.bean.ReturnBean;
import com.ghsh.code.volley.model.PersonalModel;
import com.ghsh.dialog.DProgressDialog;
import com.ghsh.services.ApkUpdateVersion;
import com.ghsh.services.UpdateService;
import com.ghsh.util.CleanManager;
import com.ghsh.util.MenberUtils;
import com.ghsh.util.SettingUtils;
import com.ghsh.view.util.DAlertUtil;

/**
 * 设置
 * */
public class SettingActivity extends BaseActivity implements OnClickListener,DResponseListener {
	private TextView titleView;
	private TextView smartmodeView, hqpictureView, normalView;// 智能模式,高质量,低质量

	private ImageView nvoiceView1, nvoiceView2, nvoiceView3;// 图片显示
	private ImageView pushView;// 推送设置
	private TextView mobileView;// 官方电话
	private TextView clearLocalSizeView,appUpdateVersionView;//本地缓存大小
	private Button websiteButton, techButton,mobileButton,exitLoginButton,clearLocalButton,appUpdateButton;// 官方网站，版权声明，官方客服
	private PersonalModel personalModel;
	private DProgressDialog progressDialog;
	private TSetting setting;
	private ApkUpdateVersion apkUpdateVersion;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		this.initView();
		this.initListener();
		this.initData();
		personalModel=new PersonalModel(this);
		personalModel.addResponseListener(this);
//		progressDialog.show();
//		personalModel.getSetting();
	}

	private void initView() {
		progressDialog=new DProgressDialog(this);
		titleView = (TextView) this.findViewById(R.id.header_title_view);
		titleView.setText(R.string.setting_title);

		smartmodeView = (TextView) this.findViewById(R.id.setting_smartmode);
		hqpictureView = (TextView) this.findViewById(R.id.setting_hqpicture);
		normalView = (TextView) this.findViewById(R.id.setting_normal);

		nvoiceView1 = (ImageView) this.findViewById(R.id.setting_invoice1);
		nvoiceView2 = (ImageView) this.findViewById(R.id.setting_invoice2);
		nvoiceView3 = (ImageView) this.findViewById(R.id.setting_invoice3);
		
		pushView = (ImageView) this.findViewById(R.id.setting_push);
		
		websiteButton = (Button) this.findViewById(R.id.setting_website);
		techButton = (Button) this.findViewById(R.id.setting_tech);
		clearLocalButton = (Button) this.findViewById(R.id.setting_clear_host);
		clearLocalSizeView= (TextView) this.findViewById(R.id.setting_clear_host_size);
		
		mobileButton= (Button) this.findViewById(R.id.setting_mobile);
		mobileView = (TextView) this.findViewById(R.id.setting_mobile_text);
		exitLoginButton= (Button) this.findViewById(R.id.setting_exitLogin);
		
		appUpdateButton= (Button) this.findViewById(R.id.setting_app_update);
		appUpdateVersionView= (TextView) this.findViewById(R.id.setting_app_update_version);
	}
	
	private void initData(){
		int nvoice=SettingUtils.readeNvoice(this);
		switch(nvoice){
		case 1:
			nvoiceView1.setVisibility(View.VISIBLE);
			break;
		case 2:
			nvoiceView2.setVisibility(View.VISIBLE);
			break;
		case 3:
			nvoiceView3.setVisibility(View.VISIBLE);
			break;
		}
		if(MenberUtils.isLogin(this)){
			exitLoginButton.setVisibility(View.VISIBLE);
		}else{
			exitLoginButton.setVisibility(View.GONE);
		}
		boolean isPush=SettingUtils.readePush(this);
		pushView.setTag(isPush);
		if (isPush) {
			pushView.setImageResource(R.drawable.setting_push_on_icon);
		} else {
			pushView.setImageResource(R.drawable.setting_push_off_icon);
		}
		clearLocalSizeView.setText(CleanManager.getCacheSize(this));
		appUpdateVersionView.setText(AppManager.getVersionName(this));
	}

	private void initListener() {
		smartmodeView.setOnClickListener(this);
		hqpictureView.setOnClickListener(this);
		normalView.setOnClickListener(this);
		pushView.setOnClickListener(this);
		mobileButton.setOnClickListener(this);
		websiteButton.setOnClickListener(this);
		techButton.setOnClickListener(this);
		exitLoginButton.setOnClickListener(this);
		clearLocalButton.setOnClickListener(this);
		appUpdateButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v == smartmodeView) {
			// 智能模式
			nvoiceView1.setVisibility(View.VISIBLE);
			nvoiceView2.setVisibility(View.GONE);
			nvoiceView3.setVisibility(View.GONE);
			SettingUtils.writeNvoice(this, 1);
		} else if (v == hqpictureView) {
			// 高质量
			nvoiceView1.setVisibility(View.GONE);
			nvoiceView2.setVisibility(View.VISIBLE);
			nvoiceView3.setVisibility(View.GONE);
			SettingUtils.writeNvoice(this, 2);
		} else if (v == normalView) {
			// 低质量
			nvoiceView1.setVisibility(View.GONE);
			nvoiceView2.setVisibility(View.GONE);
			nvoiceView3.setVisibility(View.VISIBLE);
			SettingUtils.writeNvoice(this, 3);
		} else if (v == pushView) {
			// 推送通知
			if ((Boolean) pushView.getTag()) {
				pushView.setTag(false);
				pushView.setImageResource(R.drawable.setting_push_off_icon);
			} else {
				pushView.setTag(true);
				pushView.setImageResource(R.drawable.setting_push_on_icon);
			}
			SettingUtils.writePush(this, (Boolean)pushView.getTag());
		}else if(v==clearLocalButton){
			//清空本地缓存
			DAlertUtil.alertOKAndCel(this, R.string.setting_tip_clear_host, new android.content.DialogInterface.OnClickListener(){
				@Override
				public void onClick(DialogInterface dialog, int which) {
					CleanManager.cleanCache(SettingActivity.this);
					clearLocalSizeView.setText(CleanManager.getCacheSize(SettingActivity.this));
				}
			}).show();
		}else if(v==mobileButton){
			//官方客服
			if(setting!=null&&setting.getShopTel()!=null&&!setting.getShopTel().equals("")){
				Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+setting.getShopTel()));     
			    this.startActivity(intent);  
			}else{
				this.showShortToast(R.string.not_message);
			}
		}else if(v==websiteButton){
			//官方网站
			if(setting!=null&&setting.getShopWeb()!=null&&!setting.getShopWeb().equals("")){
				Intent intent=new Intent(this,WebActivity.class);
				intent.putExtra("titleName", setting.getShopWeb());
				intent.putExtra("href", DVolleyConstans.getServiceHost(setting.getShopWeb()));
				this.startActivity(intent);
			}else{
				this.showShortToast(R.string.not_message);
			}
		}else if(v==techButton){
			//版权声明
			this.showShortToast(R.string.not_message);
		}else if(v==exitLoginButton){
			//注销登录
			this.loginOut();
			this.finish();
		}else if(v==appUpdateButton){
			//版本更新
			if(apkUpdateVersion==null){
				apkUpdateVersion=new ApkUpdateVersion(this,true);
			}
			apkUpdateVersion.checkVersion();//检查apk版本
		}
	}

	@Override
	public void onMessageResponse(ReturnBean bean, int result, String message,Throwable error) {
		progressDialog.dismiss();
		if(error!=null){
			this.showShortToast(error.getMessage());
			return;
		}
		if(result==DVolleyConstans.RESULT_OK){
			if(bean.getType()==DVolleyConstans.METHOD_GET){
				setting=(TSetting)bean.getObject();
				if(setting!=null){
					if(setting.getShopTel()!=null&&setting.getShopTel()!=null){
						mobileView.setText(setting.getShopTel());
					}
				}
			}else if(bean.getType()==DVolleyConstans.METHOD_GET_APK_VERSION){
				try {
					final float newVersion=Float.parseFloat(bean.getObject()+"");
					int oldVersion=AppManager.getVersionCode(this);
					Log.i("APP自动更新", "当前版本："+oldVersion+" 最新版本："+newVersion);
					if(newVersion>oldVersion){
						//提示是否更新版本
						AlertDialog.Builder builder = new AlertDialog.Builder(this);
						builder.setTitle("发现新版本，可以更新...").setMessage("更新操作").setPositiveButton("确认更新", new android.content.DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								Intent intent=new Intent(SettingActivity.this, UpdateService.class);
								intent.putExtra("version", newVersion);
								SettingActivity.this.startService(intent);
							}
						}).setNegativeButton("取消", null).create().show();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
