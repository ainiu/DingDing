package com.baidu.dingding.fragment;
/**
 * alt+f8 debug时选中查看值
 * f8相当于eclipse的f6跳到下一步
 * shift+f8相当于eclipse的f8跳到下一个断点，也相当于eclipse的f7跳出函数
 * f7相当于eclipse的f5就是进入到代码
 * alt+shift+f7这个是强制进入代码
 * ctrl+shift+f9 debug运行java类
 * ctrl+shift+f10正常运行java类
 * command+f2停止运行
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.baidu.dingding.R;
import com.baidu.dingding.until.ExceptionUtil;
import com.baidu.dingding.until.LogUtil;

public class MainFragent extends FragmentActivity {
    HaiWaiFragment haiWaiFragment;
    GeRenFragment geRenFragment;
    GouWuFragment gouWuFragment;
    ShouYeFragment shouYeFragment;
    FenLeifragment fenLeifragment;

    // Fragment数组
    private Fragment[] fragmentArry = null;
    // button数组
    private Button[] btnArray = new Button[5];

    // 当前显示的fragment
    int currentIndex = 2;
    /**
     * 选中的button,显示下一个fragment
     */
    int selectedIndex;
    int position;

    @Override
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onCreate(arg0);
        try {
            position = getIntent().getIntExtra("position", 2);
            currentIndex = position;
            setContentView(R.layout.main_fragment);
            setupView();
            addListener();

        } catch (Exception e) {
            ExceptionUtil.handleException(e);
        }
    }


    private void setupView() {
        // TODO Auto-generated method stub
        btnArray[0] = (Button) this.findViewById(R.id.sou_Button_haiwai);
        btnArray[1] = (Button) this.findViewById(R.id.sou_Button_fenlei);
        btnArray[2] = (Button) this.findViewById(R.id.sou_Button_souye);
        btnArray[3] = (Button) this.findViewById(R.id.sou_Button_gouwuche);
        btnArray[4] = (Button) this.findViewById(R.id.sou_Button_geren);
        // 默认选中
        btnArray[currentIndex].setSelected(true);
        // 初始化
        haiWaiFragment = new HaiWaiFragment();
        fenLeifragment = new FenLeifragment();
        shouYeFragment = new ShouYeFragment();
        gouWuFragment = new GouWuFragment();
        geRenFragment = new GeRenFragment();

        fragmentArry = new Fragment[]{haiWaiFragment, fenLeifragment,
                shouYeFragment, gouWuFragment, geRenFragment};
        LogUtil.i("FragmentActivity", "fragmentArry=" + fragmentArry.length);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
//        transaction.replace(R.id.fragment_container, shouYeFragment);
            if (currentIndex == 2) {
                transaction.add(R.id.fragment_container, shouYeFragment);
                transaction.show(shouYeFragment);
            } else if (currentIndex == 1) {
                transaction.add(R.id.fragment_container, fenLeifragment);
                transaction.show(fenLeifragment);
            } else if (currentIndex == 3) {
                transaction.add(R.id.fragment_container, gouWuFragment);
                transaction.show(gouWuFragment);
            } else if (currentIndex == 0) {
                transaction.add(R.id.fragment_container, haiWaiFragment);
                transaction.show(haiWaiFragment);
            } else if (currentIndex == 4) {
                transaction.add(R.id.fragment_container, geRenFragment);
                transaction.show(geRenFragment);
            }
            transaction.commit();



    }

    private void addListener() {
        // TODO Auto-generated method stub
        MyButtonListener myButtonListener = new MyButtonListener();
        for (int i = 0; i < btnArray.length; i++) {
            btnArray[i].setOnClickListener(myButtonListener);
        }
    }

    // 判断你点了那个按钮
    class MyButtonListener implements OnClickListener {

        @Override
        public void onClick(View v) {
            try {
                switch (v.getId()) {
                    case R.id.sou_Button_haiwai:
                        selectedIndex = 0;
                        break;
                    case R.id.sou_Button_fenlei:
                        selectedIndex = 1;
                        break;
                    case R.id.sou_Button_souye:
                        selectedIndex = 2;
                        break;
                    case R.id.sou_Button_gouwuche:
                        selectedIndex = 3;
                        break;
                    case R.id.sou_Button_geren:
                        selectedIndex = 4;
                        break;
                }

                // 判断单击是不是当前的
                if (selectedIndex != currentIndex) {
                    // 不是当前的
                    FragmentTransaction transaction = getSupportFragmentManager()
                            .beginTransaction();
                    // 当前hide
                    transaction.hide(fragmentArry[currentIndex]);
                    // show你选中
                    if (!fragmentArry[selectedIndex].isAdded()) {
                        // 以前没添加过
                        transaction.add(R.id.fragment_container,
                                fragmentArry[selectedIndex]);
                    }
                    // 事务
                    transaction.show(fragmentArry[selectedIndex]);
                    transaction.commit();

                    btnArray[currentIndex].setSelected(false);
                    btnArray[selectedIndex].setSelected(true);
                    currentIndex = selectedIndex;

                }
            } catch (Exception e) {
                ExceptionUtil.handleException(e);
            }
        }

    }
}
