package com.zzy.home.contract;

import com.zzy.commonlib.base.BaseLoadingView;
import com.zzy.commonlib.base.BasePresenter;
import com.zzy.commonlib.base.BaseView;

/**
 * @author dell-7020
 * @Description:
 * @date 2018/08/27 14:27:03
 */

public interface HomeContract {
    interface View extends BaseLoadingView {
    }

    interface Presenter extends BasePresenter {
        void putOrder();
    }
}