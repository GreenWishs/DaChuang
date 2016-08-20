package com.jlu.mzx.tiaoji;

import android.app.Application;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.jlu.mzx.tiaoji.tools.VolleySingleton;

import io.rong.imkit.RongIM;

/**
 * Created by mzx on 2016/7/26.
 */
public class MyApplication extends Application {

    public static RequestQueue mRequestQueue = null;
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        RongIM.init(this);
        context = this;
    }

    public static RequestQueue getmRequestQueue() {
        if (mRequestQueue != null) {
            return mRequestQueue;
        } else {
            return mRequestQueue = VolleySingleton.getVolleySingleton(context).getRequestQueue();
        }
    }
}
