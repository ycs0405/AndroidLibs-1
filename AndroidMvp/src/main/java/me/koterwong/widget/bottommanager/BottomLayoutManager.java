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
import android.util.SparseArray;
import android.view.View;
import android.widget.LinearLayout;

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
  private OnItemClickListener mOnItemClickListener;

  /**
   * @param context         context对象
   * @param bottomLayout    布局文件中的linearLayout对象
   * @param containerViewId 要添加Fragment的layout
   * @param selectColor     tab选中时字体的颜色
   * @param unSelectColor   tab未选中时字体的颜色
   */
  public BottomLayoutManager(Context context, LinearLayout bottomLayout,int containerViewId, int selectColor, int unSelectColor) {
    mFragments = new SparseArray<>();
    mHomeTabItems = new SparseArray<>();
    mContext = context;
    mBottomLayout = bottomLayout;
    mContainerViewId = containerViewId;
    mFragmentManager = ((FragmentActivity) (context)).getSupportFragmentManager();
    mSelectColor = selectColor;
    mUnSelectColor = unSelectColor;
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
        .setUnSelectedImageRes(unSelectImage)
        .setTitleText(title);
    homeTabItem.setTag(mHomeTabItems.size());
    homeTabItem.setOnClickListener(mTabItemClickListener);
    mBottomLayout.addView(homeTabItem, mHomeTabItems.size());
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


  /**
   * 设置底部标签图片的尺寸
   *
   * @param itemSize dpValue
   */
  public BottomLayoutManager setTabImageSize(int itemSize) {
    for (int i = 0; i < mHomeTabItems.size(); i++) {
      HomeTabItem item = mHomeTabItems.get(i);
      item.setSelectImageSize(itemSize);
    }
    return this;
  }

  /**
   * 设置条目的点击事件
   *
   * @param onItemClickListener onItemClickListener
   */
  public BottomLayoutManager setOnItemClickListener(OnItemClickListener onItemClickListener) {
    this.mOnItemClickListener = onItemClickListener;
    return this;
  }

  public SparseArray<Fragment> getFragments() {
    return mFragments;
  }

  private void setUpItemChange(HomeTabItem homeTabItem, int position) {
    resetTab();

    homeTabItem.getSelectedImage().setVisibility(View.VISIBLE);
    homeTabItem.getUnselectedImage().setVisibility(View.GONE);
    setTextColor(homeTabItem, true);

    mLastSelectPosition = mSelectPosition;
    Fragment fragment = mFragments.get(position);
    FragmentTransaction ft = mFragmentManager.beginTransaction();
    getCurrentFragment().onPause();

    if (fragment.isAdded()) {
      fragment.onResume();
    } else {
      ft.add(mContainerViewId, fragment);
    }

    showCurrentItem(ft, position);
    ft.commitAllowingStateLoss();
  }

  private void showCurrentItem(FragmentTransaction ft, int position) {
    for (int i = 0; i < mFragments.size(); i++) {
      Fragment fragment = mFragments.get(i);
      if (position == i) {
        ft.show(fragment);
      } else {
        ft.hide(fragment);
      }
    }
    mSelectPosition = position;
  }

  private Fragment getCurrentFragment() {
    return mFragments.get(mLastSelectPosition);
  }

  private void resetTab() {
    for (int i = 0; i < mHomeTabItems.size(); i++) {
      HomeTabItem tabItem = mHomeTabItems.get(i);
      tabItem.getSelectedImage().setVisibility(View.GONE);
      tabItem.getUnselectedImage().setVisibility(View.VISIBLE);
      setTextColor(tabItem, false);
    }
  }

  private void setTextColor(HomeTabItem tabItem, boolean isSelect) {
    tabItem.getTitleTextView().setTextColor(isSelect ? mSelectColor : mUnSelectColor);
  }

  public interface OnItemClickListener {
    void onItemClick(int position);
  }

  private class TabItemClickListener implements View.OnClickListener {

    @Override public void onClick(View view) {
      int position = (int) view.getTag();
      if (position == mSelectPosition) {
        return;//点击位置是当前的item
      }

      if (mOnItemClickListener != null) {
        mOnItemClickListener.onItemClick(position);
      }

      setCurrentItem(position);
    }
  }
}
