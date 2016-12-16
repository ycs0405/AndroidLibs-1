/*
 * MvpSampleContract     2016/12/15 19:51
 * Copyright (c) 2016 Koterwong All right reserved
 */
package me.koterwong.androidlibs.ui.activity.nvpsample;

import me.koterwong.mvp.BaseContract;

/**
 * Created by Koterwong on 2016/12/15 19:51
 */
public interface MvpSampleContract {

  public interface View extends BaseContract.View{
    public void showText(String s);
  }


  interface Presenter{
    public void loadData();
  }
}
