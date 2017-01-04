/*
 * BottomLayoutManager     2017/1/4 14:51
 * Copyright (c) 2017 Koterwong All right reserved
 */
package me.koterwong.widget.bottommanager;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.SparseArray;
import android.view.View;
import android.widget.LinearLayout;

import me.koterwong.R;

/**
 * Created by Koterwong on 2017/1/4 14:51
 */
public class BottomLayoutManager {
  private int mSelectColor;
  private int mUnSelectColor;

  private Context mContext;
  private FragmentManager mFragmentManager;

  private SparseArray<Fragment> mFragments;
  private SparseArray<HomeTabItem> mHomeTabItems;

  private LinearLayout mBottomLayout;  //底部布局
  private int mContainerViewId;        //添加Fragment的布局id
  private int mSelectPosition;         //当前选中的位置
  private int mLastSelectPosition;     //上一次选中的位置
  private TabItemClickListener mTabItemClickListener;

  /**
   * @param context      context对象
   * @param bottomLayout 布局文件中的linearLayout对象
   */
  public BottomLayoutManager(Context context, LinearLayout bottomLayout) {
    mFragments = new SparseArray<>();
    mHomeTabItems = new SparseArray<>();
    mContext = context;
    mBottomLayout = bottomLayout;
    mContainerViewId = mBottomLayout.getId();
    mFragmentManager = ((FragmentActivity) (context)).getSupportFragmentManager();
    mSelectColor = ContextCompat.getColor(context, R.color.text_red_color);
    mUnSelectColor = ContextCompat.getColor(context, R.color.text_light_grey);
    mTabItemClickListener = new TabItemClickListener();
  }

  /**
   * 添加具体的Fragment
   *
   * @param fragment 具体fragment
   */
  public BottomLayoutManager addFragment(Fragment fragment) {
    mFragments.put(mFragments.size(), fragment);
    return this;
  }

  /**
   * 添加具体的条目
   *
   * @param selectImage   选中的图片
   * @param unSelectImage 未选中的图片
   * @param title         标题
   */
  public BottomLayoutManager addTabItem(int selectImage, int unSelectImage, String title) {
    HomeTabItem homeTabItem = new HomeTabItem(mContext);
    homeTabItem.setSelectedImageRes(selectImage)
        .setUnselectedImageRes(unSelectImage)
        .setTitle(title);
    homeTabItem.setTag(mHomeTabItems.size());
    homeTabItem.setOnClickListener(mTabItemClickListener);
    mBottomLayout.addView(homeTabItem);
    mBottomLayout.invalidate();
    mHomeTabItems.put(mHomeTabItems.size(), homeTabItem);
    return this;
  }

  /**
   * 设置当前选中的条目
   *
   * @param position position
   */
  public BottomLayoutManager setCurrentItem(int position) {
    if (position < 0 || position > mFragments.size() || position > mHomeTabItems.size()) {
      throw new IllegalStateException("bottomLayoutManager : the select position is wrong");
    }

    HomeTabItem homeTabItem = mHomeTabItems.get(position);
    setUpItemChange(homeTabItem, position);
    return this;
  }

  private void setUpItemChange(HomeTabItem homeTabItem, int position) {
    resetTab(homeTabItem);

    mLastSelectPosition = mSelectPosition;
    Fragment fragment = mFragments.get(position);
    FragmentTransaction ft = mFragmentManager.beginTransaction();
    getCurrentFragment().onPause();
    if (fragment.isAdded()) {
      fragment.onResume();
    } else {
      ft.add(mContainerViewId, fragment);
    }

    showCurrentItem(position);
    ft.commitAllowingStateLoss();
  }

  private void showCurrentItem(int position) {
    for (int i = 0; i < mFragments.size(); i++) {
      Fragment fragment = mFragments.get(i);
      FragmentTransaction ft = mFragmentManager.beginTransaction();

      if (position == i) {
        ft.show(fragment);
      } else {
        ft.hide(fragment);
      }

      ft.commitAllowingStateLoss();
    }
    mSelectPosition = position;
  }

  private Fragment getCurrentFragment() {
    return mFragments.get(mLastSelectPosition);
  }

  private void resetTab(HomeTabItem homeTabItem) {
    for (int i = 0; i < mHomeTabItems.size(); i++) {
      HomeTabItem tabItem = mHomeTabItems.get(i);
      tabItem.getSelectedImage().setVisibility(View.GONE);
      tabItem.getUnselectedImage().setVisibility(View.VISIBLE);
      setTextColor(tabItem, false);
    }

    homeTabItem.getSelectedImage().setVisibility(View.VISIBLE);
    homeTabItem.getUnselectedImage().setVisibility(View.GONE);
    setTextColor(homeTabItem, true);
  }

  private void setTextColor(HomeTabItem tabItem, boolean isSelect) {
    tabItem.getTitleTextView().setTextColor(isSelect ? mSelectColor : mUnSelectColor);
  }

  private  class TabItemClickListener implements View.OnClickListener{

    @Override public void onClick(View view) {
      int position = (int) view.getTag();
      if (position == mSelectPosition) {
        //点击位置是当前的item
        return;
      }
      setCurrentItem(position);
    }
  }
}
