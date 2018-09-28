package com.zzy.order.view.inner;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.zzy.common.constants.ParamConstants;
import com.zzy.common.constants.RouterConstants;
import com.zzy.common.utils.CommonUtils;
import com.zzy.common.utils.DateUtils;
import com.zzy.order.R;
import com.zzy.order.utils.InnerUtils;
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
        return R.layout.order_history_list_item;
    }

    @Override
    public boolean isForViewType(Order item, int position) {
        return true;
    }

    @Override
    public void convert(ViewHolder holder,final Order order,final int position) {
        holder.setText(R.id.tvId,order.getId()+"");
        holder.setText(R.id.tvDeskNum,order.getDeskNum()+"");
        try {
            holder.setText(R.id.tvPrice, CommonUtils.formatMoney(order.getPrice()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.setText(R.id.tvCreateTime, DateUtils.getCurrentDateTime(order.getCreateTime()));

        InnerUtils.setStateText((TextView) holder.getView(R.id.tvState),order.getState());

        LinearLayout llRoot = holder.getView(R.id.llRoot);
        llRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(RouterConstants.ORDER_DETAIL)
                        .withSerializable(ParamConstants.PARAM_DATA,order).navigation();
            }
        });
    }
}
