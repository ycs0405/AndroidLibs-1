/*
 * ProgressDislag     2016/10/29 18:16
 * Copyright (c) 2016 Koterwong All right reserved
 */
package me.koterwong.widget.dialog;


import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.widget.TextView;

import me.koterwong.R;

/**
 * Created by Koterwong on 2016/10/29 18:16
 *
 * loading tip dialog,you can use it just like Dialog.
 */
public class ProgressDialog extends Dialog {
  private TextView msgTip;

  public ProgressDialog(Context context) {
    this(context, "");
  }

  public ProgressDialog(Context context, String msg) {
    this(context, R.style.progress_dialog, msg);
  }

  public ProgressDialog(Context context, int themeResId, String msg) {
    super(context, themeResId);
    this.setContentView(R.layout.dialog_progress);
    this.getWindow().getAttributes().gravity = Gravity.CENTER;
    this.setCanceledOnTouchOutside(false);
    this.msgTip = (TextView) this.findViewById(R.id.tipTextView);
    if (msgTip != null) {
      msgTip.setText(msg);
    }
  }

  public void setMessage(String message) {
    msgTip.setText(message);
  }


  @Override public void onWindowFocusChanged(boolean hasFocus) {
    if (!hasFocus) {
      dismiss();
    }
  }
}
