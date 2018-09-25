package com.zzy.manager.view;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.zzy.common.base.BaseTitleBarActivity;
import com.zzy.common.constants.BusConstants;
import com.zzy.common.constants.ParamConstants;
import com.zzy.common.constants.RouterConstants;
import com.zzy.commonlib.core.BusHelper;
import com.zzy.manager.R;
import com.zzy.manager.contract.ManagerContract;
import com.zzy.manager.presenter.ManagerPresenter;
import com.zzy.manager.view.inner.CategoryListAdapter;
import com.zzy.manager.view.inner.GoodsListAdapter;
import com.zzy.storehouse.StoreProxy;
import com.zzy.storehouse.model.Category;
import com.zzy.storehouse.model.Goods;

import java.util.List;

/**
 * @author zzy
 * @date 2018/9/20
 */
@Route(path = RouterConstants.MANAGER_CONTENT)
public class ContentManagerActivity extends BaseTitleBarActivity implements View.OnClickListener,ManagerContract.View {
    private RelativeLayout rlCategory,rlGoods;
    private View vSelectedCategory,vSelectedGoods;
    private RecyclerView rvContentList;
    private List<Category> categoryList;
    private List<Goods> goodsList;
    private CategoryListAdapter categoryListAdapter;
    private GoodsListAdapter goodsListAdapter;
    private ManagerPresenter presenter;
    private View contentView;
/***************************************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("内容管理");
        setOnBackEventListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        setRightText(getResources().getString(R.string.add));
        setOnRightListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(vSelectedCategory.getVisibility() == View.VISIBLE){
                    addNewCategory();
                }else{
                    if(categoryList.isEmpty()){
                        Toast.makeText(ContentManagerActivity.this, "请至少添加一个类别", Toast.LENGTH_LONG).show();
                        return;
                    }
                    addNewGoods();
                }
            }
        });
        initData();
    }

    private void addNewGoods() {
        ARouter.getInstance().build(RouterConstants.MANAGER_GOODS_DETAIL).withLong(ParamConstants.PARAM_ID,0).navigation();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.getGoodsList();
    }

    private void addNewCategory() {
        Category category = new Category();
        category.setName("新类别");
        categoryList.add(category);
        StoreProxy.getInstance().updateCategory(category);
        categoryListAdapter.notifyDataSetChanged();
        rvContentList.scrollToPosition(categoryList.size()-1);

        BusHelper.getBus().post(BusConstants.EVENT_STORE_DATA_CHANGED,"1");
    }

    private void initData() {
        presenter = new ManagerPresenter(this);
        presenter.start();
    }

    private void updateViews() {
        if(contentView == null){
            contentView = View.inflate(this, R.layout.manager_main_activity,null);
            getContainer().addView(contentView);
            setupMenu();
            setupRecyclerView();
        }
        updateContentList();
    }

    private void setupRecyclerView() {
        if(rvContentList == null){
            rvContentList = findViewById(R.id.rvContentList);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            rvContentList.setLayoutManager(layoutManager);
            rvContentList.setItemAnimator(new DefaultItemAnimator());

            categoryListAdapter = new CategoryListAdapter();
            goodsListAdapter = new GoodsListAdapter();
        }
    }

    private void setupMenu() {
        if(rlCategory == null){
            rlCategory = findViewById(R.id.rlCategory);
            rlGoods = findViewById(R.id.rlGoods);
            vSelectedCategory = findViewById(R.id.vSelectedCategory);
            vSelectedGoods = findViewById(R.id.vSelectedGoods);

            rlCategory.setOnClickListener(this);
            rlGoods.setOnClickListener(this);
        }
    }

    private void updateContentList(){
        if(vSelectedCategory.getVisibility() == View.VISIBLE){
            rvContentList.setAdapter(categoryListAdapter);
            categoryListAdapter.swapData(categoryList);
        }else {
            rvContentList.setAdapter(goodsListAdapter);
            goodsListAdapter.swapData(goodsList);
        }
    }


    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.rlCategory){
            vSelectedCategory.setVisibility(View.VISIBLE);
            vSelectedGoods.setVisibility(View.GONE);
            updateContentList();
        }else if(v.getId() == R.id.rlGoods){
            vSelectedGoods.setVisibility(View.VISIBLE);
            vSelectedCategory.setVisibility(View.GONE);
            presenter.getGoodsList();
        }
    }

    @Override
    public void updateCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    @Override
    public void updateGoodsList(List<Goods> goodsList) {
        this.goodsList = goodsList;
        updateViews();
    }
}
