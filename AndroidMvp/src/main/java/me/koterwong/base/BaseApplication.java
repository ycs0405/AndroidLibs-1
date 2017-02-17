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
import android.support.v7.app.AppCompatActivity;

import com.squareup.leakcanary.LeakCanary;

import java.util.LinkedList;

import me.koterwong.common.BuildConfig;
import me.koterwong.common.CrashHandler;
import me.koterwong.common.LogKw;
import me.koterwong.di.component.AppComponent;
import me.koterwong.di.component.DaggerAppComponent;
import me.koterwong.di.module.AppModule;
import me.koterwong.di.module.ClientModule;
import me.koterwong.di.module.ServiceModule;
import me.koterwong.jpush.JPushUtil;
import me.koterwong.net.GlobeHttpHandler;
import me.koterwong.utils.SPUtils;
import me.koterwong.utils.Utils;
import okhttp3.HttpUrl;

public abstract class BaseApplication extends Application {
  protected final String TAG = this.getClass().getSimpleName();
  protected static BaseApplication mApplication;
  private SPUtils mSpUtils;

  private static LinkedList<AppCompatActivity> mActivities = new LinkedList<>();
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

    Utils.init(this);
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

  public SPUtils getSpUtils() {
    if (mSpUtils == null) {
      mSpUtils = new SPUtils("config");
    }
    return mSpUtils;
  }

  public boolean isLogin() {
    return "true".equals(getSpUtils().getString("isLogin"));
  }

  public void setLogin(boolean isLogin) {
    getSpUtils().putString("isLogin", String.valueOf(isLogin));
  }

  public void addActivity(AppCompatActivity activity) {
    mActivities.add(activity);
  }

  public void removeActivity(AppCompatActivity activity) {
    mActivities.remove(activity);
  }


  public LinkedList<AppCompatActivity> getActivitys() {
    return mActivities;
  }

  public <T> T getActivity(Class<?> activity) {
    for (int i = 0; i < mActivities.size(); i++) {
      if (getActivitys().get(i).getClass().equals(activity)) {
        return (T) getActivitys().get(i);
      }
    }
    return null;
  }

  public AppCompatActivity getCurActivity() {
    return mActivities.get(mActivities.size() - 1);
  }

  /**
   * 退出程序
   */
  public void killAll() {
    LinkedList<AppCompatActivity> copy;
    synchronized (AppCompatActivity.class) {
      copy = new LinkedList<>(mActivities);
    }
    for (AppCompatActivity baseAppCompatActivity : copy) {
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
