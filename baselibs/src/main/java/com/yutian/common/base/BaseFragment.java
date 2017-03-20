package com.yutian.common.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yutian.common.net.baserx.RxManager;
import com.yutian.common.net.baserx.rxbus.RxBus;
import com.yutian.common.util.LogUtil;
import com.yutian.common.util.TUtil;
import com.yutian.common.widget.LoadingDataDialog;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 基类Fragment
 * @author guangleilei
 * @version 1.0 2016-11-08

/***************
 * 使用例子
 *********************/
//1.mvp模式
//public class SampleFragment extends BaseFragment<NewsChanelPresenter, NewsChannelModel>implements NewsChannelContract.View {
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
//public class SampleFragment extends BaseFragment {
//    @Override
//    public int getLayoutResource() {
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
public abstract class BaseFragment<T extends BasePresenter> extends Fragment {

    private  static  final  String TAG=BaseFragment.class.getSimpleName();
    protected View rootView;
    protected T mPresenter;
    protected Context mContext;
    protected Activity mActivity;
    protected RxManager mRxManager;
    private Unbinder mBinder;
    protected LoadingDataDialog loadingDataDialog;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        LogUtil.d(TAG,this.getClass().getSimpleName()+"--onAttach");
        mContext = context;
        mActivity = (Activity) context;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.d(TAG,this.getClass().getSimpleName()+"--onCreate");
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LogUtil.d(TAG,this.getClass().getSimpleName()+"--onCreateView");
        if (rootView == null)
            rootView = inflater.inflate(getLayoutResource(), container, false);
        init();
        initPresenter();
        initView();
        return rootView;
    }

    private void init() {
        mRxManager = new RxManager();
        mBinder = ButterKnife.bind(this, rootView);
        mPresenter = TUtil.getT(this, 0);
        if (mPresenter != null) {
            mPresenter.mContext = mContext;
        }
        loadingDataDialog=new LoadingDataDialog(mContext);
        RxBus.get().register(this);
    }


    //获取布局文件
    protected abstract int getLayoutResource();

    //简单页面无需mvp就不用管此方法即可,完美兼容各种实际场景的变通
    public abstract void initPresenter();

    //初始化view
    protected abstract void initView();


    @Override
    public void onStart() {
        super.onStart();
        LogUtil.d(TAG,this.getClass().getSimpleName()+"--onStart");
    }


    @Override
    public void onResume() {
        super.onResume();
        LogUtil.d(TAG,this.getClass().getSimpleName()+"--onResume");
    }


    @Override
    public void onPause() {
        super.onPause();
        LogUtil.d(TAG,this.getClass().getSimpleName()+"--onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        LogUtil.d(TAG,this.getClass().getSimpleName()+"--onStop");
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LogUtil.d(TAG,this.getClass().getSimpleName()+"--onDestroyView");
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.d(TAG,this.getClass().getSimpleName()+"--onDestroy");
        clear();
        RxBus.get().unregister(this);
    }

    private void clear() {
        if (mPresenter != null) {
            mPresenter.onDestroy();
        }
        mRxManager.clear();
        mBinder.unbind();
        loadingDataDialog.destoryProgressDialog();
    }
}
