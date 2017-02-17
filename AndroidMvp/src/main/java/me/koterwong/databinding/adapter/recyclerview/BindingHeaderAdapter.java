/*
 * BindingHeaderAdapter     2017/1/13 14:41
 * Copyright (c) 2017 Koterwong All right reserved
 */
package me.koterwong.databinding.adapter.recyclerview;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Koterwong on 2017/1/13 14:41
 *
 * HeaderView 和 FooterView 必须使用符合DataBinding标准的layout
 */
public abstract class BindingHeaderAdapter<T, D extends ViewDataBinding>
    extends BindingRvAdapter<T, D> {
  private static final int ITEM_TYPE_HEADER = 1000;
  private static final int ITEM_TYPE_FOOTER = 2000;

  private SparseArray<View> mHeaderViews = new SparseArray<>();
  private SparseArray<View> mFooterViews = new SparseArray<>();

  public BindingHeaderAdapter(int layoutId, List<T> datas, Context context) {
    super(layoutId, datas, context);
  }

  public void addFooterView(View view) {
    mFooterViews.put(mFooterViews.size() + ITEM_TYPE_FOOTER, view);
  }

  public void addHeaderView(View view) {
    mHeaderViews.put(mHeaderViews.size() + ITEM_TYPE_HEADER, view);
  }

  private boolean isHeaderPosition(int position) {
    return position < mHeaderViews.size();
  }

  private boolean isFooterPosition(int position) {
    return position >= super.getItemCount() + mHeaderViews.size();
  }

  @Override public BindingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    if (mHeaderViews.get(viewType) != null) {
      return new BindingViewHolder<>(DataBindingUtil.bind(mHeaderViews.get(viewType)));
    } else if (mFooterViews.get(viewType) != null) {
      return new BindingViewHolder<>(DataBindingUtil.bind(mHeaderViews.get(viewType)));
    }
    return super.onCreateViewHolder(parent, viewType);
  }

  @Override public void onBindViewHolder(BindingViewHolder holder, int position) {
    if (isHeaderPosition(position)) {
      return;
    }

    if (isFooterPosition(position)) {
      return;
    }

    super.onBindViewHolder(holder, position - mHeaderViews.size());
  }

  @Override public int getItemCount() {
    return super.getItemCount() + mHeaderViews.size() + mFooterViews.size();
  }

  @Override public int getItemViewType(int position) {
    if (isHeaderPosition(position)) {
      return mHeaderViews.keyAt(position);
    } else if (isFooterPosition(position)) {
      return mFooterViews.keyAt(position - mHeaderViews.size() - super.getItemCount());
    }
    return super.getItemViewType(position);
  }
}
