/*
 * BaseApplication     2016/9/20-09-20
 * Copyright (c) 2016 KOTERWONG All right reserved
 */
package me.koterwong.androidlibs.base;

import me.koterwong.androidlibs.R;
import me.koterwong.api.ApiService;
import me.koterwong.api.GlobeHttpHandler;
import me.koterwong.base.BaseApplication;
import me.koterwong.share.KwShareInterface;
import me.koterwong.share.ShareKey;
import me.koterwong.widget.stateview.StateViewMgr;
import okhttp3.HttpUrl;
import okhttp3.Request;

/**
 * Created by Koterwong on 2016/9/20 19:45
 *
 * 适合初始化一些第三方框架，和该项目中要使用的东西。
 */
public class App extends BaseApplication {

  @Override public void onCreate() {
    super.onCreate();
    KwShareInterface.init(getShareKey());

    StateViewMgr.BASE_VIEW_EMPTY_ID = R.layout.base_empty;
    StateViewMgr.BASE_VIEW_RETRY_ID = R.layout.base_retry;
    StateViewMgr.BASE_VIEW_LOADING_ID = R.layout.base_loading;
  }

  private ShareKey getShareKey() {
    return new ShareKey.Builder()
        .qqKey("1105474989")
        .qqSecret("zyVEkvUeZ8mGv5vB")
        .build();
  }

  @Override protected GlobeHttpHandler getHttpHandler() {
    return new GlobeHttpHandler() {
      @Override public Request onHttpRequestBefore(Request request) {
        return request;
      }

      @Override public void onHttpResultResponse(String response) {
        //对返回结果进行处理
      }
    };
  }

  @Override protected HttpUrl getHttpUrl() {
    return HttpUrl.parse(ApiService.BASE_URL);
  }
}
