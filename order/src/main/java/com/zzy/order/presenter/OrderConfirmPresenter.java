package com.zzy.order.presenter;


import android.support.annotation.NonNull;

import com.zzy.order.contract.OrderConfirmContract;

/**
 * @author dell-7020
 * @Description:
 * @date 2018/08/31 10:37:22
 */

public class OrderConfirmPresenter implements OrderConfirmContract.Presenter{
    private final OrderConfirmContract.View view;

/****************************************************************************************************/
    public OrderConfirmPresenter(@NonNull OrderConfirmContract.View view) {
        this.view = view;
    }
    @Override
    public void start() { }


}