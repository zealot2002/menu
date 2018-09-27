package com.zzy.manager.view;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.zzy.common.base.BaseTitleBarActivity;
import com.zzy.common.constants.RouterConstants;
import com.zzy.manager.R;

/**
 * @author zzy
 * @date 2018/9/20
 */
@Route(path = RouterConstants.MANAGER_SETTINGS)
public class SettingsActivity extends BaseTitleBarActivity implements View.OnClickListener {
    private RelativeLayout rlTodayOrder,rlHistoryOrder,rlContent,rlAbout;

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

        rlTodayOrder = findViewById(R.id.rlTodayOrder);
        rlHistoryOrder = findViewById(R.id.rlHistoryOrder);
        rlContent = findViewById(R.id.rlContent);
        rlAbout = findViewById(R.id.rlAbout);

        rlTodayOrder.setOnClickListener(this);
        rlHistoryOrder.setOnClickListener(this);
        rlContent.setOnClickListener(this);
        rlAbout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.rlTodayOrder){
            ARouter.getInstance().build(RouterConstants.ORDER_TODAY).navigation();
        }else if(v.getId() == R.id.rlHistoryOrder){
            ARouter.getInstance().build(RouterConstants.ORDER_HISTORY).navigation();
        }else if(v.getId() == R.id.rlContent){
            ARouter.getInstance().build(RouterConstants.MANAGER_CONTENT).navigation();
        }else if(v.getId() == R.id.rlAbout){
            ARouter.getInstance().build(RouterConstants.MANAGER_ABOUT).navigation();
        }

    }
}
