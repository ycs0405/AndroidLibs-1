/*
 * ImageConfig     2016/9/23-09-23
 * Copyright (c) 2016 KOTERWONG All right reserved
 */
package me.koterwong.widget.imageloader;

import android.widget.ImageView;

/**
 * Created by Koterwong on 2016/9/23 10:09
 */
public class ImageConfig {
  protected String mUrl;
  protected ImageView mImageView;
  protected int mPlaceHolder;
  protected int mErrorPic;

  public String getUrl() {
    return mUrl;
  }

  public ImageView getImageView() {
    return mImageView;
  }

  public int getPlaceholder() {
    return mPlaceHolder;
  }

  public int getErrorPic() {
    return mErrorPic;
  }

  public ImageConfig(Builder builder) {
    this.mUrl = builder.mUrl;
    this.mImageView = builder.mImageView;
    this.mPlaceHolder = builder.mPlaceHolder;
    this.mErrorPic = builder.mErrorPic;
  }

  public static class Builder {
    public String mUrl;
    public ImageView mImageView;
    public int mPlaceHolder;
    public int mErrorPic;

    public Builder url(String url) {
      this.mUrl = url;
      return this;
    }

    public Builder into(ImageView imageView) {
      this.mImageView = imageView;
      return this;
    }

    public Builder placeHolder(int placeHolder) {
      this.mPlaceHolder = placeHolder;
      return this;
    }

    public Builder errorPic(int errorPic) {
      this.mErrorPic = errorPic;
      return this;
    }

    public ImageConfig build() {
      if (mUrl == null) throw new IllegalStateException("url is required");
      if (mImageView == null) throw new IllegalStateException("imageView is required");
      return new ImageConfig(this);
    }
  }
}
