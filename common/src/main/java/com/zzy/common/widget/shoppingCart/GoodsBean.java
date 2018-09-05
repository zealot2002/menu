package com.zzy.common.widget.shoppingCart;

import java.io.Serializable;

/**
 * Created by tt on 2016/6/17.
 */
public class GoodsBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String id;
    private final String name;//名字
    private final float price;//价格
    private final String imgUri;

    public String getId() {
        return id;
    }

    public float getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public String getImgUri() {
        return imgUri;
    }

    public static class Builder {
        private String id;
        private String name;//名字
        private float price;//价格
        private String imgUri;

        public Builder(String val) {
            name = val;
        }
        public Builder id(String val) {
            id = val;
            return this;
        }
        public Builder price(float val) {
            price = val;
            return this;
        }
        public Builder imgUri(String val) {
            imgUri = val;
            return this;
        }

        public GoodsBean build() {
            return new GoodsBean(this);
        }
    }
    public GoodsBean(Builder b) {
        id = b.id;
        price = b.price;
        name = b.name;
        imgUri = b.imgUri;
    }
}
