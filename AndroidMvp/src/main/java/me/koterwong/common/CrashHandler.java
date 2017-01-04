/*
 * CrashHandler     2016/9/21-09-21
 * Copyright (c) 2016 KOTERWONG All right reserved
 */
package me.koterwong.common;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Process;
import android.os.SystemClock;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by Koterwong on 2016/9/21 14:39
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {
  private final String TAG = this.getClass().getSimpleName();
  private static final boolean DEBUG = true;
  private static final String DATA_FORMAT = "yyyy-MM-dd_HH:mm";
  //可以设置自己的保存路径
  private static final String PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + BuildConfig.TEMP_FILE_NAME;

  private String mPath = null;
  private Context mContext;
  private static final String FILE_NAME = "crash";
  private static final String FILE_NAME_SUFFIX = ".trace";

  private static CrashHandler sCrashHandler = new CrashHandler();
  private Thread.UncaughtExceptionHandler mDefaultCrashHandler;

  private CrashHandler() {}

  public static CrashHandler get() {
    return sCrashHandler;
  }

  public void init(Context context) {
    this.init(context, PATH);
  }

  public void init(Context context, String filePath) {
    if (filePath == null) {
      throw new IllegalStateException("need to specify the exception information save the path");
    }
    mPath = filePath;
    mContext = context.getApplicationContext();
    mDefaultCrashHandler = Thread.getDefaultUncaughtExceptionHandler();
    Thread.setDefaultUncaughtExceptionHandler(sCrashHandler);
  }

  @Override public void uncaughtException(Thread thread, Throwable throwable) {
    throwable.printStackTrace();
    LogKw.e(TAG, "Case : %s /n Message : %s", new Object[]{throwable.getCause().toString(),
        throwable.getMessage()});
    try {
      dumpExceptionToSDCard(throwable);
      uploadExceptionToService(throwable);
    } catch (IOException e) {
      e.printStackTrace();
    }

    if (mDefaultCrashHandler != null) {
      mDefaultCrashHandler.uncaughtException(thread, throwable);
    } else {
      Process.killProcess(Process.myPid());
    }
  }

  private void dumpExceptionToSDCard(Throwable throwable) throws IOException {
    File dir = new File(mPath);
    if (dir.exists()) {
      dir.mkdirs();
    }
    long current = SystemClock.currentThreadTimeMillis();
    String time = new SimpleDateFormat(DATA_FORMAT).format(new Date(current));
    File file = new File(mPath, FILE_NAME + time + FILE_NAME_SUFFIX);
    try {
      PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(file)));
      printWriter.print(time);
      dumpPhoneInfo(printWriter);
      printWriter.println();
      printWriter.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void dumpPhoneInfo(PrintWriter printWriter) throws PackageManager.NameNotFoundException {
    PackageManager packageManager = mContext.getPackageManager();
    PackageInfo packageInfo = packageManager.getPackageInfo(mContext.getPackageName(),
        PackageManager.GET_ACTIVITIES);
    printWriter.println("AppVersion:" + packageInfo.versionCode);
    printWriter.println("OS Version:" + Build.VERSION.SDK_INT);
    printWriter.println("Vendor:" + Build.MANUFACTURER); //手机制造商
    printWriter.println("Model:" + Build.MODEL);  //手机型号
    printWriter.println("CPU ABI:" + Build.CPU_ABI);
  }

  private void uploadExceptionToService(Throwable throwable) {
    // if necessary ,upload the information to server
  }
}
