package com.zzy.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.zzy.common.R;


/**
 * Created by tt on 2016/6/27.
 */
public class AddAndSubWidget extends LinearLayout implements View.OnClickListener{
    public interface EventListener{
        void onAddEvent();
        void onSubEvent();
    }

    private Context context;
    private LayoutInflater inflater;
    private LayoutParams layoutParams;
    private LinearLayout main;

    private ImageButton btnSub,btnAdd;
    private EditText etNum;

    private EventListener listener;
/*****************************************************************************************************/
    public AddAndSubWidget(Context context){
        this(context,null);
    }
    public AddAndSubWidget(Context context, AttributeSet attrs){
        this(context, attrs, 0);
    }

    public AddAndSubWidget(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        inflater = LayoutInflater.from(context);
        main = (LinearLayout)inflater.inflate(R.layout.add_and_sub_widget, null);
        layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        addView(main, layoutParams);
        initView();
    }

    public void setListener(EventListener listener) {
        this.listener = listener;
    }

    private void initView(){
        btnSub = findViewById(R.id.btnSub);
        btnAdd = findViewById(R.id.btnAdd);
        etNum = findViewById(R.id.etNum);

        btnSub.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
    }

    public void setValue(int value){
        if(value == 0){
            etNum.setVisibility(View.GONE);
            btnSub.setVisibility(View.GONE);
            return;
        }
        if(etNum.getVisibility()!= View.VISIBLE){
            etNum.setVisibility(View.VISIBLE);
        }
        if(btnSub.getVisibility()!= View.VISIBLE){
            btnSub.setVisibility(View.VISIBLE);
        }
        etNum.setText(value+"");
    }
    @Override
    public void onClick(View v) {
        int value = Integer.valueOf(etNum.getText().toString().trim());
        if(v.getId() == R.id.btnSub){
            if(value>0){
                value--;
                if(value == 0){
                    btnSub.setVisibility(View.GONE);
                    etNum.setVisibility(View.GONE);
                }
                etNum.setText(value+"");
                if(listener!=null){
                    listener.onSubEvent();
                }
            }
        }else if(v.getId() == R.id.btnAdd){
            if(value==99){
                return;
            }
            value++;
            if(value == 1){//只有等于1的时候，才需要再次打开，否则就是可见的
                btnSub.setVisibility(View.VISIBLE);
                etNum.setVisibility(View.VISIBLE);
            }
            etNum.setText(value+"");
            if(listener!=null){
                listener.onAddEvent();
            }
        }
    }
}
