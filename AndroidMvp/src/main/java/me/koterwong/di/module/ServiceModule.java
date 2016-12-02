/*
 * ServiceModule     2016/11/28 08:54
 * Copyright (c) 2016 Koterwong All right reserved
 */
package me.koterwong.di.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.koterwong.api.ApiService;
import retrofit2.Retrofit;

/**
 * Created by Koterwong on 2016/11/28 08:54
 *
 * 管理接口Api实例
 */
@Module
@Singleton
public class ServiceModule {

  /**
   * 提供ApiService，如果需要其他的ApiService，同样使用该方法提供出去，并将该ApiService加入到ServiceManager的构造函数。
   *
   * @param retrofit Retrofit对象在ClientModule中有提供
   * @return apiService ，可以请求网络
   */
  @Singleton @Provides ApiService provideApiService(Retrofit retrofit) {
    return retrofit.create(ApiService.class);
  }
}
