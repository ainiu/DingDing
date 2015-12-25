package com.baidu.dingding.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.baidu.dingding.R;
import com.baidu.dingding.adapter.adapterutil.ShopingPagerAdapter;
import com.baidu.dingding.view.MyView.CustomViewPager;

import java.util.ArrayList;
import java.util.List;

public class GouWuFragment extends Fragment {

    private View view;
    private Button button;
    private CustomViewPager viewPager;
    private int currIndex = 0;//当前页卡编号

    private List<Fragment> fragmentList = new ArrayList<Fragment>();
    private ShopingPagerAdapter shopingPagerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = View.inflate(getActivity(), R.layout.shopping_fragment, null);
        initView();
       initLener();
        return view;
    }

   private  Boolean index=false;
    private void initLener() {

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                index=!index;
                if(index) {
                    viewPager.setCurrentItem(0);
                    button.setText("编辑");
                }else{
                    viewPager.setCurrentItem(1);
                    button.setText("完成");
                }
            }
        });
    }


    private void initView() {
        fragmentList.add(new ShopingBianji());
        fragmentList.add(new Shopingwangcheng());
        button = (Button) view.findViewById(R.id.btn);
        viewPager = (CustomViewPager) view.findViewById(R.id.viewpager);
        viewPager.setScanScroll(true);
        //给ViewPager设置适配器
        shopingPagerAdapter = new ShopingPagerAdapter(getChildFragmentManager(), fragmentList);
        //viewPager.setAdapter(new ShopingPagerAdapter(getChildFragmentManager(), fragmentList));
        viewPager.setAdapter(shopingPagerAdapter);
        viewPager.setCurrentItem(currIndex);                             //设置当前显示标签页为第一页

    }



}
