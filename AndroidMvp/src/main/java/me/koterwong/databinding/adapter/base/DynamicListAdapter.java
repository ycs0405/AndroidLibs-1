/*
 * DynamicListAdapter     2016/11/27 08:35
 * Copyright (c) 2016 Koterwong All right reserved
 */
package me.koterwong.databinding.adapter.base;

import android.widget.BaseAdapter;

import java.util.Collection;
import java.util.List;

/**
 * Created by Koterwong on 2016/11/27 08:35
 */
public abstract class DynamicListAdapter<T> extends BaseAdapter implements DynamicListDataSet<T> {
  protected List<T> mDatas;

  public DynamicListAdapter(List<T> datas) {
    mDatas = datas;
  }

  @Override public int getCount() {
    return mDatas == null ? 0 : mDatas.size();
  }

  @Override public long getItemId(int position) {
    return position;
  }

  @Override public T getItem(int position) {
    return mDatas.get(position);
  }

  @Override public void addItem(T data) {
    mDatas.add(data);
    notifyDataSetChanged();
  }

  @Override public void addItem(T data, int position) {
    mDatas.add(position, data);
    notifyDataSetChanged();
  }

  @Override public void addItems(Collection<T> datas) {
    mDatas.addAll(datas);
    notifyDataSetChanged();
  }

  @Override public void addItems(Collection<T> datas, int position) {
    mDatas.addAll(position, datas);
    notifyDataSetChanged();
  }

  @Override public void setItem(T data, int position) {
    mDatas.set(position, data);
    notifyDataSetChanged();
  }

  @Override public void setItems(Collection<T> data) {
    mDatas.clear();
    mDatas.addAll(data);
    notifyDataSetChanged();
  }

  @Override public void removeItem(int position) {
    mDatas.remove(position);
    notifyDataSetChanged();
  }

  @Override public void removeItem(T data) {
    mDatas.remove(data);
    notifyDataSetChanged();
  }

  @Override public void removeItems(Collection<T> data) {
    mDatas.removeAll(data);
    notifyDataSetChanged();
  }

  @Override public void clear() {
    mDatas.clear();
    notifyDataSetChanged();
  }
}

