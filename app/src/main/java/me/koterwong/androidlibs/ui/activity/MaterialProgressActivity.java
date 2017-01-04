package me.koterwong.androidlibs.ui.activity;

import me.koterwong.androidlibs.R;
import me.koterwong.base.BaseAppCompatActivity;
import me.koterwong.di.component.AppComponent;
import me.koterwong.widget.MaterialProgressView;

public class MaterialProgressActivity extends BaseAppCompatActivity {
  MaterialProgressView mProgressView;

  @Override protected int getLayoutId() {
    return R.layout.activity_material_progress;
  }

  @Override protected void injectComponent(AppComponent appComponent) {
    mProgressView = (MaterialProgressView) findViewById(R.id.mp_view);
  }
}
