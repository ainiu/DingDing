package com.ghsh.activity;

import java.util.ArrayList;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;

import com.ghsh.Constants;
import com.ghsh.ShareConstants;
import com.ghsh.activity.base.BaseActivity;
import com.ghsh.adapter.ShareGridViewtAdapter;
import com.ghsh.code.bean.TGoods;
import com.ghsh.code.bean.TShare;
import com.ghsh.code.exception.AppViewException;
import com.ghsh.code.volley.DVolleyConstans;
import com.ghsh.share.qq.QqShare;
import com.ghsh.share.sina.SinaWeiboShare;
import com.ghsh.share.tencent.TencentWeiboShare;
import com.ghsh.share.weixin.WeiXinShare;
import com.ghsh.util.ImageAsyncUtil;
import com.ghsh.R;

/**
 * 商品属性选择框
 * */
public class ShareActivity extends BaseActivity implements OnItemClickListener,OnClickListener{
	private String goodsName,goodsImage;
	private GridView gridView;
	private ShareGridViewtAdapter adapter;
	private Button cancelButton;
	private Bitmap shareImage;
	private String shareImageLocalPath;
	private SinaWeiboShare sinaWeiboShare;//新浪分享
	private TencentWeiboShare tencentWeiboShare;//腾讯微博分享
	private QqShare qqShare;//QQ分享
	private WeiXinShare weiXinShare;//微信分享
	private Bundle savedInstanceState;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.savedInstanceState=savedInstanceState;
		this.setContentView(R.layout.activity_share);
		WindowManager m = getWindowManager();
		Display d = m.getDefaultDisplay(); // 为获取屏幕宽、高
		LayoutParams p = getWindow().getAttributes(); // 获取对话框当前的参数值
		p.width = d.getWidth(); // 宽度设置为屏幕
		getWindow().setAttributes(p); // 设置生效
		getWindow().setGravity(Gravity.BOTTOM); // 设置靠右对齐
	    
		this.initView();
		this.initListener();
		Intent intent=this.getIntent();
		goodsName=intent.getStringExtra("goodsName");
		goodsImage=intent.getStringExtra("goodsImage");
		if (goodsImage!=null&&!goodsImage.equals("")) {
			ImageAsyncUtil.downloadImageUrl(goodsImage, new ImageAsyncUtil.ImageListener() {
				@Override
				public void onCallback(Bitmap bitmap, String imageLocalPath, Throwable error) {
					shareImage=bitmap;
					shareImageLocalPath=imageLocalPath;
				}
			});
        }
	}
	
	private void initView() {
		adapter=new ShareGridViewtAdapter(this,new ArrayList<TShare>());
		cancelButton= (Button) this.findViewById(R.id.share_cancel_button);
		gridView = (GridView) this.findViewById(R.id.share_gridView);
		gridView.setAdapter(adapter);
		adapter.setList(ShareConstants.getShareList());
		adapter.notifyDataSetChanged();
	}

	private void initListener() {
		gridView.setOnItemClickListener(this);
		cancelButton.setOnClickListener(this);
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
		try {
			String shareUrl="http://www.ganhelife.com/mobile";//DVolleyConstans.getServiceHost("index.php?app=goods&Debug=Wap&id="+goods.getGoodsID()+"&shopID="+DVolleyConstans.getShopID());
			TShare share=adapter.getItem(position);
			TShare.ShareConfig shareConfig=share.getShareBean().shareConfig;
			if(share.getId().equals("1")){
				//分享到微信朋友圈
				if(weiXinShare==null){
					weiXinShare=new WeiXinShare(this,shareConfig.APP_ID);
				}
				weiXinShare.sharePengyouquan(goodsName, shareImage,shareUrl);
				this.finish();
			}if(share.getId().equals("2")){
				//分享到微信好友
				if(weiXinShare==null){
					weiXinShare=new WeiXinShare(this,shareConfig.APP_ID);
				}
				weiXinShare.shareHaoyou(goodsName,shareImage,shareUrl);
				this.finish();
			}else if(share.getId().equals("3")){
				//分享到QQ
				if(qqShare==null){
					qqShare=new QqShare(this,shareConfig.APP_ID);
				}
				qqShare.shareQQ(goodsName,shareUrl, goodsImage);
			}else if(share.getId().equals("4")){
				//分享到QQ空间
				if(qqShare==null){
					qqShare=new QqShare(this,shareConfig.APP_ID);
				}
				qqShare.shareQzone(goodsName,shareUrl, goodsImage);
			}else if(share.getId().equals("5")){
				//分享到腾讯微博
				if(qqShare==null){
					qqShare=new QqShare(this,shareConfig.APP_ID);
				}
				qqShare.shareQQWeiBo(goodsName+"\n"+shareUrl,shareImageLocalPath,shareImage);
			}else if(share.getId().equals("6")){
				//分享到新浪微博
				if(sinaWeiboShare==null){
					sinaWeiboShare=new SinaWeiboShare(this,shareConfig.APP_KEY);//shareConfig.APP_KEY
					sinaWeiboShare.init(savedInstanceState);
				}
				sinaWeiboShare.shearSinaWeibo(goodsName+"\n"+shareUrl, shareImage);
			}
		} catch (Throwable e) {
			AppViewException.onViewException(e);
		}
	}
	
	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		if(sinaWeiboShare!=null){
			sinaWeiboShare.handleWeiboResponse(intent);
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(Constants.DEBUG){
			Log.i("ShareActivity  onActivityResult", "requestCode:"+requestCode+" resultCode:"+resultCode);
		}
		if(qqShare!=null&&requestCode==ShareConstants.REQUEST_CODE_QQ){
			qqShare.onActivityResult(requestCode, resultCode, data);
		}
		if(sinaWeiboShare!=null&&requestCode==ShareConstants.REQUEST_CODE_SINA_WEIBO){
			sinaWeiboShare.authorizeCallBack(requestCode, resultCode, data);
		}
	}
	@Override
	public void onClick(View v) {
		if(v==cancelButton){
			this.finish();
		}
	}
}
