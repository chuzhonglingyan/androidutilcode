package com.yutian.common.adpater;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;

import com.yutian.common.util.DisplayUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * RecyclerView适配器基类
 */
public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements CommonHolder.OnNotifyChangeListener {
    private List<T> dataList = new ArrayList<>();
    private boolean enableHead = false;
    ViewHolderHelper headHolder;
    ViewGroup rootView;
    public final static int TYPE_HEAD = 0;
    public static final int TYPE_CONTENT = 1;
    protected Context mContext;
    private int layResId;
    private OnItemClickListener mOnItemClickListener;

    public BaseRecyclerAdapter(Context mContext,int layResId) {
        this.layResId = layResId;
        this.mContext = mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        rootView = parent;
        //设置ViewHolder
        int type = getItemViewType(position);
        if (type == TYPE_HEAD) {
            if (headHolder==null){
                return  ViewHolderHelper.get(mContext, null, null, 0, 0);
            }
            return headHolder;
        } else {
            ViewHolderHelper viewHolder = ViewHolderHelper.get(mContext, null, parent, layResId, -1);
            setListener(parent,viewHolder,position);
            return viewHolder;
        }
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//        runEnterAnimation(holder.itemView, position);
        //数据绑定
        if (enableHead) {
            if (position == 0) {
                bindHeadData(headHolder);
            } else {
                convert((ViewHolderHelper) holder,dataList.get(position - 1),position);
            }
        } else {
                convert((ViewHolderHelper) holder,dataList.get(position),position);
        }
    }

    protected void setListener(final ViewGroup parent, final ViewHolderHelper viewHolder,int position )
    {
        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int position = 0;
                if (enableHead) {
                    position = getPosition(viewHolder)-1;
                }else {
                    position = getPosition(viewHolder);
                }
                if (mOnItemClickListener != null&&position>=0)
                {
                    mOnItemClickListener.onItemClick(parent, v, dataList.get(position), position);
                }
            }
        });
    }

    protected int getPosition(RecyclerView.ViewHolder viewHolder)
    {
        return viewHolder.getAdapterPosition();
    }



    public void setmOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public ViewGroup getRootView() {
        return rootView;
    }
    @Override
    public int getItemCount() {
        if (enableHead) {
            return dataList.size() + 1;
        }
        return dataList.size();
    }
    @Override
    public int getItemViewType(int position) {
        if (enableHead) {
            if (position == 0) {
                return TYPE_HEAD;
            } else {
                return TYPE_CONTENT;
            }
        } else {
            return TYPE_CONTENT;
        }
    }

    protected abstract void convert(ViewHolderHelper helper, T item, int postion);


    private int lastAnimatedPosition = -1;
    protected boolean animationsLocked = false;
    private boolean delayEnterAnimation = true;
    private void runEnterAnimation(View view, int position) {
        if (animationsLocked) return;
        if (position > lastAnimatedPosition) {
            lastAnimatedPosition = position;
            view.setTranslationY(DisplayUtil.dip2px( 100));//(position+1)*50f
            view.setAlpha(0.f);
            view.animate()
                    .translationY(0).alpha(1.f)
                    .setStartDelay(delayEnterAnimation ? 20 * (position) : 0)
                    .setInterpolator(new DecelerateInterpolator(2.f))
                    .setDuration(500)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            animationsLocked = true;
                        }
                    }).start();
        }
    }

    public List<T> getDataList() {
        return dataList;
    }

    @Override
    public void onNotify() {
        //提供给CommonHolder方便刷新视图
        notifyDataSetChanged();
    }

    public void setDataList(List<T> datas) {
        dataList.clear();
        if (null != datas) {
            dataList.addAll(datas);
        }
        notifyDataSetChanged();
    }
    public void clearDatas() {
        dataList.clear();
        notifyDataSetChanged();
    }
    /**
     * 添加数据到前面
     */
    public void addItemsAtFront(List<T> datas) {
        if (null == datas) return;
        dataList.addAll(0, datas);
        notifyDataSetChanged();
    }
    /**
     * 添加数据到尾部
     */
    public void addItems(List<T> datas) {
        if (null == datas) return;
        dataList.addAll(datas);
        notifyDataSetChanged();
    }
    /**
     * 添加单条数据
     */
    public void addItem(T data) {
        if (null == data) return;
        dataList.add(data);
        notifyDataSetChanged();
    }
    /**
     * 删除单条数据
     */
    public void deletItem(T data) {
        dataList.remove(data);
        Log.d("deletItem: ", dataList.remove(data) + "");
        notifyDataSetChanged();
    }

    /**
     * 设置是否显示head
     *
     * @param ifEnable 是否显示头部
     */
    public void setEnableHead(boolean ifEnable) {
        if (headHolder==null){
            enableHead=false;
        }else {
            enableHead = ifEnable;
        }
    }


    public void setHeadHolder(int layResId) {
        enableHead = true;
        headHolder  = ViewHolderHelper.get(mContext, null, null, layResId, 0);
        notifyItemInserted(0);
    }

    public ViewHolderHelper getHeadHolder() {
        return headHolder;
    }

    public void bindHeadData(ViewHolderHelper headHolder){
        if (headHolder==null){
            return;
        }
    }

}