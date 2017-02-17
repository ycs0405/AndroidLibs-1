/*
 * PerfectClickListener     2017/2/10 15:52
 * Copyright (c) 2017 Koterwong All right reserved
 */
package me.koterwong.utils;

import android.view.View;

/**
 * Created by Koterwong on 2017/2/10 15:52
 */
public abstract class PerfectClickListener implements View.OnClickListener {

  @Override public void onClick(View view) {
    if (!CommonUtil.isLeastSingleClick()) {
      return;
    }
    onNoDoubleClick(view);
  }

  protected abstract void onNoDoubleClick(View v);
}
