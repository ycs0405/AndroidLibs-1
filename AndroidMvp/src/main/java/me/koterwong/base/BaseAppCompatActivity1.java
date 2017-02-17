/*
 * BaseActivity     2016/9/20-09-20
 * Copyright (c) 2016 KOTERWONG All right reserved
 */
package me.koterwong.base;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;

import me.koterwong.R;
import me.koterwong.annotation.KMainActivity;
import me.koterwong.common.LogKw;
import me.koterwong.di.component.AppComponent;
import me.koterwong.mvp.BaseContract;
import me.koterwong.statusbartint.StatusBarCompat;
import me.koterwong.utils.IntentHandler;
import me.koterwong.utils.KeyboardUtils;
import me.koterwong.widget.TipsToast;
import me.koterwong.widget.dialog.spotsdialog.SpotsDialog;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Koterwong on 2016/9/20 10:16
 */
public abstract class BaseAppCompatActivity1<D extends ViewDataBinding> extends AppCompatActivity
    implements BaseContract.View {
  protected final String TAG = this.getClass().getSimpleName();
  protected BaseApplication mAppContext;
  protected Context mContext;
  protected AppComponent mAppComponent;
  private CompositeSubscription mCompositeSubscription;

  private IntentHandler mIntentHandler;

  protected D mDataBinding;

  private TipsToast mTipsToast;
  private SpotsDialog mSpotsDialog;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    LogKw.e("Current BackStack Topic Activity is ---> " + this.getClass().getSimpleName());
    mAppContext = (BaseApplication) getApplication();
    mContext = this;
    synchronized (BaseAppCompatActivity.class) {
      mAppContext.addActivity(this);
    }
    mDataBinding = setContentView();
    setStatusBar(); //设置状态栏
    setupTitle();
    mAppComponent = mAppContext.getAppComponent();
    injectComponent(mAppComponent); //设置Dagger2 注入。
    initField();   //初始化activity需要的变量 。
    initData();   //初始化网络数据。
    initRxBus();
  }

  protected D setContentView() {
    return DataBindingUtil.setContentView(this, getLayoutId());
  }

  @LayoutRes
  protected abstract int getLayoutId();

  protected void setStatusBar() {
    StatusBarCompat.setColor(this, Color.parseColor("#FF0000"));
  }

  protected void setupTitle() {}

  protected void injectComponent(AppComponent appComponent) {}

  protected void initField() {}

  protected void initData() {}

  protected void initRxBus() {}

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
      mAppContext.removeActivity(this);
    }
    if (mCompositeSubscription != null && !mCompositeSubscription.isUnsubscribed()) {
      mCompositeSubscription.unsubscribe();
      mCompositeSubscription = null;
    }
  }

  protected IntentHandler getIntentHandler() {
    if (mIntentHandler == null) {
      mIntentHandler = new IntentHandler(this);
    }
    return mIntentHandler;
  }

  protected void addSubscrebe(Subscription subscription) {
    if (mCompositeSubscription == null) {
      mCompositeSubscription = new CompositeSubscription();
    }
    mCompositeSubscription.add(subscription);//将所有subscription放入,集中处理
  }

  private long exitTime;

  @Override public boolean onKeyDown(int keyCode, KeyEvent event) {
    if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
      if (KMainActivity.Helper.isMainActivity(this)) {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
          showToast("再按一次退出程序");
          exitTime = System.currentTimeMillis();
          return true;
        }
      }

      if (mSpotsDialog != null && mSpotsDialog.isShowing()) {
        mSpotsDialog.dismiss();
      }
    }
    return super.onKeyDown(keyCode, event);
  }


  //////////////////////////////
  ////// 通的View操作 /////////
  //////////////////////////////
  @Override public void showProgressDialog() {
    if (mSpotsDialog == null) {
      mSpotsDialog = new SpotsDialog(mContext, getString(R.string.loading_tip));
    }
    mSpotsDialog.show();
  }

  @Override public void showProgressDialog(String msg) {
    if (mSpotsDialog == null) {
      mSpotsDialog = new SpotsDialog(mContext);
    }
    mSpotsDialog.setMessage(msg);
    mSpotsDialog.show();
  }

  @Override public void dismissProgressDialog() {
    if (mSpotsDialog != null) {
      mSpotsDialog.dismiss();
    }
  }

  @Override public void showToast(String msg) {
    if (mTipsToast == null) {
      mTipsToast = TipsToast.makeText(this, msg, Toast.LENGTH_SHORT);
    } else {
      mTipsToast.setText(msg);
    }
    mTipsToast.show();
  }

  @Override public void showSnakeBar(String msg) {
    Snackbar.make(findViewById(android.R.id.content), msg, Snackbar.LENGTH_SHORT).show();
  }

  /**
   * 屏幕变亮
   */
  public void lightOn() {
    WindowManager.LayoutParams lp = getWindow().getAttributes();
    lp.alpha = 1.0f;
    getWindow().setAttributes(lp);
  }

  /**
   * 屏幕变暗
   */
  public void lightOff() {
    WindowManager.LayoutParams lp = getWindow().getAttributes();
    lp.alpha = 0.3f;
    getWindow().setAttributes(lp);
  }

  protected void hideKeyBord() {
    KeyboardUtils.hideSoftInput(this);
  }
}
