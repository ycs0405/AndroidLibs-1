/*
 * MyProgressSubscriber     2016/11/21 08:40
 * Copyright (c) 2016 Koterwong All right reserved
 */
package me.koterwong.net;


import me.koterwong.mvp.BaseContract;

/**
 * Created by Koterwong on 2016/11/21 08:40
 *
 * 使用该Subscriber
 */
public abstract class MyProgressSubscriber<T> extends MySubscriber<T> {
  private BaseContract.View mBaseView;

  public MyProgressSubscriber(BaseContract.View baseView) {
    mBaseView = baseView;
  }

  @Override public void onStart() {
    mBaseView.showProgressDialog();
    super.onStart();
  }

  @Override public void _onCompleted() {
    super._onCompleted();
    mBaseView.dismissProgressDialog();
  }
}
