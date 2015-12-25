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
        //��ҳ�ֲ�
        haiwaiModel = new HaiwaiModel(getActivity());
        haiwaiModel.addResponseListener(this);
        //ԭ����ַ
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
        //����
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SousuoActivity.class);
                startActivity(intent);
            }
        });

        //list���õ���¼�
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LogUtil.i("�������¼�", "position=" + position + "id=" + id);
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

        //ViewPager��adapter
        tabAdapter = new TabAdapter(getFragmentManager());
        mViewPager = (ViewPager) view.findViewById(R.id.pager);
        mTabPageIndicator = (TabPageIndicator) view.findViewById(R.id.indicator);
        mViewPager.setAdapter(tabAdapter);

        //ʵ����TabPageIndicatorȻ������ViewPager��֮����
        mTabPageIndicator.setViewPager(mViewPager);

        listView = (MyListView) view.findViewById(R.id.haiwai_fragment_listView_01);
        hwlistAdapter = new HwlistAdapter(getActivity(), hwlistBeanList);
        listView.setAdapter(hwlistAdapter);
    }


    /**
     * ������
     */
    class TabAdapter extends FragmentPagerAdapter {

        //��fragment��ʼ��
        public TabAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return haiWaiList.size();
        }

        //��ȡ��������
        @Override
        public CharSequence getPageTitle(int position) {
            LogUtil.i("titlename", "" + haiWaiList.get(position).getName());
            return haiWaiList.get(position).getName();
        }

        @Override
        public Fragment getItem(int position) {
            LogUtil.i("�����ȡ��ַ", "position=" + position);
            TabFragment fragment = new TabFragment();
            //����ѯ���ݵĲ�������fragment
            LogUtil.i("�����ȡ��ַid", "" + haiWaiList.get(position).getYcdId());
            Bundle bundle = new Bundle();
            bundle.putString("ycdId", haiWaiList.get(position).getYcdId());
            fragment.setArguments(bundle);
            return fragment;
        }

    }


    @Override
    public void onMessageResponse(ReturnBean bean, int result, String message, Throwable error) {
        LogUtil.i("������ҳ�ص�", "bean=" + bean.getObject() + "result=" + result + "mesage=" + message);

        //������
        if (error != null) {
            Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
            return;
        }
        //�ɹ�����
        if (result == DVolleyConstans.RESULT_OK) {
            if (bean.getType() == DVolleyConstans.METHOD_QUERYALL) {
                //��ѯ����
                List<AdDomain> list = (ArrayList<AdDomain>) bean.getObject();
                List<TPic> tpiclist = new ArrayList<TPic>();
                for (int i = 0; i < list.size(); i++) {
                    TPic tpic = new TPic();
                    tpic.setImageUrl(list.get(i).getPicLogpath());
                    LogUtil.i("ͼƬ��ַ", "list.get(i).getPicLogpath()=" + list.get(i).getPicLogpath());
                    tpiclist.add(tpic);
                }
                // ˢ�½���
                // BannerImageView bannerImageView = (BannerImageView) view.findViewById(R.id.sou_banner);
                bannerImageView.setAutoPay(true);
                bannerImageView.createPicView(tpiclist);
            }
        }
        /**Ҫ��������������*/
        if (result == DVolleyConstans.RESULT_OK) {
            if (bean.getType() == DVolleyConstans.METHOD_ADD) {
                //��ѯ����
                haiWaiList = (ArrayList<HaiWai>) bean.getObject();
                tabAdapter.notifyDataSetChanged();
                mTabPageIndicator.notifyDataSetChanged();
            }
        }

        /**Ҫ��������������*/
        if (result == DVolleyConstans.RESULT_OK) {
            if (bean.getType() == DVolleyConstans.METHOD_NEW) {
                //��ѯ����
                hwlistBeanList = (ArrayList<HwlistBean>) bean.getObject();
                hwlistAdapter.setBeanList(hwlistBeanList);
                hwlistAdapter.notifyDataSetChanged();
            }
        }

    }


}
