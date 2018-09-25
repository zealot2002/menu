package com.zzy.manager.view.inner;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.zzy.common.constants.ParamConstants;
import com.zzy.common.constants.RouterConstants;
import com.zzy.common.utils.ApplicationUtils;
import com.zzy.common.utils.ImageLoaderUtils;
import com.zzy.common.utils.MyToast;
import com.zzy.common.widget.RoundImageView;
import com.zzy.manager.R;
import com.zzy.storehouse.StoreProxy;
import com.zzy.storehouse.model.Goods;

import java.util.ArrayList;
import java.util.List;

public class GoodsListAdapter extends RecyclerView.Adapter<GoodsListAdapter.ViewHolder> {
    private List<Goods> dataList = new ArrayList<>();
/******************************************************************************************************************/

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName,tvEdit,tvDelete;
        public RoundImageView ivPic;

        public ViewHolder(View view) {
            super(view);
            tvName = view.findViewById(R.id.tvName);
            tvEdit = view.findViewById(R.id.tvEdit);
            tvDelete = view.findViewById(R.id.tvDelete);
            ivPic = view.findViewById(R.id.ivPic);
        }
    }

    public void swapData(List<Goods> mNewDataSet) {
        dataList = mNewDataSet;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.manager_goods_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Goods goods = dataList.get(position);
        holder.tvName.setText(goods.getName());

        ImageLoaderUtils.getInstance().showImg(ApplicationUtils.get(),goods.getImageUri(), holder.ivPic);

        holder.tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Goods bean = dataList.get(position);
                ARouter.getInstance().build(RouterConstants.MANAGER_GOODS_DETAIL).withLong(ParamConstants.PARAM_ID,bean.getId()).navigation();
            }
        });
        holder.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StoreProxy.getInstance().deleteGoods(dataList.get(position).getId());
                dataList.remove(position);
                notifyDataSetChanged();
                MyToast.show(ApplicationUtils.get(),"删除成功!");
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList == null?0: dataList.size();
    }

}
