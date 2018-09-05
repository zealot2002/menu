package com.zzy.home.model.bean;

import com.alibaba.android.arouter.facade.service.SerializationService;

import java.io.Serializable;

/**
 * @author zzy
 * @date 2018/8/27
 */

public class GoodsBean implements Serializable{
    public static final int STATE_NORMAL = 0;
    public static final int STATE_SELL_OUT = 1;
    int id;
    int categoryId;
    String name;
    String desc;
    String price;
    String imageUri;
    int state;

    public GoodsBean() {
    }

    public GoodsBean(String name, String price, String imageUri) {
        this.name = name;
        this.price = price;
        this.imageUri = imageUri;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
