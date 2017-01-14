package me.koterwong.widget.bottommanager;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import me.koterwong.R;

public class HomeTabItem extends LinearLayout {

  private static final int DEFAULT_SELECT_IMAGE_SIZE = 23;
  private static float DEFAULT_SELECT_TEXT_SIZE = 11.5f;

  private ImageView mSelectedImage;
  private ImageView mUnselectedImage;
  private int mSelectImageSize;
  private TextView mTitle;


  public HomeTabItem(Context context) {
    this(context, null);
  }

  public HomeTabItem(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
    initViews(context);
  }

  public HomeTabItem setSelectedImageRes(int imageRes) {
    mSelectedImage.setImageResource(imageRes);
    return this;
  }

  public HomeTabItem setUnSelectedImageRes(int imageRes) {
    mUnselectedImage.setImageResource(imageRes);
    return this;
  }

  public HomeTabItem setTitleText(String titleText) {
    mTitle.setText(titleText);
    return this;
  }

  public HomeTabItem setSelectImageSize(int imageSize) {
    LinearLayout.LayoutParams lp = (LayoutParams) mSelectedImage.getLayoutParams();
    lp.width = dip2px(mSelectedImage.getContext(), imageSize);
    lp.height = dip2px(mSelectedImage.getContext(), imageSize);

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

  public String getTitleText() {
    return mTitle.getText().toString();
  }

  /////////////////////////////////////////内部处理方法///////////////////////////////////////////

  /**
   * 初始化内部子View的属性
   *
   * @param context
   */
  private void initViews(Context context) {

    LinearLayout.LayoutParams lp = new LayoutParams(dip2px(context, DEFAULT_SELECT_IMAGE_SIZE), dip2px(context, DEFAULT_SELECT_IMAGE_SIZE));
    mSelectedImage = new ImageView(context);
    mUnselectedImage = new ImageView(context);
    mSelectedImage.setLayoutParams(lp);
    mSelectedImage.setVisibility(GONE);
    mUnselectedImage.setLayoutParams(lp);

    mTitle = new TextView(context);
    LinearLayout.LayoutParams textLp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    mTitle.setSingleLine();
    textLp.setMargins(0, dip2px(context, 3), 0, 0);
    mTitle.setLayoutParams(textLp);
    mTitle.setTextSize(DEFAULT_SELECT_TEXT_SIZE);

    addView(mSelectedImage);
    addView(mUnselectedImage);
    addView(mTitle);

    setBackground(ContextCompat.getDrawable(getContext(), R.drawable.selector_item_ripper_with_mask));
  }

  /**
   * 初始化一些控件自身的属性
   */
  private void init() {
    setOrientation(VERTICAL);
    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
    lp.weight = 1;
    setPadding(0, dip2px(getContext(), 5), 0, 0);
    setGravity(Gravity.CENTER);
    setLayoutParams(lp);
  }

  private int dip2px(Context context, float dpValue) {
    final float scale = context.getResources().getDisplayMetrics().density;
    return (int) (dpValue * scale + 0.5f);
  }
}
