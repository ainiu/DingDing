package com.ghsh.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ghsh.Constants;
import com.ghsh.R;
import com.ghsh.code.bean.TOrder;
import com.ghsh.code.bean.TOrderItem;
import com.ghsh.code.volley.DVolley;

public class OrderListListViewtAdapter extends AbstractBaseAdapter<TOrder> {
	private Listener listener;
	private boolean isAllOrder=false;//是否查询所有订单
	public OrderListListViewtAdapter(Context context, List<TOrder> list) {
		super(context, list);
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		OrderListHolder holder;
		final TOrder order=list.get(position);
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.activity_orderlist_listview_item, null);
			holder = new OrderListHolder();
			holder.ordersnView=(TextView)convertView.findViewById(R.id.order_item_orderSN);
			holder.totalView=(TextView)convertView.findViewById(R.id.order_item_total);
			holder.goodsLayout=(LinearLayout)convertView.findViewById(R.id.order_item_goods_layout);
			holder.totalNumberView=(TextView)convertView.findViewById(R.id.order_item_totalNumber);
			holder.statusLayout=convertView.findViewById(R.id.order_item_orderStatus_layout);
			holder.statusView=(TextView)convertView.findViewById(R.id.order_item_orderStatus);
			holder.button1_pay=(Button)convertView.findViewById(R.id.order_item_button1_pay);
			holder.button2_remind=(Button)convertView.findViewById(R.id.order_item_button2_remind);
			holder.button3_receipt=(Button)convertView.findViewById(R.id.order_item_button3_receipt);
			holder.button3_stream=(Button)convertView.findViewById(R.id.order_item_button3_stream);
			holder.button4_evaluate=(Button)convertView.findViewById(R.id.order_item_button4_evaluate);
			convertView.setTag(holder);
		} else {
			holder = (OrderListHolder) convertView.getTag();
		}
		int totalNumber=0;
		if(order.getOrderItemList()!=null&&order.getOrderItemList().size()!=0){
			holder.goodsLayout.removeAllViews();
			for(TOrderItem orderItem:order.getOrderItemList()){
				View view = LayoutInflater.from(context).inflate(R.layout.activity_orderlist_goods_item, null);
				ImageView goodsImageView = (ImageView) view.findViewById(R.id.order_item_goods_image);
				TextView goodsNameView = (TextView) view.findViewById(R.id.order_item_goods_name);
				TextView goodsPriceView = (TextView) view.findViewById(R.id.order_item_goods_price);
				TextView goodsNumberView = (TextView) view.findViewById(R.id.order_item_goods_number);
				TextView goodsAttrView = (TextView) view.findViewById(R.id.order_item_goods_goodsAttr);
				DVolley.getImage(orderItem.getGoodsImage(),goodsImageView,R.drawable.default_image);
				goodsNameView.setText(orderItem.getGoodsName());
				goodsPriceView.setText("￥"+orderItem.getGoodsPrice());
				goodsNumberView.setText(orderItem.getGoodsNumber());
				goodsAttrView.setText(orderItem.getGoodsAttr());
				holder.goodsLayout.addView(view);
				totalNumber=totalNumber+Integer.parseInt(orderItem.getGoodsNumber());
			}
		}
		holder.ordersnView.setText(order.getOrderSN());
		holder.totalView.setText("￥"+order.getOrderAmount());
		holder.totalNumberView.setText(String.format(context.getResources().getString(R.string.order_list_order_totalNumber), totalNumber+""));
		holder.button1_pay.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(listener!=null){
					listener.payCallback(order);
				}
			}
		});
		holder.button2_remind.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(listener!=null){
					listener.remindCallback(order.getOrderID(), order.getOrderSN());
				}
			}
		});
		holder.button3_receipt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(listener!=null){
					listener.receiptCallback(order.getOrderID());
				}
			}
		});
		holder.button3_stream.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(listener!=null){
					listener.streamCallback(order.getOrderID(), order.getOrderSN());
				}
			}
		});
		holder.button4_evaluate.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(listener!=null){
					listener.commentCallback(order.getOrderID());
				}
			}
		});
		holder.button1_pay.setVisibility(View.GONE);
		holder.button2_remind.setVisibility(View.GONE);
		holder.button3_stream.setVisibility(View.GONE);
		holder.button3_receipt.setVisibility(View.GONE);
		holder.button4_evaluate.setVisibility(View.GONE);
		
		if(order.getOrderType().equals(Constants.ORDER_TYLE_1)){
			//待付款
			holder.button1_pay.setVisibility(View.VISIBLE);
		}else if(order.getOrderType().equals(Constants.ORDER_TYLE_2)){
			//待发货
		}else if(order.getOrderType().equals(Constants.ORDER_TYLE_3)){
			//待收货
			holder.button3_stream.setVisibility(View.VISIBLE);
			holder.button3_receipt.setVisibility(View.VISIBLE);
		}else if(order.getOrderType().equals(Constants.ORDER_TYLE_4)){
			//已完成
			holder.button4_evaluate.setVisibility(View.VISIBLE);
		}else if(order.getOrderType().equals(Constants.ORDER_TYLE_5)){
			//取消
		}else if(order.getOrderType().equals(Constants.ORDER_TYLE_6)){
			//无效
		}else if(order.getOrderType().equals(Constants.ORDER_TYLE_7)){
			//退货
		}else if(order.getOrderType().equals(Constants.ORDER_TYLE_7)){
			//已评论
		}
//		if(!order.isEvaluationStatus()){
//			holder.button4_evaluate.setVisibility(View.VISIBLE);
//		}
		
		if(isAllOrder){
			holder.statusLayout.setVisibility(View.VISIBLE);
			holder.statusView.setText(Constants.ORDER_TYLE_MAP().get(order.getOrderType()));
		}
		
		return convertView;
	}

	public void addListener(Listener listener){
		this.listener=listener;
	}
	
	public void removeByOrderID(String orderID){
		for(TOrder order:list){
			if(order.getOrderID().equals(orderID)){
				list.remove(order);
				break;
			}
		}
		this.notifyDataSetChanged();
	}

	class OrderListHolder {
		View statusLayout;// 订单状态布局
		TextView statusView;// 订单状态
		TextView ordersnView;// 订单编号
		TextView totalNumberView;//数量
		TextView totalView;// 总额
		LinearLayout goodsLayout;// 商品列表
		Button button1_pay;// 付款按钮
		Button button2_remind;//提醒卖家发货
		Button button3_receipt;//确认收货
		Button button3_stream;//查看物流
		Button button4_evaluate;//评价
	}
	

	public void setAllOrder(boolean isAllOrder) {
		this.isAllOrder = isAllOrder;
	}

	public interface Listener{
		/**支付*/
		public void payCallback(TOrder order);
		/**确认收货*/
		public void receiptCallback(String orderID);
		/**评论*/
		public void commentCallback(String orderID);
		/**提醒卖家发货*/
		public void remindCallback(String orderID,String orderSN);
		/**查看物流*/
		public void streamCallback(String orderID,String orderSN);
	}
}
