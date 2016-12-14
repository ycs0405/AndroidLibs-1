/*
 * BottomDialogActivity     2016/10/30 13:02
 * Copyright (c) 2016 Koterwong All right reserved
 */
package me.koterwong.androidlibs.ui.activity;

import android.view.View;
import android.view.inputmethod.InputMethodManager;

import me.koterwong.androidlibs.R;
import me.koterwong.androidlibs.base.BaseActivity;
import me.koterwong.di.component.AppComponent;
import me.koterwong.widget.dialog.ProgressDialog;
import me.koterwong.widget.dialog.bottomlialog.BottomDialog;

/**
 * Created by Koterwong on 2016/10/30 13:02
 */
public class BottomDialogActivity extends BaseActivity {

  @Override protected int getLayoutId() {
    return R.layout.activity_bottom_dialog;
  }

  @Override protected void injectComponent(AppComponent appComponent) {

  }

  public void showDialog(View view) {
    BottomDialog dialog = BottomDialog.create(getSupportFragmentManager())
        .setLayoutRes(R.layout.dialog_share_layout)
        .setViewListener(new BottomDialog.ViewListener() {
          @Override public void bindView(View v) {

          }
        })
        .setDimAmount(0.2f);
    dialog.show();
  }

  public void showDialog1(final View view) {
    BottomDialog dialog = BottomDialog.create(getSupportFragmentManager())
        .setCancelOutside(false)
        .setLayoutRes(R.layout.dialog_edit_text)
        .setDimAmount(0.5f)
        .setViewListener(new BottomDialog.ViewListener() {
          @Override public void bindView(View v) {
            final InputMethodManager i = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            view.post(new Runnable() {
              @Override public void run() {
                i.showSoftInput(view, InputMethodManager.RESULT_UNCHANGED_SHOWN);
              }
            });
          }
        });
    dialog.show();
  }

  public void showPgDialog(View view) {
    ProgressDialog progressDialog = new ProgressDialog(this, getString(R.string.loading_tip));
    progressDialog.show();
  }
}
