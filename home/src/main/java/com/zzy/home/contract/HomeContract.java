package com.zzy.home.contract;

import com.zzy.commonlib.base.BaseLoadingView;
import com.zzy.commonlib.base.BasePresenter;
import com.zzy.commonlib.base.BaseView;
import com.zzy.home.model.wrapper.MenuContext;

/**
 * @author dell-7020
 * @Description:
 * @date 2018/08/27 14:27:03
 */

public interface HomeContract {
    interface View extends BaseView {
        void updateUI(Object o);
    }

    interface Presenter extends BasePresenter {
        void putOrder();
    }
}