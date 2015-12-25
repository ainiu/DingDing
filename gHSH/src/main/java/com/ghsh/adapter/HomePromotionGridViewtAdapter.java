package com.ghsh.adapter;

import java.util.List;

import android.content.Context;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ghsh.R;
import com.ghsh.code.bean.TGroup;
import com.ghsh.code.volley.DVolley;
import com.ghsh.util.DateFormatUtil;

public class HomePromotionGridViewtAdapter extends AbstractBaseAdapter<TGroup> {

	private TimeHandler timeHandler;
	public HomePromotionGridViewtAdapter(Context context, List<TGroup> list) {
		super(context, list);
		timeHandler=new TimeHandler();
		timeHandler.sendEmptyMessageDelayed(0, 1000);
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		HomeFunHolder holder;
		final TGroup group = list.get(position);
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.activity_home_listview_item_promotion, null);
			holder = new HomeFunHolder();
			holder.titleView=(TextView)convertView.findViewById(R.id.home_item_title1);
			holder.descView=(TextView)convertView.findViewById(R.id.home_item_desc1);
			holder.shopPriceView=(TextView)convertView.findViewById(R.id.home_item_shopPrice1);
			holder.timeView=(TextView)convertView.findViewById(R.id.home_item_time1);
			holder.imageView=(ImageView)convertView.findViewById(R.id.home_item_image1);
			convertView.setTag(holder);
		} else {
			holder = (HomeFunHolder) convertView.getTag();
		}
		holder.titleView.setText(group.getGroupName());
		holder.descView.setText(group.getGroupName());
		holder.shopPriceView.setText("￥"+group.getShopPrice());
		holder.timeView.setText(DateFormatUtil.getTimeFromIntA(group.getCountdownTotalTime()));
		DVolley.getImage(group.getGroupImage(), holder.imageView,R.drawable.default_image);
//		new TimeCount(holder.timeView,group.getCountdownTotalTime(),1000).start();
		return convertView;
	}

	class TimeHandler extends Handler {
		 @Override
		public void handleMessage(Message msg) {
			boolean flag=false;
			for(TGroup group:list){
				long time=group.getCountdownTotalTime()-1;
				if(time<=0){
					group.setCountdownTotalTime(0);
				}else{
					group.setCountdownTotalTime(time);
					flag=true;
				}
			}
			if(flag){
				timeHandler.sendEmptyMessageDelayed(0, 1000);
			}
			HomePromotionGridViewtAdapter.this.notifyDataSetChanged();
		}
	 }
	
//	class TimeCount extends CountDownTimer {
//		private TextView timeView;
//        public TimeCount(TextView timeView,long millisInFuture, long countDownInterval) {
////        	millisInFuture:总时长
////        	countDownInterval:倒计时间隔
//            super(millisInFuture, countDownInterval);
//            this.timeView=timeView;
//        }     
//        @Override     
//        public void onFinish() {
//        	//计算完毕
////            tv.setText("finish");        
//        } 
//        @Override     
//        public void onTick(long millisUntilFinished) {     
//        	this.timeView.setText(millisUntilFinished / 1000+"");     
////            Toast.makeText(NewActivity.this, millisUntilFinished / 1000 + "", 
////                Toast.LENGTH_LONG).show();//toast有显示时间延迟        
//        }    
//    } 
	class HomeFunHolder {
		ImageView imageView;
		TextView titleView;
		TextView descView;
		TextView shopPriceView;
		TextView timeView;
	}
}
