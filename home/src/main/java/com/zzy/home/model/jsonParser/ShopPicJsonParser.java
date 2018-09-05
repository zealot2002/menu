package com.zzy.home.model.jsonParser;

import com.zzy.common.constants.HttpConstants;
import com.zzy.commonlib.http.HInterface;
import com.zzy.commonlib.log.MyLog;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;


/**
 * @author zzy
 * @date 2018/8/27
 */

public class ShopPicJsonParser implements HInterface.JsonParser {
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

            String picUrl = dataObj.getString("url");
            return new Object[]{HttpConstants.SUCCESS,picUrl};
        } else {
            String msg = obj.getString(HttpConstants.ERROR_MESSAGE);
            return new Object[]{HttpConstants.FAIL, msg};
        }
    }
}
