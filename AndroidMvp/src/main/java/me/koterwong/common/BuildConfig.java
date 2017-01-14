/*
 * Config     2016/9/20-09-20
 * Copyright (c) 2016 KOTERWONG All right reserved
 */
package me.koterwong.common;

/**
 * Created by Koterwong on 2016/9/20 11:44
 */
public class BuildConfig {
  /** 控制Debug开关 Release版本改为false */
  public static final boolean LOG_DEBUG = true;
  /** 内存泄漏检查工具开关 */
  public static final boolean USE_CANARY = false;
  /** 保存临时文件的sd卡目录 */
  public static final String TEMP_FILE_NAME = "log";
}
