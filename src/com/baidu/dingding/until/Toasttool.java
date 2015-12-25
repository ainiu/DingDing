package com.baidu.dingding.until;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.os.Handler;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

/**
 * Created by Administrator on 2015/11/11. toast封装
 */
public class Toasttool {

    public static void MyToast(Context context ,String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

    }
    public static void MyToastLong(Context context ,String message){
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
    public static void popupInputMethodWindow(final Context context) { // 异步弹出键盘，关闭软键盘，开启方法相同，这个方法是切换开启与关闭状态的
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager imm = (InputMethodManager) context
                        .getSystemService(Service.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }, 0);
    }
    public static void popupoutMethodWindow(final Activity ac) { // 强制收回键盘
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager imm = (InputMethodManager) ac
                        .getSystemService(Service.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(ac.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }, 0);
    }
}
