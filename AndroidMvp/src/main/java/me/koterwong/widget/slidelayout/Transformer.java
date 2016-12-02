/*
 * Transformer     2016/9/29-09-29
 * Copyright (c) 2016 KOTERWONG All right reserved
 */
package me.koterwong.widget.slidelayout;

import android.support.v4.view.ViewPager;

import me.koterwong.widget.slidelayout.transformers.AccordionTransformer;
import me.koterwong.widget.slidelayout.transformers.BackgroundToForegroundTransformer;
import me.koterwong.widget.slidelayout.transformers.CubeInTransformer;
import me.koterwong.widget.slidelayout.transformers.CubeOutTransformer;
import me.koterwong.widget.slidelayout.transformers.DefaultTransformer;
import me.koterwong.widget.slidelayout.transformers.DepthPageTransformer;
import me.koterwong.widget.slidelayout.transformers.FlipHorizontalTransformer;
import me.koterwong.widget.slidelayout.transformers.FlipVerticalTransformer;
import me.koterwong.widget.slidelayout.transformers.ForegroundToBackgroundTransformer;
import me.koterwong.widget.slidelayout.transformers.RotateDownTransformer;
import me.koterwong.widget.slidelayout.transformers.RotateUpTransformer;
import me.koterwong.widget.slidelayout.transformers.ScaleInOutTransformer;
import me.koterwong.widget.slidelayout.transformers.StackTransformer;
import me.koterwong.widget.slidelayout.transformers.TabletTransformer;
import me.koterwong.widget.slidelayout.transformers.ZoomInTransformer;
import me.koterwong.widget.slidelayout.transformers.ZoomOutSlideTransformer;
import me.koterwong.widget.slidelayout.transformers.ZoomOutTranformer;

/**
 * Created by Koterwong on 2016/9/29 16:55
 */
public class Transformer {
  public static Class<? extends ViewPager.PageTransformer> Default = DefaultTransformer.class;
  public static Class<? extends ViewPager.PageTransformer> Accordion = AccordionTransformer.class;
  public static Class<? extends ViewPager.PageTransformer> BackgroundToForeground = BackgroundToForegroundTransformer.class;
  public static Class<? extends ViewPager.PageTransformer> ForegroundToBackground = ForegroundToBackgroundTransformer.class;
  public static Class<? extends ViewPager.PageTransformer> CubeIn = CubeInTransformer.class;
  public static Class<? extends ViewPager.PageTransformer> CubeOut = CubeOutTransformer.class;
  public static Class<? extends ViewPager.PageTransformer> DepthPage = DepthPageTransformer.class;
  public static Class<? extends ViewPager.PageTransformer> FlipHorizontal = FlipHorizontalTransformer.class;
  public static Class<? extends ViewPager.PageTransformer> FlipVertical = FlipVerticalTransformer.class;
  public static Class<? extends ViewPager.PageTransformer> RotateDown = RotateDownTransformer.class;
  public static Class<? extends ViewPager.PageTransformer> RotateUp = RotateUpTransformer.class;
  public static Class<? extends ViewPager.PageTransformer> ScaleInOut = ScaleInOutTransformer.class;
  public static Class<? extends ViewPager.PageTransformer> Stack = StackTransformer.class;
  public static Class<? extends ViewPager.PageTransformer> Tablet = TabletTransformer.class;
  public static Class<? extends ViewPager.PageTransformer> ZoomIn = ZoomInTransformer.class;
  public static Class<? extends ViewPager.PageTransformer> ZoomOut = ZoomOutTranformer.class;
  public static Class<? extends ViewPager.PageTransformer> ZoomOutSlide = ZoomOutSlideTransformer.class;
}
