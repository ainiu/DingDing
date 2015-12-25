package com.baidu.dingding.zhifu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.baidu.dingding.R;
import com.baidu.dingding.activity.BaseActivity;
import com.baidu.dingding.entity.ToPayBean;
import com.baidu.dingding.until.LogUtil;
import com.baidu.dingding.zhifu.model.OrderInfo;
import com.baidu.dingding.zhifu.util.DigestUtils;
import com.dinpay.plugin.activity.DinpayChannelActivity;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ZhifuActivity extends BaseActivity {

    private EditText edt_merchantcode, edt_notifyurl, edt_version, edt_signtype, edt_orderno,
            edt_ordertime, edt_orderamount, edt_productname, edt_redoflag, edt_productcode, edt_productnum,
            edt_produnctdesc, edt_extra_return_param, mer_id, order_id, order_monery, goods_name;
    private Button btn_submit;

    private String merchant_code, notify_url, interface_version, sign_type,
            order_no, order_time, order_amount, product_name, redo_flag, product_code,
            product_num, product_desc, extra_return_param, merID, orderID, monery, name;
    private List<Map<String, String>> list;
    private float mount;
    private ToPayBean toPayBean = new ToPayBean();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zhifu);
        toPayBean = getIntent().getParcelableExtra("toPayBean");
        LogUtil.i("获取订单数据", "toPayBean=" + toPayBean.toString());
        edt_merchantcode = (EditText) this.findViewById(R.id.edt_merchantcode);
        edt_notifyurl = (EditText) this.findViewById(R.id.edt_notifyurl);
        edt_version = (EditText) this.findViewById(R.id.edt_version);
        edt_signtype = (EditText) this.findViewById(R.id.edt_signtype);
        edt_orderno = (EditText) this.findViewById(R.id.edt_orderno);
        edt_ordertime = (EditText) this.findViewById(R.id.edt_ordertime);
        edt_orderamount = (EditText) this.findViewById(R.id.edt_orderamount);
        edt_productname = (EditText) this.findViewById(R.id.edt_productname);
        edt_redoflag = (EditText) this.findViewById(R.id.edt_redoflag);
        edt_productcode = (EditText) this.findViewById(R.id.edt_productcode);
        edt_productnum = (EditText) this.findViewById(R.id.edt_productnum);
        edt_produnctdesc = (EditText) this.findViewById(R.id.edt_produnctdesc);
        edt_extra_return_param = (EditText) this.findViewById(R.id.edt_extra_return_param);
        btn_submit = (Button) this.findViewById(R.id.btn_submit);
        /*//子菜单
        mer_id = (EditText) this.findViewById(R.id.mer_id);
        order_id = (EditText) this.findViewById(R.id.order_id);
        order_monery = (EditText) this.findViewById(R.id.order_monery);
        goods_name = (EditText) this.findViewById(R.id.goods_name);
        addOrder = (Button) this.findViewById(R.id.addOrder);
        //子菜单赋值
        mer_id.setText(toPayBean.getOrders_info().get(0).getMer_id());
        order_id.setText(toPayBean.getOrders_info().get(0).getOrder_id());
        order_monery.setText(toPayBean.getOrders_info().get(0).getOrder_monery());
        goods_name.setText(toPayBean.getOrders_info().get(0).getGoods_name());*/


        //edt_merchantcode.setText(toPayBean.getMerchant());//商户号(商户需替换成自己的商户号)
        edt_merchantcode.setText(toPayBean.getMerchant());
        edt_notifyurl.setText(toPayBean.getNotify_url());//服务器异步通知地址(商户需替换成自己的服务通知地址)
