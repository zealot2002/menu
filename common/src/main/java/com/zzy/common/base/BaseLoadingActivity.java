package com.zzy.common.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.view.View;

import com.zzy.common.R;
import com.zzy.common.widget.LoadingHelper;
import com.zzy.commonlib.base.BaseActivity;
import com.zzy.commonlib.base.BaseLoadingView;

/**
 * zzy
 */

abstract public class BaseLoadingActivity extends BaseActivity implements BaseLoadingView {
    private LoadingHelper loadingDialog;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        loadingDialog = new LoadingHelper(context);
    }

    public void showLoading(String s) {
        if(loadingDialog!=null){
            loadingDialog.showLoading(s);
        }
    }

    @Override
    public void showLoading() {
        showLoading("请稍候");
    }

    public void closeLoading() {
        if(loadingDialog!=null){
            loadingDialog.closeLoading();
        }
    }

    @Override
    public void showDisconnect() {
        setContentView(R.layout.disconnect);
        findViewById(R.id.btnReload).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reload(false);
            }
        });
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
        setContentView(getLayoutId());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        closeLoading();
        loadingDialog = null;
    }
    protected abstract int getLayoutId();
}
