/*
 * BaseFworkFragment     2016/9/20-09-20
 * Copyright (c) 2016 KOTERWONG All right reserved
 */
package me.koterwong.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import me.koterwong.mvp.BasePresenter;

/**
 * Created by Koterwong on 2016/9/20 14:41
 */
public abstract class BaseSupFragment<P extends BasePresenter> extends Fragment {
  protected final String TAG = this.getClass().getSimpleName();

  protected BaseAppCompatActivity mActivity;
  protected View mRootView;

  @Inject P mPresenter;

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
      savedInstanceState) {
    mActivity = (BaseAppCompatActivity) getActivity();
    mRootView = inflater.inflate(getLayoutId(), container, false);
    return mRootView;
  }

  @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    ComponentInject();
    initData();
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
  }

  @Override public void onDestroy() {
    super.onDestroy();
    if (mPresenter != null) mPresenter = null;
  }

  protected abstract int getLayoutId();

  protected abstract void ComponentInject();

  protected abstract void initData();

}
