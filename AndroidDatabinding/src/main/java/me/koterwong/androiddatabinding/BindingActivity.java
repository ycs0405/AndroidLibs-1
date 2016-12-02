/*
 * MainActivity     2016/11/22 18:37
 * Copyright (c) 2016 Koterwong All right reserved
 */
package me.koterwong.androiddatabinding;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Koterwong on 2016/11/22 18:37
 */
public class BindingActivity extends AppCompatActivity {

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_binding);
  }
}
