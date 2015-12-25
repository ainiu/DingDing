package com.baidu.dingding.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.baidu.dingding.MainActivity;
import com.baidu.dingding.R;
import com.baidu.dingding.TApplication;
import com.baidu.dingding.adapter.AbstractBaseAdapter;
import com.baidu.dingding.adapter.MyFragmentPagerAdapter;
import com.baidu.dingding.biz.BitmapCache;
import com.baidu.dingding.code.DResponseListener;
import com.baidu.dingding.code.DVolleyConstans;
import com.baidu.dingding.code.ReturnBean;
import com.baidu.dingding.code.model.AddDingdanModel;
import com.baidu.dingding.code.model.HaiwaiXQModel;
import com.baidu.dingding.code.model.HwshoucangModel;
import com.baidu.dingding.entity.HWXQEntity;
import com.baidu.dingding.entity.TPic;
import com.baidu.dingding.until.LogUtil;
import com.baidu.dingding.until.SharedPreferencesUtils;
import com.baidu.dingding.until.Toasttool;
import com.baidu.dingding.view.MyView.AddAndSubView;
import com.baidu.dingding.view.MyView.MyListView;
import com.baidu.dingding.view.MyView.NoScrollViewPager;
import com.baidu.dingding.view.ProgrressDialog.ProgressDialog;
import com.baidu.dingding.view.banner.BannerImageView;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2015/11/6. 商品详情 购物详情
 */
