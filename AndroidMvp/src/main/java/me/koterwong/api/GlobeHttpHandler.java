/*
 * GlobeHttpResultHandler     2016/9/20-09-20
 * Copyright (c) 2016 KOTERWONG All right reserved
 */
package me.koterwong.api;

import okhttp3.Request;

/**
 * Created by Koterwong on 2016/9/20 15:03
 */
public interface GlobeHttpHandler {
  Request onHttpRequestBefore(Request request);

  void onHttpResultResponse(String response);
}
