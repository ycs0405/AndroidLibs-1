/*
 * GlobeHttpResultHandler     2016/9/20-09-20
 * Copyright (c) 2016 KOTERWONG All right reserved
 */
package me.koterwong.net;

import okhttp3.Interceptor;
import okhttp3.Request;
  import okhttp3.Response;

/**
 * Created by Koterwong on 2016/9/20 15:03
 */
public interface GlobeHttpHandler {
  /** 请求拦截，可在Request中统一添加token */
  Request onHttpRequestBefore(Request request);

  /** 响应拦截，对token过期等做相应的处理 */
  Response onHttpResultResponse(String httpResult, Interceptor.Chain chain, Response response);
}
