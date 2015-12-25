package com.baidu.dingding.view.ProgrressDialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import com.baidu.dingding.R;

/**
 * Created by Administrator on 2015/11/19. 加载页面
 */
public class ProgressDialog extends Dialog{
    private Context context;

    public ProgressDialog(Context context) {
        super(context,R.style.dialog_style_comment);
        this.context = context;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.alert_progress_view);
        this.setCanceledOnTouchOutside(false);//设置点击Dialog外部任意区域关闭Dialog
        this.setCancelable(false);//设置进度条是否可以按退回键取消
    }
}
