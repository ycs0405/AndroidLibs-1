/*
 * Check     2016/10/3-10-03
 * Copyright (c) 2016 KOTERWONG All right reserved
 */
package me.koterwong.utils;

import java.util.Collection;
import java.util.Map;

/**
 * check content is null.
 *
 * Created by Koterwong on 2016/10/3 12:08
 */
public class Check {
  public static boolean isEmpty(CharSequence str) {
    return isNull(str) || str.length() == 0;
  }

  public static boolean isEmpty(Object[] os) {
    return isNull(os) || os.length == 0;
  }

  public static boolean isEmpty(Collection<?> l) {
    return isNull(l) || l.isEmpty();
  }

  public static boolean isEmpty(Map<?, ?> m) {
    return isNull(m) || m.isEmpty();
  }

  public static boolean isNull(Object o) {
    return o == null;
  }
}
