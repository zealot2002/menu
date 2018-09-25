package com.zzy.storehouse.model;

/**
 * @author zzy
 * @date 2018/9/21
 */

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import java.io.Serializable;
import java.util.List;

@Entity
public class Order implements Serializable{
    private static final long serialVersionUID = 1L;

    public static final int ORDER_STATE_WAIT = 1;
    public static final int ORDER_STATE_DOING = 2;
    public static final int ORDER_STATE_OVER = 3;

    @Id
    private Long id;

    private Long time;
    private int deskNum;
    private String remarks;
    private int state;
    private List<Goods> goodsList;


}
