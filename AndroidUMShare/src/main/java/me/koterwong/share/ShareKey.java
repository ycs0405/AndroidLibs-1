/*
 * ShareAppKey     2016/9/24-09-24
 * Copyright (c) 2016 KOTERWONG All right reserved
 */
package me.koterwong.share;

/**
 * Created by Koterwong on 2016/9/24 16:34
 */
public class ShareKey {
  public String mWxKey;
  public String mWxSecret;
  /** App id */
  public String mQQKey;
  /** App key */
  public String mQQSecret;
  public String mSinaKey;
  public String mSinaSecret;

  private ShareKey(Builder builder) {
    this.mWxKey = builder.mWxKey;
    this.mWxSecret = builder.mWxSecret;
    this.mQQKey = builder.mQQKey;
    this.mQQSecret = builder.mQQSecret;
    this.mSinaKey = builder.mSinaKey;
    this.mSinaSecret = builder.mSinaSecret;
  }

  public static class Builder {
    private String mWxKey;
    private String mWxSecret;
    private String mQQKey;
    private String mQQSecret;
    private String mSinaKey;
    private String mSinaSecret;

    public Builder wxKey(String wxKey) {
      this.mWxKey = wxKey;
      return this;
    }

    public Builder wxSecret(String wxSecret) {
      this.mWxSecret = wxSecret;
      return this;
    }

    public Builder qqKey(String QQKey) {
      this.mQQKey = QQKey;
      return this;
    }

    public Builder qqSecret(String QQSecret) {
      this.mQQSecret = QQSecret;
      return this;
    }

    public Builder sinaKey(String sinaKey) {
      this.mSinaKey = sinaKey;
      return this;
    }

    public Builder sinaSecret(String sinaSecret) {
      this.mSinaSecret = sinaSecret;
      return this;
    }

    public ShareKey build() {
      return new ShareKey(this);
    }
  }
}
