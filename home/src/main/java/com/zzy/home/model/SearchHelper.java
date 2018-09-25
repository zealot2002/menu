package com.zzy.home.model;

import android.content.Context;
import android.widget.Filter;

import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.zzy.common.utils.ApplicationUtils;
import com.zzy.commonlib.utils.SPUtils;
import com.zzy.home.constants.SPConstants;
import com.zzy.home.model.bean.GoodsSuggestion;
import com.zzy.storehouse.model.Goods;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class SearchHelper {

    private static final int MAX_QUEUE_SIZE = 20;
    private Queue<String> suggestionQueue;

    public interface OnFindGoodsListener {
        void onResults(List<Goods> results);
    }
    public interface OnFindSuggestionsListener {
        void onResults(List<GoodsSuggestion> results);
    }

    public static SearchHelper getInstance() {
        return SearchHelper.LazyHolder.ourInstance;
    }
    private static class LazyHolder {
        private static final SearchHelper ourInstance = new SearchHelper();
    }

    private SearchHelper() {
        try{
            suggestionQueue = SPUtils.readObject(ApplicationUtils.get(), SPConstants.SP_SEARCH_HISTORY_QUEUE);
        }catch(Exception e){
            e.printStackTrace();
        }
        if(suggestionQueue == null){
            suggestionQueue = new ArrayBlockingQueue<>(MAX_QUEUE_SIZE);
        }
    }
/*********************************************************************************************************/

    /**
     * 模糊匹配suggestions
     * @param context
     * @param keyword
     * @param limit     最多给出几个
     * @param simulatedDelay
     * @param listener
     */
    public void findSuggestions(Context context, String keyword, final int limit, final long simulatedDelay,
                                       final OnFindSuggestionsListener listener) {
        new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                try {
                    Thread.sleep(simulatedDelay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                List<GoodsSuggestion> suggestionList = new ArrayList<>();
                if (!(constraint == null || constraint.length() == 0)) {
                    for (String suggestion : suggestionQueue) {
                        if (suggestion.contains(constraint)) {
                            suggestionList.add(new GoodsSuggestion(suggestion));
                            if (limit != -1 && suggestionList.size() == limit) {
                                break;
                            }
                        }
                    }
                }
                FilterResults results = new FilterResults();
                results.values = suggestionList;
                results.count = suggestionList.size();
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (listener != null) {
                    listener.onResults((List<GoodsSuggestion>) results.values);
                }
            }
        }.filter(keyword);
    }

    /**
     * 搜索goods
     * @param keyword    关键字
     * @param GoodsList  数据源
     * @param listener
     */
    public void findGoods(final String keyword,final List<Goods> GoodsList,final OnFindGoodsListener listener) {
        new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<Goods> list = new ArrayList<>();
                if (!(constraint == null || constraint.length() == 0)) {
                    for (Goods goodsBean : GoodsList) {
                        if(goodsBean.getName().contains(constraint)){
                            list.add(goodsBean);
                        }
                    }
                }
                FilterResults results = new FilterResults();
                results.values = list;
                results.count = list.size();
                if(results.count>0){
                    putSuggestHistory(keyword);
                }
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (listener != null) {
                    listener.onResults((List<Goods>) results.values);
                }
            }
        }.filter(keyword);
    }

    /**
     * keyword加入history
     * @param keyword
     */
    public void putSuggestHistory(String keyword){
        try{
            if(suggestionQueue.contains(keyword)){
                return;
            }
            if(suggestionQueue.size()==MAX_QUEUE_SIZE){
                suggestionQueue.remove();
            }
            suggestionQueue.add(keyword);
            SPUtils.saveObject(ApplicationUtils.get(),SPConstants.SP_SEARCH_HISTORY_QUEUE,suggestionQueue);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public List<? extends SearchSuggestion> getHistory(int limit) {
        List<SearchSuggestion> list = new ArrayList<>();
        int count = 0;
        Object[] array = suggestionQueue.toArray();

        while(count<limit){
            if(array.length-count<=0){
                break;
            }
            list.add(new GoodsSuggestion((String) array[array.length-count-1]));
            count++;
        }
        return list;
    }
}