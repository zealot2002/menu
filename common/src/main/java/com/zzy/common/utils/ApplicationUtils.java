package com.zzy.common.utils;

import android.app.Application;
import android.content.Context;

/**
 * @author zzy
 * @date 2018/8/18
 */

public class ApplicationUtils {
    public static Application application;
    public static Context get(){
        return application;
    }
    public static void init(Application app){
        application = app;
    }

}
