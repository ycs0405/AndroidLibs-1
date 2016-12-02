/*
 * IImageLoaderStrategy     2016/9/23-09-23
 * Copyright (c) 2016 KOTERWONG All right reserved
 */
package me.koterwong.widget.imageloader;

import android.content.Context;

/**
 * Created by Koterwong on 2016/9/23 10:08
 */
public interface ILoaderStrategy<T extends ImageConfig> {
  void loadImage(Context context, T config);
}
