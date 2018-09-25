package com.zzy.common.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zzy.common.R;


public class BackTitleBar extends RelativeLayout {
/**************************************************************************************************************/
    public BackTitleBar(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }
    public BackTitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View.inflate(context, R.layout.back_titlebar, this);
    }
    public BackTitleBar(Context context) {
        this(context,null);
    }
    public void setTitle(@NonNull String s) {
        ((TextView)findViewById(R.id.tvTitle)).setText(s);
    }
    public void setOnBackEventListener(OnClickListener listener){
        findViewById(R.id.llLeft).setOnClickListener(listener);
    }
}
