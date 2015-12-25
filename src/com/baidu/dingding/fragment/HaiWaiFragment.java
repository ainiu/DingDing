package com.baidu.dingding.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.baidu.dingding.R;
import com.baidu.dingding.adapter.HwlistAdapter;
import com.baidu.dingding.code.DResponseListener;
import com.baidu.dingding.code.DVolleyConstans;
import com.baidu.dingding.code.ReturnBean;
import com.baidu.dingding.code.model.HaiwaiModel;
import com.baidu.dingding.code.model.HaiwaidizhiModel;
import com.baidu.dingding.code.model.HwListModel;
import com.baidu.dingding.entity.AdDomain;
import com.baidu.dingding.entity.HaiWai;
import com.baidu.dingding.entity.HwlistBean;
import com.baidu.dingding.entity.TPic;
import com.baidu.dingding.until.LogUtil;
import com.baidu.dingding.view.MyView.MyListView;
import com.baidu.dingding.view.SousuoActivity;
import com.baidu.dingding.view.banner.BannerImageView;
import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;
import java.util.List;

public class HaiWaiFragment extends Fragment implements DResponseListener {
    private View view;
    private BannerImageView bannerImageView;
    private HaiwaiModel haiwaiModel;
    private HaiwaidizhiModel haiwaidizhiModel;
    private HwListModel hwListModel;
    LinearLayout linearLayout;
    ViewPager mViewPager;
    TabPageIndicator mTabPageIndicator;
    TabAdapter tabAdapter;
    List<HaiWai> haiWaiList = new ArrayList<HaiWai>();
    private MyListView listView;
    HwlistAdapter hwlistAdapter;
    List<HwlistBean> hwlistBeanList = new ArrayList<HwlistBean>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //首页轮播
        haiwaiModel = new HaiwaiModel(getActivity());
        haiwaiModel.addResponseListener(this);
        //原产地址
        haiwaidizhiModel = new HaiwaidizhiModel(getActivity());
        haiwaidizhiModel.addResponseListener(this);
        //list
        hwListModel = new HwListModel(getActivity());
        hwListModel.addResponseListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.haiwai_fragment, container, false);
        initView();
        initLinener();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        haiwaiModel.findConsultList();
        haiwaidizhiModel.findConsultList();
        hwListModel.findConsultList();
    }

    private void initLinener() {
        //搜索
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SousuoActivity.class);
                startActivity(intent);
            }
        });

        //list设置点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LogUtil.i("海外点击事件", "position=" + position + "id=" + id);
                Intent intent = new Intent().setClass(getActivity(),ShopingXQ.class);
                HwlistBean hwlistBean= hwlistBeanList.get(position);
                //bu.putString("goodsId", hwlistBean.getGoodsId());
                intent.putExtra("goodsId", hwlistBean.getGoodsId());
                startActivity(intent);
            }
        });
    }

    private void initView() {
        linearLayout= (LinearLayout) view.findViewById(R.id.sou_Layout_01);
        bannerImageView = (BannerImageView) view.findViewById(R.id.sou_banner);

        //ViewPager的adapter
        tabAdapter = new TabAdapter(getFragmentManager());
        mViewPager = (ViewPager) view.findViewById(R.id.pager);
        mTabPageIndicator = (TabPageIndicator) view.findViewById(R.id.indicator);
        mViewPager.setAdapter(tabAdapter);

        //实例化TabPageIndicator然后设置ViewPager与之关联
        mTabPageIndicator.setViewPager(mViewPager);

        listView = (MyListView) view.findViewById(R.id.haiwai_fragment_listView_01);
        hwlistAdapter = new HwlistAdapter(getActivity(), hwlistBeanList);
        listView.setAdapter(hwlistAdapter);
    }


    /**
     * 适配器
     */
    class TabAdapter extends FragmentPagerAdapter {

        //将fragment初始化
        public TabAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return haiWaiList.size();
        }

        //获取标题名字
        @Override
        public CharSequence getPageTitle(int position) {
            LogUtil.i("titlename", "" + haiWaiList.get(position).getName());
            return haiWaiList.get(position).getName();
        }

        @Override
        public Fragment getItem(int position) {
            LogUtil.i("海外获取地址", "position=" + position);
            TabFragment fragment = new TabFragment();
            //将查询数据的参数传给fragment
            LogUtil.i("海外获取地址id", "" + haiWaiList.get(position).getYcdId());
            Bundle bundle = new Bundle();
            bundle.putString("ycdId", haiWaiList.get(position).getYcdId());
            fragment.setArguments(bundle);
            return fragment;
        }

    }


    @Override
    public void onMessageResponse(ReturnBean bean, int result, String message, Throwable error) {
        LogUtil.i("海外首页回调", "bean=" + bean.getObject() + "result=" + result + "mesage=" + message);

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
        /**要更新两个适配器*/
        if (result == DVolleyConstans.RESULT_OK) {
            if (bean.getType() == DVolleyConstans.METHOD_ADD) {
                //查询所有
                haiWaiList = (ArrayList<HaiWai>) bean.getObject();
                tabAdapter.notifyDataSetChanged();
                mTabPageIndicator.notifyDataSetChanged();
            }
        }

        /**要更新两个适配器*/
        if (result == DVolleyConstans.RESULT_OK) {
            if (bean.getType() == DVolleyConstans.METHOD_NEW) {
                //查询所有
                hwlistBeanList = (ArrayList<HwlistBean>) bean.getObject();
                hwlistAdapter.setBeanList(hwlistBeanList);
                hwlistAdapter.notifyDataSetChanged();
            }
        }

    }


}
