/*
 * SlideLayout     2016/9/29-09-29
 * Copyright (c) 2016 KOTERWONG All right reserved
 */
package me.koterwong.widget.slidelayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import me.koterwong.R;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


/**
 * Created by Koterwong on 2016/9/29 15:13
 *
 * 使用RxJava实现的banner 轮播条。
 *
 * 使用方法，直接在布局文件中声明。然后使用如下方式绑定图片。
 *
 * mSlideLayout
 * .bind(mimg)
 * .setPagerTransform(new ZoomInTransformer())
 * .withListener(new SlideLayout.SlideItemClick() {
 *
 * @Override public void onSlideItemClick(int position) {
 * Toast.makeText(mAppContext, "" + position, Toast.LENGTH_SHORT).show();
 * }
 * });
 */
public class SlideLayout extends FrameLayout implements ViewPager.OnPageChangeListener {
  private static final String TAG = SlideLayout.class.getSimpleName();

  /** indicator */
  @Deprecated
  private static final int INDICATOR_LEFT = Gravity.LEFT;
  private static final int INDICATOR_RIGHT = Gravity.RIGHT;
  private static final int INDICATOR_CENTER = Gravity.CENTER;
  private int mIndicatorGravity = INDICATOR_RIGHT;
  private int mIndicatorSize = 5;
  private int mIndicatorMargin = 3;
  private int mIndicatorDrawable;

  private Context mContext;
  private ViewPager mViewPager;
  private LinearLayout mIndictorLayout;
  private ImageView.ScaleType mScaleType = ImageView.ScaleType.CENTER_CROP;
  private List<ImageView> mImageViews;
  private PagerAdapter mPagerAdapter;

  private boolean mAutoPlayAble = true;
  private int DelayTime = 3;  //默认轮播时间间隔
  private Subscription mSubscription;

  private SlideItemClick mListener;

  public SlideLayout(Context context) {
    this(context, null);
  }

  public SlideLayout(Context context, AttributeSet attrs) {
    this(context, attrs, -1);
  }

  public SlideLayout(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    this.mContext = context;
    final float density = context.getResources().getDisplayMetrics().density;
    this.mIndicatorSize = (int) (mIndicatorSize * density + .5f);
    this.mIndicatorMargin = (int) (mIndicatorMargin * density + .5f);

    this.initAttr(context, attrs);
    this.init(context);
  }

  private void initAttr(Context context, AttributeSet attrs) {
    TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SlideLayout);

    mIndicatorSize = (int) ta.getDimension(R.styleable.SlideLayout_indicator_size, mIndicatorSize);
    mIndicatorMargin = (int) ta.getDimension(R.styleable.SlideLayout_indicator_margin, mIndicatorMargin);
    mScaleType = (ta.getInt(R.styleable.SlideLayout_scaleType, 6) == 1 ? ImageView.ScaleType.CENTER_CROP : ImageView.ScaleType.FIT_XY);
    mIndicatorGravity = ((ta.getInt(R.styleable.SlideLayout_IndicatorGravity, 1) == 2 ? INDICATOR_RIGHT : INDICATOR_CENTER));
//    mIndicatorDrawable = ta.getResourceId(R.styleable.SlideLayout_bg_selected, R.drawable.selector_slide_layout_indcitor);

//    if (getResources().getDrawable(mIndicatorDrawable) != null && getResources().getDrawable(mIndicatorDrawable) instanceof StateListDrawable) {
//      LogKw.e("Failed to load attribute bg_selected. it's must be a StateListDrawable and used normal enable state");
//    } else {
      mIndicatorDrawable = R.drawable.selector_slide_layout_indcitor;
//    }

