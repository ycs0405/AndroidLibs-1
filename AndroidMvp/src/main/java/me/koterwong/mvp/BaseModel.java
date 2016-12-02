/*
 * BaseModel     2016/9/20-09-20
 * Copyright (c) 2016 KOTERWONG All right reserved
 */
package me.koterwong.mvp;

import javax.inject.Inject;

import me.koterwong.api.ServiceManager;

/**
 * Created by Koterwong on 2016/9/20 14:28
 *
 */
public class BaseModel implements BaseContract.Model {
  @Inject
  protected ServiceManager mServiceManager;

  @Override public void onDestroy() {
    mServiceManager = null;
  }
}
