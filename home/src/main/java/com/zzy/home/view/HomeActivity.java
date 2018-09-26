package com.zzy.home.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.hwangjr.rxbus.thread.EventThread;
import com.zzy.common.base.BaseLoadingActivity;
import com.zzy.common.constants.BusConstants;
import com.zzy.common.constants.ParamConstants;
import com.zzy.common.constants.RouterConstants;
import com.zzy.common.utils.CommonUtils;
import com.zzy.common.widget.shoppingCart.GoodsWrapperBean;
import com.zzy.common.widget.shoppingCart.ShoppingCartWidget;
import com.zzy.commonlib.base.BaseActivity;
import com.zzy.commonlib.core.BusHelper;
import com.zzy.commonlib.log.MyLog;
import com.zzy.home.R;
import com.zzy.home.contract.HomeContract;
import com.zzy.home.model.wrapper.MenuContext;
import com.zzy.home.presenter.HomePresenter;
import com.zzy.home.view.inner.CategoryListAdapter;
import com.zzy.home.view.inner.GoodsListAdapter;
import com.zzy.home.widget.GoodsDialog;
import com.zzy.storehouse.model.Goods;

import java.io.Serializable;
import java.util.List;

/**
 * @author dell-7020
 * @Description:
 * @date 2018/08/27 14:27:03
 */

@Route(path = RouterConstants.HOME_MAIN)
public class HomeActivity extends BaseActivity implements HomeContract.View, View.OnClickListener {
    private static final int RESULT_FROM_SEARCH = 100;
    private HomeContract.Presenter presenter;
    private TextView tvSearch;
    private RecyclerView rvCategory, rvGoods;
    private ImageView ivSettings;

    private MenuContext menuContext;
    private GoodsListAdapter goodsListAdapter;
    private CategoryListAdapter categoryListAdapter;

    private ShoppingCartWidget shoppingCart;
    private GoodsDialog goodsDialog;
    private boolean needReload = false;
/****************************************************************************************************/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CommonUtils.statusBarHide(this);

        setContentView(R.layout.home_main);
        presenter = new HomePresenter(this);
        BusHelper.getBus().register(this);
        needReload = true;
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void reload(boolean b) {
        presenter.start();
    }

    @Override
    public void updateUI(Object o) {
        menuContext = (MenuContext) o;
        menuContext.prepareWrapper();
        updateViews();
    }

    private void updateViews() {
        updateSearch();
        updateSettings();
        updateShopCart();
        updateCategoryList();
        if(!menuContext.getCategoryList().isEmpty()){
            updateGoodsList(0);
        }
    }

    private void updateSettings() {
        ivSettings = findViewById(R.id.ivSettings);
        ivSettings.setOnClickListener(this);
    }

    private void updateShopCart() {
        shoppingCart = findViewById(R.id.shoppingCart);
        shoppingCart.setSubmitListener(new ShoppingCartWidget.SubmitListener() {
            @Override
            public void onSubmit(List<GoodsWrapperBean> dataList) {
                ARouter.getInstance().build(RouterConstants.ORDER_CONFIRM)
                        .withSerializable(ParamConstants.PARAM_DATA, (Serializable) dataList)
                        .navigation();
                shoppingCart.fold();
            }
        });
    }

    private void updateGoodsList(int categoryIndex) {
        if(categoryIndex<0){
            return;
        }
//        if(rvGoods == null){
            rvGoods = findViewById(R.id.rvGoods);
            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,2);
            rvGoods.setLayoutManager(layoutManager);
            goodsListAdapter = new GoodsListAdapter();
            goodsListAdapter.setListener(new GoodsListAdapter.Listener() {
                @Override
                public void onItemAdd(Goods goodsBean) {
                    com.zzy.common.widget.shoppingCart.GoodsBean bean =
                            new com.zzy.common.widget.shoppingCart.GoodsBean.Builder(goodsBean.getName())
                            .id(goodsBean.getId()+"")
                            .price(Float.valueOf(goodsBean.getPrice()))
                            .imgUri(goodsBean.getImageUri())
                            .build();
                    shoppingCart.addGoods(bean);
                }

                @Override
                public void onItemView(Goods goodsBean) {
                    showGoodsDialog(goodsBean);
                }
            });
            rvGoods.setAdapter(goodsListAdapter);
//        }
        goodsListAdapter.swapData(menuContext.getCategoryTreeMap().get(menuContext.getCategoryList().get(categoryIndex).getId()));
    }

    private void updateCategoryList() {
        rvCategory = findViewById(R.id.rvCategory);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL,false);
        rvCategory.setLayoutManager(layoutManager);
        categoryListAdapter = new CategoryListAdapter();
        rvCategory.setAdapter(categoryListAdapter);
        rvCategory.setFocusable(false);
        categoryListAdapter.setOnItemClickedListener(new CategoryListAdapter.Listener(){
            @Override
            public void onItemClicked(int position) {
                updateGoodsList(position);
                updateCategoryWrapper(position);
                categoryListAdapter.notifyDataSetChanged();
            }
        });
        categoryListAdapter.swapData(menuContext.getCategoryWrapperList());
    }

    private void updateCategoryWrapper(int position) {
        for(int i=0;i<menuContext.getCategoryWrapperList().size();i++){
            menuContext.getCategoryWrapperList().get(i).setSelected(i == position?true:false);
        }
    }

    private void updateSearch() {
        tvSearch = findViewById(R.id.tvSearch);
        tvSearch.setText("    请在这里搜索您想要的商品...                         " +
                "    请在这里搜索您想要的商品...               ");
        tvSearch.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(tvSearch!=null){
            tvSearch.setOnClickListener(this);
        }
        if(needReload){
            needReload = false;
            reload(false);
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.tvSearch){
            tvSearch.setOnClickListener(null);
            ARouter.getInstance().build(RouterConstants.SEARCH)
                    .withSerializable(ParamConstants.PARAM_GOODS_LIST, (Serializable) menuContext.getGoodsList())
                    .withSerializable(ParamConstants.PARAM_SHOPCART_DATA, (Serializable) shoppingCart.getCurrentGoods())
                    .navigation(this,RESULT_FROM_SEARCH);
        }else if(v.getId() == R.id.ivSettings){
            ARouter.getInstance().build(RouterConstants.MANAGER_SETTINGS).navigation();
        }
    }

    private void showGoodsDialog(Goods goodsBean) {
        if(goodsDialog == null){
            goodsDialog = new GoodsDialog(this);
        }
        goodsDialog.setData(goodsBean);
        goodsDialog.show();
        goodsDialog.setCanceledOnTouchOutside(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        MyLog.e("zzy onActivityResult");
        try{
            if(requestCode == RESULT_FROM_SEARCH){
                List<GoodsWrapperBean> dataList = (List<GoodsWrapperBean>) intent.getSerializableExtra(ParamConstants.PARAM_SHOPCART_DATA);
                shoppingCart.replaceAllGoods(dataList);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    private long exitTime = 0;
    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            super.onBackPressed();
        }
    }

    @Subscribe(thread = EventThread.IMMEDIATE, tags = {@Tag(value = BusConstants.EVENT_STORE_DATA_CHANGED)})
    public void onStoreDataChanged(String s){
        //clean the shopcart
        needReload = true;
    }

    @Subscribe(thread = EventThread.IMMEDIATE, tags = {@Tag(value = BusConstants.EVENT_ORDER_SUCCESS)})
    public void onOrderSuccess(String s){
        //clean the shopcart
        shoppingCart.cleanShoppingCart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BusHelper.getBus().unregister(this);
    }
}