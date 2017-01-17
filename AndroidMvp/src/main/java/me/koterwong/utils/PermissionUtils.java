/*
 * PermissionUtils     2017/1/10 17:07
 * Copyright (c) 2017 Koterwong All right reserved
 */
package me.koterwong.utils;

import android.Manifest;
import android.content.Context;
import android.widget.Toast;

import com.tbruyelle.rxpermissions.RxPermissions;

import me.koterwong.widget.TipsToast;
import rx.functions.Action1;

/**
 * 如果申请权限无效,请把每个方法中的「compose(PermissionUtil.<Boolean>bindToLifecycle(view))」删掉
 *
 * Created by Koterwong on 2017/1/10 17:07
 */
public class PermissionUtils {
  public static final String TAG = "Permission";


  public static abstract class RequestPermission {
    public abstract void onRequestPermissionSuccess();

    public void onRequestPermissionFail(Context context) {
      TipsToast.makeText(context, "没有权限", Toast.LENGTH_SHORT).show();
    }
  }

  /**
   * 请求摄像头权限
   */
  public static void launchCamera(final Context context, final RequestPermission requestPermission) {
    //先确保是否已经申请过摄像头，和写入外部存储的权限
    RxPermissions rxPermissions = RxPermissions.getInstance(context);

    boolean isPermissionsGranted =
        rxPermissions.isGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE) && rxPermissions.isGranted(Manifest.permission.CAMERA);

    if (isPermissionsGranted) {//已经申请过，直接执行操作
      requestPermission.onRequestPermissionSuccess();
    } else {
      //没有申请过，则申请
      rxPermissions
          .request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
          .subscribe(new Action1<Boolean>() {
            @Override public void call(Boolean aBoolean) {
              if (aBoolean) {
                requestPermission.onRequestPermissionSuccess();
              } else {
                requestPermission.onRequestPermissionFail(context);
              }
            }
          });
    }
  }

  /**
   * 请求摄像头权限,外部存储权限
   */
  public static void carama_readStorage(final Context context, final RequestPermission requestPermission) {
    //先确保是否已经申请过摄像头，和写入外部存储的权限
    RxPermissions rxPermissions = RxPermissions.getInstance(context);

    boolean isPermissionsGranted =
        rxPermissions.isGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE) && rxPermissions.isGranted(Manifest.permission.CAMERA) && rxPermissions.isGranted(Manifest.permission.READ_EXTERNAL_STORAGE);

    if (isPermissionsGranted) {//已经申请过，直接执行操作
      requestPermission.onRequestPermissionSuccess();
    } else {
      //没有申请过，则申请
      rxPermissions
          .request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE)
          .subscribe(new Action1<Boolean>() {
            @Override public void call(Boolean aBoolean) {
              if (aBoolean) {
                requestPermission.onRequestPermissionSuccess();
              } else {
                requestPermission.onRequestPermissionFail(context);
              }
            }
          });
    }
  }


  /**
   * 请求外部存储的权限
   */
  public static void externalStorage(final Context context, final RequestPermission requestPermission) {
    RxPermissions rxPermissions = RxPermissions.getInstance(context);

    boolean isPermissionsGranted = rxPermissions
        .isGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE);

    if (isPermissionsGranted) {//已经申请过，直接执行操作
      requestPermission.onRequestPermissionSuccess();
    } else {
      rxPermissions
          .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
          .subscribe(new Action1<Boolean>() {
            @Override public void call(Boolean aBoolean) {
              if (aBoolean) {
                requestPermission.onRequestPermissionSuccess();
              } else {
                requestPermission.onRequestPermissionFail(context);
              }
            }
          });
    }
  }


  /**
   * 请求发送短信权限
   */
  public static void sendSms(final Context context, final RequestPermission requestPermission) {
    RxPermissions rxPermissions = RxPermissions.getInstance(context);

    boolean isPermissionsGranted = rxPermissions
        .isGranted(Manifest.permission.SEND_SMS);

    if (isPermissionsGranted) {//已经申请过，直接执行操作
      requestPermission.onRequestPermissionSuccess();
    } else {//没有申请过，则申请
      rxPermissions
          .request(Manifest.permission.SEND_SMS)
          .subscribe(new Action1<Boolean>() {
            @Override public void call(Boolean aBoolean) {
              if (aBoolean) {
                requestPermission.onRequestPermissionSuccess();
              } else {
                requestPermission.onRequestPermissionFail(context);
              }
            }
          });
    }
  }


  /**
   * 请求打电话权限
   */
  public static void callPhone(final Context context, final RequestPermission requestPermission) {
    RxPermissions rxPermissions = RxPermissions.getInstance(context);

    boolean isPermissionsGranted = rxPermissions
        .isGranted(Manifest.permission.CALL_PHONE);

    if (isPermissionsGranted) {//已经申请过，直接执行操作
      requestPermission.onRequestPermissionSuccess();
    } else {//没有申请过，则申请
      rxPermissions
          .request(Manifest.permission.CALL_PHONE)
          .subscribe(new Action1<Boolean>() {
            @Override public void call(Boolean aBoolean) {
              if (aBoolean) {
                requestPermission.onRequestPermissionSuccess();
              } else {
                requestPermission.onRequestPermissionFail(context);
              }
            }
          });
    }
  }


  /**
   * 请求获取手机状态的权限
   */
  public static void readPhonestate(final Context context, final RequestPermission requestPermission) {
    RxPermissions rxPermissions = RxPermissions.getInstance(context);

    boolean isPermissionsGranted = rxPermissions
        .isGranted(Manifest.permission.READ_PHONE_STATE);

    if (isPermissionsGranted) {//已经申请过，直接执行操作
      requestPermission.onRequestPermissionSuccess();
    } else {//没有申请过，则申请
      rxPermissions
          .request(Manifest.permission.READ_PHONE_STATE)
          .subscribe(new Action1<Boolean>() {
            @Override public void call(Boolean aBoolean) {
              if (aBoolean) {
                requestPermission.onRequestPermissionSuccess();
              } else {
                requestPermission.onRequestPermissionFail(context);
              }
            }
          });
    }
  }
}
