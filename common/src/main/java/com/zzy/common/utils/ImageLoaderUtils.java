package com.zzy.common.utils;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;

/**
 * @author zzy
 * @date 2018/2/26
 */

public class ImageLoaderUtils {
    private ImageLoaderUtils() {}
    public static ImageLoaderUtils getInstance() {
        return ImageLoaderUtils.Holder.instance;
    }

    public static void pauseRequests(Context context) {
        Glide.with(context).pauseRequests();
    }

    public static void resumeRequests(Context context) {
        Glide.with(context).resumeRequests();
    }

    private static class Holder {
        private static final ImageLoaderUtils instance = new ImageLoaderUtils();
        private Holder() {}
    }
    public void showImgWithPlaceHolder(final Context context, final String url, final ImageView imageView,final Drawable drawable){
        Glide.with(context)
                .load(url)
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .crossFade()
                .placeholder(drawable)
                .priority(Priority.NORMAL)
                .into(imageView);
    }
    public void showImg(final Context context, final String url, final ImageView imageView){
        Glide.with(context)
                .load(url)
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .crossFade()
                .priority(Priority.NORMAL)
                .into(imageView);
    }
    public void showImgNoCache(final Context context, final String url, final ImageView imageView){
        Glide.with(context)
                .load(url)
                .skipMemoryCache(true)
                .crossFade()
                .priority(Priority.HIGH)
                .into(imageView);
    }
    public void showImg(final Context context, final String url, final View view){
        SimpleTarget target = new SimpleTarget<Drawable>() {
            @SuppressLint("NewApi")
            @Override
            public void onResourceReady(Drawable resource, GlideAnimation<? super Drawable> glideAnimation) {
                view.setBackground(resource);
            }
        };
        Glide.with(context)
                .load(url)
                .centerCrop()
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .crossFade()
                .dontAnimate()
                .priority(Priority.NORMAL)
                .into(target);
    }


    public void showImg(final Context context, final String url, final Target target){

        Glide.with(context)
                .load(url)
                .centerCrop()
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .crossFade()
                .dontAnimate()
                .priority(Priority.NORMAL)
                .listener(new RequestListener() {
                    @Override
                    public boolean onException(Exception e, Object model, Target target, boolean isFirstResource) {
                        return false;
                    }
                    @Override
                    public boolean onResourceReady(Object resource, Object model, Target target, boolean isFromMemoryCache, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(target);

    }

}
