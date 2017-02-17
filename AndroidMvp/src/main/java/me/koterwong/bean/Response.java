/*
 * Response     2016/11/20 09:22
 * Copyright (c) 2016 Koterwong All right reserved
 */
package me.koterwong.bean;

import com.google.gson.annotations.SerializedName;

import me.koterwong.net.ApiErrorCode;

/**
 * Created by Koterwong on 2016/11/20 09:22
 *
 * 对网络返回数据协议格式进行抽象，以更好的映射我们需要的数据模型。
 */
public class Response<T> {
  private int code;
  @SerializedName("message")
  private String msg;
  private T data;

  public boolean isOk() {
    return code == ApiErrorCode.SUCCESS_CODE || code == 1;
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }
}
