/*
 * SpotsDialog     2016/10/7 16:19
 * Copyright (c) 2016 Koterwong All right reserved
 */
package me.koterwong.widget.dialog.spotsdialog;


import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v7.app.AlertDialog;
import android.widget.TextView;

import me.koterwong.R;

/**
 * Created by Koterwong on 2016/10/7 16:19
 */
public class SpotsDialog extends AlertDialog {
  private static final String TAG = SpotsDialog.class.getSimpleName();

  private static final int DELAY = 150;
  private static final int DURATION = 1500;

  private int size;
  private AnimatedView[] spots;
  private AnimatorPlayer animator;
  private CharSequence message;

  public SpotsDialog(@NonNull Context context) {
    this(context, R.style.DefaultSpotsDialog);
  }

  public SpotsDialog(@NonNull Context context, @StyleRes int themeResId) {
    super(context, themeResId);
  }

  public SpotsDialog(@NonNull Context context, CharSequence message) {
    this(context);
    this.message = message;
  }

  public SpotsDialog(@NonNull Context context, CharSequence message, @StyleRes int theme) {
    this(context, theme);
    this.message = message;
  }

  public SpotsDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener
      cancelListener) {
    super(context, cancelable, cancelListener);
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.widge_spots_dialog);
    setCanceledOnTouchOutside(false);
    this.initMessage();
    this.intiProgress();
  }

  @Override protected void onStart() {
    super.onStart();
    animator = new AnimatorPlayer(createAnimations());
    animator.play();
  }

  private Animator[] createAnimations() {
    Animator[] animators = new Animator[size];
    for (int i = 0; i < spots.length; i++) {
      Animator move = ObjectAnimator.ofFloat(spots[i], "xFactor", 0, 1);
      move.setDuration(DURATION);
      move.setInterpolator(new HesitateInterpolator());
      move.setStartDelay(DELAY * i);
      animators[i] = move;
    }
    return animators;
  }

  @Override protected void onStop() {
    super.onStop();
    animator.stop();
  }

  @Override public void setMessage(CharSequence message) {
    ((TextView) findViewById(R.id.spots_dialog_title)).setText(message);
  }

  private void intiProgress() {
    ProgressLayout progress = (ProgressLayout) findViewById(R.id.spots_dialog_progress);
    size = progress.getSpotsCount();

    spots = new AnimatedView[size];
    int size = getContext().getResources().getDimensionPixelSize(R.dimen.spot_size);
    int progressWidth = getContext().getResources().getDimensionPixelSize(R.dimen.progress_width);
    for (int i = 0; i < spots.length; i++) {
      AnimatedView v = new AnimatedView(getContext());
      v.setBackgroundResource(R.drawable.shape_spots_dialog_progress);
      v.setTarget(progressWidth);
      v.setXFactor(-1f);
      progress.addView(v, size, size);
      spots[i] = v;
    }
  }

  private void initMessage() {
    if (message != null && message.length() > 0) {
      ((TextView) findViewById(R.id.spots_dialog_title)).setText(message);
    }
  }
}
