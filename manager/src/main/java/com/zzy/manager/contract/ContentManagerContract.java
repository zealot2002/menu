package com.zzy.manager.contract;

import com.zzy.commonlib.base.BasePresenter;
import com.zzy.commonlib.base.BaseView;
import com.zzy.storehouse.model.Category;
import com.zzy.storehouse.model.Goods;

import java.util.List;

/**
 * @author dell-7020
 * @Description:
 * @date 2018/08/27 14:27:03
 */

public interface ContentManagerContract {
    interface View extends BaseView {
        void updateCategoryList(List<Category> categoryList);
        void updateGoodsList(List<Goods> goodsList);
    }

    interface Presenter extends BasePresenter {
        void getGoodsList();
    }
}