/*
 * ServiceModule     2016/11/28 08:54
 * Copyright (c) 2016 Koterwong All right reserved
 */
package me.koterwong.di.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.koterwong.net.ServiceManager;
import retrofit2.Retrofit;

/**
 * Created by Koterwong on 2016/11/28 08:54
 *
 * 管理接口Api实例
 */
@Module
public class ServiceModule {
  @Provides @Singleton public ServiceManager providerServiceManager(Retrofit retrofit) {
    return new ServiceManager(retrofit);
  }
}
