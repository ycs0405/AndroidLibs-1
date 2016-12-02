/*
 * ProgressLayout     2016/10/8 09:29
 * Copyright (c) 2016 Koterwong All right reserved
 */
package me.koterwong.widget.dialog.spotsdialog;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import me.koterwong.R;

/**
 * Created by Koterwong on 2016/10/8 09:29
 */
public class ProgressLayout extends FrameLayout {
  private static final int DEFAULT_COUNT = 3;
  private int spotsCount = DEFAULT_COUNT;

  public ProgressLayout(Context context) {
    this(context, null);
  }

  public ProgressLayout(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public ProgressLayout(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(attrs, defStyleAttr, 0);
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  public ProgressLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    init(attrs, defStyleAttr, defStyleRes);
  }

  private void init(AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    TypedArray a = getContext().getTheme().obtainStyledAttributes(
        attrs,
        R.styleable.SpotsDialog,
        defStyleAttr, defStyleRes);
    spotsCount = a.getInt(R.styleable.SpotsDialog_DialogSpotCount, DEFAULT_COUNT);
    a.recycle();
  }

  public int getSpotsCount() {
    return spotsCount;
  }
}