    ta.recycle();
  }

  private void init(Context context) {
    View view = LayoutInflater.from(context).inflate(R.layout.widget_slide_layout, this, true);
    mViewPager = (ViewPager) view.findViewById(R.id.view_pager);
    mIndictorLayout = (LinearLayout) view.findViewById(R.id.ll_indicator);
    mIndictorLayout.setGravity(mIndicatorGravity);
    mImageViews = new ArrayList<>();
  }

  @Override
  public boolean dispatchTouchEvent(MotionEvent ev) {
    int action = ev.getAction();
    if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL
        || action == MotionEvent.ACTION_OUTSIDE)
      start();
    else if (action == MotionEvent.ACTION_DOWN)
      stop();
    return super.dispatchTouchEvent(ev);
  }

  public SlideLayout bind(String[] imgUrls) {
    bind(Arrays.asList(imgUrls));
    return this;
  }

  public SlideLayout bind(List<String> imgUrls) {
    mImageViews.clear();

    for (int i = 0; i < imgUrls.size(); i++) {
      mImageViews.add(createImageView(imgUrls.get(i)));
    }

    mPagerAdapter = new SlidePagerAdapter();
    mViewPager.setAdapter(mPagerAdapter);
    mViewPager.addOnPageChangeListener(this);

    addIndicator();
    bingListener();

    if (mAutoPlayAble) {
      int zeroItem = Integer.MAX_VALUE / 2 - (Integer.MAX_VALUE / 2) % mImageViews.size();
      mViewPager.setCurrentItem(zeroItem);
    }
    return this;
  }

  private void bingListener() {
    if (mListener == null) {
      return;
    }

    for (int i = 0; i < mImageViews.size(); i++) {
      final int position = i;
      mImageViews.get(i).setOnClickListener(new OnClickListener() {
        @Override public void onClick(View v) {
          mListener.onSlideItemClick(position);
        }
      });
    }
  }

  private void addIndicator() {
    mIndictorLayout.removeAllViews();
    for (int i = 0; i < mImageViews.size(); i++) {
      ImageView ind = new ImageView(mContext);
      LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(mIndicatorSize, mIndicatorSize);

      if (i != 0) {
        lp.leftMargin = mIndicatorMargin;
      }
      ind.setLayoutParams(lp);
      ind.setBackgroundResource(R.drawable.selector_slide_layout_indcitor);
      ind.setEnabled(false);
      if (i == 0) {
        ind.setEnabled(true);
      }

      mIndictorLayout.addView(ind);
    }
  }

  private ImageView createImageView(String s) {
    ImageView iv = new ImageView(mContext);
    LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    iv.setLayoutParams(lp);
    iv.setScaleType(mScaleType);
    Glide.with(mContext)
        .load(s)
        .placeholder(R.color.font_black_6)
        .error(R.color.font_black_6)
        .crossFade()
        .into(iv);
    return iv;
  }

  @Override public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

  }

  @Override public void onPageSelected(int position) {
    if (mImageViews != null && mImageViews.size() > 0) {
      int size = mImageViews.size();
      int realPos = position % size;
      for (int i = 0; i < size; i++) {
        mIndictorLayout.getChildAt(i).setEnabled(false);
        if (i == realPos) {
          mIndictorLayout.getChildAt(i).setEnabled(true);
        }
      }
    }
  }

  @Override public void onPageScrollStateChanged(int state) {

  }


  private class SlidePagerAdapter extends PagerAdapter {

    @Override public int getCount() {
      return mImageViews.size() == 1 ? 1 : (mAutoPlayAble ? Integer.MAX_VALUE : mImageViews.size());
    }

    @Override public boolean isViewFromObject(View view, Object object) {
      return view == object;
    }

    @Override public Object instantiateItem(ViewGroup container, int position) {
      ImageView iv = mImageViews.get(position % mImageViews.size());
      container.removeView(iv);
      container.addView(iv);
      return iv;
    }

    @Override public void destroyItem(ViewGroup container, int position, Object object) {

    }
  }

  public void start() {
    if (mImageViews.size() < 1) {
      return;
    }

    if (mSubscription != null && !mSubscription.isUnsubscribed()) {
      mSubscription.unsubscribe();
    }

    mSubscription = Observable.interval(DelayTime, TimeUnit.SECONDS)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .subscribe(new Action1<Long>() {
          @Override public void call(Long aLong) {
            mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
          }
        });

  }

  public void stop() {
    if (mImageViews.size() <= 0) {
      return;
    }

    if (!mSubscription.isUnsubscribed()) {
      mSubscription.unsubscribe();
    }
  }

  public SlideLayout withListener(SlideItemClick listener) {
    mListener = listener;
    bingListener();
    return this;
  }

  public interface SlideItemClick {
    void onSlideItemClick(int position);
  }

  public SlideLayout setPagerTransform(ViewPager.PageTransformer pagerTransform) {
    mViewPager.setPageTransformer(true, pagerTransform);
    return this;
  }

  public SlideLayout setScaleType(ImageView.ScaleType scaleType) {
    this.mScaleType = scaleType;
    return this;
  }

  public SlideLayout setIndicatorSize(int indicatorSize) {
    mIndicatorSize = indicatorSize;
    return this;
  }

  public SlideLayout setIndicatorMargin(int indicatorMargin) {
    mIndicatorMargin = indicatorMargin;
    return this;
  }

  public SlideLayout setIndicatorGravity(int indicatorGravity) {
    mIndicatorGravity = indicatorGravity;
    return this;
  }

  public SlideLayout daleyTime(int daleyTime) {
    this.DelayTime = daleyTime;
    return this;
  }
}
