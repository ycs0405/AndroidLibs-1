/*
 * AnimatorView     2016/10/8 09:31
 * Copyright (c) 2016 Koterwong All right reserved
 */
package me.koterwong.widget.dialog.spotsdialog;

import android.content.Context;
import android.view.View;

/**
 * Created by Koterwong on 2016/10/8 09:31
 */
class AnimatedView extends View{
  private int target;

  public AnimatedView(Context context) {
    super(context);
  }

  public float getXFactor() {
    return getX() / target;
  }

  public void setXFactor(float xFactor) {
    setX(target * xFactor);
  }

  public void setTarget(int target) {
    this.target = target;
  }

  public int getTarget() {
    return target;
  }
}
