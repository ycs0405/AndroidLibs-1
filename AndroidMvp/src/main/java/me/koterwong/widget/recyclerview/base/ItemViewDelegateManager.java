/*
 * ItemViewDelegateManager     2016/9/28-09-28
 * Copyright (c) 2016 KOTERWONG All right reserved
 */
package me.koterwong.widget.recyclerview.base;

import android.support.v4.util.SparseArrayCompat;

import me.koterwong.widget.recyclerview.ViewHolder;


/**
 * Created by Koterwong on 2016/9/28 18:25
 */
public class ItemViewDelegateManager<T> {
  SparseArrayCompat<ItemViewDelegate<T>> delegates = new SparseArrayCompat<>();

  public int getItemViewDelegateCount() {
    return delegates.size();
  }

  public ItemViewDelegateManager addDelegate(ItemViewDelegate<T> delegate) {
    int viewType = delegates.size();
    if (delegate != null) {
      delegates.put(viewType, delegate);
      viewType++;
    }
    return this;
  }

  public ItemViewDelegateManager addDelegate(int viewType, ItemViewDelegate<T> delegate) {
    if (delegates.get(viewType) != null) {
      throw new IllegalArgumentException(
          "An ItemViewDelegate is already registered for the viewType = " +
              viewType +
              ".Already registered ItemViewDegalate is " +
              delegates.get(viewType));
    }
    delegates.put(viewType, delegate);
    return this;
  }

  public ItemViewDelegateManager removeDelegate(int viewType) {
    int indexToRemove = delegates.indexOfKey(viewType);
    if (indexToRemove >= 0) {
      delegates.removeAt(indexToRemove);
    }
    return this;
  }

  public ItemViewDelegateManager removeDelegate(ItemViewDelegate<T> delegate) {
    if (delegate == null) {
      throw new NullPointerException("ItemViewDelegate is null");
    }
    int indexToRemove = delegates.indexOfValue(delegate);
    if (indexToRemove >= 0) {
      delegates.removeAt(indexToRemove);
    }
    return this;
  }

  public int getItemViewType(T item, int position) {
    int delegatesCount = delegates.size();
    for (int i = delegatesCount - 1; i >= 0; i--) {
      ItemViewDelegate<T> delegate = delegates.valueAt(i);
      if (delegate.isForViewType(item, position)) {
        return delegates.keyAt(i);
      }
    }
    throw new IllegalArgumentException(
        "No ItemViewDelegate added that matches position=" + position + " in data source");
  }

  public void convert(ViewHolder holder, T item, int position) {
    int delegatesCount = delegates.size();
    for (int i = 0; i < delegatesCount; i++) {
      ItemViewDelegate<T> delegate = delegates.valueAt(i);

      if (delegate.isForViewType(item, position)) {
        delegate.convert(holder, item, position);
        return;
      }
    }
    throw new IllegalArgumentException(
        "No ItemViewDelegateManager added that matches position=" + position + " in data source");
  }


  public ItemViewDelegate getItemViewDelegate(int viewType) {
    return delegates.get(viewType);
  }

  public int getItemViewLayoutId(int viewType) {
    return getItemViewDelegate(viewType).getItemViewLayoutId();
  }

  public int getItemViewType(ItemViewDelegate itemViewDelegate) {
    return delegates.indexOfValue(itemViewDelegate);
  }

}
