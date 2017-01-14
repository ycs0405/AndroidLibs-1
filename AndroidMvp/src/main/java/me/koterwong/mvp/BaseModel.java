/*
 * BaseModel     2016/9/20-09-20
 * Copyright (c) 2016 KOTERWONG All right reserved
 */
package me.koterwong.mvp;

import me.koterwong.net.ServiceManager;

/**
 * Created by Koterwong on 2016/9/20 14:28
 *
 */
public class BaseModel implements BaseContract.Model {
  protected ServiceManager mServiceManager;

  public BaseModel(ServiceManager serviceManager) {
    mServiceManager = serviceManager;
  }

  @Override public void onDestroy() {
    mServiceManager = null;
  }
}
