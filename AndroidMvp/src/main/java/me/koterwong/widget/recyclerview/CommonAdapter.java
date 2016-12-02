/*
 * CommonAdaper     2016/9/28-09-28
 * Copyright (c) 2016 KOTERWONG All right reserved
 */
package me.koterwong.widget.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;

import java.util.List;

import me.koterwong.widget.recyclerview.base.ItemViewDelegate;


/**
 * Created by Koterwong on 2016/9/28 19:05
 */
public abstract class CommonAdapter<T> extends MultiItemTypeAdapter<T> {
  protected LayoutInflater mLayoutInflater;
  protected int mLayoutId;

  public CommonAdapter(Context context, final int layoutId, List<T> datas) {
    super(context, datas);
    this.mLayoutId = layoutId;
    this.mLayoutInflater = LayoutInflater.from(context);
    addItemViewDelegate(new ItemViewDelegate<T>() {
      @Override public int getItemViewLayoutId() {
        return mLayoutId;
      }

      @Override public boolean isForViewType(T item, int position) {
        return true;
      }

      @Override public void convert(ViewHolder holder, T t, int position) {
        CommonAdapter.this.convert(holder, t, position);
      }
    });
  }

  protected abstract void convert(ViewHolder holder, T t, int position);
}
