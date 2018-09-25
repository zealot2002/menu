package com.zzy.manager.presenter;


import android.support.annotation.NonNull;

import com.zzy.manager.contract.ManagerContract;
import com.zzy.storehouse.StoreProxy;


/**
 * @author dell-7020
 * @Description:
 * @date 2018/08/27 14:27:03
 */

public class ManagerPresenter implements ManagerContract.Presenter{
    private final ManagerContract.View view;
/****************************************************************************************************/
    public ManagerPresenter(@NonNull ManagerContract.View view) {
        this.view = view;
    }
    @Override
    public void start() {
        getCategoryList();
        getGoodsList();

    }


    private void getCategoryList() {
        view.updateCategoryList(StoreProxy.getInstance().getCategoryList());
    }

    private void getGoodsList() {
        view.updateGoodsList(StoreProxy.getInstance().getAllGoodsList());
    }
}