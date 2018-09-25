package com.zzy.storehouse.model;

/**
 * @author zzy
 * @date 2018/9/21
 */

import com.zzy.storehouse.greendao.CategoryDao;
import com.zzy.storehouse.greendao.DaoSession;
import com.zzy.storehouse.greendao.GoodsDao;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToOne;

import java.io.Serializable;

@Entity
public class Goods implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    private Long id;
    private String name;
    private String desc;
    private String price;
    private String imageUri;
    private int state;

    private Long categoryId;
    @ToOne(joinProperty = "categoryId")
    private Category category;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 258717323)
    private transient GoodsDao myDao;
    @Generated(hash = 1447982564)
    public Goods(Long id, String name, String desc, String price, String imageUri,
            int state, Long categoryId) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.price = price;
        this.imageUri = imageUri;
        this.state = state;
        this.categoryId = categoryId;
    }
    @Generated(hash = 1770709345)
    public Goods() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDesc() {
        return this.desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
    public String getPrice() {
        return this.price;
    }
    public void setPrice(String price) {
        this.price = price;
    }
    public String getImageUri() {
        return this.imageUri;
    }
    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }
    public int getState() {
        return this.state;
    }
    public void setState(int state) {
        this.state = state;
    }
    public Long getCategoryId() {
        return this.categoryId;
    }
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
    @Generated(hash = 1372501278)
    private transient Long category__resolvedKey;
    /** To-one relationship, resolved on first access. */
    @Generated(hash = 728129201)
    public Category getCategory() {
        Long __key = this.categoryId;
        if (category__resolvedKey == null || !category__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            CategoryDao targetDao = daoSession.getCategoryDao();
            Category categoryNew = targetDao.load(__key);
            synchronized (this) {
                category = categoryNew;
                category__resolvedKey = __key;
            }
        }
        return category;
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1132018243)
    public void setCategory(Category category) {
        synchronized (this) {
            this.category = category;
            categoryId = category == null ? null : category.getId();
            category__resolvedKey = categoryId;
        }
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 2112467424)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getGoodsDao() : null;
    }


}
