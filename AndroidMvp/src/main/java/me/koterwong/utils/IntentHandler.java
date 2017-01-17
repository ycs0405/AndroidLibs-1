/*
 * IntentHandler     2016/9/29-09-29
 * Copyright (c) 2016 KOTERWONG All right reserved
 */
package me.koterwong.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import okhttp3.RequestBody;

import static android.R.attr.value;

/**
 * Created by Koterwong on 2016/9/29 10:32
 */
public class IntentHandler {
  private Activity mActivity;

  public IntentHandler(Activity activity) {
    mActivity = activity;
  }

  public void intentToActivity(Class<? extends Activity> activity) {
    intentToActivity(null, activity);
  }

  public void intentToActivity(Bundle bundle, Class<? extends Activity> activity) {
    Intent intent = new Intent();
    if (bundle != null) {
      intent.putExtras(bundle);
    }
    intent.putExtra("classname", mActivity.getComponentName().getClassName());
    intent.setClass(mActivity, activity);
    mActivity.startActivity(intent);
  }

  public String getString(String name) {
    if (null != mActivity.getIntent()) {
      String value = mActivity.getIntent().getStringExtra(name);
      if (!TextUtils.isEmpty(value)) {
        return value;
      }
    }
    return "";
  }

  public <T> T getParcelable(String name) {
    if (null != mActivity.getIntent()) {
      T value = mActivity.getIntent().getParcelableExtra(name);
      if (value != null) {
        return value;
      }
    }
    return null;
  }

  public <T extends Parcelable> ArrayList<T> getParcelableArrayListExtra(String name) {
    if (null != mActivity.getIntent()) {
      ArrayList<T> list = mActivity.getIntent().getParcelableArrayListExtra(name);
      if (null != list) {
        return list;
      }
    }
    return null;
  }

  public boolean getBoolean(String name) {
    if (null != mActivity.getIntent()) {
      boolean value = mActivity.getIntent().getBooleanExtra(name, false);
      return value;
    }
    return false;
  }

  public boolean isIntentFrom(Class<?> activity) {
    if (null != mActivity.getIntent()) {
      String intentFrom = mActivity.getIntent().getStringExtra("classname");
      if (!TextUtils.isEmpty(intentFrom) && intentFrom.equals(activity.getName())) {
        return true;
      }
    }
    return false;
  }
}
