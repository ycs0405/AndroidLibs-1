/*
 * BaseServiceManager     2016/9/20-09-20
 * Copyright (c) 2016 KOTERWONG All right reserved
 */
package me.koterwong.net;



import retrofit2.Retrofit;

/**
 * Created by Koterwong on 2016/9/20 14:29
 */
public class  ServiceManager {
  private Retrofit mRetrofit;

  public ServiceManager(Retrofit retrofit) {
    this.mRetrofit = retrofit;
  }

  public <T> T getApiService(Class<T> tClass) {
    return mRetrofit.create(tClass);
  }
}
