package com.zzy.common.widget.shoppingCart;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.zzy.common.R;
import com.zzy.common.utils.ApplicationUtils;
import com.zzy.common.utils.CommonUtils;
import com.zzy.common.utils.ImageLoaderUtils;
import com.zzy.common.widget.AddAndSubWidget;
import com.zzy.common.widget.RoundImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tt on 2016/6/27.
 */
public class ShoppingCartWidget extends LinearLayout implements View.OnClickListener{
    public interface SubmitListener {
        void onSubmit(List<GoodsWrapperBean> dataList);
    }
    private Context context;
    private LayoutInflater inflater;
    private LayoutParams layoutParams;
    private RelativeLayout main;

    private RelativeLayout rlShoppingCart;
    private RelativeLayout rlShoppingCartBody;

    private List<GoodsWrapperBean> goodsList;
    private ShoppingCartGoodsListAdapter adapter;
    private ListView lvGoodsList;

    private TextView tvPrice,tvHint;
    private RelativeLayout rlHint,rlSubmit;
    private LinearLayout llClean;

    private SubmitListener submitListener;
/******************************************************************************************************/
    public ShoppingCartWidget(Context context){
        this(context,null);
    }
    public ShoppingCartWidget(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShoppingCartWidget(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        inflater = LayoutInflater.from(context);
        main = (RelativeLayout)inflater.inflate(R.layout.shopping_cart_widget, null);
        layoutParams = new LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        addView(main, layoutParams);

        tvPrice = findViewById(R.id.tvPrice);
        tvPrice.setOnClickListener(this);

        rlHint = findViewById(R.id.rlHint);
        rlSubmit = findViewById(R.id.rlSubmit);
        rlSubmit.setOnClickListener(this);
        tvHint = findViewById(R.id.tvHint);

        rlShoppingCartBody = findViewById(R.id.rlShoppingCartBody);
        rlShoppingCartBody.setOnClickListener(this);
        rlShoppingCart = findViewById(R.id.rlShoppingCart);
        rlShoppingCart.setOnClickListener(this);

        llClean = findViewById(R.id.llClean);
        llClean.setOnClickListener(this);

        lvGoodsList = findViewById(R.id.lvGoodsList);
        goodsList = new ArrayList<>();
        adapter = new ShoppingCartGoodsListAdapter(goodsList);
        lvGoodsList.setAdapter(adapter);

    }


    public void setSubmitListener(SubmitListener submitListener){
        this.submitListener = submitListener;
    }
//增加一个商品
    public void addGoods(GoodsBean bean){
        boolean find = false;
        for(int i=0;i<goodsList.size();i++){
            if(goodsList.get(i).getGoodsBean().getId().equals(bean.getId())){
                find = true;
                goodsList.get(i).setNum(1+goodsList.get(i).getNum());
                break;
            }
        }
        if(!find){
            goodsList.add(new GoodsWrapperBean(bean,1));
        }
        updateShoppingCartUI();
    }

    //减少一个商品
    public void removeGoods(GoodsBean bean){
        boolean needUpdate = false;
        for(int i=0;i<goodsList.size();i++){
            if(goodsList.get(i).getGoodsBean().getId().equals(bean.getId())){
                int num = goodsList.get(i).getNum();
                if(--num>0){
                    goodsList.get(i).setNum(num);
                }else{
                    goodsList.remove(i);
                    if(goodsList.isEmpty()){
                        fold();
                        return;
                    }
                }
                needUpdate = true;
                break;
            }
        }
        if(needUpdate){
            updateShoppingCartUI();
        }
    }

//增加商品list
    public void replaceAllGoods(List<GoodsWrapperBean> goodsList){
        if(goodsList==null)
            return;
        this.goodsList.clear();
        this.goodsList.addAll(goodsList);
        updateShoppingCartUI();
    }

    /**
     * 折叠购物车
     */
    public void fold(){
        if(rlShoppingCartBody!=null){
            rlShoppingCartBody.setVisibility(View.GONE);
        }
        if(rlHint!=null){
            //show the hint
            rlHint.setVisibility(View.VISIBLE);
        }
        updateShoppingCartUI();
    }
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.tvPrice
                ||v.getId() == R.id.rlShoppingCart
                ){
            if(goodsList.size()==0){
                return;
            }
            if(rlShoppingCartBody.getVisibility()== View.VISIBLE){
                fold();
            }else{//打开购物车页面
                rlShoppingCartBody.setVisibility(View.VISIBLE);
                adapter.setDataList(goodsList);
                //dismiss the hint
                rlHint.setVisibility(View.GONE);
            }
        }else if(v.getId() == R.id.rlSubmit){
            if(submitListener !=null
                    &&!isGoodListEmpty()
                    ){
                submitListener.onSubmit(goodsList);
            }
        }else if(v.getId() == R.id.llClean){
            cleanShoppingCart();
//            AlertDialogUtils.onPopupButtonClick(context, "确认要清空购物车吗？", new AlertDialogUtils.doSomethingListener() {
//                @Override
//                public void doSomething() {
//                    cleanShoppingCart();
//                }
//            });
        }else if(v.getId() == R.id.rlShoppingCartBody){
            fold();
        }
    }

    private void updateShoppingCartUI(){
        float totalPrice=0;
        int totalProjectNum=0;
        for(GoodsWrapperBean bean:goodsList){
            totalPrice+=bean.getGoodsBean().getPrice()*bean.getNum();
            totalProjectNum+=bean.getNum();
        }
        try {
            tvPrice.setText(CommonUtils.formatMoney(totalPrice));
        } catch (Exception e) {
            e.printStackTrace();
        }
        //button
        if(totalProjectNum>0){
            tvHint.setText(totalProjectNum+"");
            if(rlShoppingCartBody.getVisibility()!=VISIBLE){
                rlHint.setVisibility(VISIBLE);
            }
            rlSubmit.setBackgroundResource(R.color.orange);
        }else{
            rlHint.setVisibility(GONE);
            rlSubmit.setBackgroundResource(R.color.text_gray);
        }
        adapter.setDataList(goodsList);
        adapter.notifyDataSetChanged();
    }

    public class ShoppingCartGoodsListAdapter extends BaseAdapter {
        private List<GoodsWrapperBean> list;
        public ShoppingCartGoodsListAdapter(List<GoodsWrapperBean> list) {
            this.list = list;
        }

        public void setDataList(List<GoodsWrapperBean> list){
            this.list = list;
            notifyDataSetChanged();
        }
        @Override
        public int getCount() {
            if (list == null) {
                return 0;
            }
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = View.inflate(context, R.layout.shopping_cart_goods_list_item, null);
                holder = new ViewHolder();
                holder.tvName = convertView.findViewById(R.id.tvName);
                holder.tvPrice = convertView.findViewById(R.id.tvPrice);
                holder.ivPic = convertView.findViewById(R.id.ivPic);

                holder.addAndSubWidget = convertView.findViewById(R.id.addAndSubWidget);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            try {
                GoodsWrapperBean bean = list.get(position);
                holder.tvName.setText(bean.getGoodsBean().getName());
                holder.tvPrice.setText(context.getResources().getString(R.string.symbol_rmb) + CommonUtils.formatMoney(bean.getGoodsBean().getPrice()) + " /份");
                holder.addAndSubWidget.setValue(bean.getNum());
                ImageLoaderUtils.getInstance().showImg(ApplicationUtils.get(),bean.getGoodsBean().getImgUri(), holder.ivPic);
                holder.addAndSubWidget.setListener(new AddAndSubWidget.EventListener() {
                    @Override
                    public void onAddEvent() {
                        addGoods(list.get(position).getGoodsBean());
                    }

                    @Override
                    public void onSubEvent() {
                        removeGoods(list.get(position).getGoodsBean());
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
            return convertView;
        }

        class ViewHolder {
            TextView tvName,tvPrice;
            AddAndSubWidget addAndSubWidget;
            RoundImageView ivPic;
        }
    }

    private boolean isGoodListEmpty() {
        boolean isEmpty = true;
        for(GoodsWrapperBean bean:goodsList){
            if(bean.getNum()>0)
                isEmpty = false;
        }
        return isEmpty;
    }
    public void cleanShoppingCart(){
        goodsList.clear();
        rlShoppingCartBody.setVisibility(View.GONE);
        updateShoppingCartUI();
    }
    public List<GoodsWrapperBean> getCurrentGoods(){
        /*筛选一下*/
        int i=0;
        while(i!=goodsList.size()){
            if(goodsList.get(i).getNum()==0){
                goodsList.remove(i);
            }else{
                i++;
            }
        }
        /**/
        return goodsList;
    }
}
