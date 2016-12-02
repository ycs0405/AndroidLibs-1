/*
 * MySubscriber     2016/11/21 08:20
 * Copyright (c) 2016 Koterwong All right reserved
 */
package me.koterwong.net;

/**
 * Created by Koterwong on 2016/11/21 08:20
 */
public abstract class MySubscriber<T> extends BaseSubscriber<T> {

  /**
   * 处理Api的错误返回。如果需要针对不同的返回码做不同的操作覆盖该方法即可。
   *
   * @param e apiError
   */
  @Override public void _onApiError(ApiErrorException e) {
    ApiErrorHelper.handleApiError(e);
  }

  /**
   * RxJava网络请求异常的错误处理。
   *
   * @param e Throwable
   */
  @Override public void _onError(Throwable e) {
    ApiErrorHelper.handleCommonError(e);
  }

  /**
   * 请求结束。
   */
  @Override public void _onCompleted() {

  }
}
