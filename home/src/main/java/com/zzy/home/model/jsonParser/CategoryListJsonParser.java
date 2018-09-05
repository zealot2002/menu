package com.zzy.home.model.jsonParser;

import com.zzy.common.constants.HttpConstants;
import com.zzy.commonlib.http.HInterface;
import com.zzy.commonlib.log.MyLog;
import com.zzy.home.model.bean.CategoryBean;

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

public class CategoryListJsonParser implements HInterface.JsonParser {
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
            List<CategoryBean> dataList = new ArrayList<>();
            JSONArray array = dataObj.getJSONArray("categoryList");
            for(int j=0;j<array.length();j++) {
                JSONObject dishObj = array.getJSONObject(j);
                CategoryBean bean = new CategoryBean();
                bean.setId(dishObj.getInt("id"));
                bean.setName(dishObj.getString("name"));

                dataList.add(bean);
            }
            return new Object[]{HttpConstants.SUCCESS,dataList};
        } else {
            String msg = obj.getString(HttpConstants.ERROR_MESSAGE);
            return new Object[]{HttpConstants.FAIL, msg};
        }
    }
}
