package com.fanc.mycar;

import android.app.Application;
import android.test.ApplicationTestCase;

import com.fanc.mycar.code.DVolley;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {

    public ApplicationTest() {
        super(Application.class);
        DVolley.init(getContext());
    }
}