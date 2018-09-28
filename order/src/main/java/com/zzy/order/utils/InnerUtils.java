package com.zzy.order.utils;

import android.widget.TextView;

import com.zzy.common.utils.ApplicationUtils;
import com.zzy.order.R;
import com.zzy.storehouse.model.Order;

/**
 * @author zzy
 * @date 2018/9/26
 */

public class InnerUtils {
    public static void setStateText(TextView tvState, int state) {
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
}
