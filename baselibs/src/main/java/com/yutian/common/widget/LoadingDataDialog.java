package com.yutian.common.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.yutian.common.R;


/**
 * @explain 加载对话框
 *
 * @author guangleilei
 *
 * @time 2017/2/27 14:32.
 */

public class LoadingDataDialog {

    private Dialog progressDialog;
    private AnimationDrawable animationDrawable;
    private Context context;

    public LoadingDataDialog(Context context) {
        this.context = context;
        progressDialog = new Dialog(context,R.style.baselib_loading_dialog);
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = LayoutInflater.from(context).inflate(
                R.layout.baselib_dialog_loading_view, null);
        ImageView imageview = (ImageView) view.findViewById(R.id.iv_loading_view);
        imageview.setImageResource(R.drawable.baselib_animation_loading);
        animationDrawable = (AnimationDrawable) imageview.getDrawable();
        progressDialog.setContentView(view);
        progressDialog.setCancelable(true);
    }

    public void startProgressDialog() {
        if (progressDialog != null && !progressDialog.isShowing()) {
            animationDrawable.start();
            progressDialog.show();
        }
    }


    public void stopProgressDialog() {
        if (progressDialog != null) {
            animationDrawable.stop();
            progressDialog.dismiss();
        }
    }


    public void destoryProgressDialog() {
        if (progressDialog != null) {
            animationDrawable.stop();
            progressDialog.dismiss();
            progressDialog = null;
        }
    }


    public void setCancelable(boolean able) {
        progressDialog.setCancelable(able);
    }

}
