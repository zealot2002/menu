package com.zzy.home.model.wrapper;


import com.zzy.storehouse.model.Category;

/**
 * @author zzy
 * @date 2018/8/27
 */

public class CategoryWrapper {
    private Category category;
    private boolean isSelected;

    public CategoryWrapper(Category category) {
        this.category = category;
        isSelected = false;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
