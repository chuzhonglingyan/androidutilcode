package com.yutian.common.base;

import android.app.Activity;

import com.yutian.common.util.LogUtil;

import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.Stack;

/**
 * Activtiy管理类
 * @author guangleilei
 * @version 1.0 2016-11-08
 */
public class AppManager {

    private static final  String MAINACTIVITY_NAME="MainActivity";

    private static class Holder {
        private static final AppManager INSTANCE = new AppManager();
    }

    private AppManager() {
    }

    public static AppManager getInstance() {

        return Holder.INSTANCE;
    }

    /***寄存整个应用Activity**/
    private  Stack<WeakReference<Activity>> activitys = new Stack<WeakReference<Activity>>();

    /**
     * 将Activity压入Application栈
     * @param task 将要压入栈的Activity对象
     */
    public  void pushActivityTask(WeakReference<Activity> task) {
        activitys.push(task);
        LogUtil.e("ActivityTask","pushActivityTask==="+task.get().getClass());
    }

    /**
     * 将传入的Activity对象从栈中移除
     * @param task
     */
    public  void removeActivityTask(WeakReference<Activity> task) {
        activitys.remove(task);
        LogUtil.e("ActivityTask","removeActivityTask==="+task.get().getClass());
    }

    /**
     * 根据指定位置从栈中移除Activity
     * @param taskIndex Activity栈索引
     */
    public  void removeTask(int taskIndex) {
        if (activitys.size() > taskIndex)
            activitys.remove(taskIndex);
    }

    /**
     * 将栈中Activity移除至栈顶
     */
    public  void removeToTop() {
        int end = activitys.size();
        int start = 1;
        for (int i = end - 1; i >= start; i--) {
            if (!activitys.get(i).get().isFinishing()) {
                activitys.get(i).get().finish();
            }
        }
    }

    /**
     * 移除全部（用于整个应用退出）
     */
    public  void closeAllActivity() {
        for (WeakReference<Activity> task : activitys) {
            Activity activity=task.get();
            if (activity!=null){
                activity.finish();
            }
        }
        activitys.clear();
    }


    /**
     * 移除Actvity除了main
     */
    public  void closeActivityElseMain() {
        Iterator<WeakReference<Activity>> it = activitys.iterator();
        while(it.hasNext()) {
            WeakReference<Activity> task = it.next();
            Activity activity=task.get();
            if (activity.getClass().getSimpleName().equals(MAINACTIVITY_NAME)){
                continue;
            }
            if (activity!=null){
                activity.finish();
            }
            it.remove();
        }
    }

    /**
     * 关闭窗体
     *
     * @param className 需要关闭窗体名称
     */
    public void closedActivity(String className) {
        Iterator<WeakReference<Activity>> it = activitys.iterator();
        while(it.hasNext()) {
            WeakReference<Activity> task = it.next();
            Activity activity=task.get();
            if (activity!=null){
                if (activity.getClass().getSimpleName().equals(className)) {
                    activity.finish();
                    it.remove();
                }
            }
        }
    }

}
