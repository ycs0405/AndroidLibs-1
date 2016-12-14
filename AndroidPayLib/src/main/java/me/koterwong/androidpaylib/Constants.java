/*
 * PayConstants     2016/12/2 17:11
 * Copyright (c) 2016 Koterwong All right reserved
 */
package me.koterwong.androidpaylib;

/**
 * Created by Koterwong on 2016/12/2 17:11
 */
public interface Constants {
  interface AliPay {
    /** 支付宝AppID */
    String APPID = "";
    /** 商户私钥，pkcs8格式 */
    String RSA_PRIVATE = "";
    /** 支付宝回调后台的NotifyUrl */
    String NOTIFY_URL = "";
    /** 支付宝公钥 */
    String RSA_PUBLIC = "";
  }

  interface WxPay {
    /** 微信 AppID，在微信开放平台创建应用，并开通支付能力 */
    String APP_ID = "";
    /** 商户号 */
    String WX_SHOP_NUM = "";
    /** 微信应用密钥 */
    String WX_API_KEUSTORE = "";
  }
}
