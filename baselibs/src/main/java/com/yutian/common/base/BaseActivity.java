package com.yutian.common.base;


import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.yutian.common.R;
import com.yutian.common.net.baserx.RxManager;
import com.yutian.common.net.baserx.rxbus.RxBus;
import com.yutian.common.util.LogUtil;
import com.yutian.common.util.TUtil;
import com.yutian.common.widget.LoadingDataDialog;
import com.yutian.common.widget.StatusBarCompat;

import java.lang.ref.WeakReference;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 基类Activity
 * @author guangleilei
 * @version 1.0 2016-11-08

/***************
 * 使用例子
 *********************/
//1.mvp模式
//public class SampleActivity extends BaseActivity<NewsChanelPresenter, NewsChannelModel>implements NewsChannelContract.View {
//    @Override
//    public int getLayoutId() {
//        return R.layout.activity_news_channel;
//    }
//
//    @Override
//    public void initPresenter() {
//        mPresenter.setVM(this, mModel);
//    }
//
//    @Override
//    public void initView() {
//    }
//}
//2.普通模式
//public class SampleActivity extends BaseActivity {
//    @Override
//    public int getLayoutId() {
//        return R.layout.activity_news_channel;
//    }
//
//    @Override
//    public void initPresenter() {
//    }
//
//    @Override
//    public void initView() {
//    }
//}
public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity {

    private static final String TAG = BaseActivity.class.getSimpleName();
    private WeakReference<Activity> taskActivity = new WeakReference<Activity>(this);
    protected Context mContext;
    protected Activity mActivity;
    protected RxManager mRxManager;
    protected T mPresenter;
    private Unbinder mBinder;
    protected LoadingDataDialog loadingDataDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.d(TAG, this.getClass().getSimpleName() + "--onCreate");
        doBeforeSetcontentView();
        setContentView(getLayoutId());
        init();
        initPresenter();
        initView();
        setListener();
    }

    protected void init() {
        AppManager.getInstance().pushActivityTask(taskActivity);
        mContext = this;
        mActivity = this;
        mRxManager = new RxManager();
        mBinder = ButterKnife.bind(this);
        mPresenter = TUtil.getT(this, 0);
        if (mPresenter != null) {
            mPresenter.mContext = mContext;
        }
        loadingDataDialog=new LoadingDataDialog(mContext);
        RxBus.get().register(this);
    }

    /**
     * 设置layout前配置
     */
    private void doBeforeSetcontentView() {
        // 设置竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // 默认着色状态栏
        SetStatusBarColor(R.color.baselib_staus_color);
    }

    /**
     * 着色状态栏（4.4以上系统有效）
     */
    protected void SetStatusBarColor(@ColorRes int corlor) {
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, corlor));
    }


    /*********************
     * 子类实现
     *****************************/
    //获取布局文件
    public abstract int getLayoutId();

    //简单页面无需mvp就不用管此方法即可,完美兼容各种实际场景的变通
    public abstract void initPresenter();

    //初始化view
    public abstract void initView();

    //初始化Listener
    public abstract void setListener();


    @Override
    protected void onStart() {
        super.onStart();
        LogUtil.d(TAG, this.getClass().getSimpleName() + "--onStart");
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        LogUtil.d(TAG, this.getClass().getSimpleName() + "--onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtil.d(TAG, this.getClass().getSimpleName() + "--onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtil.d(TAG, this.getClass().getSimpleName() + "--onPause");
    }


    @Override
    protected void onStop() {
        super.onStop();
        LogUtil.d(TAG, this.getClass().getSimpleName() + "--onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtil.d(TAG, this.getClass().getSimpleName() + "--onDestroy");
        clear();
    }


    private void clear() {
        mRxManager.clear();
        AppManager.getInstance().removeActivityTask(taskActivity);
        mBinder.unbind();
        RxBus.get().unregister(this);
        loadingDataDialog.destoryProgressDialog();
    }




}