public class ShopingXQ extends FragmentActivity implements DResponseListener, View.OnClickListener {
    private HaiwaiXQModel haiwaiXQModel;
    private BannerImageView bannerImageView;
    private TextView tv_naem, tv_jiaqian, tv_yunfei, daohuoTime, tv_suozaidi, tv_cyd;
    private RadioButton btn_xq, btn_tj, btn_lyb;
    private NoScrollViewPager viewPager;
    private List<Fragment> haiwaifragemnt_list = new ArrayList<Fragment>();
    private MyFragmentPagerAdapter myFragmentPagerAdapter;
    private int currIndex;                                                                           //viewpager当前页卡编号
    private Button btn_dianpu, btn_shouchang, btn_lijiamai, btn_gouwuche;
    private ProgressDialog progressDialog;
    private HwshoucangModel hwshoucangModel;
    private AddDingdanModel addDingdanModel;
    private ScrollView scrollView;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hw_shoping_xq);
        initData();
        initModel();
        initView();
        initLener();
    }

    String goodsId, token="", userNumber="";

    private void initData() {
        goodsId = getIntent().getStringExtra("goodsId");
        token = (String) SharedPreferencesUtils.get(this, "token", "");
        userNumber = (String) SharedPreferencesUtils.get(this, "userNumber", "");
    }

    private void initLener() {
        btn_xq.setOnClickListener(new btnListener(0));
        btn_tj.setOnClickListener(new btnListener(1));
        btn_lyb.setOnClickListener(new btnListener(2));
        btn_dianpu.setOnClickListener(this);
        btn_shouchang.setOnClickListener(this);
        btn_lijiamai.setOnClickListener(this);
        btn_gouwuche.setOnClickListener(this);
    }


    private void initView() {
        scrollView= (ScrollView) this.findViewById(R.id.haiwaixiangqing_scrollView);
        scrollView.smoothScrollTo(0,0);
        progressDialog = new ProgressDialog(this);
        btn_dianpu = (Button) this.findViewById(R.id.sou_Button_haiwai);
        btn_lijiamai = (Button) this.findViewById(R.id.shoping_lijimai);
        btn_gouwuche = (Button) this.findViewById(R.id.sou_Button_gouwuche);
        btn_shouchang = (Button) this.findViewById(R.id.sou_Button_fenlei);

        bannerImageView = (BannerImageView) this.findViewById(R.id.sou_banner);
        tv_naem = (TextView) this.findViewById(R.id.haiwai_tv_name);
        tv_jiaqian = (TextView) this.findViewById(R.id.haiwai_money);
        tv_yunfei = (TextView) this.findViewById(R.id.haiwai_yunfei_moenty);
        daohuoTime = (TextView) this.findViewById(R.id.haiwai_daohuoshijian);
        tv_suozaidi = (TextView) this.findViewById(R.id.haiwai_suozaidi);
        tv_cyd = (TextView) this.findViewById(R.id.haiwai_cyd);
        btn_xq = (RadioButton) this.findViewById(R.id.radio_btn_xq);
        btn_tj = (RadioButton) this.findViewById(R.id.radio_btn_tj);
        btn_lyb = (RadioButton) this.findViewById(R.id.radio_btn_lyb);
        viewPager = (NoScrollViewPager) this.findViewById(R.id.pager);
        viewPager.setNoScroll(true);                                                                              //设置不能滑动
        myFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), haiwaifragemnt_list);
        viewPager.setAdapter(myFragmentPagerAdapter);
        viewPager.setCurrentItem(currIndex);                                                                    //设置当前显示标签页为第一�?
        viewPager.addOnPageChangeListener(new MyOnPageChangeListener());

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.sou_Button_haiwai:                                                            //店铺
                isTokennull();
                Intent intent = new Intent(ShopingXQ.this, MainFragent.class);
                intent.putExtra("position", 1);
                startActivity(intent);
                break;
            case R.id.shoping_lijimai:                                                              //立即�?
                isTokennull();
                Intent intent1 = new Intent(ShopingXQ.this, MainFragent.class);
                intent1.putExtra("position", 3);
                startActivity(intent1);
                break;
            case R.id.sou_Button_gouwuche:                                                          //购物�?
                try {
                    showDialog();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.sou_Button_fenlei:                                                            //收藏
                isTokennull();
                hwshoucangModel.findConsultList(goodsId, token, userNumber);
                progressDialog.show();
                break;

        }
    }

    int goodsCount=1;
    //点击购物车弹出详情界�?
    private void showDialog() {
        final Dialog dialog = new AlertDialog.Builder(ShopingXQ.this).create();
        dialog.show();
        Window window = dialog.getWindow();
        // 设置布局
        window.setContentView(R.layout.alertdialog);
        // 设置宽高
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        // 设置弹出的动画效�?
        window.setWindowAnimations(R.style.AnimBottom);
        // 设置监听
        final AddAndSubView addAndSubView = (AddAndSubView) window.findViewById(R.id.addandsubview);
        addAndSubView.setButtonLayoutParm(40, 40);
        addAndSubView.setButtonBgResource(R.drawable.gouwujia, R.drawable.gouwujian);
        addAndSubView.setEditTextResource(R.drawable.gouwu_zhong);
        addAndSubView.setNum(1);
        Button ok = (Button) window.findViewById(R.id.haiwai_gouwu_btn);
        ImageView cancel = (ImageView) window.findViewById(R.id.haiwai_gouwu_cancle);
        //购物车图�?
        NetworkImageView networkImageView = (NetworkImageView) window.findViewById(R.id.haiwai_gouwu_imageView);
        ImageLoader imageLoader = new ImageLoader(TApplication.getInstance().getRequestQueue(), new BitmapCache());
        networkImageView.setImageUrl(url, imageLoader);

        TextView tv_pic = (TextView) window.findViewById(R.id.haiwai_gouwu_money);
        tv_pic.setText("¥" + hwxqEntity.getGoodsInfo().getTgjg());
        //库存
        TextView tv_kucun = (TextView) window.findViewById(R.id.haiwai_gouwu_kuncun);
        tv_kucun.setText("库存" + hwxqEntity.getGoodsInfo().getStoreCount());
        //商品属�?添加
        MyListView myListView = (MyListView) window.findViewById(R.id.mylistview);
        ShopingAddAdapter shopingAddAdapter = new ShopingAddAdapter(dialog.getContext(), mapList);
        myListView.setAdapter(shopingAddAdapter);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub提交订单
                isTokennull();
                StringBuffer stringBuffer=new StringBuffer();
                String sort = hwxqEntity.getSort();
                String sortStirng=new String(sort);
                String[] a = sortStirng.split("_");
                for(int i=0;i<a.length;i++){
                    stringBuffer.append(hashMap.get(a[i]) + "_");
                }

                /*Set<Map.Entry<String, String>> set = hashMap.entrySet();
                for (Map.Entry<String, String> en : set) {
                     String title = en.getKey();
                    String content = en.getValue();
                    stringBuffer.append(title+"("+content+")"+"_");
                }*/

                String string="";
                if(!TextUtils.isEmpty(stringBuffer.toString())) {
                     string = (stringBuffer.toString()).substring(0, (stringBuffer.toString()).length() - 1);
                }else{
                    string="";
                }
                LogUtil.i("��Ʒ�����ύ","string="+string);
              //  Toasttool.MyToast(ShopingXQ.this, (stringBuffer.toString()).substring(0,(stringBuffer.toString()).length()-1));
                int inex=addAndSubView.getNum();
                try {
                    addDingdanModel.findConsultList(userNumber,token,hwxqEntity.getGoodsInfo().getGoodsId(),goodsName,hwxqEntity.getGoodsInfo().getTgjg(),inex,freight, URLEncoder.encode(string,"UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                progressDialog.show();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                dialog.cancel();
            }
        });
    }

    private void isTokennull() {
        if(TextUtils.isEmpty(token)){
            Intent intent=new Intent(ShopingXQ.this,MainActivity.class);
            startActivity(intent);
        }
    }

    HashMap<String,String> hashMap=new HashMap<String,String>();
    public class ShopingAddAdapter extends AbstractBaseAdapter {

        public ShopingAddAdapter(Context context, List beanList) {
            super(context, beanList);
        }
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            HashMap<String, Object> map = (HashMap<String, Object>) beanList.get(position);
            convertView = mInflater.inflate(R.layout.dialog_list_item, null);

            TextView name = (TextView) convertView.findViewById(R.id.addtitle);
            final TagFlowLayout tagFlowLayout=(TagFlowLayout) convertView.findViewById(R.id.TagFlowLayout);
            tagFlowLayout.setMaxSelectCount(1);                                                         // 设置选中数量

            Set<Map.Entry<String, Object>> set = map.entrySet();
            for (Map.Entry<String, Object> en : set) {
                final String title = en.getKey();
                name.setText(title);
                final List valueList = (List) en.getValue();

                tagFlowLayout.setAdapter(new TagAdapter(valueList){
                    @Override
                    public View getView(FlowLayout parent, final int position, Object o) {
                        LayoutInflater inflater = LayoutInflater.from(ShopingXQ.this);
                        TextView tv = (TextView) inflater.inflate(R.layout.tv,
                                tagFlowLayout, false);
                        tv.setText(o.toString());
                        return tv;
                    }

                });

                //当点击某个Tag回调
                tagFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                    @Override
                    public boolean onTagClick(View view, final int position, FlowLayout parent) {
                        if(!valueList.isEmpty()) {
                            hashMap.put(title, (String) valueList.get(position));
                        }
                        return true;
                    }
                });
            }
            return convertView;
        }
    }

    /**
     * viewpager监听
     */
    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }

        @Override
        public void onPageSelected(int arg0) {
            currIndex = arg0;
        }

    }

    /**
     * 监听btn
     */
    class btnListener implements View.OnClickListener {
        private int index = 0;

        public btnListener(int i) {
            index = i;
        }

        @Override
        public void onClick(View v) {
            viewPager.setCurrentItem(index);
        }
    }

    private void initModel() {
        haiwaiXQModel = new HaiwaiXQModel(this);
        haiwaiXQModel.addResponseListener(this);
        haiwaiXQModel.findConsultList(goodsId);
        //收藏
        hwshoucangModel = new HwshoucangModel(this);
        hwshoucangModel.addResponseListener(this);
        //添加订单
        addDingdanModel=new AddDingdanModel(this);
        addDingdanModel.addResponseListener(this);
    }

    private void islogindialog(String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(msg);
        builder.setTitle("提示");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(ShopingXQ.this, MainActivity.class);
                startActivity(intent);
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    public void doClick(View view) {
        this.finish();
    }

    String web_logpath, shopingId, url, mygoods,freight,goodsName;
    List<HWXQEntity> list = new ArrayList<HWXQEntity>();
    HWXQEntity hwxqEntity = new HWXQEntity();
    List<HashMap<String, Object>> mapList = new ArrayList<HashMap<String, Object>>();

    @Override
    public void onMessageResponse(ReturnBean bean, int result, String message, Throwable error) {
        LogUtil.i("海外详情回调", "bean=" + bean.getObject() + "result=" + result + "mesage=" + message);
        progressDialog.dismiss();
        //错误处理
        if (error != null) {
            Toast.makeText(this, error.getMessage(), Toast.LENGTH_SHORT).show();
            return;
        }
        if (result == DVolleyConstans.RESULT_FAIL) {
            //Toasttool.MyToast(this,message);
            islogindialog(message);
            return;
        }
        //成功处理
        if (result == DVolleyConstans.RESULT_OK) {
            if (bean.getType()==DVolleyConstans.ADDGOODSCAR){
                String string=bean.getString();
                Toasttool.MyToast(this, string);

            }
            if (bean.getType() == DVolleyConstans.HAIWAISHOUCANG) {
                Toasttool.MyToast(this, "收藏成功");
            }
            if (bean.getType() == DVolleyConstans.METHOD_MODIFY) {

            }
            if (bean.getType() == DVolleyConstans.METHOD_HAIWAIXQ) {
                //查询�?��
                mapList = bean.getMapList();
                LogUtil.i("商品属�?添加map", "map=" + mapList.size() + mapList.toArray());

                hwxqEntity = (HWXQEntity) bean.getObject();
                List<TPic> tpiclist = new ArrayList<TPic>();
                int count = hwxqEntity.getGoodsInfo().getPictures().size();
                for (int i = 0; i < count; i++) {
                    TPic tpic = new TPic();
                    tpic.setImageUrl(hwxqEntity.getGoodsInfo().getPictures().get(i).toString());
                    tpiclist.add(tpic);
                }
                url = hwxqEntity.getGoodsInfo().getPictures().get(0).toString();
                goodsName=hwxqEntity.getGoodsInfo().getName();
                tv_naem.setText(hwxqEntity.getGoodsInfo().getName());
                tv_jiaqian.setText(hwxqEntity.getGoodsInfo().getTgjg());
                tv_yunfei.setText(hwxqEntity.getGoodsInfo().getFreight());
                daohuoTime.setText(hwxqEntity.getGoodsInfo().getArrivalTime());
                tv_suozaidi.setText(hwxqEntity.getGoodsInfo().getCountry());
                tv_cyd.setText(hwxqEntity.getGoodsInfo().getOriginPlace());
                web_logpath = hwxqEntity.getGoodsInfo().getDescription();
                shopingId = hwxqEntity.getGoodsInfo().getShopId();
                mygoods = hwxqEntity.getGoodsInfo().getGoodsId();
                freight=hwxqEntity.getGoodsInfo().getFreight();
                // 刷新界面
                bannerImageView.setAutoPay(true);
                bannerImageView.createPicView(tpiclist);
                //海外详情的详情界�?

                HWshopingxiangqing hWshopingxiangqing = new HWshopingxiangqing();
                Bundle bundle1 = new Bundle();
                bundle1.putString("description", web_logpath);
                hWshopingxiangqing.setArguments(bundle1);

                HWdianputuijian hWdianputuijian = new HWdianputuijian();
                Bundle bundle2 = new Bundle();
                bundle2.putString("shopId", shopingId);
                hWdianputuijian.setArguments(bundle2);
                LogUtil.i("给店铺推荐传商品id", "shopingId=" + shopingId);

                HWliuyanban hWliuyanban = new HWliuyanban();
                Bundle bundle3 = new Bundle();
                bundle3.putString("goodsId", mygoods);
                hWliuyanban.setArguments(bundle3);
                LogUtil.i("给店铺推荐传商品id", "goodsId=" + mygoods);
                haiwaifragemnt_list.add(hWshopingxiangqing);
                haiwaifragemnt_list.add(hWdianputuijian);
                haiwaifragemnt_list.add(hWliuyanban);
                LogUtil.i("准备更新", "海外WEB");
                myFragmentPagerAdapter.notifyDataSetChanged();

            }
        }
    }


}
