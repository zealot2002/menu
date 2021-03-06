package com.zzy.common.utils;

import android.app.ActionBar;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.view.View;
import android.view.WindowManager;

import java.io.File;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * @author zzy
 * @date 2018/9/3
 */

public class CommonUtils {
    /**
     * 设置Activity的statusBar隐藏
     * @param activity
     */
    public static void statusBarHide(Activity activity){
        try{
            // 代表 5.0 及以上
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                View decorView = activity.getWindow().getDecorView();
                int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
                decorView.setSystemUiVisibility(option);
                activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
                ActionBar actionBar = activity.getActionBar();
                if(actionBar!=null){
                    actionBar.hide();
                }
                return;
            }

            // versionCode > 4.4  and versionCode < 5.0
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 获取软件版本号
     * @return versionCode
     */
    public static String getVersionName(Context context) {
        String versionName = "1.0.1";
        try {
            versionName = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }

    public static String formatMoney(float money) throws Exception{
        NumberFormat nf = new DecimalFormat("#,###.##");
        String s = nf.format(money);
        String ret = s;
        if(!s.contains(".")){
            ret = s+".00";
        }else{
            int dotIndex = s.indexOf(".");
            if(dotIndex == s.length()-2){
                ret = s+"0";
            }
        }
        return ret;
    }
}
