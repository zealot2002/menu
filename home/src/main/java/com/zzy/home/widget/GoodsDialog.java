package com.zzy.home.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.zhy.autolayout.utils.AutoUtils;
import com.zzy.common.utils.ApplicationUtils;
import com.zzy.common.utils.CommonUtils;
import com.zzy.common.utils.ImageLoaderUtils;
import com.zzy.common.widget.RoundImageView;
import com.zzy.common.widget.shoppingCart.GoodsBean;
import com.zzy.home.R;
import com.zzy.storehouse.model.Goods;


public class GoodsDialog extends Dialog {
    private Context context;
    private TextView tvName,tvPrice,tvDesc;
    private RoundImageView ivPic;
    private Goods goodsBean;
/*************************************************************************************************/
    public GoodsDialog(Context context) {
        super(context, R.style.home_dialog);
        this.context = context;
    }

    public void setData(Goods goodsBean){
        this.goodsBean = goodsBean;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        init();
    }

    public void init() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.home_dialog, null);
        setContentView(view);

        tvName =  view.findViewById(R.id.tvName);
        tvPrice =  view.findViewById(R.id.tvPrice);
        tvDesc =  view.findViewById(R.id.tvDesc);
        ivPic =  view.findViewById(R.id.ivPic);

        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = AutoUtils.getPercentWidthSize(630);
//        lp.height = AutoUtils.getPercentHeightSize(694);
        dialogWindow.setAttributes(lp);
    }

    @Override
    public void show() {
        super.show();
        if(goodsBean!=null){
            tvName.setText(goodsBean.getName());
            try {
                tvPrice.setText(CommonUtils.formatMoney(goodsBean.getPrice())+"元");
            } catch (Exception e) {
                e.printStackTrace();
            }
            tvDesc.setText(goodsBean.getDesc());
            ImageLoaderUtils.getInstance().showImg(ApplicationUtils.get(),goodsBean.getImageUri(), ivPic);
        }
    }


}