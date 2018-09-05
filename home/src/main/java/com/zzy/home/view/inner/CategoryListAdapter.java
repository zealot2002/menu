package com.zzy.home.view.inner;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zzy.home.R;
import com.zzy.home.model.wrapper.CategoryWrapper;

import java.util.List;

/**
 * @author zzy
 * @date 2018/8/27
 */

public class CategoryListAdapter extends CommonAdapter<CategoryWrapper> {
    public CategoryListAdapter(Context context, int layoutId, List<CategoryWrapper> dataList) {
        super(context, layoutId, dataList);
    }

    @Override
    protected void convert(ViewHolder holder, CategoryWrapper item, int position) {

        TextView tvMenuName = holder.getView(R.id.tvMenuName);
        tvMenuName.setText(item.getCategoryBean().getName());

        View vSelected = holder.getView(R.id.vSelected);
        vSelected.setVisibility(item.isSelected()?View.VISIBLE:View.GONE);
    }
}