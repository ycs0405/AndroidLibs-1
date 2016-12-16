/*
 * ApiService     2016/11/28 09:08
 * Copyright (c) 2016 Koterwong All right reserved
 */
package me.koterwong.api;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by Koterwong on 2016/11/28 09:08
 *
 * RetrofitApi接口
 */
public interface ApiService {
  String BASE_URL = "http://www.baidu.com/";

  @GET("asfa")
  Observable<String> getBaiduData();
}
