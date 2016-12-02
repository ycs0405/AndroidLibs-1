/*
 * GlideConfiguration     2016/9/23-09-23
 * Copyright (c) 2016 KOTERWONG All right reserved
 */
package me.koterwong.widget.imageloader.glide;

import android.content.Context;
import android.os.Environment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.load.engine.cache.DiskLruCacheWrapper;
import com.bumptech.glide.module.GlideModule;

import java.io.File;

/**
 * Created by Koterwong on 2016/9/23 10:56
 */
public class GlideConfiguration implements GlideModule {

  public static final int DISK_CACHE_SIZE = 200 * 1024 * 1024;

  @Override public void applyOptions(final Context context, GlideBuilder builder) {

    String downLoadPath = Environment.getDownloadCacheDirectory().getPath();
    builder.setDiskCache(new DiskLruCacheFactory(downLoadPath, DISK_CACHE_SIZE));

    builder.setDiskCache(new DiskCache.Factory() {
      @Override public DiskCache build() {
        File cacheLocation = new File(context.getExternalCacheDir(), "cache_dir");
        cacheLocation.mkdirs();
        return DiskLruCacheWrapper.get(cacheLocation, DISK_CACHE_SIZE);
      }
    });
  }

  @Override public void registerComponents(Context context, Glide glide) {

  }
}
