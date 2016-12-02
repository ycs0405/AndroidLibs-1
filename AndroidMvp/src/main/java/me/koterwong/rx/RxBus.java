/*
 * RxBus     2016/10/2-10-02
 * Copyright (c) 2016 KOTERWONG All right reserved
 */
package me.koterwong.rx;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * Created by Koterwong on 2016/10/2 14:02
 */
public class RxBus {
  private static volatile RxBus defaultInstance;

  private final Subject<Object, Object> bus;

  // PublishSubject只会把在订阅发生的时间点之后来自原始Observable的数据发射给观察者
  private RxBus() {
    bus = new SerializedSubject<>(PublishSubject.create());
  }

  public static RxBus getDefault() {
    if (defaultInstance == null) {
      synchronized (RxBus.class) {
        if (defaultInstance == null) {
          defaultInstance = new RxBus();
        }
      }
    }
    return defaultInstance;
  }

  // 发送一个新的事件
  public void post(Object o) {
    bus.onNext(o);
  }

  // 根据传递的 eventType 类型返回特定类型(eventType)的 被观察者
  public <T> Observable<T> toObservable(Class<T> eventType) {
    return bus.ofType(eventType);
  }
}
