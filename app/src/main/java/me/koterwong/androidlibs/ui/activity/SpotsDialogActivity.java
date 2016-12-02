package me.koterwong.androidlibs.ui.activity;

import android.view.View;

import butterknife.OnClick;
import me.koterwong.androidlibs.R;
import me.koterwong.base.BaseAppCompatActivity;
import me.koterwong.widget.dialog.spotsdialog.SpotsDialog;

public class SpotsDialogActivity extends BaseAppCompatActivity {

  @Override protected int getLayoutId() {
    return R.layout.activity_spots_dialog;
  }

  @Override protected void injectComponent() {

  }

  @OnClick({R.id.showdialog, R.id.withmessage, R.id.withstyle, R.id.messageandstyle}) public void
  onClick(View view) {
    switch (view.getId()) {
      case R.id.showdialog:
        new SpotsDialog(this).show();
        break;
      case R.id.withmessage:
        new SpotsDialog(this, "whitmessage").show();
        break;
      case R.id.withstyle:
        break;
      case R.id.messageandstyle:
        break;
    }
  }
}
