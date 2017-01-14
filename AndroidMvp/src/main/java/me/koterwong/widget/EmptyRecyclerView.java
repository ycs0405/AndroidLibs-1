/*
 * EmptyRecyclerView     2017/1/14 11:21
 * Copyright (c) 2017 Koterwong All right reserved
 */
package me.koterwong.widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by Koterwong on 2017/1/14 11:21
 */
public class EmptyRecyclerView extends RecyclerView {
  private AdapterDataChangeListener mOnChangeListener;

  final private AdapterDataObserver observer = new AdapterDataObserver() {
    @Override
    public void onChanged() {
      if (mOnChangeListener != null)
        mOnChangeListener.onChangeListener();
    }
  };

  public EmptyRecyclerView(Context context) {
    super(context);
  }

  public EmptyRecyclerView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public EmptyRecyclerView(Context context, AttributeSet attrs,
                           int defStyle) {
    super(context, attrs, defStyle);
  }

  @Override
  public void setAdapter(Adapter adapter) {
    final Adapter oldAdapter = getAdapter();
    if (oldAdapter != null) {
      oldAdapter.unregisterAdapterDataObserver(observer);
    }
    super.setAdapter(adapter);
    if (adapter != null) {
      adapter.registerAdapterDataObserver(observer);
    }
  }

  public void setAdapterDataChangeListener(AdapterDataChangeListener listener) {
    mOnChangeListener = listener;
  }

  public interface AdapterDataChangeListener {
    void onChangeListener();
  }
}
