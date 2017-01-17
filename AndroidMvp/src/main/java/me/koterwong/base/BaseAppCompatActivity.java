/*
 * BaseActivity     2016/9/20-09-20
 * Copyright (c) 2016 KOTERWONG All right reserved
 */
package me.koterwong.base;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;

import javax.inject.Inject;

import me.koterwong.R;
import me.koterwong.annotation.KMainActivity;
import me.koterwong.common.LogKw;
import me.koterwong.di.component.AppComponent;
import me.koterwong.mvp.BaseContract;
import me.koterwong.mvp.BasePresenter;
import me.koterwong.utils.IntentHandler;
import me.koterwong.widget.TipsToast;
import me.koterwong.widget.dialog.spotsdialog.SpotsDialog;

/**
 * Created by Koterwong on 2016/9/20 10:16
 */
public abstract class BaseAppCompatActivity<P extends BasePresenter, D extends ViewDataBinding>
    extends AppCompatActivity implements BaseContract.View {

  protected final String TAG = this.getClass().getSimpleName();
  protected BaseApplication mAppContext; //appContext 对象
  protected Context mContext; // 当前Activity对象
  protected AppComponent mAppComponent;

  private IntentHandler mIntentHandler;

  @Inject
  protected P mPresenter;
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

  protected void setStatusBar() {}

  protected abstract void setupTitle();

  protected abstract void injectComponent(AppComponent appComponent);

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

    if (mPresenter != null) {
      mPresenter.onDestroy();//释放资源
    }
  }

  @Deprecated
  protected IntentHandler getIntentHandler() {
    if (mIntentHandler == null) {
      mIntentHandler = new IntentHandler(this);
    }
    return mIntentHandler;
  }

  private long exitTime;

  @Override public boolean onKeyDown(int keyCode, KeyEvent event) {
    if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
      if (KMainActivity.Helper.isMainActivity(this)) {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
          Toast.makeText(mAppContext, "再按一次退出程序", Toast.LENGTH_SHORT).show();
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
}
