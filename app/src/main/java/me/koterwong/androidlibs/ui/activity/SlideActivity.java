/*
 * SlideActivity     2016/10/1-10-01
 * Copyright (c) 2016 KOTERWONG All right reserved
 */
package me.koterwong.androidlibs.ui.activity;

import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import me.koterwong.androidlibs.R;
import me.koterwong.androidlibs.base.BaseActivity;
import me.koterwong.common.LogKw;
import me.koterwong.di.component.AppComponent;
import me.koterwong.widget.slidelayout.SlideLayout;
import me.koterwong.widget.slidelayout.transformers.ZoomInTransformer;

/**
 * Created by Koterwong on 2016/10/1 11:48
 */
public class SlideActivity extends BaseActivity {
  private List<String> mimg = new ArrayList<>();

  {
    mimg.add("http://img1.3lian.com/2015/w7/98/d/22.jpg");
    mimg.add("http://desk.zol.com.cn/showpic/1366x768_61958_102.html");
    mimg.add("http://pic1.bbzhi.com/fengjingbizhi/sijizhutibizhiyiqingliangxiari" +
        "/nature_summer_11545_2.jpg");
  }

  SlideLayout mSlideLayout;

  @Override protected int getLayoutId() {
    return R.layout.activity_slide;
  }

  @Override protected void injectComponent(AppComponent appComponent) {
    mSlideLayout = (SlideLayout) findViewById(R.id.slide_layout);

    mSlideLayout
        .bind(mimg)
        .setPagerTransform(new ZoomInTransformer())
        .withListener(new SlideLayout.SlideItemClick() {
          @Override public void onSlideItemClick(int position) {
            Toast.makeText(mAppContext, "" + position, Toast.LENGTH_SHORT).show();
          }
        }).start();
  }

  @Override protected void onPause() {
    super.onPause();
    LogKw.e("onPause");
    mSlideLayout.stop();
  }
}
