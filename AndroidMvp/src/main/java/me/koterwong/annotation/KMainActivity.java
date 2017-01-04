/*
 * KMainActivity     2016/12/11 09:35
 * Copyright (c) 2016 Koterwong All right reserved
 */
package me.koterwong.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Koterwong on 2016/12/11 09:35
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface KMainActivity {

  class Helper {
    public static boolean isMainActivity(Object object) {
      if (object == null) {
        return false;
      }

      if (object.getClass().isAnnotationPresent(KMainActivity.class)) {
        return true;
      }

      return false;
    }
  }
}
