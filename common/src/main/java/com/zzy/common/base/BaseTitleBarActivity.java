package com.zzy.common.base;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.zzy.common.R;
import com.zzy.common.utils.StatusBarUtils;
import com.zzy.common.widget.BackTitleBar;
import com.zzy.commonlib.base.BaseActivity;

/**
 * zzy
 */

abstract public class BaseTitleBarActivity extends BaseActivity{
    private BackTitleBar titleBar;
    private LinearLayout llRoot;
/********************************************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtils.setStatusBarFontIconDark(this);
        setContentView(R.layout.base_titlebar_container);
        titleBar = findViewById(R.id.titleBar);
        fixStatusHeight();
    }
    private void fixStatusHeight() {
        llRoot = findViewById(R.id.llRoot);
        int h = StatusBarUtils.getStatusBarHeight(this);
        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) llRoot.getLayoutParams();
        lp.setMargins(0, h, 0, 0);
        llRoot.setLayoutParams(lp);
    }

    protected void setTitle(String title){
        if(titleBar!=null)
            titleBar.setTitle(title);
    }
    protected void setOnBackEventListener(View.OnClickListener listener){
        if(titleBar!=null)
            titleBar.setOnBackEventListener(listener);
    }
    protected ViewGroup getContainer(){
        return findViewById(R.id.container);
    }
}
