/*
 * JPushUtil     2016/9/24-09-24
 * Copyright (c) 2016 KOTERWONG All right reserved
 */
package me.koterwong.jpush;

import android.content.Context;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import me.koterwong.common.LogKw;

/**
 * Created by Koterwong on 2016/9/24 08:20
 * 激光推送工具类。
 * 初始化、设置别名、设置标签、开启推送（用户登录）、关闭推送（用于登出）。
 */
public class JPushUtil {
  public static final String TAG = JPushUtil.class.getSimpleName();

  public static void initJPush(Context context, boolean debugMode) {
    JPushInterface.setDebugMode(debugMode);
    JPushInterface.init(context);
    JPushInterface.resumePush(context);
  }

  public static void startPushService(Context context, String account, String group) {
    JPushInterface.resumePush(context);
    try {
      final String alias = getJPushAliasAndTag(account);
      final String tag = getJPushAliasAndTag(group);
      Set<String> sets = new LinkedHashSet<>();
      sets.add(tag);
      JPushInterface.setAliasAndTags(context, alias, sets, getAliasAndTadCallBack());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void stopPushServices(Context context) {
    final String alias = "";
    Set<String> tags = new LinkedHashSet<String>();
    JPushInterface.setAliasAndTags(context, alias, tags);
    JPushInterface.stopPush(context);
  }

  private static TagAliasCallback getAliasAndTadCallBack() {
    return new TagAliasCallback() {
      @Override public void gotResult(int code, String s, Set<String> set) {
        switch (code) {
          case 0:
            LogKw.e(TAG, "Set tag and alias success");
            // 建议这里往 SharePreference 里写一个成功设置的状态。成功设置一次后，以后不必再次设置了。
            break;
          case 6002:
            LogKw.e(TAG, "Failed to set alias and tags due to timeout. Try again after 60s.");
            // 延迟 60 秒来调用 Handler 设置别名
            break;
          default:
            LogKw.e(TAG, "Failed with errorCode = " + code);
        }
      }
    };
  }

  private static String getJPushAliasAndTag(String s) {
    final boolean isAlias = isValidTagAndAlias(s);
    if (!isAlias) {
      s = s
          .replace("@", "")
          .replace("#", "")
          .replace("$", "")
          .replace("&", "")
          .replace("*", "")
          .replace("+", "")
          .replace("=", "")
          .replace(".", "")
          .replace("￥", "")
          .replace("¥", "");
    }
    if (s.length() > 40) {
      s = s.substring(s.length() - 40, s.length());
    }
    return s;
  }

  /**
   * 校验Tag Alias 只能是数字,英文字母和中文
   *
   * @param s s
   * @return
   */
  private static boolean isValidTagAndAlias(String s) {
    Pattern p = Pattern.compile("^[\u4E00-\u9FA50-9a-zA-Z_!@#$&*+=.￥¥]+$");
    Matcher m = p.matcher(s);
    return m.matches();
  }
}
