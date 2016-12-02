/*
 * DynamicListDataSet     2016/11/27 08:31
 * Copyright (c) 2016 Koterwong All right reserved
 */
package me.koterwong.androiddatabinding.adapter.base;

import java.util.Collection;

/**
 * Created by Koterwong on 2016/11/27 08:31
 */
public interface DynamicListDataSet<T> {
  void addItem(T data);

  void addItem(T data, int position);

  void addItems(Collection<T> datas);

  void addItems(Collection<T> datas, int position);

  void removeItem(int position);

  void removeItem(T data);

  void removeItems(Collection<T> data);

  void clear();
}
