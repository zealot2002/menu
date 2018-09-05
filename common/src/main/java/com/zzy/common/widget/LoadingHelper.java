package com.zzy.common.widget;

import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhy.autolayout.utils.AutoUtils;
import com.zzy.common.R;

/**
 * zzy
 */

public class LoadingHelper {
    private Dialog dialog;
    private TextView tvMessage;
    private ImageView ivIcon;
    private Animation rotateAnim;
/**************************************************************************************************/
    public LoadingHelper(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_loading, null);
        ivIcon = view.findViewById(R.id.img);
        tvMessage = view.findViewById(R.id.txt);

        dialog = new Dialog(context, R.style.dialog);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(view, new LinearLayout.LayoutParams(AutoUtils.getPercentWidthSize(360),
                AutoUtils.getPercentHeightSize(240)));
        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setAttributes(lp);

        rotateAnim = makeRotateAnim();
    }

    public void showLoading(String s){
        if(dialog!=null){
            tvMessage.setText(s);
            ivIcon.setAnimation(rotateAnim);
            rotateAnim.start();
            dialog.show();
        }
    }

    public void closeLoading(){
        if(dialog!=null){
            dialog.dismiss();
        }
        if(rotateAnim!=null){
            rotateAnim.cancel();
        }
    }

    public Animation makeRotateAnim() {
        Animation anim =new RotateAnimation(0f, 360f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setFillAfter(true); // 设置保持动画最后的状态
        anim.setRepeatCount(ValueAnimator.INFINITE);// 无限循环
        anim.setDuration(3000); // 设置动画时间
        anim.setInterpolator(new AccelerateInterpolator()); // 设置插入器
        return anim;
    }
}
