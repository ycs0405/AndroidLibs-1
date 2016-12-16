/*
 * BaseActivity     2016/9/20-09-20
 * Copyright (c) 2016 KOTERWONG All right reserved
 */
package me.koterwong.base;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;

import javax.inject.Inject;

import butterknife.ButterKnife;
import me.koterwong.annotation.KMainActivity;
import me.koterwong.common.LogKw;
import me.koterwong.di.component.AppComponent;
import me.koterwong.mvp.BasePresenter;
import me.koterwong.statusbartint.StatusBarCompat;
import me.koterwong.utils.IntentHandler;

/**
 * Created by Koterwong on 2016/9/20 10:16
 */
public abstract class BaseAppCompatActivity<P extends BasePresenter> extends AppCompatActivity {
  protected final String TAG = this.getClass().getSimpleName();
  private IntentHandler mIntentHandler;
  protected BaseApplication mApplication;

  @Inject
  protected P mPresenter;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    LogKw.e("Current BackStack Topic Activity is ---> " + this.getClass().getSimpleName());
    mApplication = (BaseApplication) getApplication();
    synchronized (BaseAppCompatActivity.class) {
      mApplication.addActivity(this);
    }
    setContentView(getLayoutId());
    setStatusBar(); //设置状态栏
    ButterKnife.bind(this);
    injectComponent(mApplication.getAppComponent()); //设置Dagger2 注入。
    initField();   //初始化activity需要的变量 。
    initData();   //初始化网络数据。
  }

  @LayoutRes
  protected abstract int getLayoutId();

  protected void setStatusBar() {
    StatusBarCompat.setColor(this, Color.parseColor("#FF0000"));
  }

  protected abstract void injectComponent(AppComponent appComponent);

  protected void initField() {}

  protected void initData() {}

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

  @Deprecated
  protected IntentHandler getIntentHandler() {
    if (mIntentHandler == null) {
      mIntentHandler = new IntentHandler(this);
    }
    return mIntentHandler;
  }

  private long exitTime ;

  @Override public boolean onKeyDown(int keyCode, KeyEvent event) {
    if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
      if (KMainActivity.Helper.isMainActivity(this)) {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
          Toast.makeText(mApplication, "再按一次退出程序", Toast.LENGTH_SHORT).show();
          exitTime = System.currentTimeMillis();
          return true;
        }
      }
    }
    return super.onKeyDown(keyCode, event);
  }

}
