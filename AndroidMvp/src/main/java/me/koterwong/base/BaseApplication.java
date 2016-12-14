/*
 * FrameWorkApplication     2016/9/2-09-02
 * Copyright (c) 2016 KOTERWONG All right reserved
 */
package me.koterwong.base;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.multidex.MultiDex;

import com.squareup.leakcanary.LeakCanary;

import java.util.LinkedList;

import me.koterwong.api.GlobeHttpHandler;
import me.koterwong.common.BuildConfig;
import me.koterwong.common.CrashHandler;
import me.koterwong.common.LogKw;
import me.koterwong.di.component.AppComponent;
import me.koterwong.di.component.DaggerAppComponent;
import me.koterwong.di.module.AppModule;
import me.koterwong.di.module.ClientModule;
import me.koterwong.di.module.ServiceModule;
import me.koterwong.jpush.JPushUtil;
import okhttp3.HttpUrl;

public abstract class BaseApplication extends Application implements IApplication {
  protected final String TAG = this.getClass().getSimpleName();
  protected static BaseApplication mApplication;

  private static LinkedList<BaseAppCompatActivity> mActivities = new LinkedList<>();
  private AppComponent mAppComponent;

  public static BaseApplication get() {
    return mApplication;
  }

  @Override public void onCreate() {
    super.onCreate();
    mApplication = this;

    if (BuildConfig.USE_CANARY) {
      LeakCanary.install(this);
    }

    initDagger2();   //初始化Dagger2 需要组件

    LogKw.setDebug(BuildConfig.LOG_DEBUG);  // init log
    JPushUtil.initJPush(this, true);  //init jPush
    CrashHandler.get().init(getApplicationContext()); //init CrashHandler
  }

  private void initDagger2() {
    mAppComponent = DaggerAppComponent.builder()
        .appModule(new AppModule(this))
        .clientModule(new ClientModule(getHttpUrl(), getHttpHandler()))
        .serviceModule(new ServiceModule())
        .build();
  }

  protected abstract HttpUrl getHttpUrl();

  protected abstract GlobeHttpHandler getHttpHandler();

  public AppComponent getAppComponent() {
    return mAppComponent;
  }

  @Override public void addActivity(BaseAppCompatActivity activity) {
    mActivities.add(activity);
  }

  @Override public void removeActivity(BaseAppCompatActivity activity) {
    mActivities.remove(activity);
  }


  @Override public LinkedList<BaseAppCompatActivity> getActivitys() {
    return mActivities;
  }

  @Override public <T> T getActivity(Class<?> activity) {
    for (int i = 0; i < mActivities.size(); i++) {
      if (getActivitys().get(i).getClass().equals(activity)) {
        return (T) getActivitys().get(i);
      }
    }
    return null;
  }

  @Override public BaseAppCompatActivity getCurActivity() {
    return mActivities.get(mActivities.size() - 1);
  }

  /**
   * 退出程序
   */
  @Override public void killAll() {
    LinkedList<BaseAppCompatActivity> copy;
    synchronized (BaseApplication.class) {
      copy = new LinkedList<>(mActivities);
    }
    for (BaseAppCompatActivity baseAppCompatActivity : copy) {
      baseAppCompatActivity.finish();
    }
  }

  /**
   * 使用 MultiDex
   */
  @Override protected void attachBaseContext(Context base) {
    super.attachBaseContext(base);
    MultiDex.install(this);
  }

  /**
   * 获取应用包名
   *
   * @return package
   */
  public String getAppPackageName() {
    PackageInfo info = null;
    try {
      info = getPackageManager().getPackageInfo(getPackageName(), 0);
      if (null != info) {
        LogKw.d("packagename", info.packageName);
        return info.packageName;
      }
    } catch (PackageManager.NameNotFoundException e) {
      e.printStackTrace();
    }
    return "";
  }
}
