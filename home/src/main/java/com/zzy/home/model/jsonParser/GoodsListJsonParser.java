package com.zzy.home.model.jsonParser;

import com.zzy.common.constants.HttpConstants;
import com.zzy.commonlib.http.HInterface;
import com.zzy.commonlib.log.MyLog;
import com.zzy.home.model.bean.CategoryBean;
import com.zzy.home.model.bean.GoodsBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zzy
 * @date 2018/8/27
 */

public class GoodsListJsonParser implements HInterface.JsonParser {
    @Override
    public Object[] parse(String s) throws JSONException {
        if(s==null){
            throw new JSONException("server return null");
        }
        JSONTokener jsonParser = new JSONTokener(s);
        JSONObject obj = (JSONObject) jsonParser.nextValue();
        int errorCode = obj.getInt(HttpConstants.ERROR_CODE);
        if (errorCode == HttpConstants.NO_ERROR) {
            JSONObject dataObj = obj.getJSONObject("data");

            List<GoodsBean> dataList = new ArrayList<>();
            JSONArray array = dataObj.getJSONArray("goodsList");
            for(int j=0;j<array.length();j++) {
                JSONObject itemObj = array.getJSONObject(j);
                GoodsBean bean = new GoodsBean();
                bean.setId(itemObj.getInt("id"));
                bean.setCategoryId(itemObj.getInt("categoryId"));
                bean.setName(itemObj.getString("name"));
                if(itemObj.has("desc")) bean.setDesc(itemObj.getString("desc"));
                bean.setPrice(itemObj.getString("price"));
                bean.setImageUri(itemObj.getString("imageUri"));
                if(itemObj.has("state")) bean.setState(itemObj.getInt("state"));

                dataList.add(bean);
            }
            return new Object[]{HttpConstants.SUCCESS,dataList};
        } else {
            String msg = obj.getString(HttpConstants.ERROR_MESSAGE);
            return new Object[]{HttpConstants.FAIL, msg};
        }
    }
}
