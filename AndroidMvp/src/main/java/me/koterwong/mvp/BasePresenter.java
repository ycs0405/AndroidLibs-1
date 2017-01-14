/*
 * BasePresenter     2016/9/20-09-20
 * Copyright (c) 2016 KOTERWONG All right reserved
 */
package me.koterwong.mvp;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Koterwong on 2016/9/20 13:58
 *
 * presenter层传入Model和View泛型获取Model和View。
 * 在每一个页面对应的Module中，提供Model和View的引用。
 */
public class BasePresenter<M extends BaseModel, V extends BaseContract.View> implements BaseContract.Presenter {

  protected final String TAG = this.getClass().getSimpleName();
  protected CompositeSubscription mCompositeSubscription;

  protected M mModel;
  protected V mView;

  public BasePresenter(V view) {
    this.mView = view;
    onStart();
  }

  public BasePresenter(M model, V view) {
    this.mModel = model;
    this.mView = view;
    onStart();
  }

  @Override public void onStart() {

  }

  /**
   * this method while called when activity destroyed.
   */
  @Override public void onDestroy() {
    unSubscribe();//解除订阅
    this.mModel.onDestroy();
    this.mModel = null;
    this.mView = null;
  }

  protected void handleError(Throwable throwable) {}

  protected void addSubscrebe(Subscription subscription) {
    if (mCompositeSubscription == null) {
      mCompositeSubscription = new CompositeSubscription();
    }
    mCompositeSubscription.add(subscription);//将所有subscription放入,集中处理
  }

  protected void unSubscribe() {
    if (mCompositeSubscription != null) {
      mCompositeSubscription.unsubscribe();//保证activity结束时取消所有正在执行的订阅
    }
  }

  @Override public void unSubscribe(Subscription subscription) {
    if (subscription != null && !subscription.isUnsubscribed()) {
      subscription.unsubscribe();//保证activity结束时取消所有正在执行的订阅
    }
  }
}
