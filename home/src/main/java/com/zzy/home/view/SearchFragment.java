package com.zzy.home.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.launcher.ARouter;
import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.zzy.common.constants.ParamConstants;
import com.zzy.common.constants.RouterConstants;
import com.zzy.common.widget.shoppingCart.GoodsWrapperBean;
import com.zzy.common.widget.shoppingCart.ShoppingCartWidget;
import com.zzy.commonlib.log.MyLog;
import com.zzy.home.R;
import com.zzy.home.model.SearchHelper;
import com.zzy.home.model.bean.GoodsBean;
import com.zzy.home.model.bean.GoodsSuggestion;
import com.zzy.home.view.inner.SearchGoodsListAdapter;
import com.zzy.home.widget.GoodsDialog;

import java.io.Serializable;
import java.util.List;

public class SearchFragment extends Fragment {
    private FloatingSearchView mSearchView;
    private RecyclerView rvGoodsList;
    private SearchGoodsListAdapter goodsListAdapter;

    private String mLastQuery = "";
//    private TitleBar titleBar;
    private ShoppingCartWidget shoppingCart;
    private GoodsDialog goodsDialog;
    private List<GoodsBean> goodsList;
/*****************************************************************************************************/
    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_search_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSearchView = view.findViewById(R.id.floating_search_view);
        rvGoodsList = view.findViewById(R.id.search_results_list);
        try{
            goodsList = (List<GoodsBean>) getActivity().getIntent().getSerializableExtra(ParamConstants.PARAM_GOODS_LIST);
            setupFloatingSearch();
            setupResultsList();
//            setupTitle(view);
            updateShopCart(view);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void updateShopCart(View view) {
        shoppingCart = view.findViewById(R.id.shoppingCart);
        /*init data*/
        List<GoodsWrapperBean> dataList = (List<GoodsWrapperBean>) getActivity().getIntent().getSerializableExtra(ParamConstants.PARAM_SHOPCART_DATA);
        shoppingCart.replaceAllGoods(dataList);
        shoppingCart.setSubmitListener(new ShoppingCartWidget.SubmitListener() {
            @Override
            public void onSubmit(List<GoodsWrapperBean> dataList) {
                ARouter.getInstance().build(RouterConstants.ORDER_CONFIRM)
                        .withSerializable(ParamConstants.PARAM_DATA, (Serializable) dataList)
                        .navigation();
                onActivityFinishing();
                getActivity().finish();
            }
        });
    }

    private void setupFloatingSearch() {
        mSearchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {

            @Override
            public void onSearchTextChanged(String oldQuery, final String newQuery) {

                if (!oldQuery.equals("") && newQuery.equals("")) {
                    mSearchView.clearSuggestions();
                } else {
                    mSearchView.showProgress();
                    SearchHelper.getInstance().findSuggestions(getActivity(), newQuery, 5,
                            250, new SearchHelper.OnFindSuggestionsListener() {

                                @Override
                                public void onResults(List<GoodsSuggestion> results) {

                                    //this will swap the data and
                                    //render the collapse/expand animations as necessary
                                    mSearchView.swapSuggestions(results);

                                    //let the users know that the background
                                    //process has completed
                                    mSearchView.hideProgress();
                                }
                            });
                }

               MyLog.e("onSearchTextChanged()");
            }
        });

        mSearchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(final SearchSuggestion searchSuggestion) {
                SearchHelper.getInstance().findGoods(searchSuggestion.getBody(),goodsList,
                        new SearchHelper.OnFindGoodsListener() {
                            @Override
                            public void onResults(List<GoodsBean> results) {
                                goodsListAdapter.swapData(results);
                            }

                        });
                MyLog.e( "onSuggestionClicked()");
                mLastQuery = searchSuggestion.getBody();
            }

            @Override
            public void onSearchAction(String query) {
                mLastQuery = query;
                SearchHelper.getInstance().findGoods(query,goodsList,
                        new SearchHelper.OnFindGoodsListener() {
                            @Override
                            public void onResults(List<GoodsBean> results) {

                            goodsListAdapter.swapData(results);
                            }

                        });
                MyLog.e( "onSearchAction()");
            }
        });

        mSearchView.setOnFocusChangeListener(new FloatingSearchView.OnFocusChangeListener() {
            @Override
            public void onFocus() {
                //show suggestions when search bar gains focus (typically history suggestions)
                mSearchView.swapSuggestions(SearchHelper.getInstance().getHistory(3));
                MyLog.e( "onFocus()");
            }

            @Override
            public void onFocusCleared() {

                //set the title of the bar so that when focus is returned a new query begins
                mSearchView.setSearchBarTitle(mLastQuery);
                MyLog.e("onFocusCleared()");
            }
        });
        //use this listener to listen to menu clicks when app:floatingSearch_leftAction="showHome"
        mSearchView.setOnHomeActionClickListener(new FloatingSearchView.OnHomeActionClickListener() {
            @Override
            public void onHomeClicked() {
                MyLog.e("onHomeClicked()");
            }
        });

        //listen for when suggestion list expands/shrinks in order to move down/up the
        //search results list
        mSearchView.setOnSuggestionsListHeightChanged(new FloatingSearchView.OnSuggestionsListHeightChanged() {
            @Override
            public void onSuggestionsListHeightChanged(float newHeight) {
                rvGoodsList.setTranslationY(newHeight);
            }
        });
    }

    private void setupResultsList() {
        goodsListAdapter = new SearchGoodsListAdapter();
        rvGoodsList.setAdapter(goodsListAdapter);
        rvGoodsList.setLayoutManager(new GridLayoutManager(getActivity(),2));
        goodsListAdapter.setListener(new SearchGoodsListAdapter.Listener() {
            @Override
            public void onItemAdd(GoodsBean goodsBean) {
                com.zzy.common.widget.shoppingCart.GoodsBean bean =
                        new com.zzy.common.widget.shoppingCart.GoodsBean.Builder(goodsBean.getName())
                                .id(goodsBean.getId()+"")
                                .price(Float.valueOf(goodsBean.getPrice()))
                                .imgUri(goodsBean.getImageUri())
                                .build();
                shoppingCart.addGoods(bean);
            }

            @Override
            public void onItemView(GoodsBean goodsBean) {
                showGoodsDialog(goodsBean);
            }
        });
    }
    private void showGoodsDialog(GoodsBean goodsBean) {
        if(goodsDialog == null){
            goodsDialog = new GoodsDialog(getActivity());
        }
        goodsDialog.setData(goodsBean);
        goodsDialog.show();
        goodsDialog.setCanceledOnTouchOutside(true);
    }

    /**
     * 需要同步shoppingCart数据给home
     */
   public void onActivityFinishing() {
        getActivity().getIntent().putExtra(ParamConstants.PARAM_SHOPCART_DATA, (Serializable) shoppingCart.getCurrentGoods());
        getActivity().setResult(Activity.RESULT_OK, getActivity().getIntent());
    }
}
