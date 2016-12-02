/*
 * ItemViewDelegate     2016/9/28-09-28
 * Copyright (c) 2016 KOTERWONG All right reserved
 */
package me.koterwong.widget.recyclerview.base;


import me.koterwong.widget.recyclerview.ViewHolder;

/**
 * Created by Koterwong on 2016/9/28 18:21
 */
public interface ItemViewDelegate<T> {
  int getItemViewLayoutId();

  boolean isForViewType(T item, int position);

  void convert(ViewHolder holder, T t, int position);

}

