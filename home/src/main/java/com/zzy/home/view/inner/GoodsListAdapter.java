package com.zzy.home.view.inner;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.arlib.floatingsearchview.util.Util;
import com.zzy.common.constants.CommonConstants;
import com.zzy.common.utils.AniUtils;
import com.zzy.common.utils.ApplicationUtils;
import com.zzy.common.utils.ImageLoaderUtils;
import com.zzy.common.widget.RoundImageView;
import com.zzy.home.R;
import com.zzy.storehouse.model.Goods;

import java.util.ArrayList;
import java.util.List;

public class GoodsListAdapter extends RecyclerView.Adapter<GoodsListAdapter.ViewHolder> {
    public interface Listener{
        void onItemAdd(Goods goodsBean);
        void onItemView(Goods goodsBean);
    }
    private List<Goods> mDataSet = new ArrayList<>();
    private int mLastAnimatedItemPosition = -1;
    private GoodsListAdapter.Listener listener;
/******************************************************************************************************************/

    public void setListener(GoodsListAdapter.Listener listener) {
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public RoundImageView ivPic;
        public TextView tvName;
        public TextView tvPrice;
        public ImageButton btnAdd;
        public RelativeLayout rlSellOut;
        public TextView tvAniAdd;

        public ViewHolder(View view) {
            super(view);
            ivPic = view.findViewById(R.id.ivPic);
            tvName = view.findViewById(R.id.tvName);
            tvAniAdd = view.findViewById(R.id.tvAniAdd);
            tvPrice = view.findViewById(R.id.tvPrice);
            btnAdd = view.findViewById(R.id.btnAdd);
            rlSellOut = view.findViewById(R.id.rlSellOut);
        }
    }

    public void swapData(List<Goods> mNewDataSet) {
        mDataSet = mNewDataSet;
        notifyDataSetChanged();
    }

    @Override
    public GoodsListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_goods_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final GoodsListAdapter.ViewHolder holder, final int position) {
        final Goods item = mDataSet.get(position);
        holder.tvName.setText(item.getName());
        holder.tvPrice.setText(ApplicationUtils.get().getResources().getString(R.string.symbol_rmb)+item.getPrice());
        ImageLoaderUtils.getInstance().showImg(ApplicationUtils.get(),item.getImageUri(), holder.ivPic);

        holder.ivPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener!=null){
                    listener.onItemView(item);
                }
            }
        });
        if(item.getState() == CommonConstants.STATE_NORMAL){
            holder.btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null){
                        listener.onItemAdd(item);
                    }
                    AniUtils.startShowAnimation(holder.tvAniAdd,300);
                }
            });
            holder.rlSellOut.setVisibility(View.GONE);
        }else{
            holder.btnAdd.setOnClickListener(null);
            holder.rlSellOut.setVisibility(View.VISIBLE);
        }

//        if(mLastAnimatedItemPosition < position){
//            animateItem(holder.itemView);
//            mLastAnimatedItemPosition = position;
//        }
    }

    @Override
    public int getItemCount() {
        return mDataSet == null?0:mDataSet.size();
    }

//    private void animateItem(View view) {
//        view.setTranslationY(Util.getScreenHeight((Activity) view.getContext()));
//        view.animate()
//                .translationY(0)
//                .setInterpolator(new DecelerateInterpolator(3.f))
//                .setDuration(700)
//                .start();
//    }
}
