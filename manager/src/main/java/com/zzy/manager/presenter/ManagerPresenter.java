package com.zzy.manager.presenter;


import android.support.annotation.NonNull;

import com.zzy.manager.contract.ContentManagerContract;
import com.zzy.storehouse.StoreProxy;


/**
 * @author dell-7020
 * @Description:
 * @date 2018/08/27 14:27:03
 */

public class ManagerPresenter implements ContentManagerContract.Presenter{
    private final ContentManagerContract.View view;
/****************************************************************************************************/
    public ManagerPresenter(@NonNull ContentManagerContract.View view) {
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

    @Override
    public void getGoodsList() {
        view.updateGoodsList(StoreProxy.getInstance().getAllGoodsList());
    }
}