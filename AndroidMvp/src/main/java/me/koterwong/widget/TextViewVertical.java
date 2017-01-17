/*
 * TextViewVertical     2017/1/13 14:04
 * Copyright (c) 2017 Koterwong All right reserved
 */
package me.koterwong.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Koterwong on 2017/1/13 14:04
 *
 * 垂直方向显示的TextView
 */
public class TextViewVertical extends TextView {
  public TextViewVertical(Context paramContext) {
    super(paramContext);
  }

  public TextViewVertical(Context paramContext, AttributeSet paramAttributeSet) {
    super(paramContext, paramAttributeSet);
  }

  public void setText(CharSequence paramCharSequence, TextView.BufferType paramBufferType) {
    if (("".equals(paramCharSequence)) || (paramCharSequence == null) || (paramCharSequence.length() == 0)) {
      return;
    }

    int j = paramCharSequence.length();
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < j; i++) {
      CharSequence charSequence = paramCharSequence.toString().subSequence(i, i + 1);
      if (i == j - 1) {
        sb.append(charSequence);
      }else{
        sb.append(charSequence + "\n");
      }
    }
    super.setText(sb, paramBufferType);
  }
}
