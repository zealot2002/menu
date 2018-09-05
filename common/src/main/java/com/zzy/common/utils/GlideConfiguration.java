package com.zzy.common.utils;

import android.content.Context;
import android.util.Log;


import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.module.GlideModule;

import java.io.File;

/**
 * @author zzy
 * @date 2018/3/14
 */

public class GlideConfiguration implements GlideModule {

    private static final String TAG = "GlideConfiguration";
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        try{
            builder.setDecodeFormat(DecodeFormat.PREFER_RGB_565);
            setMemoryStrategy(builder);
            setDiskStrategy(context,builder);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void registerComponents(Context context, Glide glide) {
        // register ModelLoaders here.
    }


    private void setMemoryStrategy(GlideBuilder builder){
        int maxMemory = (int) Runtime.getRuntime().maxMemory();//获取系统分配给应用的总内存大小
        Log.e(TAG,"maxMemory:"+maxMemory);
        int memoryCacheSize = maxMemory / 2;//设置图片内存缓存占用八分之一
        //设置内存缓存大小
        builder.setMemoryCache(new LruResourceCache(memoryCacheSize));
        builder.setBitmapPool(new LruBitmapPool(memoryCacheSize));
    }

    private void setDiskStrategy(Context context, GlideBuilder builder){
        File cacheDir = context.getExternalCacheDir();//指定的是数据的缓存地址
        int diskCacheSize = 1024 * 1024 * 100;//最多可以缓存多少字节的数据
        //设置磁盘缓存大小
        builder.setDiskCache(new DiskLruCacheFactory(cacheDir.getPath(), "glide", diskCacheSize));
    }
}
