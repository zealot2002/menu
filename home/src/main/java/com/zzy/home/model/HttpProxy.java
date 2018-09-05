package com.zzy.home.model;

import com.zzy.common.constants.HttpConstants;
import com.zzy.common.utils.HttpUtils;
import com.zzy.commonlib.http.HInterface;
import com.zzy.home.model.jsonParser.CategoryListJsonParser;
import com.zzy.home.model.jsonParser.GoodsListJsonParser;
import com.zzy.home.model.jsonParser.ShopPicJsonParser;

/**
 * @author dell-7020
 * @Description:
 * @date 2018/08/15 11:06:20
 */

public class HttpProxy {

    private static final String TAG = "HttpProxy";
/****************************************************************************************************/

    public static void getCategoryList(final HInterface.DataCallback callback){
        try {
            HttpUtils.getInstance().get(HttpConstants.GET_CATEGORY_LIST,callback,new CategoryListJsonParser());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void getShopPic(final HInterface.DataCallback callback){
        try {
            HttpUtils.getInstance().get(HttpConstants.GET_SHOP_PIC,callback,new ShopPicJsonParser());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void getGoodsList(final HInterface.DataCallback callback){
        try {
            HttpUtils.getInstance().get(HttpConstants.GET_GOODS_LIST,callback,new GoodsListJsonParser());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}