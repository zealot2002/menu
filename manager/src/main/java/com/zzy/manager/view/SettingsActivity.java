package com.zzy.manager.view;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.zzy.common.base.BaseTitleBarActivity;
import com.zzy.common.constants.CommonConstants;
import com.zzy.common.constants.ParamConstants;
import com.zzy.common.constants.RouterConstants;
import com.zzy.common.utils.ApplicationUtils;
import com.zzy.common.utils.ImageLoaderUtils;
import com.zzy.common.utils.MyToast;
import com.zzy.common.widget.RoundImageView;
import com.zzy.manager.R;
import com.zzy.storehouse.StoreProxy;
import com.zzy.storehouse.model.Category;
import com.zzy.storehouse.model.Goods;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zzy
 * @date 2018/9/20
 */
@Route(path = RouterConstants.MANAGER_SETTINGS)
public class SettingsActivity extends BaseTitleBarActivity implements View.OnClickListener {
    private RelativeLayout rlContent,rlOrder,rlAbout;

/***************************************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("设置");
        setOnBackEventListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        setupViews();
    }


    private void setupViews() {
        View contentView = View.inflate(this,R.layout.manager_settings,null);
        getContainer().addView(contentView);

        rlContent = findViewById(R.id.rlContent);
        rlOrder = findViewById(R.id.rlOrder);
        rlAbout = findViewById(R.id.rlAbout);

        rlContent.setOnClickListener(this);
        rlOrder.setOnClickListener(this);
        rlAbout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.rlContent){
            ARouter.getInstance().build(RouterConstants.MANAGER_CONTENT).navigation();
        }else if(v.getId() == R.id.rlOrder){
            ARouter.getInstance().build(RouterConstants.MANAGER_ORDER).navigation();
        }else if(v.getId() == R.id.rlAbout){
            ARouter.getInstance().build(RouterConstants.MANAGER_ABOUT).navigation();
        }

    }
}
