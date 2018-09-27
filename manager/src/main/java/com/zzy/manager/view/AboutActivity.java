package com.zzy.manager.view;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.vincent.filepicker.Constant;
import com.vincent.filepicker.activity.ImagePickActivity;
import com.vincent.filepicker.filter.entity.ImageFile;
import com.zzy.common.base.BaseTitleBarActivity;
import com.zzy.common.constants.BusConstants;
import com.zzy.common.constants.CommonConstants;
import com.zzy.common.constants.ParamConstants;
import com.zzy.common.constants.RouterConstants;
import com.zzy.common.utils.ApplicationUtils;
import com.zzy.common.utils.CommonUtils;
import com.zzy.common.utils.ImageLoaderUtils;
import com.zzy.common.utils.MyToast;
import com.zzy.common.widget.RoundImageView;
import com.zzy.commonlib.core.BusHelper;
import com.zzy.commonlib.utils.PackageUtils;
import com.zzy.manager.R;
import com.zzy.storehouse.StoreProxy;
import com.zzy.storehouse.model.Category;
import com.zzy.storehouse.model.Goods;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;

import static com.vincent.filepicker.activity.ImagePickActivity.IS_NEED_CAMERA;

/**
 * @author zzy
 * @date 2018/9/20
 */
@Route(path = RouterConstants.MANAGER_ABOUT)
public class AboutActivity extends BaseTitleBarActivity implements View.OnClickListener{
    private TextView tvVersion;
    private Button btnGood,btnShare;

    /***************************************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("关于我们");
        setOnBackEventListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        initData();
        setupViews();
    }

    private void initData() {

    }

    private void setupViews() {
        View contentView = View.inflate(this,R.layout.manager_about,null);
        getContainer().addView(contentView);


        tvVersion = findViewById(R.id.tvVersion);
        tvVersion.setText("版本: "+ CommonUtils.getVersionName(this));

        btnGood = findViewById(R.id.btnGood);
        btnShare = findViewById(R.id.btnShare);

        btnGood.setOnClickListener(this);
        btnShare.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnGood){
            Uri uri = Uri.parse("market://details?id=" + getPackageName());
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }else if(v.getId() == R.id.btnShare){
            Toast.makeText(this, "分享！~", Toast.LENGTH_SHORT).show();
        }
    }
}
