package com.baidu.dingding.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.baidu.dingding.R;
import com.baidu.dingding.adapter.NewAdapter;
import com.baidu.dingding.adapter.TejiaAdapter;
import com.baidu.dingding.code.DResponseListener;
import com.baidu.dingding.code.DVolleyConstans;
import com.baidu.dingding.code.ReturnBean;
import com.baidu.dingding.code.model.TejiaModel;
import com.baidu.dingding.code.model.UserModel;
import com.baidu.dingding.code.model.XinpinModel;
import com.baidu.dingding.entity.AdDomain;
import com.baidu.dingding.entity.NewGoods;
import com.baidu.dingding.entity.TPic;
import com.baidu.dingding.entity.XianShiEntity;
import com.baidu.dingding.until.Consts;
import com.baidu.dingding.until.ExceptionUtil;
import com.baidu.dingding.until.LogUtil;
import com.baidu.dingding.view.GongGaoActivity;
import com.baidu.dingding.view.MyView.MyGridView;
import com.baidu.dingding.view.ShopinghelpActivity;
import com.baidu.dingding.view.SousuoActivity;
import com.baidu.dingding.view.XianShiActivity;
import com.baidu.dingding.view.banner.BannerImageView;

import java.util.ArrayList;
import java.util.List;


public class ShouYeFragment extends Fragment implements DResponseListener {

    private UserModel userModel;
    private TejiaModel tejiaModel;
    private XinpinModel xinpinModel;
    private View view;
    private BannerImageView bannerImageView;
    private ImageView gonggao_img, shoping_img, xinashi_img;
    private LinearLayout linearLayout;
    private MyGridView gridView_xinping, gridView_tejia;
    private ScrollView scrollView;
    NewAdapter newAdapter;
    List<NewGoods> newGoodsList = new ArrayList<NewGoods>();

    TejiaAdapter tejiaAdapter;
    List<XianShiEntity> xianShiEntityList = new ArrayList<XianShiEntity>();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化
        userModel = new UserModel(getActivity());
        userModel.addResponseListener(this);
        //特价
        tejiaModel = new TejiaModel(getActivity());
        tejiaModel.addResponseListener(this);
        //新品
        xinpinModel = new XinpinModel(getActivity());
        xinpinModel.addResponseListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = View.inflate(getActivity(), R.layout.activity_sou, null);
        initView();
        initListener();
        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
        userModel.findConsultList();
        tejiaModel.findConsultList();
        xinpinModel.findConsultList();
    }

    @Override
    public void onMessageResponse(ReturnBean bean, int result, String message, Throwable error) {

        LogUtil.i("界面回调", "bean=" + bean.getObject() + "result=" + result + "mesage=" + message);

        //错误处理
        if (error != null) {
            Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
            return;
        }
        //成功处理
        if (result == DVolleyConstans.RESULT_OK) {
            if (bean.getType() == DVolleyConstans.METHOD_QUERYALL) {
                //查询所有
                List<AdDomain> list = (ArrayList<AdDomain>) bean.getObject();
                List<TPic> tpiclist = new ArrayList<TPic>();
                for (int i = 0; i < list.size(); i++) {
                    TPic tpic = new TPic();
                    tpic.setImageUrl(list.get(i).getPicLogpath());
                    LogUtil.i("图片地址", "list.get(i).getPicLogpath()=" + list.get(i).getPicLogpath());
                    tpiclist.add(tpic);
                }
                // 刷新界面
                // BannerImageView bannerImageView = (BannerImageView) view.findViewById(R.id.sou_banner);
                bannerImageView.setAutoPay(true);
                bannerImageView.createPicView(tpiclist);
            }
        }

        //成功处理
        if (result == DVolleyConstans.RESULT_OK) {
            if (bean.getType() == DVolleyConstans.METHOD_NEW) {
                //查询新品
                try {
                    newGoodsList = (List<NewGoods>) bean.getObject();
                    newAdapter.setBeanList(newGoodsList);
                    newAdapter.notifyDataSetChanged();
                } catch (Exception e) {
                    LogUtil.i("适配器更新出错", "");
                    ExceptionUtil.handleException(e);
                }

            }
        }
        //成功处理特价
        if (result == DVolleyConstans.RESULT_OK) {
            if (bean.getType() == DVolleyConstans.METHOD_TEJIA) {
                //查询特价
                try {
                    xianShiEntityList = (List<XianShiEntity>) bean.getObject();
                    tejiaAdapter.setBeanList(xianShiEntityList);
                    tejiaAdapter.notifyDataSetChanged();
                } catch (Exception e) {
                    LogUtil.i("适配器更新出错", "");
                    ExceptionUtil.handleException(e);
                }

            }
        }
    }


    private void initListener() {
        //搜索
        linearLayout.setOnClickListener(new LinearLayout.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(getActivity(), SousuoActivity.class);
                getActivity().startActivity(intent);
            }
        });
        //限时抢购
        xinashi_img.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), XianShiActivity.class);
                getActivity().startActivity(intent);
            }
        });
        //公告
        gonggao_img.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), GongGaoActivity.class);
                intent.putExtra("gonggaoURL", Consts.GONGGAO_WANGYE);
                getActivity().startActivity(intent);
            }
        });
        //购物帮助
        shoping_img.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),
                        ShopinghelpActivity.class);
                getActivity().startActivity(intent);
            }
        });
        //特价
        gridView_tejia.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                XianShiEntity xianshi = xianShiEntityList.get(position);
                Intent intent = new Intent(getActivity(), ShopingXQ.class);
                intent.putExtra("goodsId", xianshi.getGoodsId());
                startActivity(intent);
            }
        });
        //新品
        gridView_xinping.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NewGoods newGoods = newGoodsList.get(position);
                Intent intent = new Intent(getActivity(), ShopingXQ.class);
                intent.putExtra("goodsId", newGoods.getGoodsId());
                startActivity(intent);
            }
        });
    }


    public void initView() {
        //特价
        gridView_tejia = (MyGridView) view.findViewById(R.id.sou_tejia_grid);
        tejiaAdapter = new TejiaAdapter(getActivity(), xianShiEntityList);
        gridView_tejia.setAdapter(tejiaAdapter);
        //新品
        gridView_xinping = (MyGridView) view.findViewById(R.id.sou_xinpin_grid);
        newAdapter = new NewAdapter(getActivity(), newGoodsList);
        gridView_xinping.setAdapter(newAdapter);
        // 搜索
        linearLayout = (LinearLayout) view.findViewById(R.id.sou_Layout_01);
        // 限时
        xinashi_img = (ImageView) view.findViewById(R.id.sou_image_04);
        // 公告
        gonggao_img = (ImageView) view.findViewById(R.id.sou_image_03);
        // 购物帮助
        shoping_img = (ImageView) view.findViewById(R.id.sou_image_05);
        bannerImageView = (BannerImageView) view.findViewById(R.id.sou_banner);
        //scrllview
        scrollView = (ScrollView) view.findViewById(R.id.shouye_myscrollview);
        scrollView.smoothScrollTo(0, 0);
    }

}