//		edt_notifyurl.setText("http://192.168.1.178:3080/return/return.jsp");//服务器异步通知地址(商户需替换成自己的服务通知地址)
        edt_version.setText(toPayBean.getInterface_version());//接口版本
        edt_signtype.setText(toPayBean.getSign_type());//签名方式
        edt_orderno.setText(String.valueOf(System.currentTimeMillis()));//商户网站唯一订单号
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        edt_ordertime.setText(formatter.format(new Date()));//商户订单时间
        edt_orderamount.setText(toPayBean.getOreder_amount());//商户订单总金额
        edt_productname.setText(toPayBean.getProduct_name());//商品名称
        edt_redoflag.setText(toPayBean.getRedo_flag());//订单是否允许重复标识  可选

        edt_productcode.setText(toPayBean.getProduct_code());//商品编号   可选
        edt_productnum.setText(toPayBean.getProduct_num());//商品数量   可选
        edt_produnctdesc.setText(toPayBean.getProduct_desc());//商品描述   可选
        edt_extra_return_param.setText("");//公用回传参数    可选

       list = new ArrayList<Map<String, String>>();
        Map<String, String> params = new HashMap<String, String>();
        params.put("mer_id", toPayBean.getOrders_info().get(0).getMer_id());
        params.put("order_id", toPayBean.getOrders_info().get(0).getOrder_id());
        params.put("order_monery", toPayBean.getOrders_info().get(0).getOrder_monery());
        params.put("goods_name", toPayBean.getOrders_info().get(0).getGoods_name());
        list.add(params);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                pay();
            }
        });
    }


    @Override
    protected void onResume() {
        edt_orderno.setText(toPayBean.getOrder_no());//商户网站唯一订单号
        super.onResume();
    }

    private void pay() {
       /* merchant_code = edt_merchantcode.getText().toString();
        notify_url = edt_notifyurl.getText().toString();
        interface_version = edt_version.getText().toString();
        sign_type = edt_signtype.getText().toString();
        order_no = edt_orderno.getText().toString();
        order_time = edt_ordertime.getText().toString();

        product_name = edt_productname.getText().toString();
        redo_flag = edt_redoflag.getText().toString();
        product_code = edt_productcode.getText().toString();
        product_num = edt_productnum.getText().toString();
        product_desc = edt_produnctdesc.getText().toString();
        extra_return_param = edt_extra_return_param.getText().toString();*/

        OrderInfo info = new OrderInfo();
        info.setMerchant_code(toPayBean.getMerchant());
        info.setNotify_url(toPayBean.getNotify_url());
        info.setInterface_version(toPayBean.getInterface_version());
        info.setOrder_no(toPayBean.getOrder_no());
        info.setOrder_time(toPayBean.getOrder_time());

        info.setProduct_name(toPayBean.getProduct_name());
        info.setRedo_flag(toPayBean.getRedo_flag());//订单是否允许重复标识  可选
        info.setProduct_code(toPayBean.getProduct_code());//商品编号   可选
        info.setProduct_num(toPayBean.getProduct_num());//商品数量   可选
        info.setProduct_desc(toPayBean.getProduct_desc());//商品描述   可选
        info.setExtra_return_param(toPayBean.getExtra_return_param());//公用回传参数    可选
        info.setOrder_amount(toPayBean.getOreder_amount());

        StringBuilder s = new StringBuilder();

            for (Map<String, String> map : list) {
                s.append("{");
                for (String key : map.keySet()) {
                    s.append("\"").append(key).append("\":\"").append(map.get(key))
                            .append("\"").append(",");
                }
                s.deleteCharAt(s.lastIndexOf(","));
                s.append("}");
                s.append(",");
            }
            s.deleteCharAt(s.lastIndexOf(","));
            String jsonS = "[" + s.toString() + "]";
            Log.e("json", "json= " + "[" + s.toString() + "]");
            String enToStr = Base64.encodeToString(jsonS.getBytes(), Base64.DEFAULT);
            info.setOrders_info(enToStr);

            //组织签名规则格式
            Map<String, String> maps = info.getMap();
            StringBuffer sb = new StringBuffer();
            for (Map.Entry<String, String> entry : maps.entrySet()) {
                String value = entry.getValue();
                if (!TextUtils.isEmpty(value)) {
                    sb.append(entry.getKey() + "=" + value + "&");
                }
            }
            //商户替换自己的key
            String sign = sb.toString()+"key="+toPayBean.getKey();
            try {
                sign = DigestUtils.md5Hex(sign.getBytes("UTF-8"));
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
            }

            //组织报文
            String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                    "<dinpay><request><merchant_code>" + info.getMerchant_code() + "</merchant_code>" +
                    "<notify_url>" + info.getNotify_url() + "</notify_url>" +
                    "<interface_version>" + info.getInterface_version() + "</interface_version>" +
                    "<sign_type>" + toPayBean.getSign_type() + "</sign_type>" +
                    "<sign>" + sign + "</sign>" +
                    "<trade><order_no>" + info.getOrder_no() + "</order_no>" +
                    "<order_time>" + info.getOrder_time() + "</order_time>" +
                    "<order_amount>" + info.getOrder_amount() + "</order_amount>" +
                    "<product_name>" + info.getProduct_name() + "</product_name>" +
                    "<redo_flag>" + info.getRedo_flag() + "</redo_flag>" +
                    "<product_code>" + info.getProduct_code() + "</product_code>" +
                    "<product_num>" + info.getProduct_num() + "</product_num>" +
                    "<product_desc>" + info.getProduct_desc() + "</product_desc>" +
                    "<extra_return_param>" + info.getExtra_return_param() + "</extra_return_param>" + "<orders_info>" + info.getOrders_info() + "</orders_info>" +
                    "</trade></request></dinpay>";
            Log.i("xml=", xml);
            Intent intent = new Intent(this, DinpayChannelActivity.class);
            intent.putExtra("xml", xml);
            intent.putExtra("ActivityName", "com.merchant.activity.MerchantPayResultActivity");
            startActivity(intent);

    }
}
