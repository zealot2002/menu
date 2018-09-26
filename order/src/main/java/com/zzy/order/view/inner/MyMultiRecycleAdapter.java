package com.zzy.order.view.inner;

import android.content.Context;

import com.zzy.order.view.inner.recycleAdapter.ItemViewDelegate;
import com.zzy.order.view.inner.recycleAdapter.ItemViewDelegateManager;
import com.zzy.order.view.inner.recycleAdapter.MultiBaseAdapter;
import com.zzy.order.view.inner.recycleAdapter.ViewHolder;
import com.zzy.storehouse.model.Order;

import java.util.List;


public class MyMultiRecycleAdapter extends MultiBaseAdapter<Order> {
    protected Context mContext;
    protected List<Order> mDatas;
    protected ItemViewDelegateManager mItemViewDelegateManager;
/*****************************************************************************************************/
    public MyMultiRecycleAdapter(Context context, List<Order> datas, boolean isOpenLoadMore) {
        super(context, datas, isOpenLoadMore);
        mContext = context;
        mDatas = datas;
        mItemViewDelegateManager = new ItemViewDelegateManager();
    }

    @Override
    protected void convert(ViewHolder holder, final Order data, int position, int viewType) {
        mItemViewDelegateManager.convert(holder, data, holder.getAdapterPosition());
    }

    public MyMultiRecycleAdapter addItemViewDelegate(ItemViewDelegate itemViewDelegate) {
        mItemViewDelegateManager.addDelegate(itemViewDelegate);
        return this;
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        return mItemViewDelegateManager.getItemViewLayoutId(viewType);
    }

    @Override
    protected int getViewType(int position, Order data) {
        return mItemViewDelegateManager.getItemViewType(data,position);
    }
}
