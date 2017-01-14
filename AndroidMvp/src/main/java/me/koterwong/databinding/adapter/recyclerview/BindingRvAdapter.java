/*
 * BinddingRvAdapter     2016/11/27 09:06
 * Copyright (c) 2016 Koterwong All right reserved
 */
package me.koterwong.databinding.adapter.recyclerview;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collection;
import java.util.List;

import me.koterwong.databinding.adapter.base.DynamicListDataSet;


/**
 * Created by Koterwong on 2016/11/27 09:06
 */
public abstract class BindingRvAdapter<T, D extends ViewDataBinding>
    extends RecyclerView.Adapter<BindingViewHolder<D>> implements DynamicListDataSet<T> {
  protected List<T> mDatas;
  protected Context mContext;

  private int mLayoutId;
  protected LayoutInflater mInflater;

  public BindingRvAdapter(int layoutId, List<T> datas, Context context) {
    mLayoutId = layoutId;
    mDatas = datas;
    mContext = context;
    mInflater = LayoutInflater.from(mContext);
  }

  @Override public BindingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View rootView = mInflater.inflate(mLayoutId, parent, false);
    return new BindingViewHolder<>(DataBindingUtil.bind(rootView));
  }

  @Override public void onBindViewHolder(BindingViewHolder holder, int position) {
    bindingViews(position, mDatas.get(position), (D) holder.getBinding());
  }

  protected abstract void bindingViews(int position, T t, D binding);

  @Override public int getItemCount() {
    return mDatas == null ? 0 : mDatas.size();
  }

  ///////////////////////
  /// 动态数据 ////////////
  ///////////////////////
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
    notifyItemRemoved(position);
  }

  @Override public void removeItem(T data) {
    int pos = mDatas.indexOf(data);
    mDatas.remove(data);
    if (pos != -1) {
      notifyItemRemoved(pos);
    } else {
      notifyDataSetChanged();
    }
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
