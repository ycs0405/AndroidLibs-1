/*
 * RequestInterceptor     2016/9/20-09-20
 * Copyright (c) 2016 KOTERWONG All right reserved
 */
package me.koterwong.net;

import android.util.Log;

import java.io.IOException;

import me.koterwong.common.LogKw;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

/**
 * Created by Koterwong on 2016/9/20 15:13
 */
public class RequestInterceptor implements Interceptor {
  private static final String HTTP_REQUEST = "HttpRequest";
  private static final String HTTP_RESPONSE = "HttpResponse";

  private GlobeHttpHandler mHandler;

  public RequestInterceptor(GlobeHttpHandler handler) {
    mHandler = handler;
  }

  @Override public Response intercept(Chain chain) throws IOException {
    Request request = chain.request();

    if (mHandler != null) {
      request = mHandler.onHttpRequestBefore(request);
    }

    Buffer requestBuffer = new Buffer();
    if (request.body() != null) {
      request.body().writeTo(requestBuffer);
    }

    //打印请求信息
    LogKw.d(HTTP_REQUEST, "Method --> %s Url --> %s%n Params --> %s%n connection --> %s%n headers --> %s",
        new Object[]{
            request.method(),
            request.url(),
            request.body() == null ? "null" : requestBuffer.readUtf8(),
            chain.connection(),
            request.headers()});

    long t1 = System.nanoTime();
    Response originalResponse = chain.proceed(request);
    long t2 = System.nanoTime();

    //打印响应时间
    LogKw.d(HTTP_RESPONSE, "Received Response in %.1fms%n%s", new Object[]{
        (t2 - t1) / 1e6d,
        originalResponse.headers()});

    //打印服务器返回结果
    Response.Builder builder = originalResponse.newBuilder();
    Response clone = builder.build();  //克隆返回对象。
    ResponseBody body = clone.body();

    if (body != null) {
      MediaType mediaType = body.contentType();

      if (mediaType != null) {
        LogKw.d(HTTP_RESPONSE, "ResponseBody's contentType : " + mediaType.toString());

        if (isText(mediaType)) {
          String resp = body.string();
          LogKw.json(HTTP_RESPONSE, resp);
          body = ResponseBody.create(mediaType, resp);

          if (mHandler != null)//这里可以比客户端提前一步拿到服务器返回的结果,可以做一些操作,比如token超时,重新获取
            return  mHandler.onHttpResultResponse(resp, chain, originalResponse.newBuilder().body(body).build());
        } else {
          Log.e(HTTP_RESPONSE, "responseBody's content : " + " maybe [file part] , too large too print , ignored!");
        }
      }
    }

    return originalResponse;
  }

  private boolean isText(MediaType mediaType) {
    if (mediaType.type() != null && mediaType.type().equals("text")) {
      return true;
    }
    if (mediaType.subtype() != null) {
      if (mediaType.subtype().equals("json") ||
          mediaType.subtype().equals("xml") ||
          mediaType.subtype().equals("html") ||
          mediaType.subtype().equals("webviewhtml"))
        return true;
    }
    return false;
  }
}
