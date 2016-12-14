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
 *
 * 在RxJava中，Subject同时充当Observer 和 Observable 的角色。而SerializeSubject实现线程安全。
 * 注意：在生命周期结束的地方，取消这个事件，防止RxJava引起的内存泄漏问题。
 *
 * 参考：http://www.jianshu.com/p/ca090f6e2fe2
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

    // ofType操作符只发射指定类型的数据，其内部就是filter+cast
    // filter操作符可以使你提供一个指定的测试数据项，只有通过测试的数据才会被“发射”。
    // cast操作符可以将一个Observable转换成指定类型的Observable。
    return bus.ofType(eventType);
  }
}
