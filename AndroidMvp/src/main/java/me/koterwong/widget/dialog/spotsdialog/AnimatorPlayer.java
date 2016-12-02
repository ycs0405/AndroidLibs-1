/*
 * AnimatorPalyer     2016/10/8 09:32
 * Copyright (c) 2016 Koterwong All right reserved
 */
package me.koterwong.widget.dialog.spotsdialog;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;

/**
 * Created by Koterwong on 2016/10/8 09:32
 */
class AnimatorPlayer extends AnimatorListenerAdapter{
  private boolean interrupted = false;
  private Animator[] animators;

  AnimatorPlayer(Animator[] animators) {
    this.animators = animators;
  }

  @Override public void onAnimationEnd(Animator animation) {
    if (!interrupted) animate();
  }

  void play() {
    animate();
  }

  void stop() {
    interrupted = true;
  }

  private void animate() {
    AnimatorSet set = new AnimatorSet();
    set.playTogether(animators);
    set.addListener(this);
    set.start();
  }
}
