/*
 * BaseFworkFragment     2016/9/20-09-20
 * Copyright (c) 2016 KOTERWONG All right reserved
 */
package me.koterwong.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import me.koterwong.mvp.BasePresenter;
import me.koterwong.utils.IntentHandler;

/**
 * Created by Koterwong on 2016/9/20 14:41
 */
public abstract class BaseAppSupportFragment<P extends BasePresenter, D extends ViewDataBinding>
    extends Fragment {

  protected final String TAG = this.getClass().getSimpleName();
  private IntentHandler mIntentHandler;
  protected AppCompatActivity mActivity;
  protected View mRootView;

  @Inject
  protected P mPresenter;
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
  }

  protected IntentHandler getIntentHandler() {
    if (mIntentHandler == null) {
      mIntentHandler = new IntentHandler(mActivity);
    }

    return mIntentHandler;
  }

  @Override public void onDestroy() {
    super.onDestroy();
    if (mPresenter != null) mPresenter = null;
  }

  protected abstract int getLayoutId();

  protected abstract void ComponentInject();

  protected void initField() {}

  protected void initData() {}

}
