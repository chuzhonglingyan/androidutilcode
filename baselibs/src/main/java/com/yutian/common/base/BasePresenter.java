package com.yutian.common.base;

import android.content.Context;

import com.yutian.common.net.baserx.RxManager;
import com.yutian.common.widget.LoadingDataDialog;


/**
 * 基类BasePresenter
 * @author guangleilei
 * @version 1.0 2016-11-08
 *
 */
public abstract class BasePresenter<T,E>{
    public Context mContext;
    public E mModel;
    public T mView;
    public RxManager mRxManage = new RxManager();
    public BasePresenter() {
        attchMode();
    }

    protected LoadingDataDialog loadingDataDialog;

    public void setV(T v,LoadingDataDialog loadingDataDialog) {
        this.mView = v;
        this.loadingDataDialog=loadingDataDialog;
        this.onStart();
    }


    abstract public void attchMode();


    public void onStart(){
    };

    public void onDestroy() {
        mRxManage.clear();
    }
}
