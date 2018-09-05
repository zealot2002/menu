package com.zzy.order.view;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zzy.common.base.BaseLoadingActivity;
import com.zzy.common.constants.BusConstants;
import com.zzy.common.constants.ParamConstants;
import com.zzy.common.constants.RouterConstants;
import com.zzy.common.utils.CommonUtils;
import com.zzy.common.widget.TitleBar;
import com.zzy.common.widget.shoppingCart.GoodsWrapperBean;
import com.zzy.commonlib.core.BusHelper;
import com.zzy.commonlib.utils.TextViewUtil;
import com.zzy.order.R;
import com.zzy.order.contract.OrderConfirmContract;
import com.zzy.order.presenter.OrderConfirmPresenter;

import java.util.List;

/**
 * @author dell-7020
 * @Description:
 * @date 2018/08/31 10:37:22
 */

@Route(path = RouterConstants.ORDER_CONFIRM)
public class OrderConfirmActivity extends BaseLoadingActivity implements OrderConfirmContract.View, View.OnClickListener {
    private OrderConfirmContract.Presenter presenter;

    private TitleBar titleBar;
    private TextView tvTotal,tvDeskT,tvTips;
    private EditText etDesk,etRemark;
    private RecyclerView rvGoodsList;
    private GoodsListAdapter goodsListAdapter;
    private Button btnOk;
    private List<GoodsWrapperBean> goodsList;
/****************************************************************************************************/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CommonUtils.statusBarHide(this);
        presenter = new OrderConfirmPresenter(this);
        goodsList = (List<GoodsWrapperBean>) getIntent().getSerializableExtra(ParamConstants.PARAM_DATA);
        updateUI(null);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.order_confirm_activity;
    }

    @Override
    public void updateUI(Object o) {
        super.updateUI(o);
        setupViews();
    }

    private void setupViews() {
        setupTitle();
        setupGoodList();
        setupOthers();
    }

    private void setupOthers() {
        tvTotal = findViewById(R.id.tvTotal);
        etDesk = findViewById(R.id.etDesk);
        etRemark = findViewById(R.id.etRemark);
        tvTips = findViewById(R.id.tvTips);
        tvDeskT = findViewById(R.id.tvDeskT);
        btnOk = findViewById(R.id.btnOk);
        btnOk.setOnClickListener(this);

        tvTotal.setText("总计: "+calTotal()+"元");
        String tips = "桌号(必填)";
        tvDeskT.setTextColor(getResources().getColor(R.color.text_black));
        TextViewUtil.setSpecialTextColor(tvDeskT,tips,
                getResources().getColor(R.color.text_black),
                getResources().getColor(R.color.red),
                3,5);
    }

    private String calTotal() {
        int total = 0;
        for(GoodsWrapperBean bean:goodsList){
            total+=bean.getGoodsBean().getPrice()*bean.getNum();
        }
        return total+"";
    }

    private void setupGoodList() {
        if(rvGoodsList == null){
            rvGoodsList = findViewById(R.id.rvGoodsList);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,
                    LinearLayoutManager.VERTICAL,false);
            rvGoodsList.setLayoutManager(layoutManager);
            rvGoodsList.setFocusable(false);
        }
        goodsListAdapter = new GoodsListAdapter();
        rvGoodsList.setAdapter(goodsListAdapter);
        goodsListAdapter.swapData(goodsList);
    }

    private void setupTitle() {
        titleBar = findViewById(R.id.titleBar);
        titleBar.setTitle("确认订单");
        titleBar.setLeftOnClickedListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnOk){
            if(etDesk.getText().toString().trim().isEmpty()){
                Toast.makeText(this, "桌号不能为空", Toast.LENGTH_SHORT).show();
                return;
            }
            Toast.makeText(this, "提交成功", Toast.LENGTH_SHORT).show();
            titleBar.postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();
                    BusHelper.getBus().post(BusConstants.EVENT_ORDER_SUCCESS,"1");
                }
            },2000);
        }
    }
}