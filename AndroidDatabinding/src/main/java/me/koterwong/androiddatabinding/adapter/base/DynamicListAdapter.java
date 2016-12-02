/*
 * DynamicListAdapter     2016/11/27 08:35
 * Copyright (c) 2016 Koterwong All right reserved
 */
package me.koterwong.androiddatabinding.adapter.base;

import android.widget.BaseAdapter;

import java.util.Collection;
import java.util.List;

/**
 * Created by Koterwong on 2016/11/27 08:35
 */
public abstract class DynamicListAdapter<T> extends BaseAdapter implements DynamicListDataSet<T> {
  protected List<T> mDatas;

  public DynamicListAdapter( List<T> datas) {
    mDatas = datas;
  }

  @Override public int getCount() {
    return mDatas.size();
  }

  @Override public long getItemId(int position) {
    return position;
  }

  @Override public Object getItem(int position) {
    return mDatas.get(position);
  }

  /**
   * 添加一条数据
   *
   * @param data
   */
  @Override public void addItem(T data) {
    mDatas.add(data);
    notifyDataSetChanged();
  }

  /**
   * 指定位置添加一条数据
   *
   * @param data
   * @param position
   */
  @Override public void addItem(T data, int position) {
    mDatas.add(position, data);
    notifyDataSetChanged();
  }

  /**
   * 添加多条数据
   *
   * @param datas
   */
  @Override public void addItems(Collection<T> datas) {
    mDatas.addAll(datas);
    notifyDataSetChanged();
  }

  @Override public void addItems(Collection<T> datas, int position) {
    mDatas.addAll(position, datas);
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

