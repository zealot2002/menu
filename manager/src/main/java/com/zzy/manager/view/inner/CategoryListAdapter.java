package com.zzy.manager.view.inner;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.zzy.common.utils.ApplicationUtils;
import com.zzy.common.utils.MyToast;
import com.zzy.manager.R;
import com.zzy.storehouse.StoreProxy;
import com.zzy.storehouse.model.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.ViewHolder> {
    private List<Category> dataList = new ArrayList<>();
/******************************************************************************************************************/
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public EditText etName;
        public TextView tvEdit,tvDelete;
        public ViewHolder(View view) {
            super(view);
            etName = view.findViewById(R.id.etName);
            tvEdit = view.findViewById(R.id.tvEdit);
            tvDelete = view.findViewById(R.id.tvDelete);
        }
    }

    public void swapData(List<Category> mNewDataSet) {
        dataList = mNewDataSet;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.manager_category_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Category category = dataList.get(position);
        holder.etName.setText(category.getName());
        holder.tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Category bean = dataList.get(position);
                String text = holder.etName.getText().toString().trim();
                if(!bean.getName().equals(text)){
                    StoreProxy.getInstance().updateCategory(bean);
                    dataList.get(position).setName(text);
                    notifyDataSetChanged();
                    MyToast.show(ApplicationUtils.get(),"修改成功!");
                }
            }
        });
        holder.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StoreProxy.getInstance().deleteCategory(dataList.get(position).getId());
                dataList.remove(position);

                notifyDataSetChanged();
                MyToast.show(ApplicationUtils.get(),"删除成功!");
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList == null?0: dataList.size();
    }

}
