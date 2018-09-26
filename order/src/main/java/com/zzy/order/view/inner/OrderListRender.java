package com.zzy.order.view.inner;

import android.text.format.DateUtils;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.zzy.common.constants.ParamConstants;
import com.zzy.common.constants.RouterConstants;
import com.zzy.common.utils.ApplicationUtils;
import com.zzy.common.utils.MyToast;
import com.zzy.order.R;
import com.zzy.order.view.inner.recycleAdapter.ItemViewDelegate;
import com.zzy.order.view.inner.recycleAdapter.ViewHolder;
import com.zzy.storehouse.model.Order;

/**
 * @author zzy
 * @date 2018/9/26
 */

public class OrderListRender implements ItemViewDelegate<Order> {
    @Override
    public int getItemViewLayoutId() {
        return R.layout.order_today_list_item;
    }

    @Override
    public boolean isForViewType(Order item, int position) {
        return true;
    }

    @Override
    public void convert(ViewHolder holder, Order order, int position) {
        holder.setText(R.id.tvId,order.getId()+"");

        //        holder.tvId.setText(order.getId()+"");
//        holder.tvDeskNum.setText(order.getDeskNum()+"");
//        holder.tvCreateTime.setText(DateUtils.formatDateTime(ApplicationUtils.get(),Long.valueOf(order.getCreateTime()),DateUtils.FORMAT_SHOW_TIME));
//
//        holder.tvPrice.setText(order.getPrice()+"");
//        setStateText(holder.tvState,order.getState());
//        holder.tvOp.setText(getOpText(order.getState()));
//        holder.tvOp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(order.getState()==Order.ORDER_STATE_OVER){
//                    //view this order info
//                }else{
//                    dataList.get(position).setState(order.getState()+1);
//                }
//                StoreProxy.getInstance().updateOrder(dataList.get(position));
//                notifyDataSetChanged();
//                MyToast.show(ApplicationUtils.get(),"操作成功!");
//            }
//        });
//        holder.llRoot.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ARouter.getInstance().build(RouterConstants.ORDER_DETAIL)
//                        .withSerializable(ParamConstants.PARAM_DATA,dataList.get(position)).navigation();
//            }
//        });
    }
}
