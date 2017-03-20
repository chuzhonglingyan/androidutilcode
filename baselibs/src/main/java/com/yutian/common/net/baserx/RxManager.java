package com.yutian.common.net.baserx;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * 用于管理单个presenter的的事件
 * Created by xsf
 * on 2016.08.14:50
 */
public class RxManager {


    /*管理Observables 和 Subscribers订阅*/
    private CompositeSubscription mCompositeSubscription = new CompositeSubscription();

    /**
     * 单纯的Observables 和 Subscribers管理
     *
     * @param m
     */
    public void add(Subscription m) {
        /*订阅管理*/
        mCompositeSubscription.add(m);
    }

    /**
     * 单个presenter生命周期结束，取消订阅和所有rxbus观察
     */
    public void clear() {
        mCompositeSubscription.unsubscribe();// 取消所有订阅
    }

}
