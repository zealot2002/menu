package com.zzy.manager.view;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
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

//import droidninja.filepicker.FilePickerBuilder;
//import droidninja.filepicker.FilePickerConst;
import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;

/**
 * @author zzy
 * @date 2018/9/20
 */
@Route(path = RouterConstants.MANAGER_GOODS_DETAIL)
public class GoodsDetailActivity extends BaseTitleBarActivity implements View.OnClickListener{
    private Goods goods;
    private RoundImageView ivPic;
    private EditText etName,etPrice,etDesc;
    private Button btnOk;
    private Spinner spinnerCategory,spinnerState;
    private ArrayList<String> photoPaths = new ArrayList<>();
    private static final int PERMISSION_READ_EXTERNAL_STORAGE = 555;
/***************************************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("商品详情");
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
        long id = getIntent().getLongExtra(ParamConstants.PARAM_ID,0);
        goods = StoreProxy.getInstance().getGoods(id);

    }

    private void setupViews() {
        View contentView = View.inflate(this,R.layout.manager_goods_detail,null);
        getContainer().addView(contentView);

//        setupPic
        ivPic = findViewById(R.id.ivPic);
        ImageLoaderUtils.getInstance().showImg(ApplicationUtils.get(),goods.getImageUri(), ivPic);
        ivPic.setOnClickListener(this);
//        setupNamePrice

        etName = findViewById(R.id.etName);
        etName.setText(goods.getName());
        etPrice = findViewById(R.id.etPrice);
        etPrice.setText(goods.getPrice());

//        setupCategoryState
        spinnerCategory = findViewById(R.id.spinnerCategory);
        List<Category> categoryList = StoreProxy.getInstance().getCategoryList();
        List<String> list1 = new ArrayList<>();
        int currentCategoryIndex = 0;
        for(int i=0;i<categoryList.size();i++){
            list1.add(categoryList.get(i).getName());
            if(categoryList.get(i).getId() == goods.getCategoryId()){
                currentCategoryIndex = i;
            }
        }
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, list1);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapter);
        spinnerCategory.setSelection(currentCategoryIndex);

        spinnerState = findViewById(R.id.spinnerState);
        String[] list2 = new String[]{"正常","已售罄"};
        ArrayAdapter<String> adapter2=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, list2);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerState.setAdapter(adapter2);
        spinnerState.setSelection(goods.getState()== CommonConstants.STATE_NORMAL?0:1);
//        setupDesc
        etDesc = findViewById(R.id.etDesc);
        etDesc.setText(goods.getDesc());

//        setupOther
        btnOk = findViewById(R.id.btnOk);
        btnOk.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnOk){
            goods.setDesc(etDesc.getText().toString().trim());
            goods.setName(etName.getText().toString().trim());
            goods.setPrice(etPrice.getText().toString().trim());
            goods.setCategory(StoreProxy.getInstance().getCategoryList().get(spinnerCategory.getSelectedItemPosition()));
//            goods.setCategoryId(StoreProxy.getInstance().getCategoryList().get(spinnerCategory.getSelectedItemPosition()).getId());
            goods.setState(spinnerState.getSelectedItemPosition());
            StoreProxy.getInstance().updateGoods(goods);
            MyToast.show(ApplicationUtils.get(),"保存成功!");
            finish();
        }else if(v.getId() == R.id.ivPic){
            checkPermission();
        }
    }

    private void checkPermission() {
        PermissionGen.needPermission(this, PERMISSION_READ_EXTERNAL_STORAGE,
                new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                }
        );
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    @PermissionSuccess(requestCode = PERMISSION_READ_EXTERNAL_STORAGE)
    public void readSuccess() {
//        FilePickerBuilder.getInstance().setMaxCount(1)
//                .setSelectedFiles(photoPaths)
//                .setActivityTheme(R.style.LibAppTheme)
//                .pickPhoto(this,100);
    }

    @PermissionFail(requestCode = PERMISSION_READ_EXTERNAL_STORAGE)
    public void readFail() {
        Toast.makeText(this, "请授权app读取存储卡", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 100:
                if (resultCode == Activity.RESULT_OK && data != null) {
//                    photoPaths = new ArrayList<>();
//                    photoPaths.addAll(data.getStringArrayListExtra(FilePickerConst.KEY_SELECTED_MEDIA));
//                    String path = photoPaths.get(0);
//                    goods.setImageUri(path);
//                    ImageLoaderUtils.getInstance().showImg(ApplicationUtils.get(),goods.getImageUri(), ivPic);
                }
                break;
        }
    }

}
