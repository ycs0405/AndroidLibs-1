package me.koterwong.androidlibs.ui.activity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import me.koterwong.androidlibs.R;
import me.koterwong.androidlibs.ui.activity.nvpsample.MvpSampleActivity;
import me.koterwong.base.BaseAppCompatActivity;
import me.koterwong.di.component.AppComponent;
import me.koterwong.widget.listview.CommonAdapter;
import me.koterwong.widget.listview.ViewHolder;


public class MainActivity extends BaseAppCompatActivity {
  private List<String> mDatas = new ArrayList<>();

  {
    mDatas.add("ListView");
    mDatas.add("RecyclerView");
    mDatas.add("SlideLayout");
    mDatas.add("MaterialProgress");
    mDatas.add("SpotsDialog");
    mDatas.add("BottomDialog");
    mDatas.add("DateEmptyView");
    mDatas.add("StateButton");
    mDatas.add("textView");
    mDatas.add("mvp");
    mDatas.add("titlebar");
  }

  ListView mListView;


  @Override protected int getLayoutId() {
    return R.layout.activity_main;
  }

  @Override protected void setStatusBar() {
//    StatusBarCompat.setStatusBarImmersive(this, StatusBarCompat.DEFAULT_STATUS_BRA_ALPHA);
//    StatusBarCompat.setStatusBarImmersive(this);
  }

  @Override protected void injectComponent(AppComponent appComponent) {
    mListView = (ListView) findViewById(R.id.list_item);
  }

  @Override protected void initData() {
    mListView.setAdapter(new CommonAdapter<String>(this, R.layout.item_list_main, mDatas) {
      @Override protected void convert(ViewHolder holder, String item, int position) {
        holder.setText(R.id.textView, item);
      }
    });

    mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position == 0) {
          getIntentHandler().intentToActivity(LVActivity.class);
        } else if (position == 1) {
          getIntentHandler().intentToActivity(RvActivity.class);
        } else if (position == 2) {
          getIntentHandler().intentToActivity(SlideActivity.class);
        } else if (position == 3) {
          getIntentHandler().intentToActivity(MaterialProgressActivity.class);
        } else if (position == 4) {
          getIntentHandler().intentToActivity(SpotsDialogActivity.class);
        } else if (position == 5) {
          getIntentHandler().intentToActivity(BottomDialogActivity.class);
        } else if (position == 6) {
          getIntentHandler().intentToActivity(DataEpActivity.class);
        } else if (position == 7) {
          getIntentHandler().intentToActivity(StateButtonActivity.class);
        } else if (position == 8) {
          getIntentHandler().intentToActivity(TextViewActivity.class);
        } else if (position == 9) {
          getIntentHandler().intentToActivity(MvpSampleActivity.class);
        } else if (position == 10) {
          getIntentHandler().intentToActivity(TitlebarActivity.class);
        }
      }
    });
  }
}
