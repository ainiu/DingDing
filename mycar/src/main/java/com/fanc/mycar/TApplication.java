package com.fanc.mycar;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.fanc.mycar.code.DVolley;

/**
 * Created by Administrator on 2015/11/9.
 */
public class TApplication extends Application{
    public static boolean isRelease = false;
    @Override
    public void onCreate() {
        super.onCreate();
        DVolley.init(this);
    }

    private RequestQueue mRequestQueue;
    private static TApplication sInstance;
    public synchronized static TApplication getInstance() {
        if (null == sInstance) {
            sInstance = new TApplication();
        }
        return sInstance;
    }

    public RequestQueue getRequestQueue() {
        // lazy initialize the request queue, the queue instance will be
        // created when it is accessed for the first time
        if (mRequestQueue == null) {
            // 1
            // 2
            synchronized (TApplication.class) {
                if (mRequestQueue == null) {
                    mRequestQueue = Volley
                            .newRequestQueue(getApplicationContext());
                }
            }
        }
        return mRequestQueue;
    }
}
