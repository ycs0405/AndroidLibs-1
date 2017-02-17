/*
 * ApiErrorHelper     2016/11/21 08:50
 * Copyright (c) 2016 Koterwong All right reserved
 */
package me.koterwong.net;

import android.content.Context;
import android.widget.Toast;

import java.io.IOException;

import me.koterwong.R;
import me.koterwong.base.BaseApplication;
import me.koterwong.common.LogKw;
import me.koterwong.widget.TipsToast;
import retrofit2.adapter.rxjava.HttpException;

/**
 * Created by Koterwong on 2016/11/21 08:50
 */
public class ApiErrorHelper {
  private static Context sContext = BaseApplication.get();

  public static void handleCommonError(Throwable e) {
    e.printStackTrace();
    if (e instanceof HttpException) {
      TipsToast.makeText(sContext, sContext.getString(R.string.service_error), Toast.LENGTH_SHORT).show();
    } else if (e instanceof IOException) {
      TipsToast.makeText(sContext, sContext.getString(R.string.connect_error), Toast.LENGTH_SHORT).show();
    } else {
//      TipsToast.makeText(sContext, sContext.getString(R.string.unknown_exception), Toast.LENGTH_SHORT).show();
    }
  }

  /**
   * 服务端错误统一处理。
   *
   * @param apiError apiError
   */
  public static void handleApiError(ApiErrorException apiError) {
    if (apiError == null) {
      LogKw.e("at handleApiError method ,the arguments is null,please check");
      return;
    }
    TipsToast.makeText(BaseApplication.get(), apiError.getMessage(), Toast.LENGTH_SHORT).show();
    //ApiException处理
    int code = apiError.getErrorCode();
    switch (code) {
      case ApiErrorCode.ERROR_CLIENT_AUTHORIZED:
        //handle
        break;
      case ApiErrorCode.ERROR_USER_AUTHORIZED:
        //handle
        break;
      case ApiErrorCode.ERROR_REQUEST_PARAM:
        //handle
        break;
      case ApiErrorCode.ERROR_PARAM_CHECK:
        //handle
        break;
      case ApiErrorCode.ERROR_OTHER:
        //handle
        break;
      case ApiErrorCode.ERROR_NO_INTERNET:
        //handle
        break;
    }
  }
}
