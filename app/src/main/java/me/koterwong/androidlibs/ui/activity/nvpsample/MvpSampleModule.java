/*
 * MvpSampleModule     2016/12/15 19:56
 * Copyright (c) 2016 Koterwong All right reserved
 */
package me.koterwong.androidlibs.ui.activity.nvpsample;

import dagger.Module;
import dagger.Provides;
import me.koterwong.api.ServiceManager;
import me.koterwong.di.scope.ActScope;

/**
 * Created by Koterwong on 2016/12/15 19:56
 */
@Module
@ActScope
public class MvpSampleModule {

  private MvpSampleContract.View mView;

  public MvpSampleModule(MvpSampleContract.View view) {
    mView = view;
  }

  @ActScope @Provides public MvpSampleContract.View provideView() {
    return mView;
  }

  @ActScope @Provides public MvpSamplePresenter providerPresenter(MvpSampleModel mvpSampleModel, MvpSampleContract.View view) {
    return new MvpSamplePresenter(mvpSampleModel, view);
  }

  @ActScope @Provides public MvpSampleModel provideModule(ServiceManager serviceManager) {
    return new MvpSampleModel(serviceManager);
  }
}
