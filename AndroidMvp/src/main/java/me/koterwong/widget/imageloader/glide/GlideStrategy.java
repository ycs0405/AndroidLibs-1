/*
 * GlideStrategy     2016/9/23-09-23
 * Copyright (c) 2016 KOTERWONG All right reserved
 */
package me.koterwong.widget.imageloader.glide;

import android.app.Activity;
import android.content.Context;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import me.koterwong.widget.imageloader.ILoaderStrategy;


/**
 * Created by Koterwong on 2016/9/23 10:43
 */
public class GlideStrategy implements ILoaderStrategy<GlideConfig> {
  @Override public void loadImage(Context context, GlideConfig config) {
    RequestManager manager = null;
    if (context instanceof Activity)
      manager = Glide.with((Activity) context); //与activity生命周期保持一致，去暂停和加载图片
    else
      manager = Glide.with(context.getApplicationContext());

    DrawableRequestBuilder<String> requestBuilder = manager
        .load(config.getUrl())
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .crossFade()
        .centerCrop();
    if (config.getPlaceholder() != 0)//设置占位符
      requestBuilder.placeholder(config.getPlaceholder());
    if (config.getErrorPic() != 0)//设置错误的图片
      requestBuilder.error(config.getErrorPic());
    requestBuilder.into(config.getImageView());
  }
}
