package com.zzy.order.view;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zzy.common.base.BaseTitleBarActivity;
import com.zzy.common.constants.RouterConstants;
import com.zzy.common.utils.DateUtils;
import com.zzy.order.R;
import com.zzy.order.view.inner.OrderListAdapter;
import com.zzy.storehouse.StoreProxy;
import com.zzy.storehouse.model.Order;

import java.text.ParseException;
import java.util.Calendar;
import java.util.List;

/**
 * @author zzy
 * @date 2018/9/20
 */
@Route(path = RouterConstants.ORDER_TODAY)
public class OrderTodayActivity extends BaseTitleBarActivity {


    private View contentView;
    private List<Order> orderList;
    private RecyclerView rvOrder;
    private OrderListAdapter adapter;
    private TextView tvNum,tvTotal;
/***************************************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("今日订单");
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
        initData();
    }

    private void initData() {
        Calendar calendar = Calendar.getInstance();
        try {
            long startTime = DateUtils.getStartTime(calendar);
            long endTime = DateUtils.getEndTime(calendar);
            orderList = StoreProxy.getInstance().getOrderList(startTime,endTime,0,1000);
            updateViews();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    private void updateViews() {
        if(contentView == null){
            contentView = View.inflate(this, R.layout.order_today_activity,null);
            getContainer().addView(contentView);
            tvTotal = findViewById(R.id.tvTotal);
            tvNum = findViewById(R.id.tvNum);

            setupRecyclerView();
        }
        updateContentList();
    }

    private void updateTotal() {
        float sum = 0f;
        for(Order order:orderList){
            sum+=order.getPrice();
        }
        tvTotal.setText("今日营业额: "+sum+"元");
        tvNum.setText("共"+orderList.size()+"条");
    }

    private void setupRecyclerView() {
        rvOrder = findViewById(R.id.rvOrder);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvOrder.setLayoutManager(layoutManager);
        rvOrder.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

        adapter = new OrderListAdapter();
    }


    private void updateContentList(){
        rvOrder.setAdapter(adapter);
        adapter.swapData(orderList);
        updateTotal();
    }
}
