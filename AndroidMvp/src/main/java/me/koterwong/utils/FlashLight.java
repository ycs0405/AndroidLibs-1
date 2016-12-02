/*
 * FlashLight     2016/10/3-10-03
 * Copyright (c) 2016 KOTERWONG All right reserved
 */
package me.koterwong.utils;

import android.hardware.Camera;
import android.os.Build;
import android.os.Handler;

/**
 * Flash light
 *
 * Call requires API level 5.
 * <uses-permission android:name="android.permission.FLASHLIGHT"/>
 * <uses-permission android:name="android.permission.CAMERA"/>
 *
 * Created by Koterwong on 2016/10/3 12:10
 */
public class FlashLight {
  private Camera camera;
  private Handler handler = new Handler();

  /** More than 3 minutes auto shut down to prevent damage to hardware */
  private static final int OFF_TIME = 3 * 60 * 1000;

  public boolean turnOnFlashLight() {
    if (camera == null) {
      camera = Camera.open();
      camera.startPreview();
      Camera.Parameters parameter = camera.getParameters();
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
        parameter.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
      } else {
        parameter.set("flash-mode", "torch");
      }
      camera.setParameters(parameter);
      handler.removeCallbacksAndMessages(null);
      handler.postDelayed(new Runnable() {
        @Override
        public void run() {
          turnOffFlashLight();
        }
      }, OFF_TIME);
    }
    return true;
  }

  public boolean turnOffFlashLight() {
    if (camera != null) {
      handler.removeCallbacksAndMessages(null);
      Camera.Parameters parameter = camera.getParameters();
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
        parameter.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
      } else {
        parameter.set("flash-mode", "off");
      }
      camera.setParameters(parameter);
      camera.stopPreview();
      camera.release();
      camera = null;
    }
    return true;
  }
}
