/*
 * SmoothListViewHeader     2016/9/27-09-27
 * Copyright (c) 2016 KOTERWONG All right reserved
 */
package widget.smoothlistview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import me.koterwong.androidlibs.R;

/**
 * Created by Koterwong on 2016/9/27 19:59
 */
public class SmoothListViewHeader extends LinearLayout {
  public final static int STATE_NORMAL = 0;
  public final static int STATE_READY = 1;
  public final static int STATE_REFRESHING = 2;

  private final int ROTATE_ANIM_DURATION = 180;

  private Animation mRotateUpAnim;
  private Animation mRotateDownAnim;

  private LinearLayout mContainer;
  private ImageView mArrowImageView;
  private TextView mHintTextView;
  private ProgressBar mProgressBar;

  private int mState = STATE_NORMAL;

  public SmoothListViewHeader(Context context) {
    this(context, null);
  }

  public SmoothListViewHeader(Context context, AttributeSet attrs) {
    this(context, attrs, -1);
  }

  public SmoothListViewHeader(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    this.initView(context);
  }

  private void initView(Context context) {
    mContainer = (LinearLayout) LayoutInflater.from(context).inflate(R.layout
        .smoothlistview_head, null);
    LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, 0);
    addView(mContainer, lp);
    setGravity(Gravity.BOTTOM);

    mArrowImageView = (ImageView) findViewById(R.id.smoothlistview_header_arrow);
    mHintTextView = (TextView) findViewById(R.id.smoothlistview_header_hint_textview);
    mProgressBar = (ProgressBar) findViewById(R.id.smoothlistview_header_progressbar);

    mRotateUpAnim = new RotateAnimation(0.0f, -180.0f, Animation.RELATIVE_TO_SELF, 0.5f,
        Animation.RELATIVE_TO_SELF, 0.5f);
    mRotateUpAnim.setDuration(ROTATE_ANIM_DURATION);
    mRotateUpAnim.setFillAfter(true);
    mRotateDownAnim = new RotateAnimation(-180.0f, 0.0f, Animation.RELATIVE_TO_SELF, 0.5f,
        Animation.RELATIVE_TO_SELF, 0.5f);
    mRotateDownAnim.setDuration(ROTATE_ANIM_DURATION);
    mRotateDownAnim.setFillAfter(true);
  }

  public void setState(int state) {
    if (state == mState) return;

    if (state == STATE_REFRESHING) {  //显示进度
      mArrowImageView.clearAnimation();
      mArrowImageView.setVisibility(View.INVISIBLE);
      mProgressBar.setVisibility(View.VISIBLE);
    } else {                           //显示箭头
      mArrowImageView.setVisibility(View.VISIBLE);
      mProgressBar.setVisibility(View.INVISIBLE);
    }

    switch (state) {
      case STATE_NORMAL:
        if (mState == STATE_READY) mArrowImageView.startAnimation(mRotateDownAnim);
        if (mState == STATE_REFRESHING) mArrowImageView.clearAnimation();
        mHintTextView.setText(R.string.smoothlistview_footer_hint_normal);
        break;
      case STATE_READY:
        if (mState != STATE_REFRESHING) {
          mArrowImageView.clearAnimation();
          mArrowImageView.startAnimation(mRotateUpAnim);
          mHintTextView.setText(R.string.smoothlistview_header_hint_ready);
        }
        break;
      case STATE_REFRESHING:
        mArrowImageView.clearAnimation();
        mHintTextView.setText(R.string.smoothlistview_header_hint_loading);
        break;
      default:
    }
  }

  public void setVisiableHeight(int height) {
    if (height < 0) height = 0;
    LayoutParams lp = (LayoutParams) mContainer.getLayoutParams();
    lp.height = height;
    mContainer.setLayoutParams(lp);
  }

  public int getVisiableHeight() {
    return mContainer.getHeight();
  }
}
