package com.zzy.order.view;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zzy.common.base.BaseTitleBarActivity;
import com.zzy.common.constants.RouterConstants;
import com.zzy.common.utils.DateUtils;
import com.zzy.order.R;
import com.zzy.order.view.inner.MyMultiRecycleAdapter;
import com.zzy.order.view.inner.OrderListRender;
import com.zzy.order.view.inner.recycleAdapter.OnLoadMoreListener;
import com.zzy.storehouse.StoreProxy;
import com.zzy.storehouse.model.Order;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.aigestudio.datepicker.cons.DPMode;
import cn.aigestudio.datepicker.views.DatePicker;

/**
 * @author zzy
 * @date 2018/9/20
 */
@Route(path = RouterConstants.ORDER_HISTORY)
public class OrderHistoryActivity extends BaseTitleBarActivity {
    private View contentView;
    private List<Order> orderList;
    private RecyclerView rvOrder;
    private MyMultiRecycleAdapter adapter;
    private TextView tvStart,tvEnd,tvTotal;
    private Long startTime,endTime;
    private boolean isLoadOver = false;
    private int pageNum = 0;
    /***************************************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("历史订单");
        setOnBackEventListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setRightText("导出");
        setOnRightListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        try {
            initData();
            updateViews();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    private void initData() throws Exception{
        Calendar calendar = Calendar.getInstance();
        startTime = DateUtils.getStartTime(calendar);
        endTime = DateUtils.getEndTime(calendar);
    }

    private void updateViews() {
        if(contentView == null){
            contentView = View.inflate(this, R.layout.order_history_activity,null);
            getContainer().addView(contentView);
        }
        setupDate();
        updateOrderList();
    }

    private void setupDate() {
        tvStart = findViewById(R.id.tvStart);
        tvEnd = findViewById(R.id.tvEnd);
        tvTotal = findViewById(R.id.tvTotal);

        tvStart.setText(DateUtils.dateToString(new Date(startTime)));
        tvEnd.setText(DateUtils.dateToString(new Date(endTime)));

        tvStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog dialog = new AlertDialog.Builder(OrderHistoryActivity.this).create();
                dialog.show();
                DatePicker picker = new DatePicker(OrderHistoryActivity.this);
                picker.setDate(DateUtils.getYearOfDate(new Date(startTime)),DateUtils.getMonthOfDate(new Date(startTime)));
                picker.setMode(DPMode.SINGLE);
                picker.setOnDatePickedListener(new DatePicker.OnDatePickedListener() {
                    @Override
                    public void onDatePicked(String date) {
                        try {
                            updateStartDate(DateUtils.formatDate(date));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        dialog.dismiss();
                    }
                });
                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setContentView(picker, params);
                dialog.getWindow().setGravity(Gravity.CENTER);
            }
        });
        tvEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog dialog = new AlertDialog.Builder(OrderHistoryActivity.this).create();
                dialog.show();
                DatePicker picker = new DatePicker(OrderHistoryActivity.this);
                picker.setDate(DateUtils.getYearOfDate(new Date(endTime)),DateUtils.getMonthOfDate(new Date(endTime)));
                picker.setMode(DPMode.SINGLE);
                picker.setOnDatePickedListener(new DatePicker.OnDatePickedListener() {
                    @Override
                    public void onDatePicked(String date) {
                        try {
                            updateEndDate(DateUtils.formatDate(date));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        dialog.dismiss();
                    }
                });
                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setContentView(picker, params);
                dialog.getWindow().setGravity(Gravity.CENTER);
            }
        });
    }

    private void updateStartDate(String str) {
        try {
            calStartTime(str);
            tvStart.setText(DateUtils.dateToString(new Date(startTime)));
            updateOrderList();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private void updateEndDate(String str) {
        try {
            calEndTime(str);
            tvEnd.setText(DateUtils.dateToString(new Date(endTime)));
            updateOrderList();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private void calStartTime(String str) throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR,Integer.valueOf(str.substring(0,4)));
        calendar.set(Calendar.MONTH,Integer.valueOf(str.substring(5,7))-1);
        calendar.set(Calendar.DATE,Integer.valueOf(str.substring(8,10)));
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        long time = calendar.getTimeInMillis();
        if(time>endTime){
            Toast.makeText(this, "结束日期不能小于开始日期", Toast.LENGTH_SHORT).show();
            throw new Exception("结束日期不能小于开始日期");
        }
        startTime = calendar.getTimeInMillis();
    }
    private void calEndTime(String str) throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR,Integer.valueOf(str.substring(0,4)));
        calendar.set(Calendar.MONTH,Integer.valueOf(str.substring(5,7))-1);
        calendar.set(Calendar.DATE,Integer.valueOf(str.substring(8,10)));
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);

        long time = calendar.getTimeInMillis();
        if(time<startTime){
            Toast.makeText(this, "结束日期不能小于开始日期", Toast.LENGTH_SHORT).show();
            throw new Exception("结束日期不能小于开始日期");
        }
        endTime = time;
    }

    private void setupRecyclerView() {
        rvOrder = findViewById(R.id.rvOrder);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvOrder.setLayoutManager(layoutManager);
        rvOrder.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

        adapter = new MyMultiRecycleAdapter(this,orderList,true);
        adapter.setLoadingView(R.layout.elf_list_loading);
        /*设置加载失败布局*/
        adapter.setLoadFailedView(R.layout.elf_list_load_failed);
        /*设置加载完成布局*/
        adapter.setLoadEndView(R.layout.elf_list_load_end);

        //设置不满一屏幕，自动加载第二页
        adapter.openAutoLoadMore();
        //加载更多的事件监听
        adapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(boolean isReload) {
                if(isLoadOver){
                    return;
                }
                ++pageNum;
                appendOrderList();
            }
        });

        adapter.addItemViewDelegate(new OrderListRender());
        rvOrder.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        updateTotal();
    }

    private void updateTotal() {
        float sum = 0f;
        for(Order order:orderList){
            sum+=order.getPrice();
        }
        tvTotal.setText("总营业额: "+sum+"元");
    }

    private void appendOrderList() {
        final List<Order> list = StoreProxy.getInstance().getOrderList(startTime,endTime,pageNum,10);
        if(list.isEmpty()){
            isLoadOver = true;
            adapter.loadEnd();
            return;
        }

        rvOrder.postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter.setLoadMoreData(list);
                orderList.addAll(list);
                updateTotal();
            }
        },10);
    }


    private void updateOrderList(){
        pageNum = 0;
        isLoadOver = false;
        orderList = StoreProxy.getInstance().getOrderList(startTime,endTime,pageNum,10);
        setupRecyclerView();
    }
}
