package com.zzy.order.view.inner;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zzy.common.utils.ApplicationUtils;
import com.zzy.common.utils.CommonUtils;
import com.zzy.common.utils.ImageLoaderUtils;
import com.zzy.common.widget.RoundImageView;
import com.zzy.common.widget.shoppingCart.GoodsWrapperBean;
import com.zzy.order.R;

import java.util.ArrayList;
import java.util.List;

public class GoodsListAdapter extends RecyclerView.Adapter<GoodsListAdapter.ViewHolder> {
    private List<GoodsWrapperBean> mDataSet = new ArrayList<>();
/******************************************************************************************************************/

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public RoundImageView ivPic;
        public TextView tvName,tvPrice,tvNum;

        public ViewHolder(View view) {
            super(view);
            ivPic = view.findViewById(R.id.ivPic);
            tvName = view.findViewById(R.id.tvName);
            tvPrice = view.findViewById(R.id.tvPrice);
            tvNum = view.findViewById(R.id.tvNum);
        }
    }

    public void swapData(List<GoodsWrapperBean> mNewDataSet) {
        mDataSet = mNewDataSet;
        notifyDataSetChanged();
    }

    @Override
    public GoodsListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_goods_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GoodsListAdapter.ViewHolder holder, final int position) {
        final GoodsWrapperBean item = mDataSet.get(position);
        holder.tvName.setText(item.getGoodsBean().getName());
        try {
            holder.tvPrice.setText(ApplicationUtils.get().getResources().getString(R.string.symbol_rmb)
                    + CommonUtils.formatMoney(item.getGoodsBean().getPrice())+" /份");
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.tvNum.setText(item.getNum()+"份");
        ImageLoaderUtils.getInstance().showImg(ApplicationUtils.get(),item.getGoodsBean().getImgUri(), holder.ivPic);
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

}
