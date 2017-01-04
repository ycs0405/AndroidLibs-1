/*
 * HomeTabItem     2017/1/4 14:39
 * Copyright (c) 2017 Koterwong All right reserved
 */
package me.koterwong.widget.bottommanager;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Koterwong on 2017/1/4 14:39
 */
public class HomeTabItem extends LinearLayout {
  private static final int DEFAULT_SELECT_IMAGE_SIZE = 23;
  private static float DEFAULT_SELECT_TEXT_SIZE = 11.5f;

  private ImageView mSelectedImage;
  private ImageView mUnselectedImage;
  private TextView mTitle;


  public HomeTabItem(Context context) {
    this(context, null);
  }

  public HomeTabItem(Context context, AttributeSet attrs) {
    this(context, attrs, -1);
  }

  public HomeTabItem(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    this.init(context);
    this.initView(context);
  }

  private void init(Context context) {
    setOrientation(VERTICAL);
    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
    lp.weight = 1;
    setPadding(0, dp2px(context, 5), 0, 0);
    setGravity(Gravity.CENTER);
    setLayoutParams(lp);
  }

  private void initView(Context context) {
    LinearLayout.LayoutParams lp = new LayoutParams(dp2px(context, DEFAULT_SELECT_IMAGE_SIZE), dp2px(context, DEFAULT_SELECT_IMAGE_SIZE));
    mSelectedImage = new ImageView(context);
    mUnselectedImage = new ImageView(context);
    mSelectedImage.setLayoutParams(lp);
    mUnselectedImage.setLayoutParams(lp);
    mSelectedImage.setVisibility(GONE);

    mTitle = new TextView(context);
    LinearLayout.LayoutParams textLp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    mTitle.setSingleLine();
    textLp.setMargins(0, dp2px(context, 3), 0, 0);
    mTitle.setLayoutParams(textLp);
    mTitle.setTextSize(DEFAULT_SELECT_TEXT_SIZE);

    addView(mSelectedImage);
    addView(mUnselectedImage);
    addView(mTitle);
  }


  public HomeTabItem setTitle(String title) {
    mTitle.setText(title);
    return this;
  }

  public HomeTabItem setUnselectedImageRes(@DrawableRes int unselectedImage) {
    mUnselectedImage.setImageResource(unselectedImage);
    return this;
  }

  public HomeTabItem setSelectedImageRes(@DrawableRes int selectedImage) {
    mSelectedImage.setImageResource(selectedImage);

    return this;
  }

  public HomeTabItem setSelectImageSize(int imageSize) {
    LinearLayout.LayoutParams lp = (LayoutParams) mSelectedImage.getLayoutParams();
    lp.width = dp2px(mSelectedImage.getContext(), imageSize);
    lp.height = dp2px(mSelectedImage.getContext(), imageSize);
    mSelectedImage.setLayoutParams(lp);
    mUnselectedImage.setLayoutParams(lp);
    return this;
  }

  public ImageView getSelectedImage() {
    return mSelectedImage;
  }

  public ImageView getUnselectedImage() {
    return mUnselectedImage;
  }

  public TextView getTitleTextView() {
    return mTitle;
  }

  private int dp2px(Context context, int dpValue) {
    return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, dpValue, context.getResources().getDisplayMetrics());
  }
}
