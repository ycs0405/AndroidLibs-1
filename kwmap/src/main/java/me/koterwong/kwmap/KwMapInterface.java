/*
 * KwMapInterface     2016/9/26-09-26
 * Copyright (c) 2016 KOTERWONG All right reserved
 */
package me.koterwong.kwmap;

import android.content.Context;

import com.baidu.mapapi.SDKInitializer;

/**
 * Created by Koterwong on 2016/9/26 09:55
 */
public class KwMapInterface {
  public static void initialize(Context applicationContext){
    SDKInitializer.initialize(applicationContext);
  }
}
