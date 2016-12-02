/*
 * BinddingRvAdapter     2016/11/27 09:06
 * Copyright (c) 2016 Koterwong All right reserved
 */
package me.koterwong.androiddatabinding.adapter.recyclerview;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collection;
import java.util.List;

import me.koterwong.androiddatabinding.adapter.base.DynamicListDataSet;

/**
 * Created by Koterwong on 2016/11/27 09:06
 */
public abstract class BinddingRvAdapter<T,D extends ViewDataBinding>
    extends RecyclerView.Adapter<BindingViewHolder<D>> implements DynamicListDataSet<T> {
  protected List<T> mDatas;
  protected Context mContext;

  private int mLayoutId;
  private LayoutInflater mInflater;

  public BinddingRvAdapter(int layoutId, List<T> datas, Context context) {
    mLayoutId = layoutId;
    mDatas = datas;
    mContext = context;
    mInflater = LayoutInflater.from(mContext);
  }


  @Override public BindingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View rootView = LayoutInflater.from(parent.getContext()).inflate(mLayoutId, parent, false);
    return new BindingViewHolder<>(DataBindingUtil.bind(rootView));
  }

  @Override public void onBindViewHolder(BindingViewHolder holder, int position) {
    bindingViews(position,mDatas.get(position),(D)holder.getBinding());
  }

  protected abstract void bindingViews(int position, T t, D binding);

  @Override public int getItemCount() {
    return mDatas.size();
  }

  ///////////////////////
  /// 动态数据 ////////////
  ///////////////////////
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
