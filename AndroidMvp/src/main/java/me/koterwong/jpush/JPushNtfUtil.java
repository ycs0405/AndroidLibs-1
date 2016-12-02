/*
 * JPushNtfUtil     2016/9/24-09-24
 * Copyright (c) 2016 KOTERWONG All right reserved
 */
package me.koterwong.jpush;

import android.app.Notification;
import android.content.Context;

import cn.jpush.android.api.BasicPushNotificationBuilder;
import cn.jpush.android.api.CustomPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;
import me.koterwong.R;

/**
 * Created by Koterwong on 2016/9/24 13:18
 *
 * 激光推送通知栏定制工具类。
 */
public class JPushNtfUtil {
  /**
   * 设置JPush保留在通知栏的最多的通知条数
   *
   * @param context context
   * @param limit   条数
   */
  public static void setLatestNotificationNumber(Context context, int limit) {
    JPushInterface.setLatestNotificationNumber(context, limit);
  }

  /**
   * 设置默认编号为0的通知栏样式
   *
   * @param builder builder
   */
  public static void setDefaultPushNtfBuilder(BasicPushNotificationBuilder builder) {
    JPushInterface.setDefaultPushNotificationBuilder(builder);
  }

  /**
   * 设置指定编号的通知栏样式
   *
   * @param i       i
   * @param builder builder
   */
  public static void setPushNtfBuilder(Integer i, BasicPushNotificationBuilder builder) {
    JPushInterface.setPushNotificationBuilder(i, builder);
  }

  /**
   * 定制基础通知栏样式
   *
   * @param context  context
   * @param drawable 通知栏Icon
   */
  public static void buildBasicPushNtf(Context context, int drawable) {
    BasicPushNotificationBuilder builder = new BasicPushNotificationBuilder(context);
    builder.statusBarDrawable = drawable;
    builder.notificationFlags = Notification.FLAG_AUTO_CANCEL
        | Notification.FLAG_SHOW_LIGHTS;  //设置为自动消失和呼吸灯闪烁
    builder.notificationDefaults = Notification.DEFAULT_SOUND
        | Notification.DEFAULT_VIBRATE
        | Notification.DEFAULT_LIGHTS;  // 设置为铃声、震动、呼吸灯闪烁都要
    setDefaultPushNtfBuilder(builder);
  }

  /**
   * 自定义通知栏样式
   *
   * @param context context
   * @param layout 定制的通知栏布局
   * @param drawable1 最顶层状态栏小图标
   * @param drawable2 下拉状态栏时显示的通知图标
   */
  public static void buildCustomPushNfy(Context context, int layout, int drawable1, int drawable2) {
    CustomPushNotificationBuilder builder = new
        CustomPushNotificationBuilder(context,
        layout,  // 指定定制的 Notification Layout
        R.id.icon,
        R.id.title,
        R.id.text);
    builder.statusBarDrawable = drawable1;  // 指定最顶层状态栏小图标
    builder.layoutIconDrawable = drawable2;    // 指定下拉状态栏时显示的通知图标
    setDefaultPushNtfBuilder(builder);
  }
}
