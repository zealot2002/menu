package com.zzy.welcome;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.alibaba.android.arouter.launcher.ARouter;
import com.zzy.common.constants.RouterConstants;
import com.zzy.commonlib.log.MyLog;

public class WelcomeActivity extends AppCompatActivity {

    private final int SHOW_TIME = 3000; //开屏页时间
    private static Handler handler;
    /*******************************************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_main);
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                entryMain();
            }

        }, SHOW_TIME);
    }

    private void entryMain() {
        try {
            ARouter.getInstance().build(RouterConstants.HOME_MAIN).navigation();
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
