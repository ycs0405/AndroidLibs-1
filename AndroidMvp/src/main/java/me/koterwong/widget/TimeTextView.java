/*
 * TimeTextView     2016/10/22 17:44
 * Copyright (c) 2016 Koterwong All right reserved
 */
package me.koterwong.widget;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Koterwong on 2016/10/22 17:44
 *
 * 倒计时View
 */
public class TimeTextView extends TextView implements Runnable {

  private long[] times;
  private long mhour, mmin, msecond;// 天，小时，分钟，秒
  private boolean run = false; // 是否启动了
  private int textSize = 16;

  private int forgroundColor = Color.WHITE;
  private int backgroundColor = Color.BLACK;

  public TimeTextView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public TimeTextView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
  }

  public TimeTextView(Context context) {
    super(context);
  }

  public long[] getTimes() {
    return times;
  }

  public void setTimes(int seconds) {
    seconds = Math.abs(seconds);
    long[] times = new long[3];
    if (seconds == 0) {
      times[0] = 0;
      times[1] = 0;
      times[2] = 0;
    } else {
      times[0] = seconds / 3600;
      times[1] = (seconds % 3600) / 60;
      times[2] = (seconds % 3600) % 60;
    }

    this.times = times;
    mhour = times[0];
    mmin = times[1];
    msecond = times[2];
    showTime();
  }

  /**
   * 倒计时计算
   */
  private void computeTime() {
    if (mhour == 0 && mmin == 0 && msecond == 0) {
      run = false;
    } else {
      msecond--;
      if (msecond < 0) {
        mmin--;
        msecond = 59;
        if (mmin < 0) {
          mmin = 59;
          mhour--;
        }
      }
    }
  }

  public boolean isRun() {
    return run;
  }

  public void setRun(boolean run) {
    this.run = run;
  }

  public void setTextSize(int size) {
    textSize = size;
  }

  public void setTextColor(int forgroundColor, int backgroundColor) {
    this.forgroundColor = forgroundColor;
    this.backgroundColor = backgroundColor;
    showTime();
  }

  public interface OnTimeEndListener {
    void onTimeEnd();
  }

  private OnTimeEndListener onTimeEndListener;

  public void setOnTimeEndListener(OnTimeEndListener onTimeEndListener) {
    this.onTimeEndListener = onTimeEndListener;
  }

  @Override
  protected void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    run = false;
    removeCallbacks(this);
  }

  @Override
  public void run() {
    // 标示已经启动
    run = true;
    computeTime();
    showTime();
    if (run) {
      postDelayed(this, 1000);
    } else {
      if (null != onTimeEndListener) {
        onTimeEndListener.onTimeEnd();
      }
    }
  }

  public void showTime() {
    String strHour = mhour >= 10 ? String.valueOf(mhour) : ("0" + String.valueOf(mhour));
    String strMin = mmin >= 10 ? String.valueOf(mmin) : ("0" + String.valueOf(mmin));
    String strSecond = msecond >= 10 ? String.valueOf(msecond) : ("0" + String.valueOf(msecond));

    strHour = " " + strHour + " ";
    strMin = " " + strMin + " ";
    strSecond = " " + strSecond + " ";
    String strDote = " : ";
    String strTime = strHour + strDote + strMin + strDote + strSecond;

    SpannableString msp = new SpannableString(strTime);
    msp.setSpan(new ForegroundColorSpan(backgroundColor),
        strHour.length() + 1, strHour.length() + 3,
        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    msp.setSpan(new ForegroundColorSpan(backgroundColor),
        strHour.length() + 8, strHour.length() + 9,
        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    msp.setSpan(new AbsoluteSizeSpan(textSize, true), 0, strTime.length(),
        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    msp.setSpan(new BackgroundColorSpan(backgroundColor), 0,
        strHour.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    msp.setSpan(new BackgroundColorSpan(backgroundColor),
        strHour.length() + 3, strHour.length() + 7,
        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    msp.setSpan(new BackgroundColorSpan(backgroundColor),
        strHour.length() + 10, strHour.length() + 14,
        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    msp.setSpan(new ForegroundColorSpan(forgroundColor), 0,
        strHour.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    msp.setSpan(new ForegroundColorSpan(forgroundColor),
        strHour.length() + 3, strHour.length() + 7,
        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    msp.setSpan(new ForegroundColorSpan(forgroundColor),
        strHour.length() + 10, strHour.length() + 14,
        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    this.setText(msp);
  }
}
