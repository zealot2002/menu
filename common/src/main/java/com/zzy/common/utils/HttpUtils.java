package com.zzy.common.utils;

import com.zzy.common.constants.HttpConstants;
import com.zzy.commonlib.http.HInterface;
import com.zzy.commonlib.http.HProxy;
import com.zzy.commonlib.http.RequestCtx;
import com.zzy.commonlib.log.MyLog;

import static com.zzy.commonlib.http.HConstant.HTTP_METHOD_GET;

/**
 * @author zzy
 * @date 2018/7/31
 */

public class HttpUtils {
    private static final String TAG = "HttpUtils";
    public static HttpUtils getInstance() {
        return LazyHolder.ourInstance;
    }

    private static class LazyHolder {
        private static final HttpUtils ourInstance = new HttpUtils();
    }

    private HttpUtils() {
    }


    public String getServer() {
        return HttpConstants.SERVER_URL;
    }


//    public void req(String actionName, JSONObject innerBody, final HInterface.DataCallback callback, final HInterface.JsonParser jsonParser) throws Exception {
////            JSONObject body = wrapBody(innerBody,actionName);
//            RequestCtx ctx = new RequestCtx.Builder(getServer())
//                    .method(HTTP_METHOD_POST)
//                    .body(encryptBody)
//                    .callback(callback)
//                    .jsonParser(jsonParser)
//                    .timerout(30)
//                    .build();
//            MyLog.e(TAG,"请求服务 url:"+ctx.getUrl());
//            MyLog.e(TAG,"请求服务 body:"+body.toString());
//            HProxy.getInstance().request(ctx);
//    }
    public void get(String actionName,final HInterface.DataCallback callback, final HInterface.JsonParser jsonParser) throws Exception {
        RequestCtx ctx = new RequestCtx.Builder(getServer()+"/"+actionName)
                .method(HTTP_METHOD_GET)
                .callback(callback)
                .jsonParser(jsonParser)
                .timerout(30)
                .build();
        MyLog.e(TAG,"请求服务get url:"+ctx.getUrl());
        HProxy.getInstance().request(ctx);
    }
}
