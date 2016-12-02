/*
 * BaseServiceManager     2016/9/20-09-20
 * Copyright (c) 2016 KOTERWONG All right reserved
 */
package me.koterwong.api;


import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Koterwong on 2016/9/20 14:29
 */
@Singleton
public class ServiceManager {
  private ApiService mApiService;

  /**
   * 这里使用Inject
   * @param apiService
   */
  @Inject public ServiceManager(ApiService apiService) {
    mApiService = apiService;
  }

  public ApiService getApiService() {
    return mApiService;
  }
}
