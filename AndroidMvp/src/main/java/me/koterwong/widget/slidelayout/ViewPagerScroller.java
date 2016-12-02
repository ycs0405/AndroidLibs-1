/*
 * ViewPagerScroller     2016/9/29-09-29
 * Copyright (c) 2016 KOTERWONG All right reserved
 */
package me.koterwong.widget.slidelayout;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

/**
 * Created by Koterwong on 2016/9/29 17:02
 */
public class ViewPagerScroller extends Scroller {
  private int mDuration = SlideLayoutConfig.DURATION;

  public ViewPagerScroller(Context context) {
    super(context);
  }

  public ViewPagerScroller(Context context, Interpolator interpolator) {
    super(context, interpolator);
  }

  public ViewPagerScroller(Context context, Interpolator interpolator, boolean flywheel) {
    super(context, interpolator, flywheel);
  }

  @Override
  public void startScroll(int startX, int startY, int dx, int dy, int duration) {
    super.startScroll(startX, startY, dx, dy, mDuration);
  }

  @Override
  public void startScroll(int startX, int startY, int dx, int dy) {
    super.startScroll(startX, startY, dx, dy, mDuration);
  }

  public void setDuration(int time) {
    mDuration = time;
  }
}
