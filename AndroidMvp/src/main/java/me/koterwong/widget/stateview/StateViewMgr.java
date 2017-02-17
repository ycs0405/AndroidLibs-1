/*
 * EmptyViewManager     2016/9/26-09-26
 * Copyright (c) 2016 KOTERWONG All right reserved
 */
package me.koterwong.widget.stateview;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Koterwong on 2016/9/26 14:31
 */
public class StateViewMgr {
  public static final int NO_VIEW_ID = 0;
  public static int BASE_VIEW_LOADING_ID = NO_VIEW_ID;
  public static int BASE_VIEW_RETRY_ID = NO_VIEW_ID;
  public static int BASE_VIEW_EMPTY_ID = NO_VIEW_ID;

  public StateView mStateView;

  public OnStateViewCreate DEFAULT_LISTENER = new OnStateViewCreate() {
    @Override public void setRetryEvent(View retryView) {

    }
  };

  /**
   * 初始化DataEmptyView
   *
   * @param actOrFraOrView activity fragment or view
   * @param listener       dataEmptyListener
   * @return dataEmptyListener
   */
  public static StateViewMgr create(Object actOrFraOrView, OnStateViewCreate listener) {
    return new StateViewMgr(actOrFraOrView, listener);
  }

  private StateViewMgr(Object actOrFraOrView, OnStateViewCreate onViewCreate) {
    if (onViewCreate == null)
      onViewCreate = DEFAULT_LISTENER;
    ViewGroup contentParent = null;
    Context context;
    //get contentParent
    if (actOrFraOrView instanceof Activity) {
      Activity activity = (Activity) actOrFraOrView;
      context = activity;
      contentParent = (ViewGroup) activity.findViewById(android.R.id.content);
    } else if (actOrFraOrView instanceof Fragment) {
      Fragment fragment = (Fragment) actOrFraOrView;
      context = fragment.getActivity();
      contentParent = (ViewGroup) (fragment.getView().getParent());
    } else if (actOrFraOrView instanceof View) {
      View view = (View) actOrFraOrView;
      contentParent = (ViewGroup) (view.getParent());
      context = view.getContext();
    } else {
      throw new IllegalArgumentException("the argument's type must be Fragment Activity or View");
    }
    int childCount = contentParent.getChildCount();
    int index = 0;
    View contentView;
    if (actOrFraOrView instanceof View) {
      contentView = (View) actOrFraOrView;
      for (int i = 0; i < childCount; i++) {
        if (contentParent.getChildAt(i) == contentView) {
          index = i;
          break;
        }
      }
    } else {
      contentView = contentParent.getChildAt(0);
    }
    contentParent.removeView(contentView);  //remove orderContent
    //setup
    StateView stateView = new StateView(context);
    ViewGroup.LayoutParams oldLp = contentView.getLayoutParams();
    //add LoadingAndRetryLayout to contentParent
    contentParent.addView(stateView, index, oldLp);
    // setup empty retry loading view
    this.setupContentView(contentView, stateView);
    this.setupLoadingLayout(onViewCreate, stateView);
    this.setupRetryLayout(onViewCreate, stateView);
    this.setupEmptyLayout(onViewCreate, stateView);
    // callback
    onViewCreate.setRetryEvent(stateView.getRetryView());
    onViewCreate.setEmptyEvent(stateView.getEmptyView());
    onViewCreate.setLoadingEvent(stateView.getLoadingView());
    this.mStateView = stateView;
  }

  private void setupContentView(View oldView, StateView stateView) {
    stateView.addContentView(oldView);
  }

  private void setupEmptyLayout(OnStateViewCreate listener, StateView stateView) {
    if (listener.isCreateEmptyView()) {
      //通过onStateViewCreate自定义的EmptyView
      int layoutId = listener.generateEmptyLayoutId();
      if (layoutId != NO_VIEW_ID)
        stateView.addEmptyView(layoutId);
      else
        stateView.addEmptyView(listener.generateEmptyView());
    } else {
      if (BASE_VIEW_EMPTY_ID != NO_VIEW_ID)
        stateView.addEmptyView(BASE_VIEW_EMPTY_ID);
    }
  }

  private void setupRetryLayout(OnStateViewCreate listener, StateView stateView) {
    if (listener.isCreateRetryView()) {
      //通过onStateViewCreate自定义的RetryView
      int layoutId = listener.generateEmptyLayoutId();
      if (layoutId != NO_VIEW_ID)
        stateView.addRetryView(layoutId);
      else
        stateView.addRetryView(listener.generateRetryView());
    } else {
      if (BASE_VIEW_RETRY_ID != NO_VIEW_ID)
        stateView.addRetryView(BASE_VIEW_RETRY_ID);
    }
  }

  private void setupLoadingLayout(OnStateViewCreate listener, StateView stateView) {
    if (listener.isCreateLoadingView()) {
      //通过onStateViewCreate自定义的LoadingView
      int layoutId = listener.generateLoadingLayoutId();
      if (layoutId != NO_VIEW_ID)
        stateView.addLoadingView(layoutId);
      else
        stateView.addLoadingView(listener.generateLoadingView());
    } else {
      if (BASE_VIEW_LOADING_ID != NO_VIEW_ID)
        stateView.addLoadingView(BASE_VIEW_LOADING_ID);
    }
  }

  /**
   * make the loadingView visible ,and other gone
   */
  public void showLoadingView() {
    mStateView.showViewLoading();
  }

  /**
   * make the retryView visible ,and other gone
   */
  public void showRetryView() {
    mStateView.showViewRetry();
  }

  /**
   * make the contentView visible ,and other gone
   */
  public void showContentView() {
    mStateView.showContent();
  }

  /**
   * make the contentView visible ,and other gone
   */
  public void showEmptyView() {
    mStateView.showViewEmpty();
  }
}
