/*
 * ApiErrorCode     2016/11/20 09:39
 * Copyright (c) 2016 Koterwong All right reserved
 */
package me.koterwong.net;

/**
 * Created by Koterwong on 2016/11/20 09:39
 *
 * code为和服务端约定的状态码，这个为了更好的描述返回操作码，定义ApiCode
 */
public interface ApiErrorCode {
  /** 返回结果成功 */
  int SUCCESS_CODE = 200;
  /** 无网络连接 */
  int ERROR_NO_INTERNET = 11;
  /** 客户端错误 */
  int ERROR_CLIENT_AUTHORIZED = 1;
  /** 用户授权失败 */
  int ERROR_USER_AUTHORIZED = 2;
  /** 请求参数错误 */
  int ERROR_REQUEST_PARAM = 3;
  /** 参数检验不通过 */
  int ERROR_PARAM_CHECK = 4;
  /** 自定义错误 */
  int ERROR_OTHER = 10;
}
