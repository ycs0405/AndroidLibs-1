/*
 * ApiException     2016/11/20 09:37
 * Copyright (c) 2016 Koterwong All right reserved
 */
package me.koterwong.net;

/**
 * Created by Koterwong on 2016/11/20 09:37.
 *
 * 响应数据非0（服务端定义的操作成功返回码，也可能是1）的错误统一处理。
 *
 * 用于描述所有的不成功情况，定义ApiException。
 */
public class ApiErrorException extends Exception {
  private int errorCode;

  public ApiErrorException(int code, String msg) {
    super(msg);
    this.errorCode = code;
  }

  public int getErrorCode() {
    return errorCode;
  }
}
