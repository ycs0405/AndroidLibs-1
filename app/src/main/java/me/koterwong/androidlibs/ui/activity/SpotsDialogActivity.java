package me.koterwong.androidlibs.ui.activity;

import android.view.View;

import me.koterwong.androidlibs.R;
import me.koterwong.androidlibs.base.BaseActivity;
import me.koterwong.di.component.AppComponent;
import me.koterwong.widget.dialog.spotsdialog.SpotsDialog;

public class SpotsDialogActivity extends BaseActivity implements View.OnClickListener {

  @Override protected int getLayoutId() {
    return R.layout.activity_spots_dialog;
  }

  @Override protected void injectComponent(AppComponent appComponent) {
    findViewById(R.id.showdialog).setOnClickListener(this);
    findViewById(R.id.withmessage).setOnClickListener(this);
    findViewById(R.id.messageandstyle).setOnClickListener(this);
    findViewById(R.id.withstyle).setOnClickListener(this);
  }

  @Override public void onClick(View view) {
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
