/*
 * OnLoadingAndRetryListener     2016/9/26-09-26
 * Copyright (c) 2016 KOTERWONG All right reserved
 */
package me.koterwong.widget.stateview;

import android.view.View;

/**
 * Created by Koterwong on 2016/9/26 14:38
 *
 * 通过该抽象类可以自定义 加载中、重试、数据为空界面，并且可以设置所有View的事件。
 *
 * for this class ,you can define your loading retry and empty view,also
 * can bind view event.
 */
public abstract class OnStateViewCreate {

  /**
   * 在这里绑定重试View的事件
   *
   * @param retryView
   */
  public abstract void setRetryEvent(View retryView);

  /**
   * 在这里绑定加载中View的事件
   *
   * @param loadingView
   */
  public void setLoadingEvent(View loadingView) {}

  /**
   * 在这里绑定数据未空的界面
   *
   * @param emptyView
   */
  public void setEmptyEvent(View emptyView) {}

  /**
   * 定制加载中界面，如果需要复写即可。
   *
   * @return
   */
  public int generateLoadingLayoutId() {
    return StateViewMgr.NO_VIEW_ID;
  }

  /**
   * 定制重试界面，如果需要复写即可。
   *
   * @return
   */
  public int generateRetryLayoutId() {
    return StateViewMgr.NO_VIEW_ID;
  }

  /**
   * 定制数据为空界面，如果需要复写即可。
   *
   * @return
   */
  public int generateEmptyLayoutId() {
    return StateViewMgr.NO_VIEW_ID;
  }

  /**
   * 定制加载中界面，如果需要复写即可。
   *
   * @return
   */
  public View generateLoadingView() {
    return null;
  }

  /**
   * 定制重试界面，如果需要复写即可。
   *
   * @return
   */
  public View generateRetryView() {
    return null;
  }

  /**
   * 定制数据为空界面，如果需要复写即可。
   *
   * @return
   */
  public View generateEmptyView() {
    return null;
  }

  /**
   * 判断是否设置过 LoadingView
   *
   * @return true is set
   */
  public boolean isCreateLoadingView() {
    return generateLoadingLayoutId() != StateViewMgr.NO_VIEW_ID
        || generateLoadingView() != null;
  }

  /**
   * 判断是否设置过 RetryView
   *
   * @return true is set
   */
  public boolean isCreateRetryView() {
    return generateRetryLayoutId() != StateViewMgr.NO_VIEW_ID
        || generateRetryView() != null;
  }

  /**
   * 判断是否设置过 EmptyView
   *
   * @return true is set
   */
  public boolean isCreateEmptyView() {
    return generateEmptyLayoutId() != StateViewMgr.NO_VIEW_ID
        || generateEmptyView() != null;
  }
}
