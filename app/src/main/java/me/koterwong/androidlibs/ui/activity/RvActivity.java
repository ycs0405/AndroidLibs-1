/*
 * RvActivity     2016/9/29-09-29
 * Copyright (c) 2016 KOTERWONG All right reserved
 */
package me.koterwong.androidlibs.ui.activity;

import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import me.koterwong.androidlibs.R;
import me.koterwong.androidlibs.ui.adapter.RvMultiAdapoter;
import me.koterwong.base.BaseAppCompatActivity;
import me.koterwong.common.LogKw;
import me.koterwong.di.component.AppComponent;
import me.koterwong.statusbartint.StatusBarCompat;
import me.koterwong.widget.recyclerview.MultiItemTypeAdapter;
import me.koterwong.widget.recyclerview.wrapper.HeaderAndFooterWrapper;
import me.koterwong.widget.recyclerview.wrapper.LoadingMoreWrapper;

/**
 * Created by Koterwong on 2016/9/29 11:17
 */
public class RvActivity extends BaseAppCompatActivity {
  private final List<String> mDatas = new ArrayList<String>();

  {
    mDatas.add("0");
    mDatas.add("1");
    mDatas.add("2");
    mDatas.add("3");
    mDatas.add("4");
    mDatas.add("5");
    mDatas.add("6");
    mDatas.add("7");
    mDatas.add("8");
    mDatas.add("9");
    mDatas.add("10");
    mDatas.add("11");
    mDatas.add("12");
    mDatas.add("13");
    mDatas.add("14");
    mDatas.add("15");
  }


  RecyclerView mRecyclerView;

  @Override protected void setStatusBar() {
    StatusBarCompat.setStatusBarImmersive(this);
  }

  @Override protected int getLayoutId() {
    return R.layout.activity_rv;
  }

  @Override protected void injectComponent(AppComponent appComponent) {
    mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
  }

  @Override protected void initData() {
    mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    RvMultiAdapoter adapoter = new RvMultiAdapoter(this, mDatas);
    HeaderAndFooterWrapper headerAndFooterWrapper = new HeaderAndFooterWrapper(adapoter);
    TextView textView = new TextView(this);
    textView.setText("Header1");
    TextView textView1 = new TextView(this);
    textView1.setText("Header2");
    headerAndFooterWrapper.addHeaderView(textView);
    headerAndFooterWrapper.addHeaderView(textView1);
    LoadingMoreWrapper loadingMoreWrapper = new LoadingMoreWrapper(headerAndFooterWrapper);
    TextView textView2 = new TextView(this);
    textView2.setText("MORE");
    loadingMoreWrapper.setLoadingMoreView(textView2);
    loadingMoreWrapper.setOnLoadingMoreListener(new LoadingMoreWrapper.OnLoadingMoreListener() {
      @Override public void onLoadingMoreReq() {
        LogKw.e("logadingmore");
      }
    });
    mRecyclerView.setAdapter(loadingMoreWrapper);

    adapoter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
      @Override public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
        Snackbar.make(view, mDatas.get(position),Snackbar.LENGTH_SHORT).show();
      }

      @Override public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int
          position) {
        return false;
      }
    });
  }
}
