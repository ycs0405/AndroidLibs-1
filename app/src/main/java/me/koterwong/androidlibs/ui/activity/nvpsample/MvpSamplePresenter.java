/*
 * MvpSamplePresenter     2016/12/15 19:53
 * Copyright (c) 2016 Koterwong All right reserved
 */
package me.koterwong.androidlibs.ui.activity.nvpsample;

import me.koterwong.mvp.BasePresenter;
import rx.Subscriber;

/**
 * Created by Koterwong on 2016/12/15 19:53
 */
public class MvpSamplePresenter extends BasePresenter<MvpSampleModel,MvpSampleContract.View> implements MvpSampleContract.Presenter{

  public MvpSamplePresenter(MvpSampleModel model, MvpSampleContract.View view) {
    super(model, view);
  }

  @Override public void loadData() {
    mModel.loadData()
        .subscribe(new Subscriber<String>() {
          @Override public void onCompleted() {

          }

          @Override public void onError(Throwable e) {
            mView.showText(e.getMessage());
          }

          @Override public void onNext(String s) {
            mView.showText(s);
          }
        });
  }
}
