/*
 * BindingBaseAdapter     2016/11/27 08:56
 * Copyright (c) 2016 Koterwong All right reserved
 */
package me.koterwong.androiddatabinding.adapter.listview;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import me.koterwong.androiddatabinding.adapter.base.DynamicListAdapter;

/**
 * Created by Koterwong on 2016/11/27 08:56
 */
public abstract class BindingBaseAdapter<T, D extends ViewDataBinding>
    extends DynamicListAdapter<T> {
  private D mDatabing;

  protected int mLayoutId;
  private LayoutInflater mInflater;

  public BindingBaseAdapter(Context context, List<T> datas, @LayoutRes int layoutId) {
    super(datas);
    this.mLayoutId = layoutId;
    this.mInflater = LayoutInflater.from(context);
  }

  @Override public View getView(int position, View convertView, ViewGroup parent) {
    if (convertView == null) {
      mDatabing = DataBindingUtil.inflate(mInflater, mLayoutId, parent, false);
    } else {
      mDatabing = DataBindingUtil.bind(convertView);
    }
    bindingViews(position, mDatas.get(position),mDatabing);
    return mDatabing.getRoot();
  }

  /**
   * 对View进行数据绑定
   *  @param position
   * @param t
   * @param databing
   */
  public abstract void bindingViews(int position, T t, D databing);
}
