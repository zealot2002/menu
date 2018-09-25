package com.zzy.common.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.view.View;

/**
 * @author zzy
 * @date 2018/9/18
 */

public class StatusBarUtils {
    /**
     * 设置Android状态栏的字体颜色，状态栏为亮色的时候字体和图标是黑色，状态栏为暗色的时候字体和图标为白色
     *
     */
    public static void setStatusBarFontIconDark(@NonNull Activity activity) {
        //设置状态栏文字为暗色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //6.0以上可以通过直接设置SYSTEM_UI_FLAG_LIGHT_STATUS_BAR属性即可。
            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().setStatusBarColor(Color.GRAY);  //21以上不支持6.0直接设置的方法，可用灰色代替，具体可自己设置
            //getWindow().setStatusBarColor(Color.parseColor("#40000000"));
        } else  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){
            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);//4.4版本本身就含有暗色阴影，不作其他处理即可
        }
    }

    public static int getStatusBarHeight(Context context){
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen","android");
        int height = resources.getDimensionPixelSize(resourceId);
        return height;
    }
}
