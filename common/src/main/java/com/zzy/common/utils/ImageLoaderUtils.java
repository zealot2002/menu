package com.zzy.common.utils;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
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
                .into(imageView);
    }
    public void showImg(final Context context, final String url, final ImageView imageView){
        Glide.with(context)
                .load(url)
                .into(imageView);
    }
    public void showImgNoCache(final Context context, final String url, final ImageView imageView){
        Glide.with(context)
                .load(url)
                .into(imageView);
    }

    public void showImg(final Context context, final String url, final Target target){

        Glide.with(context)
                .load(url)
                .into(target);
    }

}
