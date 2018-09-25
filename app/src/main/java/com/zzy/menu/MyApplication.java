package com.zzy.menu;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.zhy.autolayout.config.AutoLayoutConifg;
import com.zhy.autolayout.utils.AutoUtils;
import com.zzy.common.utils.ApplicationUtils;
import com.zzy.storehouse.StoreProxy;

/**
 * @author zzy
 * @date 2018/8/27
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ARouter.init(this);
        AutoLayoutConifg.getInstance().useDeviceSize().init(this);
        ApplicationUtils.init(this);
        StoreProxy.getInstance().init(this);
    }
}
