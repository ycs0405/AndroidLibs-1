/*
 * MapViewActivity     2016/9/26-09-26
 * Copyright (c) 2016 KOTERWONG All right reserved
 */
package me.koterwong.kwmap.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;

import me.koterwong.kwmap.KwMapInterface;
import me.koterwong.kwmap.R;

/**
 * Created by Koterwong on 2016/9/26 09:59
 */
public class MapViewActivity extends AppCompatActivity {
  private MapView mMapView;
  private BaiduMap mBaiduMap;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    //一般在Application中初始化，在Activity中需要在setContentView之前。
    KwMapInterface.initialize(getApplicationContext());
    setContentView(R.layout.activity_map_view);
    mMapView = (MapView) findViewById(R.id.bmapView);
    mBaiduMap = mMapView.getMap();
  }

  @Override protected void onResume() {
    super.onResume();
    mMapView.onResume();
  }

  @Override protected void onPause() {
    super.onPause();
    mMapView.onPause();
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    mMapView.onDestroy();
  }
}
