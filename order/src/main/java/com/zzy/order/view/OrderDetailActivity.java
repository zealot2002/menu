package com.zzy.order.view;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zzy.common.base.BaseTitleBarActivity;
import com.zzy.common.constants.ParamConstants;
import com.zzy.common.constants.RouterConstants;
import com.zzy.common.widget.shoppingCart.GoodsWrapperBean;
import com.zzy.order.R;
import com.zzy.storehouse.StoreProxy;
import com.zzy.storehouse.model.Order;

import java.util.List;

/**
 * @author zzy
 * @date 2018/9/20
 */
@Route(path = RouterConstants.ORDER_DETAIL)
public class OrderDetailActivity extends BaseTitleBarActivity implements View.OnClickListener {
    private View contentView;
    private Order order;
    private TextView tvTotal,tvId;
    private EditText etDesk,etRemark;
    private RecyclerView rvGoodsList;
    private GoodsListAdapter goodsListAdapter;
    private Button btnOk;
    private List<GoodsWrapperBean> goodsList;
    private Spinner spinnerState;
/***************************************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("订单详情");
        setOnBackEventListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        try{
            initData();
            updateViews();
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    private void initData() {
        order = (Order) getIntent().getSerializableExtra(ParamConstants.PARAM_DATA);
        goodsList = new Gson().fromJson(order.getCartInfo(),new TypeToken<List<GoodsWrapperBean>>(){}.getType());
        if(goodsList.isEmpty()){
            throw new RuntimeException("goodsList is empty");
        }
    }

    private void updateViews() {
        if(contentView == null){
            contentView = View.inflate(this, R.layout.order_detail_activity,null);
            getContainer().addView(contentView);
        }
        setupGoodList();
        setupOthers();
        btnOk.requestFocus();
    }
    private void setupOthers() {
        tvTotal = findViewById(R.id.tvTotal);
        etDesk = findViewById(R.id.etDesk);
        etRemark = findViewById(R.id.etRemark);
        tvId = findViewById(R.id.tvId);
        btnOk = findViewById(R.id.btnOk);
        btnOk.setOnClickListener(this);

        tvId.setText("订单号: "+order.getId());
        tvTotal.setText("总计: "+order.getPrice()+"元");
        etDesk.setText(order.getDeskNum());
        etRemark.setText(order.getRemarks());

        spinnerState = findViewById(R.id.spinnerState);
        String[] list2 = new String[]{"待处理","进行中","已完成"};
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, list2);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerState.setAdapter(adapter);
        spinnerState.setSelection(order.getState()-1);
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

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnOk){
            order.setState(spinnerState.getSelectedItemPosition()+1);
            StoreProxy.getInstance().updateOrder(order);
            etDesk.postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();
                }
            },500);
        }
    }

}
