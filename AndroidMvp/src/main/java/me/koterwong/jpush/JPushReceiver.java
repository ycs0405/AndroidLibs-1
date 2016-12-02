/*
 * JPushReceiver     2016/9/24-09-24
 * Copyright (c) 2016 KOTERWONG All right reserved
 */
package me.koterwong.jpush;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.List;

import cn.jpush.android.api.JPushInterface;
import me.koterwong.common.LogKw;

/**
 * Created by Koterwong on 2016/9/24 10:00
 * 激光推送广播接受者。接受广播，已经在Manifest配置该广告的相应的Action。
 * 接收到的广播包括:
 * 自定义消息、通知消息、用户点击通知栏通知。目前只关心这三种广播。
 */
public class JPushReceiver extends BroadcastReceiver {
  private static final String TAG = "JPushReceiver";

  public static final int CODE_JPUSH_MESSAGE = 1001001;
  public static final int CODE_JPUSH_NOTIFICATION = 1001002;

  public static final String APP_PACKAGENAME = "APP_PACKAGENAME";
  public static final String LAUNCHER_ACTIVITY = "LAUNCHER_ACTIVITY";

  @Override
  public void onReceive(Context context, Intent intent) {
    Bundle bundle = intent.getExtras();
    LogKw.d(TAG, "[JPushReceiver] onReceive - " + intent.getAction() + ", extras: " +
        printBundle(bundle));

    if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
      String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
      LogKw.d(TAG, "[JPushReceiver] 接收Registration Id : " + regId);
      //send the Registration Id to your server...

    } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
      LogKw.d(TAG, "[JPushReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));

    } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
      int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
      LogKw.d(TAG, "[JPushReceiver] 接收到推送下来的通知\n" +
          "[JPushReceiver] 接收到推送下来的通知的ID: " + notifactionId);

    } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
      LogKw.d(TAG, "[JPushReceiver] 用户点击打开了通知");
      //打开自定义的Activity
      Intent i = new Intent();
      ComponentName componentName = new ComponentName("me.koterwong.androidlibs",
          "me.koterwong.androidlibs.MainActivity");
      i.setComponent(componentName);
      i.putExtras(bundle);
      //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
      context.startActivity(i);

    } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
      LogKw.d(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface
          .EXTRA_EXTRA));
      //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

    } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
      boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
      LogKw.d(TAG, "[MyReceiver]" + intent.getAction() + " connected state change to " + connected);
    } else {
      LogKw.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
    }
  }

  // 打印所有的 intent extra 数据
  private static String printBundle(Bundle bundle) {
    StringBuilder sb = new StringBuilder();
    for (String key : bundle.keySet()) {
      if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
        sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
      } else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
        sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
      } else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
        if (bundle.getString(JPushInterface.EXTRA_EXTRA).isEmpty()) {
          LogKw.d(TAG, "This message has no Extra data");
          continue;
        }

        try {
          JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
          Iterator<String> it = json.keys();

          while (it.hasNext()) {
            String myKey = it.next().toString();
            sb.append("\nkey:" + key + ", value: [" +
                myKey + " - " + json.optString(myKey) + "]");
          }
        } catch (JSONException e) {
          LogKw.e(TAG, "Get message extra JSON error!");
        }

      } else {
        sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
      }
    }
    return sb.toString();
  }

  private void startMainActivity(Context context) {
    try {
      ComponentName cn = new ComponentName(context, JPushReceiver.class);
      ActivityInfo info = context.getPackageManager().getReceiverInfo(cn,
          PackageManager.GET_META_DATA);
      if (null != info) {
        String packageName = null;
        String launcherActivity = null;
        if (null != info.metaData) {
          if (info.metaData.containsKey(APP_PACKAGENAME)) {
            packageName = info.metaData.getString(APP_PACKAGENAME);
          }
          if (info.metaData.containsKey(LAUNCHER_ACTIVITY)) {
            launcherActivity = info.metaData.getString(LAUNCHER_ACTIVITY);
          }
        }

        if (!isActivityRun(context, packageName)) {
          // 启动首页
          Intent startHome = new Intent("android.intent.action.MAIN");
          startHome.addCategory("android.intent.category.LAUNCHER");
          ComponentName componentName = new ComponentName(packageName, launcherActivity);
          startHome.setComponent(componentName);
          startHome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
          context.startActivity(startHome);
        }
      }
    } catch (PackageManager.NameNotFoundException e) {
      e.printStackTrace();
    }
  }

  private static boolean isActivityRun(Context context, String packageName) {
    if (null == context) {
      return true;
    }

    if (TextUtils.isEmpty(packageName)) {
      return true;
    }

    ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
    List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(100);
    boolean isAppRunning = false;
    for (ActivityManager.RunningTaskInfo info : list) {
      if (info.topActivity.getPackageName().equals(packageName)
          || info.baseActivity.getPackageName().equals(packageName)) {
        isAppRunning = true;
        break;
      }
    }
    return isAppRunning;
  }

  //send msg to MainActivity
  private void processCustomMessage(Context context, Bundle bundle) {
//    if (MainActivity.isForeground) {
//      String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
//      String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
//      Intent msgIntent = new Intent(MainActivity.MESSAGE_RECEIVED_ACTION);
//      msgIntent.putExtra(MainActivity.KEY_MESSAGE, message);
//      if (!ExampleUtil.isEmpty(extras)) {
//        try {
//          JSONObject extraJson = new JSONObject(extras);
//          if (null != extraJson && extraJson.length() > 0) {
//            msgIntent.putExtra(MainActivity.KEY_EXTRAS, extras);
//          }
//        } catch (JSONException e) {
//
//        }
//      }
//      context.sendBroadcast(msgIntent);
//    }
  }
}
