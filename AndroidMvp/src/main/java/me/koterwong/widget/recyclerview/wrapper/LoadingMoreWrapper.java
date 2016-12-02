/*
 * LoadingMoreWrapper     2016/9/29-09-29
 * Copyright (c) 2016 KOTERWONG All right reserved
 */
package me.koterwong.widget.recyclerview.wrapper;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import me.koterwong.widget.recyclerview.ViewHolder;


/**
 * Created by Koterwong on 2016/9/29 09:28
 */
public class LoadingMoreWrapper<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
  public static final int ITEM_TYPE_LOADING_MORE = Integer.MAX_VALUE - 2;

  private RecyclerView.Adapter mInnerAdapter;
  private View mLoadingMoreView;
  private int mLoadingMoreLayoutId;

  private OnLoadingMoreListener mLoadingMoreListener;

  public LoadingMoreWrapper(RecyclerView.Adapter innerAdapter) {
    mInnerAdapter = innerAdapter;
  }

  public LoadingMoreWrapper(RecyclerView.Adapter innerAdapter, View loadingMoreView) {
    mInnerAdapter = innerAdapter;
    mLoadingMoreView = loadingMoreView;
  }

  public LoadingMoreWrapper(RecyclerView.Adapter innerAdapter, int loadingMoreLayoutId) {
    mInnerAdapter = innerAdapter;
    mLoadingMoreLayoutId = loadingMoreLayoutId;
  }

  public boolean hasLoadingMore() {
    return mLoadingMoreView != null || mLoadingMoreLayoutId != 0;
  }

  public boolean isShowLoadingMore(int position) {
    return hasLoadingMore() && position >= mInnerAdapter.getItemCount();
  }

  @Override public int getItemViewType(int position) {
    if (isShowLoadingMore(position)) {
      return ITEM_TYPE_LOADING_MORE;
    }
    return mInnerAdapter.getItemViewType(position);
  }

  @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    if (viewType == ITEM_TYPE_LOADING_MORE) {
      if (mLoadingMoreView != null)
        return ViewHolder.createViewHolder(parent.getContext(), mLoadingMoreView);
      else {
        return ViewHolder.createViewHolder(parent.getContext(), parent, mLoadingMoreLayoutId);
      }
    }
    return mInnerAdapter.onCreateViewHolder(parent, viewType);
  }

  @Override public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    if (isShowLoadingMore(position)) {
      if (mLoadingMoreListener != null) {
        mLoadingMoreListener.onLoadingMoreReq();
      }
      return;
    }
    mInnerAdapter.onBindViewHolder(holder, position);
  }

  @Override public int getItemCount() {
    return mInnerAdapter.getItemCount() + (hasLoadingMore() ? 1 : 0);
  }

  @Override public void onAttachedToRecyclerView(RecyclerView recyclerView) {
    WrapperUtils.onAttachedToRecyclerView(mInnerAdapter, recyclerView, new WrapperUtils
        .SpanSizeCallback() {
      @Override
      public int getSpanSize(GridLayoutManager layoutManager, GridLayoutManager.SpanSizeLookup
          oldLookup, int position) {
        if (isShowLoadingMore(position)) {
          return layoutManager.getSpanCount();
        }
        if (oldLookup != null) {
          return oldLookup.getSpanSize(position);
        }
        return 1;
      }
    });
  }

  @Override
  public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
    mInnerAdapter.onViewAttachedToWindow(holder);
    if (isShowLoadingMore(holder.getLayoutPosition())) {
      setFullSpan(holder);
    }
  }

  private void setFullSpan(RecyclerView.ViewHolder holder) {
    ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
    if (lp != null
        && lp instanceof StaggeredGridLayoutManager.LayoutParams) {
      StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;

      p.setFullSpan(true);
    }
  }

  public interface OnLoadingMoreListener {
    void onLoadingMoreReq();
  }

  public LoadingMoreWrapper setOnLoadingMoreListener(OnLoadingMoreListener loadingMoreListener) {
    if (mLoadingMoreListener == null) {
      this.mLoadingMoreListener = loadingMoreListener;
    }
    return this;
  }

  public LoadingMoreWrapper setLoadingMoreView(View loadingMoreView) {
    this.mLoadingMoreView = loadingMoreView;
    return this;
  }

  public LoadingMoreWrapper setLoadingMoreLayoutId(int loadingMoreLayoutId) {
    this.mLoadingMoreLayoutId = loadingMoreLayoutId;
    return this;
  }
}
