package com.zzy.home.presenter;


import android.support.annotation.NonNull;

import com.zzy.common.constants.HttpConstants;
import com.zzy.common.utils.ApplicationUtils;
import com.zzy.common.utils.MyToast;
import com.zzy.commonlib.http.HInterface;
import com.zzy.commonlib.utils.NetUtils;
import com.zzy.home.contract.HomeContract;
import com.zzy.home.model.HttpProxy;
import com.zzy.home.model.bean.CategoryBean;
import com.zzy.home.model.bean.GoodsBean;
import com.zzy.home.model.wrapper.CategoryWrapper;
import com.zzy.home.model.wrapper.MenuContext;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author dell-7020
 * @Description:
 * @date 2018/08/27 14:27:03
 */

public class HomePresenter implements HomeContract.Presenter{
    private final HomeContract.View view;
    private AtomicInteger dog;
    private MenuContext menuContext;
/****************************************************************************************************/
    public HomePresenter(@NonNull HomeContract.View view) {
        this.view = view;
    }
    @Override
    public void start() {
        if (!NetUtils.isNetworkAvailable(ApplicationUtils.get())) {
            //do nothing..just go on
            view.showDisconnect();
            return;
        }
        menuContext = new MenuContext();
        dog = new AtomicInteger(0);
        view.showLoading();
        getCategoryList();
        getShopPic();
        getGoodsList();
    }


    private void getCategoryList() {
        HttpProxy.getCategoryList(new HInterface.DataCallback() {
            @Override
            public void requestCallback(int result, Object o, Object o1) {
                view.closeLoading();
                if (result == HttpConstants.SUCCESS) {
                    menuContext.setCategoryList((List<CategoryBean>) o);
                    updateUI();
                }else{
                    netErrHandle((String) o);
                }
            }
        });
    }

    private void netErrHandle(String s) {
        view.closeLoading();
        MyToast.show(ApplicationUtils.get(),s);
        view.showDisconnect();
    }

    private void updateUI() {
        if(dog.incrementAndGet() == 3) {
            view.closeLoading();
            view.updateUI(menuContext);
        }
    }

    private void getShopPic() {
        HttpProxy.getShopPic(new HInterface.DataCallback() {
            @Override
            public void requestCallback(int result, Object o, Object o1) {
                view.closeLoading();
                if (result == HttpConstants.SUCCESS) {
                    menuContext.setShopPicUrl((String) o);
                    updateUI();
                }else{
                    netErrHandle((String) o);
                }
            }
        });
    }

    private void getGoodsList() {
        HttpProxy.getGoodsList(new HInterface.DataCallback() {
            @Override
            public void requestCallback(int result, Object o, Object o1) {
                view.closeLoading();
                if (result == HttpConstants.SUCCESS) {
                    menuContext.setGoodsList((List<GoodsBean>) o);
                    updateUI();
                }else{
                    netErrHandle((String) o);
                }
            }
        });
    }

    @Override
    public void putOrder() {

    }
}