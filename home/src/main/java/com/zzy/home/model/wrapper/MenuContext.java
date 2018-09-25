package com.zzy.home.model.wrapper;

import com.zzy.storehouse.model.Category;
import com.zzy.storehouse.model.Goods;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * @author zzy
 * @date 2018/8/27
 */

public class MenuContext {
    private String shopPicUrl;
    private List<Category> categoryList;
    private List<Goods> goodsList;
    private TreeMap<Long,List<Goods>> categoryTreeMap;
    private List<CategoryWrapper> categoryWrapperList;

    public MenuContext() {
        categoryList = new ArrayList<>();
        goodsList = new ArrayList<>();
        categoryTreeMap = new TreeMap<>();
        categoryWrapperList = new ArrayList<>();
    }

    public String getShopPicUrl() {
        return shopPicUrl;
    }

    public void setShopPicUrl(String shopPicUrl) {
        this.shopPicUrl = shopPicUrl;
    }

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    public List<CategoryWrapper> getCategoryWrapperList() {
        return categoryWrapperList;
    }

    public List<Goods> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<Goods> goodsList) {
        this.goodsList = goodsList;
    }

    public TreeMap<Long, List<Goods>> getCategoryTreeMap() {
        return categoryTreeMap;
    }

    public void prepareWrapper(){
        if(categoryList==null
                ||categoryList.isEmpty()
                ||goodsList==null
                ||goodsList.isEmpty()
                ){
            return;
        }
        for(int i=0;i<categoryList.size();i++){
            categoryTreeMap.put(categoryList.get(i).getId(),new ArrayList<Goods>());
        }
        for(Goods goodsBean:goodsList){
            categoryTreeMap.get(goodsBean.getCategoryId()).add(goodsBean);
        }
        //category wrapper
        for(int i=0;i<categoryList.size();i++){
            categoryWrapperList.add(new CategoryWrapper(categoryList.get(i)));
        }
        categoryWrapperList.get(0).setSelected(true);
    }
}
