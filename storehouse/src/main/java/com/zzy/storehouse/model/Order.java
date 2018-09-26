package com.zzy.storehouse.model;

/**
 * @author zzy
 * @date 2018/9/21
 */

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.OrderBy;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Order implements Serializable{
    private static final long serialVersionUID = 1L;

    public static final int ORDER_STATE_WAIT = 1;
    public static final int ORDER_STATE_DOING = 2;
    public static final int ORDER_STATE_OVER = 3;

    @Id(autoincrement = true)
    private Long id;

    private Long createTime;
    private Long handleTime;
    private Long finishTime;
    private String deskNum;
    private String remarks;
    private Float price;

    @OrderBy
    private int state;

    /* List<GoodsWrapperBean> */
    private String cartInfo;

    @Generated(hash = 337958375)
    public Order(Long id, Long createTime, Long handleTime, Long finishTime,
            String deskNum, String remarks, Float price, int state,
            String cartInfo) {
        this.id = id;
        this.createTime = createTime;
        this.handleTime = handleTime;
        this.finishTime = finishTime;
        this.deskNum = deskNum;
        this.remarks = remarks;
        this.price = price;
        this.state = state;
        this.cartInfo = cartInfo;
    }

    @Generated(hash = 1105174599)
    public Order() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getHandleTime() {
        return this.handleTime;
    }

    public void setHandleTime(Long handleTime) {
        this.handleTime = handleTime;
    }

    public Long getFinishTime() {
        return this.finishTime;
    }

    public void setFinishTime(Long finishTime) {
        this.finishTime = finishTime;
    }

    public String getDeskNum() {
        return this.deskNum;
    }

    public void setDeskNum(String deskNum) {
        this.deskNum = deskNum;
    }

    public String getRemarks() {
        return this.remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public int getState() {
        return this.state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getCartInfo() {
        return this.cartInfo;
    }

    public void setCartInfo(String cartInfo) {
        this.cartInfo = cartInfo;
    }

    public Float getPrice() {
        return this.price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }



}
