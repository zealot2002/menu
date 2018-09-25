package com.zzy.home.model;

import com.zzy.common.constants.HttpConstants;
import com.zzy.common.utils.ApplicationUtils;
import com.zzy.commonlib.http.HInterface;
import com.zzy.commonlib.utils.FileUtils;
import com.zzy.home.model.jsonParser.CategoryListJsonParser;
import com.zzy.home.model.jsonParser.GoodsListJsonParser;
import com.zzy.home.model.jsonParser.ShopPicJsonParser;

/**
 * @author dell-7020
 * @Description:
 * @date 2018/08/15 11:06:20
 */

public class LocalJsonProxy {

    private static final String TAG = "LocalJsonProxy";
/****************************************************************************************************/

    public static void getCategoryList(final HInterface.DataCallback callback){
        try{
            String data = FileUtils.readFileFromAssets(ApplicationUtils.get(),"categoryList.json");
            Object[] ret = new CategoryListJsonParser().parse(data);
            callback.requestCallback(HttpConstants.SUCCESS,ret[1],"");
        }catch(Exception e){
            e.printStackTrace();
            callback.requestCallback(HttpConstants.FAIL,e.toString(),"");
        }
    }
    public static void getShopPic(final HInterface.DataCallback callback){
        try{
            String data = FileUtils.readFileFromAssets(ApplicationUtils.get(),"shopPic.json");
            Object[] ret = new ShopPicJsonParser().parse(data);
            callback.requestCallback(HttpConstants.SUCCESS,ret[1],"");
        }catch(Exception e){
            e.printStackTrace();
            callback.requestCallback(HttpConstants.FAIL,e.toString(),"");
        }
    }
    public static void getGoodsList(final HInterface.DataCallback callback){
        try{
            String data = FileUtils.readFileFromAssets(ApplicationUtils.get(),"goodsList.json");
            Object[] ret = new GoodsListJsonParser().parse(data);
            callback.requestCallback(HttpConstants.SUCCESS,ret[1],"");
        }catch(Exception e){
            e.printStackTrace();
            callback.requestCallback(HttpConstants.FAIL,e.toString(),"");
        }
    }
}