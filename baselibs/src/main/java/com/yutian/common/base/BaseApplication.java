package com.yutian.common.base;

import android.app.Application;
import android.content.res.Configuration;

import com.yutian.common.util.AppContext;
import com.yutian.common.util.LogUtil;


/**
 * 基类Application
 * @author guangleilei
 * @version 1.0 2016-11-08
 */
public class BaseApplication extends Application {

    private static String TAG = BaseApplication.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.d(TAG, "onCreate");
        AppContext.getApplicationContext();
    }



    @Override
    public void onTerminate() {
        // 程序终止的时候执行
        LogUtil.d(TAG, "onTerminate");
        super.onTerminate();
    }

    @Override
    public void onLowMemory() {
        // 低内存的时候执行
        LogUtil.d(TAG, "onLowMemory");
        super.onLowMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        // 程序在内存清理的时候执行
        LogUtil.d(TAG, "onTrimMemory");
        super.onTrimMemory(level);
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        LogUtil.d(TAG, "onConfigurationChanged");
        super.onConfigurationChanged(newConfig);
    }


}
