/*
 * KeyboardListenRelativeLayout     2017/1/21 16:24
 * Copyright (c) 2017 Koterwong All right reserved
 */
package me.koterwong.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Created by Koterwong on 2017/1/21 16:24
 *
 * 页面尺寸变化监听类, 继承RelativeLayout，  当页面尺寸变化时， 如软键盘弹出 ，通过该类可以监听到
 */
public class KeyboardListenRelativeLayout extends RelativeLayout {

  public KeyboardListenRelativeLayout(Context context) {
    super(context);
  }

  public KeyboardListenRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
  }

  public KeyboardListenRelativeLayout(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  private onKeyboardStateChangedListener mListener;

  /**
   * 当页面尺寸变化时， 如软键盘弹出 ，通过改接口进行回调
   */
  public interface onKeyboardStateChangedListener {
    /**
     * Description: 尺寸变化回调方法
     */
    void onKeyboardStateChanged(boolean isKeyboardShow);

  }

  /**
   * Description: 设置页面尺寸变化监听
   */
  public void setOnKeyboardStateChangedListener(onKeyboardStateChangedListener listener) {
    mListener = listener;
  }

  @Override
  protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);

    if (mListener != null) {
      if (h < oldh) {
        mListener.onKeyboardStateChanged(true);
      } else {
        mListener.onKeyboardStateChanged(false);
      }
    }
  }
}
