/*
 * CircleImageView     2016/10/2-10-02
 * Copyright (c) 2016 KOTERWONG All right reserved
 */
package me.koterwong.widget;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.support.v4.view.ViewCompat;
import android.view.animation.Animation;
import android.widget.ImageView;

/**
 * copy from android.support.v4.widget.CircleImageView and make it public
 *
 * Private class created to work around issues with AnimationListeners being called before the
 * animation is actually complete and support shadows on older platforms.
 */
public class CircleImageView extends ImageView {

  private static final int KEY_SHADOW_COLOR = 0x1E000000;
  private static final int FILL_SHADOW_COLOR = 0x3D000000;
  // PX
  private static final float X_OFFSET = 0f;
  private static final float Y_OFFSET = 1.75f;
  private static final float SHADOW_RADIUS = 3.5f;
  private static final int SHADOW_ELEVATION = 4;

  private Animation.AnimationListener mListener;
  private int mShadowRadius;

  public CircleImageView(Context context, int color) {
    super(context);
    final float density = getContext().getResources().getDisplayMetrics().density;
    final int shadowYOffset = (int) (density * Y_OFFSET);
    final int shadowXOffset = (int) (density * X_OFFSET);

    mShadowRadius = (int) (density * SHADOW_RADIUS);

    //make sure shadow at android 5.0 above and below effective
    ShapeDrawable circle;
    if (elevationSupported()) {
      circle = new ShapeDrawable(new OvalShape());
      //android 5.0 and above use setElevation
      ViewCompat.setElevation(this, SHADOW_ELEVATION * density);
    } else {
      //android 5.0 below
      OvalShape oval = new CircleImageView.OvalShadow(mShadowRadius);
      circle = new ShapeDrawable(oval);
      // 关闭硬件加速，要不绘制的阴影没有效果
      ViewCompat.setLayerType(this, ViewCompat.LAYER_TYPE_SOFTWARE, circle.getPaint());
      // 设置阴影层，Y方向稍微偏移了一点点
      circle.getPaint().setShadowLayer(mShadowRadius, shadowXOffset, shadowYOffset,
          KEY_SHADOW_COLOR);
      final int padding = mShadowRadius;
      // set padding so the inner image sits correctly within the shadow.
      setPadding(padding, padding, padding, padding);
    }
    circle.getPaint().setColor(color);
    setBackgroundDrawable(circle);
  }

  private boolean elevationSupported() {
    return android.os.Build.VERSION.SDK_INT >= 21;
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    if (!elevationSupported()) {
      // 如果不支持阴影效果，把阴影的范围加进去重新设置控件的大小
      setMeasuredDimension(getMeasuredWidth() + mShadowRadius * 2, getMeasuredHeight()
          + mShadowRadius * 2);
    }
  }

  public void setAnimationListener(Animation.AnimationListener listener) {
    mListener = listener;
  }

  @Override
  public void onAnimationStart() {
    super.onAnimationStart();
    if (mListener != null) {
      mListener.onAnimationStart(getAnimation());
    }
  }

  @Override
  public void onAnimationEnd() {
    super.onAnimationEnd();
    if (mListener != null) {
      mListener.onAnimationEnd(getAnimation());
    }
  }

  /**
   * Update the background color of the circle image view.
   *
   * @param colorRes Id of a color resource.
   */
  public void setBackgroundColorRes(int colorRes) {
    setBackgroundColor(getContext().getResources().getColor(colorRes));
  }

  @Override
  public void setBackgroundColor(int color) {
    if (getBackground() instanceof ShapeDrawable) {
      ((ShapeDrawable) getBackground()).getPaint().setColor(color);
    }
  }

  /**
   * shadow OvalShape use at android5.0 below
   */
  private class OvalShadow extends OvalShape {
    private RadialGradient mRadialGradient;
    private Paint mShadowPaint;

    OvalShadow(int shadowRadius) {
      super();
      mShadowPaint = new Paint();
      mShadowRadius = shadowRadius;
      updateRadialGradient((int) rect().width());
    }

    @Override
    protected void onResize(float width, float height) {
      super.onResize(width, height);
      updateRadialGradient((int) width);
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
      final int viewWidth = CircleImageView.this.getWidth();
      final int viewHeight = CircleImageView.this.getHeight();
      // 先画上阴影效果
      canvas.drawCircle(viewWidth / 2, viewHeight / 2, viewWidth / 2, mShadowPaint);
      // 画上内容
      canvas.drawCircle(viewWidth / 2, viewHeight / 2, viewWidth / 2 - mShadowRadius, paint);
    }
    // 环形渲染，达到阴影的效果
    private void updateRadialGradient(int diameter) {
      mRadialGradient = new RadialGradient(diameter / 2, diameter / 2,
          mShadowRadius, new int[]{FILL_SHADOW_COLOR, Color.TRANSPARENT},
          null, Shader.TileMode.CLAMP);
      mShadowPaint.setShader(mRadialGradient);
    }
  }
}

