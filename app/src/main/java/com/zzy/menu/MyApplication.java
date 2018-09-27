package com.zzy.menu;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;
import android.support.multidex.MultiDex;

import com.alibaba.android.arouter.launcher.ARouter;
import com.zhy.autolayout.config.AutoLayoutConifg;
import com.zhy.autolayout.utils.AutoUtils;
import com.zzy.common.utils.ApplicationUtils;
import com.zzy.hotfix.HotfixAdapter;
import com.zzy.storehouse.StoreProxy;

/**
 * @author zzy
 * @date 2018/8/27
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        setStrictMode();
        ARouter.init(this);
        AutoLayoutConifg.getInstance().useDeviceSize().init(this);
        ApplicationUtils.init(this);
        StoreProxy.getInstance().init(this);
        HotfixAdapter.init(this,false);


    }
    @Override
    protected void attachBaseContext(Context base) {//兼容5.0以下系统
        super.attachBaseContext(base);
        MultiDex.install(base);
        HotfixAdapter.install();
    }

    private static void setStrictMode() {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
    }
}
