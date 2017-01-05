package me.koterwong.androidlibs.ui.activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import me.koterwong.androidlibs.R;
import me.koterwong.widget.TitleBar;

public class TitlebarActivity extends AppCompatActivity {
  private TitleBar mTitleBar1;
  private TitleBar mTitleBar2;
  private TitleBar mTitleBar3;
  private TitleBar mTitleBar4;
  private TitleBar mTitleBar5;
  private TitleBar mTitleBar6;
  private TitleBar mTitleBar7;
  private TitleBar mTitleBar8;
  private TitleBar mTitleBar9;
  private TitleBar mTitleBar10;
  ImageView imageView;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_titlebar);
    mTitleBar1 = (TitleBar) findViewById(R.id.tb1);
    mTitleBar2 = (TitleBar) findViewById(R.id.tb2);
    mTitleBar3 = (TitleBar) findViewById(R.id.tb3);
    mTitleBar4 = (TitleBar) findViewById(R.id.tb4);
    mTitleBar5 = (TitleBar) findViewById(R.id.tb5);
    mTitleBar6 = (TitleBar) findViewById(R.id.tb6);
    mTitleBar7 = (TitleBar) findViewById(R.id.tb7);
    mTitleBar8 = (TitleBar) findViewById(R.id.tb8);
    mTitleBar9 = (TitleBar) findViewById(R.id.tb9);
    mTitleBar10 = (TitleBar) findViewById(R.id.tb10);

    mTitleBar1.setTitle("只有一个标题");
    mTitleBar1.setTitleSize(14);
    mTitleBar1.setTitleColor(ContextCompat.getColor(this,R.color.colorAccent));

    mTitleBar2.setLeftText("左标题");
    mTitleBar2.setLeftImageResource(R.drawable.umeng_socialize_title_back_bt);
    mTitleBar2.setLeftTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
    mTitleBar2.setLeftClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        Toast.makeText(TitlebarActivity.this, "点击了左边的图片", Toast.LENGTH_SHORT).show();
      }
    });
    mTitleBar2.setTitle("只有一个标题");
    mTitleBar2.setTitleSize(14);
    mTitleBar2.setTitleColor(ContextCompat.getColor(this,R.color.colorAccent));


    mTitleBar3.setActionTextColor(ContextCompat.getColor(this, R.color.red));
    imageView  = (ImageView) mTitleBar3.addAction(new TitleBar.ImageAction(R.drawable.icon32_photo) {
      @Override public void performAction(View view) {
        imageView.setImageResource(R.drawable.icon32_cameta);
      }
    });
    mTitleBar3.addAction(new TitleBar.TextAction("haha") {
      @Override public void performAction(View view) {
        Toast.makeText(TitlebarActivity.this, "haha", Toast.LENGTH_SHORT).show();
      }
    });


    mTitleBar4.setActionTextColor(ContextCompat.getColor(this, R.color.red));
    imageView  = (ImageView) mTitleBar4.addAction(new TitleBar.ImageAction(R.drawable.icon32_photo) {
      @Override public void performAction(View view) {
        imageView.setImageResource(R.drawable.icon32_cameta);
      }
    });

    mTitleBar5.addAction(new TitleBar.TextAction("haha") {
      @Override public void performAction(View view) {
        Toast.makeText(TitlebarActivity.this, "haha", Toast.LENGTH_SHORT).show();
      }
    });
  }
}
