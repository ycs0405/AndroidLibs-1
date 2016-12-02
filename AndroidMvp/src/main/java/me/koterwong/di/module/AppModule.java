/*
 * AppModule     2016/9/20-09-20
 * Copyright (c) 2016 KOTERWONG All right reserved
 */
package me.koterwong.di.module;

import android.app.Application;

import com.tbruyelle.rxpermissions.RxPermissions;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Koterwong on 2016/9/20 14:50
 */
@Module
public class AppModule {
  private Application mApplication;

  public AppModule(Application application) {
    mApplication = application;
  }

  /**
   * 提供全局Context对象。
   *
   * @return
   */
  @Provides @Singleton public Application provideApplication() {
    return mApplication;
  }

  /**
   * 提供RxPermissions对象
   *
   * @param application
   * @return
   */
  @Provides @Singleton public RxPermissions provideRxPermissions(Application application) {
    return RxPermissions.getInstance(application);
  }
}
