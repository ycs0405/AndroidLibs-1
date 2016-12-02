/*
 * IApplication     2016/9/2-09-02
 * Copyright (c) 2016 KOTERWONG All right reserved
 */
package me.koterwong.base;

import java.util.LinkedList;

/**
 * Created by Koterwong on 2016/9/2
 */
public interface IApplication {

  void addActivity(BaseAppCompatActivity activity);

  void removeActivity(BaseAppCompatActivity activity);

  void killAll();

  <T> T getActivity(Class<?> clazz);

  LinkedList<BaseAppCompatActivity> getActivitys();

  BaseAppCompatActivity getCurActivity();
}
