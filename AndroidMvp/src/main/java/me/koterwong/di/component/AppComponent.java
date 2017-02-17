/*
 * AppComponent     2016/11/28 07:56
 * Copyright (c) 2016 Koterwong All right reserved
 */
package me.koterwong.di.component;

import com.tbruyelle.rxpermissions.RxPermissions;

import javax.inject.Singleton;

import dagger.Component;
import me.koterwong.di.module.AppModule;
import me.koterwong.di.module.ClientModule;
import me.koterwong.di.module.ServiceModule;
import me.koterwong.net.ServiceManager;
import okhttp3.OkHttpClient;

/**
 * Created by Koterwong on 2016/11/28 07:56
 *
 * 全局Component，管理全局单例对象，需要在Application中进行初始化，且只能初始化一次，否则单例失效。
 *
 * 理解Component：连接着目标类和目标类依赖类的实例端
 * 它可以将实例注入到目标类中，实例的创建可以通过@Inject标注构造函数，
 * 也可以通过Component管理的Module创建（例如依赖的第三方库）
 */
@Singleton
@Component(modules = {AppModule.class, ClientModule.class, ServiceModule.class})
public interface AppComponent {
  RxPermissions rxPermissions();

  OkHttpClient okHttpClient();

  ServiceManager serviceManager();
}
