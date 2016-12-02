/*
 * SmoothListViewFooter     2016/9/28-09-28
 * Copyright (c) 2016 KOTERWONG All right reserved
 */
package widget.smoothlistview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import me.koterwong.androidlibs.R;

/**
 * Created by Koterwong on 2016/9/28 08:27
 */
public class SmoothListViewFooter extends LinearLayout {
  public final static int STATE_NORMAL = 0;
  public final static int STATE_READY = 1;
  public final static int STATE_LOADING = 2;

  private Context mContext;

  private View mContentView;
  private View mProgressBar;
  private TextView mHintTextView;

  public SmoothListViewFooter(Context context) {
    this(context, null);
  }

  public SmoothListViewFooter(Context context, AttributeSet attrs) {
    this(context, attrs, -1);
  }

  public SmoothListViewFooter(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    this.mContext = context;
    this.initView(mContext);
  }

  public void initView(Context ctx) {
    LinearLayout moreView = (LinearLayout) LayoutInflater.from(ctx).inflate(R.layout
        .smoothlistview_footer, null);
    addView(moreView);
    moreView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams
        .WRAP_CONTENT));

    mContentView = moreView.findViewById(R.id.smoothlistview_footer_content);
    mProgressBar = moreView.findViewById(R.id.smoothlistview_footer_progressbar);
    mHintTextView = (TextView) moreView.findViewById(R.id.smoothlistview_footer_hint_textview);
  }

  public void setState(int state) {
    mHintTextView.setVisibility(View.INVISIBLE);
    mProgressBar.setVisibility(View.INVISIBLE);
    if (state == STATE_READY) {
      mHintTextView.setVisibility(View.VISIBLE);
      mHintTextView.setText(R.string.smoothlistview_footer_hint_ready);
    } else if (state == STATE_LOADING) {
      mProgressBar.setVisibility(View.VISIBLE);
    } else if (state == STATE_NORMAL) {
      mHintTextView.setText(R.string.smoothlistview_footer_hint_normal);
      mHintTextView.setVisibility(View.VISIBLE);
    }
  }

  public void setBottomMargin(int height) {
    if (height < 0) height = 0;
    LayoutParams lp = (LayoutParams) mContentView.getLayoutParams();
    lp.bottomMargin = height;
    mContentView.setLayoutParams(lp);
  }

  public int getBottomMargin() {
    return ((LayoutParams) mContentView.getLayoutParams()).bottomMargin;
  }


  public void hideFooter() {
    LayoutParams lp = (LayoutParams) mContentView.getLayoutParams();
    lp.height = 0;
    mContentView.setLayoutParams(lp);
  }

  public void showFooter() {
    LayoutParams lp = (LayoutParams) mContentView.getLayoutParams();
    lp.height = LayoutParams.WRAP_CONTENT;
    mContentView.setLayoutParams(lp);
  }
}
