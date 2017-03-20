package com.yutian.common.net.baserx;

import android.content.Context;

import com.yutian.common.R;
import com.yutian.common.net.baserx.rxbus.RxBus;
import com.yutian.common.net.baserx.rxbus.RxBusEvent;
import com.yutian.common.util.LogUtil;
import com.yutian.common.util.NetworkUtils;
import com.yutian.common.widget.LoadingDataDialog;

import rx.Subscriber;

/**
 * @author guangleilei
 * @explain 订阅封装
 * @time 2017/2/27 14:53.
 */

public abstract class RxSubscriber<T> extends Subscriber<T> {

    private static final String TAG = RxSubscriber.class.getSimpleName();
    private Context mContext;
    private String msg;
    private boolean showDialog = true;
    private LoadingDataDialog loadingDataDialog;

    /**
     * 是否显示浮动dialog
     */
    public void showDialog() {
        this.showDialog = true;
    }

    public void hideDialog() {
        this.showDialog = true;
    }

    public RxSubscriber(Context context, String msg,LoadingDataDialog loadingDataDialog, boolean showDialog) {
        this.loadingDataDialog=loadingDataDialog;
        this.mContext = context;
        this.msg = msg;
        this.showDialog = showDialog;
    }

    public RxSubscriber(Context context) {
        this(context,context.getString(R.string.baselib_loading),null, true);
    }

    public RxSubscriber(Context context, LoadingDataDialog loadingDataDialog,boolean showDialog) {
        this(context, context.getString(R.string.baselib_loading), loadingDataDialog,showDialog);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (showDialog&&loadingDataDialog!=null) {
            try {
                loadingDataDialog.startProgressDialog();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        LogUtil.d(TAG, "Thread==" + Thread.currentThread());
    }

    @Override
    public void onCompleted() {
        if (showDialog&&loadingDataDialog!=null) {
            loadingDataDialog.stopProgressDialog();
        }
        LogUtil.d(TAG, "Thread==" + Thread.currentThread());
    }


    @Override
    public void onNext(T t) {
        if (showDialog&&loadingDataDialog!=null) {
            loadingDataDialog.stopProgressDialog();
        }
        _onNext(t);
        LogUtil.d(TAG, "Thread==" + Thread.currentThread());
    }

    @Override
    public void onError(Throwable e) {
        if (showDialog&&loadingDataDialog!=null) {
            loadingDataDialog.stopProgressDialog();
        }

        //网络
        if (!NetworkUtils.isConnected()) {
            _onError(mContext.getString(R.string.baselib_no_net), 1000);
        } else {
            ErrorMsg errorMsg = new ErrorMsg();
            errorMsg.handleException(e);

            if (errorMsg.getCode()==401){
                RxBus.get().post(new RxBusEvent("401",errorMsg.getError()));
            }
            _onError(errorMsg.getError(), errorMsg.getCode());
            LogUtil.e(TAG, "msg=" + errorMsg.getError() + ",code" + errorMsg.getCode());
        }
        LogUtil.d(TAG, "Thread==" + Thread.currentThread());
    }



    protected abstract void _onNext(T t);

    protected abstract void _onError(String message, int code);

}
