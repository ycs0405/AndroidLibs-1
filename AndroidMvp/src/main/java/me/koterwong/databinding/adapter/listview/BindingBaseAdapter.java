/*
 * BindingBaseAdapter     2016/11/27 08:56
 * Copyright (c) 2016 Koterwong All right reserved
 */
package me.koterwong.databinding.adapter.listview;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import me.koterwong.databinding.adapter.base.DynamicListAdapter;
import me.koterwong.utils.IntentHandler;


/**
 * Created by Koterwong on 2016/11/27 08:56
 */
public abstract class BindingBaseAdapter<T, D extends ViewDataBinding> extends DynamicListAdapter<T> {
  private D mDatabing;

  private int mLayoutId;
  private LayoutInflater mInflater;
  private IntentHandler mIntentHandler;
  private Context mContext;

  public BindingBaseAdapter(Context context, List<T> datas, @LayoutRes int layoutId) {
    super(datas);
    this.mContext = context;
    this.mLayoutId = layoutId;
    this.mInflater = LayoutInflater.from(context);
  }

  @Override public View getView(int position, View convertView, ViewGroup parent) {
    if (convertView == null) {
      mDatabing = DataBindingUtil.inflate(mInflater, mLayoutId, parent, false);
      convertView = mDatabing.getRoot();
      convertView.setTag(mDatabing);
    } else {
      mDatabing = (D) convertView.getTag();
//      mDatabing = DataBindingUtil.bind(convertView);
    }
    bindingViews(position, mDatas.get(position),mDatabing);
    return convertView;
//    return mDatabing.getRoot();
  }

  /**
   * 对View进行数据绑定
   *  @param position
   * @param t
   * @param databing
   */
  public abstract void bindingViews(int position, T t, D databing);

  protected IntentHandler getIntentHandler() {
    if (mIntentHandler == null) {
      mIntentHandler = new IntentHandler((Activity) mContext);
    }
    return mIntentHandler;
  }
}
