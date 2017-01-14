/*
 * BindingViewHolder     2016/11/27 09:06
 * Copyright (c) 2016 Koterwong All right reserved
 */
package me.koterwong.databinding.adapter.recyclerview;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

/**
 * Created by Koterwong on 2016/11/27 09:06
 */
public class BindingViewHolder<D extends ViewDataBinding> extends RecyclerView.ViewHolder {
  private D mBinding;

  public BindingViewHolder(D binding) {
    super(binding.getRoot());
    this.mBinding = binding;
  }

  public D getBinding() {
    return mBinding;
  }
}
