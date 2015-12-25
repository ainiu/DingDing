package com.baidu.dingding.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import com.baidu.dingding.code.model.AddressMinuteModel;
import com.baidu.dingding.code.model.AddressSaveModel;
import com.baidu.dingding.code.model.AdressdeleteModel;
import com.baidu.dingding.entity.AddressBean.City;
import com.baidu.dingding.entity.AddressBean.Country;
import com.baidu.dingding.entity.AddressBean.Provinces;
import com.baidu.dingding.entity.AddressEntity;
import com.baidu.dingding.entity.CityJsonEntity;
import com.baidu.dingding.entity.ProvinceBean;
import com.baidu.dingding.until.AddressHelp;
import com.baidu.dingding.until.LogUtil;
import com.baidu.dingding.until.SharedPreferencesUtils;
import com.baidu.dingding.until.Toasttool;
import com.baidu.dingding.view.MyView.dialog.MyProgressDialog;
import com.bigkoo.pickerview.OptionsPickerView;
import com.sevenheaven.iosswitch.ShSwitchView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

public class ShouHuoAdressActivity extends BaseActivity implements DResponseListener {
    private Button address_xiangxi_save;
    private EditText name, title_phone, phone, post, xiangxidizhi;
    private TextView suozaidiqu;
    private OptionsPickerView pvOptions;                               //三级联动控件
    private RelativeLayout relativeLayout, relativeLayout_delete;
    private ShSwitchView mTogBtn;
    private AddressMinuteModel addressMinuteModel;
    private AdressdeleteModel adressdeleteModel;
    private AddressSaveModel addressSaveModel;
    private Dialog prodialog;
    //ProgressDialog progressDialog;
    private ArrayList<ProvinceBean> options1Items = new ArrayList<ProvinceBean>();                                    //省
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<ArrayList<String>>();                          //市
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<ArrayList<ArrayList<String>>>();    // 区
    //private WebView webView;
    Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    suozaidiqu.setText((CharSequence) msg.obj);
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shou_huo_adress);
        initData();
        initModel();
        initView();
        initLener();
    }

    private void initModel() {
        //查看收货地址详细
        addressMinuteModel = new AddressMinuteModel(this);
        addressMinuteModel.addResponseListener(this);
        //删除收货地址
        adressdeleteModel = new AdressdeleteModel(this);
        adressdeleteModel.addResponseListener(this);
        //保存地址
        addressSaveModel=new AddressSaveModel(this);
        addressSaveModel.addResponseListener(this);
    }

    String shengshiqu,xiugaiquyuid,istrue;
    private void initLener() {
        address_xiangxi_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String shouhuorenname = name.getText().toString().trim();
                String shouhuorendianhua=title_phone.getText().toString().trim();
                String shouhuorenmoble=phone.getText().toString().trim();
                String shouhuorenyoubian=post.getText().toString().trim();
                String receiveAddress=xiangxidizhi.getText().toString().trim();
                String ssq=suozaidiqu.getText().toString().trim();
                if(mTogBtn.isOn()){
                    istrue=1+"";
                }else{
                    istrue=0+"";
                }

                try {
                    addressSaveModel.findConsultList(token,userNumber, URLEncoder.encode(shouhuorenname,"utf-8"),shouhuorendianhua,shouhuorenmoble,shouhuorenyoubian,URLEncoder.encode(receiveAddress,"utf-8"),xiugaiquyuid,receiveId,URLEncoder.encode(shengshiqu,"utf-8"),istrue);
                    prodialog.show();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });

        relativeLayout_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog();
            }
        });

        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pvOptions.show();
            }
        });

        //监听确定选择按钮
        pvOptions.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
            LogUtil.i("选中位置","options1="+options1+"options1="+option2+"options1="+options3);

                xiugaiquyuid = getAddress(options1, option2, options3);
                //返回的分别是三个级别的选中位置
                String tx="";
                if(Istoandthree(options1,option2,options3)){
                            tx = options1Items.get(options1).getPickerViewText()+"_"
                                    + options2Items.get(options1).get(option2)
                                    + options3Items.get(options1).get(option2).get(options3);

                }else{
                        tx = options1Items.get(options1).getPickerViewText()+"_"
                                + options2Items.get(options1).get(option2);

                }
                String str1=tx.substring(0,tx.indexOf("_"));
                String str2=tx.substring(tx.indexOf("_")+1);
                shengshiqu=str1+str2;
                Message message=new Message();
                message.what=1;
                message.obj=shengshiqu;
                myHandler.sendMessage(message);
                LogUtil.i("修改后的id","xiugaiquyuid="+xiugaiquyuid);
            }
        });
    }
    public void doClick(View view){
        this.finish();
    }
    protected void dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Confirm to delete the address");
        builder.setTitle("prompter");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                adressdeleteModel.findConsultList(userNumber, token, addressEntity.getReceiveId());
                dialog.dismiss();
                prodialog.show();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    String receiveId, userNumber, token;

    private void initData() {
        receiveId = getIntent().getStringExtra("receiveId");
        LogUtil.i("收货地址列表传收货详细地址", "receiveId=" + receiveId);
        userNumber = (String) SharedPreferencesUtils.get(this, "userNumber", "");
        token = (String) SharedPreferencesUtils.get(this, "token", "");
    }

    @Override
    protected void onResume() {
        super.onResume();
        addressMinuteModel.findConsultList(receiveId);
        // progressDialog.show();
        prodialog.show();
    }
    /*Contact contact=new Contact();
    private final class Contact {
        //Html调用此方法传递数据
        public void showcontacts(String string) {
            // 调用JS中的方法
           webView.loadUrl("javascript:getAddress('" + string + "')");
        }
    }*/
    CityJsonEntity cityjsonEntity;
    private void initView() {

        /*webView= (WebView) this.findViewById(R.id.webview);
        //开启javascript设置
        webView.getSettings().setJavaScriptEnabled(true);
        //把RIAExample的一个实例添加到js的全局对象window中
        //这样就可以使用window.javatojs来调用它的方法
        webView.addJavascriptInterface(new Contact(), "javatojs");

        //加载网页
        webView.loadUrl("file:///android_asset/region.html");*/
        address_xiangxi_save= (Button) this.findViewById(R.id.shouhuo_adress_button_01);
        pvOptions = new OptionsPickerView(this);
        // progressDialog = new ProgressDialog(this);
        prodialog = MyProgressDialog.createLoadingDialog(this, "Loding...");
        name = (EditText) this.findViewById(R.id.xinzeng_edit_name);
        title_phone = (EditText) this.findViewById(R.id.xinzeng_edit_title);
        phone = (EditText) this.findViewById(R.id.xinzeng_edit_phone);
        post = (EditText) this.findViewById(R.id.xinzeng_edit_Post);
        suozaidiqu = (TextView) this.findViewById(R.id.xinzeng_textview_suozaiqu);
        xiangxidizhi = (EditText) this.findViewById(R.id.xinzeng_edit_xiangxidizhi);
        relativeLayout = (RelativeLayout) this.findViewById(R.id.relativeLayout);
        relativeLayout_delete = (RelativeLayout) this.findViewById(R.id.reala_delete);
        mTogBtn = (ShSwitchView) findViewById(R.id.switch_view);
        mTogBtn.setOn(false);
        cityjsonEntity = CityJsonParse.initConfige(this);

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


        //三级联动效果
        pvOptions.setPicker(options1Items, options2Items, options3Items, true);
        //设置选择的三级单位
        // pwOptions.setLabels("省", "市", "区");
        pvOptions.setTitle(this.getResources().getString(R.string.dizhixuanzhe));
        pvOptions.setCyclic(false, false, false);
        //设置默认选中的三级项目
        pvOptions.setSelectOptions(0, 0, 0);

    }

    AddressEntity addressEntity=new AddressEntity();

    public String  getAddress(int i,int  j,int k){
        Provinces provinces = cityjsonEntity.getProvinces().get(i);
        City city=provinces.getCities().get(j);
        if(city.getCountries().size()==0) return city.getCity_post();
        Country country=city.getCountries().get(k);
        return country.getCountry_post();
    }

    public Boolean  Istoandthree(int m,int  n,int l){
        Provinces provinces = cityjsonEntity.getProvinces().get(m);
        City city=provinces.getCities().get(n);
        if(city.getCountries().size()==0) return false;
        Country country=city.getCountries().get(l);
        return true;
    }

    @Override
    public void onMessageResponse(ReturnBean bean, int result, String message, Throwable error) {
        LogUtil.i("收货地址", "bean=" + bean.getObject() + "result=" + result + "mesage=" + message);
        prodialog.dismiss();
        // progressDialog.dismiss();
        if (result == DVolleyConstans.RESULT_FAIL) {
            Toasttool.MyToastLong(this, message);
            return;
        }
        if (result == DVolleyConstans.RESULT_OK) {

            if (bean.getType() == DVolleyConstans.METHOD_DELETE) {
                Toasttool.MyToastLong(this, bean.getString());
                this.finish();
            }
            if(bean.getType()==DVolleyConstans.SAVERECEIVEADDRESS){
                Toasttool.MyToast(this,bean.getString());
                this.finish();
            }
            if (bean.getType() == DVolleyConstans.SEARCHRECEIVEADDRESS) {
                addressEntity = (AddressEntity) bean.getObject();
                addressEntity.getRegionId();
                name.setText(addressEntity.getReceiveName());
                title_phone.setText(addressEntity.getReceiveTel());
                phone.setText(addressEntity.getReceiveMobile());
                post.setText(addressEntity.getReceivePost());
                //contact.showcontacts(addressEntity.getRegionId());
                xiangxidizhi.setText(addressEntity.getReceiveAddress());
                xiugaiquyuid=addressEntity.getRegionId();
                if(addressEntity.getIsDefault().equals("1")){
                    mTogBtn.setOn(true, true);
                }else{
                    mTogBtn.setOn(false);
                }
                LogUtil.i("区域id", "id=" + addressEntity.getRegionId());
                String quyustr=AddressHelp.getAddress(addressEntity.getRegionId());
                LogUtil.i("找到的省市区", "id=" + quyustr);
                try {
                    suozaidiqu.setText(quyustr);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }
    }


}
