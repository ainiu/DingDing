package com.baidu.dingding.fragment;
/**
 * alt+f8 debugʱѡ�в鿴ֵ
 * f8�൱��eclipse��f6������һ��
 * shift+f8�൱��eclipse��f8������һ���ϵ㣬Ҳ�൱��eclipse��f7��������
 * f7�൱��eclipse��f5���ǽ��뵽����
 * alt+shift+f7�����ǿ�ƽ������
 * ctrl+shift+f9 debug����java��
 * ctrl+shift+f10��������java��
 * command+f2ֹͣ����
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

    // Fragment����
    private Fragment[] fragmentArry = null;
    // button����
    private Button[] btnArray = new Button[5];

    // ��ǰ��ʾ��fragment
    int currentIndex = 2;
    /**
     * ѡ�е�button,��ʾ��һ��fragment
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
        // Ĭ��ѡ��
        btnArray[currentIndex].setSelected(true);
        // ��ʼ��
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

    // �ж�������Ǹ���ť
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

                // �жϵ����ǲ��ǵ�ǰ��
                if (selectedIndex != currentIndex) {
                    // ���ǵ�ǰ��
                    FragmentTransaction transaction = getSupportFragmentManager()
                            .beginTransaction();
                    // ��ǰhide
                    transaction.hide(fragmentArry[currentIndex]);
                    // show��ѡ��
                    if (!fragmentArry[selectedIndex].isAdded()) {
                        // ��ǰû��ӹ�
                        transaction.add(R.id.fragment_container,
                                fragmentArry[selectedIndex]);
                    }
                    // ����
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
