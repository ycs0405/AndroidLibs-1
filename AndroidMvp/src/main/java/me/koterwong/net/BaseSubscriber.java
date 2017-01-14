/*
 * MySubscriber     2016/11/20 10:01
 * Copyright (c) 2016 Koterwong All right reserved
 */
package me.koterwong.net;

import me.koterwong.R;
import me.koterwong.base.BaseApplication;
import me.koterwong.bean.Response;
import me.koterwong.utils.NetUtils;
import rx.Subscriber;

/**
 * Created by Koterwong on 2016/11/20 10:01
 *
 * 自定义Subscriber，封装通用操作
 */
public abstract class BaseSubscriber<T> extends Subscriber<Response<T>> {

  /**
   * 服务端返回错误数据，code != ApiErrorCode.SUCCESS_CODE 的返回
   *
   * @param e
   */
  public abstract void _onApiError(ApiErrorException e);

  /**
   * 请求过程中发生异常
   *
   * @param e Throwable
   */
  public abstract void _onError(Throwable e);

  /**
   * 服务端的正确应答，只有服务端返回码为 Success的时候调用该方法。
   *
   * @param data 响应结果
   */
  public abstract void _onNext(T data);

  /**
   * 请求结束，请求异常和请求成功都会调用该方法。
   * RxJava的onCompleted 和onError 只会回调一个，但这里这个方法必然会调用。
   */
  public abstract void _onCompleted();

  @Override public void onStart() {
    // 判断网络是否可用
    if (!NetUtils.isConnected(BaseApplication.get())) {
      _onApiError(new ApiErrorException(ApiErrorCode.ERROR_NO_INTERNET, BaseApplication.get().getString(R.string.network_unavailable)));
      _onCompleted();
      // TODO: 2016/11/20 考虑使用网络缓存，待优化
      unsubscribe(); //在任务开始前取消订阅
    }
  }

  @Override public void onCompleted() {
    _onCompleted();
  }

  @Override public void onError(Throwable e) {
    _onError(e);
    _onCompleted();
  }

  @Override public void onNext(Response<T> response) {
    if (response.isOk()) {
      if (response.getData() != null)
        _onNext(response.getData());
    } else {
      _onApiError(new ApiErrorException(response.getCode(), response.getMsg()));
    }
  }
}
