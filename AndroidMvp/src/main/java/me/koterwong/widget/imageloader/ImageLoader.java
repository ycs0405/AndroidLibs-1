/*
 * ImageLoader     2016/9/23-09-23
 * Copyright (c) 2016 KOTERWONG All right reserved
 */
package me.koterwong.widget.imageloader;

import android.content.Context;

/**
 * Created by Koterwong on 2016/9/23 10:23
 */
public class ImageLoader {
  private ILoaderStrategy mStrategy;

  public ImageLoader(ILoaderStrategy strategy) {
    mStrategy = strategy;
  }

  public void setLoadImgStrategy(ILoaderStrategy strategy) {
    this.mStrategy = strategy;
  }

  public <T extends ImageConfig> void loadImage(Context context, T config) {
    this.mStrategy.loadImage(context, config);
  }
}
