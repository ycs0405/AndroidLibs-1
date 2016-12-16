/*
 * MvpSampleComponent     2016/12/15 20:03
 * Copyright (c) 2016 Koterwong All right reserved
 */
package me.koterwong.androidlibs.ui.activity.nvpsample;

import dagger.Component;
import me.koterwong.di.component.AppComponent;
import me.koterwong.di.scope.ActScope;

/**
 * Created by Koterwong on 2016/12/15 20:03
 */
@ActScope
@Component(modules = MvpSampleModule.class, dependencies = AppComponent.class)
public interface MvpSampleComponent {
  void inject(MvpSampleActivity mvpSampleActivity);
}
