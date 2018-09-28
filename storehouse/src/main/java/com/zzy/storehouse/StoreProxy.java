package com.zzy.storehouse;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.zzy.common.constants.SpConstants;
import com.zzy.common.utils.ApplicationUtils;
import com.zzy.commonlib.utils.FileUtils;
import com.zzy.commonlib.utils.SPUtils;
import com.zzy.storehouse.greendao.CategoryDao;
import com.zzy.storehouse.greendao.DaoMaster;
import com.zzy.storehouse.greendao.DaoSession;
import com.zzy.storehouse.greendao.GoodsDao;
import com.zzy.storehouse.greendao.OrderDao;
import com.zzy.storehouse.model.Category;
import com.zzy.storehouse.model.Goods;
import com.zzy.storehouse.model.Order;

import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.query.DeleteQuery;
import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;
import org.greenrobot.greendao.query.WhereCondition;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.List;

/**
 * @author zzy
 * @date 2018/9/21
 */

public class StoreProxy {

    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    private GoodsDao goodsDao;
    private CategoryDao categoryDao;
    private OrderDao orderDao;
/********************************************************************************************************/

    private static final StoreProxy ourInstance = new StoreProxy();

    public static StoreProxy getInstance() {
        return ourInstance;
    }

    private StoreProxy() {
    }

    public void init(Application app){
// 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
// 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
// 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
// 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
        mHelper = new DaoMaster.DevOpenHelper(app, "menu-db", null);
        db = mHelper.getWritableDatabase();
         // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();

        categoryDao = mDaoSession.getCategoryDao();
        goodsDao = mDaoSession.getGoodsDao();
        orderDao = mDaoSession.getOrderDao();

        tryLoadOriginalData();
    }

    private void tryLoadOriginalData() {
        if(SPUtils.getBoolean(ApplicationUtils.get(), SpConstants.SP_FIRST_USE,true)){
            SPUtils.putBoolean(ApplicationUtils.get(), SpConstants.SP_FIRST_USE,false);
            try {
                loadOriginalCategory();
                loadOriginalGoods();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void loadOriginalGoods() throws Exception{
        String data = FileUtils.readFileFromAssets(ApplicationUtils.get(),"goodsList.json");
        JSONTokener jsonParser = new JSONTokener(data);
        JSONObject obj = (JSONObject) jsonParser.nextValue();
        JSONObject dataObj = obj.getJSONObject("data");
        JSONArray array = dataObj.getJSONArray("goodsList");
        for(int j=0;j<array.length();j++) {
            JSONObject itemObj = array.getJSONObject(j);
            Goods bean = new Goods();
            bean.setId(itemObj.getLong("id"));
            bean.setCategoryId(itemObj.getLong("categoryId"));
            bean.setName(itemObj.getString("name"));
            if(itemObj.has("desc")) bean.setDesc(itemObj.getString("desc"));
            bean.setPrice(itemObj.getString("price"));
            bean.setImageUri(itemObj.getString("imageUri"));
            if(itemObj.has("state")) bean.setState(itemObj.getInt("state"));

            addGoods(bean);
        }
    }

    private void loadOriginalCategory() throws Exception{
        String data = FileUtils.readFileFromAssets(ApplicationUtils.get(),"categoryList.json");
        JSONTokener jsonParser = new JSONTokener(data);
        JSONObject obj = (JSONObject) jsonParser.nextValue();
        JSONObject dataObj = obj.getJSONObject("data");
        JSONArray array = dataObj.getJSONArray("categoryList");
        for(int j=0;j<array.length();j++) {
            JSONObject itemObj = array.getJSONObject(j);
            Category bean = new Category();
            bean.setId(itemObj.getLong("id"));
            bean.setName(itemObj.getString("name"));
            categoryDao.insertOrReplace(bean);
        }
    }

    /*goods api*/
    public void addGoods(@NotNull Goods goods){
        goodsDao.insert(goods);
    }

    public void deleteGoods(long id){
        goodsDao.deleteByKey(id);
    }

    public void deleteGoodsListByCategoryId(long categoryId){
        DeleteQuery deleteQuery=mDaoSession.getGoodsDao().queryBuilder()
                .where(GoodsDao.Properties.CategoryId.eq(categoryId))
                .buildDelete();
        deleteQuery.executeDeleteWithoutDetachingEntities();
    }

    public void updateGoods(@NotNull Goods goods){
        goodsDao.insertOrReplace(goods);
    }

    public Goods getGoods(long id){
        return goodsDao.loadDeep(id);
    }

    public void deleteAllGoods(){
        goodsDao.deleteAll();
    }

    public List<Goods> getGoodsList(long categoryId){
        return categoryDao.load(categoryId).getGoodsList();
    }

    public List<Goods> getAllGoodsList(){
        return goodsDao.loadAll();
    }

    /*category api*/
    public void deleteCategory(long id){
        categoryDao.deleteByKey(id);
        deleteGoodsListByCategoryId(id);
    }

    public void updateCategory(Category category){
        categoryDao.insertOrReplace(category);
    }

    public Category getCategory(long id){
        return categoryDao.load(id);
    }

    public void deleteAllCategory(){
        categoryDao.deleteAll();
    }

    public List<Category> getCategoryList(){
        return categoryDao.loadAll();
    }


    /*order api*/
    public void updateOrder(Order order){
        orderDao.insertOrReplace(order);
    }
    public Order getOrder(Long id){
        return orderDao.load(id);
    }
    public void deleteOrder(Long id){
        orderDao.deleteByKey(id);
    }
    public void deleteAllOrder(){
        orderDao.deleteAll();
    }
    public List<Order> getOrderList(){
        return orderDao.loadAll();
    }
    public List<Order> getOrderList(long startDate,long endDate,int pageIndex,int pageSize){
        List<Order> list = orderDao.queryBuilder()
                .where(OrderDao.Properties.CreateTime.between(startDate, endDate))
                .orderDesc(OrderDao.Properties.CreateTime)
                .offset(pageIndex*pageSize)
                .limit(pageSize).list();
        return list;
    }
}
