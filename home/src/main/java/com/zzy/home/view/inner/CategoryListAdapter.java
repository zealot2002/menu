package com.zzy.home.view.inner;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zzy.home.R;
import com.zzy.home.model.wrapper.CategoryWrapper;

import java.util.ArrayList;
import java.util.List;

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.ViewHolder> {
    public interface Listener{
        void onItemClicked(int position);
    }
    private List<CategoryWrapper> mDataSet = new ArrayList<>();
    private Listener listener;

/******************************************************************************************************************/
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvMenuName;
        public View vSelected;
        public RelativeLayout rlRoot;

        public ViewHolder(View view) {
            super(view);
            tvMenuName = view.findViewById(R.id.tvMenuName);
            vSelected = view.findViewById(R.id.vSelected);
            rlRoot = view.findViewById(R.id.rlRoot);
        }
    }

    public void setOnItemClickedListener(Listener listener ){
        this.listener = listener;
    }
    public void swapData(List<CategoryWrapper> mNewDataSet) {
        mDataSet = mNewDataSet;
        notifyDataSetChanged();
    }
    @Override
    public CategoryListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_category_list_item, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(final CategoryListAdapter.ViewHolder holder, final int position) {
        final CategoryWrapper item = mDataSet.get(position);
        holder.tvMenuName.setText(item.getCategory().getName());
        holder.vSelected.setVisibility(item.isSelected()?View.VISIBLE:View.GONE);
        holder.rlRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener!=null){
                    listener.onItemClicked(position);
                }
            }
        });
    }
    @Override
    public int getItemCount() {
        return mDataSet == null?0:mDataSet.size();
    }

}
