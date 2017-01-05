/*
 * BaseContract     2016/11/28 09:19
 * Copyright (c) 2016 Koterwong All right reserved
 */
package me.koterwong.mvp;

import rx.Subscription;

/**
 * Created by Koterwong on 2016/11/28 09:19
 */
public interface BaseContract {
  interface Model {
    void onDestroy();
  }

  interface Presenter {
    void onStart();

    void onDestroy();

    void unSubscribe(Subscription subscription);
  }

  interface View {
    void showProgressDialog();

    void showProgressDialog(String msg);

    void dismissProgressDialog();

    void showToast(String msg);

    void showSnakeBar(String msg);
  }
}
