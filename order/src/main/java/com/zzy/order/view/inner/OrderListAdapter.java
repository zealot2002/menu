package com.zzy.order.view.inner;

import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.zzy.common.constants.ParamConstants;
import com.zzy.common.constants.RouterConstants;
import com.zzy.common.utils.ApplicationUtils;
import com.zzy.common.utils.CommonUtils;
import com.zzy.common.utils.MyToast;
import com.zzy.order.R;
import com.zzy.storehouse.StoreProxy;
import com.zzy.storehouse.model.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.ViewHolder> {
    private List<Order> dataList = new ArrayList<>();
/******************************************************************************************************************/
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvId,tvDeskNum,tvCreateTime,tvPrice,tvState,tvOp;
        public LinearLayout llRoot;
        public ViewHolder(View view) {
            super(view);
            tvId = view.findViewById(R.id.tvId);
            tvDeskNum = view.findViewById(R.id.tvDeskNum);
            tvCreateTime = view.findViewById(R.id.tvCreateTime);
            tvPrice = view.findViewById(R.id.tvPrice);
            tvState = view.findViewById(R.id.tvState);
            tvOp = view.findViewById(R.id.tvOp);

            llRoot = view.findViewById(R.id.llRoot);
        }
    }

    public void swapData(List<Order> mNewDataSet) {
        dataList = mNewDataSet;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_today_list_item, parent, false);
        return new ViewHolder(view);
    }

    private String getStateText(int type){
        if(type == Order.ORDER_STATE_WAIT){
            return "待处理";
        }else if(type == Order.ORDER_STATE_DOING){
            return "进行中";
        }else {
            return "已完成";
        }
    }
    private String getOpText(int type){
        if(type == Order.ORDER_STATE_WAIT){
            return "处理";
        }else if(type == Order.ORDER_STATE_DOING){
            return "完成";
        }else {
            return "查看";
        }
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Order order = dataList.get(position);
        holder.tvId.setText(order.getId()+"");
        holder.tvDeskNum.setText(order.getDeskNum()+"");
        holder.tvCreateTime.setText(DateUtils.formatDateTime(ApplicationUtils.get(),Long.valueOf(order.getCreateTime()),DateUtils.FORMAT_SHOW_TIME));

        try {
            holder.tvPrice.setText(CommonUtils.formatMoney(order.getPrice()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        setStateText(holder.tvState,order.getState());
        holder.tvOp.setText(getOpText(order.getState()));
        holder.tvOp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(order.getState()==Order.ORDER_STATE_OVER){
                    //view this order info
                    viewOrder(order);
                    return;
                }else{
                    dataList.get(position).setState(order.getState()+1);
                }
                StoreProxy.getInstance().updateOrder(dataList.get(position));
                notifyDataSetChanged();
                MyToast.show(ApplicationUtils.get(),"操作成功!");
            }
        });
        holder.llRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewOrder(dataList.get(position));
            }
        });
    }

    private void viewOrder(Order order){
        ARouter.getInstance().build(RouterConstants.ORDER_DETAIL)
                .withSerializable(ParamConstants.PARAM_DATA,order).navigation();
    }

    private void setStateText(TextView tvState, int state) {
        if(state == Order.ORDER_STATE_WAIT){
            tvState.setText("待处理");
            tvState.setTextColor(ApplicationUtils.get().getResources().getColor(R.color.red));
        }else if(state == Order.ORDER_STATE_DOING){
            tvState.setText("进行中");
            tvState.setTextColor(ApplicationUtils.get().getResources().getColor(R.color.orange));
        }else {
            tvState.setText("已完成");
            tvState.setTextColor(ApplicationUtils.get().getResources().getColor(R.color.green));
        }
    }

    @Override
    public int getItemCount() {
        return dataList == null?0: dataList.size();
    }

}
