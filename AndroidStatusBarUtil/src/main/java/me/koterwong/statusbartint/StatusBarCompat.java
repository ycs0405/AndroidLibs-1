package me.koterwong.statusbartint;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.widget.DrawerLayout;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

/**
 * Created by Koterwong on 2016/8/10.
 * <p/>
 * decorView：最顶级View包括titlebar和contentView contentView：android.R.id.content 的FrameLayout
 * rootView：contentView的第一个子View
 * <p/>
 * 1.设置状态栏颜色： 调用StatusBarCompat.setColor方法实现设置状态栏纯色。兼容4.4以上。 调用StatusBarCompat
 * .setColorWithTranslucent设置状态栏透明。兼容4.4以上。
 * <p/>
 * 2.设置状态栏全透明和半透明效果： 全透明和半透明只适用于rootView设置background属性的情况，通常背景是一张图片。
 * <p/>
 * 3.设置状态栏透明和半透明效果不使用fitSystemWindow。 适用于rootView需要延伸到状态栏的情况
 * <p/>
 * 4.待完善...
 */
public class StatusBarCompat {

  public static final int DEFAULT_STATUS_BRA_ALPHA = 112;
  private static final int TRANSPAEENT = 0X00ffffff;

  public static void setColor(Activity activity, int color) {
    setColor(activity, color, 0);
  }

