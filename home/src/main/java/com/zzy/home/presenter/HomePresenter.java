package com.zzy.home.presenter;

import android.support.annotation.NonNull;

import com.zzy.common.utils.ApplicationUtils;
import com.zzy.common.utils.MyToast;
import com.zzy.home.contract.HomeContract;
import com.zzy.home.model.wrapper.MenuContext;
import com.zzy.storehouse.StoreProxy;

/**
 * @author dell-7020
 * @Description:
 * @date 2018/08/27 14:27:03
 */

public class HomePresenter implements HomeContract.Presenter{
    private final HomeContract.View view;
    private MenuContext menuContext;
    /****************************************************************************************************/
    public HomePresenter(@NonNull HomeContract.View view) {
        this.view = view;
    }
    @Override
    public void start() {
        menuContext = new MenuContext();
        getCategoryList();
        getGoodsList();
        view.updateUI(menuContext);
    }
    private void getCategoryList() {
        menuContext.setCategoryList(StoreProxy.getInstance().getCategoryList());
    }
    private void getGoodsList() {
        menuContext.setGoodsList(StoreProxy.getInstance().getAllGoodsList());
    }
    @Override
    public void putOrder() {

    }
}