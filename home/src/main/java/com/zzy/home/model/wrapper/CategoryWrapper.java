package com.zzy.home.model.wrapper;

import com.zzy.home.model.bean.CategoryBean;
import com.zzy.home.model.bean.GoodsBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zzy
 * @date 2018/8/27
 */

public class CategoryWrapper {
    private CategoryBean categoryBean;
    private boolean isSelected;

    public CategoryWrapper(CategoryBean categoryBean) {
        this.categoryBean = categoryBean;
        isSelected = false;
    }

    public CategoryBean getCategoryBean() {
        return categoryBean;
    }

    public void setCategoryBean(CategoryBean categoryBean) {
        this.categoryBean = categoryBean;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
