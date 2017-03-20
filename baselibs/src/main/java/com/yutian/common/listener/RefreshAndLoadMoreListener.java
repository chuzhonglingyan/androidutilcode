package com.yutian.common.listener;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yutian.common.net.baserx.RxManager;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by Administrator on 2017/2/8.
 */

public abstract  class RefreshAndLoadMoreListener extends RefreshListenerAdapter{

    private RxManager rxManager;
    private static  final  int STOPTIME=1;

    public RefreshAndLoadMoreListener(RxManager rxManager) {
        this.rxManager=rxManager;
    }

    @Override
    public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
        super.onRefresh(refreshLayout);
        rxManager.add( Observable.timer(STOPTIME, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                refreshLayout.finishRefreshing();
            }
        }));
    }

    @Override
    public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
        super.onLoadMore(refreshLayout);
        rxManager.add(Observable.timer(STOPTIME, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                refreshLayout.finishLoadmore();
            }
        }));

    }
}
