/*
 * HeaderAndFooterWrapper     2016/9/28-09-28
 * Copyright (c) 2016 KOTERWONG All right reserved
 */
package me.koterwong.widget.recyclerview.wrapper;

import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import me.koterwong.widget.recyclerview.ViewHolder;


/**
 * Created by Koterwong on 2016/9/28 19:27
 */
public class HeaderAndFooterWrapper<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
  private static final int BASE_ITEM_TYPE_HEADER = 100000;
  private static final int BASE_ITEM_TYPE_FOOTER = 200000;

  private SparseArrayCompat<View> mHeaderViews = new SparseArrayCompat<>();
  private SparseArrayCompat<View> mFooterViews = new SparseArrayCompat<>();

  private RecyclerView.Adapter mInnerAdapter;

  public HeaderAndFooterWrapper(RecyclerView.Adapter innerAdapter) {
    mInnerAdapter = innerAdapter;
  }

  public void addFooterView(View view) {
    mFooterViews.put(mFooterViews.size() + BASE_ITEM_TYPE_FOOTER, view);
  }

  public void addHeaderView(View view) {
    mHeaderViews.put(mHeaderViews.size() + BASE_ITEM_TYPE_HEADER, view);
  }

  @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    if (mHeaderViews.get(viewType) != null) {
      return ViewHolder.createViewHolder(parent.getContext(), mHeaderViews.get(viewType));
    } else if (mFooterViews.get(viewType) != null) {
      return ViewHolder.createViewHolder(parent.getContext(), mFooterViews.get(viewType));
    }
    return mInnerAdapter.onCreateViewHolder(parent, viewType);
  }

  @Override public int getItemViewType(int position) {
    if (isHeaderPosition(position)) {
      return mHeaderViews.keyAt(position);
    } else if (isFooterPosition(position)) {
      return mFooterViews.keyAt(position - getHeadersCount() - getRealItemCount());
    }
    return mInnerAdapter.getItemViewType(position - getHeadersCount());
  }

  /**
   * Called by RecyclerView to display the data at the specified position. This method should update
   * the contents of the {@link RecyclerView.ViewHolder#itemView} to reflect the item at the given
   * position.
   * <p>
   * Note that unlike {@link android.widget.ListView}, RecyclerView will not call this method again
   * if the position of the item changes in the data set unless the item itself is invalidated or
   * the new position cannot be determined. For this reason, you should only use the
   * <code>position</code> parameter while acquiring the related data item inside this method and
   * should not keep a copy of it. If you need the position of an item later on (e.g. in a click
   * listener), use {@link RecyclerView.ViewHolder#getAdapterPosition()} which will have the updated
   * adapter position.
   * <p>
   * Override {@link #onBindViewHolder(RecyclerView.ViewHolder, int, List)} instead if Adapter can
   * handle efficient partial bind.
   *
   * @param holder   The ViewHolder which should be updated to represent the contents of the item at
   *                 the given position in the data set.
   * @param position The position of the item within the adapter's data set.
   */
  @Override public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    if (isFooterPosition(position)) {
      return;
    }
    if (isHeaderPosition(position)) {
      return;
    }
    mInnerAdapter.onBindViewHolder(holder, position - getHeadersCount());
  }

  @Override public int getItemCount() {
    return mInnerAdapter.getItemCount() + getHeadersCount() + getFootersCount();
  }

  /**
   * Called by RecyclerView when it starts observing this Adapter.
   * <p>
   * Keep in mind that same adapter may be observed by multiple RecyclerViews.
   *
   * @param recyclerView The RecyclerView instance which started observing this adapter.
   * @see #onDetachedFromRecyclerView(RecyclerView)
   */
  @Override public void onAttachedToRecyclerView(RecyclerView recyclerView) {
    WrapperUtils.onAttachedToRecyclerView(mInnerAdapter, recyclerView, new WrapperUtils
        .SpanSizeCallback() {
      @Override public int getSpanSize(GridLayoutManager layoutManager, GridLayoutManager
          .SpanSizeLookup oldLookup, int position) {
        int viewType = getItemViewType(position);
        if (mHeaderViews.valueAt(viewType) != null) {
          return layoutManager.getSpanCount();
        } else if (mFooterViews.valueAt(viewType) != null) {
          return layoutManager.getSpanCount();
        }
        if (oldLookup != null) {
          return oldLookup.getSpanSize(position);
        }
        return 1;
      }
    });
  }

  @Override public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
    mInnerAdapter.onViewAttachedToWindow(holder);
    int position = holder.getLayoutPosition();
    if (isHeaderPosition(position) || isFooterPosition(position)) {
      WrapperUtils.setFullSpan(holder);
    }
  }

  public int getRealItemCount() {
    return mInnerAdapter.getItemCount();
  }

  private boolean isHeaderPosition(int position) {
    return position < getHeadersCount();
  }

  private boolean isFooterPosition(int position) {
    return position >= getRealItemCount() + getHeadersCount();
  }

  public int getHeadersCount() {
    return mHeaderViews.size();
  }

  public int getFootersCount() {
    return mFooterViews.size();
  }

}
