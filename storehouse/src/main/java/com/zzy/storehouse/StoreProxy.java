package com.zzy.storehouse;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.zzy.common.utils.ApplicationUtils;
import com.zzy.commonlib.utils.FileUtils;
import com.zzy.storehouse.greendao.CategoryDao;
import com.zzy.storehouse.greendao.DaoMaster;
import com.zzy.storehouse.greendao.DaoSession;
import com.zzy.storehouse.greendao.GoodsDao;
import com.zzy.storehouse.model.Category;
import com.zzy.storehouse.model.Goods;

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

        tryLoadOriginalData();
    }

    private void tryLoadOriginalData() {
//        if(SPUtils.getBoolean(ApplicationUtils.get(), SpConstants.SP_FIRST_USE,false)){
//            SPUtils.putBoolean(ApplicationUtils.get(), SpConstants.SP_FIRST_USE,true);
            try {
                loadOriginalCategory();
                loadOriginalGoods();
            } catch (Exception e) {
                e.printStackTrace();
            }
//        }
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
            JSONObject dishObj = array.getJSONObject(j);
            Category bean = new Category();
            bean.setId(dishObj.getLong("id"));
            bean.setName(dishObj.getString("name"));
            addCategory(bean);
        }
    }

    /*goods api*/
    public void addGoods(Goods goods){
        goodsDao.insert(goods);
    }

    public void deleteGoods(long id){
        goodsDao.deleteByKey(id);
    }

    public void updateGoods(Goods goods){
        goodsDao.update(goods);
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
    public void addCategory(Category category){
        categoryDao.insert(category);
    }

    public void deleteCategory(long id){
        categoryDao.deleteByKey(id);
    }

    public void updateCategory(Category category){
        categoryDao.update(category);
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

}