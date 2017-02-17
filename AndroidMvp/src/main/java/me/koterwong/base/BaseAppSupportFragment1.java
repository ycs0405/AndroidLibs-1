/*
 * BaseFragment1     2017/1/5 10:35
 * Copyright (c) 2017 Koterwong All right reserved
 */
package me.koterwong.base;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import javax.inject.Inject;

import me.koterwong.R;
import me.koterwong.mvp.BaseContract;
import me.koterwong.utils.IntentHandler;
import me.koterwong.widget.TipsToast;
import me.koterwong.widget.dialog.spotsdialog.SpotsDialog;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Koterwong on 2017/1/5 10:35
 */
public abstract class BaseAppSupportFragment1<D extends ViewDataBinding> extends Fragment
    implements BaseContract.View {
  protected final String TAG = this.getClass().getSimpleName();
  private IntentHandler mIntentHandler;
  protected AppCompatActivity mActivity;
  protected View mRootView;
  private CompositeSubscription mCompositeSubscription;
  private TipsToast mTipsToast;
  private SpotsDialog mSpotsDialog;
  @Inject
  protected D mDataBinding;

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
      savedInstanceState) {
    mActivity = (AppCompatActivity) getActivity();
    mRootView = inflater.inflate(getLayoutId(), container, false);
    mDataBinding = DataBindingUtil.bind(mRootView);
    return mRootView;
  }

  @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    ComponentInject();
    initField();
    initData();
    initRxBus();
  }

  protected IntentHandler getIntentHandler() {
    if (mIntentHandler == null) {
      mIntentHandler = new IntentHandler(mActivity);
    }

    return mIntentHandler;
  }

  protected abstract void ComponentInject();

  protected abstract int getLayoutId();

  protected void initField() {}

  protected void initData() {}

  protected void initRxBus() {}

  protected void addSubscrebe(Subscription subscription) {
    if (mCompositeSubscription == null) {
      mCompositeSubscription = new CompositeSubscription();
    }
    mCompositeSubscription.add(subscription);//将所有subscription放入,集中处理
  }


  @Override public void onDestroy() {
    super.onDestroy();
    if (mCompositeSubscription != null && !mCompositeSubscription.isUnsubscribed()) {
      mCompositeSubscription.unsubscribe();
      mCompositeSubscription = null;
    }
  }


  @Override public void showProgressDialog() {
    if (mSpotsDialog == null) {
      mSpotsDialog = new SpotsDialog(mActivity, getString(R.string.loading_tip));
    }
    mSpotsDialog.show();
  }

  @Override public void showProgressDialog(String msg) {
    if (mSpotsDialog == null) {
      mSpotsDialog = new SpotsDialog(mActivity, msg);
    }
    mSpotsDialog.show();
  }

  @Override public void dismissProgressDialog() {
    if (mSpotsDialog != null) {
      mSpotsDialog.dismiss();
    }
  }

  @Override public void showToast(String msg) {
    if (mTipsToast == null) {
      mTipsToast = TipsToast.makeText(mActivity, msg, Toast.LENGTH_SHORT);
    } else {
      mTipsToast.setText(msg);
    }
    mTipsToast.show();
  }

  @Override public void showSnakeBar(String msg) {

  }

  protected void hideSoftInput(View view) {
    InputMethodManager inputMethodManager = (InputMethodManager) mActivity.getSystemService(Activity.INPUT_METHOD_SERVICE);
    if (inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0)) {
      inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
    }
  }

  protected void showSoftInput(View view) {
    view.setFocusable(true);
    view.setFocusableInTouchMode(true);
    view.requestFocus();
    InputMethodManager imm = (InputMethodManager) mActivity.getSystemService(Activity.INPUT_METHOD_SERVICE);
    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
  }
}
