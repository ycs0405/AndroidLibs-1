/*
 * PasswordTransforEditView     2017/2/11 15:15
 * Copyright (c) 2017 Koterwong All right reserved
 */
package me.koterwong.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import me.koterwong.R;
import me.koterwong.utils.CommonUtil;

/**
 * Created by Koterwong on 2017/2/11 15:15
 */
public class PasswordTransforEditView extends RelativeLayout {
  private Context mContext;
  private EditText mEditText;
  private ImageButton mButton;
  private boolean isShowPassword = false;

  public PasswordTransforEditView(Context context, AttributeSet attrs) {
    super(context, attrs);
    mContext = context;
    init();
  }

  private void init() {
    View view = LayoutInflater.from(mContext).inflate(R.layout.layout_password_transfor, null);
    addView(view, new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

    mEditText = (EditText) view.findViewById(R.id.et_password);
    mButton = (ImageButton) view.findViewById(R.id.iv_show_password);

    mButton.setOnClickListener(new OnClickListener() {
      @Override public void onClick(View arg0) {
        isShowPassword = !isShowPassword;
        // 显示明文
        if (isShowPassword) {
          mEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
          mButton.setImageResource(R.drawable.dismiss_password);
        } else {
          mEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
          mButton.setImageResource(R.drawable.show_password);
        }
        CommonUtil.setEditViewSelectionEnd(mEditText);
      }
    });
  }

  public void setTypeface(Typeface tf) {
    mEditText.setTypeface(tf);
  }

  public void addTextChangedListener(TextWatcher watcher) {
    mEditText.addTextChangedListener(watcher);
  }

  public Editable getText() {
    return mEditText.getText();
  }

  public void setText(CharSequence text) {
    mEditText.setText(text);
  }


  public void setHint(CharSequence hint) {
    mEditText.setHint(hint);
  }

  public void setHint(int resid) {
    mEditText.setHint(resid);
  }
}
