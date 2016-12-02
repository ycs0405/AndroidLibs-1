/*
 * LvMultiAdaoter     2016/9/29-09-29
 * Copyright (c) 2016 KOTERWONG All right reserved
 */
package me.koterwong.androidlibs.ui.adapter;

import android.content.Context;

import java.util.List;

import me.koterwong.androidlibs.R;
import me.koterwong.widget.listview.MultiItemTypeAdapter;
import me.koterwong.widget.listview.ViewHolder;
import me.koterwong.widget.listview.base.ItemViewDelegate;


/**
 * Created by Koterwong on 2016/9/29 10:43
 */
public class LvMultiAdaoter extends MultiItemTypeAdapter<String> {
  public LvMultiAdaoter(Context context, List<String> datas) {
    super(context, datas);

    addItemViewDelegate(new ItemViewDelegate<String>() {

      @Override public int getItemViewLayoutId() {
        return R.layout.item_lv_lege;
      }

      @Override public boolean isForViewType(String item, int position) {
        return Integer.valueOf(item) % 2 == 0;
      }

      @Override public void convert(ViewHolder holder, String s, int position) {
        holder.setText(R.id.text1, s);
      }
    });

    addItemViewDelegate(new ItemViewDelegate<String>() {
      @Override public int getItemViewLayoutId() {
        return R.layout.item_lv_right;
      }

      @Override public boolean isForViewType(String item, int position) {
        return Integer.valueOf(item) % 2 == 1;
      }

      @Override public void convert(ViewHolder holder, String s, int position) {
        holder.setText(R.id.text2, s);
      }
    });
  }
}
