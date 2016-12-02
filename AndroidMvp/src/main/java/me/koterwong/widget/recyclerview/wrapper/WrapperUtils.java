/*
 * WrapperUtils     2016/9/29-09-29
 * Copyright (c) 2016 KOTERWONG All right reserved
 */
package me.koterwong.widget.recyclerview.wrapper;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.ViewGroup;

/**
 * Created by Koterwong on 2016/9/29 07:49
 */
public class WrapperUtils {
  public interface SpanSizeCallback {
    /**
     * @param oldLookup helper class to provide the number of spans each item occupies.
     *                  Default implementation sets each item to occupy exactly 1 span.
     * @see GridLayoutManager#setSpanSizeLookup(GridLayoutManager.SpanSizeLookup)
     */
    int getSpanSize(GridLayoutManager layoutManager, GridLayoutManager.SpanSizeLookup oldLookup,
                    int position);
  }


  public static void onAttachedToRecyclerView(RecyclerView.Adapter innerAdapter, RecyclerView
      recyclerView, final SpanSizeCallback callback) {
    innerAdapter.onAttachedToRecyclerView(recyclerView);
    RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
    if (layoutManager instanceof GridLayoutManager) {
      final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
      final GridLayoutManager.SpanSizeLookup spanSizeLookup = gridLayoutManager.getSpanSizeLookup();
      gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
        @Override public int getSpanSize(int position) {
          return callback.getSpanSize(gridLayoutManager, spanSizeLookup, position);
        }
      });
      gridLayoutManager.setSpanCount(gridLayoutManager.getSpanCount());
    }
  }

  public static void setFullSpan(RecyclerView.ViewHolder holder) {
    ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
    if (lp != null && lp instanceof StaggeredGridLayoutManager.LayoutParams) {
      StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams) lp;
      params.setFullSpan(true);
    }
  }
}
