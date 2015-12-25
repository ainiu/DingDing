package com.baidu.dingding.view;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.baidu.dingding.R;
import com.baidu.dingding.activity.BaseActivity;
import com.baidu.dingding.adapter.QuerenAdapter;
import com.baidu.dingding.code.DResponseListener;
import com.baidu.dingding.code.DVolleyConstans;
import com.baidu.dingding.code.ReturnBean;
import com.baidu.dingding.code.model.AddressListModel;
import com.baidu.dingding.code.model.CreateDingdanModel;
import com.baidu.dingding.entity.AddressEntity;
import com.baidu.dingding.entity.Shoping.GouwubeanToQueren;
import com.baidu.dingding.entity.Shoping.OrderNoBean;
import com.baidu.dingding.until.AddressHelp;
import com.baidu.dingding.until.LogUtil;
import com.baidu.dingding.until.SharedPreferencesUtils;
import com.baidu.dingding.until.Toasttool;
import com.baidu.dingding.view.MyView.MyListView;
import com.baidu.dingding.view.MyView.dialog.MyProgressDialog;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class QueRenActivity extends BaseActivity implements DResponseListener {
    private Dialog dialog;
    private AddressListModel addressListModel;
    private ArrayList<AddressEntity> addressEntityList = new ArrayList<AddressEntity>();
    private TextView shouhuoren, shouhuodizhi, lianxidianhua, youbian, genghuandizhi;
    private String userNumber = "";
    private String token = "";
    private MyListView myListView;
    private QuerenAdapter querenAdapter;
    private Button xiadan;
    private TextView pricesum;
    private EditText liuyan;
    private CreateDingdanModel createDingdanModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_que_ren);
        initData();
        initModel();
        initView();
        initLener();
    }

    private void initLener() {
        //下单并支付
        xiadan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String remark = liuyan.getText().toString().trim();
                /*try {
                    createDingdanModel.findConsultList(userNumber,token,goodsIdStringBuffer.toString().substring(0, goodsIdStringBuffer.length() - 1),countStringBuffer.toString().substring(0, countStringBuffer.length() - 1),shoppingattribute.toString().substring(0,shoppingattribute.length() - 1),consigneeName,mobile,receiveddress,postCode,province,city,area,remark);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }*/
                try {
                    createDingdanModel.findConsultList(userNumber, token,
                            URLEncoder.encode(goodsIdStringBuffer.toString().substring(0,goodsIdStringBuffer.length()- 1),"utf-8"),
                            URLEncoder.encode(countStringBuffer.toString().substring(0,countStringBuffer.length()- 1),"utf-8"),
                            URLEncoder.encode(shoppingattribute.toString().substring(0,shoppingattribute.length()- 1),"utf-8"),
                            URLEncoder.encode(consigneeName,"utf-8"),
                            mobile,
                            URLEncoder.encode(receiveddress,"utf-8"),
                            postCode,
                            URLEncoder.encode(province,"utf-8"),
                            URLEncoder.encode(city,"utf-8"),
                            URLEncoder.encode(area, "utf-8"),
                            URLEncoder.encode(remark,"utf-8"));
                    dialog.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        //更换地址
        genghuandizhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QueRenActivity.this, ShouHuoAdressActivity.class);
                intent.putExtra("receiveId", receiveId);
                startActivity(intent);
            }
        });

    }
    String receiveId;
    private void initModel() {
        //地址
        addressListModel = new AddressListModel(this);
        addressListModel.addResponseListener(this);
        //生成订单
        createDingdanModel = new CreateDingdanModel(this);
        createDingdanModel.addResponseListener(this);
    }

    public void doClick(View view) {
        finish();
    }

    private String count, privce;
    private double sum = 0.00;
    private StringBuffer goodsIdStringBuffer=new StringBuffer();
    private StringBuffer countStringBuffer = new StringBuffer();
    private ArrayList<GouwubeanToQueren> gouwucheBeanList = new ArrayList<GouwubeanToQueren>();
    private StringBuffer  shoppingattribute=new StringBuffer();
    private void initData() {
        gouwucheBeanList.clear();
        gouwucheBeanList = getIntent().getParcelableArrayListExtra("gouwuche");
        LogUtil.i("carid数据", "gouwucheBeanList=" + gouwucheBeanList.size()+gouwucheBeanList.toString());

        for (int i = 0; i < gouwucheBeanList.size(); i++) {
            count = gouwucheBeanList.get(i).getGoodsCount();
            privce = gouwucheBeanList.get(i).getPrice();
            sum += Double.parseDouble(count) * Double.parseDouble(privce);
            goodsIdStringBuffer.append(gouwucheBeanList.get(i).getGoodsId() + ",");
            countStringBuffer.append(gouwucheBeanList.get(i).getGoodsCount() + ",");

            String str = gouwucheBeanList.get(i).getGoodsSpecificationContactStr();

                if (TextUtils.isEmpty(str)) {
                    shoppingattribute.append("无" + ",");
                } else {
                    Map<String,String> map=new HashMap<String,String>();
                     StringBuilder stringBuilder = new StringBuilder();
                    String sort=gouwucheBeanList.get(i).getSort();
                    String[] b = sort.split("_");
                    String[] c=str.split(",");
                    for (int j = 0; j < c.length; j++) {
                        String key=c[j].substring(0,c[j].indexOf(':'));
                        String value= c[j].substring(c[j].indexOf(':') + 1);
                        map.put(key, value);
                       // stringBuilder.append(a[j].substring(a[j].indexOf(':') + 1, a[j].length()) + '_');
                    }
                    for(int k=0;k<b.length;k++){
                        String string=b[k];
                        stringBuilder.append(map.get(string)+"_");
                    }
                    shoppingattribute.append(stringBuilder.substring(0,stringBuilder.length()-1)+",");
                }

        }
        userNumber = (String) SharedPreferencesUtils.get(this, "userNumber", "");
        token = (String) SharedPreferencesUtils.get(this, "token", "");
        LogUtil.i("属性","shoppingattribute="+shoppingattribute.toString());
    }



    private void initView() {
        liuyan= (EditText) this.findViewById(R.id.liuyanban_editText);
        pricesum = (TextView) this.findViewById(R.id.que_ren_list_text_32);
        pricesum.setText(this.getResources().getString(R.string.shoppingcart_renmingbin) + sum);
        xiadan = (Button) this.findViewById(R.id.xiadan);
        dialog = MyProgressDialog.createLoadingDialog(this, this.getResources().getString(R.string.dialog_jiazai));
        shouhuoren = (TextView) this.findViewById(R.id.shouhuoren);
        shouhuodizhi = (TextView) this.findViewById(R.id.shouhuodizhi);
        lianxidianhua = (TextView) this.findViewById(R.id.lianxidianhua);
        youbian = (TextView) this.findViewById(R.id.youbian);
        genghuandizhi = (TextView) this.findViewById(R.id.genghuandizhi);
        myListView = (MyListView) this.findViewById(R.id.listView);
        querenAdapter = new QuerenAdapter(this, gouwucheBeanList);
        myListView.setAdapter(querenAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        addressListModel.findConsultList(userNumber);
        dialog.show();
    }
    private String consigneeName,mobile,receiveddress,postCode,province,city,area;

    @Override
    public void onMessageResponse(ReturnBean bean, int result, String message, Throwable error) {

        dialog.dismiss();
        LogUtil.i("确认请求回调", "bean=" + bean.getObject() + "result=" + result + "mesage=" + message);
        if (result == DVolleyConstans.RESULT_FAIL) {
            Toasttool.MyToastLong(this, message);
            return;
        }
        //错误处理
        if (error != null) {
            Toasttool.MyToast(this, error.getMessage());
            return;
        }
        if (result == DVolleyConstans.RESULT_OK) {
            if(bean.getType()==DVolleyConstans.CREATE_DINDAN){
                OrderNoBean orderNoBean = (OrderNoBean) bean.getObject();
                Toasttool.MyToast(this,orderNoBean.getMessage());
                Intent intent=new Intent(this,PaymentActivity.class);
                intent.putStringArrayListExtra("ordernolist", (ArrayList<String>) orderNoBean.getOrderNo());
                startActivity(intent);
                this.finish();
            }
            if (bean.getType() == DVolleyConstans.SHOUHUOADDRESS) {
                addressEntityList = (ArrayList<AddressEntity>) bean.getObject();
                for (int i = 0; i < addressEntityList.size(); i++) {
                    AddressEntity addressEntity = addressEntityList.get(i);
                    if (addressEntity.getIsDefault().equals("1")) {
                        shouhuoren.setText(this.getResources().getString(R.string.geren_shouhuoren) + ":" + addressEntity.getReceiveName());
                        String parseAddress = AddressHelp.getAddress(addressEntity.getRegionId());
                        province=parseAddress.substring(0,parseAddress.indexOf("_"));
                        LogUtil.i("province","province="+province);
                        city=parseAddress.substring(parseAddress.indexOf("_")+1,parseAddress.indexOf("市")+1);
                        LogUtil.i("city","city="+city);
                        area=parseAddress.substring(parseAddress.indexOf("市")+1);
                        LogUtil.i("area","area="+area);
                        shouhuodizhi.setText(this.getResources().getString(R.string.geren_shouhuodizhi) + ":" + province+city+area + addressEntity.getReceiveAddress());
                        lianxidianhua.setText(this.getResources().getString(R.string.geren_phone) + ":" + addressEntity.getReceiveMobile());
                        youbian.setText(this.getResources().getString(R.string.geren_youbian) + ":" + addressEntity.getReceivePost());
                        consigneeName=addressEntity.getReceiveName();
                        mobile=addressEntity.getReceiveMobile();
                        receiveddress=addressEntity.getReceiveAddress();
                        postCode=addressEntity.getReceivePost();
                        receiveId=addressEntity.getReceiveId();
                    }
                }
            }
        }
    }


}