  /**
   * 设置状态栏颜色
   * <p/>
   * 在Android5.0以上需要先清除FLAG_TRANSLUCENT_STATUS，然后直接调用setStatusBarColor方法即可。
   * <p/>
   * android4.4到android5.0需要添加FLAG_TRANSLUCENT_STATUS。并且给decorView添加一个和状态栏一样
   * 大小的View，并设置contentView的padding给状态留出空间。
   *
   * @param activity activity
   * @param color    color
   * @param alpha    alpha  0 ~ 255
   */
  public static void setColor(Activity activity, int color, int alpha) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      //this flag make sure invoke setStatusColor available
      activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
      //这个flag在5.0以上呈现半透明状态，在4.4以上为全透状态。并且contentView延伸到状态栏。5.0以上要先清楚这个Flag。
      activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
      //手动计算半透明状态，并设置状态栏颜色。
      activity.getWindow().setStatusBarColor(calculateStatueBarColor(color, alpha));
    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
      activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
      ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
      int childCount = decorView.getChildCount();
      if (childCount > 0 && decorView.getChildAt(childCount - 1) instanceof StatusBarView) {
        //说明之前已经添加过了顶部状态栏的占用的View，这里直接设置原View的颜色
        //decorView本身是一个FrameLayout，一般不会向这里添加子View。
        decorView.getChildAt(childCount - 1).setBackgroundColor(calculateStatueBarColor(color,
            alpha));
      } else {
        //说明以前没有添加过StatusBarView
        StatusBarView statusBarView = createStatusBarView(activity, color, alpha);
        decorView.addView(statusBarView);
      }
      //再调用FitSystemWindow的时候需要在View还没有初始化完毕的时候，否则会出现设置rootView的padding无法绘制。
      setRootViewFitWindow(activity);
    }
  }

  /**
   * 设置状态栏颜色  5.0以上半透明，5.0以下全透明
   *
   * @param activity 需要设置的 activity
   * @param color    状态栏颜色值
   */
  public static void setColorDiff(Activity activity, int color) {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
      return;
    }
    activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
    int count = decorView.getChildCount();
    if (count > 0 && decorView.getChildAt(count - 1) instanceof StatusBarView) {
      decorView.getChildAt(count - 1).setBackgroundColor(color);
    } else {
      StatusBarView statusView = createStatusBarView(activity, color, 0);
      decorView.addView(statusView);
    }
    setRootViewFitWindow(activity);
  }


  /**
   * 设置沉浸式状态栏，让contentView进入状态栏。
   *
   * @param activity activity
   */
  public static void setStatusBarImmersive(Activity activity) {

    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
      return;
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
      activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
      activity.getWindow().setStatusBarColor(TRANSPAEENT);
    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
      activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }
  }

  /**
   * 设置沉浸式状态栏，让contentView进入状态栏。
   *
   * @param activity activity
   * @param alpha    alpha
   */
  public static void setStatusBarImmersive(Activity activity, int alpha) {
    setStatusBarImmersive(activity);
    addTranslucentView(activity, alpha);
  }

  /**
   * 设置沉浸状态栏颜色  5.0以上半透明，5.0以下全透明
   *
   * @param activity activity
   */
  public static void setStatusBarImmersiveDiff(Activity activity) {

    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
      return;
    }

    activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    setRootViewNotFitWindow(activity);
  }

  /**
   * 设置状态栏全透明，这种情况状态呈白色。仅仅适用于contentView以图片作为background的时候
   *
   * @param activity activity
   */
  public static void setTransparent(Activity activity) {
    setStatusBarImmersive(activity);
    //这种情况，需要设置rootView的padding来为状态栏预留空间
    setRootViewFitWindow(activity);
  }

  /**
   * 设置状态栏半透明，这种情况状态呈半透明白色。仅仅适用于contentView以图片作为背景的时候
   *
   * @param activity activity
   * @param alpha    alpha
   */
  public static void setTranslucent(Activity activity, int alpha) {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
      return;
    }
    //设置状态栏全透明，但是这种情况只适用于rootView的background为图片或者纯色。其他情况不适用
    setTransparent(activity);
    //添加半透明的view到状态栏
    addTranslucentView(activity, alpha);
  }

  public static void setForDrawerLayout(Activity activity, DrawerLayout drawerLayout, int color) {
    setForDrawerLayout(activity, drawerLayout, color, 0);
  }

  public static void setForDrawerLayout(Activity activity, DrawerLayout drawerLayout, int color, int statusBarAlpha) {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
      return;
    }

    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
      activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
      activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
      activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
    } else {
      activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }
    // 生成一个状态栏大小的矩形
    // 添加 statusBarView 到布局中
    ViewGroup contentLayout = (ViewGroup) drawerLayout.getChildAt(0);
    if (contentLayout.getChildCount() > 0 && contentLayout.getChildAt(0) instanceof StatusBarView) {
      contentLayout.getChildAt(0).setBackgroundColor(calculateStatueBarColor(color, statusBarAlpha));
    } else {
      StatusBarView statusBarView = createStatusBarView(activity, color,statusBarAlpha);
      contentLayout.addView(statusBarView, 0);
    }
    // 内容布局不是 LinearLayout 时,设置padding top
    if (!(contentLayout instanceof LinearLayout) && contentLayout.getChildAt(1) != null) {
      contentLayout.getChildAt(1)
          .setPadding(contentLayout.getPaddingLeft(), getStatusBarHeight(activity) + contentLayout.getPaddingTop(),
              contentLayout.getPaddingRight(), contentLayout.getPaddingBottom());
    }
    // 设置属性
    ViewGroup drawer = (ViewGroup) drawerLayout.getChildAt(1);
    drawerLayout.setFitsSystemWindows(false);
    contentLayout.setFitsSystemWindows(false);
    contentLayout.setClipToPadding(true);
    drawer.setFitsSystemWindows(false);

    addTranslucentView(activity, statusBarAlpha);
  }


  private static void addTranslucentView(Activity activity, int alpha) {
    ViewGroup contentView = (ViewGroup) activity.findViewById(Window.ID_ANDROID_CONTENT);
    if (contentView.getChildCount() <= 1) {
      contentView.addView(createTranslucentStatusBarView(activity, alpha));
    } else {
      contentView.getChildAt(1).setBackgroundColor(Color.argb(alpha, 0, 0, 0));
    }
  }

  private static void setRootViewFitWindow(Activity activity) {

    ViewGroup rootView = (ViewGroup) ((ViewGroup) activity.findViewById(Window.ID_ANDROID_CONTENT))
        .getChildAt(0);
    rootView.setFitsSystemWindows(true);
    //设置ViewGroup中的padding是否可以绘制，默认为true，即不可以绘制。
    rootView.setClipToPadding(true);
  }

  private static void setRootViewNotFitWindow(Activity activity) {
    ViewGroup rootView = (ViewGroup) ((ViewGroup) activity.findViewById(Window.ID_ANDROID_CONTENT))
        .getChildAt(0);
    rootView.setFitsSystemWindows(false);
    rootView.setClipToPadding(true);
  }

  private static StatusBarView createTranslucentStatusBarView(Activity activity, int alpha) {
    StatusBarView statusBarView = new StatusBarView(activity);
    FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight(activity));
    statusBarView.setLayoutParams(params);
    statusBarView.setBackgroundColor(Color.argb(alpha, 0, 0, 0));
    return statusBarView;
  }

  private static StatusBarView createStatusBarView(Activity activity, int color, int alpha) {
    StatusBarView statusBarView = new StatusBarView(activity);
    FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight(activity));
    statusBarView.setLayoutParams(params);
    statusBarView.setBackgroundColor(calculateStatueBarColor(color, alpha));
    return statusBarView;
  }

  /**
   * 获取状态栏高度
   *
   * @param context context
   * @return int
   */
  public static int getStatusBarHeight(Context context) {
    int identifier = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
    return context.getResources().getDimensionPixelSize(identifier);
  }

  private static int calculateStatueBarColor(int color, int alpha) {

    float al = 1 - alpha / 255f;

    int red = color >> 16 & 0Xff;
    int green = color >> 8 & 0Xff;
    int blue = color & 0Xff;

    red = (int) (red * al + 0.5);
    green = (int) (green * al + 0.5);
    blue = (int) (blue * al + 0.5);

    return 0xff << 24 | red << 16 | green << 8 | blue;
  }
}
