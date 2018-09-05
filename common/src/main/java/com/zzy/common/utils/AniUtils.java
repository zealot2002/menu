package com.zzy.common.utils;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

/**
 * @author zzy
 * @date 2018/9/5
 */

public class AniUtils {
    public static void startShowAnimation(final View view, int duration) {
        if(null != view && duration >= 0) {
            view.setVisibility(View.VISIBLE);
            AlphaAnimation ani = new AlphaAnimation(0.0F, 1.0F);
            ani.setDuration((long)duration);
            ani.setAnimationListener(new Animation.AnimationListener() {
                public void onAnimationStart(Animation animation) {
                }

                public void onAnimationEnd(Animation animation) {
                    view.clearAnimation();
                    view.setVisibility(View.GONE);
                }

                public void onAnimationRepeat(Animation animation) {
                }
            });
            view.startAnimation(ani);
        }
    }
}
