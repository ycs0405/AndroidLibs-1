/*
 * MyFragmentPagerAdapter     2017/2/10 08:41
 * Copyright (c) 2017 Koterwong All right reserved
 */
package me.koterwong.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Koterwong on 2017/2/10 08:41
 */
public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
  private List<Fragment> mFragment;
  private List<String> mTitleList;

  public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> mFragment) {
    super(fm);
    this.mFragment = mFragment;
  }

  public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> mFragment, List<String> mTitleList) {
    super(fm);
    this.mFragment = mFragment;
    this.mTitleList = mTitleList;
  }

  @Override
  public Fragment getItem(int position) {
    return (Fragment) mFragment.get(position);
  }

  @Override
  public int getCount() {
    return mFragment.size();
  }

  @Override
  public void destroyItem(ViewGroup container, int position, Object object) {
    super.destroyItem(container, position, object);
  }

  @Override
  public CharSequence getPageTitle(int position) {
    if (mTitleList != null) {
      return mTitleList.get(position);
    } else {
      return "";
    }
  }

  public void addFragmentList(List<Fragment> fragment) {
    this.mFragment.clear();
    this.mFragment = null;
    this.mFragment = fragment;
    notifyDataSetChanged();
  }
}

