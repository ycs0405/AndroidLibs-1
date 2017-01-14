/*
 * ClientModule     2016/9/20-09-20
 * Copyright (c) 2016 KOTERWONG All right reserved
 */
package me.koterwong.di.module;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.koterwong.net.GlobeHttpHandler;
import me.koterwong.net.RequestInterceptor;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Koterwong on 2016/9/20 14:51
 *
 * 有关网络的客户端工厂
 */
@Module
public class ClientModule {
  public static final int TIME_OUT = 10;
  public static final int HTTP_RESPONSE_DISK_CACHE_MAX_SIZE = 10 * 1024 * 1024;//缓存文件最大值为10Mb

  private HttpUrl mApiUrl;
  private GlobeHttpHandler mHandler;

  public ClientModule(HttpUrl apiUrl, GlobeHttpHandler handler) {
    this.mApiUrl = apiUrl;
    this.mHandler = handler;
  }

  /**
   * 提供BaseUrl
   *
   * @return
   */
  @Provides @Singleton public HttpUrl provideHttpUrl() {
    return mApiUrl;
  }

  /**
   * 提供全局的HttpHandler，用于拦截请求和对响应结果的预处理。
   *
   * @return
   */
  @Provides @Singleton public GlobeHttpHandler provideGlobeResponseHandler() {
    return mHandler;
  }

  /**
   * 提供OkHttp拦截器。
   *
   * @param handler
   * @return
   */
  @Provides @Singleton public Interceptor provideInterptor(GlobeHttpHandler handler) {
    return new RequestInterceptor(handler);
  }

  /**
   * 提供OkHttp客户端
   *
   * @param interceptor
   * @return
   */
  @Provides @Singleton public OkHttpClient provideOkHttpClient(Interceptor interceptor) {
    final OkHttpClient.Builder builder = new OkHttpClient.Builder();
    return configOkHttpClick(builder, interceptor);
  }

  /**
   * 提供Retrofit对象
   *
   * @param okHttpClient
   * @param httpUrl
   * @return
   */
  @Provides @Singleton public Retrofit provideRetrofit(OkHttpClient okHttpClient, HttpUrl httpUrl) {
    Retrofit.Builder builder = new Retrofit.Builder();
    return configRetrofit(builder, okHttpClient, httpUrl);
  }

  private OkHttpClient configOkHttpClick(OkHttpClient.Builder builder, Interceptor interceptor) {
    OkHttpClient.Builder clientBuilder = builder
        .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
        .readTimeout(TIME_OUT, TimeUnit.SECONDS)
        .addInterceptor(interceptor);
    return clientBuilder.build();
  }

  private Retrofit configRetrofit(Retrofit.Builder builder, OkHttpClient okHttpClient, HttpUrl httpUrl) {
    return builder
        .baseUrl(mApiUrl)
        .client(okHttpClient)
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build();
  }
}
