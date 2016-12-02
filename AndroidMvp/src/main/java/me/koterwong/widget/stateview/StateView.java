/*
 * LoadingAndRetryLayout     2016/9/26-09-26
 * Copyright (c) 2016 KOTERWONG All right reserved
 */
package me.koterwong.widget.stateview;

import android.content.Context;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import me.koterwong.common.LogKw;


/**
 * Created by Koterwong on 2016/9/26 14:53
 */
public class StateView extends FrameLayout {
  private static final String TAG = StateView.class.getSimpleName();

  private View mContentView;
  private View mLoadingView;
  private View mRetryView;
  private View mEmptyView;
  private LayoutInflater mLayoutInflater;

  public StateView(Context context) {
    this(context, null);
  }

  public StateView(Context context, AttributeSet attrs) {
    this(context, attrs, -1);
  }

  public StateView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    mLayoutInflater = LayoutInflater.from(context);
  }

  public void showViewLoading() {
    if (isMainThread()) {
      showView(mLoadingView);
    } else {
      post(new Runnable() {
        @Override public void run() {
          showView(mLoadingView);
        }
      });
    }
  }

  public void showContent() {
    if (isMainThread()) {
      showView(mContentView);
    } else {
      post(new Runnable() {
        @Override public void run() {
          showView(mContentView);
        }
      });
    }
  }

  public void showViewEmpty() {
    if (isMainThread()) {
      showView(mEmptyView);
    } else {
      post(new Runnable() {
        @Override public void run() {
          showView(mEmptyView);
        }
      });
    }
  }

  public void showViewRetry() {
    if (isMainThread()) {
      showView(mRetryView);
    } else {
      post(new Runnable() {
        @Override public void run() {
          showView(mRetryView);
        }
      });
    }
  }

  private void showView(View view) {
    if (null == view) {
      LogKw.e(TAG, "NullPoint occured when show DataEmptyView...");
      return;
    }
    if (view == mLoadingView) {
      mLoadingView.setVisibility(View.VISIBLE);
      if (mRetryView != null)
        mRetryView.setVisibility(View.GONE);
      if (mContentView != null)
        mContentView.setVisibility(View.GONE);
      if (mEmptyView != null)
        mEmptyView.setVisibility(View.GONE);
    } else if (view == mRetryView) {
      mRetryView.setVisibility(View.VISIBLE);
      if (mLoadingView != null)
        mLoadingView.setVisibility(View.GONE);
      if (mContentView != null)
        mContentView.setVisibility(View.GONE);
      if (mEmptyView != null)
        mEmptyView.setVisibility(View.GONE);
    } else if (view == mContentView) {
      mContentView.setVisibility(View.VISIBLE);
      if (mLoadingView != null)
        mLoadingView.setVisibility(View.GONE);
      if (mRetryView != null)
        mRetryView.setVisibility(View.GONE);
      if (mEmptyView != null)
        mEmptyView.setVisibility(View.GONE);
    } else if (view == mEmptyView) {
      mEmptyView.setVisibility(View.VISIBLE);
      if (mLoadingView != null)
        mLoadingView.setVisibility(View.GONE);
      if (mRetryView != null)
        mRetryView.setVisibility(View.GONE);
      if (mContentView != null)
        mContentView.setVisibility(View.GONE);
    }
  }

  public View addLoadingView(int layoutId) {
    return addLoadingView(mLayoutInflater.inflate(layoutId, this, false));
  }

  public View addLoadingView(View view) {
    View loadingView = mLoadingView;
    if (loadingView != null) {
      LogKw.e(TAG, "you have already set a loading view and would be instead of this new one.");
    }
    removeView(loadingView);
    addView(view);
    mLoadingView = view;
    return mLoadingView;
  }

  public View addEmptyView(int layoutId) {
    return addEmptyView(mLayoutInflater.inflate(layoutId, this, false));
  }

  public View addEmptyView(View view) {
    View emptyView = mEmptyView;
    if (emptyView != null) {
      LogKw.e(TAG, "you have already set a empty view and would be instead of this new one.");
    }
    removeView(emptyView);
    addView(view);
    mEmptyView = view;
    return mEmptyView;
  }

  public View addRetryView(int layoutId) {
    return addRetryView(mLayoutInflater.inflate(layoutId, this, false));
  }

  public View addRetryView(View view) {
    View retryView = mRetryView;
    if (retryView != null) {
      LogKw.e(TAG, "you have already set a retry view and would be instead of this new one.");
    }
    removeView(retryView);
    addView(view);
    mRetryView = view;
    return mRetryView;
  }

  public View addContentView(View view) {
    View contentView = mContentView;
    if (contentView != null) {
      LogKw.e(TAG, "you have already set a retry view and would be instead of this new one.");
    }
    removeView(contentView);
    addView(view);
    mContentView = view;
    return mContentView;
  }

  private boolean isMainThread() {
    return Looper.myLooper() == Looper.getMainLooper();
  }

  public View getRetryView() {
    return mRetryView;
  }

  public View getLoadingView() {
    return mLoadingView;
  }

  public View getContentView() {
    return mContentView;
  }

  public View getEmptyView() {
    return mEmptyView;
  }
}
