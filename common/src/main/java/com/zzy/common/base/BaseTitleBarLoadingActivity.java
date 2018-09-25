package com.zzy.common.base;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.view.View;

import com.zzy.common.R;
import com.zzy.common.widget.LoadingHelper;
import com.zzy.commonlib.base.BaseLoadingView;

/**
 * zzy
 */

abstract public class BaseTitleBarLoadingActivity extends BaseTitleBarActivity implements BaseLoadingView {
    private LoadingHelper loadingDialog;
    private View disconnectView,contentView;
/********************************************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadingDialog = new LoadingHelper(this);
    }

    public void showLoading(String s) {
        if(loadingDialog!=null){
            loadingDialog.showLoading(s);
        }
    }

    @Override
    public void showLoading() {
        showLoading("");
    }

    public void closeLoading() {
        if(loadingDialog!=null){
            loadingDialog.closeLoading();
        }
    }

    @Override
    public void showDisconnect() {
        if(contentView!=null){
            getContainer().removeView(contentView);
        }
        if(disconnectView == null){
            disconnectView = View.inflate(this, R.layout.disconnect,null);
            disconnectView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    reload(false);
                }
            });
        }
        getContainer().addView(disconnectView);
    }

    @Override
    public void showLoadingError() {
        showDisconnect();
    }
    //子类复写此方法
    @Override
    public void reload(boolean b) {}

    //子类复写此方法

    @CallSuper
    @Override
    public void updateUI(Object o) {
        if(disconnectView!=null){
            getContainer().removeView(disconnectView);
        }
        if(contentView == null){
            contentView = View.inflate(this,getLayoutId(),null);
        }
        getContainer().addView(contentView);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        closeLoading();
        loadingDialog = null;
    }
    protected abstract int getLayoutId();
}
