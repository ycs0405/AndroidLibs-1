
package me.koterwong.widget;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextPaint;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import me.koterwong.R;

import static android.R.attr.maxWidth;


public class TipsToast extends Toast {

  public TipsToast(Context context) {
    super(context);
  }

  public static TipsToast makeText(Context context, CharSequence text, int duration) {
    TipsToast result = new TipsToast(context);

    LayoutInflater inflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View v = inflate.inflate(R.layout.toast_view_tips, null);
    TextView tv = (TextView) v.findViewById(R.id.tips_msg);
    int maxWidth = (int) (180 * context.getResources().getDisplayMetrics().density);
    autoSetTextViewGravity(tv, text.toString(), maxWidth);
    tv.setText(text);

    result.setView(v);
    // setGravity方法用于设置位置，此处为垂直居中
    result.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
    result.setDuration(duration);

    return result;
  }

  public static TipsToast makeText(Context context, int resId, int duration)
      throws Resources.NotFoundException {
    return makeText(context, context.getResources().getText(resId), duration);
  }

  public void setIcon(int iconResId) {
    if (getView() == null) {
      throw new RuntimeException("This Toast was not created with Toast.makeText()");
    }
    ImageView iv = (ImageView) getView().findViewById(R.id.tips_icon);
    if (iv == null) {
      throw new RuntimeException("This Toast was not created with Toast.makeText()");
    }
    iv.setImageResource(iconResId);
  }

  @Override
  public void setText(CharSequence s) {
    if (getView() == null) {
      throw new RuntimeException("This Toast was not created with Toast.makeText()");
    }
    TextView tv = (TextView) getView().findViewById(R.id.tips_msg);
    autoSetTextViewGravity(tv, s.toString(), maxWidth);
    if (tv == null) {
      throw new RuntimeException("This Toast was not created with Toast.makeText()");
    }
    tv.setText(s);
  }

  private static void autoSetTextViewGravity(TextView textView, String content, int maxWidth) {
    TextPaint paint = textView.getPaint();
    // 得到使用该paint写上text的时候,像素为多少
    float textLength = paint.measureText(content);
    if (textLength > maxWidth) {
      textView.setGravity(Gravity.LEFT);
    } else {
      textView.setGravity(Gravity.CENTER);
    }
  }
}
