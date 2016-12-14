/*
 * RxSchedulersHelper     2016/12/4 19:36
 * Copyright (c) 2016 Koterwong All right reserved
 */
package me.koterwong.rx;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Koterwong on 2016/12/4 19:36
 *
 * 处理RxJava线程。
 */
public class RxSchedulersHelper {

  public static <T> Observable.Transformer<T, T> io_main() {
    return new Observable.Transformer<T, T>() {
      @Override   public Observable<T> call(Observable<T> tObservable) {
        return tObservable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread());
      }
    };
  }
}
