/*
 * BaseActivity     2016/9/20-09-20
 * Copyright (c) 2016 KOTERWONG All right reserved
 */
package me.koterwong.base;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.LayoutRes;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.umeng.analytics.MobclickAgent;

import javax.inject.Inject;

import butterknife.ButterKnife;
import me.koterwong.mvp.BasePresenter;
import me.koterwong.statusbartint.StatusBarCompat;
import me.koterwong.utils.IntentHandler;
import me.koterwong.common.LogKw;

/**
 * Created by Koterwong on 2016/9/20 10:16
 * <p/>
 * 对于每一个activity，默认注入Presenter，只需要传入对应的泛型。
 * 使用dagger2管理的应用，一般每个activity和fragment都有与之对应component和module，要使注入的presenter
 * 生效需要在对应的module中提供presenter对象或者使用@Inject标注presenter的构造函数。
 * <p/>
 * injectComponent ：实现该方法注入当前的activity到dagger2。
 */
public abstract class BaseAppCompatActivity<P extends BasePresenter> extends RxAppCompatActivity {
  protected final String TAG = this.getClass().getSimpleName();

  private IntentHandler mIntentHandler;
  protected BaseApplication mApplication;

  @Inject P mPresenter;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    LogKw.e("Current BackStack Topic Activity is ---> " + this.getClass().getSimpleName());

    mApplication = (BaseApplication) getApplication();

    synchronized (BaseAppCompatActivity.class) {
      mApplication.addActivity(this);
    }

    setContentView(getLayoutId());

    //设置状态栏
    setStatusBar();

    ButterKnife.bind(this);

    //设置Dagger2 注入。
    injectComponent();

    //初始化activity需要的变量 。
    initField();

    //初始化网络数据。
    initData();
  }

  @LayoutRes
  protected abstract int getLayoutId();

  protected void setStatusBar() {
    StatusBarCompat.setColor(this, Color.parseColor("#FF0000"));
  }

  protected abstract void injectComponent();

  protected void initField() {

  }

  protected void initData() {

  }

  @Override protected void onResume() {
    super.onResume();
    MobclickAgent.onResume(this);
  }

  @Override protected void onPause() {
    super.onPause();
    MobclickAgent.onPause(this);
  }

  @Override protected void onDestroy() {
    super.onDestroy();

    synchronized (BaseAppCompatActivity.class) {
      mApplication.removeActivity(this);
    }

    if (mPresenter != null) {
      mPresenter.onDestroy();//释放资源
    }

    ButterKnife.unbind(this);
  }

  protected IntentHandler getIntentHandler() {
    if (mIntentHandler == null) {
      mIntentHandler = new IntentHandler(this);
    }
    return mIntentHandler;
  }
}
