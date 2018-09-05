package com.zzy.common.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zzy.common.R;


public class TitleBar extends RelativeLayout {
/**************************************************************************************************************/
    public TitleBar(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View.inflate(context, R.layout.applib_title_bar, this);
    }


    public TitleBar(Context context) {
        this(context,null);
    }

    public void setTitleBarBackgroundRes(int resId) {
        findViewById(R.id.rl_titlebar_root).setBackgroundResource(resId);
    }
    public void setLeftVisibility(boolean b){
        findViewById(R.id.llLeft).setVisibility(b?View.VISIBLE:View.GONE);
    }
    public void setTitleVisibility(boolean b){
        findViewById(R.id.tvTitle).setVisibility(b?View.VISIBLE:View.GONE);
    }
    public void setRightVisibility(boolean b){
        findViewById(R.id.tvRight).setVisibility(b?View.VISIBLE:View.GONE);
    }
    public void setLeftImageResid(int resId) {
        ((ImageView)findViewById(R.id.ivLeft)).setImageResource(resId);
    }
    public void setLeftText(String text) {
        ((TextView)findViewById(R.id.tvLeft)).setText(text);
    }
    public void setTitle(@NonNull String s, int color) {
        ((TextView)findViewById(R.id.tvTitle)).setText(s);
        ((TextView)findViewById(R.id.tvTitle)).setTextColor(color);
    }
    public void setTitle(@NonNull String s) {
        ((TextView)findViewById(R.id.tvTitle)).setText(s);
    }
    public void setRight(@NonNull String s, int color) {
        ((TextView)findViewById(R.id.tvRight)).setText(s);
        ((TextView)findViewById(R.id.tvRight)).setTextColor(getResources().getColor(color));
    }
    public void setLeftOnClickedListener(OnClickListener listener){
        findViewById(R.id.llLeft).setOnClickListener(listener);
    }
    public void setRightOnClickedListener(OnClickListener listener){
        findViewById(R.id.tvRight).setOnClickListener(listener);
    }
}
