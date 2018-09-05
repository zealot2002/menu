package com.zzy.home.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zzy.common.constants.RouterConstants;
import com.zzy.common.utils.CommonUtils;
import com.zzy.home.R;

/**
 * @author dell-7020
 * @Description:
 * @date 2018/08/27 14:27:03
 */

@Route(path = RouterConstants.SEARCH)
public class SearchActivity extends AppCompatActivity{

    private SearchFragment fragment;
/****************************************************************************************************/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CommonUtils.statusBarHide(this);
        setContentView(R.layout.home_search);
        fragment = new SearchFragment();
        showFragment(fragment);
    }

    private void showFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment).commit();
    }

    @Override
    public void onBackPressed() {
        fragment.onActivityFinishing();
        super.onBackPressed();
    }
}