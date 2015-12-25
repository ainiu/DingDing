package com.baidu.dingding.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.RadioButton;

import com.baidu.dingding.MainActivity;
import com.baidu.dingding.R;
import com.baidu.dingding.adapter.MyFragmentPagerAdapter;
import com.baidu.dingding.fragment.MydingAll;
import com.baidu.dingding.fragment.Mydingdaifuhuo;
import com.baidu.dingding.fragment.Mydingdaifukuan;
import com.baidu.dingding.fragment.Mydingdaishouhuo;
import com.baidu.dingding.fragment.Mydingyiwangchegn;
import com.baidu.dingding.until.LogUtil;
import com.baidu.dingding.until.SharedPreferencesUtils;
import com.baidu.dingding.view.MyView.ScrollViewPager;

import java.util.ArrayList;
import java.util.List;

public class MyOrderActivity extends FragmentActivity {
    RadioButton btn_all, btn_daifukuan, btn_daifahuo, btn_daishouhuo, btn_wancheng;
    private ScrollViewPager viewPager;
    List<Fragment> geren_list = new ArrayList<Fragment>();
    private MyFragmentPagerAdapter myFragmentPagerAdapter;
    private int currIndex;             //选择当前页
    MydingAll mydingAll;
    Mydingdaifukuan mydingdaifukuan;
    Mydingdaifuhuo mydingdaifuhuo;
    Mydingdaishouhuo mydingdaishouhuo;
    Mydingyiwangchegn mydingyiwangchegn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        String token= (String) SharedPreferencesUtils.get(this,"token","");
        LogUtil.i("用户登录的Token", "token=" + token);
        if (TextUtils.isEmpty(token)) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            return;
        }
        initData();
        initFragment();
        initView();
        initLinener();
    }

    private void initFragment() {
        mydingAll = new MydingAll();
        mydingdaifukuan = new Mydingdaifukuan();
        mydingdaifuhuo = new Mydingdaifuhuo();
        mydingdaishouhuo = new Mydingdaishouhuo();
        mydingyiwangchegn = new Mydingyiwangchegn();
        geren_list.add(mydingAll);
       /* geren_list.add(mydingdaifukuan);
        geren_list.add(mydingdaifuhuo);
        geren_list.add(mydingdaishouhuo);
        geren_list.add(mydingyiwangchegn);*/
    }

    private void initLinener() {
        btn_all.setOnClickListener(new btnListener(0));
        btn_daifukuan.setOnClickListener(new btnListener(1));
        btn_daifahuo.setOnClickListener(new btnListener(2));
        btn_daishouhuo.setOnClickListener(new btnListener(3));
        btn_wancheng.setOnClickListener(new btnListener(4));
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

    private void initData() {
        String Select_id = this.getIntent().getStringExtra("position");
       // Toasttool.MyToast(this, Select_id);
        currIndex = Integer.parseInt(Select_id);
    }

    private void initView() {
        btn_all = (RadioButton) this.findViewById(R.id.myorder_button_01);
        btn_daifukuan = (RadioButton) this.findViewById(R.id.myorder_button_02);
        btn_daifahuo = (RadioButton) this.findViewById(R.id.myorder_button_03);
        btn_daishouhuo = (RadioButton) this.findViewById(R.id.myorder_button_04);
        btn_wancheng = (RadioButton) this.findViewById(R.id.myorder_button_05);
        switch (currIndex){
            case 0:
                btn_all.setChecked(true);
                break;
            case 1:
                btn_daifukuan.setChecked(true);
                break;
            case 2:
                btn_daifahuo.setChecked(true);
                break;
            case 3:
                btn_daishouhuo.setChecked(true);
                break;
            case 4:
                btn_wancheng.setChecked(true);
                break;
        }
        viewPager = (ScrollViewPager) this.findViewById(R.id.pager);
        viewPager.setNoScroll(false);
        myFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), geren_list);
        viewPager.setAdapter(myFragmentPagerAdapter);
        viewPager.setCurrentItem(currIndex);                                                                    //设置当前显示标签页为第一页

    }


    public void doClick(View v) {
        this.finish();
    }


}
