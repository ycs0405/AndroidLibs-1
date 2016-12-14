package me.koterwong.androidlibs.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import me.koterwong.androidlibs.R;
import me.koterwong.widget.textview.AutoLinkStyleTextView;
import me.koterwong.widget.textview.ExpandTextView;

public class TextViewActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_text_view);

    AutoLinkStyleTextView autoLinkStyleTextView = (AutoLinkStyleTextView) findViewById(R.id.auto_tv);
    autoLinkStyleTextView.setOnClickCallBack(new AutoLinkStyleTextView.ClickCallBack() {
      @Override public void onClick(int position) {
        if (position == 0) {
          Toast.makeText(TextViewActivity.this, "0" + "特大新闻", Toast.LENGTH_SHORT).show();
        } else if (position == 1) {
          Toast.makeText(TextViewActivity.this, "1" + "泰迪", Toast.LENGTH_SHORT).show();
        }
      }
    });

    ExpandTextView ex = (ExpandTextView) findViewById(R.id.expand_text_view);
    ex.setOnReadMoreListener(new ExpandTextView.OnReadMoreClickListener() {
      @Override public void onExpand() {
        Toast.makeText(TextViewActivity.this, "展开", Toast.LENGTH_SHORT).show();
      }

      @Override public void onFold() {
        Toast.makeText(TextViewActivity.this, "收起", Toast.LENGTH_SHORT).show();
      }
    });
  }
}
