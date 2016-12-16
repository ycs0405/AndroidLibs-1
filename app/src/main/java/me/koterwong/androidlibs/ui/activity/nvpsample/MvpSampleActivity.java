package me.koterwong.androidlibs.ui.activity.nvpsample;

import android.widget.TextView;

import me.koterwong.androidlibs.R;
import me.koterwong.base.BaseAppCompatActivity;
import me.koterwong.di.component.AppComponent;

public class MvpSampleActivity extends BaseAppCompatActivity<MvpSamplePresenter> implements MvpSampleContract.View {
  TextView mTextView;

  @Override protected int getLayoutId() {
    return R.layout.activity_mvp_sample;
  }

  @Override protected void injectComponent(AppComponent appComponent) {
    DaggerMvpSampleComponent.builder()
        .appComponent(appComponent)
        .mvpSampleModule(new MvpSampleModule(this))
        .build()
        .inject(this);
  }

  @Override protected void initField() {
    mTextView = (TextView) findViewById(R.id.text);
  }

  @Override protected void initData() {
    mPresenter.loadData();
  }

  @Override public void showText(String s) {
    mTextView.setText(s);
  }

  @Override public void showProgressDialog() {

  }

  @Override public void dismissProgressDialog() {

  }

  @Override public void showToast() {

  }

  @Override public void showSnakeBar() {

  }
}
