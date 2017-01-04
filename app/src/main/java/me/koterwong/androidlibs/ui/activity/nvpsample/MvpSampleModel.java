/*
 * MvpSampleModel     2016/12/15 19:54
 * Copyright (c) 2016 Koterwong All right reserved
 */
package me.koterwong.androidlibs.ui.activity.nvpsample;

import me.koterwong.api.ServiceManager;
import me.koterwong.mvp.BaseModel;
import me.koterwong.rx.RxSchedulersHelper;
import rx.Observable;

/**
 * Created by Koterwong on 2016/12/15 19:54
 */
public class MvpSampleModel extends BaseModel {

  public MvpSampleModel(ServiceManager serviceManager) {
    super(serviceManager);
  }

  public Observable<String> loadData() {
    return mServiceManager.getApiService().getBaiduData().compose(RxSchedulersHelper.<String>io_main());
  }

}